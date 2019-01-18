package com.github.choonchernlim.modularizer.core

import groovy.transform.Immutable
import spock.lang.Specification

class ModularizerFingerprintService_createFingerprintByAnnotation_Spec extends Specification {

    @Immutable
    static class NoAnnotation extends ModularizerFingerprintable {
        String name
    }

    @Immutable
    static class WithAnnotation extends ModularizerFingerprintable {
        @ModularizerFingerprint
        String name

        @ModularizerFingerprint
        int key
    }

    def modularizerFingerprintService = new ModularizerFingerprintService()

    def "given null, should throw exception"() {
        when:
        modularizerFingerprintService.createFingerprintByAnnotation(null)

        then:
        thrown AssertionError
    }

    def "given no fields annotated with @FingerprintProperty, should throw exception"() {
        when:
        modularizerFingerprintService.createFingerprintByAnnotation(new NoAnnotation(name: 'name'))

        then:
        thrown AssertionError
    }

    def "given no fields annotated with @FingerprintProperty, should create fingerprint"() {
        given:
        def other = modularizerFingerprintService.createFingerPrintByValues('name', 1)

        def expected = modularizerFingerprintService.createFingerPrintByValues(1, 'name')

        assert expected != other

        when:
        def actual = modularizerFingerprintService.createFingerprintByAnnotation(new WithAnnotation(
                fingerprint: 'fingerprint',
                name: 'name',
                key: 1
        ))

        then:
        actual == expected

        when: 'Re-generating it again'
        actual = modularizerFingerprintService.createFingerprintByAnnotation(new WithAnnotation(
                fingerprint: 'fingerprint',
                name: 'name',
                key: 1
        ))

        then:
        actual == expected
    }
}
