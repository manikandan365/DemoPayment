<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<title>See transactions</title>
</head>

<body>

<h2>Transactions</h2> </br>

<div>
	<b>Person:</b>


	<g:form controller="payment" action="transactions">
		<g:select name="account" optionKey="id" from="${accounts}" optionValue="${{ it.accountId }}" noSelection="['': '- Choose account -']"></g:select>

		<g:submitButton name="submit" value="Submit"/>
	</g:form>
</div>

<div>
	Account: ${selectedAccount.accountId} <br/>
	Balance: ${selectedAccount.balance}
</div>

<div>
	<table style="width:100%;" border="true">
		<tr>
			<td><p>Received From</p></td>
			<td><p>Received By</p></td>
			<td><p>Amount</p></td>
		</tr>
		<g:each status="i" var="transaction" in="${transactions}">
			<tr>
				<td>${transaction.receivedFrom.accountId}</td>
				<td>${transaction.receivedBy.accountId}</td>
				<td>${transaction.amount}</td>
			</tr>
		</g:each>
	</table>
</div>

<div>
	<g:form controller="payment" action="pay">
		<g:hiddenField name="account" value="${selectedAccount}"/>
		<g:submitButton name="submit" value="Pay"/>
	</g:form>

</div>
</body>
</html>