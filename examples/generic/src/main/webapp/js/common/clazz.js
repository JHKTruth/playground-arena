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

define("common/clazz", [], function() {
	
	/*
	 * In ECMAScript 5, the prototypal inheritance pattern becomes officially a part of the language. 
	 * This pattern is implemented through the method Object.create(). In other words, you won't need to 
	 * roll your own function similar to object(); it will be built into the language...Object.create() 
	 * accepts an additional parameter, an object. The properties of the extra object will be added as 
	 * own properties of the new child object being returned. This is a convenience that enables you to 
	 * inherit and build upon the child object with one method call.
	 * 
	 * ECMAScript 5 adds a method bind() to Function.prototype, making it just as easy to use as apply() 
	 * and call(). So you can do expressions like: var newFunc = obj.someFunc.bind(myobj, 1, 2, 3);  
	 * This means bind together someFunc() and myobj and also pre-fill the first three arguments that someFunc() expects.
	 */
	
	var clazz = function _clazz(parent, properties) {
		
		var child, 
			proxy;
		
		child = function () {
			if(child.uber && child.uber.hasOwnProperty("__construct")) {
				child.uber.__construct.apply(this, arguments);
			}
			if(child.prototype.hasOwnProperty("__construct")) {
				child.prototype.__construct.apply(this, arguments);
			}
		};
		
		parent = parent || object;
		
		//so to break the direct link between parent's and child's prototype while benefiting from prototype
		proxy = function() {};
		child.prototype = new proxy();
		child.uber = parent.prototype;
		child.prototype.constructor = child;
		
		for(var i in properties) {
			if(properties.hasOwnProperty(i)) {
				child.prototype[i] = properties[i];
			}
		}
	};
	
	return clazz;
});