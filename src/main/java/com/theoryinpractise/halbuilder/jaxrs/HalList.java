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

import java.util.List;

/**
 *
 * @author Mikel Corcuera <mik.corcuera@gmail.com>
 */

@HalRootElement
public class HalList<T>{
    
    @HalEmbedded( name="resources")
    private List<T> resources;
    
    @HalProperty( name="total")
    private int total;
    
    private String selfLink;

    public HalList()
    {
    }
    
    public HalList( List<T> resources)
    {
        this.resources = resources;
    }
    
    public HalList( List<T> resources, int total)
    {
        this.resources = resources;
        this.total = total;
    }
    
    public List<T> getResources() {
        return resources;
    }

    public void setResources(List<T> resources) {
        this.resources = resources;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }
    
    @HalSelfLink
    public String selfLink() {
        return getSelfLink();
    }
    
    
}
