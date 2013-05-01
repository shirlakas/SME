package com.sme.handler

import java.util.Map;
import dbagent.QueryBuilder

import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Arrival;
import patientflowmonitoring.Event;

class WaitForTransportCWStateHandler extends EventHandler{
	
	@Override
	def process(Map props){
		evnt = props['event']
		unitId=props['Unit_ID']
		locationId=props['Location_ID']
		timestamp = props['timestamp']
		providerId=props['Provider_ID']
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("timestamp is " + startTimestamp)
		
		//String previousEvent = patient.lastEventReceived
		/* This Event Handler is called for patients
		 * in the WAIT_FOR_TRANSPORT_CW state
		 * If the event received is TransportInED
		 * and previous event is not transport request
		 * change the Patient state to IN_TRANSPORT_CW
		 */
		
		 if((evnt == 'PatientOutED')||(evnt =='PatientOutCCL')){
			event.eventName = EventName.PatientOutED
			patientStateId='IN_TRANSPORT_CW'
			unitId='Not defined '
			locationId='UnassignedCW'
			providerId='Tra1234'
			log.info(patientId + " arrived at WaitForTransportCWStateHandler") // for logging purpose only
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
				def patientState = new PatientState()
				patientState.target = 15
				patientState.stateName = PatientStateName.IN_TRANSPORT_CW
				updatePatientState(patientState)
				
				
				
				data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb2=new QueryBuilder();
				int iii=qb2.buildQueryPatientStateFact(data);
						
				
		
			return null;
		}

		else if (evnt=='ProceduresScheduled'){
			event.eventName = EventName.ProceduresScheduled
			patient.procedureStatus = "Scheduled"
			log.info(patientId + " arrived at WaitForTransportCWStateHandler") // for logging purpose only
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			return null;
		}
	
	}

}
