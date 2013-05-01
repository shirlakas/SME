package com.sme.handler

import dbagent.QueryBuilder
import java.util.Map;


import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Event;
import patientflowmonitoring.Admission;

class InConsultation2StateHandler extends EventHandler{
	
	@Override
	def process(Map props){
		String evnt = props['event']
		String previousEvent = patient.lastEventReceived
		locationId = props['Location_ID']
		unitId=props['Unit_ID']
		//providerId=props['Physician_ID']
		timestamp = props['timestamp']
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("timestamp is " + startTimestamp)
		
		
		/* This Event Handler is called for patients
		 * in the IN_CONSULTATION2 state
		 * If the event received is PhysicianOutED
		 * change the Patient state to IN_BED_ED
		 */
		if(evnt == 'PatientAdmittedWithNoBed'){
			event.eventName = EventName.PatientAdmittedWithNoBed
			locationId='UnassignedED'
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
			log.info(patientId + " arrived at InConsultation2StateHandler") // for logging purpose only
			
			return null;
		}
		else if(evnt == 'PatientAdmittedWithBed'){
			event.eventName = EventName.PatientAdmittedWithBed
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			log.info(patientId + " arrived at InConsultation2StateHandler") // for logging purpose only
		
			def Admission admission = new Admission()
			admission.setTimeStamp(createTimeStamp(props['timestamp']))
			admission.save()
		}
		else if (evnt=='ProceduresScheduled'){
			event.eventName = EventName.ProceduresScheduled
			patient.procedureStatus = "Scheduled"
			log.info(patientId + " arrived at InConsultation2StateHandler") // for logging purpose only
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			return null;
		}
		else if(evnt == 'PhysicianOutED'){
			event.eventName = EventName.PhysicianOutED
			patientId=patient.getPatientID()
			providerId=props['Physician_ID']
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
			log.info(patientId + " arrived at InConsultation2StateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.IN_BED_ED
			updatePatientState(patientState)
			patientStateId='IN_BED_ED'
			
			data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb1=new QueryBuilder();
				int ii=qb1.buildQueryPatientStateFact(data);
		}
		if((evnt == 'PhysicianOutED')&&(previousEvent == 'PatientAdmittedWithNoBed')){
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_BED_CW
			patientState.stateAttributes.put ("Unit_ID", props["Unit_ID"])
			patientState.target = 480
			updatePatientState(patientState)
			patientId=patient.getPatientID()
			providerId=props['Physician_ID']
			
			String[] data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb1=new QueryBuilder();
				int ii=qb1.buildQueryPatientStateFact(data);
					
				}
		/* If the event received is PatientAdmittedWithNoBed
		 * maintain the Patient state
		 */
		
		return null;
	
	}
}
	
	