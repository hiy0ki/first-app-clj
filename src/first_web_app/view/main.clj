(ns first-web-app.view.main
  (:require [first-web-app.view.layout :as layout]))

(defn home-view [req]
  (->> [:section.card
        [:h2 "home画面"]
        [:a {:href "/todo"} "todo 一覧"]]
       (layout/common req)))

