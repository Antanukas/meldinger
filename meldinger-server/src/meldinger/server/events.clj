(ns meldinger.server.events)

(defprotocol Event)

(defrecord ChatroomCreatedEvent [^String chatroom-id ^String type] Event)
