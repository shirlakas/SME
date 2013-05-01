package com.sme.handler

import java.util.Map;
import dbagent.QueryBuilder
import patientflowmonitoring.Patient
import patientflowmonitoring.PatientState;
import patientflowmonitoring.PatientState.PatientStateName
import patientflowmonitoring.Event;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.Arrival;


class NoStateHandler extends EventHandler {
	
	def process(Map props){
		
		/* This Event Handler is called for new patients 
		 * who had no previous state
		 * If the event received is TriageScore 
		 * change the Patient state to TRIAGED
		 */
		evnt = props['event']
		patientId = props['Patient_ID']
		locationId = 'AssessmentRoom'
		timestamp = props['timestamp']
		CTAS=props['CTAS']
		providerId=props['Provider_ID']
		
		dateVar=timestamp.substring(0, 10)
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("timestamp is " + startTimestamp)
		
		if(evnt =='TriageScore'){
			event.eventName = EventName.Triage  // assignment the event name value, which must be a predefined value in Event.EventName
			patient.roomID = 'AssessmentRoom'
			patientStateId='TRIAGED'
			unitId='ED'
			//Calling DAO for Patient Event
			//patientNum, eventName, providerNum,CTAS,roomNum,orderNum,orderType, procedureName,  startTimestamp, dateId,duration,  currentStateFlag,endTimestamp)
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
				QueryBuilder qb=new QueryBuilder();
				int i=qb.buildQueryPatientEventFact(data);
			log.info(evnt + " arrived at NoStateHandler") // for logging purpose only
		
			def patientState = new PatientState()				// Since this event will cause the state change of the patient, the following three lines are for updating patient state
			patientState.stateName = PatientStateName.TRIAGED
			patientState.target = 30
			updatePatientState(patientState)
		
			def Arrival arrival = new Arrival()					// This event will add a new Arrival object into the PFM system for statistics. 
			arrival.setTimeStamp(createTimeStamp(props['timestamp']))
			arrival.save()
			
			data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb1=new QueryBuilder();
				int ii=qb1.buildQueryPatientStateFact(data);
			return null;	//No return value is needed
		}
	}

}
