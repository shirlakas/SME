package com.sme.handler

import java.util.Map;
import patientflowmonitoring.BEvent.BEventName;
import patientflowmonitoring.Room;
import patientflowmonitoring.RoomState;
import patientflowmonitoring.RoomState.RoomStateName;


class BedAvailableStateHandler extends EventHandler{
	@Override
	def process(Map props){
		String evnt = props['event']
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
	
			return null;	//No return value is needed
		}
		
	}
	
}