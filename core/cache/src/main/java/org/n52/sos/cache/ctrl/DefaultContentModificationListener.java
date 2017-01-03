/*
 * Copyright (C) 2012-2017 52°North Initiative for Geospatial Open Source
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
package org.n52.sos.cache.ctrl;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.n52.iceland.cache.ContentCacheController;
import org.n52.iceland.cache.ContentCacheUpdate;
import org.n52.iceland.event.ServiceEvent;
import org.n52.iceland.event.ServiceEventListener;
import org.n52.shetland.ogc.ows.exception.OwsExceptionReport;
import org.n52.sos.cache.ctrl.action.ObservationInsertionUpdate;
import org.n52.sos.cache.ctrl.action.ResultInsertionUpdate;
import org.n52.sos.cache.ctrl.action.ResultTemplateInsertionUpdate;
import org.n52.sos.cache.ctrl.action.SensorDeletionUpdate;
import org.n52.sos.cache.ctrl.action.SensorInsertionUpdate;
import org.n52.sos.ds.CacheFeederHandler;
import org.n52.sos.event.events.ObservationInsertion;
import org.n52.sos.event.events.ResultInsertion;
import org.n52.sos.event.events.ResultTemplateInsertion;
import org.n52.sos.event.events.SensorDeletion;
import org.n52.sos.event.events.SensorInsertion;

import com.google.common.collect.Sets;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 * @since 4.0.0
 */
public class DefaultContentModificationListener implements ServiceEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultContentModificationListener.class);

    @SuppressWarnings("unchecked")
    private static final Set<Class<? extends ServiceEvent>> TYPES = Sets
            .<Class<? extends ServiceEvent>> newHashSet(
                    SensorInsertion.class,
                    ObservationInsertion.class,
                    ResultTemplateInsertion.class,
                    SensorDeletion.class,
                    ResultInsertion.class);

    private CacheFeederHandler handler;
    private ContentCacheController controller;

    @Override
    public Set<Class<? extends ServiceEvent>> getTypes() {
        return Collections.unmodifiableSet(TYPES);
    }

    @Override
    public void handle(ServiceEvent event) {
        if (event instanceof SensorInsertion) {
            SensorInsertion e = (SensorInsertion) event;
            handle(new SensorInsertionUpdate(e.getRequest(), e.getResponse()));
        } else if (event instanceof ObservationInsertion) {
            ObservationInsertion e = (ObservationInsertion) event;
            handle(new ObservationInsertionUpdate(e.getRequest()));
        } else if (event instanceof ResultTemplateInsertion) {
            ResultTemplateInsertion e = (ResultTemplateInsertion) event;
            handle(new ResultTemplateInsertionUpdate(e.getRequest(), e.getResponse()));
        } else if (event instanceof SensorDeletion) {
            SensorDeletion e = (SensorDeletion) event;
            handle(new SensorDeletionUpdate(this.handler, e.getRequest()));
        } else if (event instanceof ResultInsertion) {
            ResultInsertion e = (ResultInsertion) event;
            handle(new ResultInsertionUpdate(e.getRequest().getTemplateIdentifier(), e.getResponse().getObservation()));
        } else {
            LOGGER.debug("Can not handle modification event: {}", event);
        }
    }

    protected void handle(ContentCacheUpdate update) {
        LOGGER.debug("Updating Cache after content modification: {}", update);
        try {
            this.controller.update(update);
        } catch (OwsExceptionReport ex) {
            LOGGER.error("Error processing Event", ex);
        }
    }

    @Inject
    public void setHandler(CacheFeederHandler handler) {
        this.handler = handler;
    }

    @Inject
    public void setController(ContentCacheController controller) {
        this.controller = controller;
    }
}
