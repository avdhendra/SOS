package org.n52.sos.ds.hibernate.util.observation;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import javax.inject.Inject;

import org.n52.faroe.ConfigurationError;
import org.n52.faroe.Validation;
import org.n52.faroe.annotation.Configurable;
import org.n52.faroe.annotation.Setting;
import org.n52.iceland.cache.ContentCacheController;
import org.n52.iceland.convert.ConverterRepository;
import org.n52.iceland.i18n.I18NDAORepository;
import org.n52.iceland.i18n.I18NSettings;
import org.n52.iceland.ogc.ows.OwsServiceMetadataRepository;
import org.n52.iceland.service.ServiceSettings;
import org.n52.iceland.util.LocalizedProducer;
import org.n52.shetland.ogc.ows.OwsServiceProvider;
import org.n52.sos.cache.SosContentCache;
import org.n52.sos.ds.FeatureQueryHandler;
import org.n52.sos.ds.hibernate.dao.DaoFactory;
import org.n52.sos.ds.hibernate.util.procedure.generator.HibernateProcedureDescriptionGeneratorFactoryRepository;
import org.n52.sos.service.profile.ProfileHandler;
import org.n52.sos.util.GeometryHandler;
import org.n52.svalbard.CodingSettings;

@Configurable
public class OmObservationCreatorContext {

    private OwsServiceMetadataRepository serviceMetadataRepository;
    private I18NDAORepository i18nr;
    private String tokenSeparator;
    private String tupleSeparator;
    private String decimalSeparator;
    private DaoFactory daoFactory;
    private ProfileHandler profileHandler;
    private AdditionalObservationCreatorRepository additionalObservationCreatorRepository;
    private ContentCacheController contentCacheController;
    private FeatureQueryHandler featureQueryHandler;
    private ConverterRepository converterRepository;
    private HibernateProcedureDescriptionGeneratorFactoryRepository procedureDescriptionGeneratorFactoryRepository;
    private GeometryHandler geometryHandler;
    private Locale defaultLanguage;
    private URI serviceURL;

    @Inject
    public OmObservationCreatorContext(
            OwsServiceMetadataRepository serviceMetadataRepository,
            I18NDAORepository i18nr,
            DaoFactory daoFactory,
            ProfileHandler profileHandler,
            AdditionalObservationCreatorRepository additionalObservationCreatorRepository,
            ContentCacheController contentCacheController,
            FeatureQueryHandler featureQueryHandler,
            ConverterRepository converterRepository,
            HibernateProcedureDescriptionGeneratorFactoryRepository procedureDescriptionGeneratorFactoryRepository,
            GeometryHandler geometryHandler) {
        super();
        this.serviceMetadataRepository = serviceMetadataRepository;
        this.i18nr = i18nr;
        this.daoFactory = daoFactory;
        this.profileHandler = profileHandler;
        this.additionalObservationCreatorRepository = additionalObservationCreatorRepository;
        this.contentCacheController = contentCacheController;
        this.featureQueryHandler = featureQueryHandler;
        this.converterRepository = converterRepository;
        this.procedureDescriptionGeneratorFactoryRepository = procedureDescriptionGeneratorFactoryRepository;
        this.geometryHandler = geometryHandler;
    }

    @Setting(CodingSettings.TOKEN_SEPARATOR)
    public void setTokenSeparator(final String separator) throws ConfigurationError {
        Validation.notNullOrEmpty("Token separator", separator);
        tokenSeparator = separator;
    }

    @Setting(CodingSettings.TUPLE_SEPARATOR)
    public void setTupleSeparator(final String separator) throws ConfigurationError {
        Validation.notNullOrEmpty("Tuple separator", separator);
        tupleSeparator = separator;
    }

    @Setting(CodingSettings.DECIMAL_SEPARATOR)
    public void setDecimalSeparator(final String separator) throws ConfigurationError {
        Validation.notNullOrEmpty("Decimal separator", separator);
        decimalSeparator = separator;
    }

    @Setting(I18NSettings.I18N_DEFAULT_LANGUAGE)
    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = new Locale(defaultLanguage);
    }

    @Setting(ServiceSettings.SERVICE_URL)
    public void setServiceURL(URI serviceURL) throws ConfigurationError {
        Validation.notNull("Service URL", serviceURL);
        String url = serviceURL.toString();
        if (url.contains("?")) {
            url = url.split("[?]")[0];
        }
        try {
            this.serviceURL = new URI(url);
        } catch (URISyntaxException e) {
            new ConfigurationError(e);
        }
    }

    /**
     * @return the serviceProvider
     */
    public LocalizedProducer<OwsServiceProvider> getServiceProvider(String service) {
        return this.serviceMetadataRepository.getServiceProviderFactory(service);
    }

    /**
     * @return the i18nr
     */
    public I18NDAORepository getI18nr() {
        return i18nr;
    }

    /**
     * @return the tokenSeparator
     */
    public String getTokenSeparator() {
        return tokenSeparator;
    }

    /**
     * @return the tupleSeparator
     */
    public String getTupleSeparator() {
        return tupleSeparator;
    }

    /**
     * @return the decimalSeparator
     */
    public String getDecimalSeparator() {
        return decimalSeparator;
    }

    /**
     * @return the daoFactory
     */
    public DaoFactory getDaoFactory() {
        return daoFactory;
    }

    /**
     * @return the profileHandler
     */
    public ProfileHandler getProfileHandler() {
        return profileHandler;
    }

    /**
     * @return the additionalObservationCreatorRepository
     */
    public AdditionalObservationCreatorRepository getAdditionalObservationCreatorRepository() {
        return additionalObservationCreatorRepository;
    }

    /**
     * @return the cache
     */
    public SosContentCache getCache() {
        return (SosContentCache) contentCacheController.getCache();
    }

    /**
     * @return the featureQueryHandler
     */
    public FeatureQueryHandler getFeatureQueryHandler() {
        return featureQueryHandler;
    }

    /**
     * @return the converterRepository
     */
    public ConverterRepository getConverterRepository() {
        return converterRepository;
    }

    /**
     * @return the procedureDescriptionGeneratorFactoryRepository
     */
    public HibernateProcedureDescriptionGeneratorFactoryRepository getProcedureDescriptionGeneratorFactoryRepository() {
        return procedureDescriptionGeneratorFactoryRepository;
    }

    /**
     * @return the geometryHandler
     */
    public GeometryHandler getGeometryHandler() {
        return geometryHandler;
    }

    /**
     * @return the serviceMetadataRepository
     */
    public OwsServiceMetadataRepository getServiceMetadataRepository() {
        return serviceMetadataRepository;
    }

    /**
     * @return the defaultLanguage
     */
    public Locale getDefaultLanguage() {
        return defaultLanguage;
    }

    /**
     * @return the serviceURL
     */
    public URI getServiceURL() {
        return serviceURL;
    }

}
