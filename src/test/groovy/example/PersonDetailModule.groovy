package example

import com.github.choonchernlim.modularizer.core.ModularizerFingerprint
import com.github.choonchernlim.modularizer.core.ModularizerResultModule
import groovy.transform.Immutable

/**
 * "detail" data module where both `lastName` and `firstName` properties will be used to create the fingerprint.
 */
@Immutable
class PersonDetailModule extends ModularizerResultModule {
    String email

    @ModularizerFingerprint
    String lastName

    @ModularizerFingerprint
    String firstName
}