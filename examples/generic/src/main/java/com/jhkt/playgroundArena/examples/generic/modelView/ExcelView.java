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

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 * @author Ji Hoon Kim
 */
public class ExcelView extends AbstractExcelView {
    
    public static final String SS_NAME_KEY = "SPREAD_SHEET_NAME";
    public static final String SS_DATA_ENTRY_KEY = "SS_DATA_ENTRY_KEY";
    
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook wb, 
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ssName = String.class.cast( model.get(SS_NAME_KEY) );
        HSSFSheet sheet = ssName == null ? wb.createSheet() : wb.createSheet(ssName);
        HSSFCell cell = null;
        
        @SuppressWarnings(value = "unchecked")
        List<Object> words = (List<Object>) model.get(SS_DATA_ENTRY_KEY);
        for (int i=0; i < words.size(); i++) {
            cell = getCell(sheet, i, 0);
            setText(cell, words.get(i).toString());
        }
        
    }
    
}
