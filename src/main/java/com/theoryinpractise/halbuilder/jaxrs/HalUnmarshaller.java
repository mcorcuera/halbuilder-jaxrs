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
import com.theoryinpractise.halbuilder.api.RepresentationException;
import com.theoryinpractise.halbuilder.jaxrs.builders.BuilderException;
import com.theoryinpractise.halbuilder.jaxrs.builders.PropertyBuilder;
import com.theoryinpractise.halbuilder.jaxrs.builders.PropertyBuilderNotFoundException;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

/**
 * This class contains the functionality to unmarshal (or create) an object of a 
 * certain type out of its HAL representation. This object must be annotated
 * with the {@link HalRootElement} and its properties, embedded objects and links
 * with the corresponding annotation
 * in order to be read from the representation
 * @author Mikel Corcuera <mik.corcuera@gmail.com>
 */
public class HalUnmarshaller {
    
    /**
     * This function return an Object resulting of the unmarshalling of a certain
     * representation for a certain class
     * @param is input stream from where to read the representation
     * @param type object class to be build
     * @return the object of class type created out of the representation
     * @throws Exception  when an error creating the object occurs
     */
    public static Object unmarshal( InputStream is, Class type) throws Exception
    {
        ReadableRepresentation r;
        r = HalContext.getRepresentationFactory().readRepresentation( new InputStreamReader( is));
        
        if( r == null)
            return null;
        
        return getObjectFromRepresentation( r, type);
    }
    
    private static Object getObjectFromRepresentation( ReadableRepresentation r, Class type) throws BuilderException, Exception
    {        
        Object o = type.getConstructor().newInstance();
        
        while( type != Object.class) {
            
             final Field[] fields    = type.getDeclaredFields();
             
             for( final Field field : fields) {
                 final HalProperty halProperty = field.getAnnotation( HalProperty.class);
                 final HalEmbedded halEmbedded = field.getAnnotation( HalEmbedded.class);
                 
                 if( halProperty != null) {
                    Object property;
                    try{ 
                        property = r.getValue( halProperty.name());
                    }catch( RepresentationException e) {
                        property = null;
                    }
                    
                    if( property != null) {
                         PropertyBuilder builder = HalContext.getPropertyBuilder( field.getType());
                         
                         if( builder == null)
                             throw new PropertyBuilderNotFoundException( field.getType());
                         
                         Object builtProperty = builder.build(property);
                            
                         new PropertyDescriptor(field.getName(), o.getClass()).getWriteMethod().invoke(o, builtProperty);
                     }
                 }
                 
                 if( halEmbedded != null) {
                     ReadableRepresentation embeddedRepresentation;
                     try {
                         embeddedRepresentation = r.getResourcesByRel( halEmbedded.value()).get(0);
                     }catch( Exception e) {
                        embeddedRepresentation = null;
                    }
                     
                    if( embeddedRepresentation != null) {
                        Object embedded = getObjectFromRepresentation( embeddedRepresentation, field.getType());
                        new PropertyDescriptor( field.getName(), o.getClass()).getWriteMethod().invoke(o, embedded);
                    }
                 }
             }
            type = type.getSuperclass();
        }

        return o;
    }
    
}
