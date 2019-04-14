(ns first-web-app.core
  (:require [compojure.core :refer [routes]]
            [ring.adapter.jetty :as server]
            [ring.middleware.flash :as flash]
            [ring.middleware.keyword-params :as keyword-params]
            [ring.middleware.params :as params]
            [ring.middleware.resource :as resource]
            [ring.middleware.session :as session]
            [first-web-app.handler.main :refer [main-routes]]
            [first-web-app.handler.todo :refer [todo-routes]]
            [first-web-app.middleware :refer [wrap-dev]]
            [environ.core :refer [env]]))
   
(defn- wrap [handler middleware opt]
  (if (true? opt)
    (middleware handler)
    (if opt
      (middleware handler opt)
      handler)))

(def app
  (-> (routes
       todo-routes
       main-routes)
      (wrap wrap-dev (:dev env))
      (wrap resource/wrap-resource "public")
      (wrap keyword-params/wrap-keyword-params true)
      (wrap params/wrap-params true)
      (wrap flash/wrap-flash true)
      (wrap session/wrap-session true)))

(defonce server (atom nil))

(defn start-server [& {:keys [host port join?]
                       :or {host "localhost" port 3000 join? false}}]
  (let [port (if (string? port) (Integer/parseInt port) port)]
    (when-not @server
      (reset! server (server/run-jetty
                      #'app
                      {:host host
                       :port port
                       :join? join?})))))

(defn stop-server []
  (when @server
    (.stop @server)
    (reset! server nil)))

(defn restart-server []
  (when @server
    (stop-server)
    (start-server)))

