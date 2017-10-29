(ns meldinger.server.e2e.infrastructure.embedded-environment
  (:require [meldinger.server.e2e.infrastructure.websocket-driver :as ws-driver]
            [meldinger.server.server :as server]
            [clojure.test :as test]))

(defrecord Environment [ws])

(def ^:private env (atom nil))

(defn ws [] (:ws @env))
(defn ws-chan []
  (ws-driver/chan-of (ws)))

(defn start []
  (do
    (server/start-server)
    (let [ws (ws-driver/connect "ws://localhost:8081")]
      (reset! env (->Environment ws)))))

(defn stop []
  (do (server/stop-server)))