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

import com.codenjoy.dojo.client.Utils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameSettingsTest {

    @Test
    public void shouldGetAllKeys() {
        assertEquals("[FLYING_PILL_EFFECT_TIMEOUT, \n" +
                        "FURY_PILL_EFFECT_TIMEOUT, \n" +
                        "FLYING_PILLS_COUNT, \n" +
                        "FURY_PILLS_COUNT, \n" +
                        "GOLD_COUNT, \n" +
                        "STONES_COUNT, \n" +
                        "APPLES_COUNT, \n" +
                        "STONE_REDUCED, \n" +
                        "WIN_SCORE, \n" +
                        "APPLE_SCORE, \n" +
                        "GOLD_SCORE, \n" +
                        "DIE_PENALTY, \n" +
                        "STONE_SCORE, \n" +
                        "EAT_SCORE, \n" +
                        "SCORE_COUNTING_TYPE]",
                Utils.split(new GameSettings().allKeys(), ", \n"));
    }
}