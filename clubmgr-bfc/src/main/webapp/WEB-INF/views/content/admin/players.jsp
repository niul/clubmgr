<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<!-- Main bar -->
	<div class="9u 12u(small)">				
		<!-- Panel -->
		<div class="panel">
			<div class="panel-head">
				<h4><f:message key="admin.menu.player"/> - ${team.name}</h4>
			</div>
			<div class="box-content">
			<a href='<c:url value="/admin/addPlayer.html?uuid=${team.uuid}"/>' class="button small smallaction icon fa-plus"><span><f:message key="players.headers.actions.add"/></span></a>
			<table class="default">
				<thead>
					<tr>
						<th><f:message key="players.headers.name"/></th>
						<th><f:message key="players.headers.email"/></th>
						<th><f:message key="players.headers.teams"/></th>
						<th><f:message key="players.headers.actions"/></th>
					</tr>
				</thead>
			<c:forEach var="player" items="${players}">
				<tr>
					<td>${player.firstName} ${player.lastName}</td>
					<td>${player.email}</td>
					<td>
						<c:forEach var="team" items="${player.teams}">
							${team.name}
						</c:forEach>
					</td>
					<td>
						<a href='<c:url value="/admin/editPlayer.html?uuid=${player.uuid}&teamUuid=${team.uuid}"/>' class="button small icon fa-edit"><span><f:message key="players.headers.actions.edit"/></span></a>
						<a href='<c:url value="/admin/deletePlayer.html?uuid=${player.uuid}&teamUuid=${team.uuid}"/>' class="button small icon fa-trash"><span><f:message key="players.headers.actions.delete"/></span></a>
					</td>
				</tr>
			</c:forEach>
			</table>
			</div>
		</div>
	</div>
