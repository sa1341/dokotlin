# Kotlin Demo README 파일

## 코틀린으로 프로젝트를 구성한 이유?

매일 Java로 프로그램을 작성하다가 이번에 새로운 언어를 공부해보고 싶어서 코틀린을 공부하기로 했습니다. 코틀린은 구글이 안드로이드 개발언어로 공식적으로 채택했을 정도로 그 장래가 촉망받는 언어입니다. 주변에 지인들도 Kotlin을 공부하면서 한번 공부하라고 해서 Java에 비해서 어떤 장점이 있는지 찾아봤습니다.

#### 코틀린의 장점

- Java와 100% 상호 호환이 가능하고, JVM에서 동작한다.
- 다중 패러다임을 지원한다. (객체지향, 함수형 프로그래밍)
- Null 안정성을 보증한다.

구글리을 통해서 찾아보니 위 3가지가 대표적이였습니다. 여기서 가장 와닿는 부분은 Null 안정성이 였습니다. 실제로 간단한 Demo 프로젝트를 진행하면서 Null 처리가 정말 깔끔하게 구현이 가능했었습니다. Java에서는 Optional 객체를 사용하여 값이 null처리를 했었지만요.

## 프로젝트 도메인

시중에 나온 코틀린 책이 몇권있지만.. 어차피 공부할거 간단한 REST API를 구현하여 빨리 적응하고 싶어서 간단한 데모 프로젝트를 만들었습니다.

이제 도메인을 선정할 일만 남았는데요. 창의성이 부족한 저로써는 도무지 좋은 아이디어가 떠오르지 않았고. 이럴 때 국룰이라고 할 수 있는 가장 무난한 게시판 API 기능을 구현하기로 해봤습니다. 기본적으로 CRDU 작업을 할 때 개발자들이 가장 많이 접하는 프로젝트이기도 하기 때문입니다.

### 기술 스펙

- Spring Boot
- Spring Data JPA
- MariaDB
- Kotlin
- QueryDSL
- 테스트(RestDocs)

### 클래스 다이어그램 구성도

![image](https://user-images.githubusercontent.com/22395934/119007109-ea940180-b9cb-11eb-8d0a-e7471048b040.png)


핵심 도메인은 간단하게 게시판(Board)엔티티와 댓글(Reply) 엔티티로 구성하였습니다.

Spring Data JPA를 사용하기 때문에 MariadDB라는 관계형 데이터베이스와 매핑을 하였습니다. 

MariaDB 스키마 구조는 아래와 같습니다.

### DB 스키마 구조

#### Board 스키마 

![image](https://user-images.githubusercontent.com/22395934/119007525-43639a00-b9cc-11eb-87ab-8464c472b54d.png)

#### Reply 스키마 

![image](https://user-images.githubusercontent.com/22395934/119007619-5b3b1e00-b9cc-11eb-8db4-782cbb11aadf.png)


### 매핑 전략

게시글과 댓글 엔티티는 1<-> 다로 연관관계를 매핑하였습니다. 그 이유는 게시글 하나는 여러개의 댓글을 소유할 수 있기 때문입니다. 여기서 객체 그래프 탐색은 양방향 연관관계로 설정하였습니다. 

## 프로젝트 목차


1. [step-01: 표현 계층 구현 전략](https://github.com/sa1341/kotlin-demo/blob/master/doc/%ED%91%9C%ED%98%84%20%EA%B3%84%EC%B8%B5%20%EA%B5%AC%ED%98%84%20%EC%A0%84%EB%9E%B5.md)

2. [step-02: 게시글 및 댓글 페이징 전략](https://github.com/sa1341/kotlin-demo/blob/master/doc/%EA%B2%8C%EC%8B%9C%EA%B8%80%20%EB%B0%8F%20%EB%8C%93%EA%B8%80%20%ED%8E%98%EC%9D%B4%EC%A7%95%20%EC%A0%84%EB%9E%B5.md)

3. [step-03: Exception 전략](https://github.com/sa1341/kotlin-demo/blob/master/doc/Exception%20%EC%A0%84%EB%9E%B5.md)

4. [step-04: API 문서 관리 전략](https://github.com/sa1341/kotlin-demo/blob/master/doc/REST%20API%20%EB%AC%B8%EC%84%9C%20%EA%B4%80%EB%A6%AC.md) 


## 프로젝트를 진행하면서 겪은 시행 착오

프로젝트를 진행하면서 기존에 Spring Boot에서 사용해왔던 Java와 Kotlin의 차이점은 아래와 같습니다.

1. Lombok이 필요가 없습나다. 
    - 코틀린은 클래스 생성 시 기본적으로 getter, setter를 만들어 줍니다. 실제 `Board.id`와 같이 멤버 변수를 사용할 경우 직접 접근하는 것처럼 보이지만 내부적으로 getter를 사용하여 멤버에 접근을 합니다. 

    - val은 immutable이기 때문에 getter만 생성됨, 특징 상 setter는 불가능, var는 getter, setter 둘다 생성됩니다.

2. Kotlin의 immutable(불변성)이라는 특성 때문에 클래스 생성 시 기본적으로 상속을 허용하지 않습니다. 따라서 IntelliJ에서 코틀린 코드를 Java로 디컴파일하는 기능을 사용하여 클래스를 살펴보면 final 키워드가 클래스와 필드, 메서드에 붙어있습니다. 

이게 무슨 문제였냐면 Java에서는 기본적으로 JPA의 Lazy Loading(지연로딩)을 사용할 경우 프록시 초기화를 통해서 지연로딩이 가능했었지만, 코틀린에서는 final 키워드가 붙기 때문에 프록시가 엔티티를 상속받을 수가 없어서 프록시 초기화가 불가능한 어려움을 겪었습니다. 

이러한 문제를 해결하기 위해 갓구글링을 하였고, 빌드 스크립트에 정의되어 있는 플러그인 중에 jpa 플러그인이 @Entity 어노테이션이 적용된 클래스에 기본 생성자를 만들어준다는 것을 찾았습니다. 그리고 final 키워드 대신 open 키워드를 적용하여 프록시 생성이 가능하게 할 수 있었습니다.


번거롭지만 아래와 같이 설정하면 됩니다.

```java
plugins {
    kotlin("plugin.jpa") version "1.4.10"
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}
```

> [git 소스코드](https://github.com/sa1341/kotlin-demo)

