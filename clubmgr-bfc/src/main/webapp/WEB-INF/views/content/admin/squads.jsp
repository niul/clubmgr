<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<!-- Main bar -->
	<div class="9u 12u(small)">				
		<!-- Panel -->
		<div class="panel">
			<div class="panel-head">
				<h4><fmt:message key="admin.menu.squad"/> - ${team.name} - ${season.name}</h4>
			</div>
			<div class="box-content">
			<h3><fmt:message key="squad.active.title"/></h3>
			<table class="default">
				<thead>
					<tr>
						<th><fmt:message key="squad.active.headers.name"/></th>
						<th><fmt:message key="squad.active.headers.email"/></th>
					</tr>
				</thead>
			<c:forEach var="player" items="${players_active}">
				<tr>
					<td>${player.firstName} ${player.lastName}</td>
					<td>${player.email}</td>
				</tr>
			</c:forEach>
			</table>
			<c:if test="${fn:length(players_active) eq 0}">
			<div class="msg form_error">
				<fmt:message key="squad.active.error.no_players">
					<fmt:param value="${season.name}"/>
				</fmt:message>
			</div>
			</c:if>
			
			<h3><fmt:message key="squad.inactive.title"/></h3>
			<table class="default">
				<thead>
					<tr>
						<th><fmt:message key="squad.inactive.headers.name"/></th>
						<th><fmt:message key="squad.inactive.headers.email"/></th>
						<th><fmt:message key="squad.inactive.headers.actions"/></th>
					</tr>
				</thead>
			<c:forEach var="player" items="${players_inactive}">
				<tr>
					<td>${player.firstName} ${player.lastName}</td>
					<td>${player.email}</td>
					<td>
						<a href='<c:url value="/admin/addSquad.html?uuid=${player.uuid}&teamUuid=${team.uuid}&seasonKey=${season.seasonKey}"/>' class="button small icon fa-edit"><span><fmt:message key="squad.inactive.headers.actions.add"/></span></a>
					</td>
				</tr>
			</c:forEach>
			</table>
			</div>
		</div>
	</div>
