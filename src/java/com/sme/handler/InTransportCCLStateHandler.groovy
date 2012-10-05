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

class InTransportCCLStateHandler extends EventHandler{
	@Override
	public Object process(Map props) {
		String evnt = props['event']
		
		/* This Event Handler is called for patients
		 * in the WAIT_FOR_TRANSPORT_CCL state
		 * If the event received is TransportInCCL
		 * and previous event is PatientInCCL
		 * change the Patient state to IN_BED_CCL
		 */
		if(evnt == 'TransportInCCL'){
			event.eventName = EventName.TransportInCCL
			log.info(patientId + " arrived at InTransportCCLStateHandler") // for logging purpose only
					
			return null;
		}
		else if(evnt == 'TransportOutCCL'){
			event.eventName = EventName.TransportOutCCL
			log.info(patientId + " arrived at InTransportCCLStateHandler") // for logging purpose only
					
			return null;
		}
		else if(evnt == 'PatientInCCL'){
			event.eventName = EventName.PatientInCCL
			patient.roomID = props['Location_ID']
			
			log.info(patientId + " arrived at InTransportCCLStateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateAttributes.put ('Room_ID', props['Location_ID'])
			patientState.stateAttributes.put ('UnitId', props['Unit_ID'])
			patientState.stateName = PatientStateName.IN_BED_CCL
			updatePatientState(patientState)
				
			return null;
		}
		
	}

}


