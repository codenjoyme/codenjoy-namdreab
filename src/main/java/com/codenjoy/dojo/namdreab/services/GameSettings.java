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

import com.codenjoy.dojo.services.event.Calculator;
import com.codenjoy.dojo.services.event.ScoresImpl;
import com.codenjoy.dojo.services.settings.AllSettings;
import com.codenjoy.dojo.services.settings.SettingsImpl;

import java.util.Arrays;
import java.util.List;

import static com.codenjoy.dojo.namdreab.services.GameSettings.Keys.*;

public class GameSettings extends SettingsImpl implements AllSettings<GameSettings> {

    public enum Keys implements Key {

        FLYING_COUNT("[Game] Flying count"),
        FURY_COUNT("[Game] Fury count"),
        STONE_REDUCED("[Game] Stone reduced value"),
        WIN_SCORE("[Score] Win score"),
        APPLE_SCORE("[Score] Apple score"),
        GOLD_SCORE("[Score] Gold score"),
        DIE_PENALTY("[Score] Die penalty"),
        STONE_SCORE("[Score] Stone score"),
        EAT_SCORE("[Score] Eat enemy score"),
        SCORE_COUNTING_TYPE(ScoresImpl.SCORE_COUNTING_TYPE.key());

        private String key;

        Keys(String key) {
            this.key = key;
        }

        @Override
        public String key() {
            return key;
        }
    }

    @Override
    public List<Key> allKeys() {
        return Arrays.asList(Keys.values());
    }
    
    public GameSettings() {
        initAll();

        // сколько тиков на 1 раунд
        setTimePerRound(300);
        // сколько раундов (с тем же составом героев) на 1 матч
        setRoundsPerMatch(3);
        // // сколько тиков должно пройти от начала раунда, чтобы засчитать победу
        setMinTicksForWin(40);

        integer(FLYING_COUNT, 10);
        integer(FURY_COUNT, 10);
        integer(STONE_REDUCED, 3);

        integer(WIN_SCORE, 50);
        integer(APPLE_SCORE, 1);
        integer(GOLD_SCORE, 10);
        integer(DIE_PENALTY, -0);
        integer(STONE_SCORE, 5);
        integer(EAT_SCORE, 10);

        Levels.setup(this);
    }

    public Calculator<Integer> calculator() {
        return new Calculator<>(new Scores(this));
    }
}