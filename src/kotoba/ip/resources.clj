(ns kotoba.ip.resources
  "EDN resource loader for `kotoba.ip` constant/config resources.

  Mirrors the loader pattern in `kami-scene-contracts`
  (`kami.scene.contracts/load-edn-resource`). This file is JVM-only
  (plain `.clj`, not `.cljc`) since `clojure.java.io/resource` + `slurp`
  are not portable to ClojureScript — matching the `kotoba-lang/fea`
  precedent (`kotoba.fea.material-loader`) of keeping resource-IO helpers
  in a dedicated `.clj` file rather than reader-conditional-guarding an
  otherwise-empty `.cljc` namespace.

  The pure `.cljc` domain namespaces (`kotoba.ip.bus-protocol`,
  `kotoba.ip.noc`, ...) keep their constants embedded as plain Clojure data
  so they stay usable without resource IO on every platform. The EDN files
  under `resources/kotoba/ip/` are the canonical, diff-able,
  tooling-friendly source of truth for those constants; a round-trip test
  in `test/` asserts the embedded data matches the resource exactly, so the
  two can never silently drift."
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))

(defn load-edn
  "Load and parse an EDN resource by classpath-relative path."
  [path]
  (let [resource (io/resource path)]
    (when-not resource
      (throw (ex-info "missing kotoba.ip resource" {:path path})))
    (edn/read-string (slurp resource))))
