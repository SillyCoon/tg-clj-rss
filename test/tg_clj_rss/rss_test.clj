(ns tg-clj-rss.rss-test
  (:require [clojure.test :refer :all]
            [tg-clj-rss.core :refer :all]
            [tg-clj-rss.rss :refer [prettify-post
                                    replace-tags-with-content]]))

(def test-post '({:tag :title, :attrs {}, :content ("Увольнение")}
                 {:tag :link, :attrs {}, :content ("https://sillyblog.netlify.app/posts/sketches/resignation/")}
                 {:tag :guid, :attrs {}, :content ("https://sillyblog.netlify.app/posts/sketches/resignation/")}
                 {:tag :pubDate, :attrs {}, :content ("Thu, 15 Dec 2022 00:00:00 GMT")}))

(deftest replace-tags-with-content-test
  (is (= (replace-tags-with-content test-post) {:title "Увольнение",
                                                :link "https://sillyblog.netlify.app/posts/sketches/resignation/",
                                                :guid "https://sillyblog.netlify.app/posts/sketches/resignation/",
                                                :pubDate "Thu, 15 Dec 2022 00:00:00 GMT"})))

#_(prettify-post test-post)

(deftest prettify-post-test
  (is (= (prettify-post test-post) {:title "Увольнение",
                                    :link "https://sillyblog.netlify.app/posts/sketches/resignation/",
                                    :pubDate "Thu, 15 Dec 2022 00:00:00 GMT"})))
