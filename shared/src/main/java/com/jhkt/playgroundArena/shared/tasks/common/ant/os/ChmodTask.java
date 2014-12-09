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
import org.apache.tools.ant.taskdefs.Chmod;

import com.jhkt.playgroundArena.shared.tasks.common.ant.AbstractAntBaseTask;

/**
 * @author Ji Hoon Kim
 */
public final class ChmodTask extends AbstractAntBaseTask {

	private static final String CHMOD_TARGET = "chmod";

	private final Chmod _chmodTask;
	private final Target _chmodTarget;

	private File _dir;
	private String _permission;
	private String _fileInclusionRegExp;

	public ChmodTask() {
		super();
	}

	public ChmodTask(File dir, String permission, String fileInclusionRegExp) {
		super();

		_dir = dir;
		_permission = permission;
		_fileInclusionRegExp = fileInclusionRegExp;
	}

	{
		_chmodTarget = new Target();
		_chmodTarget.setName(CHMOD_TARGET);
		_chmodTarget.setProject(_taskProject);
		_taskProject.addTarget(_chmodTarget);

		_chmodTask = new Chmod();
		_chmodTask.setOwningTarget(_chmodTarget);
		_chmodTask.setProject(_taskProject);

		_chmodTarget.addTask(_chmodTask);
	}

	@Override
	protected void performTask() {

		_chmodTask.setDir(_dir);

		_chmodTask.setPerm(_permission);

		_chmodTask.setIncludes(_fileInclusionRegExp);

		_chmodTask.maybeConfigure();

		try {

			buildProject(CHMOD_TARGET);

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
		content.append("dir [ ");
		content.append(_dir);
		content.append(" ] ");
		content.append("permission [ ");
		content.append(_permission);
		content.append(" ] ");
		content.append("fileInclusionRegExp [ ");
		content.append(_fileInclusionRegExp);
		content.append(" ] ");
		return content.toString();
	}

	public ChmodTask dir(File dir) {
		_dir = dir;
		return this;
	}

	public ChmodTask permission(String permission) {
		_permission = permission;
		return this;
	}

	public ChmodTask fileInclusionRegExp(String fileInclusionRegExp) {
		_fileInclusionRegExp = fileInclusionRegExp;
		return this;
	}

}
