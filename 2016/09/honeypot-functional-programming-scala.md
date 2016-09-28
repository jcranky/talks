
<!--Meetup Info:-->
<!--https://www.meetup.com/pt-BR/Honeypot/events/233936294/-->

# Functional programming
Paulo "JCranky" Siqueira

Software Engineer at Zalando SE

# **Pure** Functional Programming

## Agenda:
A tour of functional programming via scala

Features, concepts
... and pointers for further studying

# Pure ?!

# Functional Programming
# what is it?

## Programming with functions

# Function:
### a mapping from input to output

```scala
def sum(a: Int, b: Int): Int = a + b
```

### Is this stil a function?
```scala
def sum(a: Int, b: Int): Int = {
  println(s"summing $a and $b")
  a + b
}
```

### What about this?
```scala
def sum(a: Int, b: Int): Int = {
  log.info(s"summing $a and $b")
  a + b
}
```

## They are **impure**

# That word again...
# **Pure**?

# No side effects

### No side effects
```scala
def sum(a: Int, b: Int): (Int, String) =
  (a + b, s"summing $a and $b")
```

# In other words...
# Immutability!

# Error handling
## Keep functional style by avoiding throwing exceptions
## Return errors explicitly

### Database access
```scala
class Repo {
  def findUsers(firtName: String): List[User] = ???
}
```

## What if something goes wrong?

### Either (Scala API)
```
class Repo {
  def findUsers(firtName: String): Either[Throwable, List[User]] = ???
}
```

### Either (Scala API)
```scala
class Repo {
  def findUsers(firtName: String): Throwable Xor List[User] = ???
}
```


# Next:
## high order functions
## commonly used to determine if a given language is function
## functions that receive and / or return functions

### daily friends: `map`
```scala
val nums = List(1, 2, 3)
val doubled = nums.map(_ * 2)
```

### nested mapping
```scala
class Repo {
  def findUser(email: String): Future[Either[Throwable, User]]
  def findFriends(user: User): Future[Either[Throwable, List[User]]]
}

val repo: Repo = ???
```

### what is the type of `friends`?
```scala
val friends = repo.findUser(email).map { user =>
  repo.findFriends(user)
}
```

### Nested futures!
```scala
Future[Future[Either[Throwable, List[User]]]]
```

### what is the type of `friends`?
```scala
val friends = repo.findUser(email).flatMap { user =>
  repo.findFriends(user)
}
```

### Nested futures!
```scala
Future[Either[Throwable, List[User]]]
```

### for comprehensions
```scala
val friends = for {
  user <- repo.findUser(email)
  friends <- repo.frindFriends(user)
} yield friends
```

## FP Advantages:
(an that is to name just a few)

## Thread-safe by nature
## easy to test
(including property testing)

### from ScalaCheck website
```scala
property("startsWith") = forAll { (a: String, b: String) =>
  (a+b).startsWith(a)
}
property("concatenate") = forAll { (a: String, b: String) =>
  (a+b).length > a.length && (a+b).length > b.length
}
property("substring") = forAll { (a: String, b: String, c: String) =>
  (a+b+c).substring(a.length, a.length+b.length) == b
}
```

### from ScalaCheck website
```scala
$ sbt test
+ String.startsWith: OK, passed 100 tests.
! String.concat: Falsified after 0 passed tests.
> ARG_0: ""
> ARG_1: ""
+ String.substring: OK, passed 100 tests.
```

# Function composition

### First, a look at the `Function1` definition
```scala
trait Function1[-T1, +R]
```
(lets ignore the variance)

### `andThen`
```scala
def andThen[A](g: (R) => A): (T1) => A
```

### two simple functions...
```scala
def doubled(i: Int): Int = i * 2
def addTwo(i: Int): Int = i + 2
```

### sequenced
```scala
def doubleAndAdd(i: Int) = (doubled _ andThen addTwo)(i)
```

### function values
```scala
val doubled = (i: Int) => i * 2
val addTwo = (i: Int) => i + 2
```

### sequenced again
```scala
val doubleAndAdd = doubled andThen addTwo
```

A def would work as well, but would be confusing due to the lack of declared parameters.

### what is the result?
```
(doubled andThen addTwo)(10)
```

### `compose`
```scala
def compose[A](g: (A) => T1): (A) => R
```

### lets sequence again
```scala
val doubledAndAdd = doubled compose addTwo
```

### what is the result?
```scala
(doubled compose addTwo)(10)
```
# What about data?

## Functional data structures

## Highly focused on recursion

## ... and immutability

### a simplified `List`
```scala
sealed trait List[+A]
final case object Nil extends List[Nothing]
final case class Cons[A](head: A, tail: List[A])
```

### recursive construction
```scala
Cons(1, Cons(2, Cons(3, Nil))
```

## In practice
## Side-effects vs. intended effects
## carefully controlled

## intentional and explicit
## i.e. something **will** happen

# Tools are needed!

# Monads!

## one perspective: containers for data

# some old suspects

# `Collection`s
```scala
List(1,2,3).map(_ * 2)
```

# `Option`s
```scala
Option(42).map(v => s"The secret: $v")
```

# Future
```scala
Future { 42 }.map(v => s"I'm a secret from there future: $v")
```

# Future: Repo
```scala
class Repo {
  def findUser(email: String): Future[Either[Throwable, User]]
}

repo.findUser(email).map(u => s"found user $u")
```

## some less known ones (from scalaz)

# Reader
## "the environment monad"
## i.e. reads the result of a computation
## database value
## config

## or any arbitrary function...
```scala
type Reader[E,A] = E => A
```
(naive version, from a non-scalaz guy)

### Reader without Scalaz
https://gist.github.com/Mortimerp9/5384467

# Writer
## for accumulating values
(like logging and/or error messages)

## acumulates a series of functions that calculates values
```scala
sealed trait Writer[W, A]
```
(simplified, naive, from non scalaz guy again)

# New problem:
## combining those is a very bureaucratic task

# monad transformers
## ReaderWriterState
## gets messy with more then two monads

### back to the Repo
```scala
class Repo {
  def findUser(email: String): Future[Either[Throwable, User]]
}
```
How to navigate that?

### can't do
```scala
for {
  userEither <- repo.findUser(email)
  user <- userEither
} yield user
```
different monads don't mix

### so need to...
```scala
for {
  userEither <- repo.findUser(email)
  user <- userEither match {
            case Right(user) => user
            case Left(t)     => ???   // what to do?
          }
} yield user

```
(just imagine with more monads combined)

# what to do?

# free monads

# the Eff monad
## stack of effects

### closing, some terms to keep your brains burning

## monads

## free monads

## monad tranformers

## applicatives

## functors

## co-monads

## coyonadas

## cofree

## Emm

## Eff

## **HELP! XD**
 
## Book: Functional Programming in Scala
(and LOTs of online resources)

## Thanks!
Questions?

Paulo "JCranky" Siqueira

@jcranky
