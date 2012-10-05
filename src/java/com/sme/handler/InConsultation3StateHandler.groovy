package com.sme.handler

import java.util.Map;
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Arrival;
import patientflowmonitoring.Event;

class InConsultation3StateHandler extends EventHandler{
	
	@Override
	public Object process(Map props) {
		String evnt = props['event']
		/* This Event Handler is called for patients
		 * in the IN_CONSULTATION3 state
		 * If the event received is PhysicianOutCW
		 * change the Patient state to IN_BED_CW
		 */
		if(evnt == 'PhysicianOutCW'){
			event.eventName = EventName.PhysicianOutCW
			
			log.info(patientId + " arrived at InConsultation3StateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.IN_BED_CW
			updatePatientState(patientState)
					
			return null;
		}
		else if(evnt == 'DischargeRequest'){
			event.eventName = EventName.DischargeRequest
			log.info(patientId + " arrived at InConsultation3StateHandler") // for logging purpose only
				
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_DISCHARGE
			patientState.target = 120
			updatePatientState(patientState)
			
			return null;
		}
	
	}
}