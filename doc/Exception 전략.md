## Exception 전략

이전에는 응용 계층이나 도메인계층, 인프라 계층에서 발생한 예외처리를 Controller에서 try ~ catch를 통해서 처리했지만, 이렇게 되면 어쩔 수 없이 Controller 메서드의 가독성이 좋지 않게 됩니다. 그래서 Business Exception, Exception 등과 같은 예외에 대해서 @RestControllerAdvice 를 통해서 전역으로 관리하도록 구현해봤습니다.


#### ExceptionHandler 적용 예제코드

```java
@RestControllerAdvice
class GlobalExceptionController {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(value = [BindException::class])
    fun handleBindException(e: BindException): ResponseEntity<ErrorResponse> {
        logger.error("handleBindException", e)
        val errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.bindingResult)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        logger.error("MethodArgumentNotValidException", e)
        val errorResponse = ErrorResponse.of(ErrorCode.METHOD_NOT_VALID, e.bindingResult)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
}
```

Rest API에서 발생하는 모든 예외는 해당 클래스에서 관리하도록 하였고, 이렇게 하면 Business Exception에 대해서도 관리하기가 쉬워집니다. 또한 통일성 있는 에러 객체를 내려줄 수도 있습니다. 

### 에러 메시지 포맷

아래 Talend를 사용하여 REST API 테스트 결과화면에서 검증 실패에 대한 에러에 대해서 pretty하게 JSON 포맷으로 내려줄 수 있습니다.

![image](https://user-images.githubusercontent.com/22395934/119015111-91c86700-b9d3-11eb-96d2-5c19d091eff5.png)


## BusinessException

어플리케이션에서 무수히 많은 Exception이 발생 할 수 있지만, 주로 업무 비즈니스에 한해서 개발자가 예상할 수 있는 Exception도 발생할 수 있습니다. 주로 EntityNotFound라는 엔티티 객체가 존재하지 않을 경우나 검증 실패 시 부적절한 값이 들어올 경우 공통으로 발생할 수 있는 InvalidException도 있습니다.

이러한 Exception을 BusinessException이라는 하나의 추상화 된 Exception 클래스를 정의하였습니다.

### BusinessException 클래스 다이어그램

![image](https://user-images.githubusercontent.com/22395934/119015992-65f9b100-b9d4-11eb-8a8d-bbbf5065553b.png)


```java
open class BusinessException(override val message: String): RuntimeException(message) {

    open lateinit var errorCode: ErrorCode

    constructor(message: String, errorCode: ErrorCode) : this(message) {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode) : this(errorCode.message) {
        this.errorCode = errorCode
    }
}
```

BusinessException은 상속이 가능하도록 open 키워드를 클래스에 적용하였습니다. unChecked Exception인 RuntimeException을 상속받았습니다. Global ExceptionHandler에서 ErrorCode의 객체를 JSON 포맷으로 파싱하여 에러 내용을 내려줍니다.

## 에러 메시지 및 코드 관리

에러 메시지와 에러 코드를 어떻게 관리할까 생각하다가 훌륭한 개발자분들의 여러 Exception 전략을 참조하게 되었고, Enum 타입의 ErrorCode 클래스를 정의하여 에러코드와 메시지를 관리하는 방법에 대해서 참조하게 되었습니다.

```java
enum class ErrorCode(val status: Int, val message: String) {
    // Common
    INVALID_INPUT_VALUE(400, "Invalid input value"),
    METHOD_NOT_ALLOWED(405, "Method is not allowed"),
    INTERNAL_SERVER_ERROR(500, "Server Error"),
    ENTITY_NOT_FOUND(400, "Entity not found"),
    METHOD_NOT_VALID(400, "Method is not valid");
}
```

만약 새로운 에러 코드와 에러 메시지가 추가된다면 ErrorCode 클래스에서만 새롭게 상수를 추가하여 유지보수 측면에서 많은 이점을 누릴 수 있는 구조라고 생각됩니다.
