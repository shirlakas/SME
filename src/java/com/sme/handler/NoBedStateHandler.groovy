package com.sme.handler


import dbagent.QueryBuilder
import java.util.Map;


import patientflowmonitoring.BEvent.BEventName;
import patientflowmonitoring.Room;
import patientflowmonitoring.RoomState;
import patientflowmonitoring.RoomState.RoomStateName;

class NoBedStateHandler extends EventHandler{
	@Override
	def process(Map props){
		evnt = props['event']
		unitId=props['Unit_ID']
		locationId=props['Location_ID']
		timestamp = props['timestamp']
		providerId=props['HouseKeeping_ID']
		//
		dateVar=timestamp.substring(0, 10)
		startTimestamp= timestamp.substring(0, 10)+' '+timestamp.substring(11, 13)+':'+timestamp.substring(14, 16)+':'+timestamp.substring(17, 19)
		log.info("date is" + dateVar)
		log.info("timestamp is " + startTimestamp)
		
		/* This Event Handler is called for new beds
		 * who had no previous state
		 * Possible events to receive include:
		 * PatientAdmittedWithBed,
		 * PatientArrivedInBed,
		 * BedCleanUpRequest,
		 * WaitForBedCleanUp,
		 * BedCleanUpStarted,
		 * BedCleanUpCompleted
		 */
		if(evnt =='PatientAdmittedWithBed'){
			event1.eventName = BEventName.PatientAdmittedWithBed  // assignment the event name value, which must be a predefined value in Event.EventName
			log.info(roomId + " arrived at NoBedStateHandler") // for logging purpose only
		
			def roomState = new RoomState()				// Since this event will cause the state change of the patient, the following three lines are for updating patient state
			roomState.stateName = RoomStateName.BED_ASSIGNED
			updateRoomState(roomState)
			providerId = 'Not Defined Num'
			roomStateId='BED_ASSIGNED'
			String[] data=[locationId,roomStateId,evnt,providerId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Room Events
			
				QueryBuilder qb=new QueryBuilder();
				int i=qb.buildQueryRoomStateFact(data);
	
	
			return null;	//No return value is needed
		}
		else if(evnt =='BedCleanUpRequest'){
			event1.eventName = BEventName.BedCleanUpRequest  // assignment the event name value, which must be a predefined value in Event.EventName
			log.info(roomId + " arrived at NoBedStateHandler") // for logging purpose only
		
			def roomState = new RoomState()				// Since this event will cause the state change of the patient, the following three lines are for updating patient state
			roomState.stateName = RoomStateName.WAIT_FOR_BED_CLEANUP
			updateRoomState(roomState)
			roomStateId='WAIT_FOR_BED_CLEANUP'
			providerId = 'Not Defined Num'
			String[] data=[locationId,roomStateId,evnt,providerId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient Events
			
				QueryBuilder qb=new QueryBuilder();
				int i=qb.buildQueryRoomStateFact(data);
	
			return null;	//No return value is needed
		
		}
		else if(evnt =='HouseKeepingInCW'){
			event1.eventName = BEventName.HouseKeepingInCW  // assignment the event name value, which must be a predefined value in Event.EventName
			log.info(roomId + " arrived at NoBedStateHandler") // for logging purpose only
		
			def roomState = new RoomState()				// Since this event will cause the state change of the patient, the following three lines are for updating patient state
			roomState.stateName = RoomStateName.IN_CLEANUP
			updateRoomState(roomState)
			roomStateId='IN_CLEANUP'
			String[] data=[locationId,roomStateId,evnt,providerId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			
			//Calling DAO for Patient Events
			
				QueryBuilder qb=new QueryBuilder();
				int i=qb.buildQueryRoomStateFact(data);
	
			return null;	//No return value is needed
		
		}
		else if(evnt =='HouseKeepingOutCW'){
			event1.eventName = BEventName.HouseKeepingOutCW  // assignment the event name value, which must be a predefined value in Event.EventName
			log.info(roomId + " arrived at NoBedStateHandler") // for logging purpose only
		
			def roomState = new RoomState()				// Since this event will cause the state change of the patient, the following three lines are for updating patient state
			roomState.stateName = RoomStateName.BED_AVAILABLE
			updateRoomState(roomState)
			
			roomStateId='BED_AVAILABLE'
			String[] data=[locationId,roomStateId,evnt,providerId,startTimestamp,startTimestamp,endTimestamp,duration,currentStateFlag]
			//Calling DAO for Patient Events
			
				QueryBuilder qb=new QueryBuilder();
				int i=qb.buildQueryRoomStateFact(data);
	
			return null;	//No return value is needed
		
		}
		
	}

}
