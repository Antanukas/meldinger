(ns e2e.websocket-e2e
  (:require [clojure.test :refer :all]
            [e2e.embedded-environment :as env]
            [e2e.websocket-driver :as ws-driver]
            [clojure.core.async :as async :refer [<!!]]))

(defn timed<!!
  ([chan]
   (timed<!! chan 1000))
  ([chan timeout-ms]
   (let [timed-chan (async/timeout timeout-ms)
         payload (<!! (async/pipe chan timed-chan))]
     payload)))

(deftest ^:suite ping-pong
  (testing "should respond with pong to ping"
    (let [_ (ws-driver/send-text (env/ws) "ping")
          message (timed<!! (env/ws-chan))]
      (is (= "pong" message)))))
