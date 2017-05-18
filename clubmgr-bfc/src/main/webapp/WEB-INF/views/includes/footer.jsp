<%@ include file="/WEB-INF/views/taglibs.jsp" %>

	<div id="footer-wrapper">
		<footer id="footer" class="container">
			<div class="row">
				<div class="4u 6u(medium) 12u$(small)">
					<section class="widget links">
						<h3><fmt:message key="footer.list1.title"/></h3>
						<ul class="style2">
							<c:forEach var="post" items="${footerPosts}">
	 							<fmt:formatDate value="${post.createDate}" var="formattedDate" 
                					type="date" pattern="MMMM d, yyyy" />
      							<li>
      								<a href='<c:url value="/post/${post.id}"/>'>${post.title}</a><br>
      								<sup>
      									${formattedDate} <fmt:message key="footer.list1.in"/>
      									<c:forEach var="category" items="${post.categories}">
      										${category.name}
      									</c:forEach>
      								</sup>
      							</li>
  							</c:forEach>
						</ul>
					</section>
				</div>
				<div class="4u 6u$(medium) 12u$(small)">
					<!-- Links -->
					<section class="widget links">
						<h3><fmt:message key="footer.list2.title"/></h3>
						<ul class="style2">
							<li><a href="<fmt:message key='footer.list2.item1.url'/>" target="_blank"><fmt:message key="footer.list2.item1.name"/></a></li>
							<li><a href="<fmt:message key='footer.list2.item2.url'/>" target="_blank"><fmt:message key="footer.list2.item2.name"/></a></li>
							<li><a href="<fmt:message key='footer.list2.item3.url'/>" target="_blank"><fmt:message key="footer.list2.item3.name"/></a></li>
							<li><a href="<fmt:message key='footer.list2.item4.url'/>" target="_blank"><fmt:message key="footer.list2.item4.name"/></a></li>
							<li><a href="<fmt:message key='footer.list2.item5.url'/>" target="_blank"><fmt:message key="footer.list2.item5.name"/></a></li>
							<li><a href="<fmt:message key='footer.list2.item6.url'/>" target="_blank"><fmt:message key="footer.list2.item6.name"/></a></li>
							<li><a href="<fmt:message key='footer.list2.item7.url'/>" target="_blank"><fmt:message key="footer.list2.item7.name"/></a></li>
						</ul>
					</section>
				</div>
				<div class="4u 12u$(medium) 12u$(small)">
					<!-- Contact -->
					<section class="widget contact last">
						<h3><fmt:message key="footer.contact"/></h3>
						<ul>
  							<li>
  								<a href="<fmt:message key='footer.social.facebook.url'/>" title="<fmt:message key='footer.social.facebook.url'/>" target="_blank">
  									<span class="fa-stack fa-2x">
										<i class="fa fa-square fa-stack-2x"></i>
  										<i class="fa fa-facebook fa-stack-1x fa-inverse" aria-hidden="true" ></i>
  									</span>
  								</a>
  							</li>
  							<li>
  								<a href="<fmt:message key='footer.social.twitter.url'/>"  title="<fmt:message key='footer.social.twitter.url'/>" target="_blank">
  									<span class="fa-stack fa-2x">
										<i class="fa fa-square fa-stack-2x"></i>
  										<i class="fa fa-twitter fa-stack-1x fa-inverse" aria-hidden="true"></i>
  									</span>
  								</a>
  							</li>
  							<li>
  								<a href="<fmt:message key='footer.social.google_plus.url'/>" title="<fmt:message key='footer.social.google_plus.url'/>" target="_blank">
  									<span class="fa-stack fa-2x">
										<i class="fa fa-square fa-stack-2x"></i>
										<i class="fa fa-google-plus fa-stack-1x fa-inverse" aria-hidden="true"></i>
									</span>
  								</a>
  							</li>
  							<li>
  								<a href="<fmt:message key='footer.social.wordpress.url'/>" title="<fmt:message key='footer.social.wordpress.url'/>" target="_blank">
  									<span class="fa-stack fa-2x">
  										<i class="fa fa-square fa-stack-2x" aria-hidden="true"></i>
  										<i class="fa fa-wordpress fa-stack-1x fa-inverse" aria-hidden="true"></i>
  									</span>
  								</a>
  							</li>
						</ul>
					</section>
				</div>
			</div>
			<div class="row">
				<div class="12u">
					<div id="map_wrapper">
    					<div id="map_canvas" class="mapping"></div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="12u">	  
					<jsp:useBean id="now" class="java.util.Date" />
	  				<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
	  				<!-- Copyright -->
	  				<div id="copyright">
	    				<ul class="menu">
		  					<li>&copy; ${year} <fmt:message key="footer.copyright"/></li><li><fmt:message key="footer.design"/></li>
						</ul>
	 				</div>
	 			</div>
	 		</div>
		</footer>
	</div>
    
	<script>
		(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
			(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
			m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
			})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
		ga('create', 'UA-31008964-3', 'auto');
		ga('send', 'pageview');
	</script>