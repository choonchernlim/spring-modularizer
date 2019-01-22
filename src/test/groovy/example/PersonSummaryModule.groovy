package example

import com.github.choonchernlim.modularizer.core.ModularizerFingerprint
import com.github.choonchernlim.modularizer.core.ModularizerResultModule

/**
 * "summary" data module where `name` property will be used to create the fingerprint.
 */
class PersonSummaryModule extends ModularizerResultModule {
    @ModularizerFingerprint
    String name
}