package com.codenjoy.dojo.namdreab.services;

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
import com.codenjoy.dojo.services.event.Calculator;
import com.codenjoy.dojo.services.event.ScoresImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static com.codenjoy.dojo.namdreab.services.Event.Type.*;
import static com.codenjoy.dojo.namdreab.services.GameSettings.Keys.*;
import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class ScoresTest {

    private GameSettings settings;
    private ScoresImpl scores;
    private Event[] events;
    private int increase;

    public ScoresTest(int score, int increase, Event... events) {
        settings = new TestGameSettings()
                .integer(WIN_SCORE, 30)
                .integer(BLUEBERRY_SCORE, 1)
                .integer(GOLD_SCORE, 5)
                .integer(DIE_PENALTY, -10)
                .integer(ACORN_SCORE, -1);
        givenScores(score);
        this.events = events;
        this.increase = increase;
    }

    private void givenScores(int score) {
        scores = new ScoresImpl<>(score, new Calculator<>(new Scores(settings)));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] params = new Object[][]{
                {0, 0, new Event[]{new Event(START)}},
                {0, +1, new Event[]{new Event(BLUEBERRY)}},
                {0, +5, new Event[]{new Event(GOLD)}},
                {0, 0, new Event[]{new Event(ACORN)}}, // счёт всегда >=0
                {0, +30, new Event[]{new Event(WIN)}},
                {0, 0, new Event[]{new Event(DIE)}}, // счёт всегда >=0
                {100, 0, new Event[]{new Event(START)}},
                {100, +1, new Event[]{new Event(BLUEBERRY)}},
                {100, +5, new Event[]{new Event(GOLD)}},
                {100, -1, new Event[]{new Event(ACORN)}},
                {100, +30, new Event[]{new Event(WIN)}},
                {100, -10, new Event[]{new Event(DIE)}},
        };
        return Arrays.asList(params);
    }

    @Test
    public void eventTest() {
        // given
        int before = scores.getScore();

        // when
        Arrays.stream(events).forEach(event -> scores.event(event));

        // then
        int after = scores.getScore();
        assertEquals("After events " + Arrays.toString(events) + " score should be",
                before + increase, after);
    }
}
