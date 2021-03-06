## Клиент и API

Организаторы предоставляют игрокам подготовленные клиенты в исходном
коде на нескольких языках. Каждый из этих клиентов уже умеет связываться
с сервером, принимать и разбирать сообщение от сервера (обычно это называется board)
и отправлять серверу команды.

Слишком много форы клиентский код не дает играющим, поскольку в этом коде
еще надо разобраться, но там реализована логика общения с сервером +
некоторое высокоуровневое API для работы с доской (что уже приятно).

Все языки так или иначе имеют похожий набор методов:

* `Solver`
  Пустой класс с одним методом — ты должен(должна) наполнить его умной логикой.
* `Direcion`
  Возможные направления движения для этой игры.
* `Point`
  `x`, `y` координаты.
* `Element`
  Тип элемента на доске.
* `Board`
  Содержит логику для удобного поиска и манипуляции элементами на поле.
  Ты можешь найти следующие методы в Board классе:
* `int boardSize();`
  Размер доски.
* `boolean isAt(Point point, Element element);`
  Находится ли в позиции point заданный элемент?
* `boolean isAt(Point point, Collection<Element>elements);`
  Находится ли в позиции point что-нибудь из заданного набора?
* `boolean isNear(Point point, Element element);`
  Есть ли вокруг клеточки с координатой point заданный элемент?
* `int countNear(Point point, Element element);`
  Сколько элементов заданного типа есть вокруг клетки с point?
* `Element getAt(Point point);`
  Элемент в текущей клетке.
* и так далее...