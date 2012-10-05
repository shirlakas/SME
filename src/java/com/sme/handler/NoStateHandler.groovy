package com.sme.handler

import java.util.Map;
import patientflowmonitoring.Patient
import patientflowmonitoring.PatientState;
import patientflowmonitoring.PatientState.PatientStateName
import patientflowmonitoring.Event;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.Arrival;


class NoStateHandler extends EventHandler {
	
	def process(Map props){
		String evnt = props['event']
		/* This Event Handler is called for new patients 
		 * who had no previous state
		 * If the event received is TriageScore 
		 * change the Patient state to TRIAGED
		 */
		if(evnt =='TriageScore'){
			event.eventName = EventName.Triage  // assignment the event name value, which must be a predefined value in Event.EventName
			patient.roomID = 'AssessmentRoom'
						
			log.info(patientId + " arrived at NoStateHandler") // for logging purpose only
		
			def patientState = new PatientState()				// Since this event will cause the state change of the patient, the following three lines are for updating patient state
			patientState.stateName = PatientStateName.TRIAGED
			patientState.target = 30
			updatePatientState(patientState)
		
			def Arrival arrival = new Arrival()					// This event will add a new Arrival object into the PFM system for statistics. 
			arrival.setTimeStamp(createTimeStamp(props['timestamp']))
			arrival.save()

			return null;	//No return value is needed
		}
	}

}
