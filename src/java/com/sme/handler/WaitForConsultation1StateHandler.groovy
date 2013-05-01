package com.sme.handler

import dbagent.QueryBuilder
import java.util.Map;


import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Arrival;
import patientflowmonitoring.Event;

class WaitForConsultation1StateHandler extends EventHandler{
	
	
	@Override
	def process(Map props){
		evnt = props['event']
		//physicianId = props['Physician_ID']
		locationId = props['Location_ID']
		unitId=props['Unit_ID']
		providerId=props['Physician_ID']
		timestamp = props['timestamp']
		
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("timestamp is " + startTimestamp)
		
		/* This Event Handler is called for patients
		 * in the WAIT_FOR_CONSULTATION1 state
		 * If the event received is PhysicianInED
		 * change the Patient state to IN_CONSULTATION1
		 */
		if(evnt == 'PhysicianInED'){
			event.eventName = EventName.PhysicianInED
			patientId=patient.getPatientID()
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
			log.info(evnt + " arrived at WaitForConsultation1StateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			//patientState.stateName = PatientStateName.IN_CONSULTATION1
			patientState.stateName = PatientStateName.IN_PHYS_INIT_ASSESS
			patientState.stateAttributes.put ('PhysicianId', props['Physician_ID'])
			updatePatientState(patientState)
			patientStateId='IN_PHYS_INIT_ASSESS'
			
			
			data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb1=new QueryBuilder();
				int ii=qb1.buildQueryPatientStateFact(data);
			return null;
		}
	
	}
}




