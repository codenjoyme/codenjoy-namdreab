package com.codenjoy.dojo.namdreab.services.ai;

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


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.games.namdreab.Board;
import com.codenjoy.dojo.namdreab.TestGameSettings;
import com.codenjoy.dojo.namdreab.model.Hero;
import com.codenjoy.dojo.namdreab.model.Level;
import com.codenjoy.dojo.namdreab.model.Namdreab;
import com.codenjoy.dojo.namdreab.model.Player;
import com.codenjoy.dojo.namdreab.services.GameSettings;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.EventListener;
import com.codenjoy.dojo.services.dice.MockDice;
import org.junit.Before;
import org.junit.Test;

import static com.codenjoy.dojo.services.Direction.RIGHT;
import static com.codenjoy.dojo.services.Direction.UP;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class AISolverTest {

    private Solver<Board> solver;
    private Board board;
    private MockDice dice;
    private GameSettings settings;

    @Before
    public void setup() {
        dice = new MockDice();
        solver = new AISolver(dice);
    }

    private void givenFl(String map) {
        board = new Board();
        board.forString(map);

        // этот весь код ниже используется сейчас только для распечатки изображения доски (для наглядности)
        // можно смело убирать, если мешает
        Level level = new Level(map);

        settings = new TestGameSettings();

        Namdreab game = new Namdreab(dice, level, settings);

        Hero hero = level.hero(game);
        EventListener listener = mock(EventListener.class);
        Player player = new Player(listener, settings);

        player.setHero(hero);
        game.newGame(player);
        hero.setActive(true);
    }

    private void testSolution(Direction expected) {
        testSolution(expected.toString());
    }

    private void testSolution(String expected) {
        assertEquals(expected, solver.get(board));
    }

    // корректный старт змейки из "стартового бокса"
    @Test
    public void startFromBox() {
        givenFl("☼☼☼☼☼☼☼☼" +
                "☼☼     ☼" +
                "╘►     ☼" +
                "☼☼     ☼" +
                "☼☼     ☼" +
                "☼☼     ☼" +
                "☼☼  ○  ☼" +
                "☼☼☼☼☼☼☼☼");
        testSolution(RIGHT);
    }

    // некуда поворачивать кроме как вверх
    @Test
    public void onlyUpTurn() {
        givenFl("☼☼☼☼☼☼☼☼" +
                "☼☼     ☼" +
                "☼#     ☼" +
                "☼☼     ☼" +
                "☼☼╘►☼  ☼" +
                "☼☼ ☼☼  ☼" +
                "☼☼    ○☼" +
                "☼☼☼☼☼☼☼☼");
        testSolution(UP);
    }
}
