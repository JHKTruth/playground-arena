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
 * Main content for various jpa plugin. Meaning this plugin is responsible for setting up the 
 * namespace of jpa. 
 * 
 * (1) Trailing dollar sign is a convention that indicates a stored reference to a wrapped set [i.e. references$]
 */
define(["jquery", "jquery.jpa.generic", "jquery.jpa.multipleComponent", "domReady-min!"], function($, generic, multipleComponent, domReady) {
	
	var rootNSMethods = $.extend(generic.rootNS, multipleComponent.rootNS),
		fnNSMethods = $.extend(generic.fnNS, multipleComponent.fnNS);
	
	$.fn.jpa = function _jpa(method) {
	    
		if(fnNSMethods[method]) {
			return fnNSMethods[method].apply(this, Array.prototype.slice.call(arguments, 1));
	    } else {
	      $.error('Method ' + method + ' does not exist on jQuery.jpa' );
	    }
	};
	
	$.jpa = function _jpa(method) {
	    
		if(rootNSMethods[method]) {
			return rootNSMethods[method].apply(this, Array.prototype.slice.call(arguments, 1));
	    } else {
	      $.error('Method ' + method + ' does not exist on jQuery.jpa' );
	    }
	};
	
	$("input[data-fieldType='Array']").jpa("allowMultipleStoreOfInputValues");
	
});