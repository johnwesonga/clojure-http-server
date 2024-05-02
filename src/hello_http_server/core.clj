(ns hello-http-server.core
  (:gen-class) ; for -main method in uberjar
  (:require [com.stuartsierra.component :as component]
            [hello-http-server.database :as database]
            [hello-http-server.web-server :as web-server]))

(defn system-map [config]
  (let [{:keys [host database port]} config]
    (component/system-map
     :db (database/new-database host database)
     :http (web-server/new-web-server port))))

(defn -get-env-or-default [key default-value]
  (or (System/getenv key) default-value))

(defn start-all []
  (def system (component/start (system-map {:port 8000
                                            :host (-get-env-or-default "DB_HOST" "localhost")
                                            :database "players"}))))

(defn stop-all []
  (component/stop system))

(defn -main [& args]
  (component/start (system-map {:port 8000
                                :host (-get-env-or-default "DB_HOST" "localhost")
                                :database "players"})))

(defn dev-main [& args]
  (component/start (system-map {:port 8000
                                :host (-get-env-or-default "DB_HOST" "localhost")
                                :database "players"})))

