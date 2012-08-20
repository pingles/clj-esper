(defproject clj-esper "1.0.0-SNAPSHOT"
  :description "Simple Esper wrapper for Clojure"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [com.espertech/esper "4.3.0" :exclusions [log4j]]])
