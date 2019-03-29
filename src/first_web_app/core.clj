(ns first-web-app.core
  (:require [ring.adapter.jetty :as server]))

(defn ok [body]
  {:status 200
   :body body})

(defn html [res]
  (assoc res :headers {"Content-Type" "text/html; charset=utf-8"}))

(defn not-found []
  {:status 404
   :body "<h1>404 page not found</h1>"})

(defn home-view [req]
  "<h1>ホーム画面</h1>
   <a href=\"/todo\">TODO 一覧</a>")

(defn home [req]
  (-> (home-view req)
      ok
      html))

;; ↑のhomeは以下と同じ
;(defn home [req]
;  (html (ok (home-view req))))


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
      ok
      html))
   
(def routes
  {"/" home
   "/todo" todo-index})


(defn match-route [uri]
  (get routes uri))

(defn handler [req]
  (let [uri (:uri req)
        maybe-fn (match-route uri)]
    (if maybe-fn
      (maybe-fn req)
      (not-found))))


(defonce server (atom nil))

;(defn handler [req]
;  {:status 200
;   :headers {"Content-type" "text/html"}
;   :body "<h1>hello, world!!</h1>"})

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

