(ns tg-clj-rss.rss
  (:require [clojure.data.xml :as xml]
            [org.httpkit.client :as http]))

(defn parse-news-from-rss [rss]
  (let [news (-> (xml/parse-str rss) :content first :content)
        news-items (drop 4 news)
        res (map :content news-items)] res))

(defn replace-tags-with-content [post]
  (into {} (map (fn [el] [(:tag el) (-> el :content first)]) post)))

(defn prettify-post [post]
  (let [pretty-post (replace-tags-with-content post)]
    (dissoc pretty-post :guid)))

(defn fetch-posts [url]
  (let [rss-xml (:body @(http/get url))
        news (parse-news-from-rss rss-xml)]
    (map prettify-post news)))
