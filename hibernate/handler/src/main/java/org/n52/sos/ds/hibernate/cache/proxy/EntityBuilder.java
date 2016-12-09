/*
 * Copyright (C) 2012-2016 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published
 * by the Free Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of
 * the following licenses, the combination of the program with the linked
 * library is not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed
 * under the aforementioned licenses, is permitted by the copyright holders
 * if the distribution is compliant with both the GNU General Public
 * License version 2 and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 */
package org.n52.sos.ds.hibernate.cache.proxy;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.n52.proxy.db.beans.RelatedFeatureEntity;
import org.n52.proxy.db.beans.RelatedFeatureRoleEntity;
import org.n52.series.db.beans.CategoryEntity;
import org.n52.series.db.beans.CountDatasetEntity;
import org.n52.series.db.beans.DatasetEntity;
import org.n52.series.db.beans.DescribableEntity;
import org.n52.series.db.beans.FeatureEntity;
import org.n52.series.db.beans.GeometryEntity;
import org.n52.series.db.beans.MeasurementDatasetEntity;
import org.n52.series.db.beans.OfferingEntity;
import org.n52.series.db.beans.PhenomenonEntity;
import org.n52.series.db.beans.ProcedureEntity;
import org.n52.series.db.beans.ServiceEntity;
import org.n52.series.db.beans.TextDatasetEntity;
import org.n52.series.db.beans.UnitEntity;
import org.n52.sos.ds.hibernate.entities.AbstractIdentifierNameDescriptionEntity;
import org.n52.sos.ds.hibernate.entities.FeatureOfInterest;
import org.n52.sos.ds.hibernate.entities.ObservableProperty;
import org.n52.sos.ds.hibernate.entities.Offering;
import org.n52.sos.ds.hibernate.entities.Procedure;
import org.n52.sos.ds.hibernate.entities.RelatedFeature;
import org.n52.sos.ds.hibernate.entities.RelatedFeatureRole;
import org.n52.sos.ds.hibernate.entities.Unit;
import org.n52.sos.ds.hibernate.entities.observation.series.Series;

public class EntityBuilder {

    public static ServiceEntity createService(String name, String description, String url, String version) {
        ServiceEntity service = new ServiceEntity();
        service.setName(name);
        service.setDescription(description);
        service.setVersion(version);
        service.setType("SOS");
        service.setUrl(url);
        return service;
    }

    public static ProcedureEntity createProcedure(Procedure sosProc, ServiceEntity service) {
        ProcedureEntity procedure = new ProcedureEntity();
        procedure.setService(service);
        setIdentifierNameDesxription(sosProc, procedure);
        procedure.setInsitu(sosProc.isInsitu());
        procedure.setMobile(sosProc.isMobile());
        if (sosProc.hasParents()) {
            Set<ProcedureEntity> parents = new HashSet<>();
            for (Procedure parent : sosProc.getParents()) {
                parents.add(createProcedure(parent, service));
            }
            procedure.setParents(parents);
        }
        if (sosProc.hasChilds()) {
            Set<ProcedureEntity> childs = new HashSet<>();
            for (Procedure child : sosProc.getChilds()) {
                childs.add(createProcedure(child, service));
            }
            procedure.setChilds(childs);
        }
        return procedure;
    }

    public static OfferingEntity createOffering(Offering sosOffering, ServiceEntity service) {
        OfferingEntity offering = new OfferingEntity();
        offering.setService(service);
        setIdentifierNameDesxription(sosOffering, offering);
        if (sosOffering.hasParents()) {
            Set<OfferingEntity> parents = new HashSet<>();
            for (Offering parent : sosOffering.getParents()) {
                parents.add(createOffering(parent, service));
            }
            offering.setParents(parents);
        }
        if (sosOffering.hasChilds()) {
            Set<OfferingEntity> childs = new HashSet<>();
            for (Offering child : sosOffering.getChilds()) {
                childs.add(createOffering(child, service));
            }
            offering.setChilds(childs);
        }
        if (sosOffering.hasObservationTypes()) {
            offering.setObservationTypes(sosOffering.getObservationTypes().stream().map(ot -> ot.getObservationType())
                    .collect(Collectors.toSet()));
        }
        return offering;
    }

    public static CategoryEntity createCategory(ObservableProperty sosObsProp, ServiceEntity service) {
        CategoryEntity category = new CategoryEntity();
        category.setService(service);
        setIdentifierNameDesxription(sosObsProp, category);
        return category;
    }

