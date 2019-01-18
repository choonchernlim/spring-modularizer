package com.github.choonchernlim.modularizer.core

import groovy.transform.EqualsAndHashCode

/**
 * This abstract class represents the root result object containing an ID property and some
 * {@link ModularizerResultModule} objects.
 */
@EqualsAndHashCode(includes = ['id'])
abstract class ModularizerResult extends ModularizerFingerprintable implements Serializable {

    String id
}
