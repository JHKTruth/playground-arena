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
package com.jhkt.playgroundArena.examples.generic.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jhkt.playgroundArena.db.nosql.mongodb.beans.UserDocument;
import com.jhkt.playgroundArena.shared.tasks.MongoDBTaskRunner;

/**
 * @author Ji Hoon Kim
 */
@Controller
@RequestMapping("/mongoDB")
public class MongoDBController extends BaseController {
    
	private MongoDBTaskRunner taskRunner;
	
    @RequestMapping(method=RequestMethod.GET)
    @ModelAttribute
    public UserDocument getUser(@RequestParam String userId) {
        return null;
    }
    
    @RequestMapping(value="/users/{user}", method=RequestMethod.GET, produces="application/json")
    @ModelAttribute
    /*
     * Define a converter from a String to an UserDocument
     */
    public void getUser(@ModelAttribute("user") UserDocument user, BindingResult result) {
        
    }
    
    @RequestMapping(value="/viewUserInfoView/{userId}", method=RequestMethod.GET)
    public ModelAndView viewUserInfoView(@PathVariable Long userId) {
		/*
		Map<String, Object> model = new HashMap<String, Object>();
		model.put(VIEW_KEYS.USER.name(), taskRunner.getUser(userId));
		
		return new ModelAndView("mongoDBViewUserInfo", model);*/
    	
    	return null;
    }
	
	//@Resource(name="mongoDBSimple")
    public void setTaskRunner(MongoDBTaskRunner taskRunner) {
        this.taskRunner = taskRunner;
    }
    
}
