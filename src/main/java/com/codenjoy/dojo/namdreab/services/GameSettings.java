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
import com.codenjoy.dojo.services.settings.AllSettings;
import com.codenjoy.dojo.services.settings.PropertiesKey;
import com.codenjoy.dojo.services.settings.SettingsImpl;

import java.util.Arrays;
import java.util.List;

import static com.codenjoy.dojo.namdreab.services.GameRunner.GAME_NAME;
import static com.codenjoy.dojo.namdreab.services.GameSettings.Keys.*;

public class GameSettings extends SettingsImpl implements AllSettings<GameSettings> {

    public enum Keys implements PropertiesKey {

        FLYING_PILL_EFFECT_TIMEOUT,
        FURY_PILL_EFFECT_TIMEOUT,
        FLYING_PILLS_COUNT,
        FURY_PILLS_COUNT,
        GOLD_COUNT,
        ACORNS_COUNT,
        BLUEBERRIES_COUNT,
        ACORN_REDUCED,
        WIN_SCORE,
        BLUEBERRY_SCORE,
        GOLD_SCORE,
        DIE_PENALTY,
        ACORN_SCORE,
        EAT_SCORE,
        SCORE_COUNTING_TYPE;

        private String key;

        Keys() {
            this.key = key(GAME_NAME);
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

        integer(FLYING_PILL_EFFECT_TIMEOUT, 10);
        integer(FURY_PILL_EFFECT_TIMEOUT, 10);

        integer(FLYING_PILLS_COUNT, 2);
        integer(FURY_PILLS_COUNT, 2);
        integer(GOLD_COUNT, 5);
        integer(ACORNS_COUNT, 10);
        integer(BLUEBERRIES_COUNT, 30);

        integer(ACORN_REDUCED, 3);

        integer(WIN_SCORE, 50);
        integer(BLUEBERRY_SCORE, 1);
        integer(GOLD_SCORE, 10);
        integer(DIE_PENALTY, -0);
        integer(ACORN_SCORE, 5);
        integer(EAT_SCORE, 10);

        Levels.setup(this);
    }

    public Calculator<Integer> calculator() {
        return new Calculator<>(new Scores(this));
    }
}