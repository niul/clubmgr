<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<article>
      <header>
        <h1><fmt:message key="mens.classics.title"/></h1>
      </header>
      <span class="image featured"><img src='<c:url value="/static/images/mens-classics.jpg"/>' alt="<fmt:message key="mens.classics.image.alt"/>" /></span>
	  <p><fmt:message key="mens.classics.main"/></p>
	  <h3><fmt:message key="teams.leagues"/>:</h3>
	  <ul class="default">
	  	<li>
	  		<fmt:message key="teams.winter"/>: <a href='<fmt:message key="mens.classics.winter.name.url"/>'><fmt:message key="mens.classics.winter.name"/></a>
	  		/
	  		<a href='<fmt:message key="mens.classics.winter.division.url"/>'><fmt:message key="mens.classics.winter.division"/></a>
	  	</li>
	  	<li>
	  		<fmt:message key="teams.summer"/>: <a href='<fmt:message key="mens.classics.summer.name.url"/>'><fmt:message key="mens.classics.summer.name"/></a>
	  	</li>
	  </ul>
	  <h3><fmt:message key="teams.training"/>:</h3>
	  <ul class="default">
	  	<li><fmt:message key="mens.classics.winter.training.1"/></li>
	  </ul>
    </article>