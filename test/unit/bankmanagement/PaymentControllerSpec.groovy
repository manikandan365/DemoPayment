package bankmanagement

import grails.plugin.mail.MailService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(PaymentController)
@Mock([Account, Transaction])
class PaymentControllerSpec extends Specification {
    def paymentService
    def mailService

    def setup() {
        paymentService = Mock(PaymentService)
        controller.paymentService = paymentService

        mailService = Mock(MailService)
        controller.mailService = mailService
    }

    def cleanup() {
    }

    def "When transactions are requested, they are rendered"() {
        given:
        Account account1 = new Account(id: 1).save(validate: false)
        Account account2 = new Account(id: 2).save(validate: false)
        and:
        Transaction transaction = new Transaction(receivedBy: account1, receivedFrom: account2, amount: 50).save(validate: false)
        and:
        params.account = 1
        when:
        def response = controller.transactions()
        then:
        response.accounts.size() == 2
        response.selectedAccount == account1
        response.transactions == [transaction]
    }

    def "when account is not chosen, valid error message is displayed"() {
        when:
        controller.transactions()
        then:
        response.redirectUrl == '/payment/accounts?error=Choose+a+valid+account'
    }

    def "Cannot transfer amount between same accounts"() {
        given:
        Account fromAccount = new Account(id: 1).save(validate: false)
        and:
        params.fromAccount = fromAccount.id
        params.toAccount = fromAccount.id
        when:
        controller.transfer()
        then:
        response.redirectUrl == '/payment/pay?error=select+valid+accounts+and+amount'
    }

    @Unroll
    def "Cannot transfer amount less than 0"() {
        given:
        Account fromAccount = new Account(id: 1).save(validate: false)
        Account toAccount = new Account(id: 2).save(validate: false)
        and:
        params.fromAccount = fromAccount.id
        params.toAccount = toAccount.id
        params.amount = amount
        when:
        controller.transfer()
        then:
        response.redirectUrl == '/payment/pay?error=select+valid+accounts+and+amount'
        where:
        amount << [0, -10]
    }

    def "Handled when service throws exception"() {
        given:
        Account fromAccount = new Account(id: 1).save(validate: false)
        Account toAccount = new Account(id: 2).save(validate: false)
        and:
        params.fromAccount = fromAccount.id
        params.toAccount = toAccount.id
        params.amount = 100
        when:
        controller.transfer()
        then:
        1*paymentService.deductAmount(_, _) >> { throw new Exception('error')}
        response.redirectUrl == '/payment/pay?error=error'
    }

    def "when succesfully transfered, amount is deducted and credited to relevant accounts"() {
        given:
        Account fromAccount = new Account(id: 1, accountId:'1').save(validate: false)
        Account toAccount = new Account(id: 2, accountId: '2').save(validate: false)
        and:
        params.fromAccount = fromAccount.id
        params.toAccount = toAccount.id
        params.amount = 100
        when:
        controller.transfer()
        then:
        1*paymentService.deductAmount(fromAccount, 100)
        1*paymentService.addAmount(toAccount,100)
        noExceptionThrown()
    }
}
