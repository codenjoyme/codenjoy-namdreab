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


import org.junit.Test;

public class GameTest extends AbstractGameTest {

    // карта со своим героем
    @Test
    public void shouldHeroOnBoard() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // старт героя из "стартового бокса"
    @Test
    public void shouldGetOutFromStartPoint() {
        dice(0);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼#     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "╘►     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼╘►    ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼#╘►   ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    // карта с черникой, желудями, бледными поганками, пилюлями ярости, деньгами
    @Test
    public void shouldBoardWithElements() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼ $ ® ☼\n" +
                "☼  ●  ☼\n" +
                "☼  © ○☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼ $ ® ☼\n" +
                "☼  ●  ☼\n" +
                "☼  © ○☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // тест событий
    @Test
    public void shouldGoldEvent_whenEatIt() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼╘►$  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼╘►$  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents("[GOLD]");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // тест событий
    @Test
    public void shouldBlueberryEvent_whenEatIt() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼╘►○  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼╘►○  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents("[BLUEBERRY]");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼╘═►  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldAcornAndDieEvents_whenEatAcorn_lengthIsTooShort() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼╘►●  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼╘►●  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents("[DIE]");

        assertEquals(false, hero().isAlive());
        assertEquals(true, hero().isActive());

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ╘☻  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertEquals(false, hero().isAlive());
        assertEquals(true, hero().isActive());
    }

    @Test
    public void shouldAcornAndAlive_whenEatAcorn_lengthIsOk() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼╔═══╗☼\n" +
                "☼╚═╗╘╝☼\n" +
                "☼  ▼  ☼\n" +
                "☼  ●  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼╔═══╗☼\n" +
                "☼╚═╗╘╝☼\n" +
                "☼  ▼  ☼\n" +
                "☼  ●  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents("[ACORN]");

        assertEquals(true, hero().isAlive());
        assertEquals(true, hero().isActive());

        assertF("☼☼☼☼☼☼☼\n" +
                "☼╔═╕  ☼\n" +
                "☼╚═╗  ☼\n" +
                "☼  ║  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼╔╕   ☼\n" +
                "☼╚═╗  ☼\n" +
                "☼  ║  ☼\n" +
                "☼  ║  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertEquals(true, hero().isAlive());
        assertEquals(true, hero().isActive());
    }

