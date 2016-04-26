<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<article>
      <header>
        <h1><f:message key="fixtures.title"/></h1>
      </header>
	  <h3>${teamseasonmap.team.name} - ${teamseasonmap.season.name}</h3>
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
	  	    <td class="not-small"><fmt:formatDate value="${fixture.date}" pattern="E MMM d, YYYY" /> @ <fmt:formatDate value="${fixture.time}" pattern="h:mma" /></td>
	  	    <td class="only-small">
	  	    	<fmt:formatDate value="${fixture.date}" pattern="E MMM, d" /> <fmt:formatDate value="${fixture.time}" pattern="h:mma" />
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