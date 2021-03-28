import bankmanagement.Account

class BootStrap {

    def init = { servletContext ->
		new Account(accountId: 'mb001', name: 'Mani', emailId: 'manikandan365@yahoo.com', balance: 200).save(failOnError: true)
		new Account(accountId: 'mb002', name: 'Ramya', emailId: 'manikandan365@yahoo.com', balance: 200).save(failOnError: true)
		new Account(accountId: 'mb003', name: 'Mrithika', emailId: 'manikandan365@yahoo.com', balance: 200).save(failOnError: true)
		new Account(accountId: 'mb004', name: 'Emil', emailId: 'manikandan365@yahoo.com', balance: 200).save(failOnError: true)
	}
		
    def destroy = {
    }
}
