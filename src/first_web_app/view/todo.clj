(ns first-web-app.view.todo
  (:require [hiccup.form :as hf]
            [first-web-app.view.layout :as layout]))

(defn todo-index-view [req todo-list]
  (->>  [:section.card
         (when-let [{:keys [msg]} (:flash req)]
           [:div.alert.alert-success [:strong msg]])
         [:h2 "todo 一覧"]
         [:ul
          (for [{:keys [title]} todo-list]
            [:li title])]]
        (layout/common req)))

(defn todo-new-view [req]
  (->> [:section.card
        [:h2 "todo 追加"]
        (hf/form-to
         [:post "/todo/new"]
         [:input {:name :title :placeholder "todoを入力してください"}]
         [:button.bg-blue "追加する"])]
       (layout/common req)))

(defn todo-show-view [req todo]
  (->> [:section.card
        (when-let [{:keys [msg]} (:flash req)]
          [:div.alert.alert-success [:strong msg]])
        [:h2 (:title todo)]]
       (layout/common req)))

(defn todo-edit-view [req todo]
  (let [todo-id (get-in req [:params :todo-id])]
    (->> [:section.card
          [:h2 "todo 編集"]
          (hf/form-to
           [:post (str "/todo/" todo-id "/edit")]
           [:input {:name :title :value (:title todo)
                    :placeholder "todoを入力してください"}]
           [:button.bg-blue "更新する"])]
         (layout/common req))))

(defn todo-delete-view [req todo]
  (let [todo-id (get-in req [:params :todo-id])]
    (->> [:section.card
          [:h2 "todo 削除"]
          (hf/form-to
           [:post (str "/todo/" todo-id "/delete")]
           [:p "次のTODOを本当に削除しますか？"]
           [:p "*" (:title todo)]
           [:button.bg-red "削除する"])]
         (layout/common req))))


