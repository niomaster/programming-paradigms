lexer grammar PL1String;

@header{package pp.block1.gen;}

STRING                  : ACCENT STRINGCONTENT ACCENT;
fragment STRINGCONTENT  : ACCENT ACCENT
                        | NONACCENT
                        | ACCENT ACCENT STRINGCONTENT
                        | NONACCENT STRINGCONTENT;
fragment ACCENT         : '"';
fragment NONACCENT      : ~'"';