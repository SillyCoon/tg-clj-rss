(ns tg-clj-rss.util
  (:require [clojure.core.async :as async]))

(defn poll [action interval]
  (let [stop (atom false)]
    (async/go-loop [stop? @stop]
      (when-not stop?
        (action)
        (async/<! (async/timeout interval))
        (recur @stop)))
    #(reset! stop true)))

(defn slurp-int [file]
  (try (Integer/parseInt (slurp file))
       (catch Exception _ nil)))

#_(def stop (non-stop-poll-action #(println 1) 1000))
#_(stop)
