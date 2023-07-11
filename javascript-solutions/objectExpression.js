'use strict';
const nameToIdx = {'x': 0, 'y': 1, 'z': 2};
const diffVars = ['x', 'y', 'z'];
function Expression(...args) {
    this.args = args;
}
Expression.prototype.diff = function(diffVar) {
    if (this.diffed === undefined) {
        this.diffed = diffVars.map(diffVar => this.diffCalc(diffVar, ...this.args));
    }
    return this.diffed[nameToIdx[diffVar]];
};

function AbstractOperation(...args) {
    Expression.call(this, ...args);
}
AbstractOperation.prototype = Object.create(Expression.prototype);
AbstractOperation.prototype.evaluate = function(...vals) {
    return this.calc(...this.args.map(arg => arg.evaluate(...vals)));
};
AbstractOperation.prototype.toString = function() {
    return this.args.map(arg => arg.toString()).concat(this.symbol()).join(' ');
};
AbstractOperation.prototype.prefix = function() {
    return '(' + this.symbol() + ' ' + this.args.map(arg => arg.prefix()).join(' ') + ')';
};
AbstractOperation.prototype.postfix = function() {
    return '(' + this.args.map(arg => arg.postfix()).join(' ') + ' ' + this.symbol() + ')';
};
AbstractOperation.prototype.simplify = function() {
    const simplified = this.args.map(arg => arg.simplify());
    if (simplified.every(arg => arg instanceof Const)) {
        return new Const(new this.constructor(...simplified).evaluate());
    } else {
        return this.simplifyCalc(...simplified);
    }
};

function Const(x) {
    Expression.call(this, x);
    this.getValue = () => this.args[0];
    this.evaluate = this.getValue;
}
Const.prototype = Object.create(Expression.prototype);
Const.prototype.toString = function() { return '' + this.getValue(); };
Const.prototype.prefix = Const.prototype.toString;
Const.prototype.postfix = Const.prototype.toString;
Const.prototype.simplify = function() { return this; };
Const.prototype.diffCalc = () => zero;

const zero = new Const(0);
const one = new Const(1);
const two = new Const(2);

function Variable(name) {
    Expression.call(this, name);
    this.getName = () => this.args[0];
    this.evaluate = (...args) => args[nameToIdx[this.getName()]];
}
Variable.prototype = Object.create(Expression.prototype);
Variable.prototype.diffCalc = (diffVar, v) => v === diffVar ? one : zero;
Variable.prototype.toString = function() { return this.getName(); };
Variable.prototype.prefix = Variable.prototype.toString;
Variable.prototype.postfix = Variable.prototype.toString;
Variable.prototype.simplify = function() { return this; };

const tokenToOp = {};

const makeOp = function(calc, symbol, diffCalc, simplifyCalc) {
    function Operation(...args) {
        AbstractOperation.call(this, ...args);
        this.getArgs = function() { return this.args; }
    }
    tokenToOp[symbol] = Operation;
    Operation.len = calc.length;
    Operation.prototype = Object.create(AbstractOperation.prototype);
    Operation.prototype.constructor = Operation;
    Operation.prototype.diffCalc = diffCalc;
    Operation.prototype.simplifyCalc = simplifyCalc;
    Operation.prototype.calc = calc;
    Operation.prototype.symbol = () => symbol;
    return Operation;
};

const isValue = (c, value) => c instanceof Const && c.getValue() === value;

const Add = makeOp((a, b) => a + b,
    '+',
    (diffVar, a, b) => new Add(a.diff(diffVar), b.diff(diffVar)),
    (a, b) =>
        isValue(a, 0) ? b :
        isValue(b, 0) ? a :
        new Add(a, b)
);
const Subtract = makeOp((a, b) => a - b,
    '-',
    (diffVar, a, b) => new Subtract(a.diff(diffVar), b.diff(diffVar)),
    (a, b) =>
        isValue(a, 0) ? new Negate(b) :
        isValue(b, 0) ? a :
        new Subtract(a, b)
);
const Multiply = makeOp((a, b) => a * b,
    '*',
    (diffVar, a, b) => new Add(new Multiply(a.diff(diffVar), b),
        new Multiply(a, b.diff(diffVar))),
    (a, b) =>
        isValue(a, 1) ? b :
        isValue(b, 1) ? a :
        isValue(a, 0) ? zero :
        isValue(b, 0) ? zero :
        new Multiply(a, b)
);
const Divide = makeOp((a, b) => a / b,
    '/',
    (diffVar, a, b) => new Subtract(new Divide(a.diff(diffVar), b),
        new Divide(new Multiply(a, b.diff(diffVar)), new Multiply(b, b))),
    (a, b) => isValue(a, 0)
        ? zero
        : isValue(b, 1)
            ? a
            : new Divide(a, b)
);

function putMinus(op) {
    if (op instanceof Multiply || op instanceof Divide) {
        return putMinus(op.getArgs()[0]) || putMinus(op.getArgs()[1]);
    }
    if (op instanceof Const) {
        op.args[0] *= -1;
        return true;
    }
    return false;
}

