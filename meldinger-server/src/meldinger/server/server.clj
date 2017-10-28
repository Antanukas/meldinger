(ns meldinger.server.server
  (:use [org.httpkit.server]))

(defn ping-pong [data]
  (if (= data "ping") "pong" "not a ping"))

(defn handler [req]
  (with-channel req channel
                (on-receive channel (fn [data] (send! channel (ping-pong data))))))

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