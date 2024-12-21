(ns liftio
  {::clerk/visibility {:code :hide :result :show}}
  (:require [clojure.set :refer [rename-keys]]
            [clojure.string :as str]
            [meander.epsilon :as m]
            [nextjournal.clerk :as clerk]
            [nextjournal.clerk-slideshow :as slideshow]))

; # 데이터 변환의 새로운 패러다임

^{::clerk/visibility {:result :hide}}
(clerk/add-viewers! [slideshow/viewer])

; ---

; # Disclaimer
; ### 새롭지 않다 👴
; ### 모나드 안나온다 🧙‍♂️ 

^{::clerk/visibility {:result :hide}}
(clerk/add-viewers! [slideshow/viewer])

; ---
;
; > Computation is symbol manipulation.
; >
; >  — Alan Turing

(clerk/html [:hr])
;
(clerk/html [:code "2 + 3 → 5"])
(clerk/html [:code "(a + b) * c → ac + bc"])

; ---
;
; > Can Programming Be Liberated from the von Neumann Style?
; >
; > — John Backus, 1977 Turing Award Lecture

(clerk/html [:hr])

; - 폰노이만 스타일 프로그래밍의 한계
; - 함수형 패러다임을 제안
; - λ-calculus

; ---
; ### 그 발언
;
; > 함수형 프로그램은 명령형 프로그램에 비해 더 간결하고 명확하며, 작성하고 이해하기 쉽다.  
; > ...  
; > 함수형 프로그램은 작은 단위로 모듈화가 되므로 각각을 독립적으로 잘 이해할 수 있다.

(clerk/html [:hr])

; - 50년이 지난 지금, 여러분은 이 주장에 어느정도 동의하는지?

; ---

; ### 사상 검증의 시간 🙋

^{::clerk/visibility {:result :hide}}
(def users [{:name "Alice" :score 80}
             {:name "Bob"   :score 70}
             {:name "Charlie" :score 90}
             {:name "David" :score 60}])

