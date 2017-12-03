(ns gossiping-bus-drivers.core)

(defn route [driver-stops]
  (take 480 (cycle driver-stops)))

(defn routes [all-drivers-stops]
  (apply map vector (map route all-drivers-stops)))

(defn gossips [drivers-count]
  (for [gossip (range 1 (inc drivers-count))]
    (conj #{} (keyword (str gossip)))))

; some sort of reduce?
;gossips  ;meets/stops  ;gossips
; [#{:1}    [2   ->       [ #{:1 :2}
;  #{:2}     2   ->         #{:1 :2}
;  #{:3}     3   ->         #{:3 :4}
;  #{:4}]    3]  ->         #{:3 :4}]
; don't use true/false 'cos more than one pair of drivers can meet at a stop
; mapping gossips on stops may be helpful {2 #{:1 :2} 3 #{:3 :4}}
; then it can be added to the set with index = stop (key in the map)

; (reduce (fn [driver-gossip meet]
;          (let [exchanged-gossips]
;            add-exchanged-gossips-to-matches)
;          driver-gossips meets)