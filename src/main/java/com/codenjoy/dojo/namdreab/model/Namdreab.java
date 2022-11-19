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
import com.codenjoy.dojo.namdreab.model.items.fight.FightDetails;
import com.codenjoy.dojo.namdreab.services.Event;
import com.codenjoy.dojo.namdreab.services.GameSettings;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.field.Accessor;
import com.codenjoy.dojo.services.field.Generator;
import com.codenjoy.dojo.services.field.PointField;
import com.codenjoy.dojo.services.printer.BoardReader;
import com.codenjoy.dojo.services.round.RoundField;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.codenjoy.dojo.namdreab.model.Hero.NEXT_TICK;
import static com.codenjoy.dojo.namdreab.services.Event.Type.*;
import static com.codenjoy.dojo.namdreab.services.GameSettings.Keys.*;
import static com.codenjoy.dojo.services.Direction.LEFT;
import static com.codenjoy.dojo.services.field.Generator.generate;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

public class Namdreab extends RoundField<Player, Hero> implements Field {

    private Level level;
    private PointField field;
    private List<Player> players;
    private Dice dice;
    private GameSettings settings;

    public Namdreab(Dice dice, Level level, GameSettings settings) {
        super(START, WIN, settings);

        this.level = level;
        this.dice = dice;
        this.settings = settings;
        this.field = new PointField();
        this.players = new LinkedList<>();

        clearScore();
    }

    @Override
    public void clearScore() {
        if (level == null) return;

        level.saveTo(field);
        field.init(this);

        generateAll();

        super.clearScore();
    }

    @Override
    protected void cleanStuff() {
        heroesClear();
    }

    @Override
    public void tickField() {
        heroesMove();
        heroesFight();
        heroesEat();
        // после еды у змеек отрастают хвосты, поэтому столкновения нужно повторить,
        // чтобы обработать ситуацию "кусь за растущий хвост", иначе eatTailThatGrows тесты не пройдут
        heroesFight();

        generateAll();
    }

    private void heroesClear() {
        players.stream()
                .map(Player::getHero)
                .filter(hero -> hero.isActive() && !hero.isAlive())
                .forEach(Hero::clear);
    }

    public void generateAll() {
        generateFuryPills();
        generateFlyingPills();
        generateGold();
        generateAcorns();
        generateBlueberries();
    }

    private void generateFuryPills() {
        generate(furyPills(), size(),
                settings, FURY_PILLS_COUNT,
                player -> freeRandom(),
                FuryPill::new);
    }

    private void generateFlyingPills() {
        generate(flyingPills(), size(),
                settings, FLYING_PILLS_COUNT,
                player -> freeRandom(),
                FlyingPill::new);
    }

    private void generateGold() {
        generate(gold(), size(),
                settings, GOLD_COUNT,
                player -> freeRandom(),
                Gold::new);
    }

    private void generateAcorns() {
        generate(acorns(), size(),
                settings, ACORNS_COUNT,
                player -> freeRandom(),
                Acorn::new);
    }

    private void generateBlueberries() {
        generate(blueberries(), size(),
                settings, BLUEBERRIES_COUNT,
                player -> freeRandom(),
                Blueberry::new);
    }

    public Optional<Point> freeRandom() {
        return Generator.freeRandom(size(), dice, this::isFree);
    }

    @Override
    protected List<Player> players() {
        return players;
    }

    private void heroesMove() {
        aliveActiveHeroes()
                .forEach(Hero::tick);
    }

    private void heroesFight() {
        FightDetails info = new FightDetails();

        notFlyingHeroes().forEach(hero -> {
            Hero enemy = enemyCrossedWith(hero);
            if (enemy != null) {
                if (enemy.isFlying()) {
                    return;
                }
                if (hero.isFury() && !enemy.isFury()) {
                    if (enemy.isAlive()) {
                        enemy.die();
                        info.cutOff(hero, enemy, enemy.size());
                    }
                } else if (!hero.isFury() && enemy.isFury()) {
                    if (hero.isAlive()) {
                        hero.die();
                        info.cutOff(enemy, hero, hero.size());
                    }
                } else {
                    if (!info.alreadyCut(hero)) {
                        int len1 = hero.reduce(enemy.size(), NEXT_TICK);
                        info.cutOff(enemy, hero, len1);
                    }

                    if (!info.alreadyCut(enemy)) {
                        int len2 = enemy.reduce(hero.size(), NEXT_TICK);
                        info.cutOff(hero, enemy, len2);
                    }
                }
                return;
            }

            enemy = enemyEatenWith(hero);
            if (enemy != null) {
                if (hero.isFury()) {
                    int len = enemy.reduceFrom(hero.head());
                    info.cutOff(hero, enemy, len);
                } else {
                    hero.die();
                    info.cutOff(enemy, hero, hero.size());
                }
            }
        });

        info.forEach((hunter, prey, reduce) -> {
            if (hunter.isAlive()) {
                hunter.event(new Event(EAT, reduce));
            }
        });
    }

