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

class WaitForProceduresStateHandler extends EventHandler{
	@Override
	def process(Map props){
		String evnt = props['event']
		def patentId=props['Patient_ID']
		unitId=props['Unit_ID']
		locationId='UnassignedCW'
		timestamp = props['timestamp']
		providerId=props['Provider_ID']
		
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("timestamp is " + startTimestamp)
		
		/* This Event Handler is called for patients
		 * in the WAIT_FOR_TRANSPORT_CW state
		 * If the event received is TransportRequest
		 * change the Patient state to WAIT_FOR_TRANSPORT_CCL
		 */
		if(evnt == 'PatientTransportRequest'){
			event.eventName = EventName.PatientTransportRequest
			log.info(patientId + " arrived at WaitForProceduresStateHandler") // for logging purpose only
			
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_TRANSPORT_CCL
			//patientState.stateAttributes.put ('LocationId', props['Location_ID'])
			patientState.stateAttributes.put ('UnitId', props['Unit_ID'])
			patientState.target = 15
			updatePatientState(patientState)
			locationId=patient.getRoomID()
			patientStateId='WAIT_FOR_TRANSPORT_CCL'
			data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb2=new QueryBuilder();
				int iii=qb2.buildQueryPatientStateFact(data);
		
			
			return null;
		}
	}

}
