(ns gossiping-bus-drivers.core)

(defn xxx []
  (filter
    (fn [[_ stops]]
      (not= (count (set stops)) (count stops)))             ; all-stops-different?
    (map-indexed                                            ; min->stops
      vector
      (take 480                                             ; day routes
            (map vector
                 (cycle [2 2 3])
                 (cycle [2 3 1])
                 (cycle [5 5 6]))))))
