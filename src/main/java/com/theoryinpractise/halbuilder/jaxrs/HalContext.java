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
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.jaxrs.builders.DateBuilder;
import com.theoryinpractise.halbuilder.jaxrs.builders.IntegerBuilder;
import com.theoryinpractise.halbuilder.jaxrs.builders.LongBuilder;
import com.theoryinpractise.halbuilder.jaxrs.builders.PropertyBuilder;
import com.theoryinpractise.halbuilder.jaxrs.builders.SqlDateBuilder;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Base class of the JAX-RS Extension for the HalBuilder API. It serves the
 * available {@link RepresentationFactory} to use, creates new <code>Representations</code>
 * and also provides utilites to register <code>PropertyBuilders</code> for unmarshalling
 * HAL representations.
 * <p>
 * It comes with the following <code>PropertyBuilders</code>:
 * <ul>
 *      <li>{@link LongBuilder}: builds {@link Long} objects </li>
 *      <li> {@link IntegerBuilder}: builds {@link Integer} objects </li>
 *      <li> {@link StringBuilder}: builds {@link String} objects </li>
 *      <li> {@link DateBuilder}: builds {@link java.util.Date} objects </li>
 *      <li> {@link SqlDateBuilder}: builds {@link java.sql.Date} </li>
 * </ul>
 * </p>
 * @author Mikel Corcuera <mik.corcuera@gmail.com>
 */
public class HalContext {
    
    private static RepresentationFactory rp;
     
     static {
         rp = new StandardRepresentationFactory()
                    .withFlag( RepresentationFactory.PRETTY_PRINT)
                    .withFlag( RepresentationFactory.STRIP_NULLS)
                    .withFlag( RepresentationFactory.SINGLE_ELEM_ARRAYS);
     }
     
     private static Map<String,PropertyBuilder> builders = new HashMap<String,PropertyBuilder>();
     
     /*
      * Register the default Property builders
     */
     static {
        registerPropertyBuilder( new LongBuilder());
        registerPropertyBuilder( new com.theoryinpractise.halbuilder.jaxrs.builders.StringBuilder());
        registerPropertyBuilder( new SqlDateBuilder());
        registerPropertyBuilder( new IntegerBuilder());
        registerPropertyBuilder( new DateBuilder());
     }
     
    /**
     * Provides the HAL <code>RepresentationFactory</code> used to write and read
     * representations.
     * @return The RepresentationFactory in use
     */
    public static RepresentationFactory getRepresentationFactory()
     {
         return rp;
     }
     
    /**
     * Creates a new <code>Representation</code> from the <code>RepresentationFactory</code>
     * @return A new <code>Representation</code>
     */
    public static Representation getNewRepresentation()
     {
         return rp.newRepresentation();
     
     }
     
    /**
     * Register a new {@link PropertyBuilder} for HAL unmarshalling. This function
     * replaces the {@link PropertyBuilder} for the same type if is already
     * present.
     * @param builder The {@link PropertyBuilder} to register
     * @see PorpertyBuilder HalUnmarshaller
     */
    public static void registerPropertyBuilder( PropertyBuilder builder)
     {
         if( builders.get(builder.getBuildType().toString()) != null) {
             builders.remove( builder.getBuildType().toString());
         }
         builders.put( builder.getBuildType().toString(), builder);
     }
    
    /**
     * Returns the registered {@link PropertyBuilder} to uses with the provided
     * type. 
     * <p>
     * First it searches for direct matches using {@link PropertyBuilder#getBuildType()},
     * and if there is no match, it searches for a suitable builder using {@link PropertyBuilder#canBuild(java.lang.Class)}
     * </p>
     * @param type The {@link Class} of the property to build
     * @return The {@link PropertyBuilder} that can build objects of the type provided. Returns <code>null</code> if ther is no match
     *          
     */
    public static PropertyBuilder getPropertyBuilder( Class type)
     {
         PropertyBuilder p = builders.get( type.toString());
         
         if( p == null) {
             for( Entry<String,PropertyBuilder> builder : builders.entrySet()) {
                 if( builder.getValue().canBuild(type)) {
                     p = builder.getValue();
                     break;
                 }
             }
         }
         
         return p;
     }
}
