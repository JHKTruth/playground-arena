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
package com.jhkt.playgroundArena.db.nosql.mongodb.beans;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jhkt.playgroundArena.db.nosql.mongodb.annotations.IDocumentKeyValue;
import com.jhkt.playgroundArena.shared.utils.JPAConstants;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

/**
 * @author Ji Hoon Kim
 */
public abstract class AbstractDocument {
    
    private final static Log _log = LogFactory.getLog(AbstractDocument.class);
    
    private static Method RESOLVE_TO_BASIC_DB_OBJECT_METHOD;
    
    static {
        try{
            RESOLVE_TO_BASIC_DB_OBJECT_METHOD = AbstractDocument.class.getMethod("resolveToBasicDBObject", JPAConstants.EMPTY_CLASS_ARGUMENT_LIST);
        }catch(Exception e){
            
        }
    }
    
    AbstractDocument() {
        super();
    }
    
    private final static Object convertBacktoClassInstanceHelper(DBObject dBObject) {
    	
    	Object returnValue = null;
    	
    	try{
            String className = dBObject.get(JPAConstants.CONVERTER_CLASS.CLASS.name()).toString();
            Class<?> classCheck = Class.forName(className);
            
            if(AbstractDocument.class.isAssignableFrom(classCheck)) {
            	Class<? extends AbstractDocument> classToConvertTo = classCheck.asSubclass(AbstractDocument.class);
            	AbstractDocument dInstance = classToConvertTo.newInstance();
            	
                for(String key : dBObject.keySet()){
                    Object value = dBObject.get(key);
                    
                    char[] propertyChars = key.toCharArray();
                    String methodMain = String.valueOf(propertyChars[0]).toUpperCase() + key.substring(1);
                    String methodName = "set" + methodMain;
                    String getMethodName = "get" + methodMain;
                    
                    if(key.equals(JPAConstants.CONVERTER_CLASS.CLASS.name())) {
                        continue;
                    }
                    
                    if(value instanceof BasicDBObject) {
                        value = convertBacktoClassInstanceHelper(BasicDBObject.class.cast( value ));
                    }
                    
                    try{
                    	Method getMethod = classToConvertTo.getMethod(getMethodName);
                    	Class<?> getReturnType = getMethod.getReturnType();
                        Method method = classToConvertTo.getMethod(methodName, getReturnType);
                        
                        if(getMethod.isAnnotationPresent(IDocumentKeyValue.class)){
                            method.invoke(dInstance, value);
                        }
                    }catch(NoSuchMethodException nsMe) {
                        _log.warn("Within convertBacktoClassInstance, following method was not found " + methodName, nsMe);
                    }
                    
                }
                
                returnValue = dInstance;
                
            }else if(Enum.class.isAssignableFrom(classCheck)) {
            	
            	List<?> constants = Arrays.asList(classCheck.getEnumConstants());
            	String name = String.class.cast( dBObject.get(JPAConstants.CONVERTER_CLASS.CONTENT.name()) );
            	for(Object constant : constants) {
            		if(constant.toString().equals(name)) {
            			returnValue = constant;
            		}
            	}
            	
            }else if(Collection.class.isAssignableFrom(classCheck)) {
            	
            	@SuppressWarnings("unchecked")
            	Class<? extends Collection<? super Object>> classToConvertTo = (Class<? extends Collection<? super Object>>) classCheck;
            	Collection<? super Object> cInstance = classToConvertTo.newInstance();
            	
            	BasicDBList bDBList = (BasicDBList) dBObject.get(JPAConstants.CONVERTER_CLASS.CONTENT.name());
            	cInstance.addAll(bDBList);
            	
            	returnValue = cInstance;
            	
            }else if(Map.class.isAssignableFrom(classCheck)) {
            	
            	@SuppressWarnings("unchecked")
            	Class<? extends Map<String, ? super Object>> classToConvertTo = (Class<? extends Map<String, ? super Object>>) classCheck;
            	Map<String, ? super Object> mInstance = classToConvertTo.newInstance();
            	
            	BasicDBObject mapObject = (BasicDBObject) dBObject.get(JPAConstants.CONVERTER_CLASS.CONTENT.name());
            	mInstance.putAll(mapObject);
            	
            	returnValue = mInstance;
            	
            }
            
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    	
    	return returnValue;
    }
    
    public final static AbstractDocument convertBacktoClassInstance(DBObject dBObject) {
        return AbstractDocument.class.cast( convertBacktoClassInstanceHelper(dBObject) );
    }
    
    public final BasicDBObject resolveToBasicDBObject() {
        
        BasicDBObject bDBObject = new BasicDBObject();
        try{
            Class<? extends AbstractDocument> currClass = getClass();
            while(currClass != null) {
                
                for(Method method : currClass.getMethods()) {
                    IDocumentKeyValue dkv = method.getAnnotation(IDocumentKeyValue.class);
                    String mName = method.getName();
                    if(dkv != null && mName.startsWith(JPAConstants.GETTER_PREFIX)) {
                        
                        try{
                            Object returnValue = method.invoke(this, JPAConstants.EMPTY_OBJECT_ARGUMENT_LIST);
                            char[] propertyChars = mName.substring(3).toCharArray();
                            String property = String.valueOf(propertyChars[0]).toLowerCase() + String.valueOf(propertyChars, 1, propertyChars.length - 1);
                            
                            if(returnValue == null) {
                                continue;
                            }
                            
                            if(returnValue instanceof AbstractDocument) {
                                
                                Object subReturnValue = RESOLVE_TO_BASIC_DB_OBJECT_METHOD.invoke(returnValue, JPAConstants.EMPTY_OBJECT_ARGUMENT_LIST);
                                bDBObject.put(property, subReturnValue);
                                
                            }else if(returnValue instanceof Enum){
                                
                            	Enum<?> enumClass = Enum.class.cast( returnValue );
                            	BasicDBObject enumObject = new BasicDBObject();
                            	
                            	enumObject.put(JPAConstants.CONVERTER_CLASS.CLASS.name(), enumClass.getClass().getName());
                            	enumObject.put(JPAConstants.CONVERTER_CLASS.CONTENT.name(), enumClass.name());
                            	
                                bDBObject.put(property, enumObject);
                                
                            }else if(returnValue instanceof Collection) {
                            	
                                Collection<?> collectionContent = (Collection<?>) returnValue;
                                BasicDBObject collectionObject = new BasicDBObject();
                                collectionObject.put(JPAConstants.CONVERTER_CLASS.CLASS.name(), collectionContent.getClass().getName());
                                
                                BasicDBList bDBList = new BasicDBList();
                                if(collectionContent.iterator().next() instanceof AbstractDocument){
                                    for(Object content : collectionContent) {
                                        if(content instanceof AbstractDocument) {
                                            Object subReturnValue = RESOLVE_TO_BASIC_DB_OBJECT_METHOD.invoke(returnValue, JPAConstants.EMPTY_OBJECT_ARGUMENT_LIST);
                                            bDBList.add(subReturnValue);
                                        }
                                    }
                                }else {
                                    bDBList.addAll(collectionContent);
                                }
                                
                                collectionObject.put(JPAConstants.CONVERTER_CLASS.CONTENT.name(), bDBList);
                                bDBObject.put(property, collectionObject);
                                
                            }else if(returnValue instanceof Map) {
                                
                                Map<?, ?> mapContent = (Map<?, ?>) returnValue;
                                BasicDBObject mapObject = new BasicDBObject();
                                mapObject.put(JPAConstants.CONVERTER_CLASS.CLASS.name(), mapContent.getClass().getName());
                                
                                Set<?> keys = mapContent.keySet();
                                if(keys.iterator().next() instanceof AbstractDocument) {
                                    
                                    Map<Object, Object> convertedMap = new HashMap<Object, Object>();
                                    for(Object key : keys) {
                                        Object value = mapContent.get(key);
                                        Object subReturnValue = RESOLVE_TO_BASIC_DB_OBJECT_METHOD.invoke(value, JPAConstants.EMPTY_OBJECT_ARGUMENT_LIST);
                                        
                                        convertedMap.put(key, subReturnValue);
                                    }
                                    
                                    mapContent = convertedMap;
                                }
                                
                                mapObject.put(JPAConstants.CONVERTER_CLASS.CONTENT.name(), mapContent);
                                bDBObject.put(property, mapObject);
                                
                            }else {
                                bDBObject.put(property, returnValue);
                            }
                            
                        }catch(Exception e) {
                            
                        }
                        
                    }
                }
                
                currClass = currClass.getSuperclass().asSubclass(AbstractDocument.class);
            }
            
            
        }catch(ClassCastException castException) {
            
        }
        
        bDBObject.put(JPAConstants.CONVERTER_CLASS.CLASS.name(), getClass().getName());
        _log.info("BdBObject " + bDBObject);
        return bDBObject;
    }
    
}
