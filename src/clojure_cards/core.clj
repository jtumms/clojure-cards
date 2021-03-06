(ns clojure-cards.core
  (:gen-class))

(def suits [:clubs :spades :hearts :diamonds])
(def ranks (range 1 14))

(def test-hand #{{:suit :clubs :rank 2}
                 {:suit :spades :rank 3}
                 {:suit :hearts :rank 4}
                 {:suit :diamonds :rank 5}})
    
                 

(defn create-deck []
  (set
    (for [suit suits
          rank ranks]
      {:suit suit :rank rank})))

(defn create-hands [deck]
  (set
    (for [c1 deck
          c2 (disj deck c1)
          c3 (disj deck c1 c2)
          c4 (disj deck c1 c2 c3)]
      #{c1 c2 c3 c4})))

(defn flush? [hand]
  (= 1 (count (set (map :suit hand)))))

(defn four-kind? [hand]
  (= 1 (count (set (map :rank hand)))))

(defn two-pair? [hand]
  (= '(2 2) (vals (frequencies (map :rank hand)))))

(defn three-kind? [hand]
  (some #{3} (vals (frequencies (map :rank hand)))))


(defn straight? [hand]
  (let [sorted-hand (sort (map :rank hand))]
    (or
       (= (set sorted-hand) #{11 12 13 1})
       (and
         (= 4 (count (distinct sorted-hand)))
         (= 3 (- (last sorted-hand) (first sorted-hand))))))) 

(defn straight-flush? [hand]
  (and
     (straight? hand)
     (flush? hand)))
    
                   
(defn -main [& args]
  (let [deck (create-deck)
        hands (create-hands deck)
        hands (filter straight-flush? hands)]
    (println (count hands))))

