package com.sme.handler

import dbagent.QueryBuilder
import java.util.Map;
import patientflowmonitoring.BEvent.BEventName;
import patientflowmonitoring.Room;
import patientflowmonitoring.RoomState;
import patientflowmonitoring.RoomState.RoomStateName;


class BedAvailableStateHandler extends EventHandler{
	@Override
	def process(Map props){
		evnt = props['event']
		unitId=props['Unit_ID']
		locationId=props['Location_ID']
		timestamp = props['timestamp']
		//providerId=props['Provider_ID']
		providerId = 'Not Defined Num'
		dateVar=timestamp.substring(0, 10)
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("date is" + dateVar)
		log.info("timestamp is " + startTimestamp)
		
		/* This Event Handler is called for new beds
		 * who had no previous state
		 * 
		 */
		if(evnt =='PatientAdmittedWithBed'){
			event1.eventName = BEventName.PatientAdmittedWithBed  // assignment the event name value, which must be a predefined value in Event.EventName
			log.info(roomId + " arrived at BedAvailableStateHandler") // for logging purpose only
		
			def roomState = new RoomState()				// Since this event will cause the state change of the patient, the following three lines are for updating patient state
			roomState.stateName = RoomStateName.BED_ASSIGNED
			updateRoomState(roomState)
			roomStateId='BED_ASSIGNED'
			String[] data=[locationId,roomStateId,evnt,providerId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Room Events
			
				QueryBuilder qb=new QueryBuilder();
				int i=qb.buildQueryRoomStateFact(data);
	
			return null;	//No return value is needed
		}
		
	}
	
}