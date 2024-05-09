(ns hello-http-server.handlers
  (:require [clojure.java.io :as io]
            [clostache.parser :as clostache]
            [hello-http-server.database :as db]
            [ring.util.response :as rr]
            [selmer.parser :as parser]
            [ring.util.http-response :refer [content-type ok]]))


(def selmer-opts {:custom-resource-path (io/resource "public")})

(defn render
  [request template & [params]]
  (-> (parser/render-file template (assoc params :page template) selmer-opts)
      (rr/response)))

(defn render-helper
  [view data]
  (clostache/render-resource (str "public/" view ".html") data))


(defn playersHandler
  [db request]
  (let [players (db/query db ["SELECT * FROM players"])]
    (rr/response
     (render-helper "players" {:players
                               (map (fn [{:keys [:players/id :players/name]}] {:id id :name name})
                                    players)}))))

(defn home
  [request db]
  (render request "index.html" {}))

(def mock-players-list
  [{:name "john" :age 43 :score 600}
   {:name "peter" :age 41 :score 560}])

(defn players
  [db request]
  (let [players (db/query db ["SELECT * FROM players"])]
    (render request "players.html" {:players players})))

(defn indexHandler
  [db request]
  (render-helper "index.html" {}))