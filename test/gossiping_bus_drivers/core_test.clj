(ns gossiping-bus-drivers.core-test
  (:require [clojure.test :refer :all]
            [gossiping-bus-drivers.core :refer :all]))

(deftest a-route-should
  (testing "have 480 stops"
    (is (= 480 (count (route [3 1 2 3])))))
  (testing "routes are cyclic"
    (let [stops [3 1 2 3]]
      (is (= [3 1 2 3] (take (count stops) (drop (count stops) (route stops))))))))

(deftest day-schedule-should
  (let [drivers-stops [[3 1 2 3] [3 2 3 1] [4 2 3 4 5]]]
    (testing "have 480 stops"
      (is (= 480 (count (day-schedule drivers-stops)))))
    (testing "contain a stop for each driver at every minute"
      (let [drivers-count (count drivers-stops)]
        (is (= true (every? #(= drivers-count (count %)) (day-schedule drivers-stops))))))))

(deftest gossips-should
  (let [drivers-stops [[3 1 2 3] [3 2 3 1] [4 2 3 4 5]]
        drivers-count (count drivers-stops)]
    (testing "contain a gossip per driver"
      (is (= drivers-count (count (gossips drivers-count)))))))

(deftest gossips-exchanged-should
  (testing "not happen when stops are different for all drivers"
    (let [stops [96 97 98 99]
          gossips [#{:1} #{:2} #{:3} #{:4}]]
      (is (= {96 #{:1} 97 #{:2} 98 #{:3} 99 #{:4}} (gossips-exchanged stops gossips)))))
  (testing "happen when two drivers meet in a stop"
    (let [stops [96 97 96 97]
          gossips [#{:1 :2} #{:2} #{:1 :3} #{:4}]]
      (is (= {96 #{:1 :2 :3} 97 #{:2 :4}} (gossips-exchanged stops gossips))))))

(deftest exchange-gossips-should
  (testing "do nothing when no drivers meet at the same stop"
    (let [stops [96 97 98 99]
          gossips [#{:1} #{:2} #{:3} #{:4}]]
      (is (= gossips) (exchange-gossips stops gossips))))
  (testing "cause gossips to be exchanged when drivers meet a the same stop"
    (let [stops [96 97 96 97]
          gossips [#{:1 :2} #{:2} #{:1 :3} #{:4}]]
      (is (= [#{:1 :2 :3} #{:2 :4} #{:1 :2 :3} #{:2 :4}]) (exchange-gossips stops gossips)))))