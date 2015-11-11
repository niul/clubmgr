<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<article>
      <header>
        <h2><f:message key="mens.jurassic.title"/></h2>
      </header>
      <span class="image featured"><img src="static/images/mens-jurassic.jpg" alt="" /></span>
	  <p><f:message key="mens.jurassic.main"/></p>
	  <h3><f:message key="teams.leagues"/>:</h3>
	  <ul class="default">
	  	<li>
	  		<f:message key="teams.winter"/>: <a href='<f:message key="mens.jurassic.winter.name.url"/>'><f:message key="mens.jurassic.winter.name"/></a>
	  		/
	  		<a href='<f:message key="mens.jurassic.winter.division.url"/>'><f:message key="mens.jurassic.winter.division"/></a>
	  	</li>
	  </ul>
	  <h3><f:message key="teams.training"/>:</h3>
	  <ul class="default">
	  	<li><f:message key="mens.jurassic.winter.training.1"/></li>
	  </ul>
    </article>