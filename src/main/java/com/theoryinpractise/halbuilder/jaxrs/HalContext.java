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
 *
 * @author Mikel Corcuera <mik.corcuera@gmail.com>
 */
public class HalContext {
    
    private static RepresentationFactory rp;
     
     static {
         rp = new StandardRepresentationFactory()
                    .withFlag( RepresentationFactory.PRETTY_PRINT)
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
     
     public static RepresentationFactory getRepresentationFactory()
     {
         return rp;
     }
     
     public static Representation getNewRepresentation()
     {
         return rp.newRepresentation();
     
     }
     
     public static void registerPropertyBuilder( PropertyBuilder p)
     {
         if( builders.get(p.getBuildType().toString()) != null) {
             builders.remove( p.getBuildType().toString());
         }
         builders.put( p.getBuildType().toString(), p);
     }
     
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
