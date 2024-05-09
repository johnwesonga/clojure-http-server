(defproject hello-http-server "0.1.0-SNAPSHOT"
  :description "Basic Ring + Component Server"
  :url "http://github.com/johnwesonga/"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring/ring-core "1.12.1"]
                 [ring/ring-jetty-adapter "1.12.1"]
                 [metosin/reitit "0.7.0-alpha7"]
                 [com.stuartsierra/component "1.1.0"]
                 [ring-logger "1.1.1"]
                 [com.github.seancorfield/next.jdbc "1.3.925"]
                 [org.postgresql/postgresql "42.1.4"]
                 [compojure "1.7.1"]
                 [de.ubercode.clostache/clostache "1.4.0"]
                 [org.slf4j/slf4j-simple "2.0.13"]
                 [selmer "1.12.59"]
                 [metosin/ring-http-response "0.9.3"]]
  :plugins [[lein-ring "0.12.6"]]
  :ring {:handler hello-http-server.router/app-routes}
  :main ^:skip-aot hello-http-server.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev {:main hello-http-server.core/dev-main}})
