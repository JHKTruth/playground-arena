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

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jhkt.playgroundArena.shared.utils.JPAConstants;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * Once again this is to just play around, since it's unreasonable to use these tools as the code is my code and I do 
 * not need to manipulate bytecode and etcetera [making the code more lengthy but wish to play around with the projects 
 * as technically if this was desired would have been better to use AspectJ]
 * 
 * @author Ji Hoon Kim
 */
public final class MongoDBBeanFactory {
	
	private final static Log _log = LogFactory.getLog(MongoDBBeanFactory.class);
	
	private static final String BEAN_PACKAGE = MongoDBBeanFactory.class.getPackage().getName();
	private static final MongoDBBeanFactory _instance = new MongoDBBeanFactory();
	
	/*
	 * Same reasoning as below and moreover for documentation
	 */
	private static class FieldHolder {
		
		static Method IS_DIRTY_METHOD;
		
		static {
			try {
				IS_DIRTY_METHOD = AbstractDocument.class.getMethod("isDirty", JPAConstants.EMPTY_CLASS_ARGUMENT_LIST);
			} catch (SecurityException se) {
				
			} catch (NoSuchMethodException nsme) {
				
			}
		}
	}
	
	private enum CLASS_POOL_TYPES {
		
		/*
		 * Can't use .class.getSimpleName() for _className as it gets loaded and will need to be swapped out
		 */
		ADDRESS("AddressDocument"), BANK_ACCOUNT("BankAccountDocument"),
		CATEGORY("CategoryDocument"), CREDIT_CARD("CreditCardDocument"),
		IMAGE("ImageDocument"), ITEM("ItemDocument"), RECEIPT("ReceiptDocument"), 
		REVIEW("ReviewDocument"), USER("UserDocument");
		
		private static final String SUPER_CLASS_NAME = AbstractState.class.getSimpleName();
		
		private String _className;
		private SoftReference<Class<?>> _classReference;
		
		CLASS_POOL_TYPES(String className) {
			
			_className = className;
		}
		
		private Class<?> getModifiedClass() {
			
			Class<?> classInstance = null;
			
			try {
				
				synchronized(this) {
					if(_classReference == null || (classInstance = _classReference.get()) == null) {
						
						/*
						 * From the implementation viewpoint, ClassPool is a hash table of CtClass objects, which uses the class names as keys. 
						 * get() in ClassPool searches this hash table to find a CtClass object associated with the specified key. If such a 
						 * CtClass object is not found, get() reads a class file to construct a new CtClass object, which is recorded in the 
						 * hash table and then returned as the resulting value of get(). 
						 */
						CtClass cc = _instance.classPool.get(BEAN_PACKAGE + "." + _className);
						CtClass superClass = cc;
						while(!superClass.getSuperclass().getName().equals(Object.class.getName())){
							superClass = superClass.getSuperclass();
						}
						
						superClass.setSuperclass(_instance.classPool.get(BEAN_PACKAGE + "." + SUPER_CLASS_NAME));
						superClass.toClass();
						
						for(CtMethod method : cc.getDeclaredMethods()) {
							if(method.getName().startsWith("set") &&  
									method.getReturnType().getSimpleName().equals(void.class.getSimpleName())) {
								method.insertAfter("{ setDirty(true); }");
							}
						}
						
						/*
						 * If ClassPool.doPruning is set to true, then Javassist prunes the data structure contained in a CtClass object 
						 * when Javassist freezes that object. To reduce memory consumption, pruning discards unnecessary attributes 
						 * (attribute_info structures) in that object. For example, Code_attribute structures (method bodies) are discarded. 
						 * Thus, after a CtClass object is pruned, the bytecode of a method is not accessible except method names, signatures, 
						 * and annotations. The pruned CtClass object cannot be defrost again. The default value of ClassPool.doPruning is false. 
						 */
						cc.stopPruning(true);
						_classReference = new SoftReference<Class<?>>(cc.toClass());
						classInstance = _classReference.get();
					}
				}
				
			}catch(NotFoundException nfe) {
				nfe.printStackTrace();
				_log.error("NotFoundException for: " + _className, nfe);
			}catch(CannotCompileException cce) {
				cce.printStackTrace();
				_log.error("CannotCompileException for: " + _className, cce);
			}
			
			return classInstance;
		}
	};
	