    // тест продолжения движения без дополнительных указаний
    @Test
    public void shouldMoveByInertia_whenNoCommand_directionLeftToRight() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼╘►   ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╘► ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼   ╘►☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldMoveByInertia_whenNoCommand_directionRightToLeft() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼   ◄╕☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ◄╕ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ◄╕  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼◄╕   ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldMoveByInertia_whenNoCommand_directionUpToDown() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldMoveByInertia_whenNoCommand_directionDownToUp() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ▲  ☼\n" +
                "☼  ╙  ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ▲  ☼\n" +
                "☼  ╙  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ▲  ☼\n" +
                "☼  ╙  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼  ▲  ☼\n" +
                "☼  ╙  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // тесты движения в заданную сторону
    @Test
    public void shouldTurnDown() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╘► ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().down();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼   ╓ ☼\n" +
                "☼   ▼ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldTurnRight() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╘► ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldTurnUp() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╘► ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().up();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼   ▲ ☼\n" +
                "☼   ╙ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldTurnLeft() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ◄╕  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // рост бороды
    @Test
    public void shouldGrow_whenEatBlueberry() {
        givenFl("☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼╘►○ ○ ○  ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents("[BLUEBERRY]");

        assertF("☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼╘═► ○ ○  ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼ ╘═►○ ○  ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents("[BLUEBERRY]");

        assertF("☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼ ╘══► ○  ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼  ╘══►○  ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents("[BLUEBERRY]");

        assertF("☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼  ╘═══►  ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼   ╘═══► ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼\n");
    }

    // тест смерти об стену
    @Test
    public void shouldDie_whenEatRock() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ╘═► ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╘═►☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents("[DIE]");

        assertEquals(false, hero().isAlive());
        assertEquals(true, hero().isActive());

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼   ╘═☻\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertEquals(false, hero().isAlive());
        assertEquals(true, hero().isActive());
    }

    // пока змея не активна, её направление "последнего движения" не меняется
    @Test
    public void shouldStopAndDontMove_whenNotActive() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ╘═► ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().setActive(false);
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ~═& ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().up();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ~═& ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().left();
        tick();

        hero().setActive(true);
        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╘═►☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // змея не может разворачиваться в противоположную сторону
    @Test
    public void shouldCantMovingBack_fromRightToLeft() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╘► ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldCantMovingBack_fromLeftToRight() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ◄╕ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ◄╕  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldCantMovingBack_fromUpToDown() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ▲  ☼\n" +
                "☼  ╙  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().down();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ▲  ☼\n" +
                "☼  ╙  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldCantMovingBack_fromDownToUp() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().up();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldTurnClockwise() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘═► ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().down();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ╘╗ ☼\n" +
                "☼   ▼ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");


        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼   ╓ ☼\n" +
                "☼   ║ ☼\n" +
                "☼   ▼ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼   ╓ ☼\n" +
                "☼  ◄╝ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ◄═╕ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().up();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ▲   ☼\n" +
                "☼ ╚╕  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ▲   ☼\n" +
                "☼ ║   ☼\n" +
                "☼ ╙   ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╔►  ☼\n" +
                "☼ ╙   ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘═► ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldTurnContrClockwise() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ╘═► ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().up();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼   ▲ ☼\n" +
                "☼  ╘╝ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼   ▲ ☼\n" +
                "☼   ║ ☼\n" +
                "☼   ╙ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ◄╗ ☼\n" +
                "☼   ╙ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ◄═╕ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().down();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╔╕  ☼\n" +
                "☼ ▼   ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╓   ☼\n" +
                "☼ ║   ☼\n" +
                "☼ ▼   ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ╓   ☼\n" +
                "☼ ╚►  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ╘═► ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // съедая бледную поганку, герой перелетает желуди
    @Test
    public void shouldFlyingOverAcorns_whenEatDeathCap() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼╘►© ●  ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals(false, hero().isFlying());

        tick();

        verifyAllEvents("[DEATH_CAP]");

        assertEquals(10, hero().flyingCount());
        assertEquals(true, hero().isFlying());

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ ╘♠ ●  ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertEquals(9, hero().flyingCount());
        assertEquals(true, hero().isFlying());

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ╘♠●  ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertEquals(8, hero().flyingCount());
        assertEquals(0, hero().furyCount());
        assertEquals(0, hero().acornsCount());
        assertEquals(true, hero().isFlying());
        assertEquals(true, hero().isAlive());

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ╘♠  ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();
        tick();

        assertEquals(6, hero().flyingCount());
        assertEquals(0, hero().acornsCount());
        assertEquals(true, hero().isFlying());
        assertEquals(true, hero().isAlive());

        // желудь остался на месте
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ●╘♠☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldDisableDeathCapEffect_when10Ticks() {
        shouldFlyingOverAcorns_whenEatDeathCap();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ●╘♠☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().up();
        tick();
        tick();
        tick();

        assertEquals(3, hero().flyingCount());
        assertEquals(true, hero().isFlying());

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼      ♠☼\n" +
                "☼      ╙☼\n" +
                "☼       ☼\n" +
                "☼    ●  ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().left();
        tick();
        tick();

        assertEquals(1, hero().flyingCount());
        assertEquals(true, hero().isFlying());

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼    ♠╕ ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ●  ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertEquals(0, hero().flyingCount());
        assertEquals(false, hero().isFlying());

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼   ◄╕  ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼    ●  ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
    }

    // съедая бледную поганку, герой может летать над собой
    @Test
    public void shouldFlyingOverMyself_whenEatDeathCap() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼╓    ☼\n" +
                "☼║    ☼\n" +
                "☼╚═══╗☼\n" +
                "☼  ©◄╝☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents("[DEATH_CAP]");

        assertEquals(9, hero().size());

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼╓    ☼\n" +
                "☼╚═══╗☼\n" +
                "☼  ♠═╝☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().up();
        tick();

        // змея не укоротилась
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼╘═♠═╗☼\n" +
                "☼  ╚═╝☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertEquals(9, hero().size());

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ♠  ☼\n" +
                "☼ ╘║═╗☼\n" +
                "☼  ╚═╝☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼  ♠  ☼\n" +
                "☼  ║  ☼\n" +
                "☼  ║═╗☼\n" +
                "☼  ╚═╝☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼ ♠╗  ☼\n" +
                "☼  ║  ☼\n" +
                "☼  ║╘╗☼\n" +
                "☼  ╚═╝☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // съедая пилюлю ярости, герой ест желуди без ущерба
    @Test
    public void shouldEatAcorns_whenEatFuryPill() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼╘►® ●  ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals(false, hero().isFury());

        tick();

        verifyAllEvents("[FURY]");

        assertEquals(10, hero().furyCount());
        assertEquals(true, hero().isFury());

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ ╘♥ ●  ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertEquals(9, hero().furyCount());
        assertEquals(0, hero().acornsCount());
        assertEquals(true, hero().isFury());

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼  ╘♥●  ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents("[ACORN]");

        assertEquals(8, hero().furyCount());
        assertEquals(0, hero().flyingCount());
        assertEquals(1, hero().acornsCount());
        assertEquals(true, hero().isFury());
        assertEquals(true, hero().isAlive());

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼   ╘♥  ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();
        tick();

        assertEquals(6, hero().furyCount());
        assertEquals(1, hero().acornsCount());
        assertEquals(true, hero().isFury());
        assertEquals(true, hero().isAlive());

        // желудь пропал
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼     ╘♥☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldDropAcorn_afterFury() {
        shouldEatAcorns_whenEatFuryPill();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼     ╘♥☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals(1, hero().acornsCount());

        hero().up();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼      ♥☼\n" +
                "☼      ╙☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");


        hero().act();
        tick();

        assertEquals(0, hero().acornsCount());

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼      ♥☼\n" +
                "☼      ╙☼\n" +
                "☼      ●☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼      ♥☼\n" +
                "☼      ╙☼\n" +
                "☼       ☼\n" +
                "☼      ●☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldDisableFuryPillEffect_when10Ticks() {
        shouldEatAcorns_whenEatFuryPill();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼     ╘♥☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().up();
        tick();
        tick();
        tick();

        assertEquals(3, hero().furyCount());
        assertEquals(true, hero().isFury());

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼      ♥☼\n" +
                "☼      ╙☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero().left();
        tick();
        tick();

        assertEquals(1, hero().furyCount());
        assertEquals(true, hero().isFury());

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼    ♥╕ ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertEquals(0, hero().furyCount());
        assertEquals(false, hero().isFury());

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼   ◄╕  ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldDropAcorn_afterFuryDisabled() {
        shouldDisableFuryPillEffect_when10Ticks();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼   ◄╕  ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertEquals(1, hero().acornsCount());

        hero().act();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄╕●  ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");


        assertEquals(0, hero().acornsCount());

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼ ◄╕ ●  ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
    }

    // разворот на 180 коротким героем невозможен
    @Test
    public void shouldTurn180_whenLengthIs2() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼╘►   ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().up(); // раньше так срабатывало
        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // разворот на 180 с откусыванием бороды невозможен
    @Test
    public void shouldTurn180_whenLengthIs5() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼╘═══►☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().down(); // раньше так срабатывало
        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ╘═══☻\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        verifyAllEvents("[DIE]");
    }

}
