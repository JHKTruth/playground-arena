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

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Mkdir;

import com.jhkt.playgroundArena.shared.tasks.common.ant.AbstractAntBaseTask;

/**
 * @author Ji Hoon Kim
 */
public final class MkdirTask extends AbstractAntBaseTask {

	private static final String MKDIR_TARGET = "mkdir";

	private final Mkdir _mkdirTask;
	private final Target _mkdirTarget;

	private String _file;

	public MkdirTask(){
		super();
	}

	public MkdirTask(String file){
		_file = file;
	}

	{
		_mkdirTarget = new Target();
		_mkdirTarget.setName(MKDIR_TARGET);
		_mkdirTarget.setProject(_taskProject);
		_taskProject.addTarget(_mkdirTarget);

		_mkdirTask = new Mkdir();
		_mkdirTask.setOwningTarget(_mkdirTarget);
		_mkdirTask.setProject(_taskProject);

		_mkdirTarget.addTask(_mkdirTask);
	}

	protected void performTask() {

		_mkdirTask.setDir(new File(_file));

		_mkdirTask.maybeConfigure();

		try {

			buildProject(MKDIR_TARGET);

		} catch (BuildException buildException) {
			_taskProject.fireBuildFinished(buildException);
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append("Error in Mkdir's performTask with following fields \n");
			errorMessage.append(toString());
			throw new RuntimeException(errorMessage.toString(), buildException);
		}

	}

	public String toString() {
		StringBuilder content = new StringBuilder();
		content.append("file [ ");
		content.append(_file);
		content.append(" ] ");

		return content.toString();
	}

	public MkdirTask file(String file) {
		_file = file;
		return this;
	}

}


