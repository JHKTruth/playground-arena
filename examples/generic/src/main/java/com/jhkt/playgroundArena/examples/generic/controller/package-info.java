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

/**
 * Note:
 * 1) Since many will use the default methodNameResolver [InternalPathMethodNameResolver], if for example a HadoopController 
 *      is set to path /hadoop/*, /hadoop/somethingTask.html?param=1 will map to HadoopController's somethingTask method
 * 2) If you are using Spring's IoC container in a non-web application environment; for example, in a rich client desktop environment; 
 *      you register a shutdown hook with the JVM. Doing so ensures a graceful shutdown and calls the relevant destroy methods on your 
 *      singleton beans so that all resources are released. Of course,     you must still configure and implement these destroy callbacks correctly.
 *      // add a shutdown hook for the above context...ctx.registerShutdownHook();
 * 4) The @Bean methods in a Spring component are processed differently than their counterparts inside a Spring @Configuration class. The difference is that 
 *      @Component classes are not enhanced with CGLIB to intercept the invocation of methods and fields. CGLIB proxying is the means by which invoking methods 
 *      or fields within @Configuration classes @Bean methods create bean metadata references to collaborating objects. Methods are not invoked with normal 
 *      Java semantics. In contrast, calling a method or field within a @Component classes @Bean method has standard Java semantics.
 * 5) The strategy for generating a name after adding a Set or a List is to peek into the collection, take the short class name of the first object in the collection, 
 *      and use that with List appended to the name. The same applies to arrays although with arrays it is not necessary to peek into the array contents.
 * 6) In Spring 3.1, the form input tag supports entering a type attribute other than 'text'. This is intended to allow rendering new HTML5 specific input types 
 *      such as 'email', 'date', 'range', and others. Note that entering type='text' is not required since 'text' is the default type.
 * 7) 
 * 
 * @author Ji Hoon Kim
 */
package com.jhkt.playgroundArena.examples.generic.controller;