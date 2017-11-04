(ns meldinger.server.events)

(defprotocol Event)

(defrecord ChatroomCreatedEvent [^String chatroom-id ^String type] Event)
(defrecord MessageSentEvent [^String chatroom-id ^String text ^String type] Event)
