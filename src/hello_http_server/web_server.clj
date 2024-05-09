(ns hello-http-server.web-server
  (:require [ring.adapter.jetty :as jetty]
            [hello-http-server.router :as router]
            [com.stuartsierra.component :as component]))


(defrecord WebServer [port server db]
  component/Lifecycle

  (start [this]
    (println "Starting http server on port:" port)
    (let [server (jetty/run-jetty  (router/app db) {:port port :join? false})]
      (assoc this :server server)))
  (stop [this]
    (println "Stopping http server...")
    (.stop server)
    (assoc this :server nil)))


(defn new-web-server
  [port]
  (component/using (map->WebServer {:port port})
                   [:db]))
