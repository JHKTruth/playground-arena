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
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.ejb.HibernateEntityManagerFactory;

import com.jhkt.playgroundArena.db.sql.mysql.beans.Item;
import com.jhkt.playgroundArena.db.sql.mysql.beans.User;

/**
 * @author Ji Hoon Kim
 */
public final class MySQLTaskRunner extends AbstractTaskRunner 
									implements ITaskRunner {
	
	private final static Log _log = LogFactory.getLog(MySQLTaskRunner.class);
	
	private static EntityManagerFactory emFactory;
	
	/*
	 * EntityManager/Session is a single-threaded non-shared object that represents a particular unit of work with the database.
	 */
	private EntityManager eManager;
	private Map<String, String> properties;
	
	private LinkedHashMap<String, Set<String>> initCommands;
	private LinkedHashMap<String, Set<String>> destroyCommands;
	
	public static SessionFactory getSessionFactory() {
	    HibernateEntityManagerFactory hibEmf = HibernateEntityManagerFactory.class.cast( emFactory );
	    return hibEmf.getSessionFactory();
	}
	
	public MySQLTaskRunner() {
		super();
	}
	
	public MySQLTaskRunner(Map<String, String> properties) {
	    super();
	    
	    this.properties = properties;
	}
	
	public Session getSession() {
	    return Session.class.cast( eManager.getDelegate() );
	}
	
	public List<User> getUsers() {
		
		List<User> users = (List<User>) eManager.createNamedQuery("findAllUsers").getResultList();
		_log.info("Got users: " + users);
		return users;
	}
	
	public User getUser(Long userId) {
		return User.class.cast( eManager.getReference(User.class, userId) );
	}
	
	public void createItem(Item item) {
		
		eManager.persist(item);
	}
	
	public void createUser(User user) {
		
		eManager.persist(user);
	}
	
	public void flushEM() {
		
		eManager.flush();
	}
	
	@Override
	public LinkedHashMap<String, Set<String>> getInitCommands() {
    	return initCommands;
    }
	public void setInitCommands(LinkedHashMap<String, Set<String>> initCommands) {
		this.initCommands = initCommands;
	}
    @Override
    public LinkedHashMap<String, Set<String>> getDestroyCommands() {
    	return destroyCommands;
    }
	public void setDestroyCommands(LinkedHashMap<String, Set<String>> destroyCommands) {
		this.destroyCommands = destroyCommands;
	}
	
    @Override
    public void init() {
    	super.init();
    	
        if(emFactory == null) {
        	emFactory = Persistence.createEntityManagerFactory("jpa");
        }
        
        if(properties != null) {
        	eManager = emFactory.createEntityManager(properties);
        }else {
        	eManager = emFactory.createEntityManager();
        }
    }
    
    @Override
    public void destroy() {
    	
    	if(emFactory != null) {
    		emFactory.close();
    	}
    	
        super.destroy();
        
    }
	
}
