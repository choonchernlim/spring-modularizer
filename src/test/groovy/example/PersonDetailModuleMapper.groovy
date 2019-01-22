package example

import com.github.choonchernlim.modularizer.core.ModularizerResultModuleMapper
import org.springframework.stereotype.Service

/**
 * Spring bean mapper class that transforms {@Link PersonEntity} to {@link PersonDetailModule}.
 */
@Service
class PersonDetailModuleMapper implements ModularizerResultModuleMapper<PersonEntity, PersonDetailModule> {
    @Override
    PersonDetailModule map(final PersonEntity entity) {
        return new PersonDetailModule(
                email: entity.email,
                lastName: entity.lastName,
                firstName: entity.firstName
        )
    }
}