<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<article>
      <header>
        <h1><fmt:message key="mens.B.title"/></h1>
      </header>
      <span class="image featured"><img src='<c:url value="/static/images/mens-b.jpg"/>' alt="<fmt:message key="mens.B.image.alt"/>" /></span>
	  <p><fmt:message key="mens.B.main"/></p>
	  <h3><fmt:message key="teams.leagues"/>:</h3>
	  <ul class="default">
	  	<li>
	  		<fmt:message key="teams.winter"/>: <a href='<fmt:message key="mens.B.winter.name.url"/>'><fmt:message key="mens.B.winter.name"/></a>
	  		/
	  		<a href='<fmt:message key="mens.B.winter.division.url"/>'><fmt:message key="mens.B.winter.division"/></a>
	  	</li>
	  	<li>
	  		<fmt:message key="teams.summer"/>: <a href='<fmt:message key="mens.B.summer.name.url"/>'><fmt:message key="mens.B.summer.name"/></a>
	  		/
	  		<a href='<fmt:message key="mens.B.summer.division.url"/>'><fmt:message key="mens.B.summer.division"/></a>
	  	</li>
	  </ul>
	  <h3><fmt:message key="teams.training"/>:</h3>
	  <ul class="default">
	  	<li><fmt:message key="mens.B.winter.training.1"/></li>
	  	<li><fmt:message key="mens.B.winter.training.2"/></li>
	  </ul>
    </article>