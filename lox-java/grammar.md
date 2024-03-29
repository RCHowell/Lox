## Grammar

```antlrv4
grammar lox;

program     : declartion* EOF
            ;
            
declaration : classDecl
            | varDecl
            | funDecl
            | statement
            ;
            
statement   : exprStmt
            | ifStmt
            | printStmt
            | returnStmt
            | while
            | block
            ;

exprStmt    : expr ';'
            ;
            
ifStmt      : 'if' '(' expression ')' statement ('else' statement)?
            ;

printStmt   : 'print' expr ';'
            ;
            
while       : 'while' '(' expression ')' statement
            ;
            
for         : 'for' '(' (varDecl|exprStmt) ';' expression? ';' expression?
            ;
            
block       : '{' declaration* '}'
            ;
           
classDecl   : 'class' IDENTIFIER ('<' IDENTIFIER)? '{' function* '}'
            ;
          
varDecl     : 'var' IDENTIFIER ('=' expr)? ';'
            ;
            
funDecl     : 'fun' function
            ;
            
function    : IDENTIFIER '(' parameters? ')' block
            ;
            
parameters  : IDENTIFIER ( ',' IDENTIFIER )*
            ;
            
returnStmt  : 'return' expression? ';'
            ;

expression  : assignment
            ;
            
// ternary    : equality '?' ternary ':' ternary
//            | equality
//            ;

assignment  : (call '.')? IDENTIFIER '=' assignment
            | logicalOr
            ;
            
logicOr     : logicAnd ( 'or' logicAnd )*
            ;
            
logicAnd    : equality ( 'and' equality )*
            ;
            
equality    : comparison ( ('!=' | '==') comparison )*
            ;
            
comparison  : term ( ('>' | '>=' | '<' | '<=') term)*
            ;
            
term        : factor ( ('-' | '+') factor )*
            ;
            
factor      : unary ( ('/' | '*') unary )*
            ;

unary       : ( '-' | '!' ) unary
            | unary
            ;

call        : primary ( '(' arguments? ')' | '.' IDENTIFIER )*
            ;
            
arguments   : expression ( ',' expression )*
            ;
            
primary     : literal
            | anonFunc
            | '(' expression ')' 
            | 'this'
            | 'super'
            | IDENTIFIER
            ;
            
anonFunc    : 'fun' '(' parameters ')' block
            ;

literal     : NUMBER
            | STRING
            | 'true'
            | 'false'
            | 'nil'
            ; 
```
