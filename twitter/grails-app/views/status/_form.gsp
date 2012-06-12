<%@ page import="zw.co.bangsoft.twitter.model.Status" %>



<div class="fieldcontain ${hasErrors(bean: statusInstance, field: 'author', 'error')} required">
	<label for="author">
		<g:message code="status.author.label" default="Author" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="author" name="author.id" from="${zw.bangsoft.twitter.auth.User.list()}" optionKey="id" required="" value="${statusInstance?.author?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: statusInstance, field: 'message', 'error')} ">
	<label for="message">
		<g:message code="status.message.label" default="Message" />
		
	</label>
	<g:textField name="message" value="${statusInstance?.message}"/>
</div>

