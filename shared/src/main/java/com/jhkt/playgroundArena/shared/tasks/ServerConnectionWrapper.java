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

/**
 * @author Ji Hoon Kim
 */
class ServerConnectionWrapper<E> {

	private int _connections;
	private E _actualInstance;
	
	ServerConnectionWrapper(E instance) {
		super();
		
		_actualInstance = instance;
	}
	
	void incrementCount() {
		_connections += 1;
	}
	void decrementCount() {
		_connections -= 1;
	}
	
	boolean allowRemoval() {
		return _connections == 1;
	}
	
	E getActualInstance() {
		return _actualInstance;
	}
	
}
