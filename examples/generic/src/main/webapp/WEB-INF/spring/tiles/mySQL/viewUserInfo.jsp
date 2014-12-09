<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
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
        <title><tiles:getAsString name="title"/></title>
        
        <tiles:insertAttribute name="generalPageUrlDefinitions" />
        
        <spring:url value="/controller/mySQL/welcomeView" var="mySQLWelcome" />
        
        <spring:url value="/controller/js-{applicationVersion}/mySQLMain.js" var="mainJsUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
    </head>
    
    <body>
        <div class="clear">
            <nav class="clear links genericBorderRadius genericBoxShadow">
                <div class="linksHeader" id="toTransform"><fmt:message key="PAGE_LINKS" /></div>
                <ul>
                    <li><a data-example-link href="${root}index.html"><fmt:message key="MAIN_INDEX" /></a>
                    </li>
                    <li><a data-example-link href="${mySQLWelcome}"><tiles:getAsString name="mysql.main"/></a>
                    </li>
                </ul>
            </nav>
            
            <section class="hProduct genericBorderRadius genericBoxShadow" itemscope itemtype="http://www.data-vocabulary.org/Product/">
                <details>
                    <summary class="panelHeader" itemprop="name">
                    	<tiles:getAsString name="mysql.userid.info"/>
                    </summary>
                    <article class="screenShotArticle">
                    
                        <div>
                            <figure>
                                <img data-img-summary="MySQL Example" itemprop="image" src="" alt="MySQLExample" />
                                <figcaption><fmt:message key="CERTAIN_TASK_EXAMPLE" /></figcaption>
                            </figure>
                        </div>
                    </article>
                </details>
            </section>
        </div>
        
        <script type="text/javascript" data-main="${mainJsUrl}" src="${requireJQueryJsUrl}"></script>
    </body>
</html>