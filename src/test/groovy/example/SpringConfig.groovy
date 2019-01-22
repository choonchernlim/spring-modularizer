package example

import com.github.choonchernlim.modularizer.config.ModularizerConfig
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 * Spring configuration that imports {@link ModularizerConfig} so that
 * {@link com.github.choonchernlim.modularizer.core.ModularizerService} can be autowired.
 *
 * Component scan `example` package so that Spring can find the mapper classes
 * (ex: {@link PersonSummaryModuleMapper} and {@link PersonDetailModuleMapper}).
 */
@Configuration
@Import(ModularizerConfig)
@ComponentScan('example')
class SpringConfig {
}
