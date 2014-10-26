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

import com.theoryinpractise.halbuilder.api.Representation;
import java.beans.PropertyDescriptor;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.MediaType;

/**
 * This class contains the functionality to get the HAL representation of 
 * a certain object. This object must be annotated with the {@link HalRootElement}
 * and its properties, embedded objects and links with the corresponding annotation
 * in order to be included in the representation.
 * @author Mikel Corcuera <mik.corcuera@gmail.com>
 */
public class HalMarshaller {
    
    /**
     * Creates the representation of one object for the desired media type. The
     * representation is written in the provided output stream.
     * @param o object to marshal
     * @param flags configuration of the output format
     * @param mediaType media type of the output
     * @param os output stream to write the representation
     * @throws Exception when an error creating the representation occurs
     */
    public static void marshal( Object o, Set<URI> flags, MediaType mediaType, OutputStream os) throws Exception
    {
        marshal( o, flags, mediaType.toString(), os);
    }
    
    
    /**
     * Creates the representation of one object for the desired media type. The
     * representation is written in the provided output stream.
     * @param o object to marshal
     * @param flags configuration of the output format
     * @param mediaType media type of the output
     * @param os output stream to write the representation
     * @throws Exception when an error creating the representation occurs
     */ 
    public static void marshal( Object o, Set<URI> flags, String mediaType, OutputStream os) throws Exception
    {
        Representation representation = getRepresentation( o, false);
        
        representation.toString( mediaType, flags, new OutputStreamWriter( os));
    }
    
    /**
     * Creates the representation of one object for the desired media type. The
     * representation is written in the provided output stream.
     * @param o object to marshal
     * @param mediaType media type of the output
     * @param os output stream to write the representation
     * @throws Exception when an error creating the representation occurs
     */
    public static void marshal( Object o, MediaType mediaType, OutputStream os) throws Exception
    {
        marshal( o, null, mediaType, os);
    }
    
    /**
     * Creates the representation of one object for the desired media type. The
     * representation is written in the provided output stream.
     * @param o object to marshal
     * @param mediaType media type of the output
     * @param os output stream to write the representation
     * @throws Exception when an error creating the representation occurs
     */
    public static void marshal( Object o, String mediaType, OutputStream os) throws Exception
    {
        marshal( o, null, mediaType, os);
    }
    
    private static Representation getRepresentation( Object o, boolean embeddedElement) throws Exception
    {
        /*
        * Create new Representation for  o
        */
        Representation rep = HalContext.getNewRepresentation();
        
        Class usingClass = o.getClass();
        
        while( usingClass != Object.class) {

            final Field[] fields    = usingClass.getDeclaredFields();
            final Method[] methods  = usingClass.getDeclaredMethods();
            
            /*
            * Inspect for annotated fields (properties and embedded Objects
            */
            for( final Field field : fields) {
                final HalProperty property = field.getAnnotation( HalProperty.class);
                final HalEmbedded embedded = field.getAnnotation( HalEmbedded.class);
                
                /*
                * Include only annotated fields
                */
                if( property != null && property.input() == false ) {
                    
                    /*
                    * Use getter to access field value
                    */
                    rep.withProperty( property.name(),  new PropertyDescriptor(field.getName(), o.getClass()).getReadMethod().invoke( o));
                }
                /*
                 * Get embedded objects
                */
                if( embedded != null) {
                    final Object embeddedObject = new PropertyDescriptor(field.getName(), o.getClass()).getReadMethod().invoke( o);
                    /*
                     * Only include not null embedded objects
                    */
                    if( embeddedObject != null) {

                        if( List.class.isAssignableFrom( embeddedObject.getClass())) {
                            /*
                            * Add every element of a List
                            */
                            for( final Object ob : (List) embeddedObject) {
                                rep.withRepresentation( embedded.value(), getRepresentation( ob, true));
                            }
                        }else {
                            rep.withRepresentation( embedded.value(), getRepresentation( embeddedObject, true));
                        }
                    }
                }
            }
            
            /*
            * Inspect for annotated methods to get links
            */
            for( Method method : methods) {
                final HalSelfLink selfLink  = method.getAnnotation(HalSelfLink.class);
                final HalLink link          = method.getAnnotation( HalLink.class);
                /*
                * Insert selfLink
                */
                if( selfLink != null) {
                    final String linkValue = (String) method.invoke(o);
                    if( linkValue != null) {
                        rep.withLink( "self", linkValue);
                    }
                }
                /*
                * Insert other links
                */
                if( link != null) {
                    final String linkValue = (String) method.invoke(o);
                    if( linkValue != null) {
                        rep.withLink( link.value(), linkValue);
                    }
                }
            }            
            usingClass = usingClass.getSuperclass();
        }
        return rep;
    }
}
