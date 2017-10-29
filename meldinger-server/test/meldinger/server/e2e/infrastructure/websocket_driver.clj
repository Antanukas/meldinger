(ns meldinger.server.e2e.infrastructure.websocket-driver
  (:require [gniazdo.core :as gniazdo]
            [clojure.core.async :as async :refer [>!! <!!]]
            [clojure.data.json :as json]))

(defrecord GniazdoSocket [socket frame-chan])

(defn connect [^String url]
  (let [chan (async/chan 200)
        on-frame (fn [^String data] (>!! chan (json/read-str data :key-fn keyword)))
        socket (gniazdo/connect url :on-receive on-frame)]
    (->GniazdoSocket socket chan)))

(defn chan-of [^GniazdoSocket ws]
  (:frame-chan ws))

(defn close [^GniazdoSocket ws]
  (do
    (gniazdo/close (:socket ws))
    (async/close! (:frame-chan ws))))

(defn send-json [^GniazdoSocket ws data-map]
  (gniazdo/send-msg (:socket ws) (json/write-str data-map)))

(defn- timed<!!
  ([chan]
   (timed<!! chan 1000))
  ([chan timeout-ms]
   (let [timed-chan (async/timeout timeout-ms)
         payload (<!! (async/pipe chan timed-chan))]
     payload)))

(defn pop-frame [^GniazdoSocket ws]
  (timed<!! (chan-of ws)))
