<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<article>
      <header>
        <h1><f:message key="standings.title"/></h1>
      </header>
	  <h3>${teamseasonmap.team.name} - ${teamseasonmap.season.name}</h3>
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