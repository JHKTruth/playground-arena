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
 * Sample generic JQuery Plugin [namely to play around] 
 * 
 */
define(["jquery"], function($) {
	
	/*
	 * Modification to expr[":"] allows changes to CSS selector
	 * 
	 * Arguments List:
	 * 1) Candidate DOM element
	 * 2) Integer index that gives the element's position within an array of candidate elements
	 * 3) Array result of a call to the RegExp.exec() method. The fourth element of this array is the 
	 *    value, if any, within parentheses after the pseudoclass filter
	 * 4) Array of candidate elements
	 */
	$.expr[":"].data = function(e, idx, match, array) {
		//To allow div:data(fieldContainer)
		return e.hasAttribute("data-" + match[3]);
	};
	
	var methods = {
			
			rootNS: {
				
				unrollArray: function(_array, _function, _scope) {
					
					var ARRAY_UNROLL_SIZE = 8,
						retValues = new Array(_array.length),
						loopLimit = parseInt(_array.length / ARRAY_UNROLL_SIZE, 10),
						fragmentLoop = _array.length % ARRAY_UNROLL_SIZE;
					
					for(var i=0, j=0; i < loopLimit; i++) {
						retValues[j] = _function.call(_scope, _array[j++]);
						retValues[j] = _function.call(_scope, _array[j++]);
						retValues[j] = _function.call(_scope, _array[j++]);
						retValues[j] = _function.call(_scope, _array[j++]);
						retValues[j] = _function.call(_scope, _array[j++]);
						retValues[j] = _function.call(_scope, _array[j++]);
						retValues[j] = _function.call(_scope, _array[j++]);
						retValues[j] = _function.call(_scope, _array[j++]);
					}
					
					/*
					 * Bad programming style, but just a note regarding the fact
					 * that Javascript only has function scope
					 */ 
					
					for(var p=0; p < fragmentLoop; p++) {
						retValues[j] = _function.call(_scope, _array[j++]);
					}
					
					return retValues;
				}
				
			},
			
			fnNS: {
				
				/*
				 * To add wrapper methods to jQuery, we must assign them as properties 
				 * to an object named fn in the $ namespace. $.fn is merely an alias for 
				 * an internal prototype property of an object that jQuery uses to create 
				 * its wrapper objects
				 */
				makeRoundCorners: function _makeRoundCorners() { 
					/*
					 * this refers to the wrapped set. If one wishes to work w/ individual elements 
					 * in the set then one should invoke return this.each(function() { });
					 */
					return this.addClass("genericBorderRadius genericBoxShadow");
				}
				
			}
			
	};
	
	return methods;
	
});