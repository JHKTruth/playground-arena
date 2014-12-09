<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="com.googlecode.jihoonPlaygroundArena.examples.generic.nls.General" />
<fmt:setBundle basename="com.googlecode.jihoonPlaygroundArena.examples.generic.nls.Cassandra" var="cassandra" />
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
        <title><fmt:message key="C.MAIN" bundle="${cassandra}" /></title>
        <spring:eval expression="@applicationProps['application.version']" var="applicationVersion" scope="session" />
        <spring:url value="/" var="root" scope="session" />
        
        <spring:url value="/controller/js-{applicationVersion}/cassandraMain.js" var="mainJsUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        <spring:url value="/controller/js-{applicationVersion}/require-jquery.js" var="requireJQueryJsUrl" scope="session">
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
            
            <section class="hProduct genericBorderRadius genericBoxShadow" itemscope itemtype="http://www.data-vocabulary.org/Product/">
	            <details>
	                <summary class="panelHeader" itemprop="name"><fmt:message key="SCREENSHOTS" /></summary>
	                <article class="screenShotArticle">
	                
	                    <div>
	                        <figure>
	                            <img data-img-summary="HTML5 Task Example" itemprop="image" src="" alt="HTML5TaskExample" />
	                            <figcaption><fmt:message key="CERTAIN_TASK_EXAMPLE" /></figcaption>
	                        </figure>
	                    </div>
	                </article>
	            </details>
        	</section>
        </div>
        
        <div class="clear">
	        <section class="genericBorderRadius genericBoxShadow">
	            <details>
	                <summary class="panelHeader"><fmt:message key="TASK_CONTENT" /></summary>
	            </details>
	        </section>
	    </div>
        
        <script type="text/javascript" data-main="${mainJsUrl}" src="${requireJQueryJsUrl}"></script>
    </body>
</html>