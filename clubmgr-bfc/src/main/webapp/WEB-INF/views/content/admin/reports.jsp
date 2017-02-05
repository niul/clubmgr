<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<!-- Main bar -->
	<div class="9u 12u(small)">				
		<!-- Panel -->
		<div class="panel">
			<div class="panel-head">
				<h4><fmt:message key="admin.menu.match"/> - ${team.name} - ${season.name}</h4>
			</div>
			<div class="box-content">
			<h4><fmt:message key="reports.title"/></h4>
			<table class="default">
				<thead>
					<tr>
						<th><fmt:message key="reports.headers.date"/></th>
						<th class="home"><fmt:message key="reports.headers.home"/></th>
						<th><fmt:message key="reports.headers.result"/></th>
						<th><fmt:message key="reports.headers.away"/></th>
						<th><fmt:message key="reports.headers.actions"/></th>
					</tr>
				</thead>
			<c:forEach var="fixture" items="${fixtures}">
				<tr>
					<td class="not-small"><fmt:formatDate value="${fixture.date}" pattern="MMM d, YYYY (E)" /></td>
					<td class="only-small"><fmt:formatDate value="${fixture.date}" pattern="MMM d, YYYY" /></td>
					<td align="right">${fixture.home}</td>
	  	   			<td align="center">${fixture.homeScore}-${fixture.awayScore}</td>
	  	    		<td>${fixture.away}</td>
	  	    		<td>
	  	    			<c:if test="${not empty fixture.homeScore}">
	  	    			<a href='<c:url value="/admin/editReport.html?uuid=${fixture.uuid}&teamUuid=${team.uuid}&seasonKey=${season.seasonKey}"/>' class="button small icon fa-edit"><span><fmt:message key="reports.headers.actions.edit"/></span></a>
	  	    			</c:if>
	  	    		</td>
				</tr>
			</c:forEach>
			</table>
			</div>
		</div>
	</div>