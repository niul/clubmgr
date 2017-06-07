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
			<c:if test="${sendEmail}">
	    	<div class="form_error">
	      		<fmt:message key="report.email.sent"/>
	    	</div>
	    	</c:if>
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
			<c:forEach var="fixture" items="${fixtures}" varStatus="vs">
				<tr class="${vs.index % 2 == 1 ? 'odd' : 'even'}">
					<td class="not-small"><fmt:formatDate value="${fixture.date}" pattern="MMM d, YYYY (E)" /></td>
					<td class="only-small"><fmt:formatDate value="${fixture.date}" pattern="MMM d, YYYY" /></td>
					<td align="right">${fixture.home}</td>
	  	   			<td align="center">${fixture.homeScore}-${fixture.awayScore}</td>
	  	    		<td>${fixture.away}</td>
	  	    		<td>
	  	    			<c:if test="${not empty fixture.homeScore}">
	  	    			<a href='<c:url value="/admin/editReport.html?uuid=${fixture.uuid}&teamUuid=${team.uuid}&seasonKey=${season.seasonKey}"/>' class="button small icon fa-edit"><span><fmt:message key="reports.headers.actions.edit"/></span></a>
	  	    			</c:if>
	  	    			
	  	    			<c:set var="today" value="<%=new java.util.Date()%>"/>
	  	    			<c:if test="${(empty fixture.homeScore) && (today lt fixture.date)}">
	  	    			<a href='<c:url value="/admin/sendFixtureEmail.html?uuid=${fixture.uuid}&teamUuid=${team.uuid}&seasonKey=${season.seasonKey}"/>' class="button small icon fa-envelope-o"><span><fmt:message key="reports.headers.actions.email"/></span></a>
	  	    			</c:if>
	  	    			
	  	    			<c:set var="nextWeek" value="<%=new java.util.Date(new java.util.Date().getTime() + 7*60*60*24*1000)%>"/>
	  	    			<c:if test="${(empty fixture.homeScore) && (nextWeek gt fixture.date)}">
	  	    			<a href='<c:url value="/fixture.html?uuid=${fixture.uuid}"/>' class="button small icon fa-envelope-o"><span><fmt:message key="reports.headers.actions.view"/></span></a>
	  	    			</c:if>
	  	    		</td>
				</tr>
			</c:forEach>
			</table>
			</div>
		</div>
	</div>
