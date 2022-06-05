package com.yolo.jean.doc

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint


open class ApiDocumentUtils {

    companion object {
        fun getDocumentRequest(): OperationRequestPreprocessor {
            return preprocessRequest(
                modifyUris()
                    .scheme("http")
                    .host("localhost")
                    .port(8080),
                prettyPrint()
            )
        }

        fun getDocumentResponse(): OperationResponsePreprocessor {
            return preprocessResponse(prettyPrint())
        }
    }
}
