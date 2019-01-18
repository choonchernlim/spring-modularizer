package com.github.choonchernlim.modularizer.core


import org.springframework.context.ApplicationContext

/**
 * Servlce class to create modularized result object.
 */
class ModularizerService {

    private final ApplicationContext context
    private final ModularizerFingerprintService fingerprintService

    ModularizerService(final ApplicationContext context, final ModularizerFingerprintService fingerprintService) {
        this.context = context
        this.fingerprintService = fingerprintService
    }

    /**
     * Populates and returns a result object based on the select modules.
     *
     * @param entity Entity containing the raw data
     * @param modules Modules needed to be populated
     * @param outputClass Root result class
     * @return Root result object
     */
    def <T extends ModularizerResult> T create(final ModularizerEntity entity = null,
                                               final Set<? extends ModularizerResultModuleConfig> modules,
                                               final Class<T> outputClass) {
        assert modules
        assert outputClass

        if (!entity) {
            return null
        }

        final Map<String, Object> map = modules.collectEntries {
            final ModularizerResultModuleMapper moduleMapper = context.getBean(it.moduleMapperClass)
            final String fieldName = it.moduleName
            final ModularizerFingerprintable moduleData = fingerprintService.withFingerprint(moduleMapper.map(entity))

            return [(fieldName): moduleData]
        } as HashMap<String, Object>

        map['id'] = entity.modularizerId

        return fingerprintService.withFingerprint(outputClass.newInstance(map))
    }
}
