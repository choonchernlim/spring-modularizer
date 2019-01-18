package com.github.choonchernlim.modularizer.core

/**
 * Interface for getting the module name and its mapper class.
 */
interface ModularizerResultModuleConfig {

    String getModuleName()

    Class<ModularizerResultModuleMapper> getModuleMapperClass()
}
