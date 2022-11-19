## What to do

The game is turn-based: Each second, the server sends the updated state of the
field to the client and waits for response. Within the next second the player
must give hero a command. If no command is given, hero moves inertially
in its current direction until stopped by a rock.

## Commands

* `UP`, `DOWN`, `LEFT`, `RIGHT` - they move hero one cell in the
  corresponding direction.
* `ACT` - drop an acorn (if hero has previously eaten at
  least one). The acorn is left at the end of hero's tail. With the help 
  of acorns players can set obstacles and block enemies.
* Movement and `ACT` commands can be combined, separating them by comma. 
  During one game cycle hero will drop an acorn and move, 
  e.g. `LEFT,ACT` or `ACT,LEFT`.

## Points

The parameters will change[(?)](#ask) as the game progresses.

## Cases

### Negative impact

- Hero that hits a rock, dies.
- Hero that hits another hero, dies.
- Hero must be at least two cells long or it dies.
- Hero that eats an acorn becomes three cells shorter, and, if that
  makes it shorter than two cells - it dies.

### Positive impact

- Hero that eats a blueberry becomes longer by one cell.
- Hero that eats a death cap flies over acorns and other heroes for 10 moves.
- Hero that eats a fury pill can bite off parts of other heroes and eat acorns without
  negative effects for 10 moves.
- Hero that eats gold gets bonus points.

### Exceptional cases

- Heros can bite off their own tails, becoming shorter without any negative effects.
- If two heroes collide head-on, the shortest hero dies. The surviving hero becomes
  shorter - by the length of the dead one (if that makes it shorter than two cells, it dies as well).
- The bitten off part of the tail always disappears, and hero is shortened.
- If two heroes, one of which is under the death cap, collide, nothing happens.
- If two heroes collide, the under the fury pill always wins.
- If two furious heroes collide, common collision rules are used.