import grails.plugins.springsecurity.SpringSecurityService;
import zw.bangsoft.twitter.auth.Role;
import zw.bangsoft.twitter.auth.User;
import zw.bangsoft.twitter.auth.UserRole;

class BootStrap {

    def init = { servletContext ->
		
		def springSecurityService
		
		if (!User.count()) {
			
			def userRole = new Role(authority: "USER_ROLE").save()
			
			def password = "pass"
			
			[ jeff: "Jeff Brown", graeme: "Graeme Rocher", burt: "Burt Burkwith" ].each { userName, realName ->
			
				def user = new User(username: userName, realName: realName, password: password, enabled: true).save()
				
				UserRole.create(user, userRole, true)
			}
		}
    }
	
    def destroy = {
    }
}
