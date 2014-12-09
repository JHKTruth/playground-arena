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
importScripts("workerTasks.js");

openPorts = [];

addEventListener("connect", connect, false);

function connect(event) {
	var port = event.ports[0];
	openPorts.push(port);
	port.onmessage = sendToAll;
};

function sendToAll(event) {
	var openPortsLength = openPorts.length;
	
	while(openPortsLength--) {
		openPorts[openPortsLength].postMessage("Message has been sent: ", event);
	}
	
	onmessage(event);
}
onmessage = function(event) {
	
	if(event.data) {
		var request = JSON.parse(event.data);
		if(!request.taskClazz) {
			close();
		}else {
			var _instance = new request.taskClazz(request);
			_instance.performTask();
		}
	}
	
}