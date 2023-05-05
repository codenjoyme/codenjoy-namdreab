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

import static com.codenjoy.dojo.services.round.RoundSettings.Keys.*;

public class MultiplayerTest extends AbstractGameTest {

    // проверяем что соперник отображается на карте
    @Test
    public void shouldHeroWithEnemyOnField_whenStart() {
        // given when
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼ ×>  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼ ×>  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ×>  ☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);
    }

    // спящие герои
    @Test
    public void shouldSleepingHero_whenSetNotActive() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼ ×>  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero(0).setActive(false);
        hero(1).setActive(false);

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ~&  ☼\n" +
                "☼     ☼\n" +
                "☼ *ø  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ *ø  ☼\n" +
                "☼     ☼\n" +
                "☼ ~&  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ~&  ☼\n" +
                "☼     ☼\n" +
                "☼ *ø  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);
    }

    @Test
    public void shouldNotSleepingHero_whenSetActive_caseGameIsMultiplayer() {
        // given
        settings().bool(ROUNDS_ENABLED, false);

        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼ ×>  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero(0).setActive(true);
        hero(1).setActive(true);

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼ ×>  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ×>  ☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ╘► ☼\n" +
                "☼     ☼\n" +
                "☼  ×> ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);
    }

    // проверяем что оба героя погибают, когда врезаются в равного соперника
    @Test
    public void shouldDie_whenHeadCrashToOtherHero_caseBothDie() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼ ×>  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero(0).down();
        hero(1).up();
        tick();

        // then
        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [DIE]\n");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ☻  ☼\n" +
                "☼  ¤  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  æ  ☼\n" +
                "☼  ☺  ☼\n" +
                "☼  ╙  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);
    }

    // проверяем что меньший герой умирает, когда врезаются голова к голове
    // больший герой уменьшается на размер меньшего
    // переставая уменьшаться на следующий ход
    @Test
    public void shouldDie_whenHeadCrashToOtherHero_caseShortestHeroDie() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼   ╘►☼\n" +
                "☼     ☼\n" +
                "☼ ×──>☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼   ╘►☼\n" +
                "☼     ☼\n" +
                "☼ ×──>☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼   ×>☼\n" +
                "☼     ☼\n" +
                "☼ ╘══►☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);

        // when
        hero(0).down();
        hero(1).up();
        tick();

        // then
        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [EAT[2], WIN]\n");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼    ╓☼\n" +
                "☼    ☻☼\n" +
                "☼  ×─┘☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼    æ☼\n" +
                "☼    ☺☼\n" +
                "☼  ╘═╝☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼    ˄☼\n" +
                "☼    ¤☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼    ▲☼\n" +
                "☼    ╙☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼    ˄☼\n" +
                "☼    ¤☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);
    }

    // проверяем что герой умирает, когда врезается в "шею" другого героя
    @Test
    public void shouldDie_whenNeckCrashToOtherHero_caseHeroAtack_caseBothDie() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼╘═►  ☼\n" +
                "☼ ×>  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼╘═►  ☼\n" +
                "☼ ×>  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼×─>  ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);

        // when
        hero(1).up();
        tick();

        // then
        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [DIE]\n");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘☺☻ ☼\n" +
                "☼  ¤  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ×☻☺ ☼\n" +
                "☼  ╙  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);
    }

    // такой же тест, но врезается игрок в противника
    // (последовательность героев в списке может оказывать значение на результат)
    @Test
    public void shouldDie_whenNeckCrashToOtherHero_caseEnemyAtack_caseBothDie() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼×─>  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼×─>  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ×>  ☼\n" +
                "☼╘═►  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);

        // when
        hero(0).down();
        tick();

        // then
        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [DIE]\n");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼ ×☻☺ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  æ  ☼\n" +
                "☼ ╘☺☻ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);
    }

    // проверяем что герой умирает, когда врезается в хвост бороды другого игрока
    @Test
    public void shouldDie_whenBeardCrashToOtherHero_caseEnemyDie() {
        // given
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ╘═►  ☼\n" +
                "☼×─>   ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ╘═►  ☼\n" +
                "☼×─>   ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ×─>  ☼\n" +
                "☼╘═►   ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        hero(1).up();
        tick();

        // then
        verifyAllEvents(
                "listener(0) => [EAT[3], WIN]\n" +
                "listener(1) => [DIE]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ☺═► ☼\n" +
                "☼ ×┘   ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ☻─> ☼\n" +
                "☼ ╘╝   ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼   ╘═►☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼   ×─>☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void shouldDie_whenNeckCrashToOtherHero_caseSameLength_caseBothDie() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼╘═►  ☼\n" +
                "☼×─>  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼╘═►  ☼\n" +
                "☼×─>  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼×─>  ☼\n" +
                "☼╘═►  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);

        // when
        hero(1).up();
        tick();

        // then
        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [DIE]\n");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘☺☻ ☼\n" +
                "☼ ×┘  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ×☻☺ ☼\n" +
                "☼ ╘╝  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);
    }

    // такой же тест, но врезается игрок в противника
    @Test
    public void shouldDie_whenNeckCrashToOtherHero_caseAttackingHeroDie() {
        // given
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╘═►   ☼\n" +
                "☼ ×─>  ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╘═►   ☼\n" +
                "☼ ×─>  ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼×─>   ☼\n" +
                "☼ ╘═►  ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        hero(0).down();
        tick();

        // then
        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [EAT[3], WIN]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ╘╗   ☼\n" +
                "☼  ☻─> ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ×┐   ☼\n" +
                "☼  ☺═► ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼   ×─>☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼   ╘═►☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);
    }

    // а тут лобовое столкновение
    @Test
    public void shouldDie_whenNeckCrashToOtherHero_caseSameLength_caseEnemyAtack_caseBothDie() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼╘═►  ☼\n" +
                "☼×─>  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼╘═►  ☼\n" +
                "☼×─>  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼×─>  ☼\n" +
                "☼╘═►  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);

        // when
        hero(0).down();
        tick();

        // then
        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [DIE]\n");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘╗  ☼\n" +
                "☼ ×☻☺ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ×┐  ☼\n" +
                "☼ ╘☺☻ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);
    }

    // TODO продолжить дальше улучшать

    // в полёте герои не вредят друг-другу (летишь сам)
    @Test
    public void shouldFlyOverEnemy_whenEatDeathCap() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼╘►©  ☼\n" +
                "☼     ☼\n" +
                "☼×>   ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents(
                "listener(0) => [DEATH_CAP]\n");

        // when
        hero(0).down();
        hero(1).up();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ♠  ☼\n" +
                "☼  ¤  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ˄  ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ♠  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼  ˄  ☼\n" +
                "☼  ¤  ☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ♠  ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);
    }

    // в полёте герои не вредят друг-другу (летишь сам)
    @Test
    public void shouldFlyOverHero_whenEatDeathCap() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼╘►   ☼\n" +
                "☼     ☼\n" +
                "☼×>©  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents(
                "listener(1) => [DEATH_CAP]\n");

        // when
        hero(0).down();
        hero(1).up();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ♦  ☼\n" +
                "☼  ¤  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ♦  ☼\n" +
                "☼  ¤  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼  ♦  ☼\n" +
                "☼  ¤  ☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ▼  ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);
    }

    // в случае ярости, герой может съесть другого
    @Test
    public void shouldEatEnemy_whenFurryPill_caseHeadAttack() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘►® ☼\n" +
                "☼     ☼\n" +
                "☼ ×>○○☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();
        tick();

        verifyAllEvents(
                "listener(0) => [FURY]\n" +
                "listener(1) => [BLUEBERRY, BLUEBERRY]\n");

        // when
        hero(0).down();
        hero(1).up();
        tick();

        // then
        verifyAllEvents(
                "listener(0) => [EAT[4], WIN]\n" +
                "listener(1) => [DIE]\n");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼    ╓☼\n" +
                "☼    ☺☼\n" +
                "☼  ×─┘☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼    ╓☼\n" +
                "☼    ♥☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);
    }

    // в случае ярости, можно откусить кусок бороды соперника
    // когда в игрока врезается противник
    @Test
    public void shouldEatEnemy_whenFurryPill_caseBeardCrashByHero() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼╘►○○ ☼\n" +
                "☼     ☼\n" +
                "☼×>®  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents(
                "listener(0) => [BLUEBERRY]\n" +
                "listener(1) => [FURY]\n");

        hero(1).up();
        tick();

        verifyAllEvents(
                "listener(0) => [BLUEBERRY]\n");

        // when
        tick();

        // then
        verifyAllEvents(
                "listener(1) => [EAT[2]]\n");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ♣╘►☼\n" +
                "☼  ¤  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);
    }

    // в случае ярости, можно откусить кусок бороды соперника
    // такой же тест, но врезается игрок в противника
    // (последовательность героев в списке может оказывать значение на результат)
    @Test
    public void shouldEatEnemy_whenFurryPill_caseBeardCrashByEnemy() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼╘►®  ☼\n" +
                "☼     ☼\n" +
                "☼×>○  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents(
                "listener(0) => [FURY]\n" +
                "listener(1) => [BLUEBERRY]\n");

        // when
        hero(0).down();
        tick();
        tick();

        // then
        verifyAllEvents(
                "listener(0) => [EAT[1]]\n");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╓  ☼\n" +
                "☼  ♥×>☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);
    }

    // если твоЙ герой остался на поле сам, а все противники погибли - тебе WIN
    @Test
    public void shouldWin_whenOneOnBoard_caseEnemyDie() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼╘►   ☼\n" +
                "☼     ☼\n" +
                "☼  ×> ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼   ×>☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ×>  ☼\n" +
                "☼     ☼\n" +
                "☼   ╘►☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
        verifyAllEvents(
                "listener(0) => [WIN]\n" +
                "listener(1) => [DIE]\n");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ╘► ☼\n" +
                "☼     ☼\n" +
                "☼    ×☺\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ×> ☼\n" +
                "☼     ☼\n" +
                "☼    ╘☻\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼   ╘►☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼   ×>☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);
    }

    // если твой герой остался на поле сам после того,
    // как противник удалился (не через die) - тебе WIN
    @Test
    public void shouldWin_whenOneOnBoard_caseEnemyLeaveRoom() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼╘►   ☼\n" +
                "☼     ☼\n" +
                "☼×>   ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼ ×>  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ×>  ☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);

        // when
        remove(0);
        tick();

        // then
        verifyAllEvents("[WIN]");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ×> ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ╘► ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼   ×>☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼   ╘►☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 1);
    }

    // герой не стартует сразу если стоит таймер
    @Test
    public void shouldWaitTillTimerThanStart() {
        // given
        settings().integer(ROUNDS_TIME_BEFORE_START, 4);

        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼╘►   ☼\n" +
                "☼     ☼\n" +
                "☼×>   ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // ждем 4 тика

        // when
        tick();

        // then
        verifyAllEvents(
                "listener(0) => [[...3...]]\n" +
                "listener(1) => [[...3...]]\n");

        // when
        tick();

        // then
        verifyAllEvents(
                "listener(0) => [[..2..]]\n" +
                "listener(1) => [[..2..]]\n");

        // when
        tick();

        // then
        verifyAllEvents(
                "listener(0) => [[.1.]]\n" +
                "listener(1) => [[.1.]]\n");

        // when
        tick();

        // then
        verifyAllEvents(
                "listener(0) => [START, [Round 1]]\n" +
                "listener(1) => [START, [Round 1]]\n");

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼╘►   ☼\n" +
                "☼     ☼\n" +
                "☼×>   ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ╘►  ☼\n" +
                "☼     ☼\n" +
                "☼ ×>  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ╘► ☼\n" +
                "☼     ☼\n" +
                "☼  ×> ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼   ╘►☼\n" +
                "☼     ☼\n" +
                "☼   ×>☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n", 0);
    }

    // если один герой погибает, стартует новый раунд
    @Test
    public void shouldStartNewGame_whenGameOver() {
        settings().integer(ROUNDS_TIME_BEFORE_START, 1)
                .integer(ROUNDS_PER_MATCH, 3);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼#   ╘►☼\n" +
                "☼#×>   ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertEquals(true, player(0).isAlive());
        assertEquals(true, player(0).isActive());

        assertEquals(true, player(1).isAlive());
        assertEquals(true, player(1).isActive());

        // ждем перехода на первый уровнь
        tick();

        assertEquals(true, player(0).isAlive());
        assertEquals(true, player(0).isActive());

        assertEquals(true, player(1).isAlive());
        assertEquals(true, player(1).isActive());

        verifyAllEvents(
                "listener(0) => [START, [Round 1]]\n" +
                "listener(1) => [START, [Round 1]]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼#   ╘►☼\n" +
                "☼#×>   ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼#    ╘☻\n" +
                "☼# ×>  ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [WIN]\n");

        assertEquals(false, player(0).isAlive());
        assertEquals(true, player(0).isActive());

        assertEquals(true, player(1).isAlive());
        assertEquals(true, player(1).isActive());

        dice(0); // стартовый спот для героя
        field().newGame(player(0)); // это делает автоматом фреймворк потому что player(0).!isAlive()

        assertEquals(true, player(0).isAlive());
        assertEquals(false, player(0).isActive());

        assertEquals(true, player(1).isAlive());
        assertEquals(true, player(1).isActive());

        tick();

        assertEquals(true, player(0).isAlive());
        assertEquals(false, player(0).isActive());

        assertEquals(true, player(1).isAlive());
        assertEquals(true, player(1).isActive());

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "~&     ☼\n" +
                "☼#  ×> ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        // последний победный тик героя!
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "~&     ☼\n" +
                "*ø     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        // ждем перехода на второй уровень
        tick();

        assertEquals(true, player(0).isAlive());
        assertEquals(true, player(0).isActive());

        assertEquals(true, player(1).isAlive());
        assertEquals(true, player(1).isActive());

        verifyAllEvents(
                "listener(0) => [START, [Round 2]]\n" +
                "listener(1) => [START, [Round 2]]\n");

        assertEquals(true, player(0).isActive());
        assertEquals(true, player(1).isActive());

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "╘►     ☼\n" +
                "×>     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼#    ╘☻\n" +
                "☼#    ×☺\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [DIE]\n");

        assertEquals(false, player(0).isAlive());
        assertEquals(true, player(0).isActive());

        assertEquals(false, player(1).isAlive());
        assertEquals(true, player(1).isActive());

        field().newGame(player(0));  // это делает автоматом фреймворк потому что player(0).!isAlive()
        field().newGame(player(1)); // это делает автоматом фреймворк потому что player(1).!isAlive()

        assertEquals(true, player(0).isAlive());
        assertEquals(false, player(0).isActive());

        assertEquals(true, player(1).isAlive());
        assertEquals(false, player(1).isActive());

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "~&     ☼\n" +
                "*ø     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        // последний тик победителя тут неуместен, все погибли
        // tick();

        // ждем перехода на третий уровень
        tick();

        assertEquals(true, player(0).isAlive());
        assertEquals(true, player(0).isActive());

        assertEquals(true, player(1).isAlive());
        assertEquals(true, player(1).isActive());

        verifyAllEvents(
                "listener(0) => [START, [Round 3]]\n" +
                "listener(1) => [START, [Round 3]]\n");

        assertEquals(true, player(0).isActive());
        assertEquals(true, player(1).isActive());

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "╘►     ☼\n" +
                "×>     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        tick();
        tick();
        tick();
        tick();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼#   ╘►☼\n" +
                "☼#   ×>☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertEquals(true, player(0).isAlive());
        assertEquals(true, player(0).isActive());

        assertEquals(true, player(1).isAlive());
        assertEquals(true, player(1).isActive());

        assertEquals(false, player(0).shouldLeave());
        assertEquals(false, player(1).shouldLeave());

        tick();

        assertEquals(true, player(0).shouldLeave());
        assertEquals(true, player(1).shouldLeave());

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼#    ╘☻\n" +
                "☼#    ×☺\n" +
                "☼☼     ☼\n" +
                "☼☼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [DIE]\n");

        assertEquals(false, player(0).isAlive());
        assertEquals(true, player(0).isActive());

        assertEquals(false, player(1).isAlive());
        assertEquals(true, player(1).isActive());

        field().remove(player(0));  // это делает автоматом фреймворк потому что player(0).shouldLeave()
        field().remove(player(1)); // это делает автоматом фреймворк потому что player(1).shouldLeave()

    }

    // Лобовое столкновение змеек - одна под fury
    @Test
    public void shouldDie_whenHeadAttack() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╘═►   ☼\n" +
                "☼    ® ☼\n" +
                "☼    ˄ ☼\n" +
                "☼    ¤ ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╘═►   ☼\n" +
                "☼    ® ☼\n" +
                "☼    ˄ ☼\n" +
                "☼    ¤ ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼×─>   ☼\n" +
                "☼    ® ☼\n" +
                "☼    ▲ ☼\n" +
                "☼    ╙ ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();
        verifyAllEvents("listener(1) => [FURY]\n");

        tick();

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [EAT[3], WIN]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ╘═☻ ☼\n" +
                "☼    ¤ ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ×─☺ ☼\n" +
                "☼    ╙ ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼    ♣ ☼\n" +
                "☼    ¤ ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼    ♥ ☼\n" +
                "☼    ╙ ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents("");
    }

    // Герой с одной только головой не живет
    // идея в том, чтоб под таблеткой ярости откусить
    // конец хвоста, оставив только голову
    @Test
    public void shouldDie_whenEatAllTailOtherHero_with1Length() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╘═►   ☼\n" +
                "☼   ®  ☼\n" +
                "☼   ˄  ☼\n" +
                "☼   ¤  ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╘═►   ☼\n" +
                "☼   ®  ☼\n" +
                "☼   ˄  ☼\n" +
                "☼   ¤  ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼×─>   ☼\n" +
                "☼   ®  ☼\n" +
                "☼   ▲  ☼\n" +
                "☼   ╙  ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();
        verifyAllEvents(
                "listener(1) => [FURY]\n");

        tick();

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [EAT[3], WIN]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ╘♣☻ ☼\n" +
                "☼   ¤  ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ×♥☺ ☼\n" +
                "☼   ╙  ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ♣  ☼\n" +
                "☼   ¤  ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ♥  ☼\n" +
                "☼   ╙  ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents("");
    }

    @Test
    public void shouldCutTail_whenFury() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╘═►   ☼\n" +
                "☼  ®   ☼\n" +
                "☼  ˄   ☼\n" +
                "☼  ¤   ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╘═►   ☼\n" +
                "☼  ®   ☼\n" +
                "☼  ˄   ☼\n" +
                "☼  ¤   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼×─>   ☼\n" +
                "☼  ®   ☼\n" +
                "☼  ▲   ☼\n" +
                "☼  ╙   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        verifyAllEvents(
                "listener(1) => [FURY]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ╘═►  ☼\n" +
                "☼  ♣   ☼\n" +
                "☼  ¤   ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ×─>  ☼\n" +
                "☼  ♥   ☼\n" +
                "☼  ╙   ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);


        tick();

        verifyAllEvents(
                "listener(1) => [EAT[1]]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ♣╘► ☼\n" +
                "☼  ¤   ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ♥×> ☼\n" +
                "☼  ╙   ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  ♣   ☼\n" +
                "☼  ¤ ╘►☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  ♥   ☼\n" +
                "☼  ╙ ×>☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents("");
    }

    @Test
    public void shouldCutLongTail_whenFury() {
        givenFl("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼╔══════╗☼\n" +
                "☼║╔════╕║☼\n" +
                "☼║╚═════╝☼\n" +
                "☼╚═══►   ☼\n" +
                "☼    ®   ☼\n" +
                "☼    ˄   ☼\n" +
                "☼    ¤   ☼\n" +
                "☼        ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼╔══════╗☼\n" +
                "☼║╔════╕║☼\n" +
                "☼║╚═════╝☼\n" +
                "☼╚═══►   ☼\n" +
                "☼    ®   ☼\n" +
                "☼    ˄   ☼\n" +
                "☼    ¤   ☼\n" +
                "☼        ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼┌──────┐☼\n" +
                "☼│┌────ö│☼\n" +
                "☼│└─────┘☼\n" +
                "☼└───>   ☼\n" +
                "☼    ®   ☼\n" +
                "☼    ▲   ☼\n" +
                "☼    ╙   ☼\n" +
                "☼        ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 1);

        tick();

        verifyAllEvents(
                "listener(1) => [FURY]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼╔══════╗☼\n" +
                "☼║╔═══╕ ║☼\n" +
                "☼║╚═════╝☼\n" +
                "☼╚════►  ☼\n" +
                "☼    ♣   ☼\n" +
                "☼    ¤   ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼┌──────┐☼\n" +
                "☼│┌───ö │☼\n" +
                "☼│└─────┘☼\n" +
                "☼└────>  ☼\n" +
                "☼    ♥   ☼\n" +
                "☼    ╙   ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 1);


        tick();

        verifyAllEvents(
                "listener(1) => [EAT[27]]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼    ♣╘► ☼\n" +
                "☼    ¤   ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼    ♥×> ☼\n" +
                "☼    ╙   ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼    ♣   ☼\n" +
                "☼    ¤ ╘►☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼    ♥   ☼\n" +
                "☼    ╙ ×>☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents("");
    }

    // с помощью этой команды можно самоуничтожиться
    // при этом на месте героя появится много ягод черники :)
    @Test
    public void shouldDieAndLeaveBlueberries_whenAct0() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼╔═══╕ ☼\n" +
                "☼║     ☼\n" +
                "☼╚═══╗ ☼\n" +
                "☼  ©◄╝ ☼\n" +
                "☼      ☼\n" +
                "☼×>    ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();
        verifyAllEvents(
                "listener(0) => [DEATH_CAP]\n");

        hero(0).up();
        tick();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼╔╕    ☼\n" +
                "☼║ ♠   ☼\n" +
                "☼╚═║═╗ ☼\n" +
                "☼  ╚═╝ ☼\n" +
                "☼      ☼\n" +
                "☼   ×> ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼┌ö    ☼\n" +
                "☼│ ♦   ☼\n" +
                "☼└─│─┐ ☼\n" +
                "☼  └─┘ ☼\n" +
                "☼      ☼\n" +
                "☼   ╘► ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        hero(0).act(0);
        tick();

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [WIN]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼○○    ☼\n" +
                "☼○ ○   ☼\n" +
                "☼○○○○○ ☼\n" +
                "☼  ○○○ ☼\n" +
                "☼      ☼\n" +
                "☼    ×>☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼○○    ☼\n" +
                "☼○ ○   ☼\n" +
                "☼○○○○○ ☼\n" +
                "☼  ○○○ ☼\n" +
                "☼      ☼\n" +
                "☼    ╘►☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents("");
    }

    // Кейз когда герои сталкиваются головами
    @Test
    public void shouldCase6() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼┌─┐   ☼\n" +
                "☼│ ¤   ☼\n" +
                "☼│     ☼\n" +
                "☼˅     ☼\n" +
                "☼◄══╕  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero(1).right();
        hero(0).up();
        tick();

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [EAT[4], WIN]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼┌─ö   ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☼☻>    ☼\n" +
                "☼╚═╕   ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼╔═╕   ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☼☺►    ☼\n" +
                "☼└─ö   ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼×─>   ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╘═►   ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents("");
    }

    @Test
    public void shouldCase6_2() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼╔═╗   ☼\n" +
                "☼║ ╙   ☼\n" +
                "☼║     ☼\n" +
                "☼▼     ☼\n" +
                "☼<──ö  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero(0).right();
        hero(1).up();
        tick();

        verifyAllEvents(
                "listener(0) => [EAT[4], WIN]\n" +
                "listener(1) => [DIE]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼╔═╕   ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☼☺►    ☼\n" +
                "☼└─ö   ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼┌─ö   ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☼☻>    ☼\n" +
                "☼╚═╕   ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╘═►   ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼×─>   ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents("");
    }

    @Test
    public void shouldCase6_3() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☼˅     ☼\n" +
                "☼◄════╕☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero(1).right();
        hero(0).up();
        tick();

        verifyAllEvents(
                "listener(0) => [EAT[4], WIN]\n" +
                "listener(1) => [DIE]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼│     ☼\n" +
                "☼└☺    ☼\n" +
                "☼╚═══╕ ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼║     ☼\n" +
                "☼╚☻    ☼\n" +
                "☼└───ö ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼▲     ☼\n" +
                "☼╙     ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼˄     ☼\n" +
                "☼¤     ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents("");
    }

    @Test
    public void shouldCase7() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼┌─┐   ☼\n" +
                "☼│ ¤   ☼\n" +
                "☼│     ☼\n" +
                "☼˅     ☼\n" +
                "☼◄══╕  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero(0).up();
        tick();

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [EAT[4], WIN]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼┌─ö   ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☼☻     ☼\n" +
                "☼╚═╕   ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼╔═╕   ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☼☺     ☼\n" +
                "☼└─ö   ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼│     ☼\n" +
                "☼˅     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼║     ☼\n" +
                "☼▼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents("");
    }

    // Когда два героя сталкиваются на объекте, сначала должно
    // быть обработано столкновение, а уж
    // затем съедение объекта выжившим героем (если такой есть).
    @Test
    public void firstFightThenEatFury() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼┌─┐   ☼\n" +
                "☼│ ¤   ☼\n" +
                "☼│     ☼\n" +
                "☼˅     ☼\n" +
                "☼®◄═══╕☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [EAT[5], FURY, WIN]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼┌─ö   ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☼☻═══╕ ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼╔═╕   ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☼☺───ö ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼♣     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼♥     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        assertEquals(0, field().furyPills().size());
        verifyAllEvents("");
    }

    @Test
    // Предыдущий тест в инвертированных ролях. Эти два теста вместе показывают что порядок игроков
    // в списке List<Player> больше не влияет на результат таких столкновений.
    public void firstFightThenEatFuryInverted() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼╔═╗   ☼\n" +
                "☼║ ╙   ☼\n" +
                "☼║     ☼\n" +
                "☼▼     ☼\n" +
                "☼®<───ö☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents(
                "listener(0) => [EAT[5], FURY, WIN]\n" +
                "listener(1) => [DIE]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼╔═╕   ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☼☺───ö ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼┌─ö   ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☼☻═══╕ ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼♥     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼♣     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        assertEquals(0, field().furyPills().size());
        verifyAllEvents("");
    }

    // Тестируем что обработка столкновений происходите до съедения черники.
    // Если бы черника "съелась" коротким героем до обработки столкновения,
    // оба героя умерли бы.
    @Test
    public void firstFightThenEatBlueberry() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼┌─┐   ☼\n" +
                "☼│ ¤   ☼\n" +
                "☼│     ☼\n" +
                "☼˅     ☼\n" +
                "☼○◄═══╕☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [EAT[5], BLUEBERRY, WIN]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼┌─┐   ☼\n" +
                "☼│ ¤   ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☼☻═══╕ ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼╔═╗   ☼\n" +
                "☼║ ╙   ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☼☺───ö ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼│     ☼\n" +
                "☼˅     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼║     ☼\n" +
                "☼▼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        assertEquals(0, field().blueberries().size());
        verifyAllEvents("");
    }

    // Тест зеркальный предыдущему чтобы показать что порядок игроков
    // на сервере не влияет на исход столкновения.
    @Test
    public void firstFightThenEatBlueberryInverted() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼╔═╗   ☼\n" +
                "☼║ ╙   ☼\n" +
                "☼║     ☼\n" +
                "☼▼     ☼\n" +
                "☼○<───ö☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents(
                "listener(0) => [EAT[5], BLUEBERRY, WIN]\n" +
                "listener(1) => [DIE]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼╔═╗   ☼\n" +
                "☼║ ╙   ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☼☺───ö ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼┌─┐   ☼\n" +
                "☼│ ¤   ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☼☻═══╕ ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼║     ☼\n" +
                "☼▼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼│     ☼\n" +
                "☼˅     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        assertEquals(0, field().blueberries().size());
        verifyAllEvents("");
    }

    // Тестируем что обработка столкновений происходите до съедения желудя.
    // Если бы желудь "съелся" коротким героем до обработки
    // столкновения, он умер бы не повредив длинного.
    @Test
    public void firstFightThenEatAcorn() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼┌─┐   ☼\n" +
                "☼│ ¤   ☼\n" +
                "☼│     ☼\n" +
                "☼˅     ☼\n" +
                "☼●◄╕   ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [EAT[2], ACORN, WIN]\n");

        // несмотря на то что на сервере столкновение обрабатывается до съедения желудя,
        // съедение желудя приводит к мгновенной утрате длинны (на 3),
        // тогда как убийство соперника приводит к утрате длинны только на следующий тик.
        // лично я бы апдейтил длинну выжившего героя тоже сразу и не показывал бы части умерших героев.
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☼☻╕    ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☼☺ö    ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼˅     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼▼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        assertEquals("[]", field().acorns().toString());
        verifyAllEvents("");
    }

    // Тест зеркальный предыдущему, показывает что порядок игроков
    // на сервере не влияет на исход столкновения.
    @Test
    public void firstFightThenEatAcornInverted() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼╔═╗   ☼\n" +
                "☼║ ╙   ☼\n" +
                "☼║     ☼\n" +
                "☼▼     ☼\n" +
                "☼●<ö   ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents(
                "listener(0) => [EAT[2], ACORN, WIN]\n" +
                "listener(1) => [DIE]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☼☺ö    ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☼☻╕    ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼▼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼˅     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        assertEquals("[]", field().acorns().toString());
        verifyAllEvents("");
    }

    // Тестируем случай когда один герой с Fury идет на клетку,
    // где был хвост второго в момент когда второй кушает чернику.
    @Test
    public void eatTailThatGrows_Fury() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  ╓   ☼\n" +
                "☼˄®║   ☼\n" +
                "☼¤ ▼ ○ ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero(1).right();
        hero(0).right();
        tick();

        verifyAllEvents(
                "listener(1) => [FURY]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼×♣╓   ☼\n" +
                "☼  ╚►○ ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╘♥æ   ☼\n" +
                "☼  └>○ ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        verifyAllEvents(
                "listener(0) => [BLUEBERRY]\n" +
                "listener(1) => [EAT[1]]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ×♣   ☼\n" +
                "☼  ╘═► ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ╘♥   ☼\n" +
                "☼  ×─> ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ×♣  ☼\n" +
                "☼   ╘═►☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ╘♥  ☼\n" +
                "☼   ×─>☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        assertEquals(0, field().blueberries().size());
        verifyAllEvents("");
    }

    // Тестируем врезание в отросший хвост.
    @Test
    public void eatTailThatGrows() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼×>╓   ☼\n" +
                "☼  ╚►○ ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents(
                "listener(0) => [BLUEBERRY, EAT[2], WIN]\n" +
                "listener(1) => [DIE]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ×☺   ☼\n" +
                "☼  ╚═► ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ╘☻   ☼\n" +
                "☼  └─> ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ╘══►☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ×──>☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        assertEquals(0, field().blueberries().size());
        verifyAllEvents("");
    }

    @Test
    public void fightCase1() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼╔═╗   ☼\n" +
                "☼║ ╙   ☼\n" +
                "☼║     ☼\n" +
                "☼▼     ☼\n" +
                "☼ <───ö☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼╔═╕   ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☼☺───ö ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼┌─ö   ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☼☻═══╕ ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [EAT[5], WIN]\n" +
                "listener(1) => [DIE]\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼▼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼˅     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void fightCase2() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼╔═╗   ☼\n" +
                "☼║ ╙   ☼\n" +
                "☼║     ☼\n" +
                "☼▼     ☼\n" +
                "☼<────ö☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼╔═╕   ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☺☻───ö ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼┌─ö   ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☻☺═══╕ ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [DIE]\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void fightCase3() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼╔═╗   ☼\n" +
                "☼║ ╙   ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☼▼<───ö☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼╔═╕   ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☼☺───ö ☼\n" +
                "☼▼     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼┌─ö   ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☼☻═══╕ ☼\n" +
                "☼˅     ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [EAT[5], WIN]\n" +
                "listener(1) => [DIE]\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼║     ☼\n" +
                "☼☻☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼│     ☼\n" +
                "☼☺☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [DIE]\n");
    }

    @Test
    public void fightCase4() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼╔═╗    ☼\n" +
                "☼║ ╙    ☼\n" +
                "☼║      ☼\n" +
                "☼║      ☼\n" +
                "☼║<───ö ☼\n" +
                "☼▼      ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼╔═╕    ☼\n" +
                "☼║      ☼\n" +
                "☼║      ☼\n" +
                "☼║      ☼\n" +
                "☼☺───ö  ☼\n" +
                "☼║      ☼\n" +
                "☼▼      ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼┌─ö    ☼\n" +
                "☼│      ☼\n" +
                "☼│      ☼\n" +
                "☼│      ☼\n" +
                "☼☻═══╕  ☼\n" +
                "☼│      ☼\n" +
                "☼˅      ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [EAT[5], WIN]\n" +
                "listener(1) => [DIE]\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼╔╕     ☼\n" +
                "☼║      ☼\n" +
                "☼║      ☼\n" +
                "☼║      ☼\n" +
                "☼║      ☼\n" +
                "☼║      ☼\n" +
                "☼║      ☼\n" +
                "☼☻☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼┌ö     ☼\n" +
                "☼│      ☼\n" +
                "☼│      ☼\n" +
                "☼│      ☼\n" +
                "☼│      ☼\n" +
                "☼│      ☼\n" +
                "☼│      ☼\n" +
                "☼☺☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [DIE]\n");
    }

    // если тиков для победы недостаточно, то WIN ты не получишь
    @Test
    public void shouldNoWin_whenIsNotEnoughTicksForWin() {
        settings().integer(ROUNDS_MIN_TICKS_FOR_WIN, 10);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼┌─┐   ☼\n" +
                "☼│ ¤   ☼\n" +
                "☼│     ☼\n" +
                "☼˅     ☼\n" +
                "☼◄══╕  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero(1).right();
        hero(0).up();
        tick();

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [EAT[4]]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼┌─ö   ☼\n" +
                "☼│     ☼\n" +
                "☼│     ☼\n" +
                "☼☻>    ☼\n" +
                "☼╚═╕   ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼╔═╕   ☼\n" +
                "☼║     ☼\n" +
                "☼║     ☼\n" +
                "☼☺►    ☼\n" +
                "☼└─ö   ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼×─>   ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╘═►   ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents("");
    }

    // с бледной поганкой и яростью я не откусываю хвост врага
    @Test
    public void shouldCase12_furyPlusFlying() {
        givenFl("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼      ○                    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼     ☼☼☼☼☼         ○       ☼\n" +
                "☼☼     ☼                     ☼\n" +
                "☼#     ☼☼☼        ☼☼☼☼#      ☼\n" +
                "☼☼     ☼          ☼   ☼  ●   ☼\n" +
                "☼☼     ☼☼☼☼#      ☼☼☼☼#      ☼\n" +
                "☼☼                ☼   ●      ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼            ┌──>           ☼\n" +
                "☼#○           ¤♥  ○          ☼\n" +
                "☼☼         ©  ╔╝             ☼\n" +
                "☼☼        ☼☼☼ ╚═╗            ☼\n" +
                "☼☼       ☼  ☼   ╚══╕         ☼\n" +
                "☼☼      ☼☼☼☼#     ☼☼   ☼#    ☼\n" +
                "☼☼      ☼   ☼     ☼ ☼ ☼ ☼    ☼\n" +
                "☼#      ☼   ☼     ☼  ☼  ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                ☼   ○ ☼    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼                  ®    ©   ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n");

        hero(0).eatDeathCap(); // добрали таблетку полета

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼      ○                    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼     ☼☼☼☼☼         ○       ☼\n" +
                "☼☼     ☼                     ☼\n" +
                "☼#     ☼☼☼        ☼☼☼☼#      ☼\n" +
                "☼☼     ☼          ☼   ☼  ●   ☼\n" +
                "☼☼     ☼☼☼☼#      ☼☼☼☼#      ☼\n" +
                "☼☼                ☼   ●      ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼            ×♠──>          ☼\n" +
                "☼#○            ║  ○          ☼\n" +
                "☼☼         ©  ╔╝             ☼\n" +
                "☼☼        ☼☼☼ ╚═╗            ☼\n" +
                "☼☼       ☼  ☼   ╚═╕          ☼\n" +
                "☼☼      ☼☼☼☼#     ☼☼   ☼#    ☼\n" +
                "☼☼      ☼   ☼     ☼ ☼ ☼ ☼    ☼\n" +
                "☼#      ☼   ☼     ☼  ☼  ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                ☼   ○ ☼    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼                  ®    ©   ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        hero(0).right();
        hero(1).down();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼      ○                    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼     ☼☼☼☼☼         ○       ☼\n" +
                "☼☼     ☼                     ☼\n" +
                "☼#     ☼☼☼        ☼☼☼☼#      ☼\n" +
                "☼☼     ☼          ☼   ☼  ●   ☼\n" +
                "☼☼     ☼☼☼☼#      ☼☼☼☼#      ☼\n" +
                "☼☼                ☼   ●      ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼            ×╔♠─┐          ☼\n" +
                "☼#○            ║  ˅          ☼\n" +
                "☼☼         ©  ╔╝             ☼\n" +
                "☼☼        ☼☼☼ ╚═╗            ☼\n" +
                "☼☼       ☼  ☼   ╚╕           ☼\n" +
                "☼☼      ☼☼☼☼#     ☼☼   ☼#    ☼\n" +
                "☼☼      ☼   ☼     ☼ ☼ ☼ ☼    ☼\n" +
                "☼#      ☼   ☼     ☼  ☼  ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                ☼   ○ ☼    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼                  ®    ©   ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();
        tick();

        hero(0).tickPills();
        hero(0).tickPills();
        hero(0).tickPills();
        hero(0).tickPills();
        assertEquals(2, hero(0).furyCount());
        assertEquals(2, hero(0).flyingCount());
        hero(0).tickPills();
        assertEquals(1, hero(0).furyCount());
        assertEquals(1, hero(0).flyingCount());

        hero(1).left();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼      ○                    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼     ☼☼☼☼☼         ○       ☼\n" +
                "☼☼     ☼                     ☼\n" +
                "☼#     ☼☼☼        ☼☼☼☼#      ☼\n" +
                "☼☼     ☼          ☼   ☼  ●   ☼\n" +
                "☼☼     ☼☼☼☼#      ☼☼☼☼#      ☼\n" +
                "☼☼                ☼   ●      ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼             ╔═×┐►         ☼\n" +
                "☼#○            ║  │          ☼\n" +
                "☼☼         ©  ╔╝  │          ☼\n" +
                "☼☼        ☼☼☼ ╚╕ <┘          ☼\n" +
                "☼☼       ☼  ☼                ☼\n" +
                "☼☼      ☼☼☼☼#     ☼☼   ☼#    ☼\n" +
                "☼☼      ☼   ☼     ☼ ☼ ☼ ☼    ☼\n" +
                "☼#      ☼   ☼     ☼  ☼  ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                ☼   ○ ☼    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼                  ®    ©   ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        verifyAllEvents(
                "listener(1) => [BLUEBERRY]\n");
    }

    // с таблеткой ярости я отгрызаю бороды,
    // даже те которые на чернике вырастают
    @Test
    public void shouldCase12_justFury() {
        givenFl("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼      ○                    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼     ☼☼☼☼☼         ○       ☼\n" +
                "☼☼     ☼                     ☼\n" +
                "☼#     ☼☼☼        ☼☼☼☼#      ☼\n" +
                "☼☼     ☼          ☼   ☼  ●   ☼\n" +
                "☼☼     ☼☼☼☼#      ☼☼☼☼#      ☼\n" +
                "☼☼                ☼   ●      ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼            ┌──>           ☼\n" +
                "☼#○           ¤♥  ○          ☼\n" +
                "☼☼         ©  ╔╝             ☼\n" +
                "☼☼        ☼☼☼ ╚═╗            ☼\n" +
                "☼☼       ☼  ☼   ╚══╕         ☼\n" +
                "☼☼      ☼☼☼☼#     ☼☼   ☼#    ☼\n" +
                "☼☼      ☼   ☼     ☼ ☼ ☼ ☼    ☼\n" +
                "☼#      ☼   ☼     ☼  ☼  ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                ☼   ○ ☼    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼                  ®    ©   ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼      ○                    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼     ☼☼☼☼☼         ○       ☼\n" +
                "☼☼     ☼                     ☼\n" +
                "☼#     ☼☼☼        ☼☼☼☼#      ☼\n" +
                "☼☼     ☼          ☼   ☼  ●   ☼\n" +
                "☼☼     ☼☼☼☼#      ☼☼☼☼#      ☼\n" +
                "☼☼                ☼   ●      ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼             ♥×─>          ☼\n" +
                "☼#○            ║  ○          ☼\n" +
                "☼☼         ©  ╔╝             ☼\n" +
                "☼☼        ☼☼☼ ╚═╗            ☼\n" +
                "☼☼       ☼  ☼   ╚═╕          ☼\n" +
                "☼☼      ☼☼☼☼#     ☼☼   ☼#    ☼\n" +
                "☼☼      ☼   ☼     ☼ ☼ ☼ ☼    ☼\n" +
                "☼#      ☼   ☼     ☼  ☼  ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                ☼   ○ ☼    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼                  ®    ©   ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        hero(0).right();
        hero(1).down();
        tick();

        verifyAllEvents(
                "listener(0) => [EAT[2], EAT[1]]\n" +
                "listener(1) => [BLUEBERRY]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼      ○                    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼     ☼☼☼☼☼         ○       ☼\n" +
                "☼☼     ☼                     ☼\n" +
                "☼#     ☼☼☼        ☼☼☼☼#      ☼\n" +
                "☼☼     ☼          ☼   ☼  ●   ☼\n" +
                "☼☼     ☼☼☼☼#      ☼☼☼☼#      ☼\n" +
                "☼☼                ☼   ●      ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼             ╔♥×┐          ☼\n" +
                "☼#○            ║  ˅          ☼\n" +
                "☼☼         ©  ╔╝             ☼\n" +
                "☼☼        ☼☼☼ ╚═╗            ☼\n" +
                "☼☼       ☼  ☼   ╚╕           ☼\n" +
                "☼☼      ☼☼☼☼#     ☼☼   ☼#    ☼\n" +
                "☼☼      ☼   ☼     ☼ ☼ ☼ ☼    ☼\n" +
                "☼#      ☼   ☼     ☼  ☼  ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                ☼   ○ ☼    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼                  ®    ©   ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼      ○                    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼     ☼☼☼☼☼         ○       ☼\n" +
                "☼☼     ☼                     ☼\n" +
                "☼#     ☼☼☼        ☼☼☼☼#      ☼\n" +
                "☼☼     ☼          ☼   ☼  ●   ☼\n" +
                "☼☼     ☼☼☼☼#      ☼☼☼☼#      ☼\n" +
                "☼☼                ☼   ●      ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼             ╔═♥æ          ☼\n" +
                "☼#○            ║  │          ☼\n" +
                "☼☼         ©  ╔╝  ˅          ☼\n" +
                "☼☼        ☼☼☼ ╚═╗            ☼\n" +
                "☼☼       ☼  ☼   ╙            ☼\n" +
                "☼☼      ☼☼☼☼#     ☼☼   ☼#    ☼\n" +
                "☼☼      ☼   ☼     ☼ ☼ ☼ ☼    ☼\n" +
                "☼#      ☼   ☼     ☼  ☼  ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                ☼   ○ ☼    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼                  ®    ©   ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

    }

    // с бледной поганкой я порхаю над врагом
    // пока полет не закончится - там змея моя
    // погибнет и враг заберет очки
    @Test
    public void shouldCase12_justFlying() {
        givenFl("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼      ○                    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼     ☼☼☼☼☼         ○       ☼\n" +
                "☼☼     ☼                     ☼\n" +
                "☼#     ☼☼☼        ☼☼☼☼#      ☼\n" +
                "☼☼     ☼          ☼   ☼  ●   ☼\n" +
                "☼☼     ☼☼☼☼#      ☼☼☼☼#      ☼\n" +
                "☼☼                ☼   ●      ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼            ┌──>           ☼\n" +
                "☼#○           ¤♠  ○          ☼\n" +
                "☼☼         ©  ╔╝             ☼\n" +
                "☼☼        ☼☼☼ ╚═╗            ☼\n" +
                "☼☼       ☼  ☼   ╚══╕         ☼\n" +
                "☼☼      ☼☼☼☼#     ☼☼   ☼#    ☼\n" +
                "☼☼      ☼   ☼     ☼ ☼ ☼ ☼    ☼\n" +
                "☼#      ☼   ☼     ☼  ☼  ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                ☼   ○ ☼    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼                  ®    ©   ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼      ○                    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼     ☼☼☼☼☼         ○       ☼\n" +
                "☼☼     ☼                     ☼\n" +
                "☼#     ☼☼☼        ☼☼☼☼#      ☼\n" +
                "☼☼     ☼          ☼   ☼  ●   ☼\n" +
                "☼☼     ☼☼☼☼#      ☼☼☼☼#      ☼\n" +
                "☼☼                ☼   ●      ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼            ×♠──>          ☼\n" +
                "☼#○            ║  ○          ☼\n" +
                "☼☼         ©  ╔╝             ☼\n" +
                "☼☼        ☼☼☼ ╚═╗            ☼\n" +
                "☼☼       ☼  ☼   ╚═╕          ☼\n" +
                "☼☼      ☼☼☼☼#     ☼☼   ☼#    ☼\n" +
                "☼☼      ☼   ☼     ☼ ☼ ☼ ☼    ☼\n" +
                "☼#      ☼   ☼     ☼  ☼  ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                ☼   ○ ☼    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼                  ®    ©   ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        hero(0).right();
        hero(1).down();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼      ○                    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼     ☼☼☼☼☼         ○       ☼\n" +
                "☼☼     ☼                     ☼\n" +
                "☼#     ☼☼☼        ☼☼☼☼#      ☼\n" +
                "☼☼     ☼          ☼   ☼  ●   ☼\n" +
                "☼☼     ☼☼☼☼#      ☼☼☼☼#      ☼\n" +
                "☼☼                ☼   ●      ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼            ×╔♠─┐          ☼\n" +
                "☼#○            ║  ˅          ☼\n" +
                "☼☼         ©  ╔╝             ☼\n" +
                "☼☼        ☼☼☼ ╚═╗            ☼\n" +
                "☼☼       ☼  ☼   ╚╕           ☼\n" +
                "☼☼      ☼☼☼☼#     ☼☼   ☼#    ☼\n" +
                "☼☼      ☼   ☼     ☼ ☼ ☼ ☼    ☼\n" +
                "☼#      ☼   ☼     ☼  ☼  ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                ☼   ○ ☼    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼                  ®    ©   ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        verifyAllEvents(
                "listener(1) => [BLUEBERRY]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼      ○                    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼     ☼☼☼☼☼         ○       ☼\n" +
                "☼☼     ☼                     ☼\n" +
                "☼#     ☼☼☼        ☼☼☼☼#      ☼\n" +
                "☼☼     ☼          ☼   ☼  ●   ☼\n" +
                "☼☼     ☼☼☼☼#      ☼☼☼☼#      ☼\n" +
                "☼☼                ☼   ●      ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼             ╔═♠┐          ☼\n" +
                "☼#○            ║  │          ☼\n" +
                "☼☼         ©  ╔╝  ˅          ☼\n" +
                "☼☼        ☼☼☼ ╚═╗            ☼\n" +
                "☼☼       ☼  ☼   ╙            ☼\n" +
                "☼☼      ☼☼☼☼#     ☼☼   ☼#    ☼\n" +
                "☼☼      ☼   ☼     ☼ ☼ ☼ ☼    ☼\n" +
                "☼#      ☼   ☼     ☼  ☼  ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                ☼   ○ ☼    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼                  ®    ©   ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        hero(0).tickPills();
        hero(0).tickPills();
        hero(0).tickPills();
        hero(0).tickPills();
        hero(0).tickPills();
        hero(0).tickPills();
        assertEquals(1, hero(0).flyingCount());
        hero(0).tickPills();
        assertEquals(0, hero(0).flyingCount());

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼      ○                    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼     ☼☼☼☼☼         ○       ☼\n" +
                "☼☼     ☼                     ☼\n" +
                "☼#     ☼☼☼        ☼☼☼☼#      ☼\n" +
                "☼☼     ☼          ☼   ☼  ●   ☼\n" +
                "☼☼     ☼☼☼☼#      ☼☼☼☼#      ☼\n" +
                "☼☼                ☼   ●      ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼             ╔×─☻          ☼\n" +
                "☼#○            ║  │          ☼\n" +
                "☼☼         ©  ╔╝  │          ☼\n" +
                "☼☼        ☼☼☼ ╚═╕ ˅          ☼\n" +
                "☼☼       ☼  ☼                ☼\n" +
                "☼☼      ☼☼☼☼#     ☼☼   ☼#    ☼\n" +
                "☼☼      ☼   ☼     ☼ ☼ ☼ ☼    ☼\n" +
                "☼#      ☼   ☼     ☼  ☼  ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                ☼   ○ ☼    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼#                           ☼\n" +
                "☼☼                  ®    ©   ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [EAT[10], WIN]\n");

    }

    @Test
    public void shouldChangeHeadSprite() {
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼╘►     ☼\n" +
                "☼       ☼\n" +
                "☼×>     ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼╘►     ☼\n" +
                "☼       ☼\n" +
                "☼×>     ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼×>     ☼\n" +
                "☼       ☼\n" +
                "☼╘►     ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n", 1);

        hero(0).eatDeathCap();
        hero(1).eatFury();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼╘♠     ☼\n" +
                "☼       ☼\n" +
                "☼×♣     ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼×♦     ☼\n" +
                "☼       ☼\n" +
                "☼╘♥     ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n", 1);

        hero(0).eatFury();
        hero(1).eatDeathCap();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼╘♠     ☼\n" +
                "☼       ☼\n" +
                "☼×♦     ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼×♦     ☼\n" +
                "☼       ☼\n" +
                "☼╘♠     ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n", 1);

        hero(0).removeFlying();
        hero(1).removeFury();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼╘♥     ☼\n" +
                "☼       ☼\n" +
                "☼×♦     ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼×♣     ☼\n" +
                "☼       ☼\n" +
                "☼╘♠     ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n", 1);

        hero(0).removeFury();
        hero(1).removeFlying();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼╘►     ☼\n" +
                "☼       ☼\n" +
                "☼×>     ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼×>     ☼\n" +
                "☼       ☼\n" +
                "☼╘►     ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void shouldCase13() {
        givenFl("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼                         ● ☼\n" +
                "☼#        ○                  ☼\n" +
                "☼☼       ●                   ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼     ☼☼☼☼☼                 ☼\n" +
                "☼☼     ☼                     ☼\n" +
                "☼#     ☼☼☼        ☼☼☼☼#      ☼\n" +
                "☼☼     ☼          ☼   ☼  ●   ☼\n" +
                "☼☼     ☼☼☼☼#      ☼☼☼☼#      ☼\n" +
                "☼☼        $       ☼          ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼     ●                     ☼\n" +
                "☼#                           ☼\n" +
                "☼☼ ●                         ☼\n" +
                "☼☼ ● ○    ☼☼☼                ☼\n" +
                "☼☼       ☼  ☼                ☼\n" +
                "☼☼      ☼☼☼☼#    ●☼☼   ☼#    ☼\n" +
                "☼☼      ☼   ☼     ☼ ☼ ☼ ☼    ☼\n" +
                "☼#      ☼   ☼     ☼  ☼  ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼    ○         ×─┐          ☼\n" +
                "☼☼                └─┐        ☼\n" +
                "☼#                  └┐     ● ☼\n" +
                "☼☼               ┌───┘╓      ☼\n" +
                "☼☼               └───♣╚► ©   ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n");

        hero(0).up();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼                         ● ☼\n" +
                "☼#        ○                  ☼\n" +
                "☼☼       ●                   ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼     ☼☼☼☼☼                 ☼\n" +
                "☼☼     ☼                     ☼\n" +
                "☼#     ☼☼☼        ☼☼☼☼#      ☼\n" +
                "☼☼     ☼          ☼   ☼  ●   ☼\n" +
                "☼☼     ☼☼☼☼#      ☼☼☼☼#      ☼\n" +
                "☼☼        $       ☼          ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼     ●                     ☼\n" +
                "☼#                           ☼\n" +
                "☼☼ ●                         ☼\n" +
                "☼☼ ● ○    ☼☼☼                ☼\n" +
                "☼☼       ☼  ☼                ☼\n" +
                "☼☼      ☼☼☼☼#    ●☼☼   ☼#    ☼\n" +
                "☼☼      ☼   ☼     ☼ ☼ ☼ ☼    ☼\n" +
                "☼#      ☼   ☼     ☼  ☼  ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼    ○          ×┐          ☼\n" +
                "☼☼                └─┐        ☼\n" +
                "☼#                  └┐     ● ☼\n" +
                "☼☼               ┌───┘ ▲     ☼\n" +
                "☼☼               └────♣╙ ©   ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼                         ● ☼\n" +
                "☼#        ○                  ☼\n" +
                "☼☼       ●                   ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼     ☼☼☼☼☼                 ☼\n" +
                "☼☼     ☼                     ☼\n" +
                "☼#     ☼☼☼        ☼☼☼☼#      ☼\n" +
                "☼☼     ☼          ☼   ☼  ●   ☼\n" +
                "☼☼     ☼☼☼☼#      ☼☼☼☼#      ☼\n" +
                "☼☼        $       ☼          ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼     ●                     ☼\n" +
                "☼#                           ☼\n" +
                "☼☼ ●                         ☼\n" +
                "☼☼ ● ○    ☼☼☼                ☼\n" +
                "☼☼       ☼  ☼                ☼\n" +
                "☼☼      ☼☼☼☼#    ●☼☼   ☼#    ☼\n" +
                "☼☼      ☼   ☼     ☼ ☼ ☼ ☼    ☼\n" +
                "☼#      ☼   ☼     ☼  ☼  ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                ☼     ☼    ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼    ○           æ          ☼\n" +
                "☼☼                └─┐        ☼\n" +
                "☼#                  └┐ ▲   ● ☼\n" +
                "☼☼               ┌───┘ ╙     ☼\n" +
                "☼☼               └─────♣ ©   ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        verifyAllEvents(
                "listener(1) => [EAT[1]]\n");
    }

    @Test
    public void headStrikeDisplay() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼˅     ☼\n" +
                "☼ ◄══╕ ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼☺══╕  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼☻──ö  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [EAT[2], WIN]\n" +
                "listener(1) => [DIE]\n");
    }

    @Test
    public void headStrikeDisplayInverted() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼▼     ☼\n" +
                "☼ <──ö ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼☻──ö  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼☺══╕  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [EAT[2], WIN]\n");
    }

    @Test
    public void headStrikeDisplay_sameSize() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼│     ☼\n" +
                "☼˅     ☼\n" +
                "☼ ◄═╕  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        // TODO тут рисуется не та голова, как в 90% случаев
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼│     ☼\n" +
                "☼☻═╕   ☼\n" + // TODO тут рисуется не та голова, как в 90% случаев
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        // TODO тут рисуется не та голова, как в 90% случаев
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼║     ☼\n" +
                "☼☺─ö   ☼\n" +  // TODO тут рисуется не та голова, как в 90% случаев
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [DIE]\n");
    }

    @Test
    public void headStrikeDisplayInverted_sameSize() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼║     ☼\n" +
                "☼▼     ☼\n" +
                "☼ <─ö  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼║     ☼\n" +
                "☼☻─ö   ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼│     ☼\n" +
                "☼☺═╕   ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [DIE]\n");
    }

    @Test
    public void headStrikeDisplay2() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼│     ☼\n" +
                "☼˅     ☼\n" +
                "☼ ◄╕   ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        // TODO тут рисуется не та голова, как в 90% случаев
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼│     ☼\n" +
                "☼☻╕    ☼\n" + // TODO тут рисуется не та голова, как в 90% случаев
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        // TODO тут рисуется не та голова, как в 90% случаев
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼║     ☼\n" +
                "☼☺ö    ☼\n" + // TODO тут рисуется не та голова, как в 90% случаев
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [DIE]\n");
    }

    @Test
    public void headStrikeDisplayInverted2() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼║     ☼\n" +
                "☼▼     ☼\n" +
                "☼ <ö   ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        // TODO тут рисуется не та голова, как в 90% случаев
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╓     ☼\n" +
                "☼║     ☼\n" +
                "☼☺ö    ☼\n" + // TODO тут рисуется не та голова, как в 90% случаев
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        // TODO тут рисуется не та голова, как в 90% случаев
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼æ     ☼\n" +
                "☼│     ☼\n" +
                "☼☻╕    ☼\n" + // TODO тут рисуется не та голова, как в 90% случаев
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [DIE]\n");
    }

    @Test
    public void bodyStrikeDisplay() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  æ   ☼\n" +
                "☼  ˅   ☼\n" +
                "☼ ◄══╕ ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  æ   ☼\n" +
                "☼◄═☺╕  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ╓   ☼\n" +
                "☼<─☻ö  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [EAT[2], WIN]\n" +
                "listener(1) => [DIE]\n");
    }

    @Test
    public void bodyStrikeDisplayInverted() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ╓   ☼\n" +
                "☼  ▼   ☼\n" +
                "☼ <──ö ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ╓   ☼\n" +
                "☼<─☻ö  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  æ   ☼\n" +
                "☼◄═☺╕  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [EAT[2], WIN]\n");
    }

    @Test
    public void flightOverBodyDisplay() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  æ   ☼\n" +
                "☼  ˅   ☼\n" +
                "☼  ©   ☼\n" +
                "☼  ◄══╕☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents(
                "listener(1) => [DEATH_CAP]\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  æ   ☼\n" +
                "☼◄═♦╕  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ╓   ☼\n" +
                "☼<─♠ö  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☻══æ   ☼\n" +
                "☼  ♦   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☺──╓   ☼\n" +
                "☼  ♠   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [DIE]\n" +
                "listener(1) => [WIN]\n");
    }

    @Test
    public void flightOverBodyDisplayInverted() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  ╓   ☼\n" +
                "☼  ▼   ☼\n" +
                "☼  ©   ☼\n" +
                "☼  <──ö☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents(
                "listener(0) => [DEATH_CAP]\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ╓   ☼\n" +
                "☼<─♠ö  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  æ   ☼\n" +
                "☼◄═♦╕  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☺──╓   ☼\n" +
                "☼  ♠   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☻══æ   ☼\n" +
                "☼  ♦   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [WIN]\n" +
                "listener(1) => [DIE]\n");
    }

    @Test
    public void flightOverHeadDisplay() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  æ   ☼\n" +
                "☼  ˅   ☼\n" +
                "☼  ©   ☼\n" +
                "☼    ◄╕☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents(
                "listener(1) => [DEATH_CAP]\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  æ   ☼\n" +
                "☼  ♦╕  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ╓   ☼\n" +
                "☼  ♠ö  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ◄æ   ☼\n" +
                "☼  ♦   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ <╓   ☼\n" +
                "☼  ♠   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void flightOverHeadDisplayInverted() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  ╓   ☼\n" +
                "☼  ▼   ☼\n" +
                "☼  ©   ☼\n" +
                "☼    <ö☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents(
                "listener(0) => [DEATH_CAP]\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ╓   ☼\n" +
                "☼  ♠ö  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  æ   ☼\n" +
                "☼  ♦╕  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ <╓   ☼\n" +
                "☼  ♠   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ◄æ   ☼\n" +
                "☼  ♦   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void headTwoFlyingHeroes_drawSmallerOnTop_start() {
        givenFl("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼   ┌─> ◄══╕☼\n" +
                "☼   │       ☼\n" +
                "☼   │       ☼\n" +
                "☼   └───ö   ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n");

        hero(0).eatDeathCap();
        hero(1).eatDeathCap();
        field().tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼   ┌──♠══╕ ☼\n" +
                "☼   │       ☼\n" +
                "☼   │       ☼\n" +
                "☼   └──ö    ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼   ╔══♦──ö ☼\n" +
                "☼   ║       ☼\n" +
                "☼   ║       ☼\n" +
                "☼   ╚══╕    ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼   ┌─♠══╕  ☼\n" +
                "☼   │       ☼\n" +
                "☼   │       ☼\n" +
                "☼   └─ö     ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼   ╔═♦──ö  ☼\n" +
                "☼   ║       ☼\n" +
                "☼   ║       ☼\n" +
                "☼   ╚═╕     ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼   ┌♠══╕♦  ☼\n" +
                "☼   │       ☼\n" +
                "☼   │       ☼\n" +
                "☼   └ö      ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼   ╔♦──ö♠  ☼\n" +
                "☼   ║       ☼\n" +
                "☼   ║       ☼\n" +
                "☼   ╚╕      ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼   ♠══╕──♦ ☼\n" +
                "☼   │       ☼\n" +
                "☼   │       ☼\n" +
                "☼   ¤       ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼   ♦──ö══♠ ☼\n" +
                "☼   ║       ☼\n" +
                "☼   ║       ☼\n" +
                "☼   ╙       ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 1);

        hero(0).down();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼   ╔═╕────♦☼\n" +
                "☼   ♠       ☼\n" +
                "☼   ¤       ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼   ┌─ö════♠☼\n" +
                "☼   ♦       ☼\n" +
                "☼   ╙       ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void headTwoFlyingHeroes_drawSmallerOnTop_finishWithFlying() {
        headTwoFlyingHeroes_drawSmallerOnTop_start();

        hero(1).up();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼          ♦☼\n" +
                "☼   ╔╕─────┘☼\n" +
                "☼   ║       ☼\n" +
                "☼   ♠       ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼          ♠☼\n" +
                "☼   ┌ö═════╝☼\n" +
                "☼   │       ☼\n" +
                "☼   ♦       ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼          ♦☼\n" +
                "☼          │☼\n" +
                "☼   ╓──────┘☼\n" +
                "☼   ║       ☼\n" +
                "☼   ║       ☼\n" +
                "☼   ♠       ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼          ♠☼\n" +
                "☼          ║☼\n" +
                "☼   æ══════╝☼\n" +
                "☼   │       ☼\n" +
                "☼   │       ☼\n" +
                "☼   ♦       ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼          ♦☼\n" +
                "☼          │☼\n" +
                "☼          │☼\n" +
                "☼    ×─────┘☼\n" +
                "☼   ╓       ☼\n" +
                "☼   ║       ☼\n" +
                "☼   ║       ☼\n" +
                "☼   ♠       ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼          ♠☼\n" +
                "☼          ║☼\n" +
                "☼          ║☼\n" +
                "☼    ╘═════╝☼\n" +
                "☼   æ       ☼\n" +
                "☼   │       ☼\n" +
                "☼   │       ☼\n" +
                "☼   ♦       ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void headTwoFlyingHeros_drawSmallerOnTop_finishWithoutFlying() {
        headTwoFlyingHeroes_drawSmallerOnTop_start();

        hero(0).removeFlying();
        hero(1).removeFlying();

        assertEquals(0, hero(0).flyingCount());
        assertEquals(0, hero(1).flyingCount());

        hero(1).up();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼          ˄☼\n" +
                "☼   ╔╕─────┘☼\n" +
                "☼   ║       ☼\n" +
                "☼   ▼       ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼          ▲☼\n" +
                "☼   ┌ö═════╝☼\n" +
                "☼   │       ☼\n" +
                "☼   ˅       ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼          ˄☼\n" +
                "☼          │☼\n" +
                "☼   ╓──────┘☼\n" +
                "☼   ║       ☼\n" +
                "☼   ║       ☼\n" +
                "☼   ▼       ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼          ▲☼\n" +
                "☼          ║☼\n" +
                "☼   æ══════╝☼\n" +
                "☼   │       ☼\n" +
                "☼   │       ☼\n" +
                "☼   ˅       ☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼          ˄☼\n" +
                "☼          │☼\n" +
                "☼          │☼\n" +
                "☼    ×─────┘☼\n" +
                "☼   ╓       ☼\n" +
                "☼   ║       ☼\n" +
                "☼   ║       ☼\n" +
                "☼   ▼       ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼           ☼\n" +
                "☼           ☼\n" +
                "☼          ▲☼\n" +
                "☼          ║☼\n" +
                "☼          ║☼\n" +
                "☼    ╘═════╝☼\n" +
                "☼   æ       ☼\n" +
                "☼   │       ☼\n" +
                "☼   │       ☼\n" +
                "☼   ˅       ☼\n" +
                "☼           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void twoFlyingHeros_andOneBlueberry_whoIsFirst() {
        givenFl("☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼ ┌─>○◄═╕ ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼\n");

        hero(0).eatDeathCap();
        hero(1).eatDeathCap();
        field().tick();

        verifyAllEvents(
                "listener(0) => [BLUEBERRY]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼ ×──♠══╕ ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼ ╘══♦──ö ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼  ×♠══╕  ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼  ╘♦──ö  ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼  ♠══╕♦  ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼  ♦──ö♠  ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼ ♠══╕──♦ ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼ ♦──ö══♠ ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼♠══╕ ×──♦☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼♦──ö ╘══♠☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼         ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void shouldCase14() {
        givenFl("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼            ○              ☼\n" +
                "☼#   ©  ○                    ☼\n" +
                "☼☼               ○         ○ ☼\n" +
                "☼☼                      ○    ☼\n" +
                "☼☼   ○                       ☼\n" +
                "☼☼●    ☼☼☼☼☼                 ☼\n" +
                "☼☼  ●  ☼               ○     ☼\n" +
                "☼#     ☼☼☼        ☼☼☼☼# ○    ☼\n" +
                "☼☼  ○  ☼          ☼○○®☼  ●  ○☼\n" +
                "☼☼     ☼☼☼☼#      ☼☼☼☼#○     ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼    ●                      ☼\n" +
                "☼#                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼       $☼☼☼           ●    ☼\n" +
                "☼☼       ☼  ☼        ╘════╗  ☼\n" +
                "☼☼      ☼☼☼☼#     ☼☼   ☼# ║  ☼\n" +
                "☼☼      ☼   ☼   ● ☼ ☼ ☼ ☼ ║  ☼\n" +
                "☼#      ☼   ☼     ☼  ☼  ☼ ║╔╗☼\n" +
                "☼☼                ☼     ☼ ╚╝║☼\n" +
                "☼☼     ●  ●   æ   ☼    ●☼   ║☼\n" +
                "☼☼            └──────┐      ║☼\n" +
                "☼☼              ┌────┘      ║☼\n" +
                "☼☼             ●│           ♥☼\n" +
                "☼#             ┌┘    ┌─────┐ ☼\n" +
                "☼☼             └─────┘    <┘ ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n");

        // hero идет по инерции вниз
        // enemy идет по инерции влево
        tick();

        hero(0).left();
        // enemy идет по инерции влево
        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼            ○              ☼\n" +
                "☼#   ©  ○                    ☼\n" +
                "☼☼               ○         ○ ☼\n" +
                "☼☼                      ○    ☼\n" +
                "☼☼   ○                       ☼\n" +
                "☼☼●    ☼☼☼☼☼                 ☼\n" +
                "☼☼  ●  ☼               ○     ☼\n" +
                "☼#     ☼☼☼        ☼☼☼☼# ○    ☼\n" +
                "☼☼  ○  ☼          ☼○○®☼  ●  ○☼\n" +
                "☼☼     ☼☼☼☼#      ☼☼☼☼#○     ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼                ☼          ☼\n" +
                "☼☼    ●                      ☼\n" +
                "☼#                           ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼       $☼☼☼           ●    ☼\n" +
                "☼☼       ☼  ☼          ╘══╗  ☼\n" +
                "☼☼      ☼☼☼☼#     ☼☼   ☼# ║  ☼\n" +
                "☼☼      ☼   ☼   ● ☼ ☼ ☼ ☼ ║  ☼\n" +
                "☼#      ☼   ☼     ☼  ☼  ☼ ║╔╗☼\n" +
                "☼☼                ☼     ☼ ╚╝║☼\n" +
                "☼☼     ●  ●       ☼    ●☼   ║☼\n" +
                "☼☼                          ║☼\n" +
                "☼☼                          ║☼\n" +
                "☼☼             ●            ║☼\n" +
                "☼#                         ♥╝☼\n" +
                "☼☼                      <──ö ☼\n" +
                "☼☼                           ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n", 0);

        verifyAllEvents(
                "listener(0) => [EAT[30]]\n");
    }

    // была бага, когда откусывали от героя два раза, то она второй раз не давалась
    // попутно в этом тесте тестируется что будет,
    // если во время откусывания по fury скушать чернику
    @Test
    public void eatTwiceSameHero() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  ╓   ☼\n" +
                "☼○ ▼   ☼\n" +
                "☼○ ®®  ☼\n" +
                "☼○○<──ö☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼○ ╓   ☼\n" +
                "☼○ ♥®  ☼\n" +
                "☼○<───ö☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼○ æ   ☼\n" +
                "☼○ ♣®  ☼\n" +
                "☼○◄═══╕☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [FURY]\n" +
                "listener(1) => [BLUEBERRY]\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼○     ☼\n" +
                "☼○ ╓®  ☼\n" +
                "☼<ö♥   ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼○     ☼\n" +
                "☼○ æ®  ☼\n" +
                "☼◄╕♣   ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [EAT[3], EAT[1]]\n" +
                "listener(1) => [BLUEBERRY]\n");

        hero(1).up();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼○     ☼\n" +
                "☼˄  ®  ☼\n" +
                "☼└ö╓   ☼\n" +
                "☼  ♥   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼○     ☼\n" +
                "☼▲  ®  ☼\n" +
                "☼╚╕æ   ☼\n" +
                "☼  ♣   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        hero(0).right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼˄     ☼\n" +
                "☼│  ®  ☼\n" +
                "☼└ö    ☼\n" +
                "☼  ╘♥  ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼▲     ☼\n" +
                "☼║  ®  ☼\n" +
                "☼╚╕    ☼\n" +
                "☼  ×♣  ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        hero(1).right();
        hero(0).up();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼┌>    ☼\n" +
                "☼│  ®  ☼\n" +
                "☼¤  ♥  ☼\n" +
                "☼   ╙  ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╔►    ☼\n" +
                "☼║  ®  ☼\n" +
                "☼╙  ♣  ☼\n" +
                "☼   ¤  ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼┌─>   ☼\n" +
                "☼¤  ♥  ☼\n" +
                "☼   ╙  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╔═►   ☼\n" +
                "☼╙  ♣  ☼\n" +
                "☼   ¤  ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [FURY]\n" +
                "listener(1) => [BLUEBERRY, BLUEBERRY]\n");

        hero(0).left();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼×──>  ☼\n" +
                "☼  ♥╕  ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼╘══►  ☼\n" +
                "☼  ♣ö  ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        hero(0).up();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ♥×> ☼\n" +
                "☼  ╙   ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ♣╘► ☼\n" +
                "☼  ¤   ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [EAT[2]]\n");
    }

    @Test
    public void eat() {
        givenFl("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼   ╔♥   ☼\n" +
                "☼   ║┌>  ☼\n" +
                "☼  ╔╝└─┐ ☼\n" +
                "☼  ╙ ×─┘ ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n");

        hero(0).down();
        hero(1).up();
        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼   ╔╗˄  ☼\n" +
                "☼   ║♥¤  ☼\n" +
                "☼  ╘╝    ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼   ┌┐▲  ☼\n" +
                "☼   │♣╙  ☼\n" +
                "☼  ×┘    ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(0) => [EAT[6]]\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼     ˄  ☼\n" +
                "☼   ╔╗¤  ☼\n" +
                "☼   ║║   ☼\n" +
                "☼   ╙♥   ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼     ▲  ☼\n" +
                "☼   ┌┐╙  ☼\n" +
                "☼   ││   ☼\n" +
                "☼   ¤♣   ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼        ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 1);
    }
}

