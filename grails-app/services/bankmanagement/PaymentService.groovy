package bankmanagement

import grails.transaction.Transactional

@Transactional
class PaymentService {

    def deductAmount(Account fromAccount, Double amount) {
		Double balance = fromAccount.balance
		if(balance && amount && balance >= amount) {
			fromAccount.balance = fromAccount.balance - amount
			fromAccount.save(flush: true)
		} else {
			throw new Exception('No sufficient balance in this account')
		}
    }

	def addAmount(Account toAccount, Double amount) {
		toAccount.balance =toAccount.balance + amount
		toAccount.save(flush: true)
	}

	def saveTransaction(Account fromAccount, Account toAccount, Double amount) {
		new Transaction(receivedFrom: fromAccount, receivedBy: toAccount, amount: amount).save(failOnError: true)
	}
}
