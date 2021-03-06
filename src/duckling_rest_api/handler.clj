(ns duckling-rest-api.handler
  (:require [compojure.core :refer :all]
            [duckling.core :as p]
            [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [compojure.route :as route]))

(defroutes app-routes
  (POST "/" request
        (let [text (or (get-in request [:params :text])
                       (get-in request [:body :text])
                       "")]
          {:status 200
           :body {:text text
                  :success true
                  :results (p/parse :en$core text, [:amount-of-money, :phone-number, :date, :time])
                  }}
          )
  )
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
      (middleware/wrap-json-body {:keywords? true})
      middleware/wrap-json-response))

(defn start [])
  (println "Loading the duckling core")
  (p/load! {:languages ["en"]})
  (println "Loaded")