    public static PhenomenonEntity createPhenomenon(ObservableProperty sosObsProp, ServiceEntity service) {
        PhenomenonEntity phenomenon = new PhenomenonEntity();
        phenomenon.setService(service);
        setIdentifierNameDesxription(sosObsProp, phenomenon);
        if (sosObsProp.hasParents()) {
            Set<PhenomenonEntity> parents = new HashSet<>();
            for (ObservableProperty parent : sosObsProp.getParents()) {
                parents.add(createPhenomenon(parent, service));
            }
            phenomenon.setParents(parents);
        }
        if (sosObsProp.hasChilds()) {
            Set<PhenomenonEntity> childs = new HashSet<>();
            for (ObservableProperty child : sosObsProp.getChilds()) {
                childs.add(createPhenomenon(child, service));
            }
            phenomenon.setChilds(childs);
        }
        return phenomenon;
    }

    public static FeatureEntity createFeature(FeatureOfInterest sosFeature, ServiceEntity service) {
        FeatureEntity feature = new FeatureEntity();
        feature.setService(service);
        setIdentifierNameDesxription(sosFeature, feature);
        feature.setGeometryEntity(new GeometryEntity().setGeometry(sosFeature.getGeom()));
        if (sosFeature.hasParents()) {
            Set<FeatureEntity> parents = new HashSet<>();
            for (FeatureOfInterest parent : sosFeature.getParents()) {
                parents.add(createFeature(parent, service));
            }
            feature.setParents(parents);
        }
        if (sosFeature.hasChilds()) {
            Set<FeatureEntity> childs = new HashSet<>();
            for (FeatureOfInterest child : sosFeature.getChilds()) {
                childs.add(createFeature(child, service));
            }
            feature.setChilds(childs);
        }
        return feature;
    }

    public static UnitEntity createUnit(Unit unit, ServiceEntity service) {
        UnitEntity entity = new UnitEntity();
        entity.setName(unit.getUnit());
        entity.setService(service);
        return entity;
    }

    public static DatasetEntity createDataset(Series series, ServiceEntity service) {
        DatasetEntity dataset = createDataset(series.getSeriesType());
        if (dataset != null) {
            setIdentifierNameDesxription(series, dataset);
            dataset.setPublished(series.isPublished());
            dataset.setDeleted(series.isDeleted());
            dataset.setFirstValueAt(series.getFirstTimeStamp());
            dataset.setLastValueAt(series.getLastTimeStamp());
            dataset.setProcedure(createProcedure(series.getProcedure(), service));
            dataset.setCategory(createCategory(series.getObservableProperty(), service));
            dataset.setFeature(createFeature(series.getFeatureOfInterest(), service));
            dataset.setPhenomenon(createPhenomenon(series.getObservableProperty(), service));
            dataset.setOffering(createOffering(series.getOffering(), service));
            if (series.isSetUnit()) {
                dataset.setUnit(createUnit(series.getUnit(), service));
            }
        }
        return dataset;
    }

    private static void setIdentifierNameDesxription(AbstractIdentifierNameDescriptionEntity sosEntity,
            DescribableEntity entity) {
        entity.setDomainId(sosEntity.getIdentifier());
        entity.setName(sosEntity.getName());
        entity.setDescription(sosEntity.getDescription());
    }

    private static DatasetEntity createDataset(String seriesType) {
        switch (seriesType.toLowerCase(Locale.ROOT)) {
        case "measurement":
            return new MeasurementDatasetEntity();
        case "text":
            return new TextDatasetEntity();
        case "count":
            return new CountDatasetEntity();
        default:
            break;
        }
        return null;
    }

    public static RelatedFeatureEntity createRelatedFeature(RelatedFeature sosRelatedFeature, ServiceEntity service) {
        RelatedFeatureEntity relatedFeature = new RelatedFeatureEntity();
        relatedFeature.setService(service);
        relatedFeature.setFeature(createFeature(sosRelatedFeature.getFeatureOfInterest(), service));
        Set<OfferingEntity> offerings = new HashSet<>();
        for (Offering offering : sosRelatedFeature.getOfferings()) {
            offerings.add(createOffering(offering, service));
        }
        relatedFeature.setOfferings(offerings);

        Set<RelatedFeatureRoleEntity> relatedFeatureRoles = new HashSet<>();
        for (RelatedFeatureRole relatedFeatureRole : sosRelatedFeature.getRelatedFeatureRoles()) {
            relatedFeatureRoles.add(createRelatedFeatureRole(relatedFeatureRole));
        }
        relatedFeature.setRelatedFeatureRoles(relatedFeatureRoles);
        return relatedFeature;
    }

    private static RelatedFeatureRoleEntity createRelatedFeatureRole(RelatedFeatureRole sosRelatedFeatureRole) {
        RelatedFeatureRoleEntity relatedFeatureRole = new RelatedFeatureRoleEntity();
        relatedFeatureRole.setRelatedFeatureRole(sosRelatedFeatureRole.getRelatedFeatureRole());
        return relatedFeatureRole;
    }
}
