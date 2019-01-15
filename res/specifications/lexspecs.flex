// Give this file to jflex program and insert the resulting .class file into your project!!
import it.unisa.parser.ParserSym;
import java_cup.runtime.Symbol;
%%
%class Yylex    // Class name of the Lexical Analyzer
%public
%unicode        // Encoding, keep this

// Class name of the symbol class that the Syntax Analyzer will use
// Interface created by CUP with a lot of int static constants.
%cupsym ParserSym

// States that this Lexel will be used by a Syntax Analyzer generated by CUP
%cup

%line	        // Accessability to line number with yyline variable
%column	        // Accessability to column number with yycolumn variable

/*
	Code copied 1 to 1 in the it.unisa.lexer.it.unisa.lexer.it.unisa.lexer.Yylex class. Useful for instance variable and methods of the it.unisa.lexer.it.unisa.lexer.it.unisa.lexer.Yylex.
	StringBuffer will carry some characters (it is not the lexem from beginLexem)
	The symbol() methods will be used to create a Symbol (class of jflex library) object, representing a token.
*/
%{
  private Symbol symbol(int kind) {
      return new Symbol(kind, yyline, yycolumn);
  }

  private Symbol symbol(int kind, Object value) {
      return new Symbol(kind, yyline, yycolumn, value);
  }

  private char clearEscape(String string) {
      return string.replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t").charAt(1);
  }
%}

// Macros
head = head
start = start
semi = ;
int = int
bool = bool
double = double
string = string
char = char

id = [:jletter:]([:jletter:]|[0-9])*
comma = ,
def = def
lpar = \(
rpar = \)
lgpar = \{
rgpar = \}

read = <-
write = ->
plus = \+
minus = -
times = \*
div = \/

int_const =  (0|[1-9]([0-9])*)
double_const = (0|[1-9]([0-9])*)\.([0-9])+((E|e)(\+|\-)?[1-9]([0-9])*)?
string_const = \"([^\r\n\"\\]|\\t|\\n|\\r|\\\"|\\\\)*\"
char_const = \'([^\r\n\"\'\\]|\\t|\\n|\\r|\\\"|\\\'|\\\\)\'

true = true
false = false
assign = =

if = if
then = then
else = else
while = while
do = do

gt = >
ge = >=
lt = <
le = <=
eq = ==
not = not
and = and
or = or

in = in
out = out
inout = inout

whitespace = ((\r|\n|\r\n|\t|\f) | [ ])+
tcomment = \/\*([^\*]|\/\*)*\*\/
eolcomment =  \/\/[^\r\n]*

%%
{head}            {return symbol(ParserSym.HEAD);}
{start}           {return symbol(ParserSym.START);}
{semi}            {return symbol(ParserSym.SEMI);}
{int}             {return symbol(ParserSym.INT);}
{bool}            {return symbol(ParserSym.BOOL);}
{double}          {return symbol(ParserSym.DOUBLE);}
{string}          {return symbol(ParserSym.STRING);}
{char}            {return symbol(ParserSym.CHAR);}

{comma}           {return symbol(ParserSym.COMMA);}
{def}             {return symbol(ParserSym.DEF);}
{lpar}            {return symbol(ParserSym.LPAR);}
{rpar}            {return symbol(ParserSym.RPAR);}
{lgpar}           {return symbol(ParserSym.LGPAR);}
{rgpar}           {return symbol(ParserSym.RGPAR);}

{read}            {return symbol(ParserSym.READ);}
{write}           {return symbol(ParserSym.WRITE);}
{plus}            {return symbol(ParserSym.PLUS);}
{minus}           {return symbol(ParserSym.MINUS);}
{times}           {return symbol(ParserSym.TIMES);}
{div}             {return symbol(ParserSym.DIV);}

{int_const}       {return symbol(ParserSym.INT_CONST, Integer.parseInt(yytext()));}
{double_const}    {return symbol(ParserSym.DOUBLE_CONST, Double.parseDouble(yytext()));}
{string_const}    {return symbol(ParserSym.STRING_CONST, yytext());}
{char_const}      {return symbol(ParserSym.CHAR_CONST, clearEscape(yytext()));}

{true}            {return symbol(ParserSym.TRUE);}
{false}           {return symbol(ParserSym.FALSE);}
{assign}          {return symbol(ParserSym.ASSIGN);}

{if}              {return symbol(ParserSym.IF);}
{then}            {return symbol(ParserSym.THEN);}
{else}            {return symbol(ParserSym.ELSE);}
{while}           {return symbol(ParserSym.WHILE);}
{do}              {return symbol(ParserSym.DO);}

{gt}              {return symbol(ParserSym.GT);}
{ge}              {return symbol(ParserSym.GE);}
{lt}              {return symbol(ParserSym.LT);}
{le}              {return symbol(ParserSym.LE);}
{eq}              {return symbol(ParserSym.EQ);}
{not}             {return symbol(ParserSym.NOT);}
{and}             {return symbol(ParserSym.AND);}
{or}              {return symbol(ParserSym.OR);}

{in}              {return symbol(ParserSym.IN);}
{out}             {return symbol(ParserSym.OUT);}
{inout}           {return symbol(ParserSym.INOUT);}

{id}              {return symbol(ParserSym.ID, yytext());}

// Patterns recognized but no token is constructed: so they will be discarded
{whitespace}      {}
{tcomment}        {}
{eolcomment}      {}

// Error fallback: unrecognized pattern
[^]               {throw new Error("Illegal character \"" + yytext() + "\""); }

// When EOF is reached a special token is generated
<<EOF>>           {return new Symbol(ParserSym.EOF);}
