(ns cfpb.qu.util
  "Utility functions for use throughout Qu."
  (:require [clojure.string :as str]
            [clojure.walk :refer [postwalk]]))

(defn str+
  [& args]
  (apply str (map name args)))

(defn ->int
  "Convert strings and integers to integers. A blank string or
anything but a string or integer will return the default value, which
is nil unless specified."
  ([val] (->int val nil))
  ([val default]
     (cond
      (integer? val) val
      (and (string? val) (not (str/blank? val)))
      (try (Integer/parseInt val)
           (catch NumberFormatException e default))
      :default default)))

(defn ->num
  "Convert strings and numbers to numbers. A blank string or
anything but a string or number will return the default value, which
is nil unless specified."
  ([val] (->num val nil))
  ([val default]
     (cond
      (number? val) val
      (and (string? val) (not (str/blank? val)))
      (try (Float/parseFloat val)
           (catch NumberFormatException e default))
      :default default)))

(defn first-or-identity
  "If the argument is a collection, return the first element in the
  collection, else return the argument."
  [thing]
  (if (coll? thing)
    (first thing)
    thing))

(defn convert-keys
  "Recursively transforms all map keys using the provided function."
  [m fun]
  (let [f (fn [[k v]] [(fun k) v])]
    ;; only apply to maps
    (postwalk (fn [x] (if (map? x) (into {} (map f x)) x)) m)))

(defn ->print
  [x]
  (println (str x))
  x)
