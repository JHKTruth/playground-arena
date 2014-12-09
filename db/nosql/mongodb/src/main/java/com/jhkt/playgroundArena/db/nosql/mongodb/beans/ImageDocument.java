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

import java.sql.Blob;

import com.jhkt.playgroundArena.db.nosql.mongodb.annotations.IDocumentKeyValue;
import com.jhkt.playgroundArena.shared.utils.JPAConstants;

/**
 * @author Ji Hoon Kim
 */
public final class ImageDocument extends AbstractDocument {
    
    private String _name;
    private Integer _sizeX;
    private Integer _sizeY;
    private Blob _content;
    
    ImageDocument() {
        super();
    }
    
    @IDocumentKeyValue
    public String getName() {
        return _name;
    }
    public void setName(String name) {
        _name = name;
    }
    
    @IDocumentKeyValue
    public Integer getSizeX() {
        return _sizeX;
    }
    public void setSizeX(Integer sizeX) {
        _sizeX = sizeX;
    }
    
    @IDocumentKeyValue
    public Integer getSizeY() {
        return _sizeY;
    }
    public void setSizeY(Integer sizeY) {
        _sizeY = sizeY;
    }
    
    @IDocumentKeyValue
    public Blob getContent() {
        return _content;
    }
    public void setContent(Blob content) {
        _content = content;
    }
    
    @Override
    public int hashCode() {
        int hashCodeVal = JPAConstants.HASH_CODE_INIT_VALUE;
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _name.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _sizeX.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _sizeY.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _content.hashCode();
        return hashCodeVal;
    }
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof ImageDocument)) {
            return false;
        }
        
        ImageDocument idInstance = ImageDocument.class.cast( instance );
        return idInstance._name.equals(_name) && idInstance._sizeX.equals(_sizeX) && idInstance._sizeY.equals(_sizeY) && 
                idInstance._content.equals(_content);
    }
    @Override
    public String toString() {
    	StringBuilder content = new StringBuilder();
    	content.append("ImageDocument{ name: ");
    	content.append(_name);
    	content.append(", sizeX: ");
    	content.append(_sizeX);
    	content.append(", sizeY: ");
    	content.append(_sizeY);
    	content.append(", content: ");
    	content.append(_content);
    	content.append(" }");
    	return content.toString();
    }
    
}
