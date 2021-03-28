package bankmanagement

class PaymentController {
	def paymentService
	def mailService

	def index() {
		redirect action: 'accounts'
	}

	def accounts() {
		[accounts: Account.list(), error: params.error]
	}

	def transactions() {
		if(params.account) {
			Account account = Account.get(params.account as Long)
			[accounts: Account.list(), selectedAccount: account, transactions: Transaction.findAllByReceivedByOrReceivedFrom(account, account)]
		} else {
			redirect(action: "accounts", params: [error: 'Choose a valid account'])
		}
	}

	def pay() {
		[accounts: Account.list(), error: params.error, success: params.success]
	}

	def transfer() {
		String error = ''
		Double amount = params.amount as Double

		Account fromAccount = Account.get(params.fromAccount as Long)
		Account toAccount = Account.get(params.toAccount as Long)

		if( fromAccount == toAccount) {
			error = 'Same accounts cant be selected'
		}

		try {
			if (fromAccount && toAccount && amount > 0 && !error) {
				paymentService.deductAmount(fromAccount, amount)
				paymentService.addAmount(toAccount, amount)

				paymentService.saveTransaction(fromAccount.refresh(), toAccount.refresh(), amount)

				mailService.sendMail {
					to fromAccount.emailId, toAccount.emailId
					from "manikandan365@yahoo.com"
					subject 'Amount transferred'
					text 'Amount transferred'
				}
			} else {
				error = 'select valid accounts and amount'
			}
		} catch (Exception e) {
			error = e.getMessage()
		}

		if (error) {
			redirect(action: "pay", params: [error: error])
		} else {
			redirect(action: "pay", params: [success: 'Amount transferred succesfully'])
		}
	}
}
