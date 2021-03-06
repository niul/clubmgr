<%@ include file="/WEB-INF/views/taglibs.jsp"%>


	  <fmt:formatDate value="${post.createDate}" var="formattedDate" 
                type="date" pattern="MMMM d, yyyy" />
                
	  <article>
	    <header>
          <h1>${post.title}</h1>
		</header>
		<sup>
			<fmt:message key="news.posted.on"/>: ${formattedDate}
			<fmt:message key="news.posted.in"/>
		  	<c:forEach var="category" items="${post.categories}">
				<a href="${post.author.url}/category/${category.slug}" target="_blank">${category.name}</a>
		  	</c:forEach>
		</sup>
		${post.content}
	  </article>
	  <c:if test="${post.previousId ne '-1'}">
	  <a href='<c:url value="/post/${post.previousId}"/>' class="button icon fa-file-text-o"><fmt:message key="post.previous"/></a>
	  </c:if> 
	  <c:if test="${not empty post.nextId}">
	  <a href='<c:url value="/post/${post.nextId}"/>' class="button icon fa-file-text-o"><fmt:message key="post.next"/></a>
	  </c:if>