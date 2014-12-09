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

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Required;

import com.jhkt.playgroundArena.db.nosql.mongodb.beans.AbstractDocument;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import com.mongodb.Mongo;

/**
 * @author Ji Hoon Kim
 */
public final class MongoDBTaskRunner extends AbstractTaskRunner 
										implements ITaskRunner {
	
	/*
	 * Note that Mongo instance represents a pool of connection to the DB.
	 * In another words have a 1-1 relationship between an instance and a server
	 */
	private static final ConcurrentMap<String, ServerConnectionWrapper<Mongo>> _mongoMaps = new ConcurrentHashMap<String, ServerConnectionWrapper<Mongo>>();
	private static final String DEFAULT_MONGO_HOSTNAME = "localhost";
	private static final int DEFAULT_MONGO_PORT = 27017;
	private static final BasicDBObject ENSURE_INDEX_CONFIG = new BasicDBObject();
	
	static {
	    ENSURE_INDEX_CONFIG.put("background", true);
	}
	
	private ServerConnectionWrapper<Mongo> _instanceWrapper;
	private String _hostNameIdentifier;
	private Map<String, Map<String, String>> _authenticationConfig;
	
	/*
	 * Below variable's format is :
	 * DBName
	 *     Collection to be created
	 *         Indexes to be created
	 * 
	 * Implement this better in the future.
	 */
	private Map<String, Map<String, Set<String>>> _dBCollections;
	private Map<Class<? extends AbstractDocument>, DBCollection> _dbCollectionMaps;
	
	public MongoDBTaskRunner() throws UnknownHostException {
		super();
		
		_hostNameIdentifier = DEFAULT_MONGO_HOSTNAME + DEFAULT_MONGO_PORT;
		
		if((_instanceWrapper = _mongoMaps.get(_hostNameIdentifier)) == null) {
		    _instanceWrapper = new ServerConnectionWrapper<Mongo>(new Mongo());
		    _mongoMaps.put(_hostNameIdentifier, _instanceWrapper);
		}
		
		_instanceWrapper.incrementCount();
	}
	
	public MongoDBTaskRunner(String hostName, int portNumber) throws UnknownHostException {
		super();
		
		_hostNameIdentifier = hostName + portNumber;
		
		if((_instanceWrapper = _mongoMaps.get(_hostNameIdentifier)) == null) {
            _instanceWrapper = new ServerConnectionWrapper<Mongo>(new Mongo(hostName, portNumber));
            _mongoMaps.put(_hostNameIdentifier, _instanceWrapper);
        }
		
		_instanceWrapper.incrementCount();
	}
	
	{
	    _dbCollectionMaps = new HashMap<Class<? extends AbstractDocument>, DBCollection>();
	}
	
	public DB getDatabase(String database) {
		return _instanceWrapper.getActualInstance().getDB(database);
	}
	
	public Map<String, Map<String, String>> getAuthenticationConfig() {
	    return _authenticationConfig;
	}
	public void setAuthenticationConfig(Map<String, Map<String, String>> authenticationConfig) {
	    _authenticationConfig = authenticationConfig;
	}
	
	public Map<String, Map<String, Set<String>>> getDBCollections() {
	    return _dBCollections;
	}
	@Required
	public void setDBCollections(Map<String, Map<String, Set<String>>> dBCollections) {
	    _dBCollections = dBCollections;
	}
	
	private DBCollection getDBCollection(AbstractDocument document) {
	    return _dbCollectionMaps.get(document);
	}
	
	private void processWriteResult(WriteResult wr) {
	    CommandResult result = wr.getLastError();
        if(!result.ok()) {
            throw new RuntimeException(result.getErrorMessage(), result.getException());
        }
	}
	
	/*
	 * One should not access errors as Mongo's philosophy is fire and forget
	 */
	public void save(AbstractDocument document) {
	    DBCollection collection = getDBCollection(document);
	    WriteResult wr = collection.save(document.resolveToBasicDBObject());
	    processWriteResult(wr);
	}
	
	public void update(AbstractDocument updateCriteria, AbstractDocument updateTo) {
	    DBCollection collection = getDBCollection(updateCriteria);
	    WriteResult wr = collection.update(updateCriteria.resolveToBasicDBObject(), updateTo.resolveToBasicDBObject());
	    processWriteResult(wr);
	}
	
	public List<AbstractDocument> query(AbstractDocument queryCriteria) {
	    DBCollection collection = getDBCollection(queryCriteria);
	    DBCursor cursor = collection.find(queryCriteria.resolveToBasicDBObject());
	    List<AbstractDocument> queryResult = new LinkedList<AbstractDocument>();
	    
	    while(cursor.hasNext()) {
	        DBObject result = cursor.next();
	        queryResult.add(AbstractDocument.convertBacktoClassInstance(result));
	    }
	    
	    return queryResult;
	}
	
	public void addDocuments(AbstractDocument... documents) {
	    DBCollection collection = getDBCollection(documents[0]);
	    
	    List<DBObject> dbObjects = new LinkedList<DBObject>();
	    for(AbstractDocument document : documents) {
	        dbObjects.add(document.resolveToBasicDBObject());
	    }
	    
	    WriteResult wr = collection.insert(dbObjects);
	    processWriteResult(wr);
	}
	
	public void removeDocument(AbstractDocument removeCriteria) {
	    DBCollection collection = getDBCollection(removeCriteria);
	    WriteResult wr = collection.remove(removeCriteria.resolveToBasicDBObject());
	    processWriteResult(wr);
	}
	
    @Override
    public void init() {
        
        if(_authenticationConfig != null) {
            /*
             * Just for playing around, usually MongoDB is used w/o security
             */
            for(String dbName : _authenticationConfig.keySet()) {
                
                DB db = getDatabase(dbName);
                Map<String, String> authentications = _authenticationConfig.get(dbName);
                
                for(String userId : authentications.keySet()) {
                    String password = authentications.get(userId);
                    db.authenticate(userId, password.toCharArray());
                }
            }
        }
        
        if(_dBCollections != null) {
            for(String dbName : _dBCollections.keySet()) {
                
                //Map<String, Map<String, Set<String>>>
                DB db = getDatabase(dbName);
                Map<String, Set<String>> collectionIndexes = _dBCollections.get(dbName);
                for(String collection : collectionIndexes.keySet()) {
                    
                    try{
                        Class<? extends AbstractDocument> loadedClass = Class.forName(collection).asSubclass(AbstractDocument.class);
                        DBCollection dbCollection = null;
                        if(!db.collectionExists(collection)){
                            /*
                             * Won't allow configuration for now, since rather needless [i.e. capped, size, and etcetera]
                             */
                            dbCollection = db.createCollection(collection, null);
                            
                        }else{
                            dbCollection = db.getCollection(collection);
                        }
                        
                        _dbCollectionMaps.put(loadedClass, dbCollection);
                        
                        for(String index : collectionIndexes.get(collection)) {
                            BasicDBObject dbIndex = new BasicDBObject();
                            dbIndex.put(index, 1);
                            dbCollection.ensureIndex(dbIndex, ENSURE_INDEX_CONFIG);
                        }
                    }catch(ClassNotFoundException cnfe) {
                        throw new RuntimeException("Class Not found for: " + collection, cnfe);
                    }
                }
            }
        }
        
    }
    
    @Override
    public void destroy() {
        
        if(_instanceWrapper != null) {
            if(_instanceWrapper.allowRemoval()) {
                _mongoMaps.remove(_hostNameIdentifier);
                for(String dbName : _instanceWrapper.getActualInstance().getDatabaseNames()){
                    _instanceWrapper.getActualInstance().dropDatabase(dbName);
                }
                _instanceWrapper.getActualInstance().close();
            }else {
                _instanceWrapper.decrementCount();
            }
        }
        
    }
    
}
