(defproject first-web-app "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :min-lein-version "2.5.3"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [ring "1.7.1"]
                 [compojure "1.6.1"]
                 [hiccup "1.0.5"]
                 [environ "1.0.1"]
                 [org.clojure/java.jdbc "0.7.9"]
                 [org.postgresql/postgresql "42.2.5"]
                 [bouncer "0.3.3"]
                 [ring/ring-defaults "0.3.2"]]
  :repl-options {:init-ns first-web-app.core}
  :uberjar-name "first-web-app.jar"
  :plugins [[lein-environ "1.0.1"]]
  :profiles
  {:dev {:dependencies [[prone "1.6.1"]]
         :env {:dev true}}
   :uberjar {:aot :all
             :main first-web-app.main}})


