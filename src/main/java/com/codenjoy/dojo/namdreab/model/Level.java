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


import com.codenjoy.dojo.client.ElementsMap;
import com.codenjoy.dojo.games.namdreab.Element;
import com.codenjoy.dojo.games.namdreab.ElementUtils;
import com.codenjoy.dojo.namdreab.model.items.*;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.field.AbstractLevel;
import com.codenjoy.dojo.services.field.PointField;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.codenjoy.dojo.games.namdreab.Element.*;
import static com.codenjoy.dojo.services.Direction.*;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

public class Level extends AbstractLevel {

    private static final ElementsMap<Element> elements = new ElementsMap<>(Element.values());

    public Level(String map) {
        super(map);
    }

    public List<Hero> heroes() {
        return Arrays.stream(new Hero[] { hero(), enemy() })
                .filter(Objects::nonNull)
                .collect(toList());
    }

    public Hero hero() {
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

        return parseHero(point);
    }

    private Hero parseHero(Point head) {
        Direction direction = headDirection(head);
        Hero hero = new Hero(direction.inverted());

        Element headElement = elementAt(head);
        if (ElementUtils.isFly(headElement)) {
            hero.eatDeathCap();
        }

        if (ElementUtils.isEvil(headElement)) {
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

    private Element elementAt(Point point) {
        return elements.get(getAt(point));
    }

    private Direction headDirection(Point head) {
        return Direction.getValues().stream()
                .map(direction -> {
                    Element at = elementAt(direction.change(head));
                    return ElementUtils.parts.get(direction).contains(at)
                            ? direction
                            : null;
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Something wrong with head"));
    }

    private Direction next(Point point, Direction direction) {
        switch (elementAt(point)) {
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

    public Hero enemy() {
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

        return parseHero(point);
    }

    public List<Blueberry> blueberries() {
        return find(Blueberry::new, BLUEBERRY);
    }

    public List<Acorn> acorns() {
        return find(Acorn::new, ACORN);
    }

    public List<DeathCap> deathCaps() {
        return find(DeathCap::new, DEATH_CAP);
    }

    public List<FuryPill> furyPills() {
        return find(FuryPill::new, FURY_PILL);
    }

    public List<Gold> gold() {
        return find(Gold::new, GOLD);
    }

    public List<Rock> rocks() {
        return find(Rock::new, ROCK);
    }

    public List<StartSpot> starts() {
        return find(StartSpot::new, START_SPOT);
    }

    @Override
    protected void fill(PointField field) {
        field.addAll(rocks());
        field.addAll(starts());
        field.addAll(blueberries());
        field.addAll(acorns());
        field.addAll(furyPills());
        field.addAll(deathCaps());
        field.addAll(gold());
    }

}
