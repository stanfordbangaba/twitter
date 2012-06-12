package zw.co.bangsoft.twitter.model

import zw.bangsoft.twitter.auth.User;

class Status {

	String message
	User author
	Date dateCreated
	
    static constraints = {
    }
	
}
