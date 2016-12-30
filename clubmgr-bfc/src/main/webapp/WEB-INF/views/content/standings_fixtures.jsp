<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<article>
      <header>
	    <h1>${teamseasonmap.team.name} / ${teamseasonmap.season.name}</h1>
        <h3><fmt:message key="standings.title"/> / <a href="${teamseasonmap.standingsUri}" target="_blank">${teamseasonmap.description}</a></h3>
      </header>
	  <table class="default standings">
	    <thead>
	      <tr>
	    	<th class="not-small"><span title="<fmt:message key='standings.headers.position.name'/>"><fmt:message key="standings.headers.position"/></span></th>
	    	<th><span title="<fmt:message key='standings.headers.team.name'/>"><fmt:message key="standings.headers.team"/></span></th>
	    	<th><span title="<fmt:message key='standings.headers.games_played.name'/>"><fmt:message key="standings.headers.games_played"/></span></th>
	    	<th><span title="<fmt:message key='standings.headers.wins.name'/>"><fmt:message key="standings.headers.wins"/></span></th>
	    	<th><span title="<fmt:message key='standings.headers.losses.name'/>"><fmt:message key="standings.headers.ties"/></span></th>
	    	<th><span title="<fmt:message key='standings.headers.ties.name'/>"><fmt:message key="standings.headers.losses"/></span></th>
	    	<th class="not-small"><span title="<fmt:message key='standings.headers.goals_for.name'/>"><fmt:message key="standings.headers.goals_for"/></span></th>
	    	<th class="not-small"><span title="<fmt:message key='standings.headers.goals_against.name'/>"><fmt:message key="standings.headers.goals_against"/></span></th>
	    	<th><span title="<fmt:message key='standings.headers.points.name'/>"><fmt:message key="standings.headers.points"/></span></th>
	      </tr>
	    </thead>
	    <tbody>
	    <c:forEach var="standing" items="${standings}">
	      <tr>
	  	    <td class="not-small">${standing.position}</td>
	  	    <td>${standing.teamName}</td>
	  	    <td>${standing.gamesPlayed}</td>
	  	    <td>${standing.wins}</td>
	  	    <td>${standing.ties}</td>
	  	    <td>${standing.losses}</td>
	  	    <td class="not-small">${standing.goalsFor}</td>
	  	    <td class="not-small">${standing.goalsAgainst}</td>
	  	    <td>${standing.points}</td>
	  	  </tr>
	    </c:forEach>
	    </tbody>
	  </table>
	</article>
	
		<article>
      <header>
        <h3><fmt:message key="fixtures.title"/> / <a href="${teamseasonmap.fixturesUri}" target="_blank">${teamseasonmap.description}</a></h3>
      </header>
	  <table class="default fixtures u-full-width">
	    <thead>
	      <tr>
	    	<th><fmt:message key="fixtures.headers.date"/></th>
	    	<th class="home"><fmt:message key="fixtures.headers.home"/></th>
	    	<th><fmt:message key="fixtures.headers.result"/></th>
	    	<th><fmt:message key="fixtures.headers.away"/></th>
	    	<th class="not-small"><fmt:message key="fixtures.headers.field"/></th>
	      </tr>
	    </thead>
	    <tbody>
	    <c:forEach var="fixture" items="${fixtures}">
	      <c:choose>
	        <c:when test="${fixture.active}">
	      <tr>
	        </c:when>
	        <c:otherwise>
	      <tr class="strikethrough">
            </c:otherwise>
          </c:choose>
	  	    <td class="not-small"><fmt:formatDate value="${fixture.date}" pattern="MMM d, YYYY (E)" /> @ <fmt:formatDate value="${fixture.time}" pattern="h:mma" /></td>
	  	    <td class="only-small">
	  	    	<fmt:formatDate value="${fixture.date}" pattern="MMM d (E)" /> <fmt:formatDate value="${fixture.time}" pattern="h:mma" />
	  	    	<br/>
	  	    	<div id="arrow${fixture.fixtureId}" class="expand icon"></div>
	  	    	<div class="field"><a class="field" onclick='expand_field(fixture${fixture.fixtureId}, "${fixture.field}", "${fixture.fieldMapUri}", arrow${fixture.fixtureId})'>Field</a></div>
	  	    	<div id="fixture${fixture.fixtureId}"></div>
	  	    </td>
	  	    <td align="right">${fixture.home}</td>
	  	    <td align="center">${fixture.homeScore}-${fixture.awayScore}</td>
	  	    <td>${fixture.away}</td>
	  	    <td class="not-small"><a href="${fixture.fieldMapUri}" target="_blank">${fixture.field}</a></td>
	  	  </tr>
	    </c:forEach>
	    </tbody>
	  </table>
    </article>
    
    <script type="text/javascript">
    function expand_field(divField, field, fieldMapUri, divArrow) { 
    	if (divField.innerHTML == "") 
    		divField.innerHTML = "<a style='position: absolute;' class='field' href='" + fieldMapUri + "' target='_blank'>" + field + "</a><br>"; 
    	else 
    		divField.innerHTML = "";
    	
    	$(divArrow).toggleClass('expanded');
    }
    </script>