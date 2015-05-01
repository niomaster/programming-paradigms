lexer grammar Identifier;

@header{package pp.block1.gen;}

IDENTIFIER              : LETTER ALNUM ALNUM ALNUM ALNUM ALNUM;
fragment LETTER         : 'a'..'z' | 'A'..'Z';
fragment ALNUM          : LETTER | '0'..'9';