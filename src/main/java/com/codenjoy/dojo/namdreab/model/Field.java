package com.codenjoy.dojo.namdreab.model;

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


import com.codenjoy.dojo.namdreab.model.items.*;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.field.Accessor;
import com.codenjoy.dojo.services.round.RoundGameField;

public interface Field extends RoundGameField<Player, Hero> {

    boolean isBarrier(Point pt);

    boolean isBlueberry(Point pt);

    boolean isAcorn(Point pt);

    boolean isDeathCap(Point pt);

    boolean isFlyAgaric(Point pt);

    boolean isStrawberry(Point pt);

    void addBlueberry(Point pt);

    boolean addAcorn(Point pt);

    void addDeathCap(Point pt);

    void addFlyAraric(Point pt);

    void addStrawberry(Point pt);

    Hero enemyEatenWith(Hero h);

    Accessor<Rock> rocks();

    Accessor<StartSpot> starts();

    Accessor<Blueberry> blueberries();

    Accessor<Acorn> acorns();

    Accessor<FlyAgaric> flyAgarics();

    Accessor<DeathCap> deathCaps();

    Accessor<Strawberry> strawberry();
}
