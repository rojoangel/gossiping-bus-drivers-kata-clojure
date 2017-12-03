(ns gossiping-bus-drivers.core
  (:require [clojure.set :as set]))

(def turn->mins 480)

(defn route [driver-stops]
  (take turn->mins (cycle driver-stops)))

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

(defn- day-schedule-gossips [drivers-stops]
  (let [drivers-count (count drivers-stops)
        gossips (gossips drivers-count)
        day-schedule (day-schedule drivers-stops)]
    (reductions exchange-gossips gossips day-schedule)))

(defn- all-drivers-aware-of-all-gossips? [drivers-gossips]
  (let [drivers-count (count drivers-gossips)]
    (every? #(= drivers-count (count %)) drivers-gossips)))

(defn- mins-or-never [mins]
  (if (> mins 480)
    :never
    mins))

(defn mins-to-get-all-gossips-around [drivers-stops]
  (->> drivers-stops
       (day-schedule-gossips)
       (take-while (complement all-drivers-aware-of-all-gossips?))
       (count)
       (mins-or-never)))
