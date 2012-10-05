package com.sme.handler

import java.util.Map;
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Arrival;
import patientflowmonitoring.Event;

class InConsultation1StateHandler extends EventHandler{
	
	@Override
	public Object process(Map props) {
		String evnt = props['event']
		/* This Event Handler is called for patients
		 * in the IN_CONSULTATION1 state
		 * If the event received is PhysicianOutED
		 * change the Patient state to IN_BED_ED
		 */
		if(evnt == 'PhysicianOutED'){
			event.eventName = EventName.PhysicianOutED
			
			log.info(patientId + " arrived at InConsultation1StateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.IN_BED_ED
			updatePatientState(patientState)
					
			return null;
		}
		else if(evnt == 'OrderRequest'){
			event.eventName = EventName.OrderRequest
			patient.pendingOrders += 1
			log.info(patientId + " arrived at InBedEDStateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_ORDERS_EXECUTION
			patientState.target = 30
			updatePatientState(patientState)
					
			return null;
		}
	
	}
}




