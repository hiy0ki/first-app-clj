(ns first-web-app.db.todo
  (:require [clojure.java.jdbc :as jdbc]
            [first-web-app.db :as db]))

(defn save-todo [title]
  (jdbc/insert! db/db-spec :todo {:title title}))

(defn find-todo-all []
  (jdbc/query db/db-spec "select * from todo"))

(defn find-first-todo [id]
  (first (jdbc/query db/db-spec ["select * from todo where id = ?" id])))

