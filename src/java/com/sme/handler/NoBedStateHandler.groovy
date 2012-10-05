package com.sme.handler


import java.util.Map;
import patientflowmonitoring.BEvent.BEventName;
import patientflowmonitoring.Room;
import patientflowmonitoring.RoomState;
import patientflowmonitoring.RoomState.RoomStateName;

class NoBedStateHandler extends EventHandler{
	@Override
	def process(Map props){
		String evnt = props['event']
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
	
			return null;	//No return value is needed
		}
		else if(evnt =='BedCleanUpRequest'){
			event1.eventName = BEventName.BedCleanUpRequest  // assignment the event name value, which must be a predefined value in Event.EventName
			log.info(roomId + " arrived at NoBedStateHandler") // for logging purpose only
		
			def roomState = new RoomState()				// Since this event will cause the state change of the patient, the following three lines are for updating patient state
			roomState.stateName = RoomStateName.WAIT_FOR_BED_CLEANUP
			updateRoomState(roomState)
	
			return null;	//No return value is needed
		
		}
		else if(evnt =='HouseKeepingInCW'){
			event1.eventName = BEventName.HouseKeepingInCW  // assignment the event name value, which must be a predefined value in Event.EventName
			log.info(roomId + " arrived at NoBedStateHandler") // for logging purpose only
		
			def roomState = new RoomState()				// Since this event will cause the state change of the patient, the following three lines are for updating patient state
			roomState.stateName = RoomStateName.IN_CLEANUP
			updateRoomState(roomState)
	
			return null;	//No return value is needed
		
		}
		else if(evnt =='HouseKeepingOutCW'){
			event1.eventName = BEventName.HouseKeepingOutCW  // assignment the event name value, which must be a predefined value in Event.EventName
			log.info(roomId + " arrived at NoBedStateHandler") // for logging purpose only
		
			def roomState = new RoomState()				// Since this event will cause the state change of the patient, the following three lines are for updating patient state
			roomState.stateName = RoomStateName.BED_AVAILABLE
			updateRoomState(roomState)
	
			return null;	//No return value is needed
		
		}
		
	}

}
