package example

import com.github.choonchernlim.modularizer.core.ModularizerFingerprint
import com.github.choonchernlim.modularizer.core.ModularizerResultModule
import groovy.transform.Immutable

/**
 * "summary" data module where `name` property will be used to create the fingerprint.
 */
@Immutable
class PersonSummaryModule extends ModularizerResultModule {
    @ModularizerFingerprint
    String name
}