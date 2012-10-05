package com.sme.handler

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
	@Override
	public Object process(Map props) {
		/* This Event Handler is called for patients
		 * in the IN_BED_CCL state
		 * If the event received is ProcedureStated
		 * change the Patient state to IN_PROCEDURE
		 */
		String evnt = props['event']
		
		if(evnt == 'ProcedureStarted'){
			event.eventName = EventName.ProcedureStarted
			log.info(patientId + " arrived at InBedCCLStateHandler") // for logging purpose only
		 
		 
			def procedureType = props['Procedure_Type']
			log.info( "procedure type is " + procedureType );
		 
			def patientState = new PatientState()
			patientState.stateAttributes.put ('ProviderId', props['Provider_ID'])
			patientState.stateAttributes.put ('ProcedureType', props['Procedure_Type'])
		 
			if(patientState.stateAttributes.ProcedureType=='Angiogram'){
				patientState.stateName = PatientStateName.IN_PROCEDURE_ANGIOGRAM
				patientState.target = 45
			}
			else if(patientState.stateAttributes.ProcedureType=='PCI'){
				patientState.stateName = PatientStateName.IN_PROCEDURE_PCI
				patientState.target = 90
			}
		
			updatePatientState(patientState)
			return null;
		}
		else if(evnt=='PatientTransportRequest'){
			event.eventName = EventName.PatientTransportRequest
			log.info(patientId + " arrived at InBedCCLStateHandler") // for logging purpose only
		 
			def patientState = new PatientState()
			patientState.stateName = PatientStateName.WAIT_FOR_TRANSPORT_CW
			patientState.stateAttributes.put ('LocationId', props['Location_ID'])
			patientState.stateAttributes.put ('UnitId', props['Unit_ID'])
			patientState.target = 15
			updatePatientState(patientState)
			return null;
			}
		else if(evnt == 'TransportInCCL'){
			event.eventName = EventName.TransportInCCL
			log.info(patientId + " arrived at InBedCCLStateHandler") // for logging purpose only
					
			return null;
		}
		else if(evnt == 'TransportOutCCL'){
			event.eventName = EventName.TransportOutCCL
			log.info(patientId + " arrived at InBedCCLStateHandler") // for logging purpose only
					
			return null;
		}
		}
	}
