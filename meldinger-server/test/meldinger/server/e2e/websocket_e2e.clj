(ns meldinger.server.e2e.websocket-e2e
  (:require [clojure.test :refer :all]
            [meldinger.server.e2e.infrastructure.embedded-environment :as env]
            [meldinger.server.e2e.infrastructure.websocket-driver :as ws-driver]
            [clojure.core.async :as async :refer [<!!]]))

(defn timed<!!
  ([chan]
   (timed<!! chan 1000))
  ([chan timeout-ms]
   (let [timed-chan (async/timeout timeout-ms)
         payload (<!! (async/pipe chan timed-chan))]
     payload)))

(deftest ^:suite ping-pong
  (testing "should handle ping/pong json"
    (do
      (ws-driver/send-json (env/ws) {:type "ping"})
      (is (= {:type "pong"} (ws-driver/pop-frame (env/ws)))))))
