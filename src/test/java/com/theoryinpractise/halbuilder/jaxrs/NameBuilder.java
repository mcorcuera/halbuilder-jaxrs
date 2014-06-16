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
import com.theoryinpractise.halbuilder.impl.representations.MutableRepresentation;
import com.theoryinpractise.halbuilder.jaxrs.builders.BuilderException;
import com.theoryinpractise.halbuilder.jaxrs.builders.PropertyBuilder;

/**
 *
 * @author Mikel Corcuera <mik.corcuera@gmail.com>
 */
public class NameBuilder implements PropertyBuilder<Name>{

    @Override
    public Name build(Object s) {
        Name n = null;
        
        if( ReadableRepresentation.class.isAssignableFrom( s.getClass())) {
            ReadableRepresentation r = (ReadableRepresentation) s;
            n = new Name( (String) r.getValue("firstName"), (String) r.getValue("lastName"));
        }
        return n;
    }

    @Override
    public boolean canBuild(Class type) {
        return Name.class.isAssignableFrom(type);
    }

    @Override
    public Class getBuildType() {
        return Name.class.getClass();
    }
    
}
