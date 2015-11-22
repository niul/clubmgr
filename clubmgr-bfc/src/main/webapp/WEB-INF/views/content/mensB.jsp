<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<article>
      <header>
        <h2><f:message key="mens.B.title"/></h2>
      </header>
      <span class="image featured"><img src='<c:url value="/static/images/mens-b.jpg"/>' alt="" /></span>
	  <p><f:message key="mens.B.main"/></p>
	  <h3><f:message key="teams.leagues"/>:</h3>
	  <ul class="default">
	  	<li>
	  		<f:message key="teams.winter"/>: <a href='<f:message key="mens.B.winter.name.url"/>'><f:message key="mens.B.winter.name"/></a>
	  		/
	  		<a href='<f:message key="mens.B.winter.division.url"/>'><f:message key="mens.B.winter.division"/></a>
	  	</li>
	  	<li>
	  		<f:message key="teams.summer"/>: <a href='<f:message key="mens.B.summer.name.url"/>'><f:message key="mens.B.summer.name"/></a>
	  		/
	  		<a href='<f:message key="mens.B.summer.division.url"/>'><f:message key="mens.B.summer.division"/></a>
	  	</li>
	  </ul>
	  <h3><f:message key="teams.training"/>:</h3>
	  <ul class="default">
	  	<li><f:message key="mens.B.winter.training.1"/></li>
	  	<li><f:message key="mens.B.winter.training.2"/></li>
	  </ul>
    </article>