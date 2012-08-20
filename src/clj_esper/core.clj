(ns clj-esper.core
  (:import [java.util Properties]
           [com.espertech.esper.client Configuration UpdateListener EPStatement EPServiceProviderManager])
  (:use [clojure.walk :only (stringify-keys)]))

(defn create-listener
  "Creates an UpdateListener proxy that can be attached to
  handle updates to Esper statements. fun will be called for
  each newEvent received."
  [fun]
  (proxy [UpdateListener] []
    (update [newEvents oldEvents]
      (apply fun newEvents))))

(defn create-service
  ([configuration]
     (EPServiceProviderManager/getDefaultProvider configuration))
  ([uri configuration]
     (EPServiceProviderManager/getProvider uri configuration)))

(defn create-statement
  "Creates an Esper statement"
  ([service statement]
     (.createEPL (.getEPAdministrator service) statement))
  ([service statement name]
     (.createEPL (.getEPAdministrator service) statement name)))

(defn attach-listener
  "Attaches the listener to the statement."
  [statement listener]
  (.addListener statement listener))

(defn send-event
  "Pushes the event into the Esper processing engine."
  [service event type]
  (.sendEvent (.getEPRuntime service) event type))

(defn configuration
  [service]
  (.. service getEPAdministrator getConfiguration))


(def *service* nil)

(defn event-name
  [event]
  (:name event))

(defn event-attribute-names
  [event]
  (-> event :attributes keys))

(defn event-attributes
  [event]
  (:attributes event))


;; more stuff here:
;; http://esper.codehaus.org/esper-2.1.0/doc/reference/en/html/configuration.html#config-java-util-map


(defmulti to-int class)
(defmethod to-int String [s] (Integer/parseInt s))
(defmethod to-int Integer [i] i)
(defmethod to-int Long [l] (int l))

(defmulti to-double class)
(defmethod to-double String [s] (if (= "null" s)
                                  nil
                                  (Double/parseDouble s)))
(defmethod to-double Integer [i] (double i))
(defmethod to-double Double [d] d)

(def coercions {:int to-int
                :string str
                :double to-double})

(defn coerce-values
  "Coerces event values into their relevant type"
  [m event-attributes]
  (letfn [(coerce [k v t]
            (let [coercion (t coercions)]
              (if (or (nil? coercion)
                      (nil? v))
                [k v]
                [k (coercion v)])))]
    (into {} (map (fn [[k v]]
                    (coerce k v (event-attributes k)))
                  m))))

(defn new-event
  [event & attrs]
  (let [attr-names (event-attribute-names event)
        empty-map (apply hash-map
                         (interleave attr-names
                                     (repeat (count attr-names) nil)))]
    (with-meta (coerce-values (merge empty-map
                                     (apply hash-map attrs))
                              (event-attributes event))
      event)))

;; Taken from the old clojure.contrib.java-utils
(defn ^Properties as-properties
  "Convert any seq of pairs to a java.utils.Properties instance.
   Uses as-str to convert both keys and values into strings."
  [m]
  (let [p (Properties.)]
    (doseq [[k v] m]
      (.setProperty p (name k) (name v)))
    p))

(defn create-configuration
  "Builds an Esper Configuration object from a sequence of defevent records"
  ([es]
     (create-configuration (Configuration. ) es))
  ([config es]
     (doseq [{:keys [name attributes]} es]
       (.addEventType config name (as-properties attributes)))
     config))

(defrecord Statement [name statement])

(defmulti create-stmt (fn [s _] (class s)))
(defmethod create-stmt String
  [s service]
  (create-statement service s))
(defmethod create-stmt Statement
  [s service]
  (create-statement service (:statement s) (:name s)))

(defn attach-statement
  "Creates a statement with attached handlers"
  [statement & handlers]
  (letfn [(broadcast [& args]
            (doseq [fn handlers] (apply fn args)))]
    (attach-listener (create-stmt statement *service*)
                     (create-listener broadcast))))

(defn attach-statements
  "Allows attachment of multiple statements to the same handlers."
  [statements & handlers]
  (doseq [s statements] (apply attach-statement s handlers)))

(defn trigger-event
  [event]
  (let [event-type (event-name (meta event))
        event-data (stringify-keys event)]
    (send-event *service* event-data event-type)))

(defn statement-names
  "Returns the names of all statements that have been registered"
  ([]
     (.. *service* getEPAdministrator getStatementNames))
  ([service]
     (.. service getEPAdministrator getStatementNames)))

(defmacro with-esper
  "Creates an Esper service and registers specified events."
  [name {:keys [events uri] :or {events [] uri (str (gensym name))}} & body]
  `(let [config# (create-configuration ~events)
         ~name (create-service ~uri config#)]
     (binding [*service* ~name]
       ~@body)))

(defmacro defevent
  "Creates an event record and registers the details in *event-attributes*"
  [event-name attributes & etc]
  (let [name-str (name event-name)
        attr-names (vec (map #(keyword (first %)) (partition 2 attributes)))
        attr-types (vec (map second (partition 2 attributes)))
        attrs (apply hash-map (interleave attr-names attr-types))]
    `(def ~event-name {:name ~name-str
                 :attributes ~attrs})))

(defmacro defstatement
  "Binds statement s to var name."
  [statement-name s]
  (let [name-str (name statement-name)]
    `(def ~statement-name (Statement. ~name-str ~s))))
