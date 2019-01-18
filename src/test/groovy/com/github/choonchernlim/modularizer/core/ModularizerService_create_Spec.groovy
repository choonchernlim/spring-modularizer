package com.github.choonchernlim.modularizer.core


import org.springframework.context.ApplicationContext
import spock.lang.Specification
import spock.lang.Unroll

class ModularizerService_create_Spec extends Specification {

    def context = Mock ApplicationContext
    def modularizerFingerprintService = new ModularizerFingerprintService()
    def modularizerService = new ModularizerService(context, modularizerFingerprintService)

    static class PersonEntity implements ModularizerEntity {
        String email
        String lastName
        String firstName

        @Override
        String getModularizerId() {
            return email
        }
    }

    static class PersonResult extends ModularizerResult {
        static class PersonSummary extends ModularizerResultModule {
            @ModularizerFingerprint
            String name
        }

        static class PersonDetail extends ModularizerResultModule {
            @ModularizerFingerprint
            String email

            String lastName
            String firstName
        }

        @ModularizerFingerprint
        PersonSummary summary

        @ModularizerFingerprint
        PersonDetail detail
    }

    static class PersonSummaryModuleMapper implements ModularizerResultModuleMapper<PersonEntity, PersonResult.PersonSummary> {
        @Override
        PersonResult.PersonSummary map(final PersonEntity entity) {
            return new PersonResult.PersonSummary(
                    name: "${entity.lastName}, ${entity.firstName}"
            )
        }
    }

    static class PersonDetailModuleMapper implements ModularizerResultModuleMapper<PersonEntity, PersonResult.PersonDetail> {
        @Override
        PersonResult.PersonDetail map(final PersonEntity entity) {
            return new PersonResult.PersonDetail(
                    email: entity.email,
                    lastName: entity.lastName,
                    firstName: entity.firstName
            )
        }
    }

    static enum PersonModuleEnum implements ModularizerResultModuleConfig {
        SUMMARY('summary', PersonSummaryModuleMapper),
        DETAIL('detail', PersonDetailModuleMapper)

        final String moduleName
        final Class<ModularizerResultModuleMapper> moduleMapperClass

        PersonModuleEnum(final String moduleName, final Class<ModularizerResultModuleMapper> moduleMapperClass) {
            this.moduleName = moduleName
            this.moduleMapperClass = moduleMapperClass
        }
    }

    static final PersonEntity PERSON_ENTITY = new PersonEntity(
            lastName: 'Bond',
            firstName: 'James',
            email: 'james.bond@gmail.com'
    )

    @Unroll
    def "given #label, should throw error"() {
        when:
        modularizerService.create(entity, modules, outputClass)

        then:
        0 * _

        thrown AssertionError

        where:
        label              | entity        | modules                           | outputClass
        'null fields'      | PERSON_ENTITY | null                              | PersonResult
        'empty fields'     | PERSON_ENTITY | [] as Set                         | PersonResult
        'null outputClass' | PERSON_ENTITY | [PersonModuleEnum.SUMMARY] as Set | null
    }

    def "given null entity, should return null"() {
        when:
        def result = modularizerService.create(null, [PersonModuleEnum.SUMMARY] as Set, PersonResult)

        then:
        0 * _

        result == null
    }

    def "given entity with 1 data module, should populate just 1 data module"() {
        when:
        def result1 = modularizerService.create(PERSON_ENTITY, [PersonModuleEnum.SUMMARY] as Set, PersonResult)

        then:
        1 * context.getBean(PersonModuleEnum.SUMMARY.moduleMapperClass) >> new PersonSummaryModuleMapper()
        0 * _

        result1.fingerprint
        result1.id == 'james.bond@gmail.com'

        result1.summary.fingerprint
        result1.summary.name == 'Bond, James'

        result1.detail == null

        when: 'Create using the same arguments again'
        def result2 = modularizerService.create(PERSON_ENTITY, [PersonModuleEnum.SUMMARY] as Set, PersonResult)

        then: 'Everything should still be the same'
        1 * context.getBean(PersonModuleEnum.SUMMARY.moduleMapperClass) >> new PersonSummaryModuleMapper()
        0 * _

        result1 == result2

        result1.fingerprint == result2.fingerprint
        result1.id == result2.id

        result1.summary == result2.summary
        result1.summary.fingerprint == result2.summary.fingerprint
        result1.summary.name == result2.summary.name

        result1.detail == result2.detail
    }

    def "given entity with 2 data modules, should populate 2 data modules"() {
        when:
        def result1 = modularizerService.create(PERSON_ENTITY,
                                                [PersonModuleEnum.SUMMARY, PersonModuleEnum.DETAIL] as Set,
                                                PersonResult)

        then:
        1 * context.getBean(PersonModuleEnum.SUMMARY.moduleMapperClass) >> new PersonSummaryModuleMapper()
        1 * context.getBean(PersonModuleEnum.DETAIL.moduleMapperClass) >> new PersonDetailModuleMapper()
        0 * _

        result1.fingerprint
        result1.id == 'james.bond@gmail.com'

        result1.summary.fingerprint
        result1.summary.name == 'Bond, James'

        result1.detail.fingerprint
        result1.detail.lastName == 'Bond'
        result1.detail.firstName == 'James'
        result1.detail.email == 'james.bond@gmail.com'

        when: 'Create using the same arguments again'
        def result2 = modularizerService.create(PERSON_ENTITY,
                                                [PersonModuleEnum.SUMMARY, PersonModuleEnum.DETAIL] as Set,
                                                PersonResult)

        then: 'Everything should still be the same'
        1 * context.getBean(PersonModuleEnum.SUMMARY.moduleMapperClass) >> new PersonSummaryModuleMapper()
        1 * context.getBean(PersonModuleEnum.DETAIL.moduleMapperClass) >> new PersonDetailModuleMapper()
        0 * _

        result1 == result2

        result1.fingerprint == result2.fingerprint
        result1.id == result2.id

        result1.summary == result2.summary
        result1.summary.fingerprint == result2.summary.fingerprint
        result1.summary.name == result2.summary.name

        result1.detail == result2.detail
        result1.detail.fingerprint == result2.detail.fingerprint
        result1.detail.lastName == result2.detail.lastName
        result1.detail.firstName == result2.detail.firstName
        result1.detail.email == result2.detail.email
    }
}
