package example

import com.github.choonchernlim.modularizer.core.ModularizerEntity
import groovy.transform.Immutable

/**
 * This class contains the raw data needed by the mapper classes to create the data module beans.
 */
@Immutable
class PersonEntity implements ModularizerEntity {
    String email
    String lastName
    String firstName

    /**
     * Returns the ID of the result object of type {@link com.github.choonchernlim.modularizer.core.ModularizerResult}.
     *
     * This ID could be an internal ID (ie: DB primary key) or some business key that will uniquely identify the result object.
     *
     * @return ID value
     */
    @Override
    String getModularizerId() {
        return email
    }
}
