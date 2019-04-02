(ns first-web-app.handler.main
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [first-web-app.util.response :as res]
            [first-web-app.view.main :as view]))

(defn home [req]
  #_(throw (Exception. "test Ecsep!!!")) ;; prone sample
  (-> (view/home-view req)
      res/response
      res/html))

(defroutes main-routes
  (GET "/" _ home)
  (route/not-found "<h1>Not Found</h1>"))



