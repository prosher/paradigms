# Paradigms course assignments

[Problem statements in Russian](https://www.kgeorgiy.info/courses/paradigms/homeworks.html)

### Homework 1. Error Handling in Java

* Add error handling to the program that evaluates expressions,
including parsing and evaluation errors.
* Human-readable error messages should be printed to the console.
  The program should not crash with exceptions.

### Homework 2. Binary search in Java

* Implement iterative and recursive variants of
binary search on an array.
* Pre- and post-conditions must be specified.
* For method implementations, proofs of compliance
with contracts in terms of Hoare triples must be given.

### Homework 3. Queue on an array in Java

* Define the model and find the invariant of the "queue" data structure.
* Define the functions that are required to implement the queue.
* Find their pre- and post-conditions, provided that the queue does not contain null.
* Implement classes that represent an array-based circular queue.
* The `ArrayQueueModule` class must implement a single queue instance using class variables.
* The `ArrayQueueADT` class must implement the queue as an abstract data type (with an explicit reference to the queue instance).
* The `ArrayQueue` class must implement the queue as a class (with an implicit reference to the queue instance).
* The following methods must be implemented: `enqueue`, `element`, `dequeue`, `size`, `isEmpty`, `clear`

### Homework 4. Queues in Java
* Define the `Queue` interface and describe its contract.
* Implement the class `LinkedQueue` - a queue on a linked list.
* Extract the common parts of the `LinkedQueue` and `ArrayQueue` classes into an `AbstractQueue` base class.

### Homework 5. Evaluate generic expressions in Java
* Add support for evaluation in various types to the program that parses and evaluates expressions of three variables.
* An implementation must not contain unchecked type conversions or use the `@SuppressWarnings` annotation.

### Homework 6. Functional expressions in JavaScript
* Write functions `cnst`, `variable`, `add`, `subtract`, `multiply`, `divide`, `negate` to evaluate expressions with three variables: `x`, `y`, and `z`.
* Functions should allow to perform calculations of the form:
```
let expr = subtract(
               multiply(
                  cnst(2),
                  variable("x")
               ),
               cnst(3)
           );
println(expr(5, 0, 0));
```
* When the expression is evaluated, each variable is replaced by the value passed as the corresponding parameter of an `expr` function. Thus, the result of the calculation of the above example should be the number 7.
* Write a parse function that parses expressions written in reverse Polish notation. For example, the result
of evaluating `parse("x x 2 - * x * 1 +")(5, 0, 0)` should be 76.

### Homework 7. Object expressions in JavaScript
* Write classes `Const`, `Variable`, `Add`, `Subtract`, `Multiply`, `Divide`, `Negate` to represent expressions with three variables: `x`, `y`, and `z`.
* The `toString` method must return the expression in reverse Polish notation.
* The `parse` function must return the parsed object expression.
* The `diff(diffVar)` method must return an expression representing the derivative of the original expression with respect to `diffVar`. For example, `expr.diff("x")` must return an expression equivalent to `new Const(2)`.
* It is required to write a `simplify()` method that simplifies expressions. For example,
`parse("x x 2 - * 1 *").diff("x").simplify().toString()` should return `"x x 2 - +"`.

### Homework 8. Error Handling in JavaScript
* Add a `parsePrefix(string)` function to HW7 that parses expressions given by a record like `"(- (* 2 x) 3)"`. If the parsed expression is invalid, the `parsePrefix` method should throw a human-readable error message.
* Add a `prefix()` method that produces an expression in the format expected by the `parsePrefix` function.

### Homework 9. Linear Algebra in Clojure
* Write functions for working with linear algebra objects such as scalars, vectors and matrices:
  * `v+`/`v-`/`v*`/`vd` and `m+`/`m-`/`m*`/`md` – coordinate-wise addition/subtraction/multiplication/division for vectors and matrices;
  * `scalar`/`vect` - scalar/vector product;
  * `v*s`, `m*s`/`m*v`/`m*m` – multiply a vector by a scalar, matrix by a scalar/vector/matrix.
  * `transpose` - transpose a matrix;
* Contracts must be specified for all functions.
* All functions must support an arbitrary number of arguments.

### Homework 10. Functional expressions in Clojure
* Write functions (arbitrary number of arguments) that represent arithmetic expressions.
* Develop an expression parser that reads expressions in standard Clojure form, which allows an arbitrary number of arguments for `+`, `-`, `*`, `/`

### Homework 11. Object Expressions in Clojure
* Write object constructors (arbitrary number of arguments) that represent arithmetic expressions.
* Write functions `evaluate` and `diff` with the same behaviour as in HW7.
* Write `toString` and `parseObject` functions operating with standard Clojure form expressions (arbitrary number of argument).

### Homework 12. Parsers combinators in Clojure
* Implement `parseObjectInfix` and `toStringInfix` functions operating with expressions in the infix form.
For example, `(toStringInfix(parseObjectInfix "2 * x - 3"))` should return `"((2 * x) - 3)"`.
* The parsing functions should be based on the library of combinators developed on the lecture (`parser.clj`).

### Homework 13. Prime numbers in Prolog
* Write rules:
   * `prime(N)`, which checks that N is a prime number.
   * `composite(N)`, which checks that N is a composite number.
   * `prime_divisors(N, Divisors)`, which checks that the Divisors list contains all prime divisors of N in ascending order.

### Homework 14. Search trees in Prolog
* Implement an associative array (map) based on search trees. Any logarithmic height search tree can be implemented for the solution.
* Write rules:
  * `map_get(TreeMap, Key, Value)`
  * `map_put(TreeMap, Key, Value, Result)`
  * `map_remove(TreeMap, Key, Result)`
  * `map_build(SortedList, TreeMap)`

### Homework 15. Parsing Expressions in Prolog
* Write `evaluate(Expression, Variables, Result)` rule that evaluates arithmetic expressions.
* Using DC grammars, implement the `infix_str(Expression, Atom)` rule that parses/prints expressions written in full-bracket infix form.