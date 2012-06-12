<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="layout" content="main"/>
<title>Insert title here</title>
</head>
<body>
  <div class="body">
  	<g:each var="user" in="${searchResult?.results}">
  		<div id="name">
  			${user.realName} <g:link controller="status" 
  									action="follow" id="${user.id}">follow</g:link>
  		</div>
  	</g:each>
  </div>
</body>
</html>