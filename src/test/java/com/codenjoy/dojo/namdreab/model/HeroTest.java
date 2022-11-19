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

import static com.codenjoy.dojo.namdreab.services.GameSettings.Keys.ACORN_REDUCED;
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

    // Проверка что борода растет
    @Test
    public void heroIncreasing() {
        int before = hero.size();
        blueberriesAtAllPoints(true);// впереди черника -> увеличиваем бородачу бороду
        hero.tick();
        hero.eat();
        blueberriesAtAllPoints(false);
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

        // если черника
        blueberriesAtAllPoints(true);
        hero.tick();
        hero.eat();
        blueberriesAtAllPoints(false);
        assertEquals("Неактивный бородач изменился!", startBody, hero.body());
        assertTrue("Бородач мертв!", hero.isAlive());

        // если желудь
        acornsAtAllPoints(true);
        hero.tick();
        hero.eat();
        acornsAtAllPoints(false);
        assertEquals("Неактивный бородач изменился!", startBody, hero.body());
        assertTrue("Бородач мертв!", hero.isAlive());

        // если скала
        rocksAtAllPoints(true);
        hero.tick();
        hero.eat();
        rocksAtAllPoints(false);
        assertEquals("Неактивный бородач изменился!", startBody, hero.body());
        assertTrue("Бородач мертв!", hero.isAlive());
    }

    // Бородач погибает при столкновении со скалой
    @Test
    public void diedByRock() {
        int before = hero.size();
        rocksAtAllPoints(true);// впереди черника -> увеличиваем бороду
        hero.tick();
        hero.eat();
        rocksAtAllPoints(false);
        assertTrue("Бородач не погиб от препятствия!", !hero.isAlive());
    }

    // короткий бородач погибает от желудя
    @Test
    public void diedByAcorn() {
        heroIncreasing(acornReduced() - 1);
        acornsAtAllPoints(true);// впереди желудь
        hero.tick();
        hero.eat();
        acornsAtAllPoints(false);
        assertTrue("Маленький бородач не погиб от желудя!", !hero.isAlive());
    }

    // большой бородач уменьшается от желудя, но не погибает
    @Test
    public void reduceByAcorn() {
        heroIncreasing(acornReduced());
        int before = hero.size();
        acornsAtAllPoints(true);// впереди желудь
        hero.tick();
        hero.eat();
        acornsAtAllPoints(false);
        assertTrue("Большой бородач погиб от желудя!", hero.isAlive());
        assertEquals("Бородач не укоротился на предполагаемую длину!",
                before - acornReduced(), hero.size());
        hero.tick();
        hero.eat();
    }

    private Integer acornReduced() {
        return settings.integer(ACORN_REDUCED);
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

    // если бородач съел желудь, желудь внутри него
    // и он может вернуть его на поле )
    @Test
    public void eatAcorn() {
        int additionLength = 4;
        int acorns = 0;
        for (int i = 0; i < 4; i++) {
            heroIncreasing(additionLength);
            acornsAtAllPoints(true);
            hero.tick();
            hero.eat();
            acornsAtAllPoints(false);
            hero.tick();
            hero.eat();
            assertTrue("Бородач погиб!", hero.isAlive());
            assertEquals("Съев желудь, он не появился внутри бородача!", ++acorns, hero.acornsCount());
        }

        // возврат желудей
        // невозможно поставить
        canSetAcorn(false);
        for (int i = 0; i < 4; i++) {
            hero.act();
            assertTrue("Бородач погиб!", hero.isAlive());
            assertEquals("Количество желудей в бородаче уменьшилось!", acorns, hero.acornsCount());
        }

        // возможно поставить
        canSetAcorn(true);
        for (int i = 0; i < 4; i++) {
            hero.act();
            assertTrue("Бородач погиб!", hero.isAlive());
            assertEquals("Количество желудей в бородаче не уменьшилось!", --acorns, hero.acornsCount());
        }
    }

    // если бородач съел бледную поганку, 10 ходов она действует
    @Test
    public void eatDeathCap() {
        flyingPillsAtAllPoints(true);
        hero.tick();
        hero.eat();
        flyingPillsAtAllPoints(false);
        for (int i = 1; i <= 10; i++) {
            hero.tick();
            hero.eat();
            assertEquals("Оставшееся количество ходов в полете не соответствует ожидаемому.",
                    10 - i, hero.flyingCount());
        }
        assertEquals("Количество ходов в полете не может быть меньше 0.", 0, hero.furyCount());
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

    private void blueberriesAtAllPoints(boolean enable) {
        when(game.isBlueberry(any(Point.class))).thenReturn(enable);// впереди черника
    }

    private void flyingPillsAtAllPoints(boolean enable) {
        when(game.isDeathCap(any(Point.class))).thenReturn(enable);// впереди бледная поганка
    }

    private void furyPillsAtAllPoints(boolean enable) {
        when(game.isFuryPill(any(Point.class))).thenReturn(enable);// впереди пилюля ярости
    }

    private void goldAtAllPoints(boolean enable) {
        when(game.isGold(any(Point.class))).thenReturn(enable);// впереди золото
    }

    private void acornsAtAllPoints(boolean enable) {
        when(game.isAcorn(any(Point.class))).thenReturn(enable);// впереди желудь
    }

    private void rocksAtAllPoints(boolean enable) {
        when(game.isBarrier(any(Point.class))).thenReturn(enable);// впереди стена
    }

    // установка желудей
    private void canSetAcorn(boolean enable) {
        when(game.addAcorn(any(Point.class))).thenReturn(enable);
    }
}