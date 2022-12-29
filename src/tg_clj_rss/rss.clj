(ns tg-clj-rss.rss
  (:require [cheshire.core :as json]
            [clojure.data.xml :as xml]
            [clojure.tools.logging :as logging]
            [org.httpkit.client :as http]
            [tg-clj-rss.util :refer [slurp-json]]))

(def hash-path "resources/hash")

(defn parse-news-from-rss [rss]
  (let [news (-> (xml/parse-str rss) :content first :content)
        news-items (drop 4 news)
        res (map :content news-items)] res))

(defn replace-tags-with-content [post]
  (into {} (map (fn [el] [(:tag el) (-> el :content first)]) post)))

(defn prettify-post [post]
  (let [pretty-post (replace-tags-with-content post)]
    (dissoc pretty-post :guid :pubDate)))

(defn get-posts [url]
  (:body @(http/get url)))

(defn posts-changed? [posts previous-posts]
  (let [previous-posts (slurp-json hash-path)]
    (not= posts previous-posts)))

(defn read-previous-posts []
  (slurp-json hash-path))

(defn save-new-posts [posts]
  (spit hash-path (json/encode posts)))

(defn fetch-posts [url]
  (let [rss-xml (get-posts url)
        news (parse-news-from-rss rss-xml)
        pretty-posts (map prettify-post news)]
    pretty-posts))

(defn fetch-posts-if-changed [url]
  (let [posts (fetch-posts url)
        previous-posts (read-previous-posts)
        _ (logging/info "Posts fetched: " (count posts))]
    (when (posts-changed? posts previous-posts)
      (save-new-posts posts)
      (remove #(some #{%} previous-posts) posts))))

(defn clear-cache []
  (spit hash-path 0))

#_(posts-changed? (fetch-posts conf/blog-rss-url))

; calculate posts hash on fetch

; keep posts hash in the file
; 