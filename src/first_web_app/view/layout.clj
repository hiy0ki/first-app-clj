(ns first-web-app.view.layout
  (:require [hiccup.page :refer [html5 include-css include-js]]))

(defn common [req & body]
  (html5
   [:head
    [:title "todo-clj"]
    (include-css "/css/normalize.css"
                 "/css/papier-1.3.1.min.css"
                 "/css/style.css")
    (include-js "/js/main.js")]
   [:body
    [:header.top-bar.bg-green.depth-3 "todo-clj"]
    [:main body]]))

