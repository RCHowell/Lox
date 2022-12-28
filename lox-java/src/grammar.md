## Grammar

```antlrv4
grammar lox;

program     : declartion* EOF
            ;
            
declaration : varDecl
            | statement
            ;
            
statement   : exprStmt
            | ifStmt
            | printStmt
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
          
varDecl     : 'var' IDENTIFIER ('=' expr)? ';';

expression  : assignment
            ;
            
// ternary    : equality '?' ternary ':' ternary
//            | equality
//            ;

assignment  : IDENTIFIER '=' assignment
            | equality
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
            | primary
            ;
            
primary     : literal
            | '(' expression ')' 
            | IDENTIFIER
            ;

literal     : NUMBER
            | STRING
            | 'true'
            | 'false'
            | 'nil'
            ; 
```
