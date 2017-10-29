(ns meldinger.server.e2e.websocket-e2e
  (:require [clojure.test :refer :all]
            [meldinger.server.e2e.infrastructure.embedded-environment :as env]
            [meldinger.server.e2e.infrastructure.websocket-driver :as ws-driver]))

(deftest ^:suite ping-pong
  (testing "should handle ping/pong json"
    (do
      (ws-driver/send-json (env/ws) {:type "ping"})
      (is (= {:type "pong"} (ws-driver/pop-frame (env/ws)))))))
