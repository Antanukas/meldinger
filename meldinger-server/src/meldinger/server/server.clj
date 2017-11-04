(ns meldinger.server.server
  (:use [org.httpkit.server])
  (:require [clojure.data.json :as json]
            [meldinger.server.handlers :refer [from-map handle-command]]))

(defn invoke-handler [data-map] (handle-command (from-map data-map)))

(defrecord Ping [type])
(defrecord Pong [type])
(defmethod from-map "Ping" [data-map] (map->Ping data-map))
(defmethod handle-command Ping [ping] (->Pong "Pong"))


(defn ws-receive-handler [channel]
  (fn [^String data]
    (let [req (json/read-str data :key-fn keyword)
          resp (invoke-handler req)
          _ (println "aaa" resp)
          resp-as-string (json/write-str resp)
          _ (println "ooo" resp-as-string)]
      (if resp-as-string (send! channel resp-as-string) nil))))

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