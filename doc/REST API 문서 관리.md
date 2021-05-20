## REST API 문서 관리

API 문서는 중요합니다. 외부 개발자나 고객사에게 가이드를 할 경우 미리 문서화를 해놓고 외부에 공개만 하면 편합니다. 하지만 요구사항은 언제나 변할 수 있고 API 스펙도 바뀔 수 있습니다. 이럴 경우 문서관리하는 비용도 만만치 않고 개발도 하면서 문서도 작성하면 시간을 비효율적으로 낭비할 여지가 있습니다.

이러한 이유때문에 Swagger, RestDocs 등 여러 API 문서를 만드는 방안이 있었지만, 저는 Spring RestDocs를 사용하기로 했습니다. 그 중에서 asciidoc 플러그인을 사용하여 API 문서를 만들기로 하였습니다.

Spring RestDocs을 사용하면 TDD 방식으로 개발을 할 경우 테스트 코드의 성공을 기반으로 문서가 생성되기 때문에 API 스펙이 바뀔때마다 TDD 방식으로 개발하면서 문서도 요구사항에 맞게 유연하게 만들 수 있는 장점이 있습니다.

>
Spring Rest Docs에 관련되서는  [Spring Rest Docs](https://velog.io/@sa1341/Spring-Rest-Docs)을 참조해주세요.


#### JUnit5 기반의 RestDocs 설정하는 클래스 파일입니다.

ApiDocumentUtils 클래스에서는 요청 정보와 응답 정보를 pretty하게 보여주도록 설정하였고, API 문서에서 프로토콜, 호스트, 포트 정보를 설정할 수 있습니다.

```java
open class ApiDocumentUtils {
    companion object {
        fun getDocumentRequest(): OperationRequestPreprocessor? {
            return preprocessRequest(
                modifyUris() // (1)
                    .scheme("http")
                    .host("localhost")
                    .port(8080),
                prettyPrint()
            )
        }

        fun getDocumentResponse(): OperationResponsePreprocessor? {
            return preprocessResponse(prettyPrint())
        }
    }
}

@ExtendWith(SpringExtension::class, RestDocumentationExtension::class)
@AutoConfigureMockMvc
open class RestDocumentTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var context: WebApplicationContext

    val restDocumentation = RestDocumentationExtension()

    @BeforeEach
    fun setUp(
        webApplicationContext: WebApplicationContext?,
        restDocumentation: RestDocumentationContextProvider?
    ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .apply<DefaultMockMvcBuilder>(
                MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
            )
            .build()
    }
}
```

단위 API나 통합 테스트를 하는 경우에 `RestDocumentTests` 클래스를 상속받아서 mockMvc 객체를 통해서 RestDocs를 생성하도록 설정하는 부분입니다. 


Build 후 생성된 API html 파일 입니다.

![스크린샷 2021-05-20 오후 6 05 53](https://user-images.githubusercontent.com/22395934/119011858-6bed9300-b9d0-11eb-9b98-86d9d67257b9.png)
