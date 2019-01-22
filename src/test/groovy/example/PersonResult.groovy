package example

import com.github.choonchernlim.modularizer.core.ModularizerFingerprint
import com.github.choonchernlim.modularizer.core.ModularizerResult

/**
 * This is the root result class where both `summary` and `detail` properties will be used to create the fingerprint.
 */
class PersonResult extends ModularizerResult {
    @ModularizerFingerprint
    PersonSummaryModule summary

    @ModularizerFingerprint
    PersonDetailModule detail
}