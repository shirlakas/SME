package com.sme.handler

import java.util.Map;
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;

class InBedCWStateHandler extends EventHandler{
	@Override
	public Object process(Map props) {
		String evnt = props['event']
		def patentId=props['Patient_ID']
		/* This Event Handler is called for patients
		 * in the IN_BED_CW state
		 * If the event received is OrderRequest
		 * change the Patient state to WAIT_FOR_ORDERS_EXECUTION
		 */
	      
		if(evnt =='ProceduresScheduled'){
			event.eventName = EventName.ProceduresScheduled
			log.info(patientId + " arrived at InBedCWStateHandler") // for logging purpose only
				
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_PROCEDURES
			updatePatientState(patientState)
			
			return null;
		}
		else if(evnt =='PhysicianInCW'){
			event.eventName = EventName.PhysicianInCW
			log.info("${patentId} arrived at InBedCWStateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.IN_CONSULTATION3
			patientState.stateAttributes.put ('PhysicianId', props['Physician_ID'])
			updatePatientState(patientState)
					
			return null;
			
		}
		else if(evnt =='DischargeRequest'){
			event.eventName = EventName.DischargeRequest
			log.info(patientId + " arrived at InBedCWStateHandler") // for logging purpose only
				
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_DISCHARGE
			updatePatientState(patientState)
			
			return null;
		}
		else if (evnt == 'TransportOutCW'){
			event.eventName = EventName.TransportOutCW
			log.info("${patentId} arrived at InBedCWStateHandler") // for logging purpose only
						
			return null;
			
		}
	}

}
