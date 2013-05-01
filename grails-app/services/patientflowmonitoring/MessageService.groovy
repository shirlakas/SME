package patientflowmonitoring

import java.util.Date;
import java.util.Map
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes;
import org.springframework.context.*;
import patientflowmonitoring.Event;
import grails.plugin.jms.*
import patientflowmonitoring.Patient.*;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.PatientState;
import org.codehaus.groovy.grails.commons.*

import com.sme.handler.EventHandler;
import com.sme.handler.TriagedStateHandler;

class MessageService {
	// MessageService is listening to the JMS queue
	
	def jmsService

    static transactional = true
	static expose = ['jms']
	
	PFMServerService sendClient
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
		def	houseKeepingId = props['HouseKeeping_ID']
		def procedureType = props['Procedure_Type']
		def unitId = props['Unit_ID']
		def roomId = props['Location_ID']
		def timestamp = props['timestamp']
		String eventn=props['event']
		
		/*********************************** Room State Machine *******************************************/
		//For Handling the Room events
		if(((roomId)&&(!patientId)&&(!physicianId))||(eventn=="PatientAdmittedWithBed")||(houseKeepingId)){
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
		/*********************************** Patient State Machine *******************************************/
		// For handling the patient events
		if(physicianId){
			patient = Patient.findByRoomID(roomId)
			if (patient){
			patientId=patient.getPatientID()
			}
		}
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
			def status= patient.getProcedureStatus()
			String previousEvent = patient.getLastEventReceived()
			switch (state) {
				case 'TRIAGED':
					EventHandler h = ctx.getBean("triagedstateHandler")
					log.info("h=" + h)
					h.handle (props)
					if(eventn == 'PatientInED'){
					WaitForConsultation1Response response = sendClient.waitForConsultation1("$patientId","$roomId","$timestamp")
					}
					break
				case 'WAIT_FOR_PHYS_INIT_ASSESS':
					EventHandler h = ctx.getBean("waitforconsultation1stateHandler")
					log.info("h=" + h)
					h.handle (props)
					if(eventn == 'PhysicianInED'){
						ConsultationStarted1Response response = sendClient.consultationStarted1("$patientId","$physicianId","$roomId","$timestamp")
						}
					break
				case 'IN_PHYS_INIT_ASSESS':
					EventHandler h = ctx.getBean("inconsultation1stateHandler")
					log.info("h=" + h)
					h.handle (props)
					if(eventn == 'PhysicianOutED'){
						ConsultationCompleted1Response response = sendClient.consultationCompleted1("$patientId","$physicianId","$roomId","$timestamp")
					}
					if((eventn == 'PhysicianOutED')&&(previousEvent =='OrderRequest')){
						WaitForOrderExecutionResponse response = sendClient.waitForOrderExecution("$patientId","$timestamp")
					}
					
					break
				case 'IN_BED_ED':
					EventHandler h = ctx.getBean("inbededstateHandler")
					log.info("h=" + h)
					h.handle (props)
					if(eventn =='OrderRequest'){
						WaitForOrderExecutionResponse response = sendClient.waitForOrderExecution("$patientId","$timestamp")
					}
					else if(eventn=='PatientAdmittedWithNoBed'){
						WaitForBedResponse response = sendClient.waitForBed("$patientId","$unitId","$timestamp")
					}
					else if(((eventn=='PatientAdmittedWithBed')&&(previousEvent=='PatientTransportRequest'))||((eventn=='PatientTransportRequest')&&(previousEvent=='PatientAdmittedWithBed'))){
						WaitForTransportResponse response = sendClient.waitForTransport("$patientId","$unitId","$timestamp")
					}
					break
				case 'WAIT_FOR_ORDERS_EXECUTION':
					EventHandler h = ctx.getBean("waitforordersexecutionstateHandler")
					log.info("h=" + h)
					h.handle (props)
					if(eventn =='OrderRequestCompleted'){
					  int pending=patient.getPendingOrders();
					  log.info("pending Orders = " + pending)
					  int processed=patient.getOrdersProcessed();
					  log.info("pending Orders = " + processed)
					  if(pending == processed){
						  roomId = patient.getRoomID();
						  WaitForConsultation2Response response = sendClient.waitForConsultation2("$patientId","$roomId","$timestamp")
						}
					}
					else if(eventn == 'PhysicianOutED'){
						ConsultationCompleted1Response response = sendClient.consultationCompleted1("$patientId","$physicianId","$roomId","$timestamp")
					}
					break
				case 'WAIT_FOR_PHYS_RE_ASSESS':
					EventHandler h = ctx.getBean("waitforconsultation2stateHandler")
					log.info("h=" + h)
					h.handle (props)
					if(eventn == 'PhysicianInED'){
						ConsultationStarted2Response response = sendClient.consultationStarted2("$patientId","$physicianId","$roomId","$timestamp")
					}
					break
				case 'IN_PHYS_RE_ASSESS':
					EventHandler h = ctx.getBean("inconsultation2stateHandler")
					log.info("h=" + h)
					h.handle (props)
					if(eventn == 'PhysicianOutED'){
						ConsultationCompleted2Response response = sendClient.consultationCompleted2("$patientId","$physicianId","$roomId","$timestamp")
					}
					if((eventn == 'PhysicianOutED')&&(previousEvent =='PatientAdmittedWithNoBed')){
						WaitForBedResponse response = sendClient.waitForBed("$patientId","CW","$timestamp")
					}
					break
				case 'WAIT_FOR_BED_CW':
					EventHandler h = ctx.getBean("waitforbedcwstateHandler")
					log.info("h=" + h)
					h.handle (props)
					if(((eventn=='PatientAdmittedWithBed')&&(previousEvent=='PatientTransportRequest'))||((eventn=='PatientTransportRequest')&&(previousEvent=='PatientAdmittedWithBed'))){
						WaitForTransportResponse response = sendClient.waitForTransport("$patientId","$unitId","$timestamp")
					}
					break
				case 'WAIT_FOR_TRANSPORT_CW':
					EventHandler h = ctx.getBean("waitfortransportcwstateHandler")
					log.info("h=" + h)
					h.handle (props) 
					if((eventn=='PatientOutED')||(eventn=='PatientOutCCL')){
						PatientTransportStartedResponse response = sendClient.patientTransportStarted("$patientId","$unitId","$timestamp")
					}
					
					break
				case 'IN_TRANSPORT_CW':
					EventHandler h = ctx.getBean("intransportcwstateHandler")
					log.info("h=" + h)
					h.handle (props)
					if(eventn=='PatientInCW'){
						if(status=="Scheduled"){
							WaitForProceduresResponse response = sendClient.waitForProcedures("$patientId","$unitId","$timestamp")
						}
						else {
							PatientArrivedInBedResponse response = sendClient.patientArrivedInBed("$patientId","$unitId","$roomId","$timestamp")
						}	
					}
					break
				case 'IN_BED_CW':
					EventHandler h = ctx.getBean("inbedcwstateHandler")
					log.info("h=" + h)
					h.handle (props)
					if(eventn=='ProceduresScheduled'){
						WaitForProceduresResponse response = sendClient.waitForProcedures("$patientId","$unitId","$timestamp")	
					}
					else if(eventn =='DischargeRequest'){
						WaitForDischargeResponse response = sendClient.waitForDischarge("$patientId","$unitId","$timestamp")
					}
					else if(eventn =='PhysicianInCW'){
						ConsultationStarted3Response response = sendClient.consultationStarted3("$patientId","$physicianId","$roomId","$timestamp")
					}
					break
				case 'WAIT_FOR_PROCEDURES':
					EventHandler h = ctx.getBean("waitforproceduresstateHandler")
					log.info("h=" + h)
					h.handle (props)
					if(eventn == 'PatientTransportRequest'){
						WaitForTransportResponse response = sendClient.waitForTransport("$patientId","$unitId","$timestamp")
					}
					break
					
				case 'WAIT_FOR_TRANSPORT_CCL':
					EventHandler h = ctx.getBean("waitfortransportcclstateHandler")
					log.info("h=" + h)
					h.handle (props)
					if(eventn=='PatientOutCW'){
						PatientTransportStartedResponse response = sendClient.patientTransportStarted("$patientId","$unitId","$timestamp")
					}
					
					break
				case 'IN_TRANSPORT_CCL':
					EventHandler h = ctx.getBean("intransportcclstateHandler")
					log.info("h=" + h)
					h.handle (props)
					if(eventn=='PatientInCCL'){
						PatientArrivedInBedResponse response = sendClient.patientArrivedInBed("$patientId","$unitId","$roomId","$timestamp")
						}
					break
				case 'IN_BED_CCL':
					EventHandler h = ctx.getBean("inbedcclstateHandler")
					log.info("h=" + h)
					h.handle (props)
					if(eventn == 'PatientTransportRequest'){
						WaitForTransportResponse response = sendClient.waitForTransport("$patientId","$unitId","$timestamp")
					}
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
					if(eventn =='DischargeRequest'){
						WaitForDischargeResponse response = sendClient.waitForDischarge("$patientId","$unitId","$timestamp")
					}
					else if(eventn == 'PhysicianOutCW'){
						ConsultationCompleted3Response response = sendClient.consultationCompleted3("$patientId","$physicianId","$roomId","$timestamp")
					}
					break
				case 'WAIT_FOR_DISCHARGE':
					EventHandler h = ctx.getBean("waitfordischargestateHandler")
					log.info("h=" + h)
					h.handle (props)
					if(eventn == 'PatientOutCW'){
						DischargeCompleted response = sendClient.dischargeCompleted("$patientId",,"$unitId","$timestamp")
					}
					else if(eventn == 'PhysicianOutCW'){
						ConsultationCompleted3Response response = sendClient.consultationCompleted3("$patientId","$physicianId","$roomId","$timestamp")
					}
					break
				case ['DISCHARGED']:
					EventHandler h = ctx.getBean("nostateHandler")
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

