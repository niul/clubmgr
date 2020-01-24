<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<article>
      <header>
        <h1><fmt:message key="fixture.title"/></h1>
      </header>
      
      <div class="row">
      	<!-- Fixture Details -->
      	<div class="4u 12u(small)">
	  	<table>
	  		<tr>
	  			<td><strong><fmt:message key="fixture.home"/>:</strong></td>
	  			<td>${fixture.home}</td>
	  		</tr>
	  		<tr>
	  			<td><strong><fmt:message key="fixture.away"/>:</strong></td>
	  			<td>${fixture.away}</td>
	  		</tr>
	  		<tr>
	  			<td><strong><fmt:message key="fixture.date"/>:</strong></td>
	  			<td><fmt:formatDate value="${fixture.date}" pattern="E MMM, d" /> @ <fmt:formatDate value="${fixture.time}" pattern="h:mma" /></td>
	  		</tr>
	  		<tr>
	  			<td><strong><fmt:message key="fixture.field"/>:</strong></td>
	  			<td><a href="${fixture.fieldMapUri}" target="_blank">${fixture.field}</a></td>
	  		</tr>
	  	</table>
	  	</div>
	  
      	<!-- Summary -->
      	<div class="2u -6u 12u(small)">
	  	<table>
	  		<tr>
	  			<td><span class="highlight-YES"><fmt:message key="fixture.yes"/></span> : </td>
	  			<td>${fixtureSummary.yes}</td>
	  		</tr>
	  		<tr>
	  			<td><span class="highlight-MAYBE"><fmt:message key="fixture.maybe"/></span> : </td>
	  			<td>${fixtureSummary.maybe}</td>
	  		</tr>
	  		<tr>
		  		<td><span class="highlight-NO"><fmt:message key="fixture.no"/></span> : </td>
		  		<td>${fixtureSummary.no}</td>
	  		</tr>
	  		<tr>
		  		<td><span class="highlight-PENDING"><fmt:message key="fixture.pending"/></span> : </td>
		  		<td>${fixtureSummary.pending}</td>
	  		</tr>
	  	</table>
	  	</div>
	  </div>
	  
	  <c:if test="${pageContext.request.method=='GET'}">
	    <c:if test="${not empty playerFixtureInfo && statusUpdated}">
	    <div class="form_error">
	      <fmt:message key="fixture.response">
		    <fmt:param value="${playerFixtureInfo.player.firstName}"/>
		    <fmt:param value="${playerFixtureInfo.status}"/>
		  </fmt:message>
	    </div>
	      <c:if test="${playerFixtureInfo.status eq 'NO' || playerFixtureInfo.status eq 'MAYBE'}">
	    	  <form:form action="${pageContext.request.contextPath}/fixture.html" 
				  modelAttribute="fixtureData" 
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
	    	<c:if test="${playerFixtureInfo.player.manager || sessionScope.user != null}">
	    	<th><fmt:message key="fixture.headers.update"/></th>
	    	</c:if>
	      </tr>
	    </thead>
	    <tbody>
	    <c:forEach var="playerFixtureInfoIter" items="${playerFixtureInfoList}" varStatus="vs">
	      <tr class="${vs.index % 2 == 1 ? 'odd' : 'even'}">
	      	<td>${playerFixtureInfoIter.player.firstName} ${playerFixtureInfoIter.player.lastName}</td>
	      	<td><span class="highlight-${playerFixtureInfoIter.status}">${playerFixtureInfoIter.status}</span></td>
	      	<td class="not-small"><fmt:formatDate value="${playerFixtureInfoIter.viewed}" pattern="E MMM, d @ h:mma" /></td>
	      	<td>${playerFixtureInfoIter.comment}</td>
	      	<c:if test="${playerFixtureInfo.player.manager || sessionScope.user != null}">
	    	<td>
	    		<a href='<c:url value="/fixture.html?uuid=${fixture.uuid}&player=${playerFixtureInfo.uuid}&updatePlayer=${playerFixtureInfoIter.uuid}&status=YES"/>'><span class="highlight-YES"><fmt:message key="fixture.yes"/></span></a> |
	    		<a href='<c:url value="/fixture.html?uuid=${fixture.uuid}&player=${playerFixtureInfo.uuid}&updatePlayer=${playerFixtureInfoIter.uuid}&status=MAYBE"/>'><span class="highlight-MAYBE"><fmt:message key="fixture.maybe"/></span></a> |
	    		<a href='<c:url value="/fixture.html?uuid=${fixture.uuid}&player=${playerFixtureInfo.uuid}&updatePlayer=${playerFixtureInfoIter.uuid}&status=NO"/>'><span class="highlight-NO"><fmt:message key="fixture.no"/></span></a>
	    	</td>
	    	</c:if>
	  	  </tr>
	    </c:forEach>
	    </tbody>
	  </table>
    </article>