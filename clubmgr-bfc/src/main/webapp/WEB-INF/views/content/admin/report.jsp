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
			<form:form action="${requestScope['javax.servlet.forward.request_uri']}" modelAttribute="playerFixtureStatisticList" method="POST">
				<spring:hasBindErrors name="playerFixtureStatisticList">
					<div class="msg form_error"><strong><fmt:message key="report.errors"/>:</strong>
					<c:forEach var="error" items="${errors.allErrors}">
						<c:choose>
 							<c:when test="${error.field == 'playerFixtureStatisticList'}">
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
				<c:forEach items="${playerFixtureStatisticList.playerFixtureStatisticList}"  var="playerFixtureStatistic" varStatus="vs">
				<tr>
					<form:input type="hidden" path="playerFixtureStatisticList[${vs.index}].player.firstName" value="${playerFixtureStatistic.player.firstName}"/>
					<form:input type="hidden" path="playerFixtureStatisticList[${vs.index}].player.lastName" value="${playerFixtureStatistic.player.lastName}"/>
					<form:input type="hidden" path="playerFixtureStatisticList[${vs.index}].player.uuid" value="${playerFixtureStatistic.player.uuid}"/>
					<form:input type="hidden" path="playerFixtureStatisticList[${vs.index}].fixture.uuid" value="${playerFixtureStatistic.fixture.uuid}"/>
					<td class="center">${playerFixtureStatistic.player.firstName} ${playerFixtureStatistic.player.lastName}</td>
					<td class="center"><form:checkbox path="playerFixtureStatisticList[${vs.index}].started" value="${playerFixtureStatistic.started}"/></td>
					<td class="center"><form:checkbox path="playerFixtureStatisticList[${vs.index}].substitute" value="${playerFixtureStatistic.substitute}"/></td>
					<td class="center"><form:checkbox path="playerFixtureStatisticList[${vs.index}].yellowCard" value="${playerFixtureStatistic.yellowCard}"/></td>
					<td class="center"><form:checkbox path="playerFixtureStatisticList[${vs.index}].redCard" value="${playerFixtureStatistic.redCard}"/></td>
					<td class="center"><form:input class="small" autocomplete="off" maxlength="1" path="playerFixtureStatisticList[${vs.index}].assists" value="${playerFixtureStatistic.assists}"/></td>
					<td class="center"><form:input class="small" autocomplete="off" maxlength="1" path="playerFixtureStatisticList[${vs.index}].goals" value="${playerFixtureStatistic.goals}"/></td>
					<td class="center"><form:input class="small" autocomplete="off" maxlength="2" path="playerFixtureStatisticList[${vs.index}].rating" value="${playerFixtureStatistic.rating}"/></td>
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
