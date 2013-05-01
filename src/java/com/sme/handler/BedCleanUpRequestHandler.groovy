package com.sme.handler

import dbagent.QueryBuilder
import java.util.Map;

import patientflowmonitoring.Room;
import patientflowmonitoring.RoomState;
import patientflowmonitoring.RoomState.RoomStateName;
import patientflowmonitoring.BEvent.BEventName;


class BedCleanUpRequestHandler extends EventHandler{
	
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
		
		event1.eventName = BEventName.BedCleanUpRequest
		def roomState = new RoomState()
		roomState.stateName = RoomStateName.WAIT_FOR_BED_CLEANUP
		roomState.stateAttributes.put ("Room_ID", props["Location_ID"])
		updateRoomState(roomState)
		
		
		roomStateId='WAIT_FOR_BED_CLEANUP'
		String[] data=[locationId,roomStateId,evnt,providerId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
		//Calling DAO for Room Events
		
			QueryBuilder qb=new QueryBuilder();
			int i=qb.buildQueryRoomStateFact(data);
		
		return null;
		
		
	}

}
