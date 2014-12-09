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
package com.jhkt.playgroundArena.shared.tasks.common;

import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Ji Hoon Kim
 */
public final class TaskQueues {
	
	private static final String DUPLICATE_QUEUE_TASK_ID_PROVIDED = "Duplicate future queue task id has been provided : ";
	private static final int EXECUTOR_SERVICE_SHUT_DOWN_LIMIT = 20;
	
	private final static TaskQueues _instance = new TaskQueues();
	
	private final static Log _log = LogFactory.getLog(TaskQueues.class);

	public enum QUEUE_TASK_ID {
		CHMOD, COMMAND, DELETE, ECHO, FILE_COPY, MKDIR, RENAME, REPLACE_TEXT, UNZIP_ARCHIVE_RELATIVE, UNZIP_ARCHIVE_ABSOLUTE_FI, UNZIP_ARCHIVE_ABSOLUTE_IS;

		public String getQueueTaskId(String queueTaskId) {
			return toString() + ":" + queueTaskId;
		}

	}
	
	private final ExecutorService _queuedService;
	private final ConcurrentMap<String, Future<Void>> _queuedTasks;
	private final Object _lock = new Object();
	private final Queue<AbstractTask> _tasks;

	private TaskQueues() {
		super();
	}

	{
		_queuedService = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors() * 2);
		_queuedTasks = new ConcurrentHashMap<String, Future<Void>>();
		_tasks = new PriorityQueue<AbstractTask>();
	}
	
	public static TaskQueues getInstance() { 
		return _instance;
	}
	
	public void addTask(AbstractTask toAdd) {
		synchronized (_lock) {
			_tasks.add(toAdd);
			execute();
		}
	}

	public void addTasks(Collection<AbstractTask> tasksToAdd) {
		synchronized (_lock) {
			_tasks.addAll(tasksToAdd);
			execute();
		}
	}

	public void execute() {

		synchronized (_lock) {
			for (AbstractTask current : _tasks) {
				current.performTask();
			}
			clearAllTask();
		}
	}

	private void clearAllTask() {
		_tasks.clear();
	}

	/*
	 * Though finalize method is not always invoked, place in the code to clean
	 * up ExecutorService for times when it is called.
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (_queuedService != null) {
			_queuedService.shutdownNow();
		}
		if (_queuedTasks != null) {
			_queuedTasks.clear();
		}
	}

	public void queueFutureTask(final String taskName, final AbstractTask toAdd) {
		final FutureTask<Void> task = new FutureTask<Void>(new Runnable() {
			public void run() {
				toAdd.performTask();
				_queuedTasks.remove(taskName);
				_log.info("Queued taskName has been completed : " + taskName);
			}
		}, null);

		Future<Void> previousTask = _queuedTasks.putIfAbsent(taskName, task);
		if (previousTask != null) {
			throw new RuntimeException(DUPLICATE_QUEUE_TASK_ID_PROVIDED
					+ taskName);
		}
		_queuedService.submit(task);
	}
	
	public void cancelQueuedFutureTask(String taskName) { 
	    Future<Void> task = _queuedTasks.get(taskName);
	    task.cancel(true);
	    _queuedTasks.remove(taskName);
	}

	public boolean isTaskDone(String taskName) {
		Future<Void> task = _queuedTasks.get(taskName);
		return (task != null && task.isDone());
	}

	public void waitForFutureTask(String taskName) {
		Future<Void> task = _queuedTasks.get(taskName);
		if (task != null) {
			try {
				_log.info("Waiting for taskName : " + taskName);
				task.get();
				_log.info("Finished waiting for the taskName : " + taskName);
			} catch (ExecutionException executeExcept) {
				_log.error(
						"Execution exception thrown within waitForFutureTask for "
								+ taskName, executeExcept);
			} catch (InterruptedException interruptedExcept) {
				Thread.currentThread().interrupt();
			} finally {
				task.cancel(true);
			}
		}
	}
	
	public void checkTaskEntrySizeAndShutDown() { 
	    if(_queuedTasks != null && _queuedTasks.size() == 0) { 
	        clearAllFutureTasks();
	    }
	}

	public void clearAllFutureTasks() {
		_queuedService.shutdown();
		try {
			_queuedService.awaitTermination(EXECUTOR_SERVICE_SHUT_DOWN_LIMIT,
					TimeUnit.SECONDS);
		} catch (InterruptedException interruptedException) {
			Thread.currentThread().interrupt();
		} finally {
			if (_queuedTasks != null) {
				_queuedTasks.clear();
			}
		}
	}

}
