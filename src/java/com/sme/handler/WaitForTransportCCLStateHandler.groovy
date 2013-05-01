package com.sme.handler

import dbagent.QueryBuilder
import java.util.Map;


import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.Event;
import patientflowmonitoring.BEvent.BEventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Room;
import patientflowmonitoring.RoomState;
import patientflowmonitoring.RoomState.RoomStateName;

class WaitForTransportCCLStateHandler extends EventHandler{
	
	@Override
	def process(Map props){
		
		def patentId=patient.patientID
		evnt = props['event']
		unitId=props['Unit_ID']
		locationId=props['Location_ID']
		timestamp = props['timestamp']
		providerId=props['Provider_ID']
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("timestamp is " + startTimestamp)
		
		//log.info("last event received is ${previousEvent}")
		/* This Event Handler is called for patients
		 * in the WAIT_FOR_TRANSPORT_CCL state
		 * If the event received is TransportInED
		 * and previous event is not transport request
		 * change the Patient state to IN_TRANSPORT_CW
		 */
		 if(evnt == 'PatientOutCW'){
			event.eventName = EventName.PatientOutCW
			log.info(patientId + " arrived at WaitForTransportCCLStateHandler") // for logging purpose only
			patientStateId='IN_TRANSPORT_CCL'
			unitId='Not defined '
			locationId='UnassignedCW'
			providerId='Tra1234'
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
				def patientState = new PatientState()
				patientState.stateName = PatientStateName.IN_TRANSPORT_CCL
				patientState.target = 15
				updatePatientState(patientState)
				
				
				data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb2=new QueryBuilder();
				int iii=qb2.buildQueryPatientStateFact(data);			
				
		
			return null;
		}		
	}

}


