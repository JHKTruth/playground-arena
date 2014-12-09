/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/*
 * This file is mainly for documentation purpose. Meaning I am playing around 
 * with some of the content so most of the code is not complete as I wanted 
 * something to reference by rather than books + online documentation.
 */

define("tasks/html5Tasks", ["tasks/_baseTasks"], function(_BaseTask) {
	
    var GeolocationTask = function(request) {
    	_BaseTask.apply(this, arguments);
    };
    GeolocationTask.prototype = new _BaseTask();
    GeolocationTask.prototype.constructor = GeolocationTask;
    
    GeolocationTask.prototype.ERROR_CONDITIONS = ["Unknown", "Permission denied", "Position is not available", "Request timeout"];
    
    GeolocationTask.prototype.performTask = function() {
        
        var _this = this;
        navigator.geolocation.getCurrentPosition( function(position) {
            //{coords: {latitude: "", longitude: "", altitude: "", accuracy: "", altitudeAccuracy: "", heading: "", speed: ""}, timestamp: ""}
            
            postMessage(JSON.stringify(_this.provideSuccessDataEntry(position)));
            }, function(error) {
                console.info("Error with : ", error, GeolocationTask.prototype.ERROR_CONDITIONS[parseInt(error, 10) - 1]);
                postMessage(JSON.stringify({taskResult: _BaseTask.prototype.RESULT_CODE.ERROR, error: error, instance: _this}));
                }, {
                    //enableHighAccuracy: true, => used for Mobile devices that access the GPS application.
                    timeout: 2000, 
                    maximumAge: 0
                    } );
    };
    
    var FibonacciComputeLongTask = function(request) {
        _BaseTask.apply(this, arguments);
    };
    FibonacciComputeLongTask.prototype = new _BaseTask();
    FibonacciComputeLongTask.prototype.constructor = FibonacciComputeLongTask;
    
    FibonacciComputeLongTask.cache = {};
    
    FibonacciComputeLongTask.prototype.performTask = function() {
        var computeRequest = this.request.computeRequest;
        if(typeof computeRequest === "undefined") {
            throw new Error("computeRequest field was not passed");
        }else {
            postMessage(JSON.stringify(this.provideSuccessDataEntry(this.fibonacci(computeRequest))));
        }
    };
    
    FibonacciComputeLongTask.prototype.fibonacci = function(val) {
        /*
         * Of course not even in the least optimal solution [Strassen's algorithm is the correct one], 
         * but this is just to test out the caching.
         */
        if(val < 2) {
            return val;
        }
        
        var computedVal = -1;
        if(FibonacciComputeLongTask.cache.hasOwnProperty(val)){
            computedVal = FibonacciComputeLongTask.cache[val];
        }else {
            computedVal = this.fibonacci(val-1) + this.fibonacci(val-2);
            FibonacciComputeLongTask.cache[val] = computedVal;
        }
        
        return computedVal;
    };
    
    var WebSocket = function(request) {
        _BaseTask.apply(this, arguments);
    };
    WebSocket.prototype = new _BaseTask();
    WebSocket.prototype.constructor = WebSocket;
    
    WebSocket.prototype.performTask = function() {
        
        if(typeof WebSocket !== "undefined") {
            if(typeof this.request.url !== "undefined") {
                var ws = new WebSocket(this.request.url);
                
                ws.addEventListener("message", function(event) {
                    
                    postMessage(JSON.stringify(this.provideSuccessDataEntry(event.data)));
                }, false);
                
                ws.addEventListener("close", function() {
                    ws.send(JSON.stringify({
                        action: "closing"
                    }));
                }, false);
                
                ws.addEventListener("open", function() {
                    ws.send(JSON.stringify({
                        action: "opening"
                    }));
                }, false);
            }else {
                throw new Error("Field of url was not provided to the request");
            }
        }
        
    };
    
    var WebSQL = function(request) {
        _BaseTask.apply(this, arguments);
    };
    WebSQL.prototype = new _BaseTask();
    WebSQL.prototype.constructor = WebSQL;
    
    WebSQL.prototype.performTask = function() {
        
        try{
            if(typeof window.openDatabase === "undefined"){
                throw new Error("window.openDatabase is not defined/WebSQL is not supported for the current browser");
            }
            
            var _this = this,
                req = _this.request;
            
            if(typeof req.sql !== "undefined" &&
                    typeof req.dbName !== "undefined" && 
                    typeof req.dbVersion !== "undefined" && 
                    typeof req.dbSize !== "undefined") {
                
                var db = openDatabase(req.dbName, req.dbVersion, (req.dbDescription || ""), req.dbSize);
                db.transaction(function(tx) {
                    tx.executeSql(req.sql, (req.arguments || []), function(tx, results) {
                        postMessage(JSON.stringify(_this.provideSuccessDataEntry(results, tx)));
                        
                    }, function(tx, error) {
                        postMessage(JSON.stringify({taskResult: _BaseTask.prototype.RESULT_CODE.ERROR, error: error, instance: _this, tx: tx}));
                    });
                });
                
            }else {
                throw new Error("Required parameters of: sql, dbName, dbVersion, dbSize is/are missing");
            }
            
        }catch(error) {
            console.info("errored out during the DB execution");
            throw error;
        }
        
    };
    
    var IndexedDB = function(request) {
        _BaseTask.apply(this, arguments);
    };
    IndexedDB.prototype = new _BaseTask();
    IndexedDB.prototype.constructor = IndexedDB;
    
    IndexedDB.prototype.dbOpenSuccess = function(event) {
        this.db = event.result || event.target.result;
        
        if(this.db.version === ''){
            var versionRequest = this.db.setVersion(this.request.db.version);
            versionRequest.addEventListener("success", this.dbCreateDBSuccess, false);
            versionRequest.addEventListener("error", this.genericError, false);
        }else{
            this.performTransactionTask();
        }
    };
    
    IndexedDB.prototype.dbCreateDBSuccess = function(event) {
        this.db = event.result || event.target.result;
        var request = this.request;
        
        if(request.db.objectStores) {
            
            var objectStores = request.db.objectStores;
            for(var i=0, objectStoresLength=objectStores.length; i < objectStoresLength; i++) {
                var objectStore = objectStores[i], 
                    createOSArgs = [objectStore.name];
                if(objectStore.keyPath) {
                    createOSArgs.push({keyPath: objectStore.keyPath});
                }
                
                if(typeof objectStore.autoIncrement !== "undefined") {
                    createOSArgs.push(objectStore.autoIncrement);
                }
                
                var store = this.db.createObjectStore.apply(this.db, createOSArgs);
                
                if(objectStore.indexes) {
                    var indexes = objectStore.indexes;
                    for(var j=0, indexesLength=indexes.length; j < indexesLength; i++) {
                        var index = indexes[j], 
                            createIndexArgs = [index.name, index.property];
                        
                        if(typeof index.unique !== "undefined") {
                            createIndexArgs.push({unique: index.unique});
                        }
                        
                        store.createIndex.apply(store, createIndexArgs);
                    }
                    
                }
            }

            this.performTransactionTask();
        }
        
    };
    
    IndexedDB.prototype.performTransactionTask = function() {
        var request = this.request, 
            transactionRequests = request.transactions;
        
        for(var i=0, transactionRequestsLength=transactionRequests.length; i < transactionRequestsLength; i++) {
            var transactionRequest = transactionRequests[i], 
                transactionRequestArgs = [transactionRequest.spanObjectStores];
            
            if(typeof transactionRequest.transactionLevel !== "undefined") {
                transactionRequestArgs.push(transactionRequest.transactionLevel);
            }
            
            var transaction = this.db.transaction.apply(this.db, transactionRequestArgs);
            var objectStore = transaction.objectStore(transactionRequest.objectStore);
            
            this.performTransactionTaskHelper(transactionRequest.tasks, objectStore);
        }
    };
    
    IndexedDB.prototype.performTransactionTaskHelper = function(tasks, objectStore) {

        var _this = this;
        for(var i=0, tasksLength=tasks.length; i < tasksLength; i++) {
            (function() {
                var task = tasks[i];
                var taskRequest = objectStore[task.method].apply(objectStore, task.args || []);
                taskRequest.addEventListener("success", function(event) {
                    postMessage(JSON.stringify(_this.provideSuccessDataEntry(event)));
                    if(typeof task.tasks !== "undefined") {
                        //for cases such as openCursor and etcetera
                        _this.performTransactionTaskHelper(task.tasks, objectStore);
                    }
                    
                    var result = event.result || event.target.result;
                    if(result && result["continue"]) {
                        //means of cursor so invoke continue
                        result["continue"]();
                    }
                }, false);
            })();
        }
        
    };
    
    IndexedDB.prototype.performTask = function() {
        
        try{
            var indexedDB = window.mozIndexedDB || window.webkitIndexedDB;
            
            if(typeof indexedDB !== "undefined") {
                var req = this.request;
                if(typeof req.db !== "undefined" && req.db.name !== "undefined") {
                    
                    var db = indexedDB.open(req.db.name);
                    db.addEventListener("success", this.dbOpenSuccess, false);
                    db.addEventListener("error", this.genericError, false);
                    
                }else {
                    throw new Error("Required parameters of: dbName is/are missing");
                }
            }
        }catch(error) {
            console.info("errored out during the IndexedDB execution");
            throw error;
        }
        
    };
    
    //FILE API, just for reference
    var _FileTask = function(request) {
        _BaseTask.apply(this, arguments);
    };
    _FileTask.prototype = new _BaseTask();
    _FileTask.prototype.constructor = _FileTask;
    
    _FileTask.prototype.performTask = function() {
        //TODO
    };
    
    var FileTaskAPI = function(request) {
        _FileTask.apply(this, arguments);
    };
    FileTaskAPI.prototype = new _FileTask();
    FileTaskAPI.prototype.constructor = FileTaskAPI;
    
    FileTaskAPI.prototype.performTask = function() {
        
        var req = this.request;
        //Later play w/ Blob
        if(typeof req.fileTasks !== "undefined") {

            if(FileReader) {
                var fileTasks = req.fileTasks;
                for(var i=0, fileTasksLength=fileTasks.length; i < fileTasksLength; i++) {
                	
                    var reader = new FileReader(), 
                        fileTask = fileTasks[i];
                    //should use onload since the listeners might not be implemented yet 
                    reader.addEventListener("load", function(event){
                        var result = event.result || event.target.result;
                        postMessage(JSON.stringify(this.provideSuccessDataEntry(result)));
                    }, false);
                    reader.addEventListener("error", this.genericError, false);
                    reader[fileTask.method].apply(reader, fileTask.args || []);
                    
                }
            }
            
        }else {
            throw new Error("Required parameters of: fileTasks is/are missing");
        }
        
    };
    
    var FileExpansionTaskAPI = function(request) {
        this.root = null;
        _FileTask.apply(this, arguments);
    };
    FileExpansionTaskAPI.prototype = new _FileTask();
    FileExpansionTaskAPI.prototype.constructor = FileExpansionTaskAPI;
    
    FileExpansionTaskAPI.prototype.performTask = function() {
        
        var _this = this,
            req = _this.request;
        
        if(typeof req.fileTasks !== "undefined" && 
                typeof req.fileSystemInfo !== "undefined") {
            
            if(requestFileSystem) {
                var fileSystemInfo = req.fileSystemInfo;
                
                requestFileSystem(fileSystemInfo.type, fileSystemInfo.size, function(event) {
                    
                    _this.root = event.root;
                    _this.performFileTask(req.fileTasks);
                }, this.genericError);
                
            }
            
        }else {
            throw new Error("Required parameters of: fileTasks and fileSystemInfo is/are missing");
        }
        
    };
    
    FileExpansionTaskAPI.prototype.performFileTask = function(fileTasks) {
        if(!fileTasks) {
            return;
        }
        
        /*
         * Many of the File Expansion APIs require the previous result and since this 
         * is mainly for documentation purpose am leaving out those cases. Perhaps in the 
         * future when I get bored.
         */
        for(var i=0, fileTasksLength=fileTasks.length; i < fileTasksLength; i++) {
            
            (function() {
            	
            	var fileTask = fileTasks[i], 
                	fileTaskArgs = fileTask.args || [],
                	_this = this;
            	
                //Quite a deal of trouble just for documentation
                if(fileTask.method === "createReader" && 
                    fileTasksLength > 0 && 
                    fileTasks[fileTasksLength - 1] === "readEntries") {
                    fileTasksLength--;
                    
                    var reader = _this.previousResult.createReader();
                    //the readEntries method reads the list of entries by blocks
                    var read = function() {
                        reader.readEntries(function(files) {
                            if(files.length){
                                postMessage(JSON.stringify(_this.provideSuccessDataEntry(files)));
                                read();
                            }else{
                                _this.performFileTask(fileTask.fileTasks);
                            }
                        }, this.genericError);
                    };
                }else {
                    fileTaskArgs.push(function(event) {
                        
                        _this.previousResult = event;
                        postMessage(JSON.stringify(_this.provideSuccessDataEntry(event)));
                        _this.performFileTask(fileTask.fileTasks);
                    });
                    
                    fileTaskArgs.push(this.genericError);
                }
                
                this.root[fileTask.method].apply(this.root, fileTaskArgs);
            })();
        }
    };
    
    var FileContentTaskAPI = function(request) {
        FileExpansionTaskAPI.apply(this, arguments);
    };
    FileContentTaskAPI.prototype = new FileExpansionTaskAPI();
    FileContentTaskAPI.prototype.constructor = FileContentTaskAPI;
    
    FileContentTaskAPI.prototype.performFileTask = function(fileTasks) {
        if(!fileTasks) {
            return;
        }
        
        for(var i=0, fileTasksLength=fileTasks.length; i < fileTasksLength; i++) {
            (function() {
                var fileTask = fileTasks[i], 
                    _this = this;
                
                this.root.getFile(fileTask.name, fileTask.args, function(entry) {
                    entry.createWriter(function(writer){
                        writer.addEventListener("writeend", function() {
                            postMessage(JSON.stringify(_this.provideSuccessDataEntry(fileTask)));
                        }, false);
                    }, this.genericError);
                    
                    var blobBuilder = BlobBuilder || WebKitBlobBuilder;
                    if(blobBuilder) {
                        var blob = new blobBuilder();
                        blob.append(fileTask.text);
                        writer.write(blob.getBlob());
                    }
                }, this.genericError);
            })();
        }
    };
    
});