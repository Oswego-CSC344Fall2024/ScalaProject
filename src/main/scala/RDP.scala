import Token.*


object RDP {

  //  Task: Implement parse function
  //  It takes a String, tokenize it and then returns the parsing result in the type of of Exp
  
   def parse(s:String):Exp
  
  
  
  //Task: Implement other parse function as needed. For example: parse_E, parse_T, parse_F
  // They will take the list of tokens and returns an AST (a value of type Exp) and the remaining tokens.
  
  //  List[Token] => (Exp, List[Token])

  
  

  //return the head of a token list
  //You can use the lookahead and matchToken as before
  // If you want, you can rewrite them as needed.
  
  def lookahead(l: List[Token]): Token = l match
    case List() => throw new Exception("no Tokens")
    case head :: tail => head

  //match token with the head of tokeList if match return tail else throw exception
  def matchToken(tok: Token, tokList: List[Token]): List[Token] = tokList match
    case head :: tail if tok == head => tail
    case _ => throw new Exception("token is not matched")

}
