<%--

    Copyright (C) 2012-2023 52°North Spatial Information Research GmbH

    This program is free software; you can redistribute it and/or modify it
    under the terms of the GNU General Public License version 2 as published
    by the Free Software Foundation.

    If the program is linked with libraries which are licensed under one of
    the following licenses, the combination of the program with the linked
    library is not considered a "derivative work" of the program:

        - Apache License, version 2.0
        - Apache Software License, version 1.0
        - GNU Lesser General Public License, version 3
        - Mozilla Public License, versions 1.0, 1.1 and 2.0
        - Common Development and Distribution License (CDDL), version 1.0

    Therefore the distribution of the program linked with libraries licensed
    under the aforementioned licenses, is permitted by the copyright holders
    if the distribution is compliant with both the GNU General Public
    License version 2 and the aforementioned licenses.

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
    Public License for more details.

--%>
<jsp:include page="header.jsp">
	<jsp:param name="step" value="4" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../common/logotitle.jsp">
	<jsp:param name="title" value="Finishing Installation" />
	<jsp:param name="leadParagraph" value="All configuration options are set. Click on 'Install' to finish the installation." />
</jsp:include>

<script type="text/javascript" src="<c:url value="/static/js/install/finish.js" />"></script>

<p>Please enter credentials to login into the administrator panel below. You can reset your admin password by executing the file <code>sql/reset_admin.sql</code> (located inside the SOS installation directory in the webapps folder of your application server) on your database.</p>

<hr />

<form action="<c:url value="/install/finish" />" method="POST" class="">

	<div class="control-group">
        <div class="form-row">
            <div class="col-md-2 textright">
                <label class="col-form-label" for="sos_website">Username</label>
            </div>
            <div class="col">
                <input class="col-lg-8 form-control required" type="text" name="admin_username" autocomplete="off" placeholder="admin" value="${admin_username}"/>
                <span class="text-muted"><span class="badge badge-warning">required</span> The username to login into the admin backend.</span>
            </div>
        </div>
	</div>

    <div class="control-group">
        <div class="form-row">
            <div class="col-md-2 textright">
                <label class="col-form-label" for="password">Password</label>
            </div>
            <div class="col">
                <input type="hidden" name="admin_password" value="${admin_password}"/>
                <input id="password" class="col-lg-8 form-control required" type="password" autocomplete="off" placeholder="password" value="${admin_password}"/>
                <span class="text-muted"><span class="badge badge-warning">required</span> The password to login into the admin backend.</span>
            </div>
        </div>
	</div>
	<hr/>
	
	 <p>Clicking 'Install' will persist all settings in the database and (depending on your configuration) create or delete tables and insert test data.</p>
	 
	<div>
		<a href="<c:url value="/install/settings" />" class="btn">Back</a>
		<button class="btn btn-info pull-right" type="submit">Install</button>
	</div>
	<br/>
</form>
<jsp:include page="../common/footer.jsp" />
