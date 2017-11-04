(ns meldinger.server.e2e.infrastructure.random-values
  (:import (java.util UUID)))

(defn a-uuid [] (.toString (UUID/randomUUID)))
