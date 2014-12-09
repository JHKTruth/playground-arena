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
package com.jhkt.playgroundArena.shared.tasks.common.ant.os;

import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.ExecTask;
import org.apache.tools.ant.types.Commandline.Argument;

import com.jhkt.playgroundArena.shared.tasks.common.ant.AbstractAntBaseTask;

/**
 * @author Ji Hoon Kim
 */
public final class CommandTask extends AbstractAntBaseTask {
	
	private static final String COMMAND_TARGET = "command";

	private final ExecTask _commandTask;
	private final Target _commandTarget;
	
	private String _command;
	private Set<String> _arguments;
	
	public CommandTask(String command, Set<String> arguments) {
		super();
		
		_command = command;
		_arguments = arguments;
	}
	
	{
		_commandTarget = new Target();
		_commandTarget.setName(COMMAND_TARGET);
		_commandTarget.setProject(_taskProject);
		_taskProject.addTarget(_commandTarget);

		_commandTask = new ExecTask();
		_commandTask.setOwningTarget(_commandTarget);
		_commandTask.setProject(_taskProject);
		_commandTask.setFailonerror(true);

		_commandTarget.addTask(_commandTask);
	}
	
	@Override
	protected void performTask() {
		
		_commandTask.setExecutable(_command);
		
		for(String arg : _arguments) {
        	Argument argument = _commandTask.createArg();
        	argument.setLine(arg);
        }
		
		_commandTask.maybeConfigure();
		
		try {
			System.out.println("Executing " + toString());
			buildProject(COMMAND_TARGET);
			
		} catch (BuildException buildException) {
			_taskProject.fireBuildFinished(buildException);
			StringBuilder errorMessage = new StringBuilder();
			errorMessage
					.append("Error in Chmod's performTask with following fields \n");
			errorMessage.append(toString());
			throw new RuntimeException(errorMessage.toString(), buildException);
		}
		
	}
	
	@Override
	public String toString() {
		StringBuilder content = new StringBuilder();
		content.append("command [ ");
		content.append(_command);
		content.append(" ] ");
		content.append("arguments [ ");
		content.append(_arguments);
		content.append(" ] ");
		return content.toString();
	}
	
	public CommandTask command(String command) { 
		_command = command;
		return this;
	}
	
	public CommandTask arguments(Set<String> arguments) { 
		_arguments = arguments;
		return this;
	}

}
