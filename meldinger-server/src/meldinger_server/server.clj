(ns meldinger-server.server
  (:use [org.httpkit.server]))

(defn ping-pong [data]
  (if (= data "ping") "pong" "not a ping"))

(defn handler [req]
  (with-channel req channel
                (on-close channel (fn [status] (println "channel closed")))
                (if (websocket? channel) (println "WebSocket channel") (println "HTTP channel"))
                (on-receive channel (fn [data] (send! channel (ping-pong data))))))

(def ^:private server-atom (atom nil))

(defn start-server
  ([] (start-server {}))
  ([{port :port, :or {port 8081}}]
   (do
     (println "Starting HTTP Server")
     (reset! server-atom (run-server handler {:port port}))
     (println (str "Server started on " port)))))

(defn stop-server []
  (when-not (nil? @server-atom)
    ;; graceful shutdown: wait 100ms for existing requests to be finished
    ;; :timeout is optional, when no timeout, stop immediately
    (@server-atom :timeout 100)
    (reset! server-atom nil)))