<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<title>See transactions</title>
</head>

<body>
<p style="color: red">${error}</p>
<h2>Transactions</h2> </br>

<div>
	<b>Person:</b>

	<g:form controller="payment" action="transactions">
		<g:select name="account" optionKey="id" from="${accounts}" optionValue="${{ it.accountId }}" noSelection="['': '- Choose account -']"></g:select>

		<g:submitButton name="submit" value="Submit" class="btn btn-success"/>
	</g:form>
</div>
</body>
</html>