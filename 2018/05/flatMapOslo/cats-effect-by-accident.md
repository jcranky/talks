
<!--
See it in action:
- clone https://github.com/hakimel/reveal.js
- copy the contents from presentation.html into reveal's index.html
- symlink this md file, and the imgs folder into reveal's root folder
- follow reveal's full setup instructions at https://github.com/hakimel/reveal.js#full-setup 
-->

![Zalando logo](imgs/logo_zalando_eci_Webversion_RGB.png)

## Learning cats-effect by Accident

Paulo "JCranky" Siqueira

Software Engineer at Zalando SE

@jcranky

---

First things first:

_Open Source is awesome!_

Note: it is the reason I'm here, lots of opportunities to learn and to help people

---

## agenda

- what are http4s, rho and cats-effect
- http4s: history
- the journey from zero to contributor

Note: 'the journey' is a reference to the way from knowing nothing about rho/http4s/cats-effect to being a contributor

---

## http4s

- FP HTTP Scala library (client and server)
- (currently) implies using cats, cats-effect and fs2

---

## rho

- a DSL for http4s
- extracts metadata from route definitions
- generates swagger documentation

---

### http4s dsl

```scala
val service = HttpService[F] {
  case GET -> Root / "orders" / id =>
    Ok(Json.obj("order_id" -> Json.fromString(id)))
}
```

---

### rho dsl

```scala
val service = new RhoService[F] with SwaggerSyntax[F] {
  "Get orders for a given id" **
    GET / "orders" / pathVar[Int] |>> { orderId: Int =>
      Ok(Json.obj("order_id" -> Json.fromInt(orderId)))
    }
  }
```

---

## cats-effect

- FP way of dealing with IO in Scala
- the leverage to enable pure FP applications

Note: the two sentences are different ways of saying the same thing

---

## http4s through the ages

Note: or, a bit of http4s history

---

![Stone Age: Scalaz](imgs/stone4.jpeg)

---

![Bronze Age: fs2.Task](imgs/bronze.jpg)

---

![Iron Age: cats-effect](imgs/iron.jpg)

---

![heavy metal](imgs/heavy-metal.jpg)

---

## bonus point:

fs2.Task is no more, replaced by cats-effect `IO`

Note: fs2.Task was deprecated and removed from fs2 itself, and fs2 now also uses cats-effect IO

---

## back to October, 2017

---

### We wanted to migrate our library to cats(-effect), with the rest of the ecosystem.

Note: we didn't want to get stuck with an old http4s (and other lib's) versions.

---

### http4s was migrated, but rho not

![Sad face](imgs/sad-face.jpg)

---

### rho was missing the iron age: cats-effect!

---

So I decided:

### "I'll do it! How hard can it be?!"

Note: Oláfur stole my sentence! lol ;)

---

![How hard can it be?!](imgs/howhard.jpg)

Note: Exactly, how hard?! Well, maybe there was a reason why this hasn't been done yet ;)

---

https://github.com/http4s/rho/pull/193

![PR Summary](imgs/pr-summary.png)

---

Changed the http4s version in sbt and...

Note: from 0.17.x to 0.18.y

---

![First Errors](imgs/first-errors.png)

---

### That was for `rho-core`!

What about other modules, and all test packages?!

Note: i.e. the errors in the previous slide were just part of it.

---

Keep in mind:

`rho` ab(uses) the type system

Note: so that it can derive the necessary information for swagger generation.

---

### First coding step: `F[_]` everywhere

tagless final maybe?

Note: is this tagless final?

---

Why? That is what http4s was doing ;)

---

![Fs](imgs/several_fs.png)

Note: point being: just abstract a "container" for something.

---

One step further and...

---

![Lots of Types](imgs/huge_types.png)

Note: I find this huge type list, which needs yet another type.

---

![Lots of Types 2](imgs/huge_types2.png)

---

![Lots of Types with F](imgs/huge_types3.png)

Note: `F` both on the type definitions and on the `Result` instantiation.

---

![Result.scala](imgs/result.png)

---

![Base & Top Result.scala](imgs/base-top-result.png)

---

![Result Syntax](imgs/result-syntax.png)

---

Next: Add / fix implicits all over the place...

---

![MaybeWritable](imgs/maybe-writable2.png)

---

And then:

```scala
class C1[F[_]: Monad]
class C2[F[_]: Applicative]
class C3[F[_]: Sync]
```

Or: learn to restrict `F[_]`

---

![Monad of F](imgs/monad-of-F.png)

---

![Applicative of F](imgs/f-applicative.png)

---

We still have a problem...

Note: in a few places.

---

![Monad of F2](imgs/monad-of-F-2.png)

---

### implicits juggling

![Rage](imgs/rage.jpg)

Note: if you want to "rage against the machine", I mean, against the scala compiler...

---

![MaybeWritable](imgs/maybe-writable2.png)

---

### Diversion: type recursion?

![Nested Recursion](imgs/recursive-f.png)

The type has to be carried around to be used by the `Response` class.

---

## Fixing tests

- we reach the "end of the world"

- drop `F[_]` and hardcode `IO` (or your preferred `Effect` implementation)

Note: and need to choose how to run our services.

---

![Hardcoded IO](imgs/hardcoded-io.png)

---

### Diversion: cats-effect hierarchy

![cats-effect hierarchy](imgs/cats-effect-typeclasses.svg)

---

And that is how I ended up learning cats-effect!

---

Bonus: exposure to datatypes I was not used to (other than cats-effect ones)

```scala
OptionT
EitherT
Kleisli
```

and others from _cats_

---

Food for thought:

`F: Effect`
 
vs.

`F: Applicative`, `F: Monad` etc

---

### Key take away:

### Want to learn something?

#### Pick an OS project you use, find something that could be better, do it.

Note: Something challenging, but not completely out of the park.

---

### Don't be afraid to ask for help: gitter #ftw

Note: maybe not all communities are very receptive, and asking for help can help you also measure that.

---

## Life is short for bad communities

---

## Thank you! Questions?!

---

![Zalando logo](imgs/logo_zalando_eci_Webversion_RGB.png)

## We are hiring!
