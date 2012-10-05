package patientflowmonitoring

class MessageController {
	
	def jmsService
	def scaffold = true
	
	def save = {
		//jmsService.send(queue:'msg.new',params.body)
		jmsService.send(queue:'SME_Event',params.body)
		flash.message = "Message queued for persistence"
		redirect(action: "list")
	}
	
	def save2 = {
		jmsService.send(queue:'SME_Event',params.body)
		render(text:"Ok", contentType:"text/html",encoding:"UTF-8")
	}
	
	def sendCEP = {
		jmsService.send(queue:'CEP_Event',params.body)
		flash.message = "Message queued for persistence"
		//render(text:"Ok", contentType:"text/html",encoding:"UTF-8")
	}
	
	def update = {
		jmsService.send(queue:'msg.update',[id:params.id,body:params.body])
		flash.message = "Message queued for update"
		redirect(action: "list")
	}
}
