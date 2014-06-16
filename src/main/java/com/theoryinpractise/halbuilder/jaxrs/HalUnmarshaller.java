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
 * 
 * @author Mikel Corcuera <mik.corcuera@gmail.com>
 */
public class HalUnmarshaller {
    
    public static Object unmarshal( InputStream is, Class type) throws Exception
    {
        ReadableRepresentation r;
        r = HalContext.getRepresentationFactory().readRepresentation( new InputStreamReader( is));
        
        return getObjectFromRepresentation( r, type);
    }
    
    private static Object getObjectFromRepresentation( ReadableRepresentation r, Class type) throws BuilderException, Exception
    {        
        Object o = type.getConstructor().newInstance();
        
        while( type != Object.class) {
            
             final Field[] fields    = type.getDeclaredFields();
             
             for( final Field field : fields) {
                 final HalProperty halProperty = field.getAnnotation( HalProperty.class);
                 
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
             }
            type = type.getSuperclass();
        }

        return o;
    }
    
}
