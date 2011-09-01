(ns clj-subdb.test.random-file-access
  (:import (java.util Arrays))
  (:use [clj-subdb.random-file-access])
  (:use [clojure.contrib.io :only (to-byte-array)])
  (:use [clojure.test]))

(def sample-file "test/fixtures/sample-file")

(deftest test-random-file
  (with-random-file file "/dev/null"
    (is (= java.io.RandomAccessFile (class (:file file))))))

(deftest test-random-seek
  (is (= {:position 30 :size 50} (random-seek {:position 0 :size 50} 30)))
  (is (= {:position 49 :size 50} (random-seek {:position 0 :size 50} 80))))

(deftest test-max-read-size
  (is (= 60 (random-max-read-size {:position 0 :size 60})))
  (is (= 50 (random-max-read-size {:position 10 :size 60}))))

(deftest test-random-read-bytes
  (with-random-file file sample-file
    (is (Arrays/equals (to-byte-array "abc") (random-read-bytes file 3)))
    (is (Arrays/equals (to-byte-array "cdef") (random-read-bytes (random-seek file 2) 4)))))

(deftest test-random-read-bytes
  (with-random-file file sample-file
    (is (Arrays/equals (to-byte-array "cdef") (random-read-bytes-from file 2 4)))))

(deftest test-random-file-initial
  (with-random-file file sample-file
    (is (Arrays/equals (to-byte-array "abcde") (random-file-initial file 5)))
    (is (Arrays/equals (to-byte-array "abcdefghijklmnopqrstuvxywz") (random-file-initial file 100)))))

(deftest test-random-file-last
  (with-random-file file sample-file
    (is (Arrays/equals (to-byte-array "vxywz") (random-file-last file 5)))
    (is (Arrays/equals (to-byte-array "abcdefghijklmnopqrstuvxywz") (random-file-last file 100)))))
