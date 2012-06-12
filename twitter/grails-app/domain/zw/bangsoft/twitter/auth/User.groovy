package zw.bangsoft.twitter.auth

import zw.co.bangsoft.twitter.model.Status;

class User {

	transient springSecurityService
	static searchable = [ only: 'realName' ]
	
	String realName
	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static constraints = {
		realName blank: false
		username blank: false, unique: true
		password blank: false
	}
	
	static hasMany = [ followed: User, status: Status ]

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
}
