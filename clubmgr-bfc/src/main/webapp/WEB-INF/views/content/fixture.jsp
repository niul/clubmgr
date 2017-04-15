<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<article>
      <header>
        <h1><fmt:message key="fixture.title"/></h1>
      </header>
	  <strong><fmt:message key="fixture.home"/>:</strong> ${fixture.home}<br/>
	  <strong><fmt:message key="fixture.away"/>:</strong> ${fixture.away}<br/>
	  <strong><fmt:message key="fixture.date"/>:</strong> <fmt:formatDate value="${fixture.date}" pattern="E MMM, d" /> @ <fmt:formatDate value="${fixture.time}" pattern="h:mma" /><br/>
	  <strong><fmt:message key="fixture.field"/>:</strong> <a href="${fixture.fieldMapUri}" target="_blank">${fixture.field}</a><br/>
	  
	  <c:if test="${pageContext.request.method=='GET'}">
	    <c:if test="${not empty playerFixtureInfo}">
	    <div class="form_error">
	      <fmt:message key="fixture.response">
		    <fmt:param value="${playerFixtureInfo.player.firstName}"/>
		    <fmt:param value="${playerFixtureInfo.status}"/>
		  </fmt:message>
	    </div>
	      <c:if test="${playerFixtureInfo.status eq 'NO' || playerFixtureInfo.status eq 'MAYBE'}">
	    	  <form:form action="${pageContext.request.contextPath}/fixture.html" 
				  commandName="fixtureData" 
				  method="post">
			  <div class="row">
			    <div class="12u">
				  <form:label path="comment"><fmt:message key="fixture.comment" />:</form:label>
				  <form:input type="text" path="comment" class="red"/>
				  <form:hidden path="uuid" />
				  <form:hidden path="player"/>
			    </div>
			    <div class="12u">
			      <ul class="actions">
			        <li>
			      	  <button type="submit" class="button icon fa-envelope-o"><fmt:message key='form.submit' /></button>
			        </li>
				  </ul>
			    </div>
			  </div>
            </form:form>
	      </c:if>
	    </c:if>
	  </c:if>
	  
	  <table class="default fixtures u-full-width">
	    <thead>
	      <tr>
	    	<th><fmt:message key="fixture.headers.player"/></th>
	    	<th><fmt:message key="fixture.headers.status"/></th>
	    	<th class="not-small"><fmt:message key="fixture.headers.viewed"/></th>
	    	<th><fmt:message key="fixture.headers.comment"/></th>
	      </tr>
	    </thead>
	    <tbody>
	    <c:forEach var="playerFixtureInfo" items="${playerFixtureInfoList}" varStatus="vs">
	      <tr class="${vs.index % 2 == 1 ? 'odd' : 'even'}">
	      	<td>${playerFixtureInfo.player.firstName} ${playerFixtureInfo.player.lastName}</td>
	      	<td><span class="highlight-${playerFixtureInfo.status}">${playerFixtureInfo.status}</span></td>
	      	<td class="not-small"><fmt:formatDate value="${playerFixtureInfo.viewed}" pattern="E MMM, d @ h:mma" /></td>
	      	<td>${playerFixtureInfo.comment}</td>
	  	  </tr>
	    </c:forEach>
	    </tbody>
	  </table>
    </article>