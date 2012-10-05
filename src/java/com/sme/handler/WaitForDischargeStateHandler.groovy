package com.sme.handler


import java.util.Map;
import patientflowmonitoring.Discharge;
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;

class WaitForDischargeStateHandler extends EventHandler{
	@Override
	public Object process(Map props) {
		String evnt = props['event']
		def patentId=props['Patient_ID']
		/* This Event Handler is called for patients
		 * in the WAIT_FOR_DISCHARGE state
		 * If the event received is PatientOutCW
		 * change the Patient state to DISCHARGE_COMPLETED
		 */
		if(evnt == 'PatientOutCW'){
			event.eventName = EventName.PatientOutCW
			log.info(patientId + " arrived at WaitForDischargeStateHandler") // for logging purpose only
				
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.DISCHARGED
			patientState.target = 15
			patient.roomID = ""
			patientState.endTime = createTimeStamp(props['timestamp'])+1
			updatePatientState(patientState)
		
			def Discharge discharge = new Discharge()
			discharge.setTimeStamp(createTimeStamp(props['timestamp']))
			discharge.save()
		
			return null;
		}
		else if(evnt=='PhysicianOutCW'){
			event.eventName = EventName.PhysicianOutCW
			log.info("${patentId} arrived at WaitForDischargeStateHandler") // for logging purpose only
						
			return null;
		}
	}

}
