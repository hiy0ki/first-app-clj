(ns first-web-app.view.todo
  (:require [hiccup.form :as hf]
            [first-web-app.view.layout :as layout]))

(defn todo-index-view [req todo-list]
  (->> `([:h2 "todo 一覧"]
         [:ul
          ~@(for [{:keys [title]} todo-list]
              [:li title])])
       (layout/common req)))

(defn todo-new-view [req]
  (->> [:section.card
        [:h2 "todo 追加"]
        (hf/form-to
         [:post "/todo/new"]
         [:input {:name :title :placeholder "todoを入力してください"}]
         [:button.bg-blue "追加する"])]
       (layout/common req)))

(defn todo-complete-view [req]
  (->> [:section.card
        [:h2 "todo を追加しました！"]]
       (layout/common req)))

(defn todo-show-view [req todo]
  (->> [:section.card
        (when-let [{:keys [msg]} (:flash req)]
          [:div.alert.alert-success [:strong msg]])
        [:h2 (:title todo)]]
       (layout/common req)))
