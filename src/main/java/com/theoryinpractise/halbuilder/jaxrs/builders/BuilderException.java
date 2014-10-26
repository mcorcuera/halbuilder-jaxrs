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
 * This exception is thrown when there is an error building a property into a 
 * certain object type.
 * @author Mikel Corcuera <mik.corcuera@gmail.com>
 */
public class BuilderException extends Exception{
    
    /**
     * Default constructor with no message
     */
    public BuilderException() {
        super();
    }
    
    /**
     * @param message the message to be shown with the exception
     */
    public BuilderException( String message) {
        super( message);
    }
    
    /**
     *
     * @param e the exception causing this exception
     */
    public BuilderException( Exception e) {
        super( e);
    }
}
