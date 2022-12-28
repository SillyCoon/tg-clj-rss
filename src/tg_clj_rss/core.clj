(ns tg-clj-rss.core
  (:require [tg-clj-rss.config :as conf]
            [tg-clj-rss.rss :refer [fetch-posts-if-changed]]
            [tg-clj-rss.telegram-bot :as tg]
            [tg-clj-rss.util :as u]
            [cheshire.core :as json]
            [clojure.tools.logging :as logging]
            [clojure.string :as str]))



(def telegram
  {:token      conf/tg-token
   :user-agent "Clojure 1.10.3"
   :timeout    300000
   :keepalive  300000})


(defn fetch-posts [] (fetch-posts-if-changed conf/blog-rss-url))
(defn send-to-tg [posts] (tg/send-message telegram
                                          -1001821656793
                                          (str/join "\n\n" (map u/pretty-post posts))
                                          {:parse-mode "HTML"}))

(defn run [] (u/poll #(when-let [new-posts (fetch-posts)]
                        (logging/info "New posts!")
                        (send-to-tg new-posts)) 5000))

(def st (run))

#_(st)
;; (defn -main
;;   [& args]
;;   (let [stop ]
;;     ))
