/*
 * Copyright (C) 2012-2022 52°North Spatial Information Research GmbH
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
package org.n52.sos.aquarius.requests;

import java.util.Map;

import org.n52.sos.aquarius.AquariusConstants;
import org.n52.sos.aquarius.pojo.ExtendedFilters;

public class GetTimeSeriesUniqueIdList extends AbstractAquariusGetRequest {

    private ExtendedFilters extendedFilter;

    public GetTimeSeriesUniqueIdList() {

    }

    public GetTimeSeriesUniqueIdList(Map<String, String> filter) {
        if (filter != null && !filter.isEmpty()) {
            setExtendedFilter(new ExtendedFilters().addFilter(filter));
        }
    }

    @Override
    public Map<String, String> getQueryParameters() {
        Map<String, String> parameters = super.getQueryParameters();
        if (hasExtendedFilters()) {
            parameters.put(AquariusConstants.Parameters.EXTENDED_FILTERS, getExtendedFilter().encodeFilters());
        }
        return parameters;
    }

    @Override
    public String getPath() {
        return AquariusConstants.Paths.GET_TIME_SERIES_UNIQUE_ID_LIST;
    }

    /**
     * @return the extendedFilter
     */
    public ExtendedFilters getExtendedFilter() {
        return extendedFilter;
    }

    /**
     * @param extendedFilter
     *            the extendedFilter to set
     */
    public GetTimeSeriesUniqueIdList setExtendedFilter(ExtendedFilters extendedFilter) {
        this.extendedFilter = extendedFilter;
        return this;
    }

    public boolean hasExtendedFilters() {
        return getExtendedFilter() != null && getExtendedFilter().hasFilters();
    }

}
