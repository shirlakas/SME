package com.sme.handler



import java.util.Map;
import dbagent.QueryBuilder
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Arrival;
import patientflowmonitoring.Event;



import org.springframework.context.*;
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes;
import org.codehaus.groovy.grails.web.context.ServletContextHolder;
import org.codehaus.groovy.grails.commons.*
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import groovy.util.XmlSlurper

import org.w3c.dom.*;



class TriagedStateHandler extends EventHandler{
 	 	
	@Override
	def process(Map props){
		/* This Event Handler is called for patients
		* in the TRIAGED state
		* If the event received is PatientInED
		* change the Patient state to WAIT_FOR_CONSULTATION1
		*/
		evnt = props['event']
		patientId = props['Patient_ID']
		locationId = props['Location_ID']
		unitId='ED'
		timestamp = props['timestamp']
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("timestamp is " + startTimestamp)
		
		
		if(evnt == 'PatientInED'){
			event.eventName = EventName.PatientInED
			
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			log.info(evnt + " arrived at NoStateHandler") // for logging purpose only
		
			patient.roomID = props['Location_ID']
			patientStateId='WAIT_FOR_PHYS_INIT_ASSESS'
			
			log.info(evnt + " arrived at TriagedStateHandler") // for logging purpose only
			log.info("$patientId , $locationId , $timestamp ")
			
			def patientState = new PatientState()
			patientState.stateAttributes.put ('Room_ID', props['Location_ID'])
			patientState.stateAttributes.put ('UnitId', 'ED')
			patientState.stateName = PatientStateName.WAIT_FOR_PHYS_INIT_ASSESS
			patientState.target = 30
			updatePatientState(patientState)
			
			data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb1=new QueryBuilder();
				int ii=qb1.buildQueryPatientStateFact(data);
			return null;
		}
	
	}
	
}
		


