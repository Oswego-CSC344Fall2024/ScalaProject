# Scala Project
Write a Scala program that performs pattern matching on strings, where patterns are expressed using only the concatenation, alternation (“|”), and optional (“?”) operators of regular expressions (no loops/”*”, no escape characters), and the tokens are letters and digits, plus period (“.”) to mean any letter/digit. Each run of the program should accept a pattern, and then any number of strings, reporting only whether they match. Your program should represent expressions as trees (use case classes) and evaluate on the inputs, without using any regular expressions or Scala’s regular expression library except for matching the individual alphanumeric characters, if you’d like. For example:
```
pattern? ((h|j)ell. worl?d)|(42)
string? hello world
match
string? jello word
match
string? jelly word
match
string? 42
match
string? 24
no match
string? hello world42
no match
```
```
pattern? I (like|love|hate)( (cat|dog))? people
string? I like cat people
match
string? I love dog people
match
string? I hate people
match
string? I likelovehate people
no match
string? I people
no match
```
## Bootstrap
An ambiguous grammar for patterns is:
```Scala
S -: E$  
E -: C | EE | E'|'E | E'?' | '(' E ')'
C -: '0' | '1' | ... | '9'| 'a' | 'b' | ... | 'z' | '.'
```
To reflect that option(‘?’) has highest precedence, then concatenation, then alternation(‘|’), the following unambiguous grammar can be created:

```Scala
  S  -: E$
  E  -: T '|' E | T
  T  -: F T | F
  F  -: A '?' | A
  A  -: C | '(' E ')'
  C  -: Alphanumeric characters plus '.'
```
where '$' is eof/end-of-string.

As with the class example, you will build an AST. This means that building nodes for E in your tree, which represents alternation (or perhaps the absence of alternation), you will build an Alternation node only if necessary. 
Your need to start your Token type and AST Types (They have been included in the project. Don't change them. They will be used in testing cases)
```Scala

(* Scanner Types *)
enum Token:
  case Tok_Char(value: Char)
  case Tok_OR
  case Tok_Q
  case Tok_LPAREN
  case Tok_RPAREN
  case Tok_End

 
(* AST Types *)
trait Exp
case class C(value:Char)
case class Concat(left: Exp, right: Option[Exp]) extends Exp
case class Optional(left: Exp) extends Exp
case class Alternation(left: Exp, right: Option[Exp]) extends Exp
```
You must implement a recursive descent parser yourself to build the AST from the input string. Remember not to use any regular expression processing other than for the individual characters (either built in or in external libraries)!

## Hints

This project is broken up roughly into three parts – the scanner, the parser, and the matcher. The parser will require you to write the most code, but the type checker should help immensely. The most difficult part of the project is probably the matcher, which requires more thinking!

some testing cases will be as follows:

```Scala
tokenize("ab") = List(Tok_Char('a'),Tok_Char('b'),Token.Tok_End)
tokenize("a.b") = List(Tok_Char('a'),Tok_Char('.'),Tok_Char('b'),Token.Tok_End)
tokenize("a|b") = List(Tok_Char('a'),Tok_OR,Tok_Char('b'),Token.Tok_End)
tokenize("ab?") = List(Tok_Char('a'),Tok_Char('b'),Tok_Q,Token.Tok_End)
tokenize("(ab)?") = List(Tok_LPAREN,Tok_Char('a'),Tok_OR,Tok_Char('b'),Tok_RPAREN,Tok_Q, Token.Tok_End)
tokenize("ab|cd?") = List(Tok_Char('a'),Tok_Char('b'),Tok_OR,Tok_Char('c'),Tok_Char('c'),Tok_Q,Token.Tok_End)

parse ("ab") = Concat (C('a'), C ('b'))
parse ("a.b") = Concat( Concat( C('a'),Concat(C('.'),C('b'))))
parse ("a|b") = Alternation (C ('a'), C ('b'))
parse ("ab?") = Concat (C ('a'), Optional (C ('b'))
parse ("(ab)?") = Optional (Concat (C ('a'), C ('b'))
parse ("ab|cd?") = Alternation (Concat (C ('a'), C ('b')), Concat (C ('c'), Optional (C ('d'))))

matcher("ab", "ab") = true
matcher("a.b", "ab") = false
matcher("a|b", "ab") = false
matcher("a|b", "a") = true
matcher("a|b", "b") = true
matcher("ab?", "a") = true
matcher("ab?", "ab") = true
  
matcher ("((h|j)ell. worl?d)|(42)","hello world") = true
matcher ("((h|j)ell. worl?d)|(42)","jello word") = true
matcher ("((h|j)ell. worl?d)|(42)","jelly word") = true 
matcher ("((h|j)ell. worl?d)|(42)","42") = true
matcher ("((h|j)ell. worl?d)|(42)","24")= false
matcher ("((h|j)ell. worl?d)|(42)","hello world42") = false

matcher("I (like|love|hate)( (cat|dog))? people","I like cat people") = true
matcher("I (like|love|hate)( (cat|dog))? people","I like dog people") = false
matcher("I (like|love|hate)( (cat|dog))? people","I hate people") = true
matcher("I (like|love|hate)( (cat|dog))? people","I people") = false
matcher("I (like|love|hate)( (cat|dog))? people","I likelovehate people") = false
```

## Extra Credit(+15%)
This project definitely includes some cases which are harder than others. One hard case which we’ll count for extra credit is below:
```Scala
matcher("a(b|bb)?bc","abc") = true
matcher("a(b|bb)?bc","abbc") = true
matcher("a(b|bb)?bc","abbbc") = true
matcher("a(b|bb)?bc","ab") = false
matcher("a(b|bb)?bc","abbbbc") = false

```
## Important actions needed
- Please remember to keep your local repo synchronized with your GitHub online repo using `git pull` every time when you start to work.
- please keep your project synchronized with the assignment repo (I may need to update the assignemnt repo several times) by checking that
there are no commit your branch is before the Scala-project:main
# Suggested Development Environment
IntelliJ with Scala extension

