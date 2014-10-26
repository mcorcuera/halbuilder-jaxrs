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

package com.theoryinpractise.halbuilder.jaxrs.builders;

import java.sql.Timestamp;

/** 
 * Property builder for java.sql.Timestamp objects
 * @author Mikel Corcuera <mik.corcuera@gmail.com>
 */
public class TimestampBuilder implements PropertyBuilder<Timestamp>{

    @Override
    public Timestamp build(Object s) throws BuilderException {
        
        long timeL = Long.parseLong( (String) s);
        
        Timestamp time = new Timestamp( timeL);
        
        return time;
    }

    @Override
    public boolean canBuild(Class type) {
        return type.isAssignableFrom( Timestamp.class);
    }

    @Override
    public Class getBuildType() {
        return Timestamp.class;
    }
    
}
