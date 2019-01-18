package com.github.choonchernlim.modularizer.core

/**
 * Interface for entity for producing modularized result object.
 */
interface ModularizerEntity {

    /**
     * Returns modularizer ID that represents the entity. This ID can be either internal ID or an unique business key.
     *
     * @return Modularizer ID
     */
    String getModularizerId()
}
