package com.sme.handler

import org.apache.commons.logging.LogFactory;

import patientflowmonitoring.Patient
import patientflowmonitoring.PatientState;
import patientflowmonitoring.PatientState.PatientStateName
import patientflowmonitoring.Room
import patientflowmonitoring.RoomState;
import patientflowmonitoring.RoomState.RoomStateName
import patientflowmonitoring.Event;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.BEvent;
import patientflowmonitoring.BEvent.BEventName;

abstract class EventHandler {
	
	static final log = LogFactory.getLog(this)
	
	def Event event
	def BEvent event1
	def Patient patient
	def Room room
	String patientId
	String physicianId
	String transportId
	String houseKeepingId
	String roomId
	String eventn
	
	def void handle(Map props){

		pre_process(props)
		process(props)
		post_process()
		
		}
	
	def void pre_process(props){
		patientId = props['Patient_ID']
		physicianId = props['Physician_ID']
		transportId = props['Transport_ID']
		roomId = props['Location_ID']
		eventn =props['event']
		log.info("~~~Event received is ${props}~~~")
		log.info("Patient ID is ${patientId}")
		// if patientId is not null then find the patient else do nothing.
		//if bedId is not null then find the bed
		if (patientId){
			log.info("~~~Creating New Event~~~")
			event = new Event()
			event.eventAttrs = props
			event.timeStamp = createTimeStamp(props['timestamp'])
			patient = Patient.findByPatientID(patientId)
			if (!patient){
				patient = new Patient(patientID:patientId)
				log.info("~~~Patient Doesn't Exist creating new Patient~~~")
				patient.save()
		}
			//patient.save()
		}
		//if physicianId is not null then use find the patient same room
		if ((physicianId)||(transportId)){
			event = new Event()
			event.eventAttrs = props
			event.timeStamp = createTimeStamp(props['timestamp'])
			patient = Patient.findByRoomID(roomId)
		}
		//if bedId is not null then find the bed
		if(((roomId)&&(!patientId)&&(!physicianId)&&(!transportId))||(eventn=='PatientAdmittedWithBed')||(houseKeepingId)){
			event1=new BEvent()
			event1.eventAttrs = props
			event1.timeStamp = createTimeStamp(props['timestamp'])
			log.info("~~~Room ID is ${roomId} ~~~")
			room = Room.findByRoomID(roomId)
			log.info("~~~Room ${room} is found~~~")
			log.info("~~~Bed event attributes are ${event1.eventAttrs} is processed~~~")
			if (!room){
				room = new Room(roomID:roomId)
				log.info("~~~RoomID not found~~~")
				room.save()
			}
		}
		
	}
	
	def void post_process(){
		if((patientId)||(physicianId)||(transportId)){
			if(event.eventName != null){
			patient.appendEvent(event)
			patient.save()
			log.info("~~~event ${event.eventName} is processed~~~")
			}
			
		}
		if(((roomId)&&(!patientId)&&(!physicianId)&&(!transportId))||(houseKeepingId)||(eventn=='PatientAdmittedWithBed')){
			if (event1.eventName != null){
				room.appendEvent(event1)
				room.save()
				log.info("~~~Post process Bed event ${event1.eventName} is processed~~~")
			}
			
		}
	}
	
	
	def abstract process(Map props)
	
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
	
	def void updatePatientState(PatientState ps){
		patient.setCurrentState(ps,createTimeStamp(event.eventAttrs['timestamp']))
	}
	
	def void updateRoomState(RoomState ps){
		room.setCurrentState(ps,createTimeStamp(event1.eventAttrs['timestamp']))
	}
	
}
