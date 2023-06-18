## JPQL문법

select문 ::=
    select절 from절 [where] [groupby] [having] [orderby]

update문 ::=
    update절 [where]

delete문 ::=
    delete절 [where]

* select m from Member as m where m.age > 18
* 엔티티와 속성은 대소문자 구분가능
* JPQL 키워드는 대소문자 구분안됨
* 엔티티이름사용,, 테이블이름이 아니다.(엔티티클래스명)
* 별칭은 필수 (m) (as는 생략가능)

## TypeQuery or Query

TypeQuery : 반환 타입이 명확할때 사용 
* em.createQuery("select m from Member m", **Member.class**);

Query : 명확하지 않을때
* em.createQuery("select m from **m.username, m.age** from Member m");

## 결과 API

getResultList() : 결과가 하나 이상일때
* 결과 없으면 빈 리스트 반환

getSingleResult() : 결과가 정확히 하나
* 결과없으면 : NoResultException, 둘 이상이면 NonUniqueResultException

## 파라미터 바인딩

TypedQuery<Member> query = em.createQuery("select m from Member m **where m.username = :username**", Member.class);
query.setParameter("username","member1");

* 메소드 체인

em.createQuery("select m from Member m where m.username = :username", Member.class)
.setParameter("username","member1")
.getResultList();


## 프로젝션

select 절에 조회할 대상을 지정하는 것 (m / m.team / m.address / m.username)

엔티티 프로젝션의 결과를 수정했을경우 영속성 컨텍스트에 포함됨 (수정이 반영된다.)

### 여러값 조회시
1. query 타입 조회
2. query <Object[]> 타입 조회
3. new 명령어로 조회
   * DTO를 만들어서 결과를 받는다.(타입을 설정)
   * new 패키지명.생성자(매개변수)로 조회 가능

## 페이징 처리
* setFirstResult(int startPosition) : 조회시작 위치 (0부터 시작)
* setMaxResults(int maxResult) : 조회할 데이터 수

```java
List<Member> list = em.createQuery("select m from Member m order by m.age desc", Member.class)
.setFirstResult(0)
.setMaxResults(10)
.getResultList();
```

## 조인

1. 내부조인
    ```sql 
   select m from Member m [INNER] JOIN m.team t
   ``` 
2. 외부조인 (Member와 )
    ```sql 
    select m from Member m LEFT [OUTER] JOIN m.team t
   ``` 
3. 세타조인 (두 테이블을 다 들고와서 보는것)
    ```sql 
   select m from Member m, Team t where m.username = t.name\
   ``` 

### ON 절
  * 조인대상 필터링
  * 연관관계없는 엔티티 외부 조인
    ```sql
    select m, t FROM
    Member m LEFT JOIN Team t on m.username = t.name
    ```
  
## 서브쿼리 ( 쿼리에 쿼리를 만든다 )
* 나이가 평균보다 많은 회원
    ```sql
    select m from Member 
    where m.age > (select avg(m2.age) from Member m2)
    ```
* JPA 서브쿼리 한계 
  * FROM 절의 서브쿼리는 현재 JPQL 에서 불가능하다. (조인으로 해결)


## 타입표현
* JPQL 타입 표현
  1. 문자 : 'hello','she'
  2. 숫자 : 10L(long), 10D(double), 10F(Float)
  3. Boolean : TRUE,FALSE
  4. ENUM : jpabook.MemberType.Admin (패키지명 포함)
     * enum 사용 시 패키지포함하여 접근해야됨.
  5. 엔티티타입 : TYPE(m) = Member(상속관계 사용가능)

* 기타
  * exists, in
  * and, or, not
  * '>, =, <
  * between,like, isnull

## 조건식 - CASE 식
* 기본 케이스식
  * 조건에 대한 결과를 출력해라
* 단순 케이스식
  * 정확하게 매칭이 되면 출력해라
* coalesce : 하나씩 조회해서 null 아니면 반환
  * 사용자 이름이 없으면 이름 없는 회원을 반환
* NULLIF : 두값이같으면 null 반환, 다르면 첫번째값 반환
  * 사용자 이름이 관리자면 null 반환하고 나머지는 본인의 이름을 반환

## JPQL 함수
### JPQL 기본함수 
* concat (두 문자 합쳐준다)
  ```sql
  select concat('a','b') from Member m
* substring
* trim
* lower,upper
* length
* locate
  ```sql
    select locate('de','abcdegf') from Member m // 4출력
  ```
* abs, sqrt, mod
* size, index

### 사용자 정의 함수 호출
하이버네이트는 사용 전 방언에 추가해야 한다.
내가 사용하는 Dialect 를 상속받아서 사용자정의 클래스를 만들고 persistence xml에 등록하면 된다.


## 경로표현식
* .(점) 을 찍어서 객체 그래프 탐색하는 것
### 상태필드 : 단순히 값을 저장하기 위한 필드
* m.username
* 경로 탐색의 끝
### 연관필드 : 연관관계를 위한 필드
* 단일 값 연관 필드
  * @ManyToOne, @OneToOne 같이 대상이 엔티티
  * **묵시적 내부조인 발생**, 탐색O
  * 쿼리튜닝할때 어려움 (성능 저하원인될 수 도)
* 컬렉션 값 연관필드
  * @OneToMany, @ManyToMany 같이 대상이 컬렉션
  * 묵시적 내부조인 발생, 탐색X
  * t.members <- 점을 찍을 수 가 없음! (.size 정도만 가능)
  * 명시적 조인을 통해 별칭 얻으면 별칭 통해서 탐색 가능

결론 : 묵시적 조인 쓰지마라,, 튜닝도어렵고! 실무에선 비추다.


## fetch 조인
실무에서는 매우매우 중요하다
* sql 종류 x
* jpql **성능 최적화**를 위해 제공하는 기능
* 연관된 엔티티 컬렉션을 sql 한번에 함께 조회
* join fetch 명령어 사용

회원을 조회하면서 연관된 팀도 함께 조회
```jpaql
select m from Member m join fetch m.team
```
```sql
SELECT M.*, T.* FROM MEMBER M INNER JOIN TEAM T ON M.TEAM_ID=T.id
```

LAZY 형식의 조인 방식보다 fetch 조인이 먼저 실행이 되어 그래프 탐색시 캐시에 저장된 데이터를 통해 빠르게 해결

N+1문제를 해결할 수 있음

### 컬렉션 페치 조인
```jpaql
select t from Team t join fetch t.members 
```
DB 쿼리 날렸더니 n줄이 나올 수 있다.
1. SQL DISTINCT 추가로 중복을 없앤다
2. 애플리케이션에서 엔티티 중복제거

### 페치 조인과 일반 조인의 차이
* 일반 조인 실행 시 연관된 엔티티를 함께 조회하지 않음
* JPQL의 결과는 연관관계 고려X
* 페치 조인 사용 시 연관된 엔티티를 함께 조회함(즉시로딩)
* **페치 조인은 객체 그래프를 SQL 한번에 조회하는 개념**

### 페치 조인의 특징과 한계
* 페치 조인 대상에는 별칭을 줄 수 없다.
  * 하이버네이트는 가능, 가급적 사용X
* 둘 이상의 결렉션은 페치 조인할 수 없다.
* 컬렉션을 페치조인하면 페이징 API를 사용할 수없다.