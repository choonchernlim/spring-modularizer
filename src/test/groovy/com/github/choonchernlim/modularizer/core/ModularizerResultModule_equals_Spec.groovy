package com.github.choonchernlim.modularizer.core

import groovy.transform.EqualsAndHashCode
import spock.lang.Specification

class ModularizerResultModule_equals_Spec extends Specification {

    static class Person extends ModularizerResultModule {
        String name
    }

    @EqualsAndHashCode(includes = ['name'])
    static class CustomPerson extends ModularizerResultModule {
        String name
    }

    def "given 2 objects with same fingerprints, should be equal"() {
        when:
        def p1 = new Person(fingerprint: 'fingerprint-1', name: 'name-1')
        def p2 = new Person(fingerprint: 'fingerprint-1', name: 'name-2')

        then:
        p1 == p2
        p2 == p1
    }

    def "given 2 objects with different fingerprints, should not be equal"() {
        when:
        def p1 = new Person(fingerprint: 'fingerprint-1', name: 'name-1')
        def p2 = new Person(fingerprint: 'fingerprint-2', name: 'name-1')

        then:
        p1 != p2
        p2 != p1
    }

    def "given overridden equals/hashcode with 2 objects with same name but different id and fingerprint, should be equal"() {
        when:
        def p1 = new CustomPerson(fingerprint: 'fingerprint-3', name: 'name-1')
        def p2 = new CustomPerson(fingerprint: 'fingerprint-4', name: 'name-1')

        then:
        p1 == p2
        p2 == p1
    }

    def "given overridden equals/hashcode with 2 objects with different name but same id and fingerprint, should not be equal"() {
        when:
        def p1 = new CustomPerson(fingerprint: 'fingerprint-1', name: 'name-1')
        def p2 = new CustomPerson(fingerprint: 'fingerprint-1', name: 'name-2')

        then:
        p1 != p2
        p2 != p1
    }
}
