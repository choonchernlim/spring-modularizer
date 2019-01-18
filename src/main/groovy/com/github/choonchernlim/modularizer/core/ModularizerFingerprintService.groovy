package com.github.choonchernlim.modularizer.core

import org.apache.commons.codec.digest.DigestUtils

import java.lang.reflect.Field

/**
 * A service class that generates unique fingerprints.
 */
class ModularizerFingerprintService {

    /**
     * Creates fingerprint by scanning for fields annotated with @FingerprintProperty, then sets the fingerprint
     * value before returning the object.
     *
     * @param modularizerFingerprintable Fingerprintable object containing {@link ModularizerFingerprint} annotation(s)
     * @return New instance containing fingerprint value
     */
    def <T extends ModularizerFingerprintable> T withFingerprint(final T modularizerFingerprintable) {
        assert modularizerFingerprintable

        // recursively find declared field called `fingerprint` 
        Class clazz = modularizerFingerprintable.class
        while (!clazz.declaredFields.any { it.name == 'fingerprint' }) {
            clazz = clazz.superclass
        }

        final Field field = clazz.getDeclaredField('fingerprint')
        field.setAccessible(true)
        field.set(modularizerFingerprintable, createFingerprintByAnnotation(modularizerFingerprintable))

        return modularizerFingerprintable
    }

    /**
     * Generates fingerprint by scanning for fields annotated with @FingerprintProperty.
     *
     * @param modularizerFingerprintable Fingerprintable object containing {@link ModularizerFingerprint} annotation(s)
     * @return Fingerprint
     */
    String createFingerprintByAnnotation(final ModularizerFingerprintable modularizerFingerprintable) {
        assert modularizerFingerprintable

        // - get all fields
        // - remove unannotated fields
        final List<Field> fingerprintFields = modularizerFingerprintable.properties.
                collect { prop ->
                    modularizerFingerprintable.class.declaredFields.find {
                        it.name == prop.key && ModularizerFingerprint in it.declaredAnnotations*.annotationType()
                    }
                }.
                findAll { it }

        assert fingerprintFields, "Class ${modularizerFingerprintable.class} must have at least one field annotated with ${ModularizerFingerprint.class}."

        // - sort field by its name to ensure the order of the fingerprint property is always the same
        // - get all field values
        final Object[] fingerprintPropertyValues = fingerprintFields.
                sort { it.name }.
                collect {
                    // to allow accessing "private final" field
                    it.setAccessible(true)

                    return it.get(modularizerFingerprintable)
                }

        return createFingerPrintByValues(fingerprintPropertyValues)
    }

    /**
     * Generates fingerprint based on the given data objects. If the data object is of
     * type {@link ModularizerFingerprintable} and contains fingerprint value, that value will be used.
     * Otherwise, the object's toString() value will be used.
     *
     * @param objects Data objects
     * @return Fingerprint
     */
    String createFingerPrintByValues(final Object... objects) {
        assert objects

        final String combined = objects.
                collect {
                    final boolean isFingerprinted = it &&
                                                    it instanceof ModularizerFingerprintable &&
                                                    ((ModularizerFingerprintable) it).fingerprint?.trim()

                    final String className = it?.class?.simpleName ?: '-'

                    final String value = isFingerprinted ?
                            ((ModularizerFingerprintable) it).fingerprint :
                            it?.toString()

                    return "${className}:${value?.trim() ?: '-'}"
                }.
                join(',')

        return DigestUtils.sha256Hex(combined).toUpperCase()
    }
}