	private final ClassPool classPool;
	
	private MongoDBBeanFactory() {
		super();
		
		classPool = ClassPool.getDefault();
		classPool.insertClassPath(new ClassClassPath(getClass()));
	}
	
	public static boolean isDirty(Object instance) {
		boolean dirty = false;
		
		try {
			dirty = Boolean.class.cast( FieldHolder.IS_DIRTY_METHOD.invoke(instance, JPAConstants.EMPTY_OBJECT_ARGUMENT_LIST) );
		} catch (IllegalArgumentException iae) {
			_log.error("IllegalArgumentException for " + instance, iae);
		} catch (IllegalAccessException iae2) {
			_log.error("IllegalAccessException for " + instance, iae2);
		} catch (InvocationTargetException ite) {
			_log.error("InvocationTargetException for " + instance, ite);
		}
		
		return dirty;
	}
	
	public static MongoDBBeanFactory getInstance() {
		return _instance;
	}
	
	public AddressDocument createAddress() {
		AddressDocument address = null;
		try {
			address = CLASS_POOL_TYPES.ADDRESS.getModifiedClass().asSubclass(AddressDocument.class).newInstance();
		} catch (InstantiationException ie) {
			
		} catch (IllegalAccessException ie2) {
			
		}
		return address;
	}
	
	public BankAccountDocument createBankAccount() {
		BankAccountDocument bankAccount = null;
		try {
			bankAccount = CLASS_POOL_TYPES.BANK_ACCOUNT.getModifiedClass().asSubclass(BankAccountDocument.class).newInstance();
		} catch (InstantiationException ie) {
			
		} catch (IllegalAccessException ie2) {
			
		}
		return bankAccount;
	}
	
	public CategoryDocument createCategory() {
		CategoryDocument category = null;
		try {
			category = CLASS_POOL_TYPES.CATEGORY.getModifiedClass().asSubclass(CategoryDocument.class).newInstance();
		} catch (InstantiationException ie) {
			
		} catch (IllegalAccessException ie2) {
			
		}
		return category;
	}
	
	public CreditCardDocument createCreditCard() {
		CreditCardDocument creditCard = null;
		try {
			creditCard = CLASS_POOL_TYPES.CREDIT_CARD.getModifiedClass().asSubclass(CreditCardDocument.class).newInstance();
		} catch (InstantiationException ie) {
			
		} catch (IllegalAccessException ie2) {
			
		}
		return creditCard;
	}
	
	public ImageDocument createImage() {
		ImageDocument image = null;
		try {
			image = CLASS_POOL_TYPES.IMAGE.getModifiedClass().asSubclass(ImageDocument.class).newInstance();
		} catch (InstantiationException ie) {
			
		} catch (IllegalAccessException ie2) {
			
		}
		return image;
	}
	
	public ItemDocument createItem() {
		ItemDocument item = null;
		try {
			item = CLASS_POOL_TYPES.ITEM.getModifiedClass().asSubclass(ItemDocument.class).newInstance();
		} catch (InstantiationException ie) {
			
		} catch (IllegalAccessException ie2) {
			
		}
		return item;
	}
	
	public ReceiptDocument createReceipt() {
		ReceiptDocument receipt = null;
		try {
			receipt = CLASS_POOL_TYPES.RECEIPT.getModifiedClass().asSubclass(ReceiptDocument.class).newInstance();
		} catch (InstantiationException ie) {
			
		} catch (IllegalAccessException ie2) {
			
		}
		return receipt;
	}
	
	public ReviewDocument createReview() {
		ReviewDocument review = null;
		try {
			review = CLASS_POOL_TYPES.REVIEW.getModifiedClass().asSubclass(ReviewDocument.class).newInstance();
		} catch (InstantiationException ie) {
			
		} catch (IllegalAccessException ie2) {
			
		}
		return review;
	}
	
	public UserDocument createUser() {
		UserDocument user = null;
		try {
			user = CLASS_POOL_TYPES.USER.getModifiedClass().asSubclass(UserDocument.class).newInstance();
		} catch (InstantiationException ie) {
			
		} catch (IllegalAccessException ie2) {
			
		}
		return user;
	}
	
}
