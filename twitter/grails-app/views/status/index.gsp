<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="layout" content="main"/>
<title>What are you doing?</title>
<g:javascript library="jquery"></g:javascript>
</head>
<body>
  <div class="body">
  
  	<h1>Search For People To Follow</h1>
  	<div class="searchForm">
  		<g:form controller="searchable">
  			<g:textField name="q" value=""/>
  		</g:form>
  	</div>
  	
  	<h1>What are you doing?</h1>
  	<div class="updateStatusForm">
  		<g:formRemote name="updateStatusForm" 
  					url="[action:'updateStatus']"
  					update="messageList" 
  					onSuccess="document.updateStatusForm.message.value=''">
  			<g:textArea name="message" value=""></g:textArea><br/>
  			<g:submitButton name="Update Status"/>
  		</g:formRemote>
  	</div>
  	<div id="messageList">
  		<g:if test="${messages}">
  			<g:render template="messages" collection="${messages}" var="message"></g:render>
  		</g:if>
  	</div>
  </div>
</body>
</html>