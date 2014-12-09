<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="com.googlecode.jihoonPlaygroundArena.examples.generic.nls.General" />
<!DOCTYPE html>
<html lang=en>
<!--
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
//-->
    <head>
        <meta charset="utf-8">
        <spring:eval expression="@applicationProps['application.version']" var="applicationVersion" scope="session" />
        <spring:url value="/" var="root" scope="session" />
        
        <spring:url value="/controller/js-{applicationVersion}/main.js" var="mainJsUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        <spring:url value="/controller/js-{applicationVersion}/require-jquery.js" var="requireJQueryJsUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        <spring:url value="/controller/js-{applicationVersion}/thirdParty/jQuery/jquery.plugins.resourceJS.examples.js" var="jQueryPluginsResourceJsExamplesJsUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        
        <spring:url value="/controller/css-{applicationVersion}/thirdParty/jQuery/plugins/images/slider/arrow-prev.png" var="arrowPrevImageUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        <spring:url value="/controller/css-{applicationVersion}/thirdParty/jQuery/plugins/images/slider/arrow-next.png" var="arrowNextImageUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        <spring:url value="/controller/css-{applicationVersion}/thirdParty/jQuery/plugins/images/slider/example-frame.png" var="exampleFrameImageUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        
        <spring:url value="/controller/css-{applicationVersion}/all.css" var="allCssUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        
        <link href="${allCssUrl}" rel="stylesheet"></link>
    </head>
    
    <body>
        <div class="clear">
            <nav class="clear links genericBorderRadius genericBoxShadow">
                <div class="linksHeader" id="toTransform"><fmt:message key="PAGE_LINKS" /></div>
                <ul>
                    <li><a data-example-link href="${root}index.html"><fmt:message key="MAIN_INDEX" /></a>
                    </li>
                </ul>
            </nav>
            
            <section class="hProduct genericBorderRadius genericBoxShadow floatR" itemscope itemtype="http://www.data-vocabulary.org/Product/">
                <details>
                    <summary class="panelHeader" itemprop="name"><fmt:message key="REQUIRE_JS_PAGE_DESC" /></summary>
                    <article class="pad10">
                        <div class="gwSlidesContent">
                            <div class="gwSlidesExample">
                                <div class="gwSlides">
                                    <div class="gwSlidesContainer">
                                        <div class="gwSlide">
                                            <h1><fmt:message key="ARBOR_WITH_MOUSING_EXAMPLE" /></h1>
                                            <p><fmt:message key="ARBOR_EXAMPLE_DESC" /></p>
                                            <div>
                                                <canvas id="arborMousing" width="730" height="460"></canvas>
                                            </div>
                                        </div>
                                        
                                        <div class="gwSlide">
                                            <h1><fmt:message key="TODO" /></h1>
                                            <p><fmt:message key="AN_ANOTHER_EXAMPLE" /></p>
                                        </div>
                                    </div>
                                    <a href="#" class="gwSlidesPrev"><img src="${arrowPrevImageUrl}" width="24" height="43" alt="Arrow Prev"></a>
                                    <a href="#" class="gwSlidesNext"><img src="${arrowNextImageUrl}" width="24" height="43" alt="Arrow Next"></a>
                                </div>
                                <img src="${exampleFrameImageUrl}" width="739" height="341" alt="Example Frame" class="gwSlidesFrame">
                            </div>
                            
                            <div class="gwSlidesFooter">
                            	<fmt:message key="SLIDES_REFERENCE" />
                            </div>
                        </div>
                    </article>
                </details>
            </section>
        </div>
        
        <script type="text/javascript" data-main="${mainJsUrl}" src="${requireJQueryJsUrl}"></script>
        <script type="text/javascript" src="${jQueryPluginsResourceJsExamplesJsUrl}"></script>
    </body>
</html>