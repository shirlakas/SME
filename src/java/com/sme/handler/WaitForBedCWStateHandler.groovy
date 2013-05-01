package com.sme.handler

import dbagent.QueryBuilder
import java.util.Map;


import patientflowmonitoring.Admission;
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Arrival;
import patientflowmonitoring.Event;

class WaitForBedCWStateHandler extends EventHandler{
	
	@Override
	def process(Map props){
		 evnt = props['event']
		String previousEvent = patient.lastEventReceived
		unitId=props['Unit_ID']
		locationId=props['Location_ID']
		timestamp = props['timestamp']
		providerId=props['Provider_ID']
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("timestamp is " + startTimestamp)
		
		
		/* This Event Handler is called for patients
		 * in the WAIT_FOR_BED_CW state
		 * If the event received is PatientAdmittedWithBed
		 * and previous event is not transport request
		 * maintain the Patient state to WAIT_FOR_ORDERS_EXECUTION
		*/
		if(evnt == 'PatientAdmittedWithBed'){
			event.eventName = EventName.PatientAdmittedWithBed
			providerId='Not Defined Num'
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
			log.info(patientId + " arrived at WaitForBedCWStateHandler") // for logging purpose only
			
			roomStateId='BED_ASSIGNED'
			 data=[locationId,roomStateId,evnt,providerId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Room Events
			
				QueryBuilder qb1=new QueryBuilder();
				int ii=qb1.buildQueryRoomStateFact(data);
				
			def Admission admission = new Admission()
			admission.setTimeStamp(createTimeStamp(props['timestamp']))
			admission.save()
			
			
			if(previousEvent=="PatientTransportRequest"){
				def patientState = new PatientState()
				patientState.stateName = PatientStateName.WAIT_FOR_TRANSPORT_CW
				patientState.stateAttributes.put ('LocationId', props['Location_ID'])
				patientState.stateAttributes.put ('UnitId', props['Unit_ID'])
				patientState.target = 15
				updatePatientState(patientState)
				providerId='Not Defined Num'
				patientStateId='WAIT_FOR_TRANSPORT_CW'
				
				data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb2=new QueryBuilder();
				int iii=qb2.buildQueryPatientStateFact(data);
			}
			return null;
		}
		else if(evnt == 'PatientTransportRequest'){
			event.eventName = EventName.PatientTransportRequest
			locationId=patient.getRoomID()
		
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
			log.info(patientId + " arrived at WaitForBedCWStateHandler") // for logging purpose only
			if(previousEvent=="PatientAdmittedWithBed"){
				def patientState = new PatientState()
				patientState.stateName = PatientStateName.WAIT_FOR_TRANSPORT_CW
				patientState.stateAttributes.put ('UnitId', props['Unit_ID'])
				patientState.target = 15
				updatePatientState(patientState)
				
				patientStateId='WAIT_FOR_TRANSPORT_CW'
				
			 data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb1=new QueryBuilder();
				int ii=qb1.buildQueryPatientStateFact(data);
			}
			return null;
		}
		else if(evnt=='ProceduresScheduled'){
			event.eventName = EventName.ProceduresScheduled
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
			patient.procedureStatus = "Scheduled"
			log.info(patientId + " arrived at WaitForBedCWStateHandler") // for logging purpose only
			return null;
		}
	}

}
	
