<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<article>
      <header>
	    <h1>${teamseasonmap.team.name} - ${teamseasonmap.season.name}</h1>
        <h3><f:message key="standings.title"/></h3>
      </header>
	  <table class="default standings">
	    <thead>
	      <tr>
	    	<th class="not-small"><span title="<f:message key='standings.headers.position.name'/>"><f:message key="standings.headers.position"/></span></th>
	    	<th><span title="<f:message key='standings.headers.team.name'/>"><f:message key="standings.headers.team"/></span></th>
	    	<th><span title="<f:message key='standings.headers.games_played.name'/>"><f:message key="standings.headers.games_played"/></span></th>
	    	<th><span title="<f:message key='standings.headers.wins.name'/>"><f:message key="standings.headers.wins"/></span></th>
	    	<th><span title="<f:message key='standings.headers.losses.name'/>"><f:message key="standings.headers.ties"/></span></th>
	    	<th><span title="<f:message key='standings.headers.ties.name'/>"><f:message key="standings.headers.losses"/></span></th>
	    	<th class="not-small"><span title="<f:message key='standings.headers.goals_for.name'/>"><f:message key="standings.headers.goals_for"/></span></th>
	    	<th class="not-small"><span title="<f:message key='standings.headers.goals_against.name'/>"><f:message key="standings.headers.goals_against"/></span></th>
	    	<th><span title="<f:message key='standings.headers.points.name'/>"><f:message key="standings.headers.points"/></span></th>
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
        <h3><f:message key="fixtures.title"/></h3>
      </header>
	  <table class="default fixtures u-full-width">
	    <thead>
	      <tr>
	    	<th><f:message key="fixtures.headers.date"/></th>
	    	<th class="home"><f:message key="fixtures.headers.home"/></th>
	    	<th><f:message key="fixtures.headers.result"/></th>
	    	<th><f:message key="fixtures.headers.away"/></th>
	    	<th class="not-small"><f:message key="fixtures.headers.field"/></th>
	      </tr>
	    </thead>
	    <tbody>
	    <c:forEach var="fixture" items="${fixtures}">
	      <tr>
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