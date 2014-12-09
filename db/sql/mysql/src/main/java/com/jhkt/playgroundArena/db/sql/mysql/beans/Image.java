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
package com.jhkt.playgroundArena.db.sql.mysql.beans;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Parent;

import com.jhkt.playgroundArena.shared.utils.JPAConstants;

/**
 * @author Ji Hoon Kim
 */
@Embeddable
@MappedSuperclass
public class Image implements Serializable {
    
    private static final long serialVersionUID = 2415866795285414726L;
    
	private Item item;
    private String fileName;
    
    private Integer sizeX;
    private Integer sizeY;
    private Blob content;
    
    @Parent
    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
        this.item = item;
    }
    
    @Column(name="FILE_NAME",
            length=100
    )
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @Column(name="SIZE_X")
    public Integer getSizeX() {
        return sizeX;
    }
    public void setSizeX(Integer sizeX) {
        this.sizeX = sizeX;
    }
    
    @Column(name="SIZE_Y")
    public Integer getSizeY() {
        return sizeY;
    }
    public void setSizeY(Integer sizeY) {
        this.sizeY = sizeY;
    }
    
    @Lob
    @Column(name="CONTENT",
            length=65536
    )
    public Blob getContent() {
        return content;
    }
    public void setContent(Blob content) {
        this.content = content;
    }
    
    @Override
    public int hashCode() {
        int hashCodeVal = JPAConstants.HASH_CODE_INIT_VALUE;
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + fileName.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + sizeX.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + sizeY.hashCode();
        return hashCodeVal;
    }
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof Image)) {
            return false;
        }
        
        Image image = Image.class.cast( instance );
        return image.fileName.equals(fileName) && image.sizeX.equals(sizeX) && image.sizeY.equals(sizeY);
    }
    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append("Image{ item: ");
        content.append(item);
        content.append(", fileName: ");
        content.append(fileName);
        content.append(", sizeX: ");
        content.append(sizeX);
        content.append(", sizeY: ");
        content.append(sizeY);
        content.append(", content: ");
        content.append(content);
        content.append(" }");
        
        return content.toString();
    }
    
}
