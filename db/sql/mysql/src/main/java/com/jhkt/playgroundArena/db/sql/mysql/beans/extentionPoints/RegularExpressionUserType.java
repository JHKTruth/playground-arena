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
import java.util.Properties;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.type.StringType;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;

/**
 * @author Ji Hoon Kim
 */
public class RegularExpressionUserType 
                        implements EnhancedUserType, ParameterizedType {
    
    private static final StringType STRING_TYPE = new StringType();
    
    private Pattern _pattern;
    
    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }
    
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }
    
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return Serializable.class.cast( value );
    }
    
    @Override
    public boolean equals(Object first, Object second) throws HibernateException {
        if((first == null && second != null) || (first != null && second == null) 
                    || !(first.getClass().equals(second.getClass()))) {
            
            return false;
        }
        return first == second;
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
    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws HibernateException, SQLException {
        String storedRegularExpression = resultSet.getString( names[0] );
        if(!resultSet.wasNull()) {
            _pattern = Pattern.compile(storedRegularExpression);
        }
        return _pattern;
    }
    
    @Override
    public void nullSafeSet(PreparedStatement statement, Object value, int index) throws HibernateException, SQLException {
        if(value == null) {
            statement.setNull(index, STRING_TYPE.sqlType());
        }else {
            statement.setString(index, _pattern.pattern() );
        }
    }
    
    @Override
    public Object replace(Object original, Object owner, Object target) throws HibernateException {
        return original;
    }
    
    @Override
    public Class<Pattern> returnedClass() {
        return Pattern.class;
    }
    
    @Override
    public int[] sqlTypes() {
        return new int[] { STRING_TYPE.sqlType() };
    }
    
    @Override
    public void setParameterValues(Properties parameters) {
        String regularExpression = parameters.getProperty("regularExpression");
        _pattern = Pattern.compile(regularExpression);
    }
    
    @Override
    public Object fromXMLString(String xmlValue) {
        return Pattern.compile(xmlValue);
    }
    
    @Override
    public String objectToSQLString(Object value) {
        return '\'' + Pattern.class.cast( value ).pattern() + '\'';
    }
    
    @Override
    public String toXMLString(Object value) {
        return Pattern.class.cast( value ).pattern();
    }
    
}
