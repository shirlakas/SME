package com.sme.handler

import java.util.Map;
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Arrival;
import patientflowmonitoring.Event;

class WaitForOrdersExecutionStateHandler extends EventHandler{
	
	@Override
	public Object process(Map props) {
		String evnt = props['event']
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
			return null;
			}
		else if (evnt =='OrderRequestCompleted' ){
			event.eventName = EventName.OrderRequestCompleted
			patient.ordersProcessed += 1
			log.info(patientId + " arrived at WaitForOrdersExecutionStateHandler") // for logging purpose only
						
			if(patient.pendingOrders == patient.ordersProcessed){
				def patientState = new PatientState()
				patientState.stateName = PatientStateName.WAIT_FOR_CONSULTATION2
				patientState.target = 30
				updatePatientState(patientState)
			}
			return null;
		}
	
	}
}




