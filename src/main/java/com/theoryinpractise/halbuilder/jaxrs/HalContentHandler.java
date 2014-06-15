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

import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationException;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;


/**
 *
 * @author Mikel Corcuera <mik.corcuera@gmail.com> 
 */
@Provider
public class HalContentHandler implements MessageBodyWriter, MessageBodyReader<Object> {

    private static final MediaType HAL_JSON_TYPE = new MediaType("application", "hal+json");

    private static final MediaType HAL_XML_TYPE  = new MediaType("application", "hal+xml");
    
    private static final MediaType JSON_TYPE = new MediaType( "application", "json");

    @Override
    public boolean isWriteable(Class aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        
        /*
        * Check if the object to write is a Java Bean annotated with HALRootElement
        */
        return aClass.isAnnotationPresent( HalRootElement.class) 
                && (mediaType.isCompatible(HAL_JSON_TYPE) || mediaType.isCompatible(HAL_XML_TYPE));
    }

    
    @Override
    public long getSize(Object o, Class aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return -1L;
    }

    
    @Override
    public void writeTo(Object o, Class aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap headers, OutputStream outputStream) throws IOException, WebApplicationException {
        
        try {
            if( o != null) {
                HalMarshaller.marshal(o, mediaType, outputStream);
            }else
                throw new Exception( "Error creating HAL Representation of Object " + o);
        } catch( Exception e) {
            throw new  InternalServerErrorException( e);
        }
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] antns, MediaType mediaType) {
        return mediaType.isCompatible(HAL_JSON_TYPE) 
                || mediaType.isCompatible(HAL_XML_TYPE) 
                || mediaType.isCompatible( JSON_TYPE);
    }

    @Override
    public Object readFrom(Class<Object> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException {
         Object o; 
        try {
           o = HalUnmarshaller.unmarshal(in, type);
        }catch( Exception e) {
            throw new InternalServerErrorException( e);
        }
        
        return o;
    }
    
}
