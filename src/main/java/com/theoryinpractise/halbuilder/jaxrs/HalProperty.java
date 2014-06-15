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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The <code>@HalProperty</code> annotation identifies fields that will be 
 * present in the HAL representation.
 * 
 * <p>
 * It can only be used in a field. The fields annotated using this tag must 
 * have proper Java Beans getters and setters.
 * </p>
 * @author Mikel Corcuera <mik.corcuera@gmail.com>
 */


@Retention( RetentionPolicy.RUNTIME)
@Target( ElementType.FIELD)
public @interface HalProperty {
    
    /**
     * The name to show in the HAL representation of the Object
     * @return The name of the HAL property
     */
    String name();
        
    /**
     * Set the field to be only for input pourposes. If <code>true</code>,
     * the output representation of the object will not have the value of the 
     * field althoug it can be read from an input HAL representation
     * @return True if the value is only present for input, false otherwise
     */
    boolean input() default false;
    
}
