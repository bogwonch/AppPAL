grammar AppPAL;

VARIABLE: UPPER ALNUM*;
variable: VARIABLE;

CONSTANT: '"' ~["]+ '"';
constant: CONSTANT;

e: variable
 | constant
 ;

ZERO: '0';
INF: 'inf';
d: ZERO
 | INF
 ;

PREDICATE_NAME: LOWER ALNUM*;
predicate: PREDICATE_NAME ('(' e (',' e)* ')')?;
can_say: 'can-say' d fact;
can_act_as: 'can-act-as' e;

vp: predicate
  | can_say
  | can_act_as
  ;

fact: e vp;

antecedent: 'if' (fact (',' fact)* );
constraint: 'where' c;
claim: fact antecedent? constraint?;

assertion: e 'says' claim '.';

ac: assertion+;


c: 'true';

WS: [ \t\r\n]+ -> skip;

fragment
LOWER: [a-z];

fragment
UPPER: [A-Z];

fragment
ALNUM: [a-zA-Z0-9];