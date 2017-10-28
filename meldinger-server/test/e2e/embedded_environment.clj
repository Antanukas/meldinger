(ns e2e.embedded-environment
  (:require [e2e.websocket-driver :as ws-driver]
            [meldinger-server.server :as server]
            [clojure.test :as test]))

(def env (atom {}))

(defn start []
  (do
    (server/start-server)
    (let [ws (ws-driver/connect "ws://localhost:8081")]
      (reset! env {:ws ws}))))

(defn stop []
  (do (server/stop-server)))

(defn ws [] (:ws @env))
(defn ws-chan []
  ;(get-chan (:ws @env)))
  (:frame-chan (:ws @env)))