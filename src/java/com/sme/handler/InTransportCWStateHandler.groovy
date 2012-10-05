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

class InTransportCWStateHandler extends EventHandler{
	@Override
	public Object process(Map props) {
		String evnt = props['event']
		/* This Event Handler is called for patients
		 * in the IN_TRANSPORT_CW state
		 * If the event received is TransportInCW
		 * and previous event is PaitentInCW
		 * change the Patient state to IN_BED_CW
		 */
		def status =patient.procedureStatus 
		if(evnt == 'TransportInCW'){
			event.eventName = EventName.TransportInCW
			log.info(patientId + " arrived at InTransportCWStateHandler") // for logging purpose only
					
			return null;
		}
		else if(evnt == 'TransportOutCW'){
			event.eventName = EventName.TransportOutCW
			log.info(patientId + " arrived at InTransportCWStateHandler") // for logging purpose only
					
			return null;
		}
		else if((evnt == 'PatientInCW')&&(status == "Scheduled")){
			event.eventName = EventName.PatientInCW
			patient.roomID = props['Location_ID']
			log.info(patientId + " arrived at InTransportCWStateHandler") // for logging purpose only		
			
			def patientState = new PatientState()
			patientState.stateAttributes.put ('Room_ID', props['Location_ID'])
			patientState.stateAttributes.put ('UnitId', props['Unit_ID'])
			patientState.stateName = PatientStateName.WAIT_FOR_PROCEDURES
			updatePatientState(patientState)
				
			return null;
		}
		else if(evnt =='PatientInCW'){
			event.eventName = EventName.PatientInCW
			patient.roomID = props['Location_ID']
			log.info(patientId + " arrived at InTransportCWStateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateAttributes.put ('Room_ID', props['Location_ID'])
			patientState.stateAttributes.put ('UnitId', props['Unit_ID'])
			patientState.stateName = PatientStateName.IN_BED_CW
			updatePatientState(patientState)
				
			return null;
		}
		else if (evnt=='ProceduresScheduled'){
			event.eventName = EventName.ProceduresScheduled
			patient.procedureStatus = "Scheduled"
			log.info(patientId + " arrived at InTransportCWStateHandler") // for logging purpose only
			return null;
		}
	}

}
