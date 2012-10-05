package com.sme.handler

import java.util.Map;
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.BEvent.BEventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Room;
import patientflowmonitoring.RoomState;
import patientflowmonitoring.RoomState.RoomStateName;

class WaitForProceduresStateHandler extends EventHandler{
	@Override
	public Object process(Map props) {
		String evnt = props['event']
		/* This Event Handler is called for patients
		 * in the WAIT_FOR_TRANSPORT_CW state
		 * If the event received is TransportRequest
		 * change the Patient state to WAIT_FOR_TRANSPORT_CCL
		 */
		if(evnt == 'PatientTransportRequest'){
			event.eventName = EventName.PatientTransportRequest
			log.info(patientId + " arrived at WaitForProceduresStateHandler") // for logging purpose only
					
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_TRANSPORT_CCL
			patientState.stateAttributes.put ('LocationId', props['Location_ID'])
			patientState.stateAttributes.put ('UnitId', props['Unit_ID'])
			patientState.target = 15
			updatePatientState(patientState)
			
			return null;
		}
	}

}
