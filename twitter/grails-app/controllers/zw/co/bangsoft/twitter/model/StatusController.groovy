package zw.co.bangsoft.twitter.model

import org.springframework.dao.DataIntegrityViolationException

import zw.bangsoft.twitter.auth.User;

class StatusController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def springSecurityService

    def index() {
		
		[ messages: currentUserTimeline() ]
    
	}

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [statusInstanceList: Status.list(params), statusInstanceTotal: Status.count()]
    }

    def create() {
        [statusInstance: new Status(params)]
    }

    def save() {
        def statusInstance = new Status(params)
        if (!statusInstance.save(flush: true)) {
            render(view: "create", model: [statusInstance: statusInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'status.label', default: 'Status'), statusInstance.id])
        redirect(action: "show", id: statusInstance.id)
    }

    def show() {
        def statusInstance = Status.get(params.id)
        if (!statusInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'status.label', default: 'Status'), params.id])
            redirect(action: "list")
            return
        }

        [statusInstance: statusInstance]
    }

    def edit() {
        def statusInstance = Status.get(params.id)
        if (!statusInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'status.label', default: 'Status'), params.id])
            redirect(action: "list")
            return
        }

        [statusInstance: statusInstance]
    }

    def update() {
        def statusInstance = Status.get(params.id)
        if (!statusInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'status.label', default: 'Status'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (statusInstance.version > version) {
                statusInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'status.label', default: 'Status')] as Object[],
                          "Another user has updated this Status while you were editing")
                render(view: "edit", model: [statusInstance: statusInstance])
                return
            }
        }

        statusInstance.properties = params

        if (!statusInstance.save(flush: true)) {
            render(view: "edit", model: [statusInstance: statusInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'status.label', default: 'Status'), statusInstance.id])
        redirect(action: "show", id: statusInstance.id)
    }

    def delete() {
        def statusInstance = Status.get(params.id)
        if (!statusInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'status.label', default: 'Status'), params.id])
            redirect(action: "list")
            return
        }

        try {
            statusInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'status.label', default: 'Status'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'status.label', default: 'Status'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
	
	def updateStatus() {
		
		def status = new Status(message: params.message)
		status.author = lookupUser()
		status.save()
		def messages = currentUserTimeline()
		render template: 'messages', collection: messages, var: 'message'
	}
	
	private lookupUser() {
		User.get(springSecurityService.principal.id)
	}
	
	private currentUserTimeline() {
		
		def user = lookupUser()
		
		def messages = Status.withCriteria {
			
			author {
				eq 'username', user.username
			}
			
			if (user.followed) {
				inList 'author', user.followed 
			}
			
			maxResults 10
			order 'dateCreated', 'desc'
		}
		
		messages
	}
	
	def follow() {
		def followed = User.get(params.id)
		if (followed) {
			def currentUser = lookupUser()
			currentUser.addToFollowed(followed)
			currentUser.save()
		}
		redirect action: 'index'
	}
}
