prime(N) :-
	min_divisor(N, N).
	
composite(N) :-
	\+ prime(N).

min_divisor(N, D) :- min_divisor_table(N, D), !.
	
prime_divisors(1, []) :- !.
prime_divisors(N, [H | T]) :-
    number(N), min_divisor(N, H),
	N1 is N / H, prime_divisors(N1, T), !.
prime_divisors(N, Primes) :-
    sorted_primes_list(Primes), product(Primes, N), !.

compact_prime_divisors(N, CDs) :-
    prime_divisors(N, Primes), zip_sorted(Primes, CDs), !.
compact_prime_divisors(N, CDs) :-
    zip_sorted(Primes, CDs), prime_divisors(N, Primes), !.

zip_sorted([], []).
zip_sorted([H | T], [(H, 1) | TR]) :- list(TR), zip_sorted(T, TR), !.
zip_sorted([H, H | T], [(H, X) | TR]) :-
    number(X), X1 is X - 1, zip_sorted([H | T], [(H, X1) | TR]), !.
zip_sorted([H, H | T], [(H, X1) | TR]) :-
    not number(X), zip_sorted([H | T], [(H, X) | TR]), X1 is X + 1, !.
zip_sorted([H | T], [(H, 1) | TR]) :- not list(TR), zip_sorted(T, TR), !.

sorted_primes_list([]).
sorted_primes_list([H]) :- prime(H).
sorted_primes_list([H1, H2 | T]) :-
    H1 =< H2, prime(H1), sorted_primes_list([H2 | T]).

product([], 1).
product([H | T], N) :-
    product(T, N1), N is H * N1.


init(MAX_N) :-
	make_sieve(2, MAX_N).

make_sieve(MAX_N, MAX_N).
make_sieve(N, MAX_N) :-
	\+ min_divisor(N, _), assertz(min_divisor_table(N, N)),
	P is N * N, calc_min_div_for_products(P, N, MAX_N),
	N1 is N + 1, make_sieve(N1, MAX_N).
make_sieve(N, MAX_N) :-
	min_divisor(N, _),
	N1 is N + 1, make_sieve(N1, MAX_N).

calc_min_div_for_products(P, N, MAX_N) :-
    P =< MAX_N,
	assertz(min_divisor_table(P, N)),
	P1 is P + N, calc_min_div_for_products(P1, N, MAX_N).
