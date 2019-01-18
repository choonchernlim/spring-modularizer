package com.github.choonchernlim.modularizer.core

import groovy.transform.Immutable
import spock.lang.Specification

class ModularizerFingerprintService_withFingerprint_Spec extends Specification {

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
        modularizerFingerprintService.withFingerprint(null)

        then:
        thrown AssertionError
    }

    def "given no fields annotated with @ModularizerFingerprint, should throw exception"() {
        when:
        modularizerFingerprintService.withFingerprint(new NoAnnotation(name: 'name'))

        then:
        thrown AssertionError
    }

    def "given no fields annotated with @ModularizerFingerprint, should create fingerprint"() {
        given:
        def other = modularizerFingerprintService.createFingerPrintByValues('name', 1)

        def expected = modularizerFingerprintService.createFingerPrintByValues(1, 'name')

        assert expected != other

        when:
        WithAnnotation actual = modularizerFingerprintService.withFingerprint(new WithAnnotation(
                fingerprint: 'fingerprint',
                name: 'name',
                key: 1
        ))

        then:
        actual.fingerprint == expected
    }
}
