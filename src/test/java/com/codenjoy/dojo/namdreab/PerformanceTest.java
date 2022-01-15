package com.codenjoy.dojo.namdreab;

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

import com.codenjoy.dojo.client.local.DiceGenerator;
import com.codenjoy.dojo.namdreab.services.GameRunner;
import com.codenjoy.dojo.namdreab.services.GameSettings;
import com.codenjoy.dojo.services.Dice;
import org.junit.Test;

import static com.codenjoy.dojo.utils.TestUtils.assertPerformance;
import static org.junit.Assert.assertEquals;

public class PerformanceTest {

    @Test
    public void test() {
        // about 5.9 sec
        int players = 5;
        int ticks = 1000;

        int expectedCreation = 2999;
        int expectedPrint = 7000;
        int expectedTick = 1000;

        Dice dice = new DiceGenerator().getDice(2000);
        GameRunner runner = new GameRunner(){

            @Override
            public Dice getDice() {
                return dice;
            }

            @Override
            public GameSettings getSettings() {
                return new GameSettings();
            }
        };

        boolean printBoard = false;
        String board = assertPerformance(runner,
                players, ticks,
                expectedCreation, expectedPrint, expectedTick,
                printBoard);

        assertEquals(
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼☼   ☼ ○☼    ☼       ©☼       ☼\n" +
                "☼☼○     ☼☼☼      ☼    ☼   ○☼  ☼\n" +
                "☼#○     ○     ☼ ☼☼☼☼ ☼☼    ☼ $☼\n" +
                "☼☼    ☼☼☼☼ ☼☼$☼  ○☼   ☼☼☼ ☼☼☼○☼\n" +
                "☼☼  ☼         ☼   ☼☼   ☼  ○☼  ☼\n" +
                "☼☼☼☼☼ ☼☼☼  ☼  ☼ ○         ○   ☼\n" +
                "☼☼ ☼ ♦ ○   ☼  $ ○☼☼  ☼☼☼☼ ○☼  ☼\n" +
                "☼☼  ┌┘☼☼☼ ☼☼     ☼    ☼    ☼ ●☼\n" +
                "☼#  │ ╘═►     ☼ ☼☼☼  ☼☼ ○  ☼  ☼\n" +
                "☼☼  ¤   ☼☼☼☼ ☼☼   ☼○  ☼☼☼ ☼☼☼ ☼\n" +
                "☼☼  ☼   ☼     ☼   ☼   ☼ ○  ☼  ☼\n" +
                "☼☼☼☼☼ ☼☼☼  ☼  ☼˄              ☼\n" +
                "☼☼ ●☼   ☼  ☼   │☼☼  ☼☼☼  ☼☼☼  ☼\n" +
                "☼☼      ☼  ☼ ┌─┘ ☼  ○ ☼    ☼  ☼\n" +
                "☼#      ●  ┌─┘☼  ☼☼☼ ☼☼○ æ ☼  ☼\n" +
                "☼☼      ☼☼☼│☼☼☼   ☼ © ☼☼☼│☼☼☼○☼\n" +
                "☼☼    ☼ ☼  └ö ☼ ● ☼┌─────┘ ☼  ☼\n" +
                "☼☼☼☼☼ ☼☼☼  ☼  ☼    │ ○☼☼☼     ☼\n" +
                "☼☼  ☼   ☼● ☼☼   ☼☼☼│  ☼    ☼  ☼\n" +
                "☼☼         ☼┌──────┘  ☼  ☼☼☼  ☼\n" +
                "☼#      ☼● ┌┘ ☼ ☼☼☼☼ ☼☼○   ☼  ☼\n" +
                "☼☼  ○   ☼☼ │☼☼☼●  ☼○● ☼☼☼ ☼☼☼ ☼\n" +
                "☼☼  ☼   ☼  └─┐☼   ☼☼ ○®  ® ☼  ☼\n" +
                "☼☼☼☼☼  ☼☼  ☼☼│☼  ☼☼   ☼☼☼  ○  ☼\n" +
                "☼☼ ☼    ☼  ☼ └───> $  ☼   ●☼○ ☼\n" +
                "☼☼    ☼☼☼  ☼    ☼☼☼☼ ☼☼    ☼○ ☼\n" +
                "☼#            ☼   ☼   ☼☼☼ ☼☼☼○☼\n" +
                "☼☼     ˄☼☼ ☼☼☼☼   ☼    $  ●☼  ☼\n" +
                "☼☼  ☼×─┘☼     ○       ☼○      ☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n",
                board);
    }
}