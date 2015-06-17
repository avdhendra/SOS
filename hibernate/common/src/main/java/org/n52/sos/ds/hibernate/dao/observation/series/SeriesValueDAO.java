/**
 * Copyright (C) 2012-2015 52°North Initiative for Geospatial Open Source
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
package org.n52.sos.ds.hibernate.dao.observation.series;

import org.hibernate.Criteria;
import org.n52.sos.ds.hibernate.dao.observation.AbstractValueDAO;
import org.n52.sos.ds.hibernate.entities.observation.series.AbstractSeriesValueDAO;
import org.n52.sos.ds.hibernate.entities.observation.series.AbstractValuedSeriesObservation;
import org.n52.sos.exception.CodedException;
import org.n52.sos.request.GetObservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link AbstractValueDAO} for series concept
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * @since 4.1.0
 *
 */
public class SeriesValueDAO extends AbstractSeriesValueDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeriesValueDAO.class);

    @Override
    protected void addSpecificRestrictions(Criteria c, GetObservationRequest request) throws CodedException {
        // nothing  to add
}

    @Override
    protected Class<?> getSeriesValueClass() {
        return AbstractValuedSeriesObservation.class;
    }

//    /**
//     * Query streaming value for parameter as {@link ScrollableResults}
//     *
//     * @param request
//     *            {@link GetObservationRequest}
//     * @param series
//     *            Datasource series id
//     * @param temporalFilterCriterion
//     *            Temporal filter {@link Criterion}
//     * @param session
//     *            Hibernate Session
//     * @return Resulting {@link ScrollableResults}
//     * @throws HibernateException
//     *             If an error occurs when querying the {@link AbstractValuedLegacyObservation}s
//     * @throws OwsExceptionReport
//     *             If an error occurs when querying the {@link AbstractValuedLegacyObservation}s
//     */
//    public ScrollableResults getStreamingSeriesValuesFor(GetObservationRequest request, long series,
//            Criterion temporalFilterCriterion, Session session) throws OwsExceptionReport {
//        return getSeriesValueCriteriaFor(request, series, temporalFilterCriterion, session).scroll(
//                ScrollMode.FORWARD_ONLY);
//    }
//
//    /**
//     * Query streaming value for parameter as {@link ScrollableResults}
//     *
//     * @param request
//     *            {@link GetObservationRequest}
//     * @param series
//     *            Datasource series id
//     * @param session
//     *            Hibernate Session
//     * @return Resulting {@link ScrollableResults}
//     * @throws OwsExceptionReport
//     *             If an error occurs when querying the {@link AbstractValuedLegacyObservation}s
//     */
//    public ScrollableResults getStreamingSeriesValuesFor(GetObservationRequest request, long series, Session session)
//            throws OwsExceptionReport {
//        return getSeriesValueCriteriaFor(request, series, null, session).scroll(ScrollMode.FORWARD_ONLY);
//    }
//
//    /**
//     * Query streaming value for parameter as chunk {@link List}
//     *
//     * @param request
//     *            {@link GetObservationRequest}
//     * @param series
//     *            Datasource series id
//     * @param temporalFilterCriterion
//     *            Temporal filter {@link Criterion}
//     * @param chunkSize
//     *            chunk size
//     * @param currentRow
//     *            Start row
//     * @param session
//     *            Hibernate Session
//     * @return Resulting chunk {@link List}
//     * @throws OwsExceptionReport
//     *             If an error occurs when querying the {@link AbstractValuedLegacyObservation}s
//     */
//    @SuppressWarnings("unchecked")
//    public List<ValuedSeriesObservation<?>> getStreamingSeriesValuesFor(GetObservationRequest request, long series,
//            Criterion temporalFilterCriterion, int chunkSize, int currentRow, Session session)
//            throws OwsExceptionReport {
//        Criteria c = getSeriesValueCriteriaFor(request, series, temporalFilterCriterion, session);
//        addChunkValuesToCriteria(c, chunkSize, currentRow);
//        LOGGER.debug("QUERY getStreamingSeriesValuesFor(): {}", HibernateHelper.getSqlString(c));
//        return (List<ValuedSeriesObservation<?>>) c.list();
//    }
//
//    /**
//     * Query streaming value for parameter as chunk {@link List}
//     *
//     * @param request
//     *            {@link GetObservationRequest}
//     * @param series
//     *            Datasource series id
//     * @param chunkSize
//     *            Chunk size
//     * @param currentRow
//     *            Start row
//     * @param session
//     *            Hibernate Session
//     * @return Resulting chunk {@link List}
//     * @throws OwsExceptionReport
//     *             If an error occurs when querying the {@link AbstractValuedLegacyObservation}s
//     */
//    @SuppressWarnings("unchecked")
//    public List<ValuedSeriesObservation<?>> getStreamingSeriesValuesFor(GetObservationRequest request, long series, int chunkSize,
//            int currentRow, Session session) throws OwsExceptionReport {
//        Criteria c = getSeriesValueCriteriaFor(request, series, null, session);
//        addChunkValuesToCriteria(c, chunkSize, currentRow);
//        LOGGER.debug("QUERY getStreamingSeriesValuesFor(): {}", HibernateHelper.getSqlString(c));
//        return (List<ValuedSeriesObservation<?>>) c.list();
//    }
//
//    /**
//     * Get {@link Criteria} for parameter
//     *
//     * @param request
//     *            {@link GetObservationRequest}
//     * @param series
//     *            Datasource series id
//     * @param temporalFilterCriterion
//     *            Temporal filter {@link Criterion}
//     * @param session
//     *            Hibernate Session
//     * @return Resulting {@link Criteria}
//     * @throws OwsExceptionReport
//     *             If an error occurs when adding Spatial Filtering Profile
//     *             restrictions
//     */
//    private Criteria getSeriesValueCriteriaFor(GetObservationRequest request, long series,
//            Criterion temporalFilterCriterion, Session session) throws OwsExceptionReport {
//        final Criteria c =
//                getDefaultObservationCriteria(AbstractValuedSeriesObservation.class, session).createAlias(AbstractValuedSeriesObservation.SERIES, "s");
//
//        checkAndAddSpatialFilteringProfileCriterion(c, request, session);
//
//        c.add(Restrictions.eq("s." + Series.ID, series));
//
//        if (CollectionHelper.isNotEmpty(request.getOfferings())) {
//            c.createCriteria(AbstractValuedSeriesObservation.OFFERINGS).add(Restrictions.in(Offering.IDENTIFIER, request.getOfferings()));
//        }
//
//        String logArgs = "request, series, offerings";
//        if (temporalFilterCriterion != null) {
//            logArgs += ", filterCriterion";
//            c.add(temporalFilterCriterion);
//        }
//        LOGGER.debug("QUERY getStreamingSeriesValuesFor({}): {}", logArgs, HibernateHelper.getSqlString(c));
//        return c.setReadOnly(true);
//    }
//
//    /**
//     * Get default {@link Criteria} for {@link Class}
//     *
//     * @param clazz
//     *            {@link Class} to get default {@link Criteria} for
//     * @param session
//     *            Hibernate Session
//     * @return Default {@link Criteria}
//     */
//    public Criteria getDefaultObservationCriteria(Class<?> clazz, Session session) {
//        return session.createCriteria(clazz).add(Restrictions.eq(AbstractValuedSeriesObservation.DELETED, false))
//                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//    }
//
//    /**
//     * Query unit for parameter
//     *
//     * @param request
//     *            {@link GetObservationRequest}
//     * @param series
//     *            Datasource series id
//     * @param session
//     *            Hibernate Session
//     * @return Unit or null if no unit is set
//     * @throws OwsExceptionReport
//     *             If an error occurs when querying the unit
//     */
//    public String getUnit(GetObservationRequest request, long series, Session session) throws OwsExceptionReport {
//        Criteria c = getSeriesValueCriteriaFor(request, series, null, session);
//        Unit unit = (Unit) c.setMaxResults(1).setProjection(Projections.property(AbstractValuedSeriesObservation.UNIT)).uniqueResult();
//        if (unit != null && unit.isSetUnit()) {
//            return unit.getUnit();
//        }
//        return null;
//    }

}
