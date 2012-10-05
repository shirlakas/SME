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

import java.util.Map;

class InProcedureStateHandler extends EventHandler{
	@Override
	public Object process(Map props) {
		/* This Event Handler is called for patients
		 * in the IN_BED_CCL state
		 * If the event received is ProcedureStated
		 * change the Patient state to IN_PROCEDURE
		 */
		String evnt = props['event']
		
		if(evnt == 'ProcedureCompleted'){
			event.eventName = EventName.ProcedureCompleted
			log.info(patientId + " arrived at InProcedureStateHandler") // for logging purpose only
		 
		 
			def procedureType = props['Procedure_Type']
			log.info( "procedure type is " + procedureType );
		 
			def patientState = new PatientState()
			patientState.stateAttributes.put ('ProviderId', props['Provider_ID'])
			patientState.stateAttributes.put ('ProcedureType', props['Procedure_Type'])
		 
			patientState.stateName = PatientStateName.IN_BED_CCL
			
			updatePatientState(patientState)
			
			return null;
		}
	}

}

