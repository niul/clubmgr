<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<script src='<c:url value="/static/js/jquery.tablesorter.min.js"/>'></script>

	<article>
      <header>
	    <h1>${teamseasonmap.team.name} / ${teamseasonmap.season.name}</h1>
      </header>
      <div class="outline">
        <div class="tab_header">
	      <ul class='tabs'>
		    <li><a href='#tab1'><fmt:message key="season.header.standings"/></a></li>
		    <li><a href='#tab2'><fmt:message key="season.header.fixtures"/></a></li>
		    <li><a href='#tab3'><fmt:message key="season.header.statistics"/></a></li>
	      </ul>
	    </div>
	  <div id='tab1'>
        <h3><fmt:message key="standings.title"/> / <a href="${teamseasonmap.standingsUri}" target="_blank">${teamseasonmap.description}</a></h3>
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
	    <c:forEach var="standing" items="${standings}" varStatus="vs">
	      <tr class="${vs.index % 2 == 1 ? 'odd' : 'even'}">
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
	  </div>
	  <div id='tab2'>
        <h3><fmt:message key="fixtures.title"/> / <a href="${teamseasonmap.fixturesUri}" target="_blank">${teamseasonmap.description}</a></h3>
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
	    <c:forEach var="fixture" items="${fixtures}" varStatus="vs">
	      <c:choose>
	        <c:when test="${fixture.active}">
	      <tr  class="${vs.index % 2 == 1 ? 'odd' : 'even'}">
	        </c:when>
	        <c:otherwise>
	      <tr class="strikethrough ${vs.index % 2 == 1 ? 'odd' : 'even'}">
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
	  </div>
	  <div id='tab3'>
        <h3><fmt:message key="statistics.title"/> / ${teamseasonmap.description}</h3>
  	    <table id="statsTable" class="default fixtures u-full-width tablesorter">
	    <thead>
	      <tr>
	    	<th><fmt:message key="statistics.headers.name"/></th>
	    	<th><fmt:message key="statistics.headers.games_played"/></th>
	    	<th><fmt:message key="statistics.headers.yellow"/></th>
	    	<th><fmt:message key="statistics.headers.red"/></th>
	    	<th><fmt:message key="statistics.headers.assists"/></th>
	    	<th><fmt:message key="statistics.headers.goals"/></th>
	      </tr>
	    </thead>
	    <tbody>
	    <c:forEach var="playerStat" items="${player_stats}" varStatus="vs">
	      <tr  class="${vs.index % 2 == 1 ? 'odd' : 'even'}">
	  	    <td>${playerStat.firstName} ${playerStat.lastName}</td>
	  	    <td>${playerStat.started + playerStat.substitute}</td>
	  	    <td>${playerStat.yellowCards}</td>
	  	    <td>${playerStat.redCards}</td>
	  	    <td>${playerStat.assists}</td>
	  	    <td>${playerStat.goals}</td>
	  	  </tr>
	    </c:forEach>
	    </tbody>
	  </table>
	  </div>
      
	  </div>
	</article>
	
		<article>
      <header>
      </header>
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
    <script>
    $(document).ready(function() {
    	$("#statsTable").tablesorter({widgets: ['zebra']}); 
    	
    	$('ul.tabs').each(function() {
    	  // For each set of tabs, we want to keep track of
    	  // which tab is active and its associated content
    	  var $active, $content, $links = $(this).find('a');

    	  // If the location.hash matches one of the links, use that as the active tab.
    	  // If no match is found, use the first link as the initial active tab.
    	  $active = $($links.filter('[href="'+location.hash+'"]')[0] || $links[0]);
    	  $active.addClass('active');

    	  $content = $($active[0].hash);

    	  // Hide the remaining content
    	  $links.not($active).each(function () {
    	    $(this.hash).hide();
    	  });

    	  // Bind the click event handler
    	  $(this).on('click', 'a', function(e){
    	    // Make the old tab inactive.
    	    $active.removeClass('active');
    	    $content.hide();

    	    // Update the variables with the new link and content
    	    $active = $(this);
    	    $content = $(this.hash);

    	    // Make the tab active.
    	    $active.addClass('active');
    	    $content.show();

    	    // Prevent the anchor's default click action
    	    e.preventDefault();
    	  });
    	});
    });
    </script>