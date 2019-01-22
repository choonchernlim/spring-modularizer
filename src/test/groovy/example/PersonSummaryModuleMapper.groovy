package example

import com.github.choonchernlim.modularizer.core.ModularizerResultModuleMapper
import org.springframework.stereotype.Service

/**
 * Spring bean mapper class that transforms {@Link PersonEntity} to {@link PersonSummaryModule}.
 */
@Service
class PersonSummaryModuleMapper implements ModularizerResultModuleMapper<PersonEntity, PersonSummaryModule> {
    @Override
    PersonSummaryModule map(final PersonEntity entity) {
        return new PersonSummaryModule(
                name: "${entity.lastName}, ${entity.firstName}"
        )
    }
}
