# clj-esper

A simple Clojure wrapper for [Esper](http://esper.codehaus.org): Complex Event Processing. 

> Esper and NEsper enable rapid development of applications that process large volumes of incoming messages or events. Esper and NEsper filter and analyze events in various ways, and respond to conditions of interest in real-time.

It provides a SQL-like language across a stream of data:

	SELECT COUNT(1) as success_count
	FROM web_log.win:time(60 seconds)
	WHERE status >= 200 AND status < 400
	OUTPUT SNAPSHOT EVERY 1 SECONDS

## Usage

Esper is organised around events. clj-esper provides a `defevent` macro that makes it easier to build map event objects for esper (clj-esper uses Esper's [Map Event Type](http://esper.codehaus.org/esper-4.3.0/doc/reference/en/html/event_representation.html#eventrep-java-util-map)).

	(defevent TestEvent [a :int b :string])

Having defined an event, the `with-esper` macro can be used to build the Esper runtime and dispatch events into it.

	(def output-events (atom []))
	(defn- handler
	  [atom]
	  (fn [x]
	    (swap! atom conj x)))

	(def statement "SELECT a, b FROM TestEvent")

	(with-esper service #{TestEvent}
	      (attach-statement statement (handler output-events))
	      (trigger-event (new-event TestEvent :a 1 :b "Hello"))

For more examples please see the tests.

## License

Copyright &copy; 2011 Paul Ingles.

Distributed under the Eclipse Public License, the same as Clojure.
