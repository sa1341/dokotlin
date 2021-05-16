package com.yolo.dokotlin.doc

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors.*


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
