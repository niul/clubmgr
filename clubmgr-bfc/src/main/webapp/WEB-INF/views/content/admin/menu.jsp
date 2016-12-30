<%@ include file="/WEB-INF/views/taglibs.jsp"%>

<div class="12u">
	<header>
		<h1><fmt:message key="admin.title"/></h1>
	</header>
</div>

<!-- Sidebar -->
	<div class="3u 12u$(small)">				
		<!-- Panel -->
		<div class="panel">
			<!-- Panel Head -->
			<div class="panel-head">
				<h4><fmt:message key="admin.menu.header"/></h4>
			</div>
			<!-- End Box Head-->
					
			<div class="box-content">
				<!-- Filter -->
				<div class="filter">
				<c:if test="${fn:length(teams) gt 1}">
				<form:form action="${requestScope['javax.servlet.forward.request_uri']}" commandName="team" method="POST">
					<label><fmt:message key="admin.menu.filter.team"/>:</label>
					<form:select class="field" path="uuid" onchange="this.form.submit()">
					<c:if test="${(page=='admin_players')}">
						<fmt:message key="admin.menu.filter.all" var="allTeams"/> 
						<form:option value="${all_uuid}" label="${allTeams}"/>
					</c:if>
						<form:options items="${teams}" itemValue="uuid" itemLabel="name"/>
					</form:select>
					<input type="hidden" name="seasonKey" value="${season.seasonKey}"/>
				</form:form>
				</c:if>
					
				<c:if test="${(page=='admin_squads') || (page=='admin_reports')}">
				<form:form action="${requestScope['javax.servlet.forward.request_uri']}" commandName="season" method="POST">
					<label><fmt:message key="admin.menu.filter.season"/>:</label>
					<form:select class="field" path="seasonKey" onchange="this.form.submit()">
						<form:options items="${seasons}" itemValue="seasonKey" itemLabel="name"/>
					</form:select>
					<input type="hidden" name="uuid" value="${team.uuid}"/>
				</form:form>
				</c:if>
				</div>
				<!-- End Filter -->
				
				<a href='<c:url value="/admin/players.html"/>' class="button action small icon fa-user"><span><fmt:message key="admin.menu.player"/></span></a>
				<a href='<c:url value="/admin/squads.html"/>' class="button action small icon fa-users"><span><fmt:message key="admin.menu.squad"/></span></a>
				<a href='<c:url value="/admin/reports.html"/>' class="button action small icon fa-file-text-o"><span><fmt:message key="admin.menu.match"/></span></a>
			</div>
		</div>
		<!-- End Box -->
	</div>
	<!-- End Sidebar -->
