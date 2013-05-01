// Place your Spring DSL code here

import org.apache.activemq.ActiveMQConnectionFactory

beans = {
	
	jmsConnectionFactory(ActiveMQConnectionFactory){
		brokerURL = "tcp://localhost:61616"
	}
	
	defaultJmsTemplate(org.springframework.jms.core.JmsTemplate){
		connectionFactory = ref("jmsConnectionFactory")
	}
	// Patient State Handlers
	nostateHandler(com.sme.handler.NoStateHandler){}
	triagedstateHandler(com.sme.handler.TriagedStateHandler){}
	waitforconsultation1stateHandler(com.sme.handler.WaitForConsultation1StateHandler){}
	inconsultation1stateHandler(com.sme.handler.InConsultation1StateHandler){}
	inbededstateHandler(com.sme.handler.InBedEDStateHandler){}
	waitforordersexecutionstateHandler(com.sme.handler.WaitForOrdersExecutionStateHandler){}
	waitforconsultation2stateHandler(com.sme.handler.WaitForConsultation2StateHandler){}
	inconsultation2stateHandler(com.sme.handler.InConsultation2StateHandler){}
	waitforbedcwstateHandler(com.sme.handler.WaitForBedCWStateHandler){}
	waitfortransportcwstateHandler(com.sme.handler.WaitForTransportCWStateHandler){}
	intransportcwstateHandler(com.sme.handler.InTransportCWStateHandler){}
	inbedcwstateHandler(com.sme.handler.InBedCWStateHandler){}
	waitforproceduresstateHandler(com.sme.handler.WaitForProceduresStateHandler){}
	waitfortransportcclstateHandler(com.sme.handler.WaitForTransportCCLStateHandler){}
	intransportcclstateHandler(com.sme.handler.InTransportCCLStateHandler){}
	inbedcclstateHandler(com.sme.handler.InBedCCLStateHandler){}
	inprocedurestateHandler(com.sme.handler.InProcedureStateHandler){}
	inconsultation3stateHandler(com.sme.handler.InConsultation3StateHandler){}
	waitfordischargestateHandler(com.sme.handler.WaitForDischargeStateHandler){}
	
	//Bed State Handlers
	nobedstateHandler(com.sme.handler.NoBedStateHandler){}
	bedassignedstateHandler(com.sme.handler.BedAssignedStateHandler){}
	waitforbedcleanupstateHandler(com.sme.handler.WaitForBedCleanUpStateHandler){}
	bedincleanupstateHandler(com.sme.handler.BedInCleanUpStateHandler){}
	bedavailablestateHandler(com.sme.handler.BedAvailableStateHandler){}
	
	//
	
	
	//Event Handlers
	/*triagescoreHandler(com.sme.handler.TriageScoreHandler){}
	patientregisteredHandler(com.sme.handler.PatientRegisteredHandler){}
	patientinedHandler(com.sme.handler.PatientInEDHandler){}
	physicianinedHandler(com.sme.handler.PhysicianInEDHandler){}
	physicianoutedHandler(com.sme.handler.PhysicianOutEDHandler){}
	waitforconsultation1Handler(com.sme.handler.WaitForConsultation1Handler){}
	waitforconsultation2Handler(com.sme.handler.WaitForConsultation2Handler){}
	consultationstarted1Handler(com.sme.handler.ConsultationStarted1Handler){}
	consultationstarted2Handler(com.sme.handler.ConsultationStarted2Handler){}
	orderrequestHandler(com.sme.handler.OrderRequestHandler){}
	consultationcompleted1Handler(com.sme.handler.ConsultationCompleted1Handler){}
	consultationcompleted2Handler(com.sme.handler.ConsultationCompleted2Handler){}
	requestreferralHandler(com.sme.handler.RequestReferralHandler){}
	waitfororderexecutionHandler(com.sme.handler.WaitForOrderExecutionHandler){}
	orderrequestcompletedHandler(com.sme.handler.OrderRequestCompletedHandler){}
	//orderexecutioncompletedHandler(com.sme.handler.OrderExecutionCompletedHandler){}
	ordersexecutioncompletedHandler(com.sme.handler.OrdersExecutionCompletedHandler){}
	bedrequestHandler(com.sme.handler.BedRequestHandler){}
	patientadmittedwithnobedHandler(com.sme.handler.PatientAdmittedWithNoBedHandler){}
	waitforbedHandler(com.sme.handler.WaitForBedHandler){}
	patientadmittedwithbedHandler(com.sme.handler.PatientAdmittedWithBedHandler){}
	patienttransportrequestHandler(com.sme.handler.PatientTransportRequestHandler){}
	waitfortransportHandler(com.sme.handler.WaitForTransportHandler){}
	patienttransportstartedHandler(com.sme.handler.PatientTransportStartedHandler){}
	patientarrivedinbedHandler(com.sme.handler.PatientArrivedInBedHandler){}
	
	//extended scenario events
	consultationstarted3Handler(com.sme.handler.ConsultationStarted3Handler){}
	consultationcompleted3Handler(com.sme.handler.ConsultationCompleted3Handler){}
	waitforproceduresHandler(com.sme.handler.WaitForProceduresHandler){}
	procedurerequestHandler(com.sme.handler.ProcedureRequestHandler){}
	procedurestartedHandler(com.sme.handler.ProcedureStartedHandler){}
	procedurecompletedHandler(com.sme.handler.ProcedureCompletedHandler){}
	proceduresexecutioncompletedHandler(com.sme.handler.ProceduresExecutionCompletedHandler){}
	proceduresscheduledHandler(com.sme.handler.ProceduresScheduledHandler){}
	procedureupdatedHandler(com.sme.handler.ProcedureUpdatedHandler){}
	dischargecompletedHandler(com.sme.handler.DischargeCompletedHandler){}
	dischargerequestHandler(com.sme.handler.DischargeRequestHandler){}
	waitfordischargeHandler(com.sme.handler.WaitForDischargeHandler){}
	bedcleanuprequestHandler(com.sme.handler.BedCleanUpRequestHandler){}
	waitforbedcleanupHandler(com.sme.handler.WaitForBedCleanUpHandler){}
	bedcleanupstartedHandler(com.sme.handler.BedCleanUpStartedHandler){}
	bedcleanupcompletedHandler(com.sme.handler.BedCleanUpCompletedHandler){}
	
	*/
	
	
	
}
