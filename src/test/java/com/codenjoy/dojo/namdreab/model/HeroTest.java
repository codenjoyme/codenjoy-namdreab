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


import com.codenjoy.dojo.namdreab.TestGameSettings;
import com.codenjoy.dojo.namdreab.model.items.Tail;
import com.codenjoy.dojo.namdreab.services.GameSettings;
import com.codenjoy.dojo.services.Point;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static com.codenjoy.dojo.namdreab.services.GameSettings.Keys.STONE_REDUCED;
import static com.codenjoy.dojo.services.PointImpl.pt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HeroTest {

    private Namdreab game;
    private Hero hero;
    private GameSettings settings;

    @Before
    public void setup() {
        settings = new TestGameSettings();
        hero = new Hero(pt(0, 0));
        game = mock(Namdreab.class);
        when(game.settings()).thenReturn(settings);
        hero.init(game);
        hero.setActive(true);
        hero.setPlayer(mock(Player.class));
        checkStartValues();
    }

    private void checkStartValues() {
        assertTrue("Бородач мертв!", hero.isAlive());
        assertTrue("Бородач не активен!", hero.isActive());
    }

    private void heroIncreasing(int additionLength) {
        for (int i = 0; i < additionLength; i++)
            heroIncreasing();
    }

    // Проверка что бородач растет
    @Test
    public void heroIncreasing() {
        int before = hero.size();
        applesAtAllPoints(true);// впереди яблоко -> увеличиваем змейку
        hero.tick();
        hero.eat();
        applesAtAllPoints(false);
        assertEquals("Голова бородача не увеличилась!", before + 1, hero.size());
    }

    // Проверка что неактивный бородач ничего не делает
    @Test
    public void heroInactive() {
        hero.setActive(false);
        LinkedList<Tail> startBody = new LinkedList<>(hero.body());
        // просто тик
        hero.tick();
        hero.eat();
        assertEquals("Неактивный бородач изменился!", startBody, hero.body());
        assertTrue("Бородач мертв!", hero.isAlive());
        // если яблоко
        applesAtAllPoints(true);
        hero.tick();
        hero.eat();
        applesAtAllPoints(false);
        assertEquals("Неактивный бородач изменился!", startBody, hero.body());
        assertTrue("Бородач мертв!", hero.isAlive());
        // если камень
        stonesAtAllPoints(true);
        hero.tick();
        hero.eat();
        stonesAtAllPoints(false);
        assertEquals("Неактивный бородач изменился!", startBody, hero.body());
        assertTrue("Бородач мертв!", hero.isAlive());
        // если стена
        wallsAtAllPoints(true);
        hero.tick();
        hero.eat();
        wallsAtAllPoints(false);
        assertEquals("Неактивный бородач изменился!", startBody, hero.body());
        assertTrue("Бородач мертв!", hero.isAlive());
    }

    // Бородач погибает при столкновении со стеной
    @Test
    public void diedByWall() {
        int before = hero.size();
        wallsAtAllPoints(true);// впереди яблоко -> увеличиваем змейку
        hero.tick();
        hero.eat();
        wallsAtAllPoints(false);
        assertTrue("Бородач не погиб от препятствия!", !hero.isAlive());
    }

    // тест что короткий бородач погибает от камня
    @Test
    public void diedByStone() {
        heroIncreasing(stoneReduced() - 1);
        stonesAtAllPoints(true);// впереди камень
        hero.tick();
        hero.eat();
        stonesAtAllPoints(false);
        assertTrue("Маленький бородач не погиб от камня!", !hero.isAlive());
    }

    // тест что большой бородач уменьшается от камня, но не погибает
    @Test
    public void reduceByStone() {
        heroIncreasing(stoneReduced());
        int before = hero.size();
        stonesAtAllPoints(true);// впереди камень
        hero.tick();
        hero.eat();
        stonesAtAllPoints(false);
        assertTrue("Большой бородач погиб от камня!", hero.isAlive());
        assertEquals("Бородач не укоротился на предполагаемую длину!",
                before - stoneReduced(), hero.size());
        hero.tick();
        hero.eat();
    }

    private Integer stoneReduced() {
        return settings.integer(STONE_REDUCED);
    }

    // бородач может откусить себе хвост
    @Test
    public void reduceItself() {
        int additionLength = 5;
        heroIncreasing(additionLength);
        assertEquals("Бородач не удлиннился!", additionLength + 2, hero.size());
        hero.down();
        hero.tick();
        hero.eat();
        hero.left();
        hero.tick();
        hero.eat();
        hero.up();
        hero.tick();
        hero.eat();
        assertTrue("Бородач погиб укусив свою бороду!", hero.isAlive());
        assertEquals("Укусив свою бороду, бородач не укоротился!", 4, hero.size());
    }

    // если бородач съела камень, камень внутри неё
    // и она может вернуть его на поле
    @Test
    public void eatStone() {
        int additionLength = 4;
        int stonesCount = 0;
        for (int i = 0; i < 4; i++) {
            heroIncreasing(additionLength);
            stonesAtAllPoints(true);
            hero.tick();
            hero.eat();
            stonesAtAllPoints(false);
            hero.tick();
            hero.eat();
            assertTrue("Бородач погиб!", hero.isAlive());
            assertEquals("Съев камень, он не появился внутри бородача!", ++stonesCount, hero.stonesCount());
        }
        // возврат камней
        // невозможно поставить
        canSetStone(false);
        for (int i = 0; i < 4; i++) {
            hero.act();
            assertTrue("Бородач погиб!", hero.isAlive());
            assertEquals("Количество камней в бородаче уменьшилось!", stonesCount, hero.stonesCount());
        }
        // возможно поставить
        canSetStone(true);
        for (int i = 0; i < 4; i++) {
            hero.act();
            assertTrue("Бородач погиб!", hero.isAlive());
            assertEquals("Количество камней в бородаче не уменьшилось!", --stonesCount, hero.stonesCount());
        }
    }

    // если бородач съела пилюлю полёта, 10 ходов она действует
    @Test
    public void eatFlyingPill() {
        flyingPillsAtAllPoints(true);
        hero.tick();
        hero.eat();
        flyingPillsAtAllPoints(false);
        for (int i = 1; i <= 10; i++) {
            hero.tick();
            hero.eat();
            assertEquals("Оставшееся количество ходов полёта не соответствует ожидаемому.",
                    10 - i, hero.flyingCount());
        }
        assertEquals("Количество ходов полёта не может быть меньше 0.", 0, hero.furyCount());
    }

    // если бородач съела пилюлю ярости, 10 ходов она действует
    @Test
    public void eatFuryPill() {
        furyPillsAtAllPoints(true);
        hero.tick();
        hero.eat();
        furyPillsAtAllPoints(false);
        for (int i = 0; i <= 10; i++) {
            assertEquals("Оставшееся количество ходов ярости не соответствует ожидаемому.",
                    10 - i, hero.furyCount());
            hero.tick();
            hero.eat();
        }
        assertEquals("Количество ходов ярости не может быть меньше 0.", 0, hero.furyCount());
    }

    private void applesAtAllPoints(boolean enable) {
        when(game.isApple(any(Point.class))).thenReturn(enable);// впереди яблоко
    }

    private void flyingPillsAtAllPoints(boolean enable) {
        when(game.isFlyingPill(any(Point.class))).thenReturn(enable);// впереди пилюля полёта
    }

    private void furyPillsAtAllPoints(boolean enable) {
        when(game.isFuryPill(any(Point.class))).thenReturn(enable);// впереди пилюля ярости
    }

    private void goldAtAllPoints(boolean enable) {
        when(game.isGold(any(Point.class))).thenReturn(enable);// впереди золото
    }

    private void stonesAtAllPoints(boolean enable) {
        when(game.isStone(any(Point.class))).thenReturn(enable);// впереди камень
    }

    private void wallsAtAllPoints(boolean enable) {
        when(game.isBarrier(any(Point.class))).thenReturn(enable);// впереди стена
    }

    // установка камней
    private void canSetStone(boolean enable) {
        when(game.addStone(any(Point.class))).thenReturn(enable);
    }
}