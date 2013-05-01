package com.sme.handler

import dbagent.QueryBuilder
import java.util.Map;


import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Arrival;
import patientflowmonitoring.Event;

class InConsultation3StateHandler extends EventHandler{
	
	@Override
	def process(Map props){
		def patentId=patient.patientID
		evnt = props['event']
		unitId=props['Unit_ID']
		locationId=props['Location_ID']
		timestamp = props['timestamp']
		providerId=props['Provider_ID']
		String previousEvent = patient.lastEventReceived
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("timestamp is " + startTimestamp)
		
		/* This Event Handler is called for patients
		 * in the IN_CONSULTATION3 state
		 * If the event received is PhysicianOutCW
		 * change the Patient state to IN_BED_CW
		 */
		if(evnt == 'PhysicianOutCW'){
			event.eventName = EventName.PhysicianOutCW
			patientId=patient.getPatientID()
			providerId=props['Physician_ID']
			
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
			log.info(patientId + " arrived at InConsultation3StateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.IN_BED_CW
			updatePatientState(patientState)
			
			patientStateId='IN_BED_CW'
			
			data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb2=new QueryBuilder();
				int iii=qb2.buildQueryPatientStateFact(data);
					
			
		}
		else if(evnt == 'DischargeRequest'){
			event.eventName = EventName.DischargeRequest
			log.info(patientId + " arrived at InConsultation3StateHandler") // for logging purpose only
			
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
				
					
		}
		if((evnt == 'PhysicianOutCW')&&(previousEvent == 'DischargeRequest')){
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_DISCHARGE
			patientState.target = 120
			updatePatientState(patientState)
			
			providerId=props['Physician_ID']
			patientId=patient.getPatientID()
			patientStateId='WAIT_FOR_DISCHARGE'
			
			String[] data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb1=new QueryBuilder();
				int ii=qb1.buildQueryPatientStateFact(data);
		}
	
	}
}