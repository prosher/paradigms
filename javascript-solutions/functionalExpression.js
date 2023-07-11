"use strict"
function calcArgs(func) {
    const calcedFunc = (...args) => (...vars) => func(...args.map(arg => arg(...vars)));
    calcedFunc.len = func.length;
    return calcedFunc;
}

const add = calcArgs((a, b) => a + b);
const subtract = calcArgs((a, b) => a - b);
const multiply = calcArgs((a, b) => a * b);
const divide = calcArgs((a, b) => a / b);
const negate = calcArgs(a => -a);
const madd = calcArgs((a, b, c) => a * b + c);
const floor = calcArgs(Math.floor);
const ceil = calcArgs(Math.ceil);
const varToIdx = {'x': 0, 'y': 1, 'z': 2};

const variable = name => (...args) => args[varToIdx[name]];
const cnst = value => () => value;
const one = cnst(1);
const two = cnst(2);
const cnsts = new Map([['one', one], ['two', two]]);

const operations = new Map([
    ['negate', negate],
    ['_', floor], ['^', ceil],
    ['*+', madd],
    ['*', multiply], ['/', divide],
    ['+', add], ['-', subtract],
]);

function parse(expr) {
    const stack = [];
    const squash = op => op(...stack.splice(-op.len));
    const selectVarCnst = a =>
        cnsts.has(a) ? cnsts.get(a) :
        isFinite(a) ? cnst(Number(a)) :
        variable(a);
    expr.trim().split(/\s+/).forEach(
        token => stack.push(
            operations.has(token) ? squash(operations.get(token)) : selectVarCnst(token)
        ));
    return stack[0];
}
