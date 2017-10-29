(ns meldinger.server.e2e.suite
  (:require [clojure.test :refer :all]
            [meldinger.server.e2e.infrastructure.embedded-environment :as env]
            [meldinger.server.e2e.websocket-e2e]))

; This is the only way I found in order to start/stop env before and after all E2E tests
; (use-fixtures) runs before and after each test namespace
(deftest ^:e2e e2e
  (try
    (println "Running E2E Suite")
    (env/start)

    (meldinger.server.e2e.websocket-e2e/ping-pong)

    (finally (env/stop))))

(defn test-ns-hook [] (e2e))
