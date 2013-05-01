package com.sme.handler

import dbagent.QueryBuilder
import java.util.Map;
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;

class InBedCWStateHandler extends EventHandler{
	@Override
	def process(Map props){
		evnt = props['event']
		def patentId=props['Patient_ID']
		unitId=props['Unit_ID']
		//locationId=props['Location_ID']
		locationId=patient.getRoomID()
		timestamp = props['timestamp']
		providerId=props['Provider_ID']
		
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("timestamp is " + startTimestamp)
		
		/* This Event Handler is called for patients
		 * in the IN_BED_CW state
		 * If the event received is OrderRequest
		 * change the Patient state to WAIT_FOR_ORDERS_EXECUTION
		 */
	      
		if(evnt =='ProceduresScheduled'){
			event.eventName = EventName.ProceduresScheduled
			patient.procedureStatus = "Scheduled"
			providerId='Not Defined Num'
			//locationId=patient.getRoomID()
			log.info(patientId + " arrived at InBedCWStateHandler") // for logging purpose only
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
				
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_PROCEDURES
			updatePatientState(patientState)
			patientStateId='WAIT_FOR_PROCEDURES'
			
			data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb2=new QueryBuilder();
				int iii=qb2.buildQueryPatientStateFact(data);
			
			return null;
		}
		else if(evnt =='PhysicianInCW'){
			event.eventName = EventName.PhysicianInCW
			log.info("${patentId} arrived at InBedCWStateHandler") // for logging purpose only
			patientId=patient.getPatientID()
			patientStateId='IN_CONSULTATION3'
			//locationId=patient.getRoomID()
			providerId=props['Physician_ID']
			
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.IN_CONSULTATION3
			patientState.stateAttributes.put ('PhysicianId', props['Physician_ID'])
			updatePatientState(patientState)
			
			data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb2=new QueryBuilder();
				int iii=qb2.buildQueryPatientStateFact(data);
					
			return null;
			
		}
		else if(evnt =='DischargeRequest'){
			event.eventName = EventName.DischargeRequest
			log.info(patientId + " arrived at InBedCWStateHandler") // for logging purpose only
			patientStateId='WAIT_FOR_DISCHARGE'
			//locationId=patient.getRoomID()
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
				
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_DISCHARGE
			updatePatientState(patientState)
			
			data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb2=new QueryBuilder();
				int iii=qb2.buildQueryPatientStateFact(data);
					
			
			return null;
		}
		else if (evnt == 'TransportOutCW'){
			event.eventName = EventName.TransportOutCW
			log.info("${patentId} arrived at InBedCWStateHandler") // for logging purpose only
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
						
			return null;
			
		}
	}

}
