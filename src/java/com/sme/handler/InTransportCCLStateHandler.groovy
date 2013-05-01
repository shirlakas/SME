package com.sme.handler

import dbagent.QueryBuilder
import java.util.Map;


import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.BEvent.BEventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Room;
import patientflowmonitoring.RoomState;
import patientflowmonitoring.RoomState.RoomStateName;

class InTransportCCLStateHandler extends EventHandler{
	@Override
	def process(Map props){
		def patentId=patient.patientID
		evnt = props['event']
		unitId=props['Unit_ID']
		locationId=props['Location_ID']
		timestamp = props['timestamp']
		providerId='Tra1234'
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("timestamp is " + startTimestamp)
		
		/* This Event Handler is called for patients
		 * in the WAIT_FOR_TRANSPORT_CCL state
		 * If the event received is PatientInCCL
		 * change the Patient state to IN_BED_CCL
		 */
	
		if(evnt == 'PatientInCCL'){
			event.eventName = EventName.PatientInCCL
			patient.roomID = props['Location_ID']
			
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
			log.info(patientId + " arrived at InTransportCCLStateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateAttributes.put ('Room_ID', props['Location_ID'])
			patientState.stateAttributes.put ('UnitId', 'CCL')
			patientState.stateName = PatientStateName.IN_BED_CCL
			updatePatientState(patientState)
			patientStateId='IN_BED_CCL'
			data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb2=new QueryBuilder();
				int iii=qb2.buildQueryPatientStateFact(data);
				
			return null;
		}
		
	}

}


