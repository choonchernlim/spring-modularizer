package com.github.choonchernlim.modularizer.core

import java.lang.reflect.Field

class ModularizerUtils {

    /**
     * Recursively find the declared field from the given object and set the value using reflection.
     *
     * @param object Object
     * @param fieldName Declared field name
     * @param value Value to be set
     * @return Object with the value set
     */
    static <T> T setFieldValueByReflection(final T object, final String fieldName, final Object value) {
        assert object
        assert fieldName?.trim()

        Class clazz = object.class
        Field field

        // recursively find declared field
        while (!(field = clazz.declaredFields.find { it.name == fieldName })) {
            clazz = clazz.superclass

            assert clazz, "${object.class} does not have declared field [${fieldName}]."
        }

        field.setAccessible(true)
        field.set(object, value)

        return object
    }
}
