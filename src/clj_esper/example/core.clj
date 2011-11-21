(ns clj-esper.example.core
  (:use [clj-esper.core :only (defevent defstatement with-esper attach-statement trigger-event new-event)]))

(defevent FooEvent [foo :string])
(defstatement select-test "SELECT foo FROM FooEvent")

(defn handler
  [msg]
  (println "foo: " (.get msg "foo")))

(defn -main []
  (with-esper service {:uri "/some-uri"
                       :events #{FooEvent}}
    (attach-statement select-test handler)
    (trigger-event (new-event FooEvent :foo "Hello, World!"))))
