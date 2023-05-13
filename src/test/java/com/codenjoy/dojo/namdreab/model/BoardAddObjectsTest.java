package com.codenjoy.dojo.namdreab.model;

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


import com.codenjoy.dojo.namdreab.model.items.*;
import com.codenjoy.dojo.services.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static com.codenjoy.dojo.services.PointImpl.pt;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class BoardAddObjectsTest extends AbstractGameTest {

    private Point addition;
    boolean add;

    public BoardAddObjectsTest(Point addition, boolean add) {
        this.addition = addition;
        this.add = add;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] params = new Object[][]{
                // нельзя ставить чернику на чернику, желуди, грибы, золото, стены
                {new Blueberry(pt(2, 2)), false},
                {new Blueberry(pt(2, 1)), false},
                {new Blueberry(pt(3, 3)), false},
                {new Blueberry(pt(3, 2)), false},
                {new Blueberry(pt(3, 1)), false},
                {new Blueberry(pt(3, 0)), false},

                // нельзя ставить желуди на чернику, желуди, грибы, золото, стены и справа от выходов
                {new Acorn(pt(2, 3)), false},
                {new Acorn(pt(2, 2)), false},
                {new Acorn(pt(2, 1)), false},
                {new Acorn(pt(3, 3)), false},
                {new Acorn(pt(3, 2)), false},
                {new Acorn(pt(3, 1)), false},
                {new Acorn(pt(3, 0)), false},

                // нельзя ставить бледные поганки на чернику, желуди, грибы, золото, стены
                {new DeathCap(pt(2, 2)), false},
                {new DeathCap(pt(2, 1)), false},
                {new DeathCap(pt(3, 3)), false},
                {new DeathCap(pt(3, 2)), false},
                {new DeathCap(pt(3, 1)), false},
                {new DeathCap(pt(3, 0)), false},

                // нельзя ставить таблетки ярости на чернику, желуди, грибы, золото, стены
                {new FlyAgaric(pt(2, 2)), false},
                {new FlyAgaric(pt(2, 1)), false},
                {new FlyAgaric(pt(3, 3)), false},
                {new FlyAgaric(pt(3, 2)), false},
                {new FlyAgaric(pt(3, 1)), false},
                {new FlyAgaric(pt(3, 0)), false},

                // нельзя ставить золото на чернику, желуди, грибы, золото, стены
                {new Strawberry(pt(2, 2)), false},
                {new Strawberry(pt(2, 1)), false},
                {new Strawberry(pt(3, 3)), false},
                {new Strawberry(pt(3, 2)), false},
                {new Strawberry(pt(3, 1)), false},
                {new Strawberry(pt(3, 0)), false},

                // можно ставить чернику, желуди, грибы и золото в пустое место
                {new Blueberry(pt(4, 2)), true},
                {new Acorn(pt(4, 2)), true},
                {new DeathCap(pt(4, 2)), true},
                {new FlyAgaric(pt(4, 2)), true},
                {new Strawberry(pt(4, 2)), true},
        };
        return Arrays.asList(params);
    }

    @Test
    public void oneOrLessObjectAtPoint() {
        givenFl("☼☼☼☼☼☼☼" +
                "☼ ╘►  ☼" +
                "☼     ☼" +
                "☼# ©  ☼" +
                "☼ ®○  ☼" +
                "☼ $●  ☼" +
                "☼☼☼☼☼☼☼");
        int before = 1;
        Point object = field().getAt(addition);
        field().addToPoint(addition);
        field().tick();
        int objectsAfter = 0;
        String objType = addition.getClass().toString().replaceAll(".*\\.", "");
        switch (objType) {
            case "Blueberry":
                objectsAfter = field().blueberries().size();
                break;
            case "Acorn":
                objectsAfter = field().acorns().size();
                break;
            case "DeathCap":
                objectsAfter = field().deathCaps().size();
                break;
            case "FlyAgaric":
                objectsAfter = field().flyAgarics().size();
                break;
            case "Strawberry":
                objectsAfter = field().strawberry().size();
                break;
            default:
                fail("Отсутствуют действия на объект типа " + objType);
        }
        if (add)
            assertEquals("Новый объект '" + objType + "' не был добавлен на поле!",
                    before + 1, objectsAfter);
        else
            assertEquals("Добавился новый объект '" + objType + "'" + " поверх существующего объекта!" +
                            (object == null ? null : object.getClass()),
                    before, objectsAfter);
    }

}
