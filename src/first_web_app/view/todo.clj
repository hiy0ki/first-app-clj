(ns first-web-app.view.todo
  (:require [first-web-app.view.layout :as layout]))

(defn todo-index-view [req todo-list]
  (->> `([:h2 "todo 一覧"]
         [:ul
          ~@(for [{:keys [title]} todo-list]
              [:li title])])
       (layout/common req)))


