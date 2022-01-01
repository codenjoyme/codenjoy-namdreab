package com.codenjoy.dojo.namdreab.model.hero;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
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
import com.codenjoy.dojo.namdreab.model.Player;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.services.State;

import java.util.Arrays;
import java.util.List;

import static com.codenjoy.dojo.games.namdreab.Element.*;

public class Tail extends PointImpl implements State<Element, Player> {

    private Hero hero;

    Tail(Point pt, Hero hero) {
        super(pt.getX(), pt.getY());
        this.hero = hero;
    }

    Tail(int x, int y, Hero hero) {
        super(x, y);
        this.hero = hero;
    }

    private Element getTail(TailDirection direction, boolean itIsMyHero) {
        if (itIsMyHero)
            return getMyTail(direction);
        else
            return getEnemyTail(direction);
    }

    private Element getMyTail(TailDirection direction) {
        switch (direction) {
            case VERTICAL_DOWN:
                return TAIL_END_DOWN;
            case VERTICAL_UP:
                return TAIL_END_UP;
            case HORIZONTAL_LEFT:
                return TAIL_END_LEFT;
            case HORIZONTAL_RIGHT:
                return TAIL_END_RIGHT;
            default:
                return OTHER;
        }
    }

    private Element getEnemyTail(TailDirection direction) {
        switch (direction) {
            case VERTICAL_DOWN:
                return ENEMY_TAIL_END_DOWN;
            case VERTICAL_UP:
                return ENEMY_TAIL_END_UP;
            case HORIZONTAL_LEFT:
                return ENEMY_TAIL_END_LEFT;
            case HORIZONTAL_RIGHT:
                return ENEMY_TAIL_END_RIGHT;
            default:
                return OTHER;
        }
    }

    private Element getHead(Direction direction, boolean itIsMyHero) {
        if (itIsMyHero)
            return getMyHead(direction);
        else
            return getEnemyHead(direction);
    }

    private Element getMyHead(Direction direction) {
        switch (direction) {
            case DOWN:
                return HEAD_DOWN;
            case UP:
                return HEAD_UP;
            case LEFT:
                return HEAD_LEFT;
            case RIGHT:
                return HEAD_RIGHT;
            default:
                return OTHER;
        }
    }

    private Element getEnemyHead(Direction direction) {
        switch (direction) {
            case DOWN:
                return ENEMY_HEAD_DOWN;
            case UP:
                return ENEMY_HEAD_UP;
            case LEFT:
                return ENEMY_HEAD_LEFT;
            case RIGHT:
                return ENEMY_HEAD_RIGHT;
            default:
                return OTHER;
        }
    }

    private Element getBody(BodyDirection bodyDirection, boolean itIsMyHero) {
        if (itIsMyHero)
            return getMyBody(bodyDirection);
        else
            return getEnemyBody(bodyDirection);
    }

    private Element getMyBody(BodyDirection bodyDirection) {
        switch (bodyDirection) {
            case HORIZONTAL:
                return BODY_HORIZONTAL;
            case VERTICAL:
                return BODY_VERTICAL;
            case TURNED_LEFT_DOWN:
                return BODY_LEFT_DOWN;
            case TURNED_LEFT_UP:
                return BODY_LEFT_UP;
            case TURNED_RIGHT_DOWN:
                return BODY_RIGHT_DOWN;
            case TURNED_RIGHT_UP:
                return BODY_RIGHT_UP;
            default:
                return OTHER;
        }
    }

    private Element getEnemyBody(BodyDirection bodyDirection) {
        switch (bodyDirection) {
            case HORIZONTAL:
                return ENEMY_BODY_HORIZONTAL;
            case VERTICAL:
                return ENEMY_BODY_VERTICAL;
            case TURNED_LEFT_DOWN:
                return ENEMY_BODY_LEFT_DOWN;
            case TURNED_LEFT_UP:
                return ENEMY_BODY_LEFT_UP;
            case TURNED_RIGHT_DOWN:
                return ENEMY_BODY_RIGHT_DOWN;
            case TURNED_RIGHT_UP:
                return ENEMY_BODY_RIGHT_UP;
            default:
                return OTHER;
        }
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        Hero hero = player.getHero();
        return heroPart(hero, Arrays.asList(alsoAtPoint));
    }

    private Element heroPart(Hero hero, List<Object> alsoAtPoint) {
        Tail higher = getHigher(alsoAtPoint);
        Hero hero2 = higher.hero;
        boolean itsMe = hero.equals(hero2);
        if (higher.isHead()) {
            if (hero2.isAlive()) {
                if (!hero2.isActive()) {
                    return itsMe ? HEAD_SLEEP : ENEMY_HEAD_SLEEP;
                } else if (hero2.isFlying()) {
                    return itsMe ? HEAD_FLY : ENEMY_HEAD_FLY;
                } else if (hero2.isFury()) {
                    return itsMe ? HEAD_EVIL : ENEMY_HEAD_EVIL;
                } else {
                    return getHead(hero2.getDirection(), itsMe);
                }
            } else {
                return itsMe ? HEAD_DEAD : ENEMY_HEAD_DEAD;
            }
        }
        if (higher.isTail()) {
            if (hero2.isActive()) {
                return getTail(hero2.getTailDirection(), itsMe);
            } else {
                return itsMe ? TAIL_INACTIVE : ENEMY_TAIL_INACTIVE;
            }
        }
        return getBody(hero2.getBodyDirection(higher), itsMe);
    }

    private Tail getHigher(List<Object> elements) {
        return elements.stream()
                .filter(p -> p instanceof Tail)
                .map(p -> (Tail)p)
                .sorted((t1, t2) -> {
                    boolean isHead1 = t1.isHead();
                    boolean isHead2 = t2.isHead();
                    if (isHead1 && isHead2) {
                        return 0;
                    }

                    if (isHead1 && !isHead2) {
                        return -1;
                    }
                    if (!isHead1 && isHead2) {
                        return 1;
                    }
                    return Integer.compare(t2.getBodyIndex(), t1.getBodyIndex());
                })
                .findFirst()
                .orElse(this);
    }

    private int getBodyIndex() {
        return hero.getBodyIndex(this);
    }

    private boolean isHead() {
        return hero.itsMyHead(this);
    }

    private boolean isTail() {
        return hero.itsMyTail(this);
    }

}