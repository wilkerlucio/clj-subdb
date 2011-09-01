(ns clj-subdb.test.crypto
  (:use clj-subdb.crypto)
  (:use clojure.test))

(deftest test-md5-hash
  (is (= "827ccb0eea8a706c4c34a16891f84e7b" (md5 "12345"))))

(deftest test-md5-hash-from-bytes
  (is (= "827ccb0eea8a706c4c34a16891f84e7b" (md5-bytes (.getBytes "12345")))))
