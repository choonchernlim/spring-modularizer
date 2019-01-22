package com.github.choonchernlim.modularizer.core

import groovy.transform.Immutable
import spock.lang.Specification
import spock.lang.Unroll

class ModularizerUtils_setFieldValueByReflection_Spec extends Specification {

    @Immutable
    static class MyObject extends ModularizerFingerprintable {
        String name
    }

    @Unroll
    def "given #label, should throw exception"() {
        when:
        ModularizerUtils.setFieldValueByReflection(object, fieldName, 'value')

        then:
        thrown AssertionError

        where:
        label               | object                     | fieldName
        'null object'       | null                       | 'fingerprint'
        'null fieldName'    | new MyObject(name: 'name') | null
        'blank fieldName'   | new MyObject(name: 'name') | ' '
        'invalid fieldName' | new MyObject(name: 'name') | 'XXXx'
    }

    def "given field in concrete class, should set it"() {
        given:
        def object = new MyObject(name: null)

        assert !object.name?.trim()
        assert !object.fingerprint?.trim()

        when:
        object = ModularizerUtils.setFieldValueByReflection(object, 'name', 'value')

        then:
        object.name == 'value'
        !object.fingerprint?.trim()

        when:
        object = ModularizerUtils.setFieldValueByReflection(object, 'name', null)

        then:
        !object.name?.trim()
        !object.fingerprint?.trim()
    }

    def "given field in superclass, should set it"() {
        given:
        def object = new MyObject(name: 'name')
        assert object.name == 'name'
        assert !object.fingerprint?.trim()

        when:
        object = ModularizerUtils.setFieldValueByReflection(object, 'fingerprint', 'value')

        then:
        object.name == 'name'
        object.fingerprint == 'value'

        when:
        object = ModularizerUtils.setFieldValueByReflection(object, 'fingerprint', null)

        then:
        object.name == 'name'
        !object.fingerprint?.trim()
    }


}
