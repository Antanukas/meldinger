(ns meldinger.server.server
  (:use [org.httpkit.server])
  (:require [clojure.data.json :as json]))

(defn ping-pong [data]
  (if (= data {:type "ping"}) {:type "pong"} {:type "not-a-ping"}))

(defn handle-ws [data-map]
  (ping-pong data-map))

(defn ws-receive-handler [channel]
  (fn [^String data]
    (let [req (json/read-str data :key-fn keyword)
          resp (handle-ws req)
          resp-as-string (json/write-str resp)]
      (if resp (send! channel resp-as-string) nil))))

(defn handler [req]
  (with-channel req channel
                (on-receive channel (ws-receive-handler channel))))

(def ^:private server-atom (atom nil))

(defn start-server
  ([] (start-server {}))
  ([{port :port, :or {port 8081}}]
   (do
     (println "Starting Meldinger server...")
     (reset! server-atom (run-server handler {:port port}))
     (println (str "Meldinger started. port: " port)))))

(defn stop-server []
  (when-not (nil? @server-atom)
    (println "Stopping Meldinger server...")
    (@server-atom :timeout 5000)
    (reset! server-atom nil)
    (println "Stopping Meldinger server... Done")))