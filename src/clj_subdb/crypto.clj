(ns clj-subdb.crypto
  (:import (java.security MessageDigest)))

(defn- bytes-to-hex [bytes]
  (.toString (new BigInteger 1 bytes) 16))

(defn md5-bytes [bytes]
  (let [alg (doto (MessageDigest/getInstance "MD5")
            (.reset)
            (.update bytes))]
    (bytes-to-hex (.digest alg))))

(defn md5 [& strings]
  (let [string (apply str strings)]
    (md5-bytes (.getBytes string))))
