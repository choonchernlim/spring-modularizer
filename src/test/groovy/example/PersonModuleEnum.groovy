package example

import com.github.choonchernlim.modularizer.core.ModularizerResultModuleConfig
import com.github.choonchernlim.modularizer.core.ModularizerResultModuleMapper

/**
 * All available person modules and the Spring-managed mapper classes.
 */
enum PersonModuleEnum implements ModularizerResultModuleConfig {
    SUMMARY('summary', PersonSummaryModuleMapper),
    DETAIL('detail', PersonDetailModuleMapper)

    final String moduleName
    final Class<ModularizerResultModuleMapper> moduleMapperClass

    PersonModuleEnum(final String moduleName, final Class<ModularizerResultModuleMapper> moduleMapperClass) {
        this.moduleName = moduleName
        this.moduleMapperClass = moduleMapperClass
    }
}