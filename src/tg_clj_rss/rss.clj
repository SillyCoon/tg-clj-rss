(ns tg-clj-rss.rss
  (:require [clojure.data.xml :as xml]
            [org.httpkit.client :as http]
            [tg-clj-rss.util :refer [slurp-int]]
            [tg-clj-rss.config :as conf]))

(def hash-path "resources/hash")

(defn parse-news-from-rss [rss]
  (let [news (-> (xml/parse-str rss) :content first :content)
        news-items (drop 4 news)
        res (map :content news-items)] res))

(defn replace-tags-with-content [post]
  (into {} (map (fn [el] [(:tag el) (-> el :content first)]) post)))

(defn prettify-post [post]
  (let [pretty-post (replace-tags-with-content post)]
    (dissoc pretty-post :guid)))

(defn get-posts [url]
  (:body @(http/get url)))

(defn posts-changed? [posts]
  (let [posts-hash (hash posts)
        previous-hash (slurp-int hash-path)]
    (not= posts-hash previous-hash)))

(defn update-hash [posts]
  (spit hash-path (hash posts)))

(defn fetch-posts [url]
  (let [rss-xml (get-posts url)
        news (parse-news-from-rss rss-xml)
        pretty-posts (map prettify-post news)]
    pretty-posts))

(defn fetch-posts-if-changed [url]
  (let [posts (fetch-posts url)]
    (when (posts-changed? posts)
      (update-hash posts)
      posts)))

(defn clear-cache []
  (spit hash-path 0))

#_(posts-changed? (fetch-posts conf/blog-rss-url))

; calculate posts hash on fetch

; keep posts hash in the file
; 