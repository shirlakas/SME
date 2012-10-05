package com.sme.handler

import java.util.Map;
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.Event;
import patientflowmonitoring.BEvent.BEventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Room;
import patientflowmonitoring.RoomState;
import patientflowmonitoring.RoomState.RoomStateName;

class WaitForTransportCCLStateHandler extends EventHandler{
	
	@Override
	public Object process(Map props) {
		String evnt = props['event']
		String previousEvent =patient.lastEventReceived
		def patentId=patient.patientID
		log.info("last event received is ${previousEvent}")
		/* This Event Handler is called for patients
		 * in the WAIT_FOR_TRANSPORT_CCL state
		 * If the event received is TransportInED
		 * and previous event is not transport request
		 * change the Patient state to IN_TRANSPORT_CW
		 */
		if(evnt == 'TransportInCW'){
			event.eventName = EventName.TransportInCW
			log.info("${patentId} arrived at WaitForTransportCCLStateHandler") // for logging purpose only
					
			return null;
		}
		else if((evnt == 'PatientOutCW')&&(previousEvent!='TransportOutCW')){
			event.eventName = EventName.PatientOutCW
			log.info(patientId + " arrived at WaitForTransportCCLStateHandler") // for logging purpose only
					
			return null;
		}
		else if((evnt == 'PatientOutCW')&&(previousEvent=='TransportOutCW')){
			event.eventName = EventName.PatientOutCW
			log.info(patientId + " arrived at WaitForTransportCCLStateHandler") // for logging purpose only
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.IN_TRANSPORT_CCL
			patientState.target = 15
			updatePatientState(patientState)
			
			return null;
		}
		else if((evnt =='TransportOutCW')&&(previousEvent!='PatientOutCW')){
			event.eventName = EventName.TransportOutCW
			log.info("${patentId} arrived at WaitForTransportCCLStateHandler") // for logging purpose only
					
			return null;
		}
		else if((evnt =='TransportOutCW')&&(previousEvent=='PatientOutCW')){
			event.eventName = EventName.TransportOutCW
			log.info("${patentId} arrived at WaitForTransportCCLStateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateAttributes.put ('TransportId', props['Transport_ID'])
			patientState.stateAttributes.put ('LocationId', props['Location_ID'])
			patientState.stateAttributes.put ('UnitId', props['Unit_ID'])
			patientState.target = 15
			patientState.stateName = PatientStateName.IN_TRANSPORT_CCL
			updatePatientState(patientState)
			
			return null;
		}
		
	}

}


