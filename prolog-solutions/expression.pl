lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).

flatten([], []).
flatten([H | T], R) :- append(H, FT, R), flatten(T, FT).

boolean(N, 1.0) :- N > 0.0, !.
boolean(_, 0.0) :- !.

op_not_(A, R) :- R is 1.0 - A.

op_or_(0.0, 0.0, 0.0) :- !.
op_or_(_, _, 1) :- !.

op_xor_(A, B, R) :- R is (A + B) mod 2.0.

op_and_(1.0, 1.0, 1.0) :- !.
op_and_(_, _, 0.0) :- !.

op_not(A, R) :- boolean(A, AB), op_not_(AB, R).
op_or(A, B, R) :- boolean(A, AB), boolean(B, BB), op_or_(AB, BB, R).
op_xor(A, B, R) :- boolean(A, AB), boolean(B, BB), op_xor_(AB, BB, R).
op_and(A, B, R) :- boolean(A, AB), boolean(B, BB), op_and_(AB, BB, R).


variable(Name, variable(Name)).
const(Value, const(Value)).

operation(op_add, A, B, R) :- R is A + B.
operation(op_subtract, A, B, R) :- R is A - B.
operation(op_multiply, A, B, R) :- R is A * B.
operation(op_divide, A, B, R) :- R is A / B.
operation(op_negate, A, R) :- R is A * -1.0.
operation(op_not, A, R) :- op_not(A, R).
operation(op_xor, A, B, R) :- op_xor(A, B, R).
operation(op_or, A, B, R) :- op_or(A, B, R).
operation(op_and, A, B, R) :- op_and(A, B, R).

evaluate(const(Value), _, Value).
evaluate(variable(Name), Vars, R) :-
	atom_chars(Name, [H | T]),
	lookup(H, Vars, R).
evaluate(operation(Op, A), Vars, R) :-
    evaluate(A, Vars, AV),
    operation(Op, AV, R).
evaluate(operation(Op, A, B), Vars, R) :-
    evaluate(A, Vars, AV),
    evaluate(B, Vars, BV),
    operation(Op, AV, BV, R).

nonvar(V, _) :- var(V).
nonvar(V, T) :- nonvar(V), call(T).

:- load_library('alice.tuprolog.lib.DCGLibrary').

seq_p([], _) --> [].
seq_p([H | T], Allowed) -->
  { member(H, Allowed)},
  [H],
  seq_p(T, Allowed).

digits_p(N) --> seq_p(N, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']).
var_p(N) --> seq_p(N, [x, y, z, 'X', 'Y', 'Z']).

number_p(['-' | T]) --> ['-'], number_p(T).
number_p(N) -->
    {nonvar(N, flatten([F, ['.'], S], N))},
    digits_p(F), ['.'], digits_p(S),
    {flatten([F, ['.'], S], N)}.
number_p(N) --> digits_p(N).

op_p(op_add) --> ['+'].
op_p(op_subtract) --> ['-'].
op_p(op_multiply) --> ['*'].
op_p(op_divide) --> ['/'].
op_p(op_negate) --> {atom_chars("negate", N)}, N.
op_p(op_not) --> ['!'].
op_p(op_or) --> ['|', '|'].
op_p(op_and) --> ['&', '&'].
op_p(op_xor) --> ['^', '^'].

ws_p(1) --> [' '].
ws_p(_) --> [].
ws_p(_) --> [' '], ws_p(_).

expr_p(const(Value)) -->
    {nonvar(Value, number_chars(Value, Chars))},
    number_p(Chars),
    {Chars = [_ | _], number_chars(Value, Chars)}.

expr_p(variable(Name)) -->
    {nonvar(Name, atom_chars(Name, Chars))},
    var_p(Chars),
    {Chars = [_ | _], atom_chars(Name, Chars)}.

expr_p(operation(Op, A, B)) -->
    ['('], ws_p(0), expr_p(A), ws_p(1), op_p(Op), ws_p(1), expr_p(B), ws_p(0), [')'].

expr_p(operation(Op, A)) -->
    op_p(Op), ws_p(1), expr_p(A).

infix_p(E) --> ws_p(0), expr_p(E), ws_p(0).

infix_str(E, A) :- ground(E), phrase(infix_p(E), C), !, atom_chars(A, C).
infix_str(E, A) :-   atom(A), atom_chars(A, C), phrase(infix_p(E), C), !.