const Negate = makeOp(a => -a,
    'negate',
    (diffVar, a) => new Negate(a.diff(diffVar)),
    a => putMinus(a) ? a : new Negate(a)
);

const sum = array => array.reduce((acc, cur) => acc + cur, 0);
function makeSumSq(n) {
    const sumsq = makeOp((...args) => sum(args.map(e => e * e)),
        'sumsq' + n,
        (diffVar, ...args) => new Multiply(
            two,
            args.reduce((acc, cur) => new Add(acc, new Multiply(cur, cur.diff(diffVar))), zero)
        ),
        function() { return this; }
    );
    sumsq.len = n;
    return sumsq;
}
const Sumsq2 = makeSumSq(2);
const Sumsq3 = makeSumSq(3);
const Sumsq4 = makeSumSq(4);
const Sumsq5 = makeSumSq(5);

function makeDistance(n) {
    const dist = makeOp((...args) => Math.sqrt(sum(args.map(e => e * e))),
        'distance' + n,
        function(diffVar, ...args) {
            return args.reduce((acc, cur) => new Add(acc,
                    new Divide(new Multiply(cur, cur.diff(diffVar)), this)),
                zero);
        },
        function() { return this; }
    );
    dist.len = n;
    return dist;
}
const Distance2 = makeDistance(2);
const Distance3 = makeDistance(3);
const Distance4 = makeDistance(4);
const Distance5 = makeDistance(5);

const Sumexp = makeOp(
    (...args) => sum(args.map(Math.exp)),
    'sumexp',
    (diffVar, ...args) =>
        args.reduce((acc, cur) => new Add(acc, new Multiply(new Sumexp(cur), cur.diff(diffVar))), zero));
const LSE = makeOp(
    (...args) => Math.log(sum(args.map(Math.exp))),
    'lse',
    (diffVar, ...args) => new Divide(new Sumexp(...args).diff(diffVar), new Sumexp(...args))
);

const parse = function(expr) {
    const stack = [];
    const squash = op => new op(...stack.splice(-op.len));
    const selectVarConst = a =>
            isFinite(a) ? new Const(Number(a)) :
                new Variable(a);
    expr.trim().split(/\s+/).forEach(token => stack.push(
        token in tokenToOp ? squash(tokenToOp[token]) :
            selectVarConst(token)));
    return stack[0];
};

const ExprParseError = function(message) {
    this.message = message;
};
ExprParseError.prototype = Object.create(Error.prototype);
ExprParseError.prototype.name = "ExprParseError";
ExprParseError.prototype.constructor = ExprParseError;

const OperationExprParseError = function(message) {
    this.message = message;
};
OperationExprParseError.prototype = Object.create(ExprParseError.prototype);
OperationExprParseError.prototype.name = "OperationExprParseError";
OperationExprParseError.prototype.constructor = OperationExprParseError;

const VariableExprParseError = function(message) {
    this.message = message;
};
VariableExprParseError.prototype = Object.create(ExprParseError.prototype);
VariableExprParseError.prototype.name = "VariableExprParseError";
VariableExprParseError.prototype.constructor = VariableExprParseError;

const assert = function(condition, error, message) {
    if (!condition) {
        throw new error(message);
    }
};

const parsePolish = function(expr, reversed) {
    assert(expr.length !== 0, ExprParseError, "empty expression");
    const leftBracket = reversed ? ')' : '(';
    const rightBracket = reversed ? '(' : ')';
    expr = expr
        .replaceAll(leftBracket, ' ' + leftBracket + ' ')
        .replaceAll(rightBracket, ' ' + rightBracket + ' ')
        .trim().split(/\s+/);
    expr = reversed ? expr.reverse() : expr;

    let i = 0;
    const take = () => expr[i++];
    const test = expected => expr[i] === expected;
    const selectVarConst = function(token) {
        if (isFinite(token)) {
            return new Const(Number(token));
        }
        if (nameToIdx[token] !== undefined) {
            return new Variable(token);
        }
        throw new VariableExprParseError('illegal variable name: ' + token);
    };

    const parseExpr = function() {
        let token = take();
        if (token.charAt(0) !== leftBracket) {
            return selectVarConst(token);
        }
        if (token.length === 1) {
            token = take();
        } else {
            token = token.slice(1)
        }
        const op = tokenToOp[token];
        assert(op !== undefined,
            OperationExprParseError, 'unknown operation: ' + token);
        const args = [];
        while (!test(rightBracket) && (op.len === 0 || args.length < op.len)) {
            args.push(parseExpr());
        }
        assert(op.len === 0 || args.length === op.len, OperationExprParseError,
            '\'' + token + '\' takes ' + op.len + ' arguments, found ' + args.length);
        assert(test(rightBracket),
            ExprParseError, 'missing ' + rightBracket);
        take();
        return new op(...(reversed ? args.reverse() : args));
    };

    const result = parseExpr();
    assert(i === expr.length,
        ExprParseError, 'expected end of expression, found: ' + expr[i]);
    return result;
};

const parsePrefix = expr => parsePolish(expr, false);
const parsePostfix = expr => parsePolish(expr, true);
