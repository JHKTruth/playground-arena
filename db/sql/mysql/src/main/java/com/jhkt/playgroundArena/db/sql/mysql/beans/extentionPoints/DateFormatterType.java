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
package com.jhkt.playgroundArena.db.sql.mysql.beans.extentionPoints;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import com.jhkt.playgroundArena.db.sql.mysql.beans.extentionPoints.beans.FormattedDate;

/**
 * Just for documentation
 * 
 * @author Ji Hoon Kim
 */
public class DateFormatterType implements CompositeUserType {
    
    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd;HH:mm:ss z");
    private static final StringType STRING_TYPE = new StringType();
    
    @Override
    public Object assemble(Serializable cached, SessionImplementor session, Object owner) throws HibernateException {
        return cached;
    }
    
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }
    
    @Override
    public Serializable disassemble(Object value, SessionImplementor session) throws HibernateException {
        return Serializable.class.cast( value );
    }
    
    @Override
    public boolean equals(Object first, Object second) throws HibernateException {
        if((first == null && second != null) || (first != null && second == null) 
                || !(first.getClass().equals(second.getClass()))) {
            
            return false;
        }
        return first.equals( second );
    }
    
    @Override
    public String[] getPropertyNames() {
        return new String[] { "formattedDate" };
    }
    
    @Override
    public Type[] getPropertyTypes() {
        return new Type[] { STRING_TYPE };
    }
    
    @Override
    public Object getPropertyValue(Object component, int property) throws HibernateException {
        if(component == null) {
            return null;
        }
        
        FormattedDate formattedDate = FormattedDate.class.cast( component );
        return formattedDate.getFormattedDate();
    }
    
    @Override
    public int hashCode(Object instance) throws HibernateException {
        return instance.hashCode();
    }
    
    @Override
    public boolean isMutable() {
        return false;
    }
    
    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        String stringFormat = String.class.cast( STRING_TYPE.get(resultSet, names[0]) );
        Date parsedDate = null;
        if(stringFormat.length() > 0){
            try{
                parsedDate = DATE_FORMATTER.parse(stringFormat);
            }catch(ParseException pe){
                
            }
        }
        return new FormattedDate(stringFormat, parsedDate);
    }
    
    @Override
    public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if(value == null) {
            STRING_TYPE.set(statement, null, index);
        }else {
            FormattedDate formattedDate = FormattedDate.class.cast( value );
            STRING_TYPE.set(statement, formattedDate.getFormattedDate(), index);
        }
    }
    
    @Override
    public Object replace(Object original, Object owner, SessionImplementor session, Object target) throws HibernateException {
        return original;
    }
    
    @Override
    public Class<FormattedDate> returnedClass() {
        return FormattedDate.class;
    }
    
    @Override
    public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
        if(component == null) {
            return;
        }
        
        String stringFormat = String.class.cast( value );
        Date parsedDate = null;
        try{
            parsedDate = DATE_FORMATTER.parse(stringFormat);
        }catch(ParseException pe){
            
        }
        
        FormattedDate formattedDate = FormattedDate.class.cast( component );
        formattedDate.setFormattedDate(stringFormat, parsedDate);
    }
    
}
