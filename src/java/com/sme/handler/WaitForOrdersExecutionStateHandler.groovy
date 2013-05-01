package com.sme.handler

import dbagent.QueryBuilder
import java.util.Map;


import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Arrival;
import patientflowmonitoring.Event;

class WaitForOrdersExecutionStateHandler extends EventHandler{
	
	@Override
	def process(Map props){
		evnt = props['event']
		timestamp = props['timestamp']
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("timestamp is " + startTimestamp)
		
		unitId='ED'
		locationId=patient.getRoomID()
		orderNum=props['Order_ID']
		orderType=props['OrderType']
		providerId=props['Provider_ID']
		/* This Event Handler is called for patients
		 * in the IN_BED_ED state
		 * If the event received is OrderRequest
		 * maintain the Patient state at WAIT_FOR_ORDERS_EXECUTION
		 * If the event received is OrderRequestCompleted
		 * Check if all requests have been processed
		 * and Update Patient state to WAIT_FOR_CONSULTATION2
		 */
		if(evnt == 'OrderRequest'){
			event.eventName = EventName.OrderRequest
			patient.pendingOrders += 1
			log.info(patientId + " arrived at WaitForOrdersExecutionStateHandler") // for logging purpose only
			patientStateId='WAIT_FOR_ORDERS_EXECUTION'
			
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
				QueryBuilder qb=new QueryBuilder();
				int i=qb.buildQueryPatientEventFact(data);
					
			return null;
			}
		else if (evnt =='OrderRequestCompleted' ){
			event.eventName = EventName.OrderRequestCompleted
			patient.ordersProcessed += 1
			log.info(patientId + " arrived at WaitForOrdersExecutionStateHandler") // for logging purpose only
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
						
			if(patient.getPendingOrders() == patient.getOrdersProcessed()){
				def patientState = new PatientState()
				patientState.stateName = PatientStateName.WAIT_FOR_PHYS_RE_ASSESS
				patientState.target = 30
				updatePatientState(patientState)
				
				patientStateId='WAIT_FOR_PHYS_RE_ASSESS'
				data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb1=new QueryBuilder();
				int ii=qb1.buildQueryPatientStateFact(data);
			}
			
			return null;
		}
		
	
	}
}




