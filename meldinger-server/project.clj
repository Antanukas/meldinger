(defproject meldinger-server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [http-kit "2.2.0"]

                 [org.clojure/core.async "0.3.443"]
                 [http.async.client "1.2.0"]
                 [stylefruits/gniazdo-jsr356 "1.0.0"]
                 [com.jakemccrary/lein-test-refresh "0.21.1"]]

  :profiles {:dev {:plugins [[com.jakemccrary/lein-test-refresh "0.21.1"]
                             [venantius/ultra "0.5.1"]]}}
  :test-selectors {:default (complement :suite)
                   :e2e :e2e})
