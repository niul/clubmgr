<%@ include file="/WEB-INF/views/taglibs.jsp" %>
	
	<div id="header-wrapper">
		<header id="header" class="container">
			<div class="row">
			<!-- Logo -->
			<div id="logo" class="10u 12u$(medium)">
				<a href='<c:url value="/index.html"/>'><img id="header_logo" src='<c:url value="/static/images/bombasticfc_logo.png"/>' alt="<f:message key="header.logo.image.alt"/>" width="160" height="160"></a>
				<span><f:message key="header.title"/></span>
			</div>
			<!-- Navigation Bar -->
			<nav id="nav">
	   			<ul>
	    			<c:choose>
	      			<c:when test="${page=='home'}">
	        			<li class="current"><a href='<c:url value="/index.html"/>'><f:message key="menu.item1"/></a></li>
	      			</c:when>
	      			<c:otherwise>
	        			<li><a href='<c:url value="/index.html"/>'><f:message key="menu.item1"/></a></li>
	      			</c:otherwise>
	    			</c:choose>
        			<c:choose>
	      			<c:when test="${(page=='teams') || (page=='mens') || (page=='mensA') || (page=='mensB') || (page=='mensClassic') || (page=='mensJurassic') || (page=='womens') || (page=='womensA') || (page=='standings_fixtures')}">
	        			<li class="current">
	          				<a href="#"><f:message key="menu.item4"/></a>
	          				<ul>
	            				<li>
	            					<a href='<c:url value="/mens.html"/>'><f:message key="teams.section.main.b1"/></a>
	          						<ul>
	            						<li>
	            							<a href='<c:url value="/mensA.html"/>'><f:message key="menu.mens.b1"/></a>
	            							<ul>
	            								<li><a href='<c:url value="/season/BOMBASTIC_MENS_A/WINTER_2015"/>'><f:message key="teams.season.winter2015"/></a></li>
	            								<li><a href='<c:url value="/season/BOMBASTIC_MENS_A/SUMMER_2015"/>'><f:message key="teams.season.summer2015"/></a></li>
	            							</ul>
	            						</li>
	            						<li>
	            							<a href='<c:url value="/mensB.html"/>'><f:message key="menu.mens.b2"/></a>
	            							<ul>
	            								<li><a href='<c:url value="/season/BOMBASTIC_MENS_B/WINTER_2015"/>'><f:message key="teams.season.winter2015"/></a></li>
	            							</ul>
	            						</li>
	            						<li>
	            							<a href='<c:url value="/mensClassics.html"/>'><f:message key="menu.mens.b3"/></a>
	            							<ul>
	            								<li><a href='<c:url value="/season/BOMBASTIC_MENS_CLASSICS/WINTER_2015"/>'><f:message key="teams.season.winter2015"/></a></li>
	            							</ul>
	            						</li>
	            						<li>
	            							<a href='<c:url value="/mensJurassic.html"/>'><f:message key="menu.mens.b4"/></a>
	            							<ul>
	            								<li><a href='<c:url value="/season/BOMBASTIC_MENS_JURASSIC/WINTER_2015"/>'><f:message key="teams.season.winter2015"/></a></li>
	            							</ul>
	            						</li>
	          						</ul>
	          					</li>
	            				<li>
	            					<a href='<c:url value="/womens.html"/>'><f:message key="menu.womens.b1"/></a>
	            					<ul>
	            						<li><a href='<c:url value="/season/BOMBASTIC_WOMENS/WINTER_2015"/>'><f:message key="teams.season.winter2015"/></a></li>
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
	            					<a href='<c:url value="/mens.html"/>'><f:message key="teams.section.main.b1"/></a>
	          						<ul>
	            						<li>
	            							<a href='<c:url value="/mensA.html"/>'><f:message key="menu.mens.b1"/></a>
	            							<ul>
	            								<li><a href='<c:url value="/season/BOMBASTIC_MENS_A/WINTER_2015"/>'><f:message key="teams.season.winter2015"/></a></li>
	            								<li><a href='<c:url value="/season/BOMBASTIC_MENS_A/SUMMER_2015"/>'><f:message key="teams.season.summer2015"/></a></li>
	            							</ul>
	            						</li>
	            						<li>
	            							<a href='<c:url value="/mensB.html"/>'><f:message key="menu.mens.b2"/></a>
	            							<ul>
	            								<li><a href='<c:url value="/season/BOMBASTIC_MENS_B/WINTER_2015"/>'><f:message key="teams.season.winter2015"/></a></li>
	            							</ul>
	            						</li>
	            						<li>
	            							<a href='<c:url value="/mensClassics.html"/>'><f:message key="menu.mens.b3"/></a>
	            							<ul>
	            								<li><a href='<c:url value="/season/BOMBASTIC_MENS_CLASSICS/WINTER_2015"/>'><f:message key="teams.season.winter2015"/></a></li>
	            							</ul>
	            						</li>
	            						<li>
	            							<a href='<c:url value="/mensJurassic.html"/>'><f:message key="menu.mens.b4"/></a>
	            							<ul>
	            								<li><a href='<c:url value="/season/BOMBASTIC_MENS_JURASSIC/WINTER_2015"/>'><f:message key="teams.season.winter2015"/></a></li>
	            							</ul>
	            						</li>
	          						</ul>
	          					</li>
	            				<li>
	            					<a href='<c:url value="/womens.html"/>'><f:message key="menu.womens.b1"/></a>
	            					<ul>
	            						<li><a href='<c:url value="/season/BOMBASTIC_WOMENS/WINTER_2015"/>'><f:message key="teams.season.winter2015"/></a></li>
	            					</ul>
	            				</li>
	          				</ul>
	        			</li>
	      			</c:otherwise>
	    			</c:choose>
	    			<c:choose>
	      			<c:when test="${page=='news' || page=='post'}">
	       				<li class="current"><a href='<c:url value="/news.html"/>'><f:message key="menu.item3"/></a></li>
	      			</c:when>
	      			<c:otherwise>
	        			<li><a href='<c:url value="/news.html"/>'><f:message key="menu.item3"/></a></li>
	      			</c:otherwise>
	    			</c:choose>
	    			<c:choose>
	      			<c:when test="${page=='contact'}">
	        			<li class="current"><a href='<c:url value="/contact.html"/>'><f:message key="menu.item5"/></a></li>
	      			</c:when>
	      			<c:otherwise>
	        			<li><a href='<c:url value="/contact.html"/>'><f:message key="menu.item5"/></a></li>
	      			</c:otherwise>
	    			</c:choose>
	    			<c:choose>
	      			<c:when test="${page=='sponsors'}">
	        				<li class="current"><a href='<c:url value="/sponsors.html"/>'><f:message key="menu.item6"/></a></li>
	      			</c:when>
	      			<c:otherwise>
	        			<li><a href='<c:url value="/sponsors.html"/>'><f:message key="menu.item6"/></a></li>
	      			</c:otherwise>
	    			</c:choose>
	    			<c:choose>
	      			<c:when test="${page=='about'}">
	       				<li class="current"><a href='<c:url value="/about.html"/>'><f:message key="menu.item2"/></a></li>
	      			</c:when>
	      			<c:otherwise>
	        			<li><a href='<c:url value="/about.html"/>'><f:message key="menu.item2"/></a></li>
	      			</c:otherwise>
	    			</c:choose>
        		</ul>
      		</nav>
      			<div class="social-buttons 2u 12u$(medium) 12u$(small)">
					<ul class="share-buttons">
  						<li><a href="https://www.facebook.com/sharer/sharer.php?u=${cf:urlEncode(url)}&t=" title="Share on Facebook" target="_blank" onclick="window.open('https://www.facebook.com/sharer/sharer.php?u=' + encodeURIComponent('${url}') + '&t=' + encodeURIComponent('${url}')); return false;"><img src='<c:url value="/static/images/social/share/Facebook.png"/>' alt="<f:message key="footer.social.facebook.image.alt"/>"></a></li>
  						<li><a href="https://twitter.com/intent/tweet?source=${cf:urlEncode(url)}&text=:%20${cf:urlEncode(url)}&via=bombasticfc" target="_blank" title="Tweet" onclick="window.open('https://twitter.com/intent/tweet?text=' + encodeURIComponent(document.title) + ':%20'  + encodeURIComponent('${url}')); return false;"><img src='<c:url value="/static/images/social/share/Twitter.png"/>' alt="<f:message key="footer.social.twitter.image.alt"/>"></a></li>
  						<li><a href="https://plus.google.com/share?url=${cf:urlEncode(url)}" target="_blank" title="Share on Google+" onclick="window.open('https://plus.google.com/share?url=' + encodeURIComponent('${url}')); return false;"><img src='<c:url value="/static/images/social/share/Google+.png"/>' alt="<f:message key="footer.social.google.image.alt"/>"></a></li>
  						<li><a href="http://wordpress.com/press-this.php?u=${cf:urlEncode(url)}&t=&s=" target="_blank" title="Publish on WordPress" onclick="window.open('http://wordpress.com/press-this.php?u=' + encodeURIComponent('${url}') + '&t=' +  encodeURIComponent(document.title)); return false;"><img src='<c:url value="/static/images/social/share/Wordpress.png"/>' alt="<f:message key="footer.social.wordpress.image.alt"/>"></a></li>
					</ul>
				</div>
			</div>
		</header>
	</div>