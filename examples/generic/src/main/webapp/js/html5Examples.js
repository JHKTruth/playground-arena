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
 * Would have included this file as part of main.js for optimization; 
 * however there exists an issue with arbor where it, I think, tries to perform 
 * loading of some content and it blows up. 
 */
define(["jquery", "tasks/canvasTasks", "thirdParty/jQuery/plugins/slides.min.jquery", 
        "order-min!tasks/_baseTasks", "order-min!tasks/html5Tasks", "domReady-min!"], function($, canvasTasks) {
	
	$(function() {

    	$(".gwSlides").slides({
            preload: true,
            //preloadImage: '${jQueryPluginCssUrl}/images/slider/loading.gif',
            container: "gwSlidesContainer",
            next: "gwSlidesNext",
            prev: "gwSlidesPrev",
            generatePagination: true,
            //play: 5000,
            //pause: 2500,
            hoverPause: true
        });
    	
    	new canvasTasks("#canvasExperimental", "#shader-fs", "#shader-vs").init();
    	
    });
	
});