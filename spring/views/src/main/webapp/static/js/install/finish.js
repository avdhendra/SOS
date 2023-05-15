/*
 * Copyright (C) 2012-2023 52°North Spatial Information Research GmbH
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
$(function () {
    show_config_validation_success();
    setup_password_form();
});


function show_config_validation_success() {
    if (document.referrer) {
        if (document.referrer.matches(/install\/settings/)) {
            showSuccess("Settings successfully tested.");
        }
    }
    warnIfNotHttps();
};

function setup_password_form() {
    $(function () {
        var $pwFacade = $("input#password"),
            $pwHidden = $("input[name=admin_password]"),
            $submit = $('button[type=submit]'),
            $inputs = $("input[type=text]");

        $inputs.bind("keyup input", function () {
            var empty = false;
            $inputs.each(function () {
                if ($(this).val() === "") {
                    empty = true;
                }
            });
            $submit.attr("disabled", empty);
        }).trigger("input");
        $pwFacade.bind('focus', function () {
            $pwFacade.val($pwHidden.val());
        }).bind('blur', function () {
            $pwFacade.val($pwFacade.val().replace(/./g, String.fromCharCode(8226)));
        }).bind("keyup input", function () {
            $pwHidden.val($pwFacade.val());
        }).trigger("blur");
        $submit.click(function () {
            $submit.attr("disabled", true);
            $submit.parents("form").submit();
        });
    });
};