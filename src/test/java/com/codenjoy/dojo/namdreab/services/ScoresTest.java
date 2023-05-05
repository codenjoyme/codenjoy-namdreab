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

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static com.codenjoy.dojo.namdreab.services.GameSettings.Keys.ACORN_SCORE;
import static com.codenjoy.dojo.namdreab.services.GameSettings.Keys.DIE_PENALTY;
import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;


public class ScoresTest {

    public static final String SEPARATOR_BEFORE_AFTER = " => ";
    public static final String SEPARATOR_EVENT_PARAMETERS = ",";
    public static final String SEPARATOR_EVENTS = " ";

    private GameSettings settings;

    public ScoresTest() {
        settings = new TestGameSettings()
                .integer(DIE_PENALTY, -10)
                .integer(ACORN_SCORE, -1);
    }

    private ScoresImpl givenScores(int score) {
        return new ScoresImpl<>(score, new Calculator<>(new Scores(settings)));
    }

    @Test
    public void eventsTest() {
        assertEvents(
                "0 START => +0 => 0\n" +
                        "0 BLUEBERRY => +1 => 1\n" +
                        "0 BLUEBERRY BLUEBERRY BLUEBERRY => +1 +1 +1 => 3\n" +
                        "0 GOLD => +10 => 10\n" +
                        "0 ACORN => +0 => 0\n" +
                        "0 WIN => +50 => 50\n" +
                        "0 DIE => +0 => 0\n" +
                        "100 START => +0 => 100\n" +
                        "100 BLUEBERRY => +1 => 101\n" +
                        "100 GOLD => +10 => 110\n" +
                        "100 ACORN => -1 => 99\n" +
                        "100 WIN => +50 => 150\n" +
                        "100 FURY => +0 => 100\n" +
                        "100 DEATH_CAP => +0 => 100\n" +
                        "100 EAT => +10 => 110\n" +
                        "100 EAT,2 => +20 => 120\n" +
                        "100 EAT,5 => +50 => 150\n" +
                        "100 EAT,5 EAT,2 => +50 +20 => 170\n" +
                        "100 DIE => -10 => 90\n" +
                        "100 START GOLD ACORN BLUEBERRY WIN FURY EAT,5 DEATH_CAP EAT,2 DIE => +0 +10 -1 +1 +50 +0 +50 +0 +20 -10 => 220");
    }

    private void assertEvents(String expected) {
        String actual = forAll(expected, this::run);
        assertEquals(expected, actual);
    }

    private String forAll(String expected, Function<String, String> lineProcessor) {
        return Arrays.stream(expected.split("\n"))
                .map(lineProcessor)
                .collect(joining("\n"));
    }

    private String run(String expected) {
        String left = expected.split(SEPARATOR_BEFORE_AFTER)[0];
        String[] parts = left.split(SEPARATOR_EVENTS);

        AtomicInteger score = new AtomicInteger(Integer.parseInt(parts[0]));
        ScoresImpl scores = givenScores(score.get());

        String scoresHistory = Arrays.asList(parts)
                .subList(1, parts.length).stream()
                .map(this::event)
                .peek(scores::event)
                .map(event -> sign(scores.getScore() - score.get()))
                .peek(it -> score.set(scores.getScore()))
                .collect(joining(SEPARATOR_EVENTS));

        return String.format("%s%s%s%s%s",
                left,
                SEPARATOR_BEFORE_AFTER,
                scoresHistory,
                SEPARATOR_BEFORE_AFTER,
                scores.getScore());
    }

    private String sign(int value) {
        return (value >= 0)
                ? "+" + value
                : String.valueOf(value);
    }

    private Event event(String it) {
        if (it.contains(SEPARATOR_EVENT_PARAMETERS)) {
            String name = it.split(SEPARATOR_EVENT_PARAMETERS)[0];
            Event.Type type = Event.Type.valueOf(name);
            int value = Integer.parseInt(it.split(SEPARATOR_EVENT_PARAMETERS)[1]);
            return new Event(type, value);
        }

        return new Event(Event.Type.valueOf(it));
    }
}