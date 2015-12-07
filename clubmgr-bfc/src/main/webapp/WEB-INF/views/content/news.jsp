<%@ include file="/WEB-INF/views/taglibs.jsp"%>

      <h1><f:message key="news.title"/></h1>
      <c:forEach var="post" items="${posts}">
      
	  <fmt:formatDate value="${post.createDate}" var="formattedDate" 
                type="date" pattern="MMMM d, yyyy" />
	  <article>
	    <header>
          <h3><a href='<c:url value="/post/${post.id}"/>'>${post.title}</a></h3>
		</header>
		<sup>
			<f:message key="news.posted.on"/>: ${formattedDate}
			<f:message key="news.posted.in"/>
		  	<c:forEach var="category" items="${post.categories}">
				<a href="${post.author.url}/category/${category.slug}" target="_blank">${category.name}</a>
		  	</c:forEach>
		</sup>
		<p>
		${post.excerpt}
		<p>
		<a href='<c:url value="/post/${post.id}"/>' class="button icon fa-file-text-o"><f:message key="news.posted.more"/></a>
	  </article>
	  
	  </c:forEach>
	  <c:if test="${previous >= 0}">
	  <a href="news.html?start=${previous}" class="button icon fa-files-o"><f:message key="news.previous"/></a>
	  </c:if> 
	  <c:if test="${next ne '0'}">
	  <a href="news.html?start=${next}" class="button icon fa-files-o"><f:message key="news.next"/></a>
	  </c:if>