(ns meldinger.server.handlers
  (:require [meldinger.server.commands :refer :all]
            [meldinger.server.events :refer :all])
  (:import (meldinger.server.commands CreateChatroomCommand)))

(defmulti from-map (fn [data-map] (:type data-map)))
(defmulti handle-command (fn [obj] (class obj)))

(defmethod from-map "CreateChatroomCommand" [data-map] (map->CreateChatroomCommand data-map))
(defmethod handle-command CreateChatroomCommand [cmd]
  (->ChatroomCreatedEvent (:chatroom-id cmd) "ChatroomCreatedEvent"))

