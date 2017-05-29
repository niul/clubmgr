<%@ include file="/WEB-INF/views/taglibs.jsp" %>
	
	<div id="header-wrapper">
		<header id="header" class="container">
		
			<div class="row">
		<sec:authorize access="hasAnyRole('ADMIN','CLUB_MGR','TEAM_MGR')">
			<div id="logout">
				<fmt:message key="admin.welcome"/> ${user.firstName} <a href="<c:url value="/logout.html" />"><fmt:message key="login.logout"/></a>
			</div>
		</sec:authorize>
			<!-- Logo -->
			<div id="logo" class="9u 12u$(medium)">
				<a href='<c:url value="/index.html"/>'><img id="header_logo" src='<c:url value="/static/images/bombasticfc_logo.png"/>' alt="<fmt:message key="header.logo.image.alt"/>" width="160" height="160"></a>
				<span><fmt:message key="header.title"/></span>
			</div>
			<!-- Navigation Bar -->
			<nav id="nav">
	   			<ul>
		<sec:authorize access="hasAnyRole('ADMIN','CLUB_MGR','TEAM_MGR')">
	    			<c:choose>
	      			<c:when test="${fn:contains(requestScope['javax.servlet.forward.request_uri'], 'admin')}">
	        			<li class="current">
	      			</c:when>
	      			<c:otherwise>
	        			<li>
	      			</c:otherwise>
	    			</c:choose>
	        				<a href="#"><fmt:message key="menu.item0"/></a>
	        				<ul>
	        					<li><a href='<c:url value="/admin/change.html"/>'><fmt:message key="password_change.title"/></a></li>
	        					<li><a href='<c:url value="/admin/players.html"/>'><fmt:message key="admin.title"/></a></li>
	        				</ul>
		</sec:authorize>
	    			<c:choose>
	      			<c:when test="${page=='home'}">
	        			<li class="current">
	      			</c:when>
	      			<c:otherwise>
	      				<li>
	      			</c:otherwise>
	    			</c:choose>
	        			<a href='<c:url value="/index.html"/>'><fmt:message key="menu.item1"/></a>
	        			</li>
        			<c:choose>
	      			<c:when test="${(page=='teams') || (page=='mens') || (page=='mensA') || (page=='mensB') || (page=='mensClassic') || (page=='mensJurassic') || (page=='womens') || (page=='womensA') || (page=='standings_fixtures')}">
	        			<li class="current">
	        		</c:when>
	        		<c:otherwise>
	        			<li>
	        		</c:otherwise>
	        		</c:choose>
	          				<a href="#"><fmt:message key="menu.item4"/></a>
	          				<ul>
	            				<li>
	            					<a href='<c:url value="/womens.html"/>'><fmt:message key="menu.womens.b1"/></a>
	            					<ul>
	            						<li><a href='<c:url value="/season/BOMBASTIC_WOMENS/SUMMER_2017"/>'><fmt:message key="teams.season.summer2017"/></a></li>
	            						<li><a href='<c:url value="/season/BOMBASTIC_WOMENS/WINTER_2016"/>'><fmt:message key="teams.season.winter2016"/></a></li>
	            						<li>
	            							<a href="#"><fmt:message key="menu.archive"/></a>
	            							<ul>
	            								<li><a href='<c:url value="/season/BOMBASTIC_WOMENS/SUMMER_2016"/>'><fmt:message key="teams.season.summer2016"/></a></li>
	            								<li><a href='<c:url value="/season/BOMBASTIC_WOMENS/WINTER_2015"/>'><fmt:message key="teams.season.winter2015"/></a></li>
	            								<li><a href='<c:url value="/season/BOMBASTIC_WOMENS/SUMMER_2015"/>'><fmt:message key="teams.season.summer2015"/></a></li>
	            							</ul>
	            						</li>
	            					</ul>
	            				</li>
	            				<li>
	            					<a href='<c:url value="/mens.html"/>'><fmt:message key="teams.section.main.b1"/></a>
	          						<ul>
	            						<li>
	            							<a href='<c:url value="/mensA.html"/>'><fmt:message key="menu.mens.b1"/></a>
	            							<ul>
	            								<li><a href='<c:url value="/seasonDetail/BOMBASTIC_MENS_RRSL/SUMMER_2017"/>'><fmt:message key="teams.season.summer2017"/></a></li>
	            								<li><a href='<c:url value="/season/BOMBASTIC_MENS_A/WINTER_2016"/>'><fmt:message key="teams.season.winter2016"/></a></li>
	            								<li>
	            									<a href="#"><fmt:message key="menu.archive"/></a>
	            									<ul>
	            										<li><a href='<c:url value="/season/BOMBASTIC_MENS_A/SUMMER_2016"/>'><fmt:message key="teams.season.summer2016"/></a></li>
	            										<li><a href='<c:url value="/season/BOMBASTIC_MENS_A/WINTER_2015"/>'><fmt:message key="teams.season.winter2015"/></a></li>
	            										<li><a href='<c:url value="/season/BOMBASTIC_MENS_A/SUMMER_2015"/>'><fmt:message key="teams.season.summer2015"/></a></li>
	            									</ul>
	            								</li>
	            							</ul>
	            						</li>
	            						<li>
	            							<a href='<c:url value="/mensB.html"/>'><fmt:message key="menu.mens.b2"/></a>
	            							<ul>
	            								<li><a href='<c:url value="/season/BOMBASTIC_MENS_B/WINTER_2016"/>'><fmt:message key="teams.season.winter2016"/></a></li>
	            								<li><a href='<c:url value="/season/BOMBASTIC_MENS_B/WINTER_2015"/>'><fmt:message key="teams.season.winter2015"/></a></li>
	            							</ul>
	            						</li>
	            						<li>
	            							<a href='<c:url value="/mensClassics.html"/>'><fmt:message key="menu.mens.b3"/></a>
	            							<ul>
	            								<li><a href='<c:url value="/seasonDetail/BOMBASTIC_MENS_CESL/SUMMER_2017"/>'><fmt:message key="teams.season.summer2017"/></a></li>
	            								<li><a href='<c:url value="/season/BOMBASTIC_MENS_CLASSICS/WINTER_2016"/>'><fmt:message key="teams.season.winter2016"/></a></li>
	            								<li>
	            									<a href="#"><fmt:message key="menu.archive"/></a>
	            									<ul>
	            										<li><a href='<c:url value="/season/BOMBASTIC_MENS_CLASSICS/SUMMER_2016"/>'><fmt:message key="teams.season.summer2016"/></a></li>
	            										<li><a href='<c:url value="/season/BOMBASTIC_MENS_CLASSICS/WINTER_2015"/>'><fmt:message key="teams.season.winter2015"/></a></li>
	            										<li><a href='<c:url value="/season/BOMBASTIC_MENS_CLASSICS/SUMMER_2015"/>'><fmt:message key="teams.season.summer2015"/></a></li>
	            									</ul>
	            								</li>
	            							</ul>
	            						</li>
	            						<li>
	            							<a href='<c:url value="/mensJurassic.html"/>'><fmt:message key="menu.mens.b4"/></a>
	            							<ul>
	            								<li><a href='<c:url value="/fixtures/BOMBASTIC_MENS_JURASSIC/SUMMER_2017"/>'><fmt:message key="teams.season.summer2017"/></a></li>
	            								<li>
	            									<a href="#"><fmt:message key="menu.archive"/></a>
	            									<ul>
	            										<li><a href='<c:url value="/season/BOMBASTIC_MENS_JURASSIC/WINTER_2016"/>'><fmt:message key="teams.season.winter2016"/></a></li>
	            										<li><a href='<c:url value="/season/BOMBASTIC_MENS_JURASSIC/WINTER_2015"/>'><fmt:message key="teams.season.winter2015"/></a></li>
	            									</ul>
	            								</li>
	            							</ul>
	            						</li>
	          						</ul>
	          					</li>
	          				</ul>
	        			</li>
	    			<c:choose>
	      			<c:when test="${page=='news' || page=='post'}">
	       				<li class="current">
	      			</c:when>
	      			<c:otherwise>
	      				<li>
	      			</c:otherwise>
	    			</c:choose>
	       				<a href='<c:url value="/news.html"/>'><fmt:message key="menu.item3"/></a>
	       				</li>
	    			<c:choose>
	      			<c:when test="${page=='contact'}">
	        			<li class="current">
	      			</c:when>
	      			<c:otherwise>
	      				<li>
	      			</c:otherwise>
	    			</c:choose>
	        			<a href='<c:url value="/contact.html"/>'><fmt:message key="menu.item5"/></a>
	        			</li>
	    			<c:choose>
	      			<c:when test="${page=='sponsors'}">
	        				<li class="current">
	      			</c:when>
	      			<c:otherwise>
	      				<li>
	      			</c:otherwise>
	    			</c:choose>
	        			<a href='<c:url value="/sponsors.html"/>'><fmt:message key="menu.item6"/></a>
	        			</li>
	    			<c:choose>
	      			<c:when test="${page=='about'}">
	       				<li class="current">
	      			</c:when>
	      			<c:otherwise>
	      				<li>
	      			</c:otherwise>
	    			</c:choose>
	        			<a href='<c:url value="/about.html"/>'><fmt:message key="menu.item2"/></a>
	        			</li>
        		</ul>
      		</nav>
      			<div class="social-buttons 3u 12u$(medium) 12u$(small)">
					<ul class="share-buttons">
  						<li><a href="https://www.facebook.com/sharer/sharer.php?u=${cf:urlEncode(url)}&t=" title="Share on Facebook" target="_blank" onclick="window.open('https://www.facebook.com/sharer/sharer.php?u=' + encodeURIComponent('${url}') + '&t=' + encodeURIComponent('${url}')); return false;"><img src='<c:url value="/static/images/social/share/Facebook.png"/>' alt="<fmt:message key="footer.social.facebook.image.alt"/>"></a></li>
  						<li><a href="https://twitter.com/intent/tweet?source=${cf:urlEncode(url)}&text=:%20${cf:urlEncode(url)}&via=bombasticfc" target="_blank" title="Tweet" onclick="window.open('https://twitter.com/intent/tweet?text=' + encodeURIComponent(document.title) + ':%20'  + encodeURIComponent('${url}')); return false;"><img src='<c:url value="/static/images/social/share/Twitter.png"/>' alt="<fmt:message key="footer.social.twitter.image.alt"/>"></a></li>
  						<li><a href="https://plus.google.com/share?url=${cf:urlEncode(url)}" target="_blank" title="Share on Google+" onclick="window.open('https://plus.google.com/share?url=' + encodeURIComponent('${url}')); return false;"><img src='<c:url value="/static/images/social/share/Google+.png"/>' alt="<fmt:message key="footer.social.google.image.alt"/>"></a></li>
  						<li><a href="http://wordpress.com/press-this.php?u=${cf:urlEncode(url)}&t=&s=" target="_blank" title="Publish on WordPress" onclick="window.open('http://wordpress.com/press-this.php?u=' + encodeURIComponent('${url}') + '&t=' +  encodeURIComponent(document.title)); return false;"><img src='<c:url value="/static/images/social/share/Wordpress.png"/>' alt="<fmt:message key="footer.social.wordpress.image.alt"/>"></a></li>
					</ul>
				</div>
			</div>
		</header>
	</div>