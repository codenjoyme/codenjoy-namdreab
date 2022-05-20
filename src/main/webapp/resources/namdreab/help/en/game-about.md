## What is the game about

Keep in mind: when writing your bot you should stick to its movement logic.
The rest of the game is ready for you.

You have to write a hero bot that will beat other bots by points. All players play on the
same field. Hero can move to empty cells in four directions but cannot move to the previous cell.

On its path a hero can encounter stones, gold, fury pills, flying pills, aplles or other
heroes. If hero eats a stone it becomse shorter by 4 cells. If its length is less
than 2, it dies. For gold, aplles and dead competitors hero gets 
bonus points[*](index.md#ask).
For its own death and eaten stones hero gets demerit points[*](index.md#ask).
The points are summed up.

The player with most points when time expires wins. A dead hero immediately
vanishes and reappears on one of the respawn cells, waiting for the next
round (start event).

## Game Author

If you have any questions feel free to contact me:
Author **Корсиков Илья**,
email [kors.ilya@gmail.com](mailto:kors.ilya@gmail.com),
skype [kk.ilya](skype:kk.ilya).