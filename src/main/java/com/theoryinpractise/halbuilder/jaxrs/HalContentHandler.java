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

import com.theoryinpractise.halbuilder.jaxrs.builders.BuilderException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;


/**
 * This class include the functionality of marshalling and unmarshalling objects
 * in the HAL format in the JAX-RS application. For that, it implements 
 * MessageBodyWritter and MessageBody Reader
 * @author Mikel Corcuera <mik.corcuera@gmail.com> 
 */
@Provider
public class HalContentHandler implements MessageBodyWriter, MessageBodyReader<Object> {

    private static final MediaType HAL_JSON_TYPE = new MediaType("application", "hal+json");

    private static final MediaType HAL_XML_TYPE  = new MediaType("application", "hal+xml");
    
    private static final MediaType JSON_TYPE = new MediaType( "application", "json");

    /**
     *
     * @param aClass
     * @param type
     * @param annotations
     * @param mediaType
     * @return
     */
    @Override
    public boolean isWriteable(Class aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        
        /*
        * Check if the object to write is a Java Bean annotated with HalRootElement
        */
        return aClass.isAnnotationPresent( HalRootElement.class) 
                && (mediaType.isCompatible(HAL_JSON_TYPE) || mediaType.isCompatible(HAL_XML_TYPE));
    }

    /**
     *
     * @param o
     * @param aClass
     * @param type
     * @param annotations
     * @param mediaType
     * @return
     */
    @Override
    public long getSize(Object o, Class aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return -1L;
    }

    /**
     *
     * @param o
     * @param aClass
     * @param type
     * @param annotations
     * @param mediaType
     * @param headers
     * @param outputStream
     * @throws IOException
     * @throws WebApplicationException
     */
    @Override
    public void writeTo(Object o, Class aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap headers, OutputStream outputStream) throws IOException, WebApplicationException {
        
        try {
            if( o != null) {
                HalMarshaller.marshal(o, mediaType, outputStream);
            }else
                throw new Exception( "Error creating HAL Representation of Object " + o);
        } catch( Exception e) {
            System.out.println( e.getMessage());
            throw new  InternalServerErrorException( e.getMessage());
        }
    }

    /**
     *
     * @param type
     * @param genericType
     * @param antns
     * @param mediaType
     * @return
     */
    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] antns, MediaType mediaType) {
        
        return mediaType.isCompatible(HAL_JSON_TYPE) 
                || mediaType.isCompatible(HAL_XML_TYPE) 
                || mediaType.isCompatible( JSON_TYPE);
    }

    /**
     *
     * @param type
     * @param type1
     * @param antns
     * @param mt
     * @param mm
     * @param in
     * @return
     * @throws IOException
     * @throws WebApplicationException
     */
    @Override
    public Object readFrom(Class<Object> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException {
        Object o;
         
        try {
           o = HalUnmarshaller.unmarshal(in, type);
        }catch( BuilderException e) {
            throw new BadRequestException( e);
        }catch( Exception e) {
            System.out.println( "HAL BuilderException: " + e.getMessage());
            throw new InternalServerErrorException(  e.getMessage());
        }
        
        return o;
    }
    
}
