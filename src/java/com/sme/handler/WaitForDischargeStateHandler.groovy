package com.sme.handler


import dbagent.QueryBuilder
import java.util.Map;


import patientflowmonitoring.Discharge;
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;

class WaitForDischargeStateHandler extends EventHandler{
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
		
		/* This Event Handler is called for patients
		 * in the WAIT_FOR_DISCHARGE state
		 * If the event received is PatientOutCW
		 * change the Patient state to DISCHARGE_COMPLETED
		 */
		if(evnt == 'PatientOutCW'){
			providerId='Not Defined Num'
			event.eventName = EventName.PatientOutCW
			log.info(patientId + " arrived at WaitForDischargeStateHandler") // for logging purpose only
			
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
				
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.DISCHARGED
			patientState.target = 15
			patient.roomID = ""
			patientState.endTime = createTimeStamp(props['timestamp'])+1
			updatePatientState(patientState)
		
			patientStateId='DISCHARGED'
			
			data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb2=new QueryBuilder();
				int iii=qb2.buildQueryPatientStateFact(data);
			
			def Discharge discharge = new Discharge()
			discharge.setTimeStamp(createTimeStamp(props['timestamp']))
			discharge.save()
		
			return null;
		}
		
	}

}
