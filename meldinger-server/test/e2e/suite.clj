(ns e2e.suite
  (:require [clojure.test :refer :all]
            [e2e.embedded-environment :as env]
            [e2e.websocket-e2e]))

; This is the only way I found in order to start/stop env before and after all E2E tests
; (use-fixtures) runs before and after each test namespace

(deftest ^:integration e2e
  (try
    (env/start)

    (e2e.websocket-e2e/ping-pong)

    (finally (env/stop))))

(defn test-ns-hook [] (e2e))
