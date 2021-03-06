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
 * The <code>@HalLink</code> annotation is used to identify methods that return 
 * links related to the object.
 * <p>
 * It can only be used in a field.
 * <p>
 * @see HalSelfLink
 * @author Mikel Corcuera <mik.corcuera@gmail.com>
 */

@Retention( RetentionPolicy.RUNTIME)
@Target( ElementType.METHOD)
public @interface HalLink {

    /**
     * The name of the link on the HAL representation
     * @return The name of the link
     */
    String value();
}
