<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<article>
      <header>
        <h2><f:message key="womens.A.title"/></h2>
      </header>
      <span class="image featured"><img src="static/images/womens-a.jpg" alt="" /></span>
	  <p><f:message key="womens.A.main"/></p>
	  <h3><f:message key="teams.leagues"/>:</h3>
	  <ul class="default">
	  	<li>
	  		<f:message key="teams.winter"/>: <a href='<f:message key="womens.A.winter.name.url"/>'><f:message key="womens.A.winter.name"/></a>
	  		/
	  		<a href='<f:message key="womens.A.winter.division.url"/>'><f:message key="womens.A.winter.division"/></a>
	  	</li>
	  	<li>
	  		<f:message key="teams.summer"/>: <a href='<f:message key="womens.A.summer.name.url"/>'><f:message key="womens.A.summer.name"/></a>
	  		/
	  		<a href='<f:message key="womens.A.summer.division.url"/>'><f:message key="womens.A.summer.division"/></a>
	  	</li>
	  </ul>
	  <h3><f:message key="teams.training"/>:</h3>
	  <ul class="default">
	  	<li><f:message key="womens.A.winter.training.1"/></li>
	  </ul>
    </article>