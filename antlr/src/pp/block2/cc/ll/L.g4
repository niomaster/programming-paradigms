lexer grammar L;

@header{package pp.block2.cc.ll;}

A : 'a';
B : 'b';
C : 'c';

TYPO : (~('a' | 'b' | 'c'))+;