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

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;


/**
 * @author Ji Hoon Kim
 */
public class PDFView extends AbstractPdfView {
    
    public static final String PDF_DATA_ENTRY_KEY = "PDF_DATA_ENTRY_KEY";
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, 
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        @SuppressWarnings(value = "unchecked")
        List<Object> data = (List<Object>) model.get(PDF_DATA_ENTRY_KEY);
        
        for(Object dataEntry : data) {
            doc.add( new Paragraph(dataEntry.toString()));
        }
        
    }
    
}
