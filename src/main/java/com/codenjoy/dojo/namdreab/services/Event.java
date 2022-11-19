package com.codenjoy.dojo.namdreab.services;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.services.event.EventObject;

import java.util.Objects;

public class Event implements EventObject<Event.Type, Integer> {

    private Type type;
    private int value;

    public enum Type {
        START,
        WIN,
        DIE,
        BLUEBERRY,
        ACORN,
        EAT,
        GOLD,
        FLYING,
        FURY
    }

    public Event(Type type) {
        this(type, 1);
    }

    public Event(Type type, int value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        return _equals(o);
    }

    @Override
    public int hashCode() {
        return _hashCode();
    }

    @Override
    public Type type() {
        return type;
    }

    @Override
    public Integer value() {
        return value;
    }

    @Override
    public String toString() {
        if (isEat()) {
            return String.format("%s[%s]", type, value);
        }
        return type.name();
    }

    private boolean isEat() {
        return type == Type.EAT;
    }
}
