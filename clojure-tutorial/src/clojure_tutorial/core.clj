(ns clojure-tutorial.core
  (:gen-class))

(use 'clojure.java.io)

(defn write-to-file
  [file text]
  (with-open [wrtr (writer file)]
    (.write wrtr text)))

(defn read-from-file
  [file]
  (try
    (println (slurp file))

  (catch Exception e (println "Error : " (.getMessage e)))))

;; Apend to file
(defn append-to-file
  [file text]
  (with-open [wrtr (writer file :append true)]
    (.write wrtr text)))

(defn read-line-from-file
  [file]
  (with-open [rdr (reader file)]
    (doseq [line (line-seq rdr)]
      (println line))))


 ;; Atoms and watchers examples - used to change values
(defn atom-ex
  [x]

  (def atomEx (atom x))

  (add-watch atomEx :watcher
       (fn [key atom old-state new-state]
         (println "atomEx changed from "
                  old-state " to " new-state)))

  (println "1st x" @atomEx)
  (reset! atomEx 10)
  (println "2nd x" @atomEx)
  (swap! atomEx inc)
  (println "Increment x" @atomEx)
)

 ;; Agent examples
(defn agent-ex
  []

  (def tickets-sold (agent 0))
  (send tickets-sold + 15)

  (println "Tickets " @tickets-sold)  ; doesn't allow Agent to update, send hasn't completed

  (send tickets-sold + 10)
  (await-for 100 tickets-sold)       ; waits for Agent to update total
  (println "Tickets " @tickets-sold)

  (shutdown-agents)
)


   ;; Math functions
 (defn math-stuff
   []
   (println (+ 1 2 3))
   (println (- 5 3 2))
   (println (* 2 5))
   (println (/ 10 5))
   (println (mod 12 5))
   (println(inc 5))            ; increment
   (println(dec 5))            ; decrement
   (println(Math/abs -10))     ; Absolute value
   (println(Math/cbrt 8))      ; Cube Root
   (println(Math/sqrt 4))      ; Square Root
   (println(Math/ceil 4.5))    ; Round up
   (println(Math/floor 4.5))   ; Round down
   (println(Math/exp 1))       ; e to the power of 1
   (println(Math/hypot 2 2))   ; sqrt(x^2 + y^2)
   (println(Math/log 2.71828)) ; Natural logarithm
   (println(Math/log10 100))   ; Base 10 log
   (println(Math/max 1 5))     ; Max value
   (println(Math/min 1 5))     ; Min value
   (println(Math/pow 2 2))     ; Power

   (println(rand-int 20))

   (println (reduce + [1 2 3]))
   (println Math/PI)
 )


 ;; Function examples
 (defn say-hello
   "Receives a name with 1 parameter and responds"
   [name]

   (println "Hello again" name))

 (defn get-sum
   [x y]

   (println(+ x y)))

 (defn get-sum-more
   ([x y z]
    (println(+ x y z)))

   ([x y]
    (println(+ x y))))


 ;; Destructuring - binding of values in a data structure to symbols
 (defn destruct
   []
   (def vectVals [1 2 3 4])
   (let [[one two & the-rest] vectVals]
     (println one two the-rest)))

 ;; Struct map
 (defn struct-map-ex
   []
   (defstruct Customer :Name :Phone)
   (def cust1 (struct Customer "Doug" 3092618133))
   (def cust2 (struct-map Customer :Name "Sally"
                :Phone 5551212))
   (println cust1)
   (println (:Name cust2)))


 ;; Macros - code generator - ` tells clojure to return without evaluating
 (defmacro discount
   ([cond dis1 dis2]
    (list `if cond dis1 dis2)))

 (defmacro reg-math
   [calc]
   (list (second calc) (first calc) (nth calc 2)))

 (defmacro do-more
   [cond & body]
   (list `if cond (cons 'do body)))

 ; Syntax quoting
 (defmacro do-more-2
   [cond & body]

   `(if ~cond (do ~@body)))

;;
;;


(defn -main
  [& args]

 ;; Data manipulations
  (def aString "Hello")
  (def aDouble 1.234)
  (def aLong 15)
 ;; (format "This is a string %s" aString)
 ;; (format "5 spaces and %5d" aLong)
 ;; (format "Leading zeroes %08d" aLong)
 ;; (format "%-4d left justified" aLong)
 ;; (format "3 decimals %.3f" aDouble)


 ;; Equality
  ; = is equal
  ; not= is not equal

 ;; and / or / true / false / not true / not false

  ;; if then else
  (defn can-vote
    [age]
    (if (>= age 18)
      (println "You can vote")
      (println "You can't vote")
      ))

  ;; do logic
  (defn can-do-more
    [age]
    (do (println "You can drive")
      (println "You can vote"))
      (println "You can't vote"))

  ;; When example
  (defn when-ex
    [tof]
    (when tof
      (println "1st thing")
      (println "2nd thing")))

  ;; Cond example
  (defn what-grade
    [n]
    (cond
      (< n 5) (println "Preschool")
      (= n 5) (println "Kindergarten")
      (and (> n 5) (,= n 18)) (format "Go to grade %d"
                                      (- n 5))
      :else "Go to College"))

  ;; Loops
  (defn one-to-x
    [x]
    (def i (atom 1))
    (while (<= @i x)
      (do
        (println @i)
        (swap! i inc))))

  ;; Do something a specific number of times
  (defn dbl-to-x
    [x]
    (dotimes [i x]
      (println (* i 2))))

 ;; Loop number of times against an input value
  (defn triple-to-x
    [x y]
    (loop [i x]
      (when (< i y)
        (println (* i 3))

        (recur (+ i 1)))))


 ;; Iterating through a sequence
  (defn print-list
    [& nums]
    (doseq [x nums]
      (println x)))


 ;; List manipulations
  (println (list "Dog" 1 3.4 true))
  (println (first (list 1 2 4)))
  (println (rest (list 1 2 4)))  ; returns all but 1st in list
  (println (nth (list 1 2 4) 1)) ; index starts with 0
  (println (list* 1 2 [3 4])) ; adds 3 and 4 to list
  (println (cons 3 (list 1 2)))  ; cons adds one value to left of list

 ;; Sets - lists of unique values
  (println (set '(1 1 2)))  ;returns unique values in list
  (println (get (set '(3 2)) 2)) ; returns the 2nd index value
  (println (conj (set '(3 2)) 1)) ; adds 1 to beginning of set - 1 3 2
  (println (contains? (set '(3 2)) 2))
  (println (disj (set '(3 2)) 2))  ; removes the 2 from set

 ;; Vector manipulations
  (println (get (vector 3 2) 1)) ; gets the first value in vector - index 0
  (println (conj (vector 3 2) 1)) ; appends value to vector at end
  (println (pop (vector 3 2))) ; removes the first value in vector
  (println (subvec (vector 1 2 3 4) 1 3)) ; removes 1st and 3rd index values [2 3]


 ;; Map manipulations
  (println (hash-map "Name" "Derek" "Age" 51)) ; Creates a 2 name/value map
  (println (sorted-map 3 51 2 "Derek" 1 "Vikings")) ; Sorts map in name order
  (println (find (hash-map "Name" "Derek" "Age" 51) "Name")) ; Finds value for key [Name Derek]
  (println (contains? (hash-map "Name" "Derek" "Age" 51) 51))
  (println (keys (hash-map "Name" "Derek" "Age" 51))) ; Returns just (Age Name)
  (println (merge-with + (hash-map "Name" "Derek") (hash-map "Age" 51))) ; Combines {Age 51, Name Derek}

  (atom-ex 5) ; executes atom-ex function


  (agent-ex)


  (math-stuff)

  (say-hello "Derek")
  (get-sum 4 5)
  (get-sum-more 1 2 3)


  ;; Executing conditional logic
  (can-vote 17)
  (can-do-more 24)
  (when-ex true)
  (what-grade 19)


  ;; Executing loop logic
  (one-to-x 5)
  (dbl-to-x 5)
  (triple-to-x 1 5)

  (print-list 7 8 9)


  ;; Accessing files and writing to file
  (write-to-file "test.txt" "This is a sentence\n")
  (read-from-file "test.txt")
  (append-to-file "test.txt" "This is another sentence\n")
  (read-line-from-file "test.txt")


  ;; Destructuing
  (destruct)

  ;; Struct map
  (struct-map-ex)


  ;; Anonymous functions
  (println(map (fn [x] (* x x)) (range 1 10)))  ;  will generate list 1 4 9 16 25 36 49 64 81

  ;; compact anonymous function (# = fn) (% = value from the list (range 1 10))
  (map #(* % 3) (range 1 10)) ; will generate list of each number in range * 3

  ;; anonymous function with two inputs (multiplied 1st and 2nd arguements = 6)
  (#(* %1 %2) 2 3)



  (defn custom-multiplier
    [mult-by]
    #(* % mult-by))
  (def mult-by-3 (custom-multiplier 3))
  (mult-by-3 3)

  ;; Filter lists
  (take 2 [1 2 3])            ;  (1 2)
  (drop 1 [1 2 3])            ;  (2 3)
  (take-while neg? [-1 0 1])  ; (-1)
  (drop-while neg? [-1 0 1])  ; (0 1)
  (filter #(> % 2) [1 2 3 4]) ; (3 4)


  ;execute macro
  (discount (> 25 65) (println "10% Off")
            (println "Full Price"))

  (reg-math (2 + 5))

  (do-more (< 1 2) (println "Hello")
           (println "Hello Again"))

  (do-more-2 (< 1 2) (println "Hello")
           (println "Hello Again"))


)
