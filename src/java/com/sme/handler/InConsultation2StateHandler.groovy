package com.sme.handler

import java.util.Map;
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Event;
import patientflowmonitoring.Admission;

class InConsultation2StateHandler extends EventHandler{
	
	@Override
	public Object process(Map props) {
		String evnt = props['event']
		/* This Event Handler is called for patients
		 * in the IN_CONSULTATION2 state
		 * If the event received is PhysicianOutED
		 * change the Patient state to IN_BED_ED
		 */
		if(evnt == 'PhysicianOutED'){
			event.eventName = EventName.PhysicianOutED
			
			log.info(patientId + " arrived at InConsultation2StateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.IN_BED_ED
			updatePatientState(patientState)
					
			return null;
		}
		else if(evnt == 'PatientAdmittedWithNoBed'){
			event.eventName = EventName.PatientAdmittedWithNoBed
			log.info(patientId + " arrived at InConsutlation2StateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_BED_CW
			patientState.stateAttributes.put ("Unit_ID", props["Unit_ID"])
			patientState.target = 480
			updatePatientState(patientState)
					
			return null;
		}
		/* If the event received is PatientAdmittedWithNoBed
		 * maintain the Patient state
		 */
		else if(evnt == 'PatientAdmittedWithBed'){
			event.eventName = EventName.PatientAdmittedWithBed
			log.info(patientId + " arrived at InConsultation2StateHandler") // for logging purpose only
		
			def Admission admission = new Admission()
			admission.setTimeStamp(createTimeStamp(props['timestamp']))
			admission.save()
		}
		else if (evnt=='ProceduresScheduled'){
			event.eventName = EventName.ProceduresScheduled
			patient.procedureStatus = "Scheduled"
			log.info(patientId + " arrived at InConsultation2StateHandler") // for logging purpose only
			return null;
		}
	
	}
}
	
	