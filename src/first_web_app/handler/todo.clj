(ns first-web-app.handler.todo
  (:require [compojure.core :refer [defroutes context GET POST]]
            [first-web-app.util.response :as res]))

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
      res/html))


(defn todo-new [req] "todo new")
(defn todo-new-post [req] "todo new post")
(defn todo-search [req] "todo search")
(defn todo-show [req] "todo show")
(defn todo-edit [req] "todo edit")
(defn todo-edit-post [req] "todo edit post")
(defn todo-delete [req] "todo delete")
(defn todo-delete-post [req] "todo delete post")

(defroutes todo-routes
  (context "/todo" _
           (GET "/" _ todo-index)
           (GET "/new" _ todo-new)
           (POST "/new" _ todo-new-post)
           (GET "/search" _ todo-search)
           (context "/:todo-id" _
                    (GET "/" _ todo-show)
                    (GET "/edit" _ todo-edit)
                    (POST "/edit" _ todo-edit-post)
                    (GET "/delete" _ todo-delete)
                    (POST "/delete" _ todo-delete-post))))

