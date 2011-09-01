(ns clj-subdb.random-file-access
  (:import (java.io RandomAccessFile)))

(defn random-file
  ([path mode]
   (let [file (RandomAccessFile. path mode)]
     {:file file :position 0 :size (.length file)}))

  ([path] (random-file path "r")))

(defmacro with-random-file
  [bind path & body]
  `(let [original-instance# (random-file ~path)]
     (with-open [file-instance# (:file original-instance#)]
       (let [~bind original-instance#] ~@body))))

(defn random-seek [{size :size :as file} pos]
  (assoc file :position (min pos (dec size))))

(defn random-max-read-size [{:keys [size position]}]
  (- size position))

(defn random-read-bytes [{fp :file pos :position :as file} length]
  (let [read-size (min length (random-max-read-size file))
        buffer (byte-array read-size)]
    (doto fp (.seek pos) (.read buffer))
    buffer))

(defn random-read-bytes-from [file, position, size]
  (random-read-bytes (random-seek file position) size))

(defn random-file-initial [file, size]
  (random-read-bytes-from file 0 size))

(defn random-file-last [{file-size :size :as file}, size]
  (let [real-size (min size file-size)]
    (random-read-bytes-from file (- file-size real-size) real-size)))
