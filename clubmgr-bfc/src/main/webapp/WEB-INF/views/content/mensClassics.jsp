<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<article>
      <header>
        <h2><f:message key="mens.classics.title"/></h2>
      </header>
      <span class="image featured"><img src="static/images/mens-classics.jpg" alt="" /></span>
	  <p><f:message key="mens.classics.main"/></p>
	  <h3><f:message key="teams.leagues"/>:</h3>
	  <ul class="default">
	  	<li>
	  		<f:message key="teams.winter"/>: <a href='<f:message key="mens.classics.winter.name.url"/>'><f:message key="mens.classics.winter.name"/></a>
	  		/
	  		<a href='<f:message key="mens.classics.winter.division.url"/>'><f:message key="mens.classics.winter.division"/></a>
	  	</li>
	  	<li>
	  		<f:message key="teams.summer"/>: <a href='<f:message key="mens.classics.summer.name.url"/>'><f:message key="mens.classics.summer.name"/></a>
	  	</li>
	  </ul>
	  <h3><f:message key="teams.training"/>:</h3>
	  <ul class="default">
	  	<li><f:message key="mens.classics.winter.training.1"/></li>
	  </ul>
    </article>