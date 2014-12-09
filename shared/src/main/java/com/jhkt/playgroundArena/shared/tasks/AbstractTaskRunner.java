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
package com.jhkt.playgroundArena.shared.tasks;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jhkt.playgroundArena.shared.tasks.common.TaskQueues;
import com.jhkt.playgroundArena.shared.tasks.common.TaskQueues.QUEUE_TASK_ID;
import com.jhkt.playgroundArena.shared.tasks.common.ant.os.CommandTask;

/**
 * @author Ji Hoon Kim
 */
abstract class AbstractTaskRunner implements ITaskRunner {
    
    private List<String> _initCommandTaskNames;
    
    {
        _initCommandTaskNames = new LinkedList<String>();
    }
	
	public LinkedHashMap<String, Set<String>> getInitCommands() {
    	return null;
    }
    public LinkedHashMap<String, Set<String>> getDestroyCommands() {
    	return null;
    }
	
	@Override
    public void init() {
		
		final Map<String, Set<String>> initCommands = getInitCommands();
		if(initCommands != null) {
			
			TaskQueues taskQueues = TaskQueues.getInstance();
			
			for(final String command : initCommands.keySet()) {
				/*
				 * Make sure that Spring supports usage of specific impl [i.e. LinkedHashMap] so the entries 
				 * will be traversed as they are listed within the config. 
				 */
				
			    String commandName = QUEUE_TASK_ID.COMMAND.getQueueTaskId(command);
			    _initCommandTaskNames.add(commandName);
				taskQueues.queueFutureTask(commandName, new CommandTask(command, initCommands.get(command)));
			}
			
		}
        
    }
    
    @Override
    public void destroy() {
        
    	final Map<String, Set<String>> destroyCommands = getDestroyCommands();
    	
    	TaskQueues taskQueues = TaskQueues.getInstance();
    	
    	for(String cmdTaskName : _initCommandTaskNames) { 
    	    taskQueues.cancelQueuedFutureTask(cmdTaskName);
    	}
    	
		if(destroyCommands != null) {
			
			for(final String command : destroyCommands.keySet()) {
				
				taskQueues.queueFutureTask(QUEUE_TASK_ID.COMMAND.getQueueTaskId(command), new CommandTask(command, destroyCommands.get(command)));
			}
			
		}
    	
		taskQueues.checkTaskEntrySizeAndShutDown();
    }
    
    @Override
    protected void finalize() throws Throwable {
    	destroy();
    	
    	super.finalize();
    	
    }
    
}
