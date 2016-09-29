
# Scala World 2016 - a Recap

_Paulo "JCranky" Siqueira_ & _Matthew de Detrich_
![Rheged Centre Entrance](https://c4.staticflickr.com/9/8631/29999124355_b0a4357da2_k.jpg)


# In a really short summary:

## schedule a bit messy and the website not very responsive
## great technical sessions
_and most (if not all) were recorded, afaik_

# Agenda
## a quick overview of talks I saw
## mostly very briefly, to entice curiosity

# Day 1
## Martin Odersky: "Compiler as an in-memory database"
_or something like that..._

## apparently was not the first time this talk was given
## but was the first time I saw it
_you should be able to find it around_

## about the dotty compiler inner works

#![Scala Compiler ER](https://c7.staticflickr.com/6/5785/29705384710_9c70e2cc77_z.jpg)

## take-aways
## number of compiler phases is increasing... alot - _from 2x to 50+_
## individual phases designed in a FP style


## Ron: Functional Databases (and Doobie)

## some background before moving to Doobie
## recursive data
## some advanced terms to twist some minds
## Fix and Cofree: "?"
## Functor: "you can map"
## traversable functors

## Doobie: purely functional database

## or as in the website:

## "doobie is a pure functional JDBC layer for Scala"

## being teached at the https://www.scala-exercises.org/

## docs at https://tpolecat.github.io/doobie-0.2.0/00-index.html


# Olafur: scala.meta workshop
## didn't work for me, serious network issues
## https://olafurpg.github.io/scala.meta-workshop/


# Raul: Run free
## free monads
## nested types and monads
## nested pyramids in code, due to excessive monad nesting

#![Nested Code](https://c1.staticflickr.com/9/8762/29885769912_f7673183cc_k.jpg)

## monad transformers
## free monads


# Tim Perret: Enterprise Algebra
## from the title, I would guess a simple talk
## one of the most advanced ones from the whole conference
## github.com/verizon
## sbt: automate all the things
#![Verizon Repos](https://c3.staticflickr.com/9/8543/29371657034_735eb5bcd5_k.jpg)
## java refugees
# to think about
## free / coyonadas
## functors (one more talk with it)
## type lambdas?
## nomad monads?


# Heiko Seeberger: Akka Streams
## I don't really use akka streams
## so I couldn't follow too much
## showed lots of code
(but most of the code seemed a bit too complex or too full of boilerplate)


# Roland Kuhn: Distributed Vs. Composed
## designed to be a bit controversial
## tried to mathematically justify the actor model
(which was a bit complex but pretty cool)
## PI calculus
## lots of math, head hurting at some points...
(lack of beer?)
## protocols for interprocess / actor communication
(focused but not exclusive for actors)
## mapping from PI calculus to the actor model
## research somehow touching typed actors?
## showed some future code to be in the akka implementation
(part of it still only in a PR from him for now)
```CODE```
## alternative for free monads in the future?
## everything really experimental for now


## In the meanwhile, in the evening...
# Scala:
## Slowly Compiled Academic Language


# Day 2

# Dick Wall / Josh Suereth: For: what is it good for?
## fun and light, a good "break"
## ended with some more advanced concepts
## bottom-line:
# "for is not a loop"
## -Xprint:parser -> show parsed / de-sugarized fors
```CODE```
## -Xshow-phases  -> print the compiler phases
```CODE```
## inline assignments
```_ = println("some logging")```
(quick tip for debuging)
##(who uses println for debuggin, right?)
## for == Monad + withFilter stuff
## monads dont mix
## "the essence of the iterator pattern" paper
## off
# StateMonad
# Emm
# Sinks
# Comonads

  
# Phillip Haller - safer concurrency
## LaCasa library
## boxing of mutable state
## sending boxes around
## "continuation passing"
## consumable permissions
## intermezzo spores


## Lukas Rytz - Tales from compiling to the JVM
## development of the scala 2.12 compiler
## new jvm features
## invoke dynamics
## invoke special
## default methods on java interfaces
## one puzzler:
## java / scala boxing is slightly different
(mention the non-nulable scala primitive that gets null from Java and turns it into 0 ?)
(if I can reproduce the error...)


# Manohor - Staged Parser Combinators
## composition of parsers
## ParseQuery
(implemented with macros)
## multi-stage programming / parser processing
## each parser combinator can run in a different stage / phase
## fill the gaps possible in each stage, fill Rep[T] in the rest
## research from EPFL, done, full implementation missing / to come


# Dmitry: Complexity: Accidental and Essential
## a few scala puzzlers
## (mention the scala puzzlers book here)
## Option to remove Predef?
(I couldn't find it though...)
## dotty will help remove some problems
## others can't really be removed
(to avoid breaking the community too much)
# possible victimns:
## type projection
## structural types
## implicit (tuple1 -> Unit) will die
## ```DelayedInit```
## DelayedInit: super lambda from the body
## lots of compiler magic required
## is already deprecated and **will die**
## new DelayedInit will come, using the new feature that allows traits to take params
## and with no special compiler support
## auto-tupling should die, but wont
(code sample here)
## some frameworks (like) uses it heavily - including the stdlib  


# Conclusions
# Main take aways
## there is still LOTs to learn about scala
(hence the *crazy terms* briefly mentioned)
## the scala ecosystem **IS** evolving
## **2.12** and **dotty** are going to be cool *:D*




----- Tech Talk Sent / Details:

title: Scala World Recap
speaker: internal
duration: 30 minutes
speaker: Paulo Renato Campos de Siqueira
team: Team Glitch

description: I had the opportunity to attend Scala World, an advanced but fun conference nonetheless. Lets have a tour around what was there to be seen.

device: windows / ubuntu

location: BMO

link to edit the form:

https://docs.google.com/a/zalando.de/forms/d/e/1FAIpQLScjQUyoWM1bIpWchzo5IAg6TQauN57AJwRk2XcrG60eqy_BZg/viewform?edit2=2_ABaOnuePH3bYbIhMP0huNcy2pwkdHmPG7qPzItFI2-RVywhlh-Z-Exd-XMOG

