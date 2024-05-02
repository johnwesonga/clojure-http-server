(ns hello-http-server.handlers
  (:require [ring.util.response :as rr]
            [hello-http-server.database :as db]
            [clostache.parser :as clostache]))

(defn render-helper
  [view data]
  (clostache/render-resource (str "public/" view ".html") data))

(defn playersHandler
  [db request]
  (let [players (db/query db ["SELECT * FROM players"])]
    (rr/response
     (clostache/render-resource "public/players.html" {:players
                                                       (map (fn [{:keys [:players/id :players/name]}] {:id id :name name})
                                                            players)}))))

(defn playersHandler
  [db request]
  (let [players (db/query db ["SELECT * FROM players"])]
    (rr/response
     (render-helper "players" {:players
                               (map (fn [{:keys [:players/id :players/name]}] {:id id :name name})
                                    players)}))))


(defn indexHandler
  [db request]
  (render-helper "index" {}))