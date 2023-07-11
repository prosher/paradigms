%%% map_build(ListMap, TreeMap)
map_build([], tree(empty)).
map_build([(K, V) | T], Result) :-
	map_build(T, Tree), !, map_put(Tree, K, V, Result).

%%% map_get(TreeMap, K, V)
map_get(tree(Root), K, V) :- tree_search(Root, K, V), V \= empty.

%%% map_put(TreeMap, K, V, Result)
map_put(tree(Root), K, V, tree(Result)) :- tree_put(Root, leaf(K, V), Result).

%%% map_remove(TreeMap, K, Result)
map_remove(tree(Root), K, tree(Result)) :- tree_remove(Root, K, Result).

%%% map_putIfAbsent(TreeMap, K, V, Result)
map_putIfAbsent(tree(Root), K, V, tree(Result)) :-
    tree_search(Root, K, empty), tree_put(Root, leaf(K, V), Result).
map_putIfAbsent(tree(Root), K, _, tree(Root)) :- not tree_search(Root, K, empty).


%%% tree_search(Root, K, Result)
tree_search(empty, _, empty) :- !.
tree_search(leaf(K, V), K, V) :- !.
tree_search(leaf(K1, _), K, empty) :- K \= K1, !.
tree_search(node(K, V, _, _, _), K, V) :- !.
tree_search(node(K1, _, L, _, _), K, Result) :- K < K1, tree_search(L, K, Result), !.
tree_search(node(K1, _, _, R, _), K, Result) :- K > K1, tree_search(R, K, Result), !.

%%% max(A, B, R)
max(A, B, A) :- A >= B.
max(A, B, B) :- A < B.

%%% tree_height(Root, Result).
tree_height(empty, 0).
tree_height(leaf(_, _), 1).
tree_height(node(_, _, L, R, _), H2) :-
    tree_height(L, HL), tree_height(R, HR), max(HL, HR, H1), H2 is H1 + 1.

%%% tree_update_height(Root, Result)
tree_update_height(node(K, V, L, R, _), node(K, V, L, R, H1)) :-
    tree_height(node(K, V, L, R, _), H1).

%%% tree_height_diff(Root, Result).
tree_height_diff(node(_, _, L, R, _), Result) :-
    tree_height(L, HL), tree_height(R, HR), Result is HL - HR.

%%% tree_rebalance(Root, Result)
tree_rebalance(node(K, V, L, R, H), Result1) :-
    tree_height_diff(node(K, V, L, R, H), H), H = 2, tree_height_diff(L, H1), H1 = -1,
    tree_rotate_left(L, Result), tree_rotate_right(node(K, V, Result, R, H), Result1), !.
tree_rebalance(node(K, V, L, R, H), Result1) :-
    tree_height_diff(node(K, V, L, R, H), H), H = -2, tree_height_diff(R, H1), H1 = 1,
    tree_rotate_right(L, Result), tree_rotate_left(node(K, V, Result, R, H), Result1), !.
tree_rebalance(Root, Result) :-
    tree_height_diff(Root, H), H = 2, tree_rotate_right(Root, Result), !.
tree_rebalance(Root, Result) :-
    tree_height_diff(Root, H), H = -2, tree_rotate_left(Root, Result), !.
tree_rebalance(Root, Root).

%%% tree_rotate_right(Root, Result)
tree_rotate_right(node(K1, V1, node(K2, V2, L2, R2, _), R1, _), Result1) :-
    tree_update_height(node(K1, V1, R2, R1, _), NR1),
    tree_update_height(node(K2, V2, L2, NR1, _), Result1).
%%% tree_rotate_left(Root, Result)
tree_rotate_left(node(K1, V1, L1, node(K2, V2, L2, R2, _), _), Result1) :-
    tree_update_height(node(K1, V1, L1, L2, _), NL1),
    tree_update_height(node(K2, V2, NL1, R2, _), Result1).

%%% tree_put(Root, Node, Result)
tree_put(empty, leaf(K, V), leaf(K, V)).
tree_put(leaf(K, _), leaf(K, V), leaf(K, V)).
tree_put(leaf(K1, V1), leaf(K, V), node(K1, V1, leaf(K, V), empty, 1)) :- K < K1.
tree_put(leaf(K1, V1), leaf(K, V), node(K1, V1, empty, leaf(K, V), -1)) :- K > K1.
tree_put(node(K, _, L, R, H), leaf(K, V), node(K, V, L, R, H)).
tree_put(node(K1, V1, L, R, _), leaf(K, V), node(K1, V1, Result1, R, H1)) :-
    K < K1, tree_put(L, leaf(K, V), Result), !,
    tree_rebalance(Result, Result1), tree_height(Result1, H1).
tree_put(node(K1, V1, L, R, _), leaf(K, V), node(K1, V1, L, Result1, H1)) :-
    K > K1, tree_put(R, leaf(K, V), Result), !,
    tree_rebalance(Result, Result1), tree_height(Result1, H1).

%%% tree_remove(Root, K, Result)
tree_remove(empty, _, empty).
tree_remove(leaf(K, _), K, empty).
tree_remove(leaf(K1, V1), K, leaf(K1, V1)) :- K \= K1.
tree_remove(node(K, _, L, empty, _), K, L) :- !.
tree_remove(node(K, _, empty, R, _), K, R) :- !.
tree_remove(node(K, _, L, R, H), K, node(K1, V1, L, NewR, H)) :-
    tree_min(R, (K1, V1)), tree_remove(R, K1, NewR), !.
tree_remove(node(K1, V1, L, R, _), K, Result2) :-
    K < K1, tree_remove(L, K, Result), !,
    tree_rebalance(Result, Result1), tree_height(Result1, H1),
    tree_build_node(K1, V1, Result1, R, H1, Result2).
tree_remove(node(K1, V1, L, R, _), K, Result2) :-
	K > K1, tree_remove(R, K, Result), !,
	tree_rebalance(Result, Result1), tree_height(Result1, H1),
	tree_build_node(K1, V1, L, Result1, H1, Result2).

%%% tree_min(Root, Result)
tree_min(leaf(K, V), (K, V)).
tree_min(node(K, V, empty, _, _), (K, V)).
tree_min(node(_, _, L, _, _), Result) :- L \= empty, tree_min(L, Result), !.

tree_build_node(K, V, empty, empty, _, leaf(K, V)).
tree_build_node(K, V, L, R, H, node(K, V, L, R, H)).