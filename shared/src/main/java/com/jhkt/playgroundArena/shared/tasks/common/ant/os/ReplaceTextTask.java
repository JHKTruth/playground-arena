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
import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Replace;
import org.apache.tools.ant.taskdefs.Replace.NestedString;
import org.apache.tools.ant.taskdefs.Replace.Replacefilter;
import org.apache.tools.ant.taskdefs.optional.ReplaceRegExp;

import com.jhkt.playgroundArena.shared.tasks.common.ant.AbstractAntBaseTask;

/**
 * @author Ji Hoon Kim
 */
public final class ReplaceTextTask extends AbstractAntBaseTask {

	// make the below reg exp better later
	public static final String CLEAN_REG_EXP_MATCH = "\\s{2,}";
	public static final String CLEAN_REG_EXP_REPLACE_WITH = System
			.getProperty("line.separator");

	private static final String REPLACE_MULTI_LINE_TARGET = "replace_multi_line";
	private static final String REPLACE_TEXT_TARGET = "replace_text";
	private static final String REPLACE_REG_EXP_TARGET = "replace_reg_exp";

	private final Target _replaceMultiLineTarget;
	private final Target _replaceTextTarget;
	private final Target _replaceRegExpTarget;

	private final Replace _replaceMultiLineTask;
	private final Replace _replaceTextTask;
	private final ReplaceRegExp _replaceRegExpTask;

	private final Map<String, String> _replaceMap;

	private boolean _multiLineReplace;
	private boolean _replaceText;
	private boolean _replaceRegExp;

	private String _file;
	private String _regMatch;
	private String _regReplace;
	private String _flags;

	public ReplaceTextTask() {
		super();
	}

	public ReplaceTextTask(String file) {
		_file = file;
	}

	{
		// ReplaceMultiLineTarget
		_replaceMultiLineTarget = new Target();
		_replaceMultiLineTarget.setName(REPLACE_MULTI_LINE_TARGET);
		_replaceMultiLineTarget.setProject(_taskProject);
		_taskProject.addTarget(_replaceMultiLineTarget);

		_replaceMultiLineTask = new Replace();
		_replaceMultiLineTask.setOwningTarget(_replaceMultiLineTarget);
		_replaceMultiLineTask.setProject(_taskProject);

		_replaceMultiLineTarget.addTask(_replaceMultiLineTask);

		// ReplaceTextTarget
		_replaceTextTarget = new Target();
		_replaceTextTarget.setName(REPLACE_TEXT_TARGET);
		_replaceTextTarget.setProject(_taskProject);
		_taskProject.addTarget(_replaceTextTarget);

		_replaceTextTask = new Replace();
		_replaceTextTask.setOwningTarget(_replaceTextTarget);
		_replaceTextTask.setProject(_taskProject);

		_replaceTextTarget.addTask(_replaceTextTask);

		// ReplaceRegExpTarget
		_replaceRegExpTarget = new Target();
		_replaceRegExpTarget.setName(REPLACE_REG_EXP_TARGET);
		_replaceRegExpTarget.setProject(_taskProject);
		_taskProject.addTarget(_replaceRegExpTarget);

		_replaceRegExpTask = new ReplaceRegExp();
		_replaceRegExpTask.setOwningTarget(_replaceRegExpTarget);
		_replaceRegExpTask.setProject(_taskProject);

		_replaceRegExpTarget.addTask(_replaceRegExpTask);

		_replaceMap = new HashMap<String, String>();
		_flags = "gis";
		_multiLineReplace = true;
	}

	public void addTokenValue(String token, String value) {
		_replaceMap.put(token, value);
	}

	protected void performTask() {

		try {

			String targetToExecute = "";

			if (_multiLineReplace) {

				_replaceMultiLineTask.setFile(new File(_file));

				for (String tokenVal : _replaceMap.keySet()) {

					Replacefilter replaceFilt = _replaceMultiLineTask
							.createReplacefilter();
					replaceFilt.setToken(tokenVal);
					replaceFilt.setValue((String) _replaceMap.get(tokenVal));
				}
				_replaceMultiLineTask.maybeConfigure();
				targetToExecute = REPLACE_MULTI_LINE_TARGET;
			} else if (_replaceText) {

				_replaceTextTask.setFile(new File(_file));

				for (String tokenVal : _replaceMap.keySet()) {

					NestedString nestedToken = _replaceTextTask
							.createReplaceToken();
					nestedToken.addText(tokenVal);
					NestedString nestedValue = _replaceTextTask
							.createReplaceValue();
					nestedValue.addText(_replaceMap.get(tokenVal));
				}

				_replaceTextTask.maybeConfigure();
				targetToExecute = REPLACE_TEXT_TARGET;
			} else if (_replaceRegExp) {

				_replaceRegExpTask.setFile(new File(_file));
				_replaceRegExpTask.setMatch(_regMatch);
				_replaceRegExpTask.setReplace(_regReplace);
				_replaceRegExpTask.setFlags(_flags);

				_replaceRegExpTask.maybeConfigure();
				targetToExecute = REPLACE_REG_EXP_TARGET;
			}

			buildProject(targetToExecute);

		} catch (BuildException buildException) {
			_taskProject.fireBuildFinished(buildException);
			StringBuilder errorMessage = new StringBuilder();
			errorMessage
					.append("Error in ReplaceText's performTask with following fields \n");
			errorMessage.append(toString());
			throw new RuntimeException(errorMessage.toString(), buildException);
		}

	}

	public String toString() {
		StringBuilder content = new StringBuilder();
		content.append("multiLineReplace [ ");
		content.append(_multiLineReplace);
		content.append(" ] ");
		content.append("replaceText [ ");
		content.append(_replaceText);
		content.append(" ] ");
		content.append("replaceRegExp [ ");
		content.append(_replaceRegExp);
		content.append(" ] ");
		content.append("file [ ");
		content.append(_file);
		content.append(" ] ");
		content.append("regMatch [ ");
		content.append(_regMatch);
		content.append(" ] ");
		content.append("regReplace [ ");
		content.append(_regReplace);
		content.append(" ] ");
		content.append("flags [ ");
		content.append(_flags);
		content.append(" ] ");
		content.append("replaceMap [");
		for (String currVal : _replaceMap.keySet()) {
			content.append(" ");
			content.append("key/value");
			content.append(currVal);
			content.append("/");
			content.append(_replaceMap.get(currVal));
		}
		content.append(" ] ");
		return content.toString();
	}

	public ReplaceTextTask file(String file) {
		_file = file;
		return this;
	}

	public ReplaceTextTask multiLineReplace(boolean multiLineReplace) {
		_multiLineReplace = multiLineReplace;
		_replaceText = false;
		_replaceRegExp = false;
		return this;
	}

	public ReplaceTextTask replaceRegExp(boolean replaceRegExp) {
		_replaceRegExp = replaceRegExp;
		_multiLineReplace = false;
		_replaceText = false;
		return this;
	}

	public ReplaceTextTask replaceText(boolean replaceText) {
		_replaceText = replaceText;
		_multiLineReplace = false;
		_replaceRegExp = false;
		return this;
	}

	public ReplaceTextTask flags(String flags) {
		_flags = flags;
		return this;
	}

	public ReplaceTextTask regMatch(String regMatch) {
		_regMatch = regMatch;
		return this;
	}

	public ReplaceTextTask regReplace(String regReplace) {
		_regReplace = regReplace;
		return this;
	}

}
