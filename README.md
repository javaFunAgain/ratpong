# ratpong


This is sample implementation of classic game PONG. Or more precisely REST server that
support internet play in PONG.

For client code go to : .................


# Runing?
Just call ```gradle run ```
And the navigate to localhost:9000/...


# Purpose
The goal of this project is  to prepare clean Java example of non blocking server architecture.
System uses as little mutability as possible, does not depend on huge frameworks etc.
Code should be concise (as for java standards).

For a moment system uses such technologies:
- Ratpack providing  REST server library,
- JavaSlang for immutable data structures,
- Airomem(prevayler) for simple persistence
- Junit5 for simple tests,


 
 
Notice that system does not use any special dependency injection frameworks or containers.
It is 2017 and we do not need it.

# Legal
Pleas bear in mind that PONG is a registered trademark that belongs to Atari Corporation.
This source code is created only for educational purposes and you may use it according to provided license.

# TODO
- Use JOOQ for simple SQL persitence,
- better exception handling and diagnostics,
 




