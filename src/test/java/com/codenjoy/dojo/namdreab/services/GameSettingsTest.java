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

import com.codenjoy.dojo.utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameSettingsTest {

    @Test
    public void shouldGetAllKeys() {
        assertEquals("FLYING_PILL_EFFECT_TIMEOUT =[Game] Flying effect timeout\n" +
                    "FURY_PILL_EFFECT_TIMEOUT   =[Game] Fury effect timeout\n" +
                    "FLYING_PILLS_COUNT         =[Game] Flying pills count\n" +
                    "FURY_PILLS_COUNT           =[Game] Fury pills count\n" +
                    "GOLD_COUNT                 =[Game] Gold count\n" +
                    "STONES_COUNT               =[Game] Stone count\n" +
                    "APPLES_COUNT               =[Game] Apple count\n" +
                    "STONE_REDUCED              =[Game] Stone reduced value\n" +
                    "WIN_SCORE                  =[Score] Win score\n" +
                    "APPLE_SCORE                =[Score] Apple score\n" +
                    "GOLD_SCORE                 =[Score] Gold score\n" +
                    "DIE_PENALTY                =[Score] Die penalty\n" +
                    "STONE_SCORE                =[Score] Stone score\n" +
                    "EAT_SCORE                  =[Score] Eat enemy score\n" +
                    "SCORE_COUNTING_TYPE        =[Score] Counting score mode",
                TestUtils.toString(new GameSettings().allKeys()));
    }
}