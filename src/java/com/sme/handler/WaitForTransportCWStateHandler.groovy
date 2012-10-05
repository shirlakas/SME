package com.sme.handler

import java.util.Map;
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Arrival;
import patientflowmonitoring.Event;

class WaitForTransportCWStateHandler extends EventHandler{
	
	@Override
	public Object process(Map props) {
		String evnt = props['event']
		String previousEvent = patient.lastEventReceived
		/* This Event Handler is called for patients
		 * in the WAIT_FOR_TRANSPORT_CW state
		 * If the event received is TransportInED
		 * and previous event is not transport request
		 * change the Patient state to IN_TRANSPORT_CW
		 */
		
		log.info("previous event is  ${previousEvent}") // for logging purpose only
		
		if(evnt == 'TransportInED'){
			event.eventName = EventName.TransportInED
			log.info(patientId + " arrived at WaitForTransportCWStateHandler") // for logging purpose only
					
			return null;
		}
		else if((evnt == 'PatientOutED')&&(previousEvent!='TransportOutED')){
			event.eventName = EventName.PatientOutED
			log.info(patientId + " arrived at WaitForTransportCWStateHandler") // for logging purpose only
					
			return null;
		}
		else if((evnt == 'PatientOutED')&&(previousEvent=='TransportOutED')){
			event.eventName = EventName.PatientOutED
			log.info(patientId + " arrived at WaitForTransportCWStateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateAttributes.put ('LocationId', props['Location_ID'])
			patientState.stateAttributes.put ('UnitId', props['Unit_ID'])
			patientState.target = 15
			patientState.stateName = PatientStateName.IN_TRANSPORT_CW
			updatePatientState(patientState)
			return null;
		}
		else if((evnt =='TransportOutED')&&(previousEvent!='PatientOutED')){
			event.eventName = EventName.TransportOutED
			log.info(patientId + " arrived at WaitForTransportCWStateHandler") // for logging purpose only
					
			return null;
		}
		else if((evnt =='TransportOutED')&&(previousEvent=='PatientOutED')){
			event.eventName = EventName.TransportOutED
			log.info(patientId + " arrived at WaitForTransportCWStateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateAttributes.put ('TransportId', props['Transport_ID'])
			patientState.stateAttributes.put ('LocationId', props['Location_ID'])
			patientState.stateAttributes.put ('UnitId', props['Unit_ID'])
			patientState.target = 15
			patientState.stateName = PatientStateName.IN_TRANSPORT_CW
			updatePatientState(patientState)
			
			return null;
		}
		else if(evnt == 'TransportInCCL'){
			event.eventName = EventName.TransportInCCL
			log.info(patientId + " arrived at WaitForTransportCWStateHandler") // for logging purpose only
					
			return null;
		}
		else if((evnt == 'PatientOutCCL')&&(previousEvent!='TransportOutCCL')){
			event.eventName = EventName.PatientOutCCL
			log.info(patientId + " arrived at WaitForTransportCWStateHandler") // for logging purpose only
					
			return null;
		}
		else if((evnt == 'PatientOutCCL')&&(previousEvent=='TransportOutCCL')){
			event.eventName = EventName.PatientOutCCL
			log.info(patientId + " arrived at WaitForTransportCWStateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.IN_TRANSPORT_CW
			patientState.target = 15
			updatePatientState(patientState)
			return null;
		}
		else if((evnt =='TransportOutCCL')&&(previousEvent!='PatientOutCCL')){
			event.eventName = EventName.TransportOutCCL
			log.info(patientId + " arrived at WaitForTransportCWStateHandler") // for logging purpose only
					
			return null;
		}
		else if((evnt =='TransportOutCCL')&&(previousEvent=='PatientOutCCL')){
			event.eventName = EventName.TransportOutCCL
			log.info(patientId + " arrived at WaitForTransportCWStateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateAttributes.put ('TransportId', props['Transport_ID'])
			patientState.stateAttributes.put ('LocationId', props['Location_ID'])
			patientState.stateAttributes.put ('UnitId', props['Unit_ID'])
			patientState.target = 15
			patientState.stateName = PatientStateName.IN_TRANSPORT_CW
			updatePatientState(patientState)
			
			return null;
		}
		else if (evnt=='ProceduresScheduled'){
			event.eventName = EventName.ProceduresScheduled
			patient.procedureStatus = "Scheduled"
			log.info(patientId + " arrived at WaitForTransportCWStateHandler") // for logging purpose only
			return null;
		}
	
	}

}
