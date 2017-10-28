(ns meldinger.server.unit.suite
  (:require [clojure.test :refer :all]))

(deftest ^unit unit
  (meldinger.server.unit.core-test/a-test))

(defn test-ns-hook [] (unit))
