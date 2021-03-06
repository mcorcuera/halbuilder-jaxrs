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

/**
 * This exception is thrown when trying to build a property of certain type and
 * the necessary PropertyBuilder is not found
 * @author Mikel Corcuera <mik.corcuera@gmail.com>
 */
public class PropertyBuilderNotFoundException extends Exception{
            
    /**
     * @param c the class of the property that could not be built
     */
    public PropertyBuilderNotFoundException( Class c)
    {
        super( "PropertyBuilder for type " + c.toString() + " not found");
    }
}
