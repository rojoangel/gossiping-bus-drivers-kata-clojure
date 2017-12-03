(ns gossiping-bus-drivers.core-test
  (:require [clojure.test :refer :all]
            [gossiping-bus-drivers.core :refer :all]))

(deftest a-route-should
  (testing "have 480 stops"
    (is (= 480 (count (route [3 1 2 3])))))
  (testing "routes are cyclic"
    (let [stops [3 1 2 3]]
      (is (= [3 1 2 3] (take (count stops) (drop (count stops) (route stops))))))))
