package com.codenjoy.dojo.snakebattle.services;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
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


import com.codenjoy.dojo.services.PlayerScores;

import static com.codenjoy.dojo.snakebattle.services.GameSettings.Keys.*;

public class Scores implements PlayerScores {

    private GameSettings settings;
    private volatile int score;

    public Scores(int startScore, GameSettings settings) {
        this.score = startScore;
        this.settings = settings;
    }

    @Override
    public int clear() {
        return score = 0;
    }

    @Override
    public Integer getScore() {
        return score;
    }

    @Override
    public void event(Object object) {
        if (!(object instanceof Event))
            return;
        Event event = (Event)object;
        if (event.isWin()) {
            score += settings.integer(WIN_SCORE);
        } else if (event.isApple()) {
            score += settings.integer(APPLE_SCORE);
        } else if (event.isGold()) {
            score += settings.integer(GOLD_SCORE);
        } else if (event.isDie()) {
            score -= settings.integer(DIE_PENALTY);
        } else if (event.isStone()) {
            score += settings.integer(STONE_SCORE);
        } else if (event.isEat()) {
            score += settings.integer(EAT_SCORE) * event.getAmount();
        }
        score = Math.max(0, score);
    }

    @Override
    public void update(Object score) {
        this.score = Integer.valueOf(score.toString());
    }
}
