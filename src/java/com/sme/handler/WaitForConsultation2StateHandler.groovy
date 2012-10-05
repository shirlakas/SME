package com.sme.handler

import java.util.Map;
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Arrival;
import patientflowmonitoring.Event;	

class WaitForConsultation2StateHandler extends EventHandler{
	
	@Override
	public Object process(Map props) {
		String evnt = props['event']
		/* This Event Handler is called for patients
		 * in the WAIT_FOR_CONSULTATION2 state
		 * If the event received is PhysicianInED
		 * change the Patient state to IN_CONSULTATION2
		 */
		if(evnt == 'PhysicianInED'){
			event.eventName = EventName.PhysicianInED
			
			log.info(patientId + " arrived at WaitForConsultation2StateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.IN_CONSULTATION2
			patientState.stateAttributes.put ('PhysicianId', props['Physician_ID'])
			updatePatientState(patientState)
					
			return null;
		}
	
	}
}




