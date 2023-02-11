package com.yolo.jean.api.global.common.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DateUtils {

    companion object {

        fun parseCreatedDt(createdAt: Instant): String {
            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")
                .withZone(ZoneId.systemDefault())
            return formatter.format(createdAt)
        }

        fun parseUpdatedDt(updatedAt: Instant): String {
            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")
                .withZone(ZoneId.systemDefault())
            return formatter.format(updatedAt)
        }
    }
}
