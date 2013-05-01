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

class InTransportCWStateHandler extends EventHandler{
	@Override
	def process(Map props){
		evnt = props['event']
		unitId=props['Unit_ID']
		locationId=props['Location_ID']
		timestamp = props['timestamp']
		//providerId=props['Provider_ID']
		providerId='Not Defined Num'
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("timestamp is " + startTimestamp)
		
		/* This Event Handler is called for patients
		 * in the IN_TRANSPORT_CW state
		 * If the event received is PaitentInCW
		 * change the Patient state to IN_BED_CW
		 */
		def status =patient.procedureStatus 
		
		if(evnt=='PatientInCW'){
			event.eventName = EventName.PatientInCW
			patient.roomID = props['Location_ID']
			log.info(patientId + " arrived at InTransportCWStateHandler") // for logging purpose only
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
			if(status == "Scheduled"){
				def patientState = new PatientState()
				patientState.stateAttributes.put ('Room_ID', props['Location_ID'])
				patientState.stateAttributes.put ('UnitId', 'CW')
				patientState.stateName = PatientStateName.WAIT_FOR_PROCEDURES
				updatePatientState(patientState)
				patientStateId='WAIT_FOR_PROCEDURES'
				
				data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb2=new QueryBuilder();
				int iii=qb2.buildQueryPatientStateFact(data);
						
			}
			else{
				def patientState = new PatientState()
				patientState.stateAttributes.put ('Room_ID', props['Location_ID'])
				patientState.stateAttributes.put ('UnitId', 'CW')
				patientState.stateName = PatientStateName.IN_BED_CW
				updatePatientState(patientState)
				patientStateId='IN_BED_CW'
				
				data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb2=new QueryBuilder();
				int iii=qb2.buildQueryPatientStateFact(data);
			}
			return null;
		}
		else if (evnt=='ProceduresScheduled'){
			event.eventName = EventName.ProceduresScheduled
			patient.procedureStatus = "Scheduled"
			log.info(patientId + " arrived at InTransportCWStateHandler") // for logging purpose only
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			return null;
		}
	}

}
