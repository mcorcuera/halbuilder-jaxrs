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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mikel Corcuera <mik.corcuera@gmail.com>
 */
public class DateBuilder implements PropertyBuilder<Date>{

    @Override
    public Date build(Object s) {
        Date date = null;
        try {
            SimpleDateFormat parser = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
            date = parser.parse( (String) s);
        } catch (ParseException ex) {
            Logger.getLogger(DateBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
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
