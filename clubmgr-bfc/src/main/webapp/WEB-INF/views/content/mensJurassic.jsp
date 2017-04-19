<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<article>
      <header>
        <h1><fmt:message key="mens.jurassic.title"/></h1>
      </header>
      <span class="image featured"><img src='<c:url value="/static/images/mens-jurassic.jpg"/>' alt="<fmt:message key="mens.jurassics.image.alt"/>" /></span>
	  <p><fmt:message key="mens.jurassic.main"/></p>
	  <h3><fmt:message key="teams.leagues"/>:</h3>
	  <ul class="default">
	  	<li>
	  		<fmt:message key="teams.winter"/>: <a href='<fmt:message key="mens.jurassic.winter.name.url"/>' target="_blank"><fmt:message key="mens.jurassic.winter.name"/></a>
	  		/
	  		<a href='<fmt:message key="mens.jurassic.winter.division.url"/>' target="_blank"><fmt:message key="mens.jurassic.winter.division"/></a>
	  	</li>
	  </ul>
	  <h3><fmt:message key="teams.training"/>:</h3>
	  <ul class="default">
	  	<li><fmt:message key="mens.jurassic.winter.training.1"/></li>
	  </ul>
    </article>