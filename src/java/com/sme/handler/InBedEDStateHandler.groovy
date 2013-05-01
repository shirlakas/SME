package com.sme.handler

import dbagent.QueryBuilder
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
	def process(Map props){
		evnt = props['event']
		orderNum=null
		String previousEvent = patient.lastEventReceived
		
		unitId=props['Unit_ID']
		locationId=props['Location_ID']
		timestamp = props['timestamp']
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("timestamp is " + startTimestamp)
		
		
		/* This Event Handler is called for patients
		 * in the IN_BED_ED state
		 * If the event received is OrderRequest
		 * change the Patient state to WAIT_FOR_ORDERS_EXECUTION
		 */
		if(evnt == 'OrderRequest'){
			event.eventName = EventName.OrderRequest
			orderNum=props['Order_ID']
			unitId='ED'
			locationId=patient.getRoomID()
			orderNum=props['Order_ID']
			orderType=props['OrderType']
			providerId=props['Provider_ID']
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
				QueryBuilder qb=new QueryBuilder();
				int i=qb.buildQueryPatientEventFact(data);
				
			log.info(patientId + " arrived at InBedEDStateHandler") // for logging purpose only
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_ORDERS_EXECUTION
			patientState.target = 30
			updatePatientState(patientState)
			patientStateId='WAIT_FOR_ORDERS_EXECUTION'
			
			log.info(" pending orders is " + patient.getPendingOrders()) // for logging purpose only
			if(patient.getPendingOrders()<1){
				data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb1=new QueryBuilder();
				int ii=qb1.buildQueryPatientStateFact(data);
			}		
			patient.pendingOrders += 1
		}
		/* If the event received is PatientAdmittedWithNoBed
		* change the Patient state to WAIT_FOR_BED_CW
		*/
		else if(evnt == 'PatientAdmittedWithNoBed'){
			event.eventName = EventName.PatientAdmittedWithNoBed
			log.info(patientId + " arrived at InBedEDStateHandler") // for logging purpose only
			providerId='Not Defined Num'
			locationId='UnassignedED'
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_BED_CW
			patientState.stateAttributes.put ("Unit_ID", props["Unit_ID"])
			patientState.target = 480
			updatePatientState(patientState)
			patientStateId='WAIT_FOR_BED_CW'
			
			
			data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb1=new QueryBuilder();
				int ii=qb1.buildQueryPatientStateFact(data);
					
		}
		/* If the event received is PatientAdmittedWithNoBed
		 * maintain the Patient state
		 */
		else if(evnt == 'PatientAdmittedWithBed'){
			event.eventName = EventName.PatientAdmittedWithBed
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
			log.info(patientId + " arrived at InBedEDStateHandler") // for logging purpose only
		
			def Admission admission = new Admission()
			admission.setTimeStamp(createTimeStamp(props['timestamp']))
			admission.save()
			if(previousEvent=="PatientTransportRequest"){
				def patientState = new PatientState()
				patientState.stateName = PatientStateName.WAIT_FOR_TRANSPORT_CW
				patientState.stateAttributes.put ('LocationId', props['Location_ID'])
				patientState.stateAttributes.put ('UnitId', props['Unit_ID'])
				patientState.target = 15
				updatePatientState(patientState)
				patientStateId='WAIT_FOR_TRANSPORT_CW'
				
				data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb1=new QueryBuilder();
				int ii=qb1.buildQueryPatientStateFact(data);
			}
			
		}
		else if (evnt=='ProceduresScheduled'){
			event.eventName = EventName.ProceduresScheduled
			patient.procedureStatus = "Scheduled"
			providerId='Not Defined Num'
			locationId=patient.getRoomID()
			log.info(patientId + " arrived at InBedEDStateHandler") // for logging purpose only	
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
		}
		else if (evnt=='PatientTransportRequest'){
			event.eventName = EventName.PatientTransportRequest
			locationId='UnassignedED'
			log.info(patientId + " arrived at InBedEDStateHandler")
			String[] data=[patientId,evnt,providerId, CTAS, locationId,orderNum,orderType,procedureId,startTimestamp,startTimestamp,duration,currentStateFlag,endTimestamp]
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryPatientEventFact(data);
			
			if (previousEvent=="PatientAdmittedWithBed"){
				def patientState = new PatientState()
				patientState.stateName = PatientStateName.WAIT_FOR_TRANSPORT_CW
				patientState.stateAttributes.put ('LocationId', props['Location_ID'])
				patientState.stateAttributes.put ('UnitId', props['Unit_ID'])
				patientState.target = 15
				updatePatientState(patientState)
				patientId=patient.getPatientID()
				patientStateId='WAIT_FOR_TRANSPORT_CW'
				
				data=[patientId,patientStateId,providerId,procedureId,locationId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient State
			//patientNum, stateName, providerNum, procedureName, roomNum, startTimestamp, date, endTimestamp, duration, currentStateFlag)
				QueryBuilder qb1=new QueryBuilder();
				int ii=qb1.buildQueryPatientStateFact(data);
				
			}
			return null;
		}
			
	}
}




