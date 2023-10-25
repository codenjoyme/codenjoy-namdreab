* `Solver`
  An empty class with one method — you'll have to fill it with smart logic.
* `Direcion`
  Possible commands for this game.
* `Point`
  `x`, `y` coordinates.
* `Element`
  Type of the element on the board.
* `Board`
  Еncapsulating the line with useful methods for searching
  elements on the board. The following methods can be found in the board:
* `int boardSize();`
  Size of the board
* `boolean isAt(Point point, Element element);`
  Whether the given element has given coordinate?
* `boolean isAt(Point point, Collection<Element>elements);`
  Whether any object from the given set is located in given coordinate?
* `boolean isNear(Point point, Element element);`
  Whether the given element is located near the cell with the given coordinate?
* `int countNear(Point point, Element element);`
  How many elements of the given type exist around the cell with given coordinate?
* `Element getAt(Point point);`
  Element in the current cell.
* etc... 