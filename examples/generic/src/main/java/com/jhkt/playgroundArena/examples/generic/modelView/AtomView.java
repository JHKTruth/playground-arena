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
package com.jhkt.playgroundArena.examples.generic.modelView;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;

import com.sun.syndication.feed.atom.Content;
import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.feed.atom.Feed;

/**
 * Check out the example at: http://blog.springsource.com/2009/03/16/adding-an-atom-view-to-an-application-using-springs-rest-support/
 * 
 * @author Ji Hoon Kim
 */
public class AtomView extends AbstractAtomFeedView {
    
    public static final String ATOM_DATA_ENTRY_KEY = "ATOM_DATA_ENTRY_KEY";
    
    public enum FEED_KEYS {
        ID, TITLE, UPDATED, SUMMARY
    }
    
    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Feed feed, HttpServletRequest request) {
        
        for(FEED_KEYS key : FEED_KEYS.values()) {
            Object value = model.get(key.name());
            if(value != null) {
                switch(key) {
                case ID: feed.setId(value.toString()); break;
                case TITLE: feed.setTitle(value.toString()); break;
                case UPDATED: feed.setUpdated(Date.class.cast( value )); break;
                }
            }
        }
        
    }
    
    @Override
    protected List<Entry> buildFeedEntries(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        @SuppressWarnings("unchecked")
        Collection<Map<String, Object>> dataEntries = (Collection<Map<String, Object>>) model.get(ATOM_DATA_ENTRY_KEY);
        List<Entry> entries = new LinkedList<Entry>();
        
        for(Map<String, Object> content : dataEntries) {
            Entry entry = new Entry();
            
            for(FEED_KEYS key : FEED_KEYS.values()) {
                Object value = content.get(key.name());
                if(value != null) {
                    Content summary = new Content();
                    
                    switch(key) {
                    case ID: entry.setId(value.toString()); break;
                    case TITLE: entry.setTitle(value.toString()); break;
                    case UPDATED: entry.setUpdated(Date.class.cast( value )); break;
                    case SUMMARY: summary.setValue(value.toString()); break;
                    }
                }
            }
            
            entries.add(entry);
        }
        
        return entries;
    }
    
}
