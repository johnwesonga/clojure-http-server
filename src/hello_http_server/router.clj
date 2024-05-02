(ns hello-http-server.router
  (:require
   [hello-http-server.handlers :as handlers]
   [compojure.core :refer [routes GET]]
   [compojure.route :as route]
   [ring.middleware.resource :refer [wrap-resource]]
   [ring.logger :as logger]))



(defn app-routes
  [app-component]
  (routes
   (GET "/" request (handlers/indexHandler app-component request))
   (GET "/players" request (handlers/playersHandler app-component request))
   (route/not-found "<h1>Page not found</h1>")))

(defn app
  [app-component]
  (-> app-component
      app-routes
      (logger/wrap-with-logger)
      (wrap-resource "public")))