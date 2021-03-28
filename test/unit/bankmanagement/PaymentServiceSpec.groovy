package bankmanagement

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(PaymentService)
@Mock(Account)
class PaymentServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    @Unroll
    def "When amount is deducted, balance should be more than requested amount"() {
        given:
        Account account = new Account(balance: 100)
        when:
        service.deductAmount(account, balance)
        then:
        thrown(Exception)
        where:
        balance << [0, 150]
    }

    def "When valid amount to be deducted, amount is reduced from account"() {
        given:
        Account account = new Account(balance: 100).save(validate: false)
        when:
        service.deductAmount(account, 50)
        then:
        account.balance == 50
    }

    def "When valid amount to be transfered, amount is added to account"() {
        given:
        Account account = new Account(balance: 100).save(validate: false)
        when:
        service.addAmount(account, 50)
        then:
        account.balance == 150
    }

}