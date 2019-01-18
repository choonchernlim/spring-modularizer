package com.github.choonchernlim.modularizer.config

import com.github.choonchernlim.modularizer.core.ModularizerFingerprintService
import com.github.choonchernlim.modularizer.core.ModularizerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ModularizerConfig {
    @Autowired
    ApplicationContext context

    @Bean
    ModularizerFingerprintService modularizerFingerprintService() {
        return new ModularizerFingerprintService()
    }

    @Bean
    ModularizerService modularizerService(final ModularizerFingerprintService modularizerFingerprintService) {
        return new ModularizerService(context, modularizerFingerprintService)
    }
}
