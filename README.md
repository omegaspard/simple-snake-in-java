# simple-snake-in-java

The snake game in java.

The challenge of this project would be to have a transparent wa

# Motivations

I always been interested in game development, with this project I want to get more in touch with it.

Before using a gaming development framework I want to have a better understanding of the game architecture and the gaming engine in general which is why I want to code
this game only using [OpenGL](https://www.opengl.org/) and more specificly the Java wrapper of it named [JOGL](https://jogamp.org/jogl/www/).

In the code of the differents games I browsed, I felt that the rendering part of the code was directing the whole project code.
My first opinion on this is that any parts of a game project that result in graphical aspect should be more like a skin and easely interchangeable, wich is not possible
if the code of the projet is organized around the rendering part.
This is why I firstly code the game in terminal mode.

The main challenge of this project would be to have a code that make it possible to change the rendering mode (terminal/openGL) in a transparent way, just like if we had
a "Drawing interface" with different implementation like "Terminal/OpenGLGraphics".

So far I encountered issues with adding the openGL mode because it works differently than I expected and I coded the terminal mode without thinking about it.

TODO list

- Add the OpenGL.
  - Since I'm stuck on this maybe try to code the whole project only with openGL first ?
- Add tests.
