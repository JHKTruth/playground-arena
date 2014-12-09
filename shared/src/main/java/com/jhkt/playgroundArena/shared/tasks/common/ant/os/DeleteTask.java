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
import org.apache.tools.ant.taskdefs.Delete;

import com.jhkt.playgroundArena.shared.tasks.common.ant.AbstractAntBaseTask;

/**
 * @author Ji Hoon Kim
 */
public final class DeleteTask extends AbstractAntBaseTask {

	private static final String DELETE_TARGET = "delete";

	private final Delete _deleteTask;
	private final Target _deleteTarget;

	private String _deleteResource;
	private boolean _isDirectory;

	public DeleteTask() {
		super();
	}

	public DeleteTask(String deleteResource, boolean isDirectory) {
		super();
		_deleteResource = deleteResource;
		_isDirectory = isDirectory;
	}

	{
		_deleteTarget = new Target();
		_deleteTarget.setName(DELETE_TARGET);
		_deleteTarget.setProject(_taskProject);
		_taskProject.addTarget(_deleteTarget);

		_deleteTask = new Delete();
		_deleteTask.setOwningTarget(_deleteTarget);
		_deleteTask.setProject(_taskProject);

		_deleteTarget.addTask(_deleteTask);
	}

	protected void performTask() {

		if (_isDirectory) {
			_deleteTask.setDir(new File(_deleteResource));
		} else {
			_deleteTask.setFile(new File(_deleteResource));
		}

		_deleteTask.maybeConfigure();

		try {

			buildProject(DELETE_TARGET);

		} catch (BuildException buildException) {
			_taskProject.fireBuildFinished(buildException);
			StringBuilder errorMessage = new StringBuilder();
			errorMessage
					.append("Error in Delete's performTask with following fields \n");
			errorMessage.append(toString());
			throw new RuntimeException(errorMessage.toString(), buildException);
		}

	}

	public String toString() {
		StringBuilder content = new StringBuilder();
		content.append("deleteResource [ ");
		content.append(_deleteResource);
		content.append(" ] ");
		content.append("isDirectory [ ");
		content.append(_isDirectory);
		content.append(" ] ");

		return content.toString();
	}

	public DeleteTask deleteResource(String deleteResource) {
		_deleteResource = deleteResource;
		return this;
	}

	public DeleteTask directory(boolean isDirectory) {
		_isDirectory = isDirectory;
		return this;
	}

}
