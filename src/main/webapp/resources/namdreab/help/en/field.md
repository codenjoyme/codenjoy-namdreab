## Field example

Here is an example of a string from the server.

<pre>☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼   ☼  ☼○  ● ☼☼☼   ☼  ☼☼☼   ☼☼#          ○ ☼☼☼  ☼●☼☼☼☼ ☼☼ ☼☼☼ ©☼®     ●  ☼☼☼☼☼☼ ☼☼☼  ☼  ☼☼☼ ☼$●    ©☼$ ☼☼☼    ☼☼☼ ☼☼  ☼☼#        ╘►  ☼☼☼  ☼○  ☼☼☼☼ ☼☼☼☼  ☼  ®☼ ┌─ö ☼☼☼○☼☼ ☼☼☼ │☼♦ ☼☼☼   $    └─┘ ☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼</pre>

The length of the string is equal to the area of the field.
If you insert a hyphen character strings every
`sqrt(length(string))` characters, then you will get a readable
field image.

<pre>☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼
☼☼   ☼  ☼○  ● ☼
☼☼   ☼  ☼☼☼   ☼
☼#          ○ ☼
☼☼  ☼●☼☼☼☼ ☼☼ ☼
☼☼ ©☼®     ●  ☼
☼☼☼☼☼ ☼☼☼  ☼  ☼
☼☼ ☼$●    ©☼$ ☼
☼☼    ☼☼☼ ☼☼  ☼
☼#        ╘►  ☼
☼☼  ☼○  ☼☼☼☼ ☼☼
☼☼  ☼  ®☼ ┌─ö ☼
☼☼○☼☼ ☼☼☼ │☼♦ ☼
☼☼   $    └─┘ ☼
☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼</pre>

The first character of the line corresponds to a cell located on the
left-top corner and has the `[0, 14]` coordinate. The following example
shows the position of the hero (the `►` character) – `[11,5]`. left-bottom
corner has the `[0, 0]` coordinate.

This is what you see on UI: