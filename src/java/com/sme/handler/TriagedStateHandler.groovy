package com.sme.handler

import PFMServerService;

import java.util.Map;
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Arrival;
import patientflowmonitoring.Event;
import patientflowmonitoring.WaitForConsultation1Response;
import patientflowmonitoring.WaitForConsultation1;
import patientflowmonitoring.PFMServerService;
import patientflowmonitoring.*;
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes;
import org.springframework.context.*;


import org.xml.sax.SAXException;
import javax.xml.parsers.*;

//import java.io.IOException;
//import java.util.Map
//import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes;
//import org.codehaus.groovy.grails.web.context.ServletContextHolder
//import org.springframework.context.*;

class TriagedStateHandler extends EventHandler {
	PFMServerService	sendClient
	
	@Override
	public Object process(Map props) {
		String evnt = props['event']
		String patientId = props['Patient_ID']
		String locationId = props['Location_ID']
		String timestamp = props['timestamp']
		/* This Event Handler is called for patients
		 * in the TRIAGED state
		 * If the event received is PatientInED
		 * change the Patient state to WAIT_FOR_CONSULTATION1
		 */
		
		if(evnt == 'PatientInED'){
			event.eventName = EventName.PatientInED
			patient.roomID = props['Location_ID']
		
			log.info(patientId + " arrived at TriagedStateHandler") // for logging purpose only
			log.info("$patientId , $locationId , $timestamp ")
			//WaitForConsultation1Response response = sendClient.waitForConsultation1("$patientId","$locationId","$timestamp")
			
			def patientState = new PatientState()
			patientState.stateAttributes.put ('Room_ID', props['Location_ID'])
			patientState.stateAttributes.put ('UnitId', 'ED')
			patientState.stateName = PatientStateName.WAIT_FOR_CONSULTATION1
			patientState.target = 30
			updatePatientState(patientState)
			//WaitForConsultation1Response response=sendClient.waitForConsultation1("$patientId", "$locationId", "$timestamp")
			//WaitForConsultation1Response response = sendClient.waitForConsultation1("$patientId","$locationId","$timestamp")
			
			return null;
		}
	
	}
}

