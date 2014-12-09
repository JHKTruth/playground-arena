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
package com.jhkt.playgroundArena.shared.tasks.common.ant;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;

import com.jhkt.playgroundArena.shared.tasks.common.AbstractTask;

/**
 * @author Ji Hoon Kim
 */
public abstract class AbstractAntBaseTask extends AbstractTask {

	protected Project _taskProject;
	private DefaultLogger _consoleLogger;

	public AbstractAntBaseTask() {
		super();
		_taskProject = new Project();
		_consoleLogger = new DefaultLogger();
		_consoleLogger.setErrorPrintStream(System.err);
		_consoleLogger.setOutputPrintStream(System.out);
		_consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
		_taskProject.addBuildListener(_consoleLogger);
	}

	protected void buildProject(String targetToExecute) {
		_taskProject.fireBuildStarted();
		_taskProject.init();
		_taskProject.executeTarget(targetToExecute);
		_taskProject.fireBuildFinished(null);
	}
	
}
