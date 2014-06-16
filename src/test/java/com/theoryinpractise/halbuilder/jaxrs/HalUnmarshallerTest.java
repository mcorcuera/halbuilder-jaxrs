/*
 * Copyright (C) 2014 Mikel Corcuera <mik.corcuera@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.theoryinpractise.halbuilder.jaxrs;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Mikel Corcuera <mik.corcuera@gmail.com>
 */
public class HalUnmarshallerTest {
    
    public HalUnmarshallerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of unmarshal method, of class HalUnmarshaller.
     */
    @Test
    public void testUnmarshal() {
        
        System.out.println("unmarshal");
        
        Resource r1 = new Resource( 124L, new Name("John", "Doe"), Date.valueOf("1991-03-18"));
        Resource r2 = null;
        
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HalMarshaller.marshal(r1, RepresentationFactory.HAL_JSON, baos);
            String representation = baos.toString( "UTF-8");
            
            InputStream is = new ByteArrayInputStream( representation.getBytes(StandardCharsets.UTF_8));
            Object expResult = r1;
            HalContext.registerPropertyBuilder( new NameBuilder());
            r2 = (Resource) HalUnmarshaller.unmarshal(is, Resource.class);
        } catch (Exception ex) {
            Logger.getLogger(HalUnmarshallerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        assertEquals(r1, r2);
    }
    
   
    
}
