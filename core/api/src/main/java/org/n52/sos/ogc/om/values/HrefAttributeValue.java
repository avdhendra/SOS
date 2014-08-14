/**
 * Copyright (C) 2012-2014 52°North Initiative for Geospatial Open Source
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
package org.n52.sos.ogc.om.values;

import org.n52.sos.ogc.om.values.visitor.ThrowingValueVisitor;
import org.n52.sos.ogc.om.values.visitor.ThrowingVoidValueVisitor;
import org.n52.sos.ogc.om.values.visitor.ValueVisitor;
import org.n52.sos.ogc.om.values.visitor.VoidValueVisitor;
import org.n52.sos.util.StringHelper;
import org.n52.sos.w3c.xlink.W3CHrefAttribute;

public class HrefAttributeValue implements Value<W3CHrefAttribute> {

    private static final long serialVersionUID = 1762674768718660098L;

    private W3CHrefAttribute value;

    /**
     * Unit of measure
     */
    private String unit;

    public HrefAttributeValue() {
    }

    public HrefAttributeValue(W3CHrefAttribute value) {
        setValue(value);
    }

    @Override
    public void setValue(W3CHrefAttribute value) {
       this.value = value;
    }

    @Override
    public W3CHrefAttribute getValue() {
        return value;
    }

    @Override
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String getUnit() {
        return unit;
    }

    @Override
    public boolean isSetValue() {
        return getValue() != null && getValue().isSetHref();
    }


    @Override
    public boolean isSetUnit() {
        return StringHelper.isNotEmpty(getUnit());
    }

    @Override
    public String toString() {
        return String.format("HrefAttributeValue [value=%s, unit=%s]", getValue(), getUnit());
    }

   @Override
    public <X> X accept(ValueVisitor<X> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void accept(VoidValueVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <X, T extends Exception> X accept(ThrowingValueVisitor<X, T> visitor) throws T {
        return visitor.visit(this);
    }

    @Override
    public <T extends Exception> void accept(ThrowingVoidValueVisitor<T> visitor) throws T {
        visitor.visit(this);
    }
}
