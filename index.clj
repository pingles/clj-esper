{:namespaces
 ({:source-url nil,
   :wiki-url "clj-esper.core-api.html",
   :name "clj-esper.core",
   :doc nil}),
 :vars
 ({:arglists ([statement listener]),
   :name "attach-listener",
   :namespace "clj-esper.core",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url "/clj-esper.core-api.html#clj-esper.core/attach-listener",
   :doc "Attaches the listener to the statement.",
   :var-type "function",
   :line 29,
   :file "/Users/paul/Work/clj-esper/src/clj_esper/core.clj"}
  {:arglists ([statement & handlers]),
   :name "attach-statement",
   :namespace "clj-esper.core",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/clj-esper.core-api.html#clj-esper.core/attach-statement",
   :doc "Creates a statement with attached handlers",
   :var-type "function",
   :line 122,
   :file "/Users/paul/Work/clj-esper/src/clj_esper/core.clj"}
  {:arglists ([statements & handlers]),
   :name "attach-statements",
   :namespace "clj-esper.core",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/clj-esper.core-api.html#clj-esper.core/attach-statements",
   :doc
   "Allows attachment of multiple statements to the same handlers.",
   :var-type "function",
   :line 130,
   :file "/Users/paul/Work/clj-esper/src/clj_esper/core.clj"}
  {:arglists ([m event-attributes]),
   :name "coerce-values",
   :namespace "clj-esper.core",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url "/clj-esper.core-api.html#clj-esper.core/coerce-values",
   :doc "Coerces event values into their relevant type",
   :var-type "function",
   :line 79,
   :file "/Users/paul/Work/clj-esper/src/clj_esper/core.clj"}
  {:arglists ([es] [config es]),
   :name "create-configuration",
   :namespace "clj-esper.core",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/clj-esper.core-api.html#clj-esper.core/create-configuration",
   :doc
   "Builds an Esper Configuration object from a sequence of defevent records",
   :var-type "function",
   :line 103,
   :file "/Users/paul/Work/clj-esper/src/clj_esper/core.clj"}
  {:arglists ([fun]),
   :name "create-listener",
   :namespace "clj-esper.core",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url "/clj-esper.core-api.html#clj-esper.core/create-listener",
   :doc
   "Creates an UpdateListener proxy that can be attached to\nhandle updates to Esper statements. fun will be called for\neach newEvent received.",
   :var-type "function",
   :line 7,
   :file "/Users/paul/Work/clj-esper/src/clj_esper/core.clj"}
  {:arglists ([service statement] [service statement name]),
   :name "create-statement",
   :namespace "clj-esper.core",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/clj-esper.core-api.html#clj-esper.core/create-statement",
   :doc "Creates an Esper statement",
   :var-type "function",
   :line 22,
   :file "/Users/paul/Work/clj-esper/src/clj_esper/core.clj"}
  {:arglists ([name attributes & etc]),
   :name "defevent",
   :namespace "clj-esper.core",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url "/clj-esper.core-api.html#clj-esper.core/defevent",
   :doc
   "Creates an event record and registers the details in *event-attributes*",
   :var-type "macro",
   :line 156,
   :file "/Users/paul/Work/clj-esper/src/clj_esper/core.clj"}
  {:arglists ([name s]),
   :name "defstatement",
   :namespace "clj-esper.core",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url "/clj-esper.core-api.html#clj-esper.core/defstatement",
   :doc "Binds statement s to var name.",
   :var-type "macro",
   :line 166,
   :file "/Users/paul/Work/clj-esper/src/clj_esper/core.clj"}
  {:arglists ([service event type]),
   :name "send-event",
   :namespace "clj-esper.core",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url "/clj-esper.core-api.html#clj-esper.core/send-event",
   :doc "Pushes the event into the Esper processing engine.",
   :var-type "function",
   :line 34,
   :file "/Users/paul/Work/clj-esper/src/clj_esper/core.clj"}
  {:arglists ([] [service]),
   :name "statement-names",
   :namespace "clj-esper.core",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url "/clj-esper.core-api.html#clj-esper.core/statement-names",
   :doc
   "Returns the names of all statements that have been registered",
   :var-type "function",
   :line 141,
   :file "/Users/paul/Work/clj-esper/src/clj_esper/core.clj"}
  {:arglists
   ([name
     {:keys [events uri], :or {events [], uri (str (gensym name))}}
     &
     body]),
   :name "with-esper",
   :namespace "clj-esper.core",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url "/clj-esper.core-api.html#clj-esper.core/with-esper",
   :doc "Creates an Esper service and registers specified events.",
   :var-type "macro",
   :line 148,
   :file "/Users/paul/Work/clj-esper/src/clj_esper/core.clj"})}
