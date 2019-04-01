(ns first-web-app.core
  (:require [compojure.core :refer [routes]]
            [ring.adapter.jetty :as server]
            [first-web-app.handler.main :refer [main-routes]]
            [first-web-app.handler.todo :refer [todo-routes]]))
   
(defonce server (atom nil))

(def app
  (routes
   todo-routes
   main-routes))

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

