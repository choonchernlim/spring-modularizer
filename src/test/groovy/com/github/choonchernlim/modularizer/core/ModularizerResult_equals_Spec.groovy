package com.github.choonchernlim.modularizer.core

import groovy.transform.EqualsAndHashCode
import spock.lang.Specification

class ModularizerResult_equals_Spec extends Specification {

    static class Person extends ModularizerResult {
        String name
    }

    @EqualsAndHashCode(includes = ['name'])
    static class CustomPerson extends ModularizerResult {
        String name
    }

    def "given 2 objects with same id but different fingerprints, should be equal"() {
        when:
        def p1 = new Person(id: 1, fingerprint: 'fingerprint-1', name: 'name-1')
        def p2 = new Person(id: 1, fingerprint: 'fingerprint-2', name: 'name-2')

        then:
        p1 == p2
        p2 == p1
    }

    def "given 2 objects with different id but same fingerprints, should not be equal"() {
        when:
        def p1 = new Person(id: 1, fingerprint: 'fingerprint-1', name: 'name-1')
        def p2 = new Person(id: 2, fingerprint: 'fingerprint-1', name: 'name-1')

        then:
        p1 != p2
        p2 != p1
    }

    def "given overridden equals/hashcode with 2 objects with same name but different id and fingerprint, should be equal"() {
        when:
        def p1 = new CustomPerson(id: 1, fingerprint: 'fingerprint-3', name: 'name-1')
        def p2 = new CustomPerson(id: 2, fingerprint: 'fingerprint-4', name: 'name-1')

        then:
        p1 == p2
        p2 == p1
    }

    def "given overridden equals/hashcode with 2 objects with different name but same id and fingerprint, should not be equal"() {
        when:
        def p1 = new CustomPerson(id: 1, fingerprint: 'fingerprint-1', name: 'name-1')
        def p2 = new CustomPerson(id: 1, fingerprint: 'fingerprint-1', name: 'name-2')

        then:
        p1 != p2
        p2 != p1
    }
}
