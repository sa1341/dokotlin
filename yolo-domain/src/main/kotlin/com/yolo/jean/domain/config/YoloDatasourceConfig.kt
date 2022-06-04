package com.yolo.jean.domain.config

import com.yolo.jean.domain.common.logger
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@Configuration
@EnableJpaAuditing
class YoloDatasourceConfig {

    private val log by logger()

    private val DEFAULT_TIME_ZONE = TimeZone.getTimeZone("Asia/Seoul")

    init {
        log.debug("####### YoloDatasourceConfig init #######")
        initTimeZone()
    }

    private fun initTimeZone() {

        val userTimeZone = System.getProperty("user.timezone")

        if (userTimeZone != null && DEFAULT_TIME_ZONE.id != userTimeZone) {
            log.warn(
                "VM option [user.timezone={}] will be ignored",
                userTimeZone
            )
        }

        TimeZone.setDefault(DEFAULT_TIME_ZONE)

        if (TimeZone.getDefault() != DEFAULT_TIME_ZONE) {
            throw AssertionError(
                String.format(
                    "Expected [%s] but was [%s]",
                    DEFAULT_TIME_ZONE.id,
                    TimeZone.getDefault().id
                )
            )
        }
    }
}
