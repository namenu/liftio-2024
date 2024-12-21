(ns user
  (:require [meander.epsilon :as m]
            [nextjournal.clerk :as clerk]))

;(clerk/serve! {:browse? true
;               :watch-paths ["notebooks" "slideshow"]})

(clerk/serve! {:browse      true
               :watch-paths ["notebooks"]})
(clerk/show! "notebooks/liftio.clj")

(comment

  (clerk/show! 'nextjournal.clerk.tap)

  (clerk/build! {:paths ["notebooks/liftio.clj"] :out-path "output"})


  :rcf)
