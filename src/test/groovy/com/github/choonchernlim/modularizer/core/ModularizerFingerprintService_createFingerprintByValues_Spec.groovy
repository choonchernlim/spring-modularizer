package com.github.choonchernlim.modularizer.core

import groovy.transform.ToString
import spock.lang.Specification

import java.time.LocalDateTime

class ModularizerFingerprintService_createFingerprintByValues_Spec extends Specification {

    @ToString
    static class PersonFingerprintable extends ModularizerFingerprintable {
        String name
    }

    def modularizerFingerprintService = new ModularizerFingerprintService()

    def "given null, should throw exception"() {
        when:
        modularizerFingerprintService.createFingerPrintByValues(null)

        then:
        thrown AssertionError
    }

    def "given no params, should throw exception"() {
        when:
        modularizerFingerprintService.createFingerPrintByValues()

        then:
        thrown AssertionError
    }

    def "given params with null values, should generate fingerprint"() {
        when:
        def fingerprint = modularizerFingerprintService.createFingerPrintByValues(null, null, null)

        then:
        fingerprint?.trim()
    }

    def "given various params and multiple executions, should always generate same fingerprint"() {
        given:
        String string = 'string'
        LocalDateTime localDateTime = LocalDateTime.now()
        Integer number = 100

        when:
        def fingerprint1 = modularizerFingerprintService.createFingerPrintByValues(string, localDateTime, number)
        def fingerprint2 = modularizerFingerprintService.createFingerPrintByValues(string, localDateTime, number)
        def fingerprint3 = modularizerFingerprintService.createFingerPrintByValues(string, localDateTime, number)

        then:
        fingerprint1?.trim()
        fingerprint1 == fingerprint2
        fingerprint1 == fingerprint3
    }

    def "given 2 PersonFingerprintable objects with same fingerprint values, should match"() {
        given:
        PersonFingerprintable person1 = new PersonFingerprintable(name: 'Mike', fingerprint: 'fingerprint')
        PersonFingerprintable person2 = new PersonFingerprintable(name: 'Jones', fingerprint: 'fingerprint')

        when:
        def fingerprint1 = modularizerFingerprintService.createFingerPrintByValues(person1)
        def fingerprint2 = modularizerFingerprintService.createFingerPrintByValues(person2)

        then:
        fingerprint1?.trim()
        fingerprint2?.trim()
        fingerprint1 == fingerprint2
    }

    def "given 2 PersonFingerprintable objects with different fingerprint values, should not match"() {
        given:
        PersonFingerprintable person1 = new PersonFingerprintable(name: 'Mike', fingerprint: 'fingerprint-1')
        PersonFingerprintable person2 = new PersonFingerprintable(name: 'Jones', fingerprint: 'fingerprint-2')

        when:
        def fingerprint1 = modularizerFingerprintService.createFingerPrintByValues(person1)
        def fingerprint2 = modularizerFingerprintService.createFingerPrintByValues(person2)

        then:
        fingerprint1?.trim()
        fingerprint2?.trim()
        fingerprint1 != fingerprint2
    }

    def "given 2 PersonFingerprintable objects with no fingerprint values but same toString values, should match"() {
        given:
        PersonFingerprintable person1 = new PersonFingerprintable(name: 'Mike', fingerprint: null)
        PersonFingerprintable person2 = new PersonFingerprintable(name: 'Mike', fingerprint: null)

        when:
        def fingerprint1 = modularizerFingerprintService.createFingerPrintByValues(person1)
        def fingerprint2 = modularizerFingerprintService.createFingerPrintByValues(person2)

        then:
        fingerprint1?.trim()
        fingerprint2?.trim()
        fingerprint1 == fingerprint2
    }

    def "given 2 PersonFingerprintable objects with no fingerprint values and different toString values, should not match"() {
        given:
        PersonFingerprintable person1 = new PersonFingerprintable(name: 'Mike', fingerprint: null)
        PersonFingerprintable person2 = new PersonFingerprintable(name: 'Jones', fingerprint: null)

        when:
        def fingerprint1 = modularizerFingerprintService.createFingerPrintByValues(person1)
        def fingerprint2 = modularizerFingerprintService.createFingerPrintByValues(person2)

        then:
        fingerprint1?.trim()
        fingerprint2?.trim()
        fingerprint1 != fingerprint2
    }
}
