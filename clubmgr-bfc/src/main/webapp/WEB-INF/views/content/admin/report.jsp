<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<!-- Main bar -->
	<div class="9u 12u(small)">				
		<!-- Panel -->
		<div class="panel">
			<div class="panel-head">
				<h4><fmt:message key="report.title"/></h4>
			</div>
			<div class="box-content">
				<h4>
					<fmt:formatDate value="${fixture.date}" pattern="MMM d, YYYY (E)" var="fixtureDate"/>
					<fmt:message key="report.description">
						<fmt:param value="${fixture.home}"/>
						<fmt:param value="${fixture.homeScore}"/>
						<fmt:param value="${fixture.awayScore}"/>
						<fmt:param value="${fixture.away}"/>
						<fmt:param value="${fixtureDate}"/>
					</fmt:message>
				</h4>
			<form:form action="${requestScope['javax.servlet.forward.request_uri']}" modelAttribute="playerFixtureInfoList" method="POST">
				<spring:hasBindErrors name="playerFixtureInfoList">
					<div class="msg form_error"><strong><fmt:message key="report.errors"/>:</strong>
					<c:forEach var="error" items="${errors.allErrors}">
						<c:choose>
 							<c:when test="${error.field == 'playerFixtureInfoList'}">
								<div><spring:message message="${error}" /></div>
							</c:when>
							<c:otherwise>
								<div>[${error.rejectedValue.player.firstName} ${error.rejectedValue.player.lastName}]: <spring:message message="${error}" /></div>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					</div>
    			</spring:hasBindErrors>
			<table class="default">
				<thead>
					<tr>
						<th><fmt:message key="report.header.player"/></th>
						<th><fmt:message key="report.header.starter"/></th>
						<th><fmt:message key="report.header.substitute"/></th>
						<th><fmt:message key="report.header.yellow_card"/></th>
						<th><fmt:message key="report.header.red_card"/></th>
						<th><fmt:message key="report.header.assists"/></th>
						<th><fmt:message key="report.header.goals"/></th>
						<th><fmt:message key="report.header.rating"/></th>
					</tr>
				</thead>
				<c:forEach items="${playerFixtureInfoList.playerFixtureInfoList}"  var="playerFixtureInfo" varStatus="vs">
				<tr>
					<form:input type="hidden" path="playerFixtureInfoList[${vs.index}].player.firstName" value="${playerFixtureInfo.player.firstName}"/>
					<form:input type="hidden" path="playerFixtureInfoList[${vs.index}].player.lastName" value="${playerFixtureInfo.player.lastName}"/>
					<form:input type="hidden" path="playerFixtureInfoList[${vs.index}].player.uuid" value="${playerFixtureInfo.player.uuid}"/>
					<form:input type="hidden" path="playerFixtureInfoList[${vs.index}].fixture.uuid" value="${playerFixtureInfo.fixture.uuid}"/>
					<td class="center">${playerFixtureInfo.player.firstName} ${playerFixtureInfo.player.lastName}</td>
					<td class="center"><form:checkbox path="playerFixtureInfoList[${vs.index}].started" value="${playerFixtureInfo.started}"/></td>
					<td class="center"><form:checkbox path="playerFixtureInfoList[${vs.index}].substitute" value="${playerFixtureInfo.substitute}"/></td>
					<td class="center"><form:checkbox path="playerFixtureInfoList[${vs.index}].yellowCard" value="${playerFixtureInfo.yellowCard}"/></td>
					<td class="center"><form:checkbox path="playerFixtureInfoList[${vs.index}].redCard" value="${playerFixtureInfo.redCard}"/></td>
					<td class="center"><form:input class="small" autocomplete="off" maxlength="1" path="playerFixtureInfoList[${vs.index}].assists" value="${playerFixtureInfo.assists}"/></td>
					<td class="center"><form:input class="small" autocomplete="off" maxlength="1" path="playerFixtureInfoList[${vs.index}].goals" value="${playerFixtureInfo.goals}"/></td>
					<td class="center"><form:input class="small" autocomplete="off" maxlength="2" path="playerFixtureInfoList[${vs.index}].rating" value="${playerFixtureInfo.rating}"/></td>
				</tr>
				</c:forEach>
			</table>
			<input type="hidden" name="uuid" value="${fixture.uuid}"/>
			<input type="hidden" name="teamUuid" value="${team.uuid}"/>
			<input type="hidden" name="seasonKey" value="${season.seasonKey}"/>
			
			<div class="row 50%">
				<div class="12u">
					<ul class="actions">
						<li>
							<button type="submit" class="button icon fa-plus"><fmt:message key="form.submit"/></button>
						</li>
						<li>
							<button type="reset" class="button icon fa-undo"><fmt:message key="form.reset"/></button>
						</li>
					</ul>
				</div>
			</div>
			</form:form>
			</div>
		</div>
	</div>
