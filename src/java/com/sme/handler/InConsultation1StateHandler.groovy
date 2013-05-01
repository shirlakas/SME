package com.sme.handler

import dbagent.QueryBuilder
import java.util.Map;


import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Arrival;
import patientflowmonitoring.Event;

class InConsultation1StateHandler extends EventHandler{
	
	@Override
	def process(Map props){
		evnt = props['event']
		String previousEvent = patient.lastEventReceived
		//physicianId = props['Physician_ID']
		locationId = props['Location_ID']
		unitId=props['Unit_ID']
		
		timestamp = props['timestamp']
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("timestamp is " + startTimestamp)
		
		/* This Event Handler is called for patients
		 * in the IN_CONSULTATION1 state
		 * If the event received is PhysicianOutED
		 * change the Patient state to IN_BED_ED
		 */
		if(evnt == 'OrderRequest'){
			event.eventName = EventName.OrderRequest
			patient.pendingOrders += 1
			log.info(patientId + " arrived at InBedEDStateHandler") // for logging purpose only
			patientStateId='IN_PHYS_INIT_ASSESS'
			orderNum=props['Order_ID']
			orderType=props['OrderType']
			providerId=props['Provider_ID']
			locationId = patient.getRoomID();
			unitId='ED'
			
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
		}
		else if(evnt == 'PhysicianOutED'){
			event.eventName = EventName.PhysicianOutED
			patientId=patient.getPatientID()
			providerId=props['Physician_ID']
			
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
			log.info(patientId + " arrived at InConsultation1StateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.IN_BED_ED
			updatePatientState(patientState)
			patientStateId='IN_BED_ED'
			
			data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb1=new QueryBuilder();
				int ii=qb1.buildQueryPatientStateFact(data);
			
		}
		
		if((evnt == 'PhysicianOutED')&&(previousEvent == 'OrderRequest')){
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_ORDERS_EXECUTION
			patientState.target = 30
			updatePatientState(patientState)
			patientStateId='WAIT_FOR_ORDERS_EXECUTION'
			providerId=props['Physician_ID']
			patientId=patient.getPatientID()
			
			String[] data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb1=new QueryBuilder();
				int ii=qb1.buildQueryPatientStateFact(data);
		}
	
	}
}