    private void heroesEat() {
        aliveActiveHeroes().forEach(hero -> {
            Point head = hero.head();
            hero.eat();

            if (blueberries().contains(head)) {
                blueberries().removeAt(head);
                hero.event(new Event(BLUEBERRY));
            }
            if (acorns().contains(head) && !hero.isFlying()) {
                acorns().removeAt(head);
                if (hero.isAlive()) {
                    hero.event(new Event(ACORN));
                }
            }
            if (gold().contains(head)) {
                gold().removeAt(head);
                hero.event(new Event(GOLD));
            }
            if (flyingPills().contains(head)) {
                flyingPills().removeAt(head);
                hero.event(new Event(FLYING));
            }
            if (furyPills().contains(head)) {
                furyPills().removeAt(head);
                hero.event(new Event(FURY));
            }
        });
    }

    private Stream<Hero> notFlyingHeroes() {
        return aliveActiveHeroes()
                .filter(hero -> !hero.isFlying());
    }

    public int size() {
        return field.size();
    }

    @Override
    public boolean isBarrier(Point pt) {
        return pt.isOutOf(size())
                || rocks().contains(pt)
                || starts().contains(pt);
    }

    @Override
    public Optional<Point> freeRandom(Player player) {
        for (int i = 0; i < 10 && starts().size() != 0; i++) {
            StartSpot start = starts().all().get(dice.next(starts().size()));
            if (freeOfHero(start)) {
                return Optional.of(start);
            }
        }
        for (StartSpot start : starts()) {
            if (freeOfHero(start)) {
                return Optional.of(start);
            }
        }
        return Optional.empty();
    }

    public boolean isFree(Point pt) {
        return isFreeOfObjects(pt)
                && freeOfHero(pt);
    }

    public boolean isFreeForAcorn(Point pt) {
        return isFree(pt)
                && !starts().contains(LEFT.change(pt));
    }

    public boolean isFreeOfObjects(Point pt) {
        return !(blueberries().contains(pt)
                || acorns().contains(pt)
                || rocks().contains(pt)
                || starts().contains(pt)
                || flyingPills().contains(pt)
                || furyPills().contains(pt)
                || gold().contains(pt));
    }

