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
import com.codenjoy.dojo.services.event.EventObject;
import com.codenjoy.dojo.services.event.ScoresMap;
import com.codenjoy.dojo.services.settings.SettingsReader;
import com.codenjoy.dojo.utils.scorestest.AbstractScoresTest;
import org.junit.Test;

import java.util.function.Function;

import static com.codenjoy.dojo.namdreab.services.GameSettings.Keys.ACORN_SCORE;
import static com.codenjoy.dojo.namdreab.services.GameSettings.Keys.DIE_PENALTY;


public class ScoresTest extends AbstractScoresTest {

    @Override
    public GameSettings settings() {
        return new TestGameSettings()
                .integer(DIE_PENALTY, -10)
                .integer(ACORN_SCORE, -1);
    }

    @Override
    public Function<SettingsReader, ? extends ScoresMap<?>> scores() {
        return Scores::new;
    }

    @Override
    protected Class<? extends EventObject> events() {
        return Event.class;
    }

    @Override
    protected Class<? extends Enum> eventTypes() {
        return Event.Type.class;
    }

    @Test
    public void shouldCollectScores() {
        assertEvents("100:\n" +
                "START > +0 = 100\n" +
                "BLUEBERRY > +1 = 101\n" +
                "GOLD > +10 = 111\n" +
                "ACORN > -1 = 110\n" +
                "WIN > +50 = 160\n" +
                "FURY > +0 = 160\n" +
                "DEATH_CAP > +0 = 160\n" +
                "EAT > +10 = 170\n" +
                "EAT,2 > +20 = 190\n" +
                "EAT,5 > +50 = 240\n" +
                "DIE > -10 = 230");
    }

    @Test
    public void shouldNotLessThanZero_ifAcorn() {
        assertEvents("0:\n" +
                "ACORN > +0 = 0");
    }

    @Test
    public void shouldNotLessThanZero_ifDie() {
        assertEvents("0:\n" +
                "DIE > +0 = 0");
    }
}