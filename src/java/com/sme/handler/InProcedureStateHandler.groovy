package com.sme.handler


import java.util.Map;
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.BEvent.BEventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Room;
import patientflowmonitoring.RoomState;
import patientflowmonitoring.RoomState.RoomStateName;

import dbagent.QueryBuilder
import java.util.Map;


class InProcedureStateHandler extends EventHandler{
	@Override
	def process(Map props){
		/* This Event Handler is called for patients
		 * in the IN_PROCEDURE ANGIOGRAM or IN_PROCEDURE_PCI state
		 * If the event received is ProcedureCompleted
		 * change the Patient state to IN_BED_CCL
		 */
		evnt = props['event']
		
		unitId='CCL'
		locationId=patient.getRoomID()
		timestamp = props['timestamp']
		providerId=props['Provider_ID']
		
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("timestamp is " + startTimestamp)
		
		if(evnt == 'ProcedureCompleted'){
			event.eventName = EventName.ProcedureCompleted
			patient.procedureStatus = "Completed"
			log.info(patientId + " arrived at InProcedureStateHandler") // for logging purpose only
			
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
		 
			def patientState = new PatientState()
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

