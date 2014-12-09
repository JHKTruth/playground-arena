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
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.PatternSet;

import com.jhkt.playgroundArena.shared.tasks.common.ant.AbstractAntBaseTask;

/**
 * @author Ji Hoon Kim
 */
public final class FileCopyTask extends AbstractAntBaseTask {

	private static final String COPY_TARGET = "copy";

	private final Copy _copyTask;
	private final Target _copyTarget;

	private String _copyFile;
	private String _copyToFile;

	private String _copyDir;
	private List<String> _copyInclude;
	private List<String> _copyExclude;
	private String _copyTo;
	private FileSet _dirCopyFileSet;

	public FileCopyTask() {
		super();
	}

	public FileCopyTask(String copyFile, String copyToFile) {
		super();
		_copyFile = copyFile;
		_copyToFile = copyToFile;
	}

	public FileCopyTask(String copyDir, List<String> copyInclude,
			List<String> copyExclude, String copyTo) {
		super();
		_copyDir = copyDir;
		_copyInclude = copyInclude;
		_copyExclude = copyExclude;
		_copyTo = copyTo;
	}

	{
		_copyTarget = new Target();
		_copyTarget.setName(COPY_TARGET);
		_copyTarget.setProject(_taskProject);
		_taskProject.addTarget(_copyTarget);

		_copyTask = new Copy();
		_copyTask.setOwningTarget(_copyTarget);
		_copyTask.setProject(_taskProject);

		_copyTarget.addTask(_copyTask);
	}

	private void configureParameters() {

		if (_copyFile != null) {
			// means by file copy
			_copyTask.setFile(new File(_copyFile));

		} else {
			// means by dir copy with fileset
			_dirCopyFileSet = new FileSet();
			_dirCopyFileSet.setDir(new File(_copyDir));

			if (_copyInclude != null) {

				for (String currentCopyInclude : _copyInclude) {
					PatternSet.NameEntry copyIncludeNE = _dirCopyFileSet
							.createInclude();
					copyIncludeNE.setName(currentCopyInclude);
				}
			}

			if (_copyExclude != null) {

				for (String currentCopyExclude : _copyExclude) {
					PatternSet.NameEntry copyExcludeNE = _dirCopyFileSet
							.createExclude();
					copyExcludeNE.setName(currentCopyExclude);
				}
			}

			_copyTask.addFileset(_dirCopyFileSet);

		}

		if (_copyToFile != null) {
			_copyTask.setTofile(new File(_copyToFile));
		} else {
			_copyTask.setTodir(new File(_copyTo));
		}

		_copyTask.maybeConfigure();
	}

	protected void performTask() {

		configureParameters();

		try {

			buildProject(COPY_TARGET);

		} catch (BuildException buildException) {
			_taskProject.fireBuildFinished(buildException);
			StringBuilder errorMessage = new StringBuilder();
			errorMessage
					.append("Error in Copy's performTask with following fields \n");
			errorMessage.append(toString());
			throw new RuntimeException(errorMessage.toString(), buildException);
		}

	}

	public String toString() {
		StringBuilder content = new StringBuilder();
		content.append("copyFile [ ");
		content.append(_copyFile);
		content.append(" ] ");
		content.append("copyDir [ ");
		content.append(_copyDir);
		content.append(" ] ");
		content.append("copyInclude [ ");
		content.append(_copyInclude);
		content.append(" ] ");
		content.append("copyExclude [ ");
		content.append(_copyExclude);
		content.append(" ] ");
		content.append("copyTo [ ");
		content.append(_copyTo);
		content.append(" ] ");
		content.append("copyToFile [ ");
		content.append(_copyToFile);
		content.append(" ] ");

		return content.toString();
	}

	public FileCopyTask copyFile(String copyFile) {
		_copyFile = copyFile;
		return this;
	}

	public FileCopyTask copyDir(String copyDir) {
		_copyDir = copyDir;
		return this;
	}

	public FileCopyTask copyExclude(List<String> copyExclude) {
		_copyExclude = copyExclude;
		return this;
	}

	public FileCopyTask copyInclude(List<String> copyInclude) {
		_copyInclude = copyInclude;
		return this;
	}

	public FileCopyTask copyTo(String copyTo) {
		_copyTo = copyTo;
		return this;
	}

	public FileCopyTask copyToFile(String copyToFile) {
		_copyToFile = copyToFile;
		return this;
	}

}
