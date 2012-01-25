;; ## Fact query generation

(ns com.puppetlabs.cmdb.query.facts
  (:refer-clojure :exclude [case compile conj! distinct disj! drop sort take])
  (:use clojureql.core))

(defn facts-for-node
  "Fetch the facts for the given node, as a map of `{fact value}`"
  [db node]
  {:pre [(string? node)]
   :post [(map? %)]}
  (let [facts (-> (table db :certname_facts)
                  (project [:fact, :value])
                  (select (where (= :certname node))))]
    (into {} (for [fact @facts]
               [(:fact fact) (:value fact)]))))