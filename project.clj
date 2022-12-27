(defproject tg-clj-rss "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/tools.logging "1.2.4"]
                 [org.clojure/core.async "1.6.673"]
                 [org.clojure/data.xml "0.0.8"]
                 [http-kit "2.6.0"]
                 [cheshire "5.11.0"]
                 [compojure "1.7.0"]]
  :main ^:skip-aot tg-clj-rss.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
