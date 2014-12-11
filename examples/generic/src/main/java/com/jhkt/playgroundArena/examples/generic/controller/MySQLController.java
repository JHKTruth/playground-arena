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

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jhkt.playgroundArena.db.sql.mysql.beans.User;
import com.jhkt.playgroundArena.shared.beans.Result;
import com.jhkt.playgroundArena.shared.tasks.MySQLTaskRunner;

/**
 * @author Ji Hoon Kim
 */
@Controller
@RequestMapping("/mySQL")
public class MySQLController extends BaseController {
    
	private MySQLTaskRunner taskRunner;
	
	@RequestMapping(value="/welcomeView", method=RequestMethod.GET)
    public ModelAndView welcomeView() {
		
		Map<String, Object> model = new HashMap<>();
		model.put(VIEW_KEYS.USER_LIST.name(), taskRunner.getUsers());
		
		return new ModelAndView("mySQLWelcome", model);
    }
	
	@RequestMapping(value="/viewUserInfoView", method=RequestMethod.GET)
    public ModelAndView viewUserInfoView(@RequestParam Long userId) {
		
		Map<String, Object> model = new HashMap<>();
		model.put(VIEW_KEYS.USER.name(), taskRunner.getUser(userId));
		
		return new ModelAndView("mySQLViewUserInfo", model);
    }
	
	@RequestMapping(value="/createUser", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	@ResponseBody
	public Result createUser(@RequestBody User user) {
		
		return new Result(Result.CODE.SUCCESS);
	}
	
	@Resource(name="mySQLSimple")
	public void setTaskRunner(MySQLTaskRunner taskRunner) {
        this.taskRunner = taskRunner;
    }
	
}