^{::clerk/visibility {:code :show :result :show}}
(->> users
     (filter #(> (:score %) 75))
     (map :name)
     (into []))

; ---
; ### 인생은 실전

^{::clerk/visibility {:code :show, :result :hide}}
(defn- get-info-of-month
  [data ks]
  (->> (vals (select-keys data ks))
       (map #(str/split % #","))
       (apply concat)
       (map str/trim)
       (remove #(zero? (count %)))))

; ---
; ### 실제 개발 과정

^::clerk/no-cache
(clerk/html (read-string (slurp "notebooks/liftio/fig1.edn")))

;- Type / Shape
;- REPL
;- 여전히 불투명한 과정

; ---
; #### REPL Demo

^{::clerk/visibility {:code :hide :result :hide}}
(def movies
  [{:title    "Inception"
    :director "Christopher Nolan"
    :year     2010}
   {:title    "Interstellar"
    :director "Christopher Nolan"
    :year     2014}
   {:title    "The Dark Knight"
    :director "Christopher Nolan"
    :year     2008}
   {:title    "Pulp Fiction"
    :director "Quentin Tarantino"
    :year     1994}
   {:title    "Kill Bill"
    :director "Quentin Tarantino"
    :year     2003}])

^{::clerk/visibility           {:code :show :result :hide}
  ::clerk/auto-expand-results? true}
(defn movies-by-director [director movies]
  (->> movies
       (filter #(= (:director %) director))
       (map #(select-keys % [:title :year]))
       (map #(rename-keys % {:title :movie-title :year :release-year}))))

^{::clerk/visibility           {:code :show}
  ::clerk/auto-expand-results? true}
(movies-by-director "Christopher Nolan" movies)

; ---
(clerk/html
  {::clerk/width :full}
  [:div {:style {:display         "flex"
                 :gap             "20px"
                 :justify-content "center"
                 :padding         "20px"}}
   [:div {:src   "image2.jpg"
          :alt   "Second image"
          :style {:border-radius "8px"
                  :box-shadow    "0 4px 8px rgba(0, 0, 0, 0.1)"}}
    (clerk/image "notebooks/liftio/repl-1p.png")]
   [:div {:src   "image2.jpg"
          :alt   "Second image"
          :style {:border-radius "8px"
                  :box-shadow    "0 4px 8px rgba(0, 0, 0, 0.1)"}}
    (clerk/image "notebooks/liftio/repl-12p.png")]])

; REPL 관련해서는 김상현님이 2021년 LiftIO 발표를 참고하세요.


; ---
; ### `->>` 연산자의 이면 

^{::clerk/visibility {:code :show, :result :hide}}
(->> users
     (filter #(> (:score %) 75))
     (map :name)
     (into []))

; - 직렬을 의미
; - 실행 순서에 영향 (`map->filter` vs. `filter->map`)

; ---
; ### 추상화 단계++

^::clerk/no-cache
(clerk/html (read-string (slurp "notebooks/liftio/fig2.edn")))

; - FP - 어떻게(how) 대신 무엇(what)을
; - 무엇은 다시 어떻게로

; ---

(clerk/caption
  "Drawing Hands by M.C. Escher"
  (clerk/image "notebooks/liftio/DrawingHands2.jpg"))

; ---
; ### 다시 무엇(What)으로

^::clerk/no-cache
(clerk/html (read-string (slurp "notebooks/liftio/fig3.edn")))

;- 계산은 단지 기호의 조작일 뿐이다
;- S2의 기호들과 S1의 기호들은 어떤 관계가 있나?

; ---
(clerk/html
  [:div
   [:h1 "Meander" [:sup "ε"]
    [:h3 "/mēˈandər/"]]])

; ---
; ### Meander

(clerk/html
  [:ul
   [:li "Pattern Matching & Term Rewriting"]
   [:li "Declarative Data Transformation"]])

(clerk/html
  [:div {:className "ml-8"}
   (clerk/image "notebooks/liftio/meander.jpg")])

{::clerk/visibility {:code :show}}

; ---
; ### 아까 예시를 Meander로 변환해보기

^{::clerk/visibility {:code :show, :result :hide}}
(->> movies
     (filter #(= (:director %) "Christopher Nolan"))
     (map #(select-keys % [:title :year]))
     (map #(rename-keys % {:title :movie-title :year :release-year})))

^{::clerk/visibility {:code :fold :result :hide}}
(m/search movies

          ;; 패턴
          (m/scan {:title    ?title
                   :director "Quentin Tarantino"
                   :year     ?year})

          ;; 결과
          {:movie-title  ?title
           :release-year ?year})


; ---
; ###	패턴 매칭 (m/match)

^{::clerk/visibility {:result :hide}}
(def data {:name "Alice" :age 30})

(m/match data

         {:name ?name :age ?age}

         (str ?name " is " ?age " years old."))

; ---

; ### 논리 변수 (?var)

(m/match [1 2 3]
         [1 ?y 3]
         ?y)

(m/match [1 2 3]
         [_ ?y _]
         ?y)

^{::clerk/visibility {:code :hide}}
(clerk/html [:hr])

; - Destructuring 과 유사하지만 더 강력한

; ---
; ###	다중 결과 검색, 조건부 매칭 (m/search, m/pred)
; 하나의 입력에서 여러 개의 매칭 결과를 찾아냅니다.

^{::clerk/visibility {:result :hide}}
(def data [{:name "Alice" :age 30}
           {:name "Bob" :age 25}
           {:name "Charlie" :age 35}])

(m/search data

          (m/scan {:name ?name
                   :age (m/pred #(>= % 30))})

          ?name)

^{::clerk/visibility {:code :hide}}
(clerk/html [:hr])

; - `m/scan`: 시퀀스에서 패턴 찾기
; - `m/pred`: 조건부 매칭

; ---
; ### 데이터 변환 (m/rewrite)

^{::clerk/visibility {:result :hide}}
(def data {:product "Book"
           :price   100
           :details {:title  "The Art of Computer Programming"
                     :weight 0.5}})
^{::clerk/auto-expand-results? true}
(m/rewrite data
           {:price ?price, & ?rest}
           ; 10% 세금 추가
           {:price (* ?price 1.1), & ?rest})

^{::clerk/visibility {:code :hide}}
(clerk/html [:hr])

; - `& ?rest` - 나머지 부분을 매칭하라
; - `match` 가 결과가 즉시 평가되는 반면, `rewrite` 는 변수들이 치환된 값으로 반환

; ---
; ### 서브트리 검색 (m/$)
; 데이터 구조 내의 서브트리를 재귀적으로 검색하여 패턴에 맞는 부분을 찾아냅니다.

^{::clerk/visibility {:result :hide}}
(def data [[1] 2 [[3 4] 5]])

^{::clerk/auto-expand-results? true}
(m/search data

          (m/$ [?a ?b])   ;; 길이가 2인 리스트 매칭
          
          [?a ?b])

; ---
; ### 조금 더 복잡하게 - 같이 생각해봅시다 ☕️

^{::clerk/visibility {:code :hide}}
(clerk/code {::clerk/opts {:language "javascript"}} "const data = {
  people: [{ name: \"김철수\", id: 1 },
           { name: \"이영희\", id: 2 },
           { name: \"박민수\", id: 3 }],
  addresses: {
    1: [{ address: \"서울특별시 강남구 테헤란로\", city: \"서울\", zip: \"06241\", preferred: true },
        { address: \"서울특별시 서초구 반포대로\", city: \"서울\", zip: \"06504\", preferred: false }],
    2: [{ address: \"부산광역시 해운대구 센텀중앙로\", city: \"부산\", zip: \"48058\", preferred: true }],
    3: [{ address: \"대구광역시 중구 국채보상로\", city: \"대구\", zip: \"41940\", preferred: true }]
  },
  visits: {
    1: [{ date: \"2023-01-01\", geoLocation: { zip: \"06241\" } }],
    2: [{ date: \"2023-02-15\", geoLocation: { zip: \"12345\" } },
        { date: \"2023-02-16\", geoLocation: { zip: \"48058\" } }],
    3: [{ date: \"2023-03-01\", geoLocation: { zip: \"41950\" } },
        { date: \"2023-03-02\", geoLocation: { zip: \"41940\" } }]
  }};")

^{::clerk/visibility {:code :hide, :result :hide}}
(def data
  {:people    [{:name "김철수" :id 1}
               {:name "이영희" :id 2}
               {:name "박민수" :id 3}]
   :addresses {1 [{:address   "서울특별시 강남구 테헤란로"
                   :city      "서울"
                   :zip       "06241"
                   :preferred true}
                  {:address   "서울특별시 서초구 반포대로"
                   :city      "서울"
                   :zip       "06504"
                   :preferred false}]
               2 [{:address   "부산광역시 해운대구 센텀중앙로"
                   :city      "부산"
                   :zip       "48058"
                   :preferred true}]
               3 [{:address   "대구광역시 중구 국채보상로"
                   :city      "대구"
                   :zip       "41940"
                   :preferred true}]}
   :visits    {1 [{:date         "2023-01-01"
                   :geo-location {:zip "06241"}}]
               2 [{:date         "2023-02-15"
                   :geo-location {:zip "12345"}}
                  {:date         "2023-02-16"
                   :geo-location {:zip "48058"}}]
               3 [{:date         "2023-03-01"
                   :geo-location {:zip "41950"}}
                  {:date         "2023-03-02"
                   :geo-location {:zip "41940"}}]}})

; 우편번호가 잘못된 방문 기록을 찾아내고 싶다.

^{::clerk/visibility {:code :hide, :result :show}}
(clerk/code "[
  { name: \"이영희\", id: 2, zip: \"12345\", date: \"2023-02-15\" },
  { name: \"박민수\", id: 3, zip: \"41950\", date: \"2023-03-01\" }
]")


; ---

{::clerk/visibility {:code :hide}}

^{::clerk/visibility {:result :hide}}
(def normal-ver
  (clerk/code {::clerk/opts {:language "javascript"}} "
function findNonMatchingVisits(preferredZip, visits) {
  return visits.filter(visit => visit.geoLocation.zip !== preferredZip);
}

function findBadVisitsForPerson(addresses, visits, person) {
  const preferredAddress = addresses.find(addr => addr.preferred);
  if (!preferredAddress) return [];

  const preferredZip = preferredAddress.zip;
  const nonMatchingVisits = findNonMatchingVisits(preferredZip, visits);

  return nonMatchingVisits.map(visit => ({
    name: person.name,
    id: person.id,
    zip: visit.geoLocation.zip,
    date: visit.date
  }));
}

function findPotentialBadVisits(data) {
  return data.people.flatMap(person => {
    const addresses = data.addresses[person.id] || [];
    const visits = data.visits[person.id] || [];
    return findBadVisitsForPerson(addresses, visits, person);
  });
}"))

^{::clerk/visibility {:result :hide}}
(def meander-ver
  (clerk/code "(defn find-potential-bad-visits [data]
  (m/search data
  
    {:people    (m/scan {:id ?id :name ?name})
     :addresses {?id (m/scan {:preferred true :zip ?zip})}
     :visits    {?id (m/scan {:geo-location {:zip (m/and (m/not ?zip) ?bad-zip)}
                              :date ?date})}}
                           
    {:name ?name
     :id   ?id
     :zip  ?bad-zip
     :date ?date}))
"))

(clerk/html
  {::clerk/width :full}
  [:div {:className "w-full flex flex-col md:flex-row gap-4"}
   [:div {:className "flex-1 p-4 bg-gray-50 rounded-lg"}
    [:h4 {:className "text-lg font-semibold mb-2"} "JavaScript"]
    normal-ver]
   ])

; ---

(clerk/html
  {::clerk/width :full}
  [:div {:className "w-full flex flex-col md:flex-row gap-4"}
   [:div {:className "flex-1 p-4 bg-gray-50 rounded-lg"}
    [:h4 {:className "text-lg font-semibold mb-2"} "JavaScript"]
    normal-ver]
   [:div {:className "flex-1 p-4 bg-gray-50 rounded-lg"}
    [:h4 {:className "text-lg font-semibold mb-2"} "Meander"]
    meander-ver]])

; ---
; ### Term Rewriting
;
; 특정 표현을 정의된 규칙에 따라 다른 표현으로 변환하는 것  
;
; - 컴파일러 최적화
; - 자동 정리 증명
; - 컴퓨터 대수 시스템

; ---
; ### 치환 (m/with)

^{::clerk/visibility {:code :show :result :hide}}
(defn simplify-addition [expr]
  (m/match expr
           
           (m/with [%add (m/or (+ 0 %add)
                               (+ %add 0)
                               (+ %add %add)
                               !xs)]
             %add)
           
           (m/rewrite !xs
                      [] 0
                      [?x] ?x
                      _ (+ . !xs ...))))


(clerk/code "(simplify-addition '(+ (+ 0 (+ 0 3)) 0))
;=> 3

(simplify-addition '(+ (+ 0 (+ 0 (+ 3 (+ 2 0)))) 1)))
;=> (+ 3 2 1)")

; ---
; ### 재귀적 변환 (m/cata)

^{::clerk/visibility {:code :show, :result :show}}
(m/rewrite
  {:x
    {:y
      [{:foo 1 :bar 2}
       {:foo 4 :bar 8}]}}

  {:x
    {:y [!vs ...]}}
  [(m/cata !vs) ...]

  {:foo ?foo :bar ?bar}
  [?foo ?bar])

^{::clerk/visibility {:code :hide}}
(clerk/html [:hr])

; - catamorphism

; ---
; ### 문법 정의 (m/defsyntax)

^{::clerk/visibility {:code :show, :result :hide}}
(m/defsyntax phone-number
  [pattern]
  `(m/re #"\d{3}-\d{4}-\d{4}" ~pattern))

^{::clerk/visibility {:code :hide, :result :show}}
(clerk/html [:br])

^{::clerk/visibility {:code :show, :result :show}}
(m/match "010-1234-5678"
         
         (phone-number ?num)
         :valid
         
         _
         :invalid)

; ---

; ## 실제 사례 ♨️

; ---
; ### GraphQL Schema in Data (EDN)

(clerk/code 
  "{:enums      {:episode {:description \"The episodes of the original Star Wars trilogy. \"
                        :values      [:NEWHOPE :EMPIRE :JEDI]}}

 :interfaces {:character {:fields {:id         {:type String}
                                   :name       {:type String}
                                   :appears_in {:type (list :episode)}
                                   :friends    {:type (list :character)}}}}

 :objects    {:human {:implements [:character]
                      :fields     {:id          {:type String}
                                   :name        {:type String}
                                   :appears_in  {:type (list :episode)}
                                   :friends     {:type (list :character)
                                                 :resolve :friends}
                                   :home_planet {:type String}}}}

 :queries    {:hero {:type    (non-null :character)
                     :args    {:episode {:type :episode}}
                     :resolve :hero}}}")

; ---
; ### GraphQL + Relay

; - Relay 페이지네이션: GraphQL 스키마 위에 정의된 특별한 명세
(clerk/image "notebooks/liftio/pagination.png")

; ---

; 1️⃣ 자체 명세에서
^{::clerk/visibility {:code :show :result :hide}}
{:Friend {:implements [:Node] 
          :pagination :relay    ;; 👈
          :fields     {:id      {:type 'ID!}
                       :name    {:type 'String!}
                       :follows {:type 'FriendConnection!}}}}
; 2️⃣`:pagination` 을 모두 찾아
^{::clerk/visibility {:code :show, :result :hide}}
(m/search data
          {!type {:pagination :relay}}
          !type)

; 3️⃣ Codegen
^{::clerk/visibility {:code :show :result :hide}}
{:FriendEdge       {:fields {:node   {:type 'Friend}
                             :cursor {:type 'String!}}}

 :FriendConnection {:fields {:edges    {:type '[FriendEdge]}
                             :pageInfo {:type 'PageInfo!}}}}

; ---

; ### 페이지 쿼리 명세 검사

; - 정방향 페이지네이션: `first`, `after` 인자가 필요
; - 역방향 페이지네이션: `last`, `before` 인자가 필요  
^{::clerk/visibility {:code :hide, :result :hide}}
(def pagination-args)
^{::clerk/visibility {:code :show, :result :hide}}
(m/match pagination-args

         {:first  {:type (pos-int? ?first)}
          :after  {:type (cursor? ?after)}
          :last   {:type (pos-int? ?last)}
          :before {:type (cursor? ?before)}}
         "양방향"

         {:first {:type (pos-int? ?first)}
          :after {:type (cursor? ?after)}}
         "정방향"

         {:last   {:type (pos-int? ?last)}
          :before {:type (cursor? ?before)}}
         "역방향"

         _ nil)

; ---

; ### 미사용 선언 찾기

{::clerk/visibility {:code :hide, :result :hide}}

; 아래 스키마에서 사용되지 않는 Input 타입을 찾아라.

^{::clerk/visibility {:code :show, :result :hide}}
(def schema
  {:input-objects {:UserInput  {:fields {:name     {:type 'String}
                                         :email    {:type 'String}
                                         :password {:type 'String}}}

                   :Dummy      {:fields {:id {:type :UserInput}}}}       ;; 👈 미사용

   :mutations     {:createUser {:type :User
                                :args {:input {:type '(non-null :UserInput)}}}}})

(defn unreachable-input-types [t]
  (m/find schema

          {:mutations {?query {:args {?input {:type (m/$ ~t)}}}}}
          ?query

          {:input-objects {?type {:fields {?field {:type (m/$ ~t)}}}}}
          ?type))

(def unreachable
  (clerk/code "(defn unreachable-input-types [t]
  (m/find schema

    {(m/or :queries :mutations) {?query {:args {?input {:type (m/$ ~t)}}}}}
    ?query

    {:input-objects {?type {:fields {?field {:type (m/$ ~t)}}}}}
    ?type))
"))

{::clerk/visibility {:code :hide, :result :show}}
(clerk/html
  {::clerk/width :full}
  [:div
   [:style "
   details {
     width: 80%;
     margin: 10px 0;
     padding: 10px;
     border: 1px solid #ccc;
     border-radius: 4px;
   }
   summary {
     cursor: pointer;
     padding: 5px;
   }
   summary:hover {
     background-color: #f5f5f5;
   }"]
   
   [:details
    [:summary "Answer"]
    ;quiz
    unreachable]
   [:details
    [:summary "Test"]
    (clerk/code "(unreachable-input-types :Dummy) ; => nil")
    (clerk/code "(unreachable-input-types :UserInput) ; => :createUser")]])

; ---
; ### 리졸버 작성
; - 예: 유저 농장의 날씨를 기상청에서 조회해서 알려주는 API
; - 서로 다른 데이터소스의 데이터들을 JOIN 하기

^::clerk/no-cache
(clerk/image "notebooks/liftio/dag3.png")

^{::clerk/visibility {:code :show, :result :hide}}
(m/rewrite {:user    {,,,}
            :farm    {,,,}
            :weather {,,,}}
           ;; match & rewrite
           )

; ---
; ### Meander 의 좋았던 점
;
; - 변환의 투명성
;   - 스펙이 변경에 매우 유연
; - 파서가 불필요
;   - 조건부 매칭, 문법 정의
; - 직관적인 데이터 결합
; - 풍부한 표현력
;   - 튜링-완전

; ---
; ### Meander 의 아쉬웠던 점

; - 속도
;   - 대부분 손으로 직접 짠 코드와 차이 없음
;   - 재귀적인 매칭에서 실수는 치명적 (튜링-완전)
; - 적응 난이도
;   - 학습할 시간에 Cursor 로 대충 짜버리는게
; - 모든 것이 못으로 보임
;   - 모든 로직을 Meander 로 짜고픈 유혹에 빠짐
;   - 때로는 절차를 드러내는 것이 나을 수도 있음

; ---
; ### Meander 내부에는
;
; - Parser
; - IR
; - DCE
; - Optimizer
; - Type Inference
; - Codegen 
; - ...
;
; 익숙한 키워드들의 향연


; ---
; ### 사실 컴파일러

; - `compiler : code -> code`
; - `meander : data -> data`

; - Given Homoiconicity, `code = data`
;    - `meander ↔ compiler`

(clerk/html [:hr])
; - Meander ζ - 미앤더로 미앤더 만들기 프로젝트
; - PEP 636 – Structural Pattern Matching

; ---
; ### 더 읽을거리

(clerk/html
  [:div.max-h-200.overflow-hidden.w-60
   (clerk/image "notebooks/liftio/sdf.png")])

; > ...패턴과 패턴 부합은 계산적 사고를 표현하는 한 방법일 수 있으며, 문제에 따라서는 다른 프로그래밍 방법보다 패턴과 패턴 부합으로 더 많은 것을 얻을 수 있다.
; > 그렇지만 패턴 부합이 이 세상 모든 문제의 답은 아님을 주의해야 한다. **따라서 패턴 부합에 중독되지는 말기 바란다.**

; ---
; ### 연결주의 시대에서 기호주의 의미 찾기

; Q. 요즘 같은 AI 시대에도 의미가 있습니까?
;
; A. 아마도요. "아직까지는" 의도 전달이 더 중요하니까요.

; ---
; ## End
;
