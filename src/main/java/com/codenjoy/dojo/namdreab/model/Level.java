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


import com.codenjoy.dojo.games.namdreab.Element;
import com.codenjoy.dojo.namdreab.model.items.*;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.field.AbstractLevel;
import com.codenjoy.dojo.services.field.PointField;

import java.util.List;
import java.util.Objects;

import static com.codenjoy.dojo.games.namdreab.Element.*;
import static com.codenjoy.dojo.services.Direction.*;
import static java.util.function.Function.identity;

public class Level extends AbstractLevel {

    public Level(String map) {
        super(map);
    }

    public Hero hero(Field field) {
        Point point = find(identity(),
                HEAD_DOWN,
                HEAD_UP,
                HEAD_LEFT,
                HEAD_RIGHT,
                HEAD_SLEEP,
                HEAD_DEAD,
                HEAD_EVIL,
                HEAD_FLY).stream()
                        .findAny()
                        .orElse(null);

        if (point == null) {
            return null;
        }

        return parseHero(point, field);
    }

    private Hero parseHero(Point head, Field field) {
        Direction direction = headDirection(head);
        Hero hero = new Hero(direction.inverted());
        hero.init(field);

        Element headElement = getAt(head);
        if (headElement.isFly()) {
            hero.eatFlying();
        }

        if (headElement.isEvil()) {
            hero.eatFury();
        }

        hero.addTail(head);

        Point point = head;
        while (direction != null) {
            point = direction.change(point);
            hero.addTail(point);
            direction = next(point, direction);
        }

        return hero;
    }

    private Direction headDirection(Point head) {
        return Direction.getValues().stream()
                .map(direction -> {
                    Element at = getAt(direction.change(head));
                    return parts.get(direction).contains(at)
                            ? direction
                            : null;
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Something wrong with head"));
    }

    private Direction next(Point point, Direction direction) {
        switch (getAt(point)) {
            case BODY_HORIZONTAL:
            case ENEMY_BODY_HORIZONTAL:
            case BODY_VERTICAL:
            case ENEMY_BODY_VERTICAL:
                return direction;
            case BODY_LEFT_DOWN:
            case ENEMY_BODY_LEFT_DOWN:
                return direction == RIGHT ? DOWN : LEFT;
            case BODY_RIGHT_DOWN:
            case ENEMY_BODY_RIGHT_DOWN:
                return direction == LEFT ? DOWN : RIGHT;
            case BODY_LEFT_UP:
            case ENEMY_BODY_LEFT_UP:
                return direction == RIGHT ? UP : LEFT;
            case BODY_RIGHT_UP:
            case ENEMY_BODY_RIGHT_UP:
                return direction == LEFT ? UP : RIGHT;
            default:
                return null;
        }
    }

    public Hero enemy(Field field) {
        Point point = find(identity(),
                ENEMY_HEAD_DOWN,
                ENEMY_HEAD_UP,
                ENEMY_HEAD_LEFT,
                ENEMY_HEAD_RIGHT,
                ENEMY_HEAD_SLEEP,
                ENEMY_HEAD_DEAD,
                ENEMY_HEAD_EVIL,
                ENEMY_HEAD_FLY)
                .stream()
                .findAny()
                .orElse(null);

        if (point == null) {
            return null;
        }

        return parseHero(point, field);
    }

    public List<Apple> apples() {
        return find(Apple::new, APPLE);
    }

    public List<Stone> stones() {
        return find(Stone::new, STONE);
    }

    public List<FlyingPill> flyingPills() {
        return find(FlyingPill::new, FLYING_PILL);
    }

    public List<FuryPill> furyPills() {
        return find(FuryPill::new, FURY_PILL);
    }

    public List<Gold> gold() {
        return find(Gold::new, GOLD);
    }

    public List<Wall> walls() {
        return find(Wall::new, WALL);
    }

    public List<StartFloor> starts() {
        return find(StartFloor::new, START_FLOOR);
    }

    @Override
    protected void fill(PointField field) {
        field.addAll(walls());
        field.addAll(starts());
        field.addAll(apples());
        field.addAll(stones());
        field.addAll(furyPills());

        // TODO other elements here
    }

}
