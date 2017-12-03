(ns gossiping-bus-drivers.core
  (:require [clojure.set :as set]))

(defn route [driver-stops]
  (take 480 (cycle driver-stops)))

(defn day-schedule [drivers-stops]
  (apply map vector (map route drivers-stops)))

(defn gossips [drivers-count]
  (for [gossip (range 1 (inc drivers-count))]
    (conj #{} (keyword (str gossip)))))

(defn gossips-exchanged [gossips stops]
  (reduce (fn [acc [stop-num gossips-at-stop]] (update acc stop-num set/union gossips-at-stop))
          {} (map vector stops gossips)))

(defn exchange-gossips [gossips stops]
  (let [gossips-exchanged (gossips-exchanged gossips stops)]
    (for [stop stops] (get gossips-exchanged stop))))

(defn mins-to-get-all-gossips-around [drivers-stops]
  (let [day-schedule (day-schedule drivers-stops)
        drivers-count (count drivers-stops)
        gossips (gossips drivers-count)
        mins-to-get-all-gossips-around (count
                                         (take-while (fn [drivers-gossips]
                                                       (not-every? #(= drivers-count (count %)) drivers-gossips))
                                                     (reductions exchange-gossips gossips day-schedule)))]
    (if (> mins-to-get-all-gossips-around 480)
      :never
      mins-to-get-all-gossips-around)))
