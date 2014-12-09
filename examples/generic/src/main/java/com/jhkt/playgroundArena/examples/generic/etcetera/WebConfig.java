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
package com.jhkt.playgroundArena.examples.generic.etcetera;

import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jhkt.playgroundArena.examples.generic.interceptors.OpenTimeInterceptor;

/**
 * By providing the below annotations RequestMappingHandlerMapping, a RequestMappingHandlerAdapter, and an ExceptionHandlerExceptionResolver 
 * (among others) in support of processing requests with annotated controller methods using annotations such as @RequestMapping , @ExceptionHandler, and others
 * are registered. Also various other components are enabled [too many to list, check out the Spring documentation for specifics]. In fact if you open 
 * @EnableWebMvc you can see the @Import statement.
 * 
 * @author Ji Hoon Kim
 */
@EnableWebMvc
@Configuration
@PropertySource("classpath:application.properties")
public class WebConfig extends WebMvcConfigurerAdapter {
    
	private static final int CACHE_PERIOD = 31556926;
	
    @Inject
    Environment env;
    
    @Override
    public void addFormatters(FormatterRegistry registry) {
        
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*
         * This option allows static resource requests following a particular URL pattern to be served by a ResourceHttpRequestHandler 
         * from any of a list of Resource locations. This provides a convenient way to serve static resources from locations other 
         * than the web application root, including locations on the classpath...Multiple resource locations may be specified using a 
         * comma-separated list of values. The locations specified will be checked in the specified order for the presence of the resource 
         * for any given request. [i.e. "classpath:/META-INF/public-web-resources/"]
         * 
         * To represent the URL correctly use Spring JSP tags: 
         * <spring:eval expression="@applicationProps['application.version']" var="applicationVersion"/>
         * <spring:url value="/spring-resources-{applicationVersion}" var="resourceUrl">
         *      <spring:param name="applicationVersion" value="${applicationVersion}"/>
         * </spring:url>
         * <script src="${resourceUrl}/some.js" type="text/javascript"> </script>
         * 
         */
        registry.addResourceHandler("/css-" + env.getProperty("application.version") + "/**").addResourceLocations("/css/").setCachePeriod(CACHE_PERIOD);
        registry.addResourceHandler("/images-" + env.getProperty("application.version") + "/**").addResourceLocations("/images/").setCachePeriod(CACHE_PERIOD);
        registry.addResourceHandler("/js-" + env.getProperty("application.version") + "/**").addResourceLocations("/js/").setCachePeriod(CACHE_PERIOD);
        registry.addResourceHandler("/themes-" + env.getProperty("application.version") + "/**").addResourceLocations("/themes/").setCachePeriod(CACHE_PERIOD);
        
        /*
         * Add the following entries so that content from other files [i.e. all.css] can access dependent files correctly  
         */
        registry.addResourceHandler("/themes/**").addResourceLocations("/themes/").setCachePeriod(CACHE_PERIOD);
    }
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    @Override
    public void configureMessageConverters( List<HttpMessageConverter<?>> converters) {
        
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new OpenTimeInterceptor(9, 18)).addPathPatterns("/mongoDB/*");
        
    }
    
}
