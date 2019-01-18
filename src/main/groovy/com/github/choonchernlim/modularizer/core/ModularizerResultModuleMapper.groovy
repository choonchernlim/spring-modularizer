package com.github.choonchernlim.modularizer.core

/**
 * Interface to map {@link ModularizerEntity} to {@link ModularizerResultModule}.
 */
interface ModularizerResultModuleMapper<T extends ModularizerEntity, K extends ModularizerResultModule> {
    K map(T entity)
}
