package com.sme.handler

import dbagent.QueryBuilder
import java.util.Map;
import patientflowmonitoring.Patient;
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Event.EventName;
import patientflowmonitoring.BEvent.BEventName;
import patientflowmonitoring.PatientState.PatientStateName;
import patientflowmonitoring.Room;
import patientflowmonitoring.RoomState;
import patientflowmonitoring.RoomState.RoomStateName;

class InBedCCLStateHandler extends EventHandler{
	String procedureType
	String timestamp
	String evnt
	
	@Override
	def process(Map props){
		patientId=patient.getPatientID()
		unitId='CCL'
		evnt = props['event']
		locationId=props['Location_ID']
		timestamp = props['timestamp']
		providerId=props['Provider_ID']
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("timestamp is " + startTimestamp)
		
		/* This Event Handler is called for patients
		 * in the IN_BED_CCL state
		 * If the event received is ProcedureStated
		 * change the Patient state to IN_PROCEDURE
		 */
		
		
		if(evnt =='ProcedureStarted'){
			event.eventName = EventName.ProcedureStarted
			log.info(evnt + " arrived at InBedCCLStateHandler") // for logging purpose only
			procedureType = props['Procedure_Type']
			log.info( "procedure type is " + procedureType );
			locationId=patient.getRoomID()
			
			
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
			def patientState = new PatientState()
			if(procedureType == 'Angiogram'){
				patientState.stateName = PatientStateName.IN_PROCEDURE_ANGIOGRAM
				patientState.target = 45
				patientStateId='IN_PROCEDURE_ANGIOGRAM'
				
				
			}
			else if(procedureType == 'PCI'){
				patientState.stateName = PatientStateName.IN_PROCEDURE_PCI
				patientState.target = 90
				patientStateId='IN_PROCEDURE_PCI'
			}
			patientState.stateAttributes.put ('ProviderId', props['Provider_ID'])
			patientState.stateAttributes.put ('procedureType', props['Procedure_Type'])
		 	updatePatientState(patientState)
			data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb2=new QueryBuilder();
				int iii=qb2.buildQueryPatientStateFact(data);
			 
			return null;
		}
		else if(evnt=='PatientTransportRequest'){
			event.eventName = EventName.PatientTransportRequest
			log.info(evnt + " arrived at InBedCCLStateHandler") // for logging purpose only
			locationId=patient.getRoomID()
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_TRANSPORT_CW
			patientState.stateAttributes.put ('LocationId', props['Location_ID'])
			patientState.stateAttributes.put ('UnitId', 'CCL')
			patientState.target = 15
			updatePatientState(patientState)
			
			patientStateId='WAIT_FOR_TRANSPORT_CW'
			
			data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb2=new QueryBuilder();
				int iii=qb2.buildQueryPatientStateFact(data);
				
			return null;
			}
		
		}
	}
