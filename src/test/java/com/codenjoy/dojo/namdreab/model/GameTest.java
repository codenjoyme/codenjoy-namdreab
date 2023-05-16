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


import com.codenjoy.dojo.namdreab.services.GameSettings;
import org.junit.Test;

import static com.codenjoy.dojo.services.round.RoundSettings.Keys.ROUNDS_ENABLED;

public class GameTest extends AbstractGameTest {

    @Override
    protected GameSettings setupSettings(GameSettings settings) {
        return super.setupSettings(settings)
                .bool(ROUNDS_ENABLED, false);
    }

    // карта со своим героем
    @Test
    public void shouldHeroOnBoard_whenStartGame() {
        // given when
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // then
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
    public void shouldGetOutFromStartPoint_whenAddNewPlayer_saseSingleSpot() {
        // given
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼#     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        dice(0);
        givenPlayer();

        // then
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

    // старт героя из другого "стартового бокса"
    @Test
    public void shouldGetOutFromStartPoint_whenAddNewPlayer_caseAnotherSpot() {
        // given
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼#     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼#     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        dice(1); // select another spot
        givenPlayer();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼#     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "╘►     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼#     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼╘►    ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼#     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼#╘►   ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    // карта с черникой, желудями, бледными поганками, пилюлями ярости, деньгами
    @Test
    public void shouldDrawBoardWithElements_whenStartGame() {
        // given when
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼ $ ® ☼\n" +
                "☼  ●  ☼\n" +
                "☼  © ○☼\n" +
                "☼☼☼☼☼☼☼\n");

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼ $ ® ☼\n" +
                "☼  ●  ☼\n" +
                "☼  © ○☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldStrawberryEvent_whenEatIt() {
        // given
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

        // when
        tick();

        // then
        verifyAllEvents("[STRAWBERRY]");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldBlueberryEvent_whenEatIt() {
        // given
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

        // when
        tick();

        // then
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
    public void shouldDie_whenEatAcorn_caseLengthIsTooShort() {
        // given
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

        // when
        tick();

        // then
        verifyAllEvents("[DIE]");

        assertHeroStatus(
                "active:\n" +
                "hero(0)=true\n" +
                "alive\n" +
                "hero(0)=false");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ╘☻  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertHeroStatus(
                "active:\n" +
                "hero(0)=true\n" +
                "alive\n" +
                "hero(0)=false");
    }

    @Test
    public void shouldAlive_whenEatAcorn_caseLengthIsOk() {
        // given
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

        // when
        tick();

        // then
        verifyAllEvents("[ACORN]");

        assertHeroStatus(
                "active:\n" +
                "hero(0)=true\n" +
                "alive\n" +
                "hero(0)=true");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼╔═╕  ☼\n" +
                "☼╚═╗  ☼\n" +
                "☼  ║  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼╔╕   ☼\n" +
                "☼╚═╗  ☼\n" +
                "☼  ║  ☼\n" +
                "☼  ║  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertHeroStatus(
                "active:\n" +
                "hero(0)=true\n" +
                "alive\n" +
                "hero(0)=true");
    }

    // тест продолжения движения без дополнительных указаний
    @Test
    public void shouldMoveByInertia_whenNoCommand_caseDirectionFromLeftToRight() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼╘►   ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╘► ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼   ╘►☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldMoveByInertia_whenNoCommand_caseDirectionFromRightToLeft() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼   ◄╕☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ◄╕ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ◄╕  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼◄╕   ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldMoveByInertia_whenNoCommand_caseDirectionUpToDown() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldMoveByInertia_whenNoCommand_caseDirectionDownToUp() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ▲  ☼\n" +
                "☼  ╙  ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ▲  ☼\n" +
                "☼  ╙  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ▲  ☼\n" +
                "☼  ╙  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
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
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╘► ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().down();
        tick();

        // then
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
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
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
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╘► ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().up();
        tick();

        // then
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
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
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
        // given
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

        // when
        tick();

        // then
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

        // when
        tick();

        // then
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

        // when
        tick();

        // then
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

        // when
        tick();

        // then
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

        // when
        tick();

        // then
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

        // when
        tick();

        // then
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
        // given
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

        // when
        tick();

        // then
        verifyAllEvents("[DIE]");

        assertHeroStatus(
                "active:\n" +
                "hero(0)=true\n" +
                "alive\n" +
                "hero(0)=false");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼   ╘═☻\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertHeroStatus(
                "active:\n" +
                "hero(0)=true\n" +
                "alive\n" +
                "hero(0)=false");
    }

    // пока герой не активен, его направление движения не меняется
    @Test
    public void shouldStopAndDontMove_whenNotActive() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ╘═► ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().setActive(false);
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ~═& ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().up();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ~═& ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        hero().setActive(true);
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╘═►☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // герой не может разворачиваться в противоположную сторону
    @Test
    public void shouldCantMovingBack_caseMovingFromRightToLeft() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╘► ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldCantMovingBack_caseMovingFromLeftToRight() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ◄╕ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ◄╕  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldCantMovingBack_caseMovingFromUpToDown() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ▲  ☼\n" +
                "☼  ╙  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().down();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ▲  ☼\n" +
                "☼  ╙  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldCantMovingBack_caseMovingFromDownToUp() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().up();
        tick();

        // then
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
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘═► ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().down();
        tick();

        // then
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

        // when
        hero().left();
        tick();

        // then
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

        // when
        hero().up();
        tick();

        // then
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

        // when
        hero().right();
        tick();

        // then
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
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ╘═► ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().up();
        tick();

        // then
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

        // when
        hero().left();
        tick();

        // then
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

        // when
        hero().down();
        tick();

        // then
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

        // when
        hero().right();
        tick();

        // then
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

    // съедая бледную поганку, герой пропускает желуди отсавляя их не тронутыми
    @Test
    public void shouldFlyingOverAcorns_whenEatDeathCap() {
        // given
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼╘►© ●  ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // then
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

        // when
        tick();

        // then
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

        // when
        tick();

        // then
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

        // when
        tick();
        tick();

        // then
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
    public void shouldDisableDeathCapEffect_when10TicksHavePassed() {
        // given
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

        // when
        hero().up();
        tick();
        tick();
        tick();

        // then
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

        // when
        hero().left();
        tick();
        tick();

        // then
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

        // when
        tick();

        // then
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

    // съедая бледную поганку, герой может пролетать над своей бородой
    @Test
    public void shouldFlyingOverMyself_whenEatDeathCap() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼╓    ☼\n" +
                "☼║    ☼\n" +
                "☼╚═══╗☼\n" +
                "☼  ©◄╝☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        // then
        verifyAllEvents("[DEATH_CAP]");

        assertEquals(9, hero().size());

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼╓    ☼\n" +
                "☼╚═══╗☼\n" +
                "☼  ♠═╝☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().up();
        tick();

        // then
        // борода не укоротилась
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼╘═♠═╗☼\n" +
                "☼  ╚═╝☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        assertEquals(9, hero().size());

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ♠  ☼\n" +
                "☼ ╘║═╗☼\n" +
                "☼  ╚═╝☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼  ♠  ☼\n" +
                "☼  ║  ☼\n" +
                "☼  ║═╗☼\n" +
                "☼  ╚═╝☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼ ♠╗  ☼\n" +
                "☼  ║  ☼\n" +
                "☼  ║╘╗☼\n" +
                "☼  ╚═╝☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // съедая пилюлю ярости, герой ест желуди без ущерба для бороды
    @Test
    public void shouldEatAcornsWithoutAnyCuts_whenEatFlyAgaric() {
        // given
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

        // then
        verifyAllEvents("[FLY_AGARIC]");

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

        // when
        tick();

        // then
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

        // when
        tick();

        // then
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

        // when
        tick();
        tick();

        // then
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
    public void shouldDropAcornPickedUpAfterEatingFlyAgaric_whenHeroDoAct() {
        // given
        shouldEatAcornsWithoutAnyCuts_whenEatFlyAgaric();

        // then
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

        // when
        hero().act();
        tick();

        // then
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
    public void shouldDisableFlyAgaricEffect_when10TicksHavePassed() {
        // given
        shouldEatAcornsWithoutAnyCuts_whenEatFlyAgaric();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼     ╘♥☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // when
        hero().up();
        tick();
        tick();
        tick();

        // then
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

        // when
        hero().left();
        tick();
        tick();

        // then
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

        // when
        tick();

        // then
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
    public void shouldDropAcorn_afterFlyAgaricDisabled() {
        // given
        shouldDisableFlyAgaricEffect_when10TicksHavePassed();

        // then
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

        // when
        hero().act();
        tick();

        // then
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

        // when
        tick();

        // then
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
    public void shoulCantTurn180_whenLengthIs2() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼╘►   ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().up(); // раньше так срабатывало
        hero().left();
        tick();

        // then
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
    public void shouldCantTurn180_whenLengthIs5() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼╘═══►☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().down(); // раньше так срабатывало
        hero().left();
        tick();

        // then
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