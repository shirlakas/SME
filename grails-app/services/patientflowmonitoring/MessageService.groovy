package patientflowmonitoring

import java.util.Date;
import java.util.Map
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes;
import org.springframework.context.*;
import com.sme.handler.EventHandler
import patientflowmonitoring.Event;
import grails.plugin.jms.*
import patientflowmonitoring.Patient.*;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.PatientState;

class MessageService {
	// MessageService is listening to the JMS queue
	
	def jmsService

    static transactional = true
	static expose = ['jms']
	
	def patientService
	def roomService
	def monitorService
	def grailsApplication
	def Patient patient
	def Room room
	def Event event
	
    def serviceMethod() {

    }
	
	@Queue(name='SME_Event')
	def SME_EventArrive(msg){
		
		log.info('what I received is ' + msg)
		def ctx = grailsApplication.mainContext
		def props = processMsg(msg)
		def patientId = props['Patient_ID']
		def	physicianId = props['Physician_ID']
		def	transportId = props['Transport_ID']
		def	houseKeepingId = props['HouseKeeping_ID']
		def roomId = props['Location_ID']
		String eventn=props['event']
		//For Handling the Room events
		if(((roomId)&&(!patientId)&&(!physicianId)&&(!transportId))||(eventn=="PatientAdmittedWithBed")||(houseKeepingId)){
			room = Room.findByRoomID(roomId)
			if (!room){
				EventHandler h = ctx.getBean("nobedstateHandler")
				log.info("h=" + h)
				h.handle (props)		
			}
			else{
				String r_state = room.getCurrentState()
				if(r_state==null){
					r_state=""
				}
				log.info("current room state is ${r_state}")
				switch (r_state){
					case "":
						EventHandler h = ctx.getBean("nobedstateHandler")
						log.info("h=" + h)
						h.handle (props)
						break
					case "BED_AVAILABLE":
						EventHandler h = ctx.getBean("bedavailablestateHandler")
						log.info("h=" + h)
						h.handle (props)
						break
					
					case "BED_ASSIGNED":
						EventHandler h = ctx.getBean("bedassignedstateHandler")
						log.info("h=" + h)
						h.handle (props)
						break
						
					case "WAIT_FOR_BED_CLEANUP":
						EventHandler h = ctx.getBean("waitforbedcleanupstateHandler")
						log.info("h=" + h)
						h.handle (props)
						break
						
					case "IN_CLEANUP":
						EventHandler h = ctx.getBean("bedincleanupstateHandler")
						log.info("h=" + h)
						h.handle (props)
						break
				}
				
			}
		}
		// For handling the patient events
		if(patientId){
			patient = Patient.findByPatientID(patientId)
			if (!patient){
				EventHandler h = ctx.getBean("nostateHandler")
				log.info("h=" + h)
				h.handle (props)		
			}
			else{
			String state = patient.getCurrentState()
			log.info("current patient state is ${state}")
			switch (state) {
				case 'TRIAGED':
					EventHandler h = ctx.getBean("triagedstateHandler")
					log.info("h=" + h)
					h.handle (props)
					break
				case 'WAIT_FOR_CONSULTATION1':
					EventHandler h = ctx.getBean("waitforconsultation1stateHandler")
					log.info("h=" + h)
					h.handle (props)
					break
				case 'IN_BED_ED':
					EventHandler h = ctx.getBean("inbededstateHandler")
					log.info("h=" + h)
					h.handle (props)
					break
				case 'WAIT_FOR_ORDERS_EXECUTION':
					EventHandler h = ctx.getBean("waitforordersexecutionstateHandler")
					log.info("h=" + h)
					h.handle (props)
					break
				case 'WAIT_FOR_CONSULTATION2':
					EventHandler h = ctx.getBean("waitforconsultation2stateHandler")
					log.info("h=" + h)
					h.handle (props)
					break
				case 'WAIT_FOR_BED_CW':
					EventHandler h = ctx.getBean("waitforbedcwstateHandler")
					log.info("h=" + h)
					h.handle (props)
					break
				case 'WAIT_FOR_TRANSPORT_CW':
					EventHandler h = ctx.getBean("waitfortransportcwstateHandler")
					log.info("h=" + h)
					h.handle (props)
					break
				case 'IN_TRANSPORT_CW':
					EventHandler h = ctx.getBean("intransportcwstateHandler")
					log.info("h=" + h)
					h.handle (props)
					break
				case 'IN_BED_CW':
					EventHandler h = ctx.getBean("inbedcwstateHandler")
					log.info("h=" + h)
					h.handle (props)
					break
				case 'WAIT_FOR_PROCEDURES':
					EventHandler h = ctx.getBean("waitforproceduresstateHandler")
					log.info("h=" + h)
					h.handle (props)
					break
					
				case 'WAIT_FOR_TRANSPORT_CCL':
					EventHandler h = ctx.getBean("waitfortransportcclstateHandler")
					log.info("h=" + h)
					h.handle (props)
					break
				case 'IN_TRANSPORT_CCL':
					EventHandler h = ctx.getBean("intransportcclstateHandler")
					log.info("h=" + h)
					h.handle (props)
					break
				case 'IN_BED_CCL':
					EventHandler h = ctx.getBean("inbedcclstateHandler")
					log.info("h=" + h)
					h.handle (props)
					break
				case ['IN_PROCEDURE_ANGIOGRAM','IN_PROCEDURE_PCI']:
					EventHandler h = ctx.getBean("inprocedurestateHandler")
					log.info("h=" + h)
					h.handle (props)
					break
				case 'IN_CONSULTATION3':
					EventHandler h = ctx.getBean("inconsultation3stateHandler")
					log.info("h=" + h)
					h.handle (props)
					break
				case 'WAIT_FOR_DISCHARGE':
					EventHandler h = ctx.getBean("waitfordischargestateHandler")
					log.info("h=" + h)
					h.handle (props)
					break
				case ['DISCHARGED']:
					EventHandler h = ctx.getBean("nostateHandler")
					log.info("h=" + h)
					h.handle (props)	
					break
				}			
			
			}
		}
		if(physicianId){
			patient = Patient.findByRoomID(roomId)
			if(patient){
				String state = patient.getCurrentState()
				log.info("current patient state is ${state}")
				switch (state) {
					case 'WAIT_FOR_CONSULTATION1':
						EventHandler h = ctx.getBean("waitforconsultation1stateHandler")
						log.info("h=" + h)
						h.handle (props)
						break
					case 'IN_CONSULTATION1':
						EventHandler h = ctx.getBean("inconsultation1stateHandler")
						log.info("h=" + h)
						h.handle (props)
						break
					case 'WAIT_FOR_CONSULTATION2':
						EventHandler h = ctx.getBean("waitforconsultation2stateHandler")
						log.info("h=" + h)
						h.handle (props)
						break
					case 'IN_CONSULTATION2':
						EventHandler h = ctx.getBean("inconsultation2stateHandler")
						log.info("h=" + h)
						h.handle (props)
						break
					case 'IN_BED_CW':
						EventHandler h = ctx.getBean("inbedcwstateHandler")
						log.info("h=" + h)
						h.handle (props)
						break
					case 'IN_CONSULTATION3':
						EventHandler h = ctx.getBean("inconsultation3stateHandler")
						log.info("h=" + h)
						h.handle (props)
						break
					case 'WAIT_FOR_DISCHARGE':
						EventHandler h = ctx.getBean("waitfordischargestateHandler")
						log.info("h=" + h)
						h.handle (props)
						break
					}
				}
		}
		if(transportId){
			patient = Patient.findByRoomID(roomId)
			if(patient){
				String state = patient.getCurrentState()
				log.info("current patient state is ${state}")
				switch (state) {
					case 'WAIT_FOR_TRANSPORT_CW':
						EventHandler h = ctx.getBean("waitfortransportcwstateHandler")
						log.info("h=" + h)
						h.handle (props)
						break
					case 'IN_TRANSPORT_CW':
						EventHandler h = ctx.getBean("intransportcwstateHandler")
						log.info("h=" + h)
						h.handle (props)
						break
					case 'WAIT_FOR_TRANSPORT_CCL':
						EventHandler h = ctx.getBean("waitfortransportcclstateHandler")
						log.info("h=" + h)
						h.handle (props)
						break
					case 'IN_TRANSPORT_CCL':
						EventHandler h = ctx.getBean("intransportcclstateHandler")
						log.info("h=" + h)
						h.handle (props)
						break
						}
				 }
			}
		}
	
	def processMsg(msg){
		def parts = msg.split(',')
		def props = [:]
		parts.each{
			def t = it.split(":")
			props.put (t.getAt (0), t.getAt(1))
		}
		return props
	}

	def Date createTimeStamp(String ts){
		if (ts.startsWith("*")){
			def c= new GregorianCalendar()
			def year = c.get(Calendar.YEAR).toString()
			def month = (c.get(Calendar.MONTH)+1).toString().padLeft(2,'0')
			def date = c.get(Calendar.DATE).toString()
			ts=ts.replace("*", "${year}-${month}-${date}")
		}
		return Date.parse ("yyyy-MM-dd/HH-mm-ss", ts)
	}
}

