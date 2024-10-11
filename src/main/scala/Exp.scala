trait Exp
case class C(value:Char) extends Exp
case class Concat(left: Exp, right: Option[Exp]) extends Exp
case class Optional(left: Exp) extends Exp
case class Alternation(left: Exp, right: Option[Exp]) extends Exp

object Exp{
//Implement the matcher function
// The matcher function should take a pattern with a type of EXP, and a string
// It will return a Boolean value showing whether the pattern and string match
def matcher(pattern: Exp, s: String): Boolean=
  true

}