(ns hello-http-server.database
  (:require [com.stuartsierra.component :as component]
            [next.jdbc :as jdbc]))

;;"jdbc:postgresql://<hostname>:port/<database-name>?user=<username>&password=<password>&sslmode=require"


(defn- data-source
  [host port dbname user password]
  (str "jdbc:postgresql://" host ":" port "/" dbname "?user=" user "&password=" password))

(defrecord Database [host database]
  component/Lifecycle
  ;; Lifecycle functions
  ;; -------------------
  ;;
  ;; The `start` and `stop` functions are called by the component system to
  ;; start and stop the component.
  ;;
  ;; In this case, we don't need to do anything special when starting or stopping
  ;; the database, so we just return nil.
  (start [this]
    (println "Connecting to Postgres on" host ", accessing " database "database on 5432")
    (let [conn (jdbc/get-connection (data-source host "5432" database "postgres" "postgres"))]
      (assoc this :connection conn)))
  (stop [this]
    (println "Disconnecting from Database")
    (when-let [conn (:connection this)]
      (.close conn))
    (assoc this :connection nil)))

(defn new-database [host database]
  (map->Database {:host host :database database}))

(defprotocol IDB
  (query [this sql-params]))

(extend-type Database
  IDB
;; Query the database using SQL.
  (query [component sql-params]
    (let [conn (:connection component)]
      (jdbc/execute! conn sql-params))))