    private boolean freeOfHero(Point pt) {
        for (Hero hero : heroes()) {
            if (hero != null
                    && hero.body().contains(pt)
                    && !hero.tail().equals(pt))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isBlueberry(Point pt) {
        return blueberries().contains(pt);
    }

    @Override
    public boolean isAcorn(Point pt) {
        return acorns().contains(pt);
    }

    @Override
    public boolean isFlyingPill(Point pt) {
        return flyingPills().contains(pt);
    }

    @Override
    public boolean isFuryPill(Point pt) {
        return furyPills().contains(pt);
    }

    @Override
    public boolean isGold(Point pt) {
        return gold().contains(pt);
    }

    @Override
    public Hero enemyEatenWith(Hero hunter) {
        return aliveEnemies(hunter)
                .filter(hero -> !hero.isFlying())
                .filter(hero -> hero.body().contains(hunter.head()))
                .findFirst()
                .orElse(null);
    }

    private Stream<Hero> aliveEnemies(Hero other) {
        return aliveActiveHeroes()
                .filter(hero -> !hero.equals(other));
    }

    private Hero enemyCrossedWith(Hero hero) {
        return aliveEnemies(hero)
                .filter(hero::isHeadIntersect)
                .findFirst()
                .orElse(null);
    }

    public void addToPoint(Point pt) {
        if (pt instanceof Blueberry) {
            addBlueberry(pt);
        } else if (pt instanceof Acorn) {
            addAcorn(pt);
        } else if (pt instanceof FlyingPill) {
            addFlyingPill(pt);
        } else if (pt instanceof FuryPill) {
            addFuryPill(pt);
        } else if (pt instanceof Gold) {
            addGold(pt);
        } else {
            fail("Невозможно добавить на поле объект типа " + pt.getClass());
        }
    }

    @Override
    public void addBlueberry(Point pt) {
        if (isFree(pt)) {
            blueberries().add(new Blueberry(pt));
        }
    }

    @Override
    public boolean addAcorn(Point pt) {
        if (isFreeForAcorn(pt)) {
            acorns().add(new Acorn(pt));
            return true;
        }
        return false;
    }

    @Override
    public void addFlyingPill(Point pt) {
        if (isFree(pt)) {
            flyingPills().add(new FlyingPill(pt));
        }
    }

    @Override
    public void addFuryPill(Point pt) {
        if (isFree(pt)) {
            furyPills().add(new FuryPill(pt));
        }
    }

    @Override
    public void addGold(Point pt) {
        if (isFree(pt)) {
            gold().add(new Gold(pt));
        }
    }

    public List<Hero> heroes() {
        return players.stream()
                .map(Player::getHero)
                .collect(toList());
    }

    @Override
    protected void onAdd(Player player) {
        player.newHero(this);
    }

    @Override
    protected void onRemove(Player player) {
        // do nothing
    }

    @Override
    public GameSettings settings() {
        return settings;
    }

    public BoardReader<Player> reader() {
        return new BoardReader<>() {
            private int size = Namdreab.this.size();

            @Override
            public int size() {
                return size;
            }

            @Override
            public void addAll(Player player, Consumer<Iterable<? extends Point>> processor) {
                processor.accept(new LinkedHashSet<>(){{
                    drawHeroes(not(Hero::isAlive),  hero -> Arrays.asList(hero.head()));
                    drawHeroes(Hero::isFlying,      Hero::reversedBody);
                    drawHeroes(not(Hero::isFlying), Hero::reversedBody);

                    addAll(rocks().all());
                    addAll(blueberries().all());
                    addAll(acorns().all());
                    addAll(flyingPills().all());
                    addAll(furyPills().all());
                    addAll(gold().all());
                    addAll(starts().all());

                    for (Point pt : this.toArray(new Point[0])) {
                        if (pt.isOutOf(Namdreab.this.size())) {
                            remove(pt);
                        }
                    }
                }

                    private void drawHeroes(Predicate<Hero> filter,
                                    Function<Hero, List<? extends Point>> getElements)
                    {
                        Namdreab.this.heroes().stream()
                                .filter(filter)
                                .sorted(Comparator.comparingInt(Hero::size))
                                        .forEach(hero -> addAll(getElements.apply(hero)));
                    }
                });
            }
        };
    }

    private void fail(String message) {
        throw new RuntimeException(message);
    }

    public Point getAt(Point pt) {
        if (blueberries().contains(pt)) {
            return new Blueberry(pt);
        }
        if (acorns().contains(pt)) {
            return new Acorn(pt);
        }
        if (flyingPills().contains(pt)) {
            return new FlyingPill(pt);
        }
        if (furyPills().contains(pt)) {
            return new FuryPill(pt);
        }
        if (gold().contains(pt)) {
            return new Gold(pt);
        }
        if (starts().contains(pt)) {
            return new StartSpot(pt);
        }
        if (rocks().contains(pt)) {
            return new Rock(pt);
        }
        for (Player player : players) {
            if (player.getHero().body().contains(pt)) {
                return player.getHero().neck(); // это просто любой объект типа Tail
            }
        }
        return null;
    }

    @Override
    public Accessor<Rock> rocks() {
        return field.of(Rock.class);
    }

    @Override
    public Accessor<StartSpot> starts() {
        return field.of(StartSpot.class);
    }

    @Override
    public Accessor<Blueberry> blueberries() {
        return field.of(Blueberry.class);
    }

    @Override
    public Accessor<Acorn> acorns() {
        return field.of(Acorn.class);
    }

    @Override
    public Accessor<FuryPill> furyPills() {
        return field.of(FuryPill.class);
    }

    @Override
    public Accessor<FlyingPill> flyingPills() {
        return field.of(FlyingPill.class);
    }

    @Override
    public Accessor<Gold> gold() {
        return field.of(Gold.class);
    }
}