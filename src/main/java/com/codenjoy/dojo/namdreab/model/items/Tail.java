package com.codenjoy.dojo.namdreab.model.items;

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


import com.codenjoy.dojo.games.namdreab.Element;
import com.codenjoy.dojo.namdreab.model.Hero;
import com.codenjoy.dojo.namdreab.model.Player;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.services.printer.state.State;

import java.util.Arrays;
import java.util.List;

import static com.codenjoy.dojo.games.namdreab.Element.*;

public class Tail extends PointImpl implements State<Element, Player> {

    private Hero hero;

    public Tail(Point pt, Hero hero) {
        super(pt.getX(), pt.getY());
        this.hero = hero;
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        Hero hero = player.getHero();
        return higher(Arrays.asList(alsoAtPoint)).heroPart(hero);
    }

    private Element heroPart(Hero hero) {
        Hero hero2 = this.hero;
        boolean itsMe = hero.equals(hero2);
        if (isHead()) {
            if (hero2.isAlive()) {
                if (!hero2.isActive()) {
                    return itsMe ? HEAD_SLEEP : ENEMY_HEAD_SLEEP;
                } else if (hero2.isFlying()) {
                    return itsMe ? HEAD_FLY : ENEMY_HEAD_FLY;
                } else if (hero2.isFury()) {
                    return itsMe ? HEAD_EVIL : ENEMY_HEAD_EVIL;
                } else {
                    return Element.head(hero2.direction(), itsMe);
                }
            } else {
                return itsMe ? HEAD_DEAD : ENEMY_HEAD_DEAD;
            }
        }
        if (isTail()) {
            if (hero2.isActive()) {
                return Element.tail(hero2.tailDirection(), itsMe);
            } else {
                return itsMe ? TAIL_INACTIVE : ENEMY_TAIL_INACTIVE;
            }
        }
        return Element.body(hero2.bodyDirection(this), itsMe);
    }

    private Tail higher(List<Object> elements) {
        return elements.stream()
                .filter(element -> element instanceof Tail)
                .map(element -> (Tail) element)
                .min((t1, t2) -> {
                    boolean isHead1 = t1.isHead();
                    boolean isHead2 = t2.isHead();
                    if (isHead1 && isHead2) {
                        return 0;
                    }

                    if (isHead1) {
                        return -1;
                    }
                    if (isHead2) {
                        return 1;
                    }
                    return Integer.compare(t2.bodyIndex(), t1.bodyIndex());
                })
                .orElse(this);
    }

    private int bodyIndex() {
        return hero.getBodyIndex(this);
    }

    private boolean isHead() {
        return hero.itsMyHead(this);
    }

    private boolean isTail() {
        return hero.itsMyTail(this);
    }
}