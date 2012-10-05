package com.sme.handler

import java.util.Map;
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Arrival;
import patientflowmonitoring.Event;

class WaitForBedCWStateHandler extends EventHandler{
	
	@Override
	public Object process(Map props) {
		String evnt = props['event']
		String previousEvent = patient.lastEventReceived
		/* This Event Handler is called for patients
		 * in the WAIT_FOR_BED_CW state
		 * If the event received is PatientAdmittedWithBed
		 * and previous event is not transport request
		 * maintain the Patient state to WAIT_FOR_ORDERS_EXECUTION
		 */
		if((evnt == 'PatientAdmittedWithBed')&&(previousEvent!="PatientTransportRequest")){
			event.eventName = EventName.PatientAdmittedWithBed
			log.info(patientId + " arrived at WaitForBedCWStateHandler") // for logging purpose only
					
			return null;
		}
		else if((evnt == 'PatientAdmittedWithBed')&&(previousEvent=="PatientTransportRequest")){
			event.eventName = EventName.PatientAdmittedWithBed
			log.info(patientId + " arrived at WaitForBedCWStateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_TRANSPORT_CW
			patientState.stateAttributes.put ('LocationId', props['Location_ID'])
			patientState.stateAttributes.put ('UnitId', props['Unit_ID'])
			patientState.target = 15
			updatePatientState(patientState)
			return null;
		}
		else if((evnt == 'PatientTransportRequest')&&(previousEvent=="PatientAdmittedWithBed")){
			event.eventName = EventName.PatientTransportRequest
			log.info(patientId + " arrived at WaitForBedCWStateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_TRANSPORT_CW
			patientState.stateAttributes.put ('LocationId', props['Location_ID'])
			patientState.stateAttributes.put ('UnitId', props['Unit_ID'])
			patientState.target = 15
			updatePatientState(patientState)
			return null;
		}
		else if (evnt=='ProceduresScheduled'){
			event.eventName = EventName.ProceduresScheduled
			patient.procedureStatus = "Scheduled"
			log.info(patientId + " arrived at WaitForBedCWStateHandler") // for logging purpose only
			return null;
		}
	}

}
	
