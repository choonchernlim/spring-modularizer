package com.github.choonchernlim.modularizer.core

import groovy.transform.EqualsAndHashCode

/**
 * This abstract class introduces a fingerprint property.
 */
@EqualsAndHashCode(includes = ['fingerprint'])
abstract class ModularizerFingerprintable implements Serializable {

    String fingerprint
}
