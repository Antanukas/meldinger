(ns e2e.websocket-driver
  (:require [gniazdo.core :as gniazdo]
            [clojure.core.async :as async :refer [>!!]]))

(defprotocol WebSocket
  (close [this])
  (get-chan [this]))

(defrecord GniazdoSocket [socket frame-chan]
  WebSocket
  (get-chan [this] (:frame-chan this))
  (close [this]
    (do
      (gniazdo/close (:socket this))
      (async/close! frame-chan))))

(defrecord WebSocketDriverConfig [url])

(defn ^WebSocket connect [^String url]
  (let [chan (async/chan 200)
        on-frame (fn [^String data] (>!! chan data))
        socket (gniazdo/connect url :on-receive on-frame)]
    (->GniazdoSocket socket chan)))

(defn close [ws] (gniazdo/close (:socket ws)))

(defn send-text [ws data] (gniazdo/send-msg (:socket ws) data))
