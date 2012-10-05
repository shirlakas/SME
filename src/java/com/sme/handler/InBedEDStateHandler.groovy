package com.sme.handler

import java.util.Map;
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Admission;
import patientflowmonitoring.Room;
import patientflowmonitoring.RoomState;
import patientflowmonitoring.RoomState.RoomStateName;
import patientflowmonitoring.BEvent.BEventName;

class InBedEDStateHandler extends EventHandler{
	
	@Override
	public Object process(Map props) {
		String evnt = props['event']
		/* This Event Handler is called for patients
		 * in the IN_BED_ED state
		 * If the event received is OrderRequest
		 * change the Patient state to WAIT_FOR_ORDERS_EXECUTION
		 */
		if(evnt == 'OrderRequest'){
			event.eventName = EventName.OrderRequest
			patient.pendingOrders += 1
			log.info(patientId + " arrived at InBedEDStateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_ORDERS_EXECUTION
			patientState.target = 30
			updatePatientState(patientState)
					
			return null;
		}
		/* If the event received is PatientAdmittedWithNoBed
		* change the Patient state to WAIT_FOR_BED_CW
		*/
		else if(evnt == 'PatientAdmittedWithNoBed'){
			event.eventName = EventName.PatientAdmittedWithNoBed
			log.info(patientId + " arrived at InBedEDStateHandler") // for logging purpose only
			
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
			log.info(patientId + " arrived at InBedEDStateHandler") // for logging purpose only
		
			def Admission admission = new Admission()
			admission.setTimeStamp(createTimeStamp(props['timestamp']))
			admission.save()
			
		}
		else if (evnt=='ProceduresScheduled'){
			event.eventName = EventName.ProceduresScheduled
			patient.procedureStatus = "Scheduled"
			log.info(patientId + " arrived at InBedEDStateHandler") // for logging purpose only
			
		}
	
	}
}




