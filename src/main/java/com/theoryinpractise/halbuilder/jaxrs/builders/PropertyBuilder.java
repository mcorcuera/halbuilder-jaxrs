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
 * This interface provide functions to build a Java object based on the 
 * HAL property in the HAL representation. Thus, classes implementing this 
 * interface must provide with the logic to build the property for the class
 * they are configured
 * @author Mikel Corcuera <mik.corcuera@gmail.com>
 * @param <T> The object type that the PropertyBuilder can build.
 */
public interface PropertyBuilder<T> {
        
    /**
     * This function takes the input parameter and builds it into an object
     * of class <code>T</code>.
     * @param s object to be converted in into an object
     * of class <code>T</code>.
     * @return the built object of class <code>T</code>
     * @throws BuilderException when the function can not build the object out
     * of the property input
     */
    public T build( Object s) throws BuilderException;
    
    /**
     * This functions tells if the PropertyBuilder is able to build a object of 
     * a certain class. This function will be use when not direct matching using
     * getBuildType() is found.
     * @param type the object class to build
     * @return true if it is able to build an object of type class
     */
    public boolean canBuild( Class type);
    
    /**
     * 
     * @return the class of the build type of the class. That is, the class of T
     */
    public Class getBuildType();
}
