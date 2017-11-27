(defproject gossiping-bus-drivers "0.1.0-SNAPSHOT"
  :description "Gossiping Bus Drivers Kata"
  :url "http://kata-log.rocks/gossiping-bus-drivers-kata"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]]
  :main ^:skip-aot gossiping-bus-drivers.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :plugins [[lein-auto "0.1.2"]])
