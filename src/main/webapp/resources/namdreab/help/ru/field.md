## Пример поля

Вот пример строки от сервера:

<pre>board=☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼  ○                       ●☼☼#                           ☼☼☼      ☼#         ●         ☼☼☼                           ☼☼#                        æ  ☼☼☼                ☼#      │  ☼☼☼      ☼☼☼        ☼  ☼   │  ☼☼#      ☼          ☼  ☼   │  ☼☼☼      ☼        ● ☼  ☼   │  ☼☼☼      ☼☼☼æ       ○   ● ┌┘  ☼☼#         │    ☼#     <─┘ ● ☼☼☼     ○<──┘  ●           æ  ☼☼☼     ▲             ☼   ˄│  ☼☼#     ║                 └┘  ☼☼☼     ║                     ☼☼☼     ║           ☼#     ●● ☼☼#     ║ ☼☼ ☼                ☼☼☼     ║    ☼                ☼☼☼  ●  ║ ☼☼ ☼                ☼☼#     ║    ☼             ●  ☼☼☼ ● ╘═╝   ☼#                ☼☼☼     ●           ○         ☼☼#                  ☼☼☼   ●  ☼☼☼                        ● ●☼☼☼               ☼☼☼#        ☼☼#                           ☼☼☼                           ☼☼☼                           ☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼</pre>

Длинна строки равна площади поля `N*N`. Если вставить символ
переноса строки каждые `N=sqrt(length(string))` символов, то
получится читабельное изображение поля:

<pre>☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼
☼☼  ○                       ●☼
☼#                           ☼
☼☼      ☼#         ●         ☼
☼☼                           ☼
☼#                        æ  ☼
☼☼                ☼#      │  ☼
☼☼      ☼☼☼        ☼  ☼   │  ☼
☼#      ☼          ☼  ☼   │  ☼
☼☼      ☼        ● ☼  ☼   │  ☼
☼☼      ☼☼☼æ       ○   ● ┌┘  ☼
☼#         │    ☼#     <─┘ ● ☼
☼☼     ○<──┘  ●           æ  ☼
☼☼     ▲             ☼   ˄│  ☼
☼#     ║                 └┘  ☼
☼☼     ║                     ☼
☼☼     ║           ☼#     ●● ☼
☼#     ║ ☼☼ ☼                ☼
☼☼     ║    ☼                ☼
☼☼  ●  ║ ☼☼ ☼                ☼
☼#     ║    ☼             ●  ☼
☼☼ ● ╘═╝   ☼#                ☼
☼☼     ●           ○         ☼
☼#                  ☼☼☼   ●  ☼
☼☼                        ● ●☼
☼☼               ☼☼☼#        ☼
☼#                           ☼
☼☼                           ☼
☼☼                           ☼
☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼</pre>

Первый символ строки соответствует ячейке расположенной в
левом верхнем углу и имеет координату `[0, 29]`.
Координата `[0, 0]` соответствует левому нижнему углу.
В этом примере — позиция головы змеи (символ `▲`) — `[7, 16]`.

Как это поле выглядит в реале: