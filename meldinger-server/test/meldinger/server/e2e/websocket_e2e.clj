(ns meldinger.server.e2e.websocket-e2e
  (:require [clojure.test :refer :all]
            [meldinger.server.e2e.infrastructure.random-values :refer :all]
            [meldinger.server.e2e.infrastructure.embedded-environment :as env]
            [meldinger.server.e2e.infrastructure.websocket-driver :as ws-driver]
            [meldinger.server.commands :refer :all]
            [meldinger.server.events :refer :all])
  (:import (java.util UUID)))

(deftest ^:suite ping-pong
  (testing "should handle ping/pong json"
    (do
      (ws-driver/send-json (env/ws) {:type "Ping"})
      (is (= {:type "Pong"} (ws-driver/pop-frame (env/ws)))))))


(deftest ^:suite commands
  (testing "CreateChatroomCommand"
    (let [chatroom-id (a-uuid)
          command (->CreateChatroomCommand chatroom-id "CreateChatroomCommand")
          expected-event (->ChatroomCreatedEvent chatroom-id "ChatroomCreatedEvent")

          _ (ws-driver/send-json (env/ws) command)

          _ (println "zz" (ws-driver/pop-frame (env/ws)))
          received-event (map->ChatroomCreatedEvent (ws-driver/pop-frame (env/ws)))]
      (is (= expected-event received-event)))))


