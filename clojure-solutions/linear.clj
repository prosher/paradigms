(defn tensor-size [t]
  (letfn
    [(t-size [t]
       ((fn [t coll]
          (cond
            (nil? t) coll
            (number? t) coll
            (vector? t) (recur (first t) (cons (count t) coll))))
        t '()))
     (t-size-check [t coll]
       (cond
         (nil? t) false
         (number? t) (empty? coll)
         (vector? t) (and
                       (seq coll)
                       (== (first coll) (count t))
                       (every? #(t-size-check % (rest coll)) t))))]
    (let [size (reverse (t-size t))]
      (if (t-size-check t size) size))))
(defn vec? [v] (== 1 (count (tensor-size v))))
(defn matrix? [m] (== 2 (count (tensor-size m))))
(defn tensor? [t] (boolean (tensor-size t)))
(defn same-size-tensors? [& ts]
  (let [tsizes (mapv tensor-size ts)]
    (and (first tsizes) (apply = tsizes))))
(defn multi-arg-binary [f]
  (fn ([arg & args] (reduce f arg args))))
(defn tensor-elem-op-checked [f type-checker]
  (fn op [& ts]
    {:pre  [(and
              ;(every? type-checker ts)
              (apply same-size-tensors? ts))]
     :post [(and
              ;(type-checker %)
              (apply same-size-tensors? % ts))]}
    (if (every? number? ts)
      (apply f ts)
      (apply mapv op ts))))
(defn t-op [f] (tensor-elem-op-checked f tensor?))
(def t+ (t-op +'))
(def t- (t-op -'))
(def t* (t-op *'))
(def td (t-op /))
(defn v-op [f] (tensor-elem-op-checked f vec?))
(def v+ (v-op +'))
(def v- (v-op -'))
(def v* (v-op *'))
(def vd (v-op /))
(defn m-op [f] (tensor-elem-op-checked f matrix?))
(def m+ (m-op +'))
(def m- (m-op -'))
(def m* (m-op *'))
(def md (m-op /))
(defn transpose [m]
  {:pre  [(matrix? m)]
   :post [(or (= (reverse (tensor-size m)) (tensor-size %)) (and (every? empty? m) (= % [])))]}
  (apply mapv vector m))
(def v*s
  (fn [v & ss]
    {:pre  [(and (vec? v) (every? number? ss))]
     :post [(same-size-tensors? v %)]}
    (let [s (apply * ss)]
      (mapv #(* % s) v))))
(def m*s
  (fn [m & ss]
    {:pre  [(and (matrix? m) (every? number? ss))]
     :post [(same-size-tensors? m %)]}
    (let [s (apply * ss)]
      (mapv #(v*s % s) m))))
(def m*v
  (fn [m & vs]
      {:pre  [(and (matrix? m) (vec? (first vs)) (== (second (tensor-size m)) (first (tensor-size (first vs)))))]
       :post [(and (vec? %) (== (first (tensor-size m)) (first (tensor-size %))))]}
    (let [v (apply v* vs)]
      (mapv #(apply + (v* % v)) m))))
(def m*m
  (multi-arg-binary
    (fn [m1 m2]
      {:pre  [(and (matrix? m1) (matrix? m2)
                   (== (second (tensor-size m1)) (first (tensor-size m2))))]
       :post [(and (matrix? %)
                   (== (first (tensor-size m1)) (first (tensor-size %)))
                   (== (second (tensor-size m2)) (second (tensor-size %))))]}
      (transpose (mapv #(m*v m1 %) (transpose m2))))))
(defn scalar [& vs]
  {:pre  [(and (vec? (first vs)) (apply same-size-tensors? vs))]
   :post [(number? %)]}
  (apply + (apply v* vs)))
(def vect
  (multi-arg-binary
    (fn [v1 v2]
      {:pre  [(apply = '(3) (mapv tensor-size [v1 v2]))]
       :post [(same-size-tensors? % v1 v2)]}
      (let [[x1 y1 z1] v1 [x2 y2 z2] v2]
        [(- (* y1 z2) (* y2 z1))
         (- (* z1 x2) (* z2 x1))
         (- (* x1 y2) (* x2 y1))]))))
