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

define("tasks/_baseTasks", [], function() {
	
	var _BaseTask = function(request) {
		this.data = {};
		this.request = request ? JSON.parse(request) : null;
	};
	
	_BaseTask.prototype.RESULT_CODE = {
			SUCCESS: 1,
			ERROR: 0
	};
	
	_BaseTask.prototype.genericError = function(event) {
		var errorContent = {taskResult: this.RESULT_CODE.ERROR, error: event, instance: this};
		postMessage(JSON.stringify(errorContent));
	};
	
	_BaseTask.prototype.provideSuccessDataEntry = function(content) {
		
		var additionalContent = [];
		if(arguments.length > 1) {
			additionalContent = Array.prototype.slice.call(arguments, 1);
		}
		
		this.data = {
				content: content,
				additionalContent: additionalContent,
				taskResult: this.RESULT_CODE.SUCCESS,
				instance: this
		};
		
		console.info("Success Data ", this.data);
	};
	
	_BaseTask.prototype.performTask = function() {
		throw new Error("Should be implemented by sub classes");
	};
	
	return _BaseTask;
});