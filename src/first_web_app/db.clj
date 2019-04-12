(ns first-web-app.db
  [:require [clojure.java.jdbc :as jdbc]])

(def db-spec {:dbtype "postgresql"
              :dbname "sample"
              :host "localhost"
              :port 5432
              :user "hiy0ki"
              :password ""}) ; todo envから取る

(defn migrate []
  (jdbc/db-do-commands
   db-spec
   (jdbc/create-table-ddl :todo
                          [[:id :serial]
                           [:title :varchar]])))


