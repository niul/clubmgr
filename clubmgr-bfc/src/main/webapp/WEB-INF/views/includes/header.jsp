<%@ include file="/WEB-INF/views/taglibs.jsp" %>
	
	<div id="header-wrapper">
		<header id="header" class="container">
			<!-- Logo -->
			<div id="logo" >
				<a href="index.html"><img id="header_logo" border="0" alt="bombastic fc" src='<c:url value="/static/images/bombasticfc_logo.png"/>' width="160" height="160"></a>
				<span><f:message key="header.title"/></span>
			</div>
			<!-- Navigation Bar -->
			<nav id="nav">
	   			<ul>
	    			<c:choose>
	      			<c:when test="${page=='home'}">
	        			<li class="current"><a href="index.html"><f:message key="menu.item1"/></a></li>
	      			</c:when>
	      			<c:otherwise>
	        			<li><a href="index.html"><f:message key="menu.item1"/></a></li>
	      			</c:otherwise>
	    			</c:choose>
        			<c:choose>
	      			<c:when test="${(page=='teams') || (page=='mensA') || (page=='mensB') || (page=='mensClassic') || (page=='mensJurassic') || (page=='womens') || (page=='womensA') || (page=='standings') || (page=='fixtures')}">
	        			<li class="current">
	          				<a href="#"><f:message key="menu.item4"/></a>
	          				<ul>
	            				<li>
	            					<a href="mens.html"><f:message key="teams.section.main.b1"/></a>
	          						<ul>
	            						<li>
	            							<a href="mensA.html"><f:message key="menu.mens.b1"/></a>
	            							<ul>
	            								<li><a href="standings_fixtures.html?team=BOMBASTIC_MENS_A&season=WINTER_2015"><f:message key="teams.season.winter2015"/></a></li>
	            							</ul>
	            						</li>
	            						<li>
	            							<a href="mensB.html"><f:message key="menu.mens.b2"/></a>
	            							<ul>
	            								<li><a href="standings_fixtures.html?team=BOMBASTIC_MENS_B&season=WINTER_2015"><f:message key="teams.season.winter2015"/></a></li>
	            							</ul>
	            						</li>
	            						<li>
	            							<a href="mensClassics.html"><f:message key="menu.mens.b3"/></a>
	            							<ul>
	            								<li><a href="standings_fixtures.html?team=BOMBASTIC_MENS_CLASSICS&season=WINTER_2015"><f:message key="teams.season.winter2015"/></a></li>
	            								<li><a href="fixtures.html?team=BOMBASTIC_MENS_CLASSICS&season=WINTER_2015"><f:message key="teams.section.fixtures"/></a></li>
	            							</ul>
	            						</li>
	            						<li>
	            							<a href="mensJurassic.html"><f:message key="menu.mens.b4"/></a>
	            							<ul>
	            								<li><a href="standings_fixtures.html?team=BOMBASTIC_MENS_JURASSIC&season=WINTER_2015"><f:message key="teams.season.winter2015"/></a></li>
	            							</ul>
	            						</li>
	          						</ul>
	          					</li>
	            				<li>
	            					<a href="womens.html"><f:message key="menu.womens.b1"/></a>
	            					<ul>
	            						<li><a href="standings_fixtures.html?team=BOMBASTIC_WOMENS&season=WINTER_2015"><f:message key="teams.season.winter2015"/></a></li>
	            					</ul>
	            				</li>
	          				</ul>
	        			</li>
	      			</c:when>
	      			<c:otherwise>
	        			<li>
	          				<a href="#"><f:message key="menu.item4"/></a>
	          				<ul>
	            				<li>
	            					<a href="mens.html"><f:message key="teams.section.main.b1"/></a>
	          						<ul>
	            						<li>
	            							<a href="mensA.html"><f:message key="menu.mens.b1"/></a>
	            							<ul>
	            								<li><a href="standings_fixtures.html?team=BOMBASTIC_MENS_A&season=WINTER_2015"><f:message key="teams.season.winter2015"/></a></li>
	            							</ul>
	            						</li>
	            						<li>
	            							<a href="mensB.html"><f:message key="menu.mens.b2"/></a>
	            							<ul>
	            								<li><a href="standings_fixtures.html?team=BOMBASTIC_MENS_B&season=WINTER_2015"><f:message key="teams.season.winter2015"/></a></li>
	            							</ul>
	            						</li>
	            						<li>
	            							<a href="mensClassics.html"><f:message key="menu.mens.b3"/></a>
	            							<ul>
	            								<li><a href="standings_fixtures.html?team=BOMBASTIC_MENS_CLASSICS&season=WINTER_2015"><f:message key="teams.season.winter2015"/></a></li>
	            							</ul>
	            						</li>
	            						<li>
	            							<a href="mensJurassic.html"><f:message key="menu.mens.b4"/></a>
	            							<ul>
	            								<li><a href="standings_fixtures.html?team=BOMBASTIC_MENS_JURASSIC&season=WINTER_2015"><f:message key="teams.season.winter2015"/></a></li>
	            							</ul>
	            						</li>
	          						</ul>
	          					</li>
	            				<li>
	            					<a href="womens.html"><f:message key="menu.womens.b1"/></a>
	            					<ul>
	            						<li><a href="standings_fixtures.html?team=BOMBASTIC_WOMENS&season=WINTER_2015"><f:message key="teams.season.winter2015"/></a></li>
	            					</ul>
	            				</li>
	          				</ul>
	        			</li>
	      			</c:otherwise>
	    			</c:choose>
	    			<c:choose>
	      			<c:when test="${page=='news' || page=='post'}">
	       				<li class="current"><a href="news.html"><f:message key="menu.item3"/></a></li>
	      			</c:when>
	      			<c:otherwise>
	        			<li><a href="news.html"><f:message key="menu.item3"/></a></li>
	      			</c:otherwise>
	    			</c:choose>
	    			<c:choose>
	      			<c:when test="${page=='contact'}">
	        			<li class="current"><a href="contact.html"><f:message key="menu.item5"/></a></li>
	      			</c:when>
	      			<c:otherwise>
	        			<li><a href="contact.html"><f:message key="menu.item5"/></a></li>
	      			</c:otherwise>
	    			</c:choose>
	    			<c:choose>
	      			<c:when test="${page=='sponsors'}">
	        				<li class="current"><a href="sponsors.html"><f:message key="menu.item6"/></a></li>
	      			</c:when>
	      			<c:otherwise>
	        			<li><a href="sponsors.html"><f:message key="menu.item6"/></a></li>
	      			</c:otherwise>
	    			</c:choose>
	    			<c:choose>
	      			<c:when test="${page=='about'}">
	       				<li class="current"><a href="about.html"><f:message key="menu.item2"/></a></li>
	      			</c:when>
	      			<c:otherwise>
	        			<li><a href="about.html"><f:message key="menu.item2"/></a></li>
	      			</c:otherwise>
	    			</c:choose>
        		</ul>
      		</nav>
			<div class="row">
      			<div class="2u -10u 12u$(medium) 12u$(small)">
      				<f:message key="header.base_url" var="baseUrl"/>
      				<c:set var="params" value="${pageContext.request.queryString}"/>
      				<c:if test="${empty params}">
						<c:set var="url" value="${baseUrl}${requestScope['javax.servlet.forward.request_uri']}" />
					</c:if>
      				<c:if test="${not empty params}">
      					<c:set var="url" value="${baseUrl}${requestScope['javax.servlet.forward.request_uri']}?${pageContext.request.queryString}" />
      				</c:if>
					
					<ul class="share-buttons">
  						<li><a href="https://www.facebook.com/sharer/sharer.php?u=${cf:urlEncode(url)}&t=" title="Share on Facebook" target="_blank" onclick="window.open('https://www.facebook.com/sharer/sharer.php?u=' + encodeURIComponent(${url}) + '&t=' + encodeURIComponent(${url})); return false;"><img src='<c:url value="/static/images/social/share/Facebook.png"/>'></a></li>
  						<li><a href="https://twitter.com/intent/tweet?source=${cf:urlEncode(url)}&text=:%20${cf:urlEncode(url)}&via=bombasticfc" target="_blank" title="Tweet" onclick="window.open('https://twitter.com/intent/tweet?text=' + encodeURIComponent(document.title) + ':%20'  + encodeURIComponent(${url})); return false;"><img src='<c:url value="/static/images/social/share/Twitter.png"/>'></a></li>
  						<li><a href="https://plus.google.com/share?url=${cf:urlEncode(url)}" target="_blank" title="Share on Google+" onclick="window.open('https://plus.google.com/share?url=' + encodeURIComponent(${url})); return false;"><img src='<c:url value="/static/images/social/share/Google+.png"/>'></a></li>
  						<li><a href="http://wordpress.com/press-this.php?u=${cf:urlEncode(url)}&t=&s=" target="_blank" title="Publish on WordPress" onclick="window.open('http://wordpress.com/press-this.php?u=' + encodeURIComponent(${url}) + '&t=' +  encodeURIComponent(document.title)); return false;"><img src='<c:url value="/static/images/social/share/Wordpress.png"/>'></a></li>
					</ul>
				</div>
			</div>
		</header>
	</div>