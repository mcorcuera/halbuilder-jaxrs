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

import java.sql.Date;

/**
 *Property builder for java.sql.Date objects
 * @author Mikel Corcuera <mik.corcuera@gmail.com>
 */
public class SqlDateBuilder implements PropertyBuilder<Date>{

    @Override
    public Date build(Object s) {
        return Date.valueOf( (String)s);
    }

    @Override
    public boolean canBuild(Class type) {
        return Date.class.isAssignableFrom(type);
    }

    @Override
    public Class getBuildType() {
        return Date.class;
    }
    
}
