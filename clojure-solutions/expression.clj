(defn make-parser [-op-map -constant -variable]
  (fn parser [s]
    ((fn parse [token]
       (cond
         (list? token) (apply (get -op-map (first token)) (map parse (rest token)))
         (number? token) (-constant token)
         (symbol? token) (-variable (str token))))
     (read-string s))))
(defn div ([x] (/ 1 (double x))) ([x & xs] (reduce #(/ (double %1) (double %2)) x xs)))


(defn expr [f] (fn evaluate [var-map] (f var-map)))
(defn constant [value] (expr (constantly value)))
(defn variable [name] (expr (fn [var-map] (get var-map name))))
(defn make-op [op] (fn [& args] (expr (fn [var-map] (apply op (mapv #(% var-map) args))))))
(def add (make-op +'))
(def subtract (make-op -'))
(def multiply (make-op *'))
(def divide (make-op div))
(def negate (make-op #(-' %)))
(def exp (make-op #(Math/exp %)))
(def log (make-op #(Math/log %)))
(defn sumexp [& args] (apply add (map exp args)))
(defn lse [& args] (log (apply sumexp args)))
(def op-map
  {'negate negate
   '*      multiply
   '/      divide
   '+      add
   '-      subtract
   'sumexp sumexp
   'lse    lse})
(def parseFunction (make-parser op-map constant variable))


(definterface Expression
  (^Number evaluate [varMap])
  (diff [^String diffVar])
  (^String toStringInfix []))
(def diffVars ["x" "y" "z"])
(defn makeExpression [evalFunc diffFunc toStringFunc toStringInfixFunc]
  (deftype Expr [args eval diffCalc toStr toStrInfix]
    Expression
    (evaluate [this varMap] ((.-eval this) this varMap))
    (diff [this diffVar] ((.-diffCalc this) this diffVar))
    (toStringInfix [this] ((.-toStrInfix this) this))
    Object
    (toString [this] ((.-toStr this) this)))
  (fn constructor [& args] (user.Expr. args evalFunc diffFunc toStringFunc toStringInfixFunc)))
(declare zero)
(def Constant
  (let [toStr #(str (first (.-args %)))]
    (makeExpression
      (fn [this _] (first (.-args this)))
      (fn [this _] (if (= this zero) this zero))
      toStr
      toStr)))
(def zero (Constant 0.0))
(def one (Constant 1.0))
(def two (Constant 2.0))
(def Variable
  (let [toStr #(first (.-args %))]
    (makeExpression
      #(get %2 (.toLowerCase (str (ffirst (.-args %)))))
      #(if (= %2 (first (.-args %))) one zero)
      toStr
      toStr)))
(def operationMap {})
(defn makeOp [f name diff]
  (let [op (makeExpression
             (fn [this varMap] (apply f (map #(.evaluate % varMap) (.-args this))))
             (fn [this diffVar] (diff (.-args this) diffVar))
             (fn [this] (str "(" name " " (clojure.string/join " " (map #(.toString %) (.-args this))) ")"))
             (fn [this]
               (let [args (map #(.toStringInfix %) (.-args this))]
                 (cond
                   (== 1 (count args)) (apply str name " " args)
                   (== 2 (count args)) (str "(" (first args) " " name " " (second args) ")")))))]
    (def operationMap (assoc operationMap name op))
    op))
(def Add
  (makeOp
    +'
    '+
    (fn [xs diffVar] (apply Add (map #(.diff % diffVar) xs)))))
(def Subtract
  (makeOp
    -'
    '-
    (fn [xs diffVar] (apply Subtract (map #(.diff % diffVar) xs)))))
(declare Divide)
(def Multiply
  (makeOp
    *'
    '*
    (fn [xs diffVar]
      (second
        (reduce
          (fn [[product diffed] arg]
            [(Multiply arg product)
             (Add (Multiply (.diff arg diffVar) product)
                  (Multiply arg diffed))])
          [one zero]
          xs)))))

(def Divide
  (makeOp
    div
    '/
    (fn [xs diffVar]
      (if (== 1 (count xs))
        (.diff (Divide one (first xs)) diffVar)
        (let [divisors (apply Multiply (rest xs))]
          (Subtract
            (Divide (.diff (first xs) diffVar) divisors)
            (Divide (Multiply (first xs) (.diff divisors diffVar))
                    (Multiply divisors divisors))))))))
(def Negate
  (makeOp
    #(-' %)
    'negate
    (fn [xs diffVar] (apply Negate (map #(.diff % diffVar) xs)))))
(defn meansq ([] 0) ([& xs] (/ (apply +' (map #(* % %) xs)) (count xs))))
(def Meansq
  (makeOp
    meansq
    'meansq
    (fn [xs diffVar]
      (apply Add
             (map #(Multiply
                     (Divide two (Constant (count xs)))
                     %
                     (.diff % diffVar))
                  xs)))))
(def RMS
  (makeOp
    (fn [& xs] (Math/sqrt (apply meansq xs)))
    'rms
    (fn [xs diffVar] (Divide (.diff (apply Meansq xs) diffVar)
                             (Multiply two (apply RMS xs))))))
(defn evaluate [obj varMap] (.evaluate obj varMap))
(defn toString [obj] (.toString obj))
(defn diff [obj diffVar] (.diff obj diffVar))
(def parseObject (make-parser operationMap Constant Variable))


(defn bool [x] (if (> x 0) 1 0))
(def Not (makeOp (comp #(bit-xor % 1) bool) '! ()))
(defn bit-op [op] (fn [& args] (apply op (map bool args))))
(def And (makeOp (bit-op bit-and) '&& ()))
(def Or (makeOp (bit-op bit-or) '|| ()))
(def Xor (makeOp (bit-op bit-xor) (symbol "^^") ()))
(defn toStringInfix [obj] (.toStringInfix obj))
(load-file "parser.clj")
(def *all-chars (mapv char (range 0 128)))
(def *letter (+char (apply str (filter #(Character/isLetter %) *all-chars))))
(def *digit (+char (apply str (filter #(Character/isDigit %) *all-chars))))
(def *space (+char (apply str (filter #(Character/isWhitespace %) *all-chars))))
(def *ws (+ignore (+star *space)))
(def *identifier (+str (+seqf cons *letter (+star (+or *letter *digit)))))
(def *number (+map (comp read-string (partial apply str) flatten (partial reduce cons))
                   (+seq
                     (+opt (+char "+-"))
                     (+plus *digit)
                     (+opt (+seq (+char ".") (+plus *digit))))))
(def *const (+map Constant *number))
(def *var (+map Variable *identifier))
(defn *sym [sym] (apply +seqf (constantly sym) (map (comp +char str) (str sym))))
(defn *op [syms parse-seq]
     (apply +or (map #(+map (partial apply (get operationMap %)) (parse-seq %)) syms)))
(defn *unary [syms parser] (*op syms #(+seq (+ignore (*sym %)) *ws parser)))
(defn *build-tree [] (partial reduce #(if (empty? %2) %1 ((get operationMap (first %2)) %1 (second %2)))))
(defn *priority-level [syms next] (+seqf (*build-tree)
                                    next
                                    (+star (+seq *ws (apply +or (map *sym syms)) *ws next))))
(def parseObjectInfix
  (letfn [(*xor [] (*priority-level [(symbol "^^")] (*or)))
          (*or [] (*priority-level ['||] (*and)))
          (*and [] (*priority-level ['&&] (*expr)))
          (*expr [] (*priority-level ['+ '-] (*term)))
          (*term [] (*priority-level ['* '/] (*factor)))
          (*factor [] (+or (*unary ['! 'negate] (delay (*factor)))
                           *var
                           *const
                           (+seqn 1 (+char "(") *ws (delay (*xor)) *ws (+char ")"))))]
    (+parser (+seqn 0 *ws (*xor) *ws))))