<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<article>
      <header>
        <h1><fmt:message key="mens.A.title"/></h1>
      </header>
      <span class="image featured"><img src='<c:url value="/static/images/mens-a-2017.jpg"/>' alt="<fmt:message key="mens.A.image.alt"/>" /></span>
	  <p><fmt:message key="mens.A.main"/></p>
	  <h3><fmt:message key="teams.leagues"/>:</h3>
	  <ul class="default">
	  	<li>
	  		<fmt:message key="teams.winter"/>: <a href='<fmt:message key="mens.A.winter.name.url"/>' target="_blank"><fmt:message key="mens.A.winter.name"/></a>
	  		/
	  		<a href='<fmt:message key="mens.A.winter.division.url"/>' target="_blank"><fmt:message key="mens.A.winter.division"/></a>
	  	</li>
	  	<li>
	  		<fmt:message key="teams.summer"/>: <a href='<fmt:message key="mens.A.summer.name.url"/>' target="_blank"><fmt:message key="mens.A.summer.name"/></a>
	  		/
	  		<a href='<fmt:message key="mens.A.summer.division.url"/>' target="_blank"><fmt:message key="mens.A.summer.division"/></a>
	  	</li>
	  </ul>
	  <h3><fmt:message key="teams.training"/>:</h3>
	  <ul class="default">
	  	<li><fmt:message key="mens.A.winter.training.1"/></li>
	  </ul>
    </article>