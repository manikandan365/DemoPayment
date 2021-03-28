
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<title>Pay Some Person</title>
</head>

<body>
<p style="color: red">${error}</p>
<p style="color: green">${success}</p>
<g:form controller="payment" action="transfer">
<h2>Pay</h2>
<br/>
<b>From: </b> <g:select name="fromAccount" optionKey="id" from="${accounts}" optionValue="${{ it.accountId }}" noSelection="['': '- Choose account -']"></g:select>
<br/>
<b>To: </b> <g:select name="toAccount" optionKey="id" from="${accounts}" optionValue="${{ it.accountId }}" noSelection="['': '- Choose account -']"></g:select>
</br>
<b>Amount: </b> <g:textField name="amount" />
</br>
<g:submitButton name="submit" value="Submit" style="background: darkgreen"/>
</g:form>

<div>
	<g:form controller="payment" action="accounts">
	<g:submitButton name="submit" value="See Accounts" style="background: #666666"/>
	</g:form>
</div>
</body>
</html>