(ns meldinger.server.commands)

(defprotocol Command)
; TODO good way to convert to map
(defrecord CreateChatroomCommand [^String chatroom-id ^String type] Command)
(defrecord SendMessageCommand [^String chatroom-id ^String text ^String type] Command)