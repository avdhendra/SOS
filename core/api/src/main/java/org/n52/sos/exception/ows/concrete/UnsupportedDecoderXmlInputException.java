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
package org.n52.sos.exception.ows.concrete;

import org.apache.xmlbeans.XmlObject;
import org.w3c.dom.Node;

import org.n52.svalbard.decode.Decoder;
import org.n52.svalbard.decode.exception.UnsupportedDecoderInputException;

public class UnsupportedDecoderXmlInputException extends UnsupportedDecoderInputException {
    private static final long serialVersionUID = -7244575661954080973L;

    public UnsupportedDecoderXmlInputException(Decoder<?, ?> decoder, XmlObject o) {
        super(decoder, o == null ? null : getName(o));
    }

    private static String getName(XmlObject o) {
        return getName(o.getDomNode());
    }

    private static String getName(Node n) {
        if (n.getPrefix() == null || n.getPrefix().isEmpty()) {
            return n.getLocalName();
        } else {
            return n.getPrefix() + ":" + n.getLocalName();
        }
    }
}
