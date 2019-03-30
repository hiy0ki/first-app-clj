(ns first-web-app.core
  (:require [compojure.core :refer [defroutes context GET]]
            [compojure.route :as route]
            [ring.adapter.jetty :as server]
            [ring.util.response :as res]))

(defn html [res]
  (res/content-type res  "text/html; charset=utf-8"))

(defn home-view [req]
  "<h1>ホーム画面</h1>
   <a href=\"/todo\">TODO 一覧</a>")

(defn home [req]
  (-> (home-view req)
      res/response
      html))

(def todo-list
  [{:title "朝ゴハンを作る"}
   {:title "朝ゴハンを食べる"}
   {:title "朝ゴハンを燃やす"}
   {:title "朝ゴハンを片付ける"}])

(defn todo-index-view [req]
  `("<h1>TODO 一覧</h1>"
    "<ul>"
    ~@(for [{:keys [title]} todo-list]
        (str "<li>" title "</li>"))
    "</ul>"))

(defn todo-index [req]
  (-> (todo-index-view req)
      res/response
      html))
   
(def routes
  {"/" home
   "/todo" todo-index})

(defn match-route [uri]
  (get routes uri))

(defroutes handler
  (GET "/" req home)
  (GET "/todo" req todo-index)
  (route/not-found "<h1>404 page not found</h1>"))

(defonce server (atom nil))

(defn start-server [& {:keys [host port join?]
                       :or {host "localhost" port 3000 join? false}}]
  (let [port (if (string? port) (Integer/parseInt port) port)]
    (when-not @server
      (reset! server (server/run-jetty
                      #'handler
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

