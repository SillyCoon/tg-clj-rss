(ns tg-clj-rss.util
  (:require [clojure.core.async :as async]
            [cheshire.core :as json]))

(defn poll [action interval]
  (let [stop (atom false)]
    (async/go-loop [stop? @stop]
      (when-not stop?
        (action)
        (async/<! (async/timeout interval))
        (recur @stop)))
    #(reset! stop true)))

(defn slurp-json [file]
  (try (json/decode (slurp file) true)
       (catch Exception _ nil)))

(defn pretty-post [{:keys [title link pubDate]}]
  (str "<i>" title "</i>" "\n" link "\n" pubDate))

#_(def stop (poll #(do (println 1) (println "KEJK")) 1000))
#_(stop)
