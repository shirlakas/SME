import patientflowmonitoring.Patient
import patientflowmonitoring.PatientState;
import patientflowmonitoring.PatientState.PatientStateName
import patientflowmonitoring.Unit
import patientflowmonitoring.Room


class BootStrap {
	
	def jmsService

    def init = { servletContext ->
		createUnits()
		createTestRecords()
    }
    def destroy = {
    }
	
	def createTestRecords(){
		jmsService.send(queue:'SME_Event','event:TriageScore,timestamp:2012-10-04/03-30-00,Provider_ID:Nurse745774,CTAS:2,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:PatientInED,timestamp:2012-10-04/03-45-00,Location_ID:ED1,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:PhysicianInED,timestamp:2012-10-04/04-20-00,Unit_ID:ED,Location_ID:ED1,Physician_ID:Phy77777')
		jmsService.send(queue:'SME_Event','event:PhysicianOutED,timestamp:2012-10-04/04-45-00,Unit_ID:ED,Location_ID:ED1,Physician_ID:Phy77777')
		jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1111,OrderType:ECG,Provider_ID:Phy777777,timestamp:2012-10-04/04-49-00,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1112,OrderType:BloodSample,Provider_ID:Phy777777,timestamp:2012-10-04/04-50-00,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1113,OrderType:BloodAnalysis,Provider_ID:Phy777777,timestamp:2012-10-04/04-51-00,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1111,OrderType:ECG,Provider_ID:Nurse121212,timestamp:2012-10-04/05-05-00,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1112,OrderType:BloodSample,Provider_ID:Nurse121212,timestamp:2012-10-04/05-10-00,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1113,OrderType:BloodAnalysis,Provider_ID:Nurse121212,timestamp:2012-10-04/05-40-00,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:PhysicianInED,timestamp:2012-10-04/06-05-00,Unit_ID:ED,Location_ID:ED1,Physician_ID:Phy77777')
		jmsService.send(queue:'SME_Event','event:PhysicianOutED,timestamp:2012-10-04/07-00-00,Unit_ID:ED,Location_ID:ED1,Physician_ID:Phy77777')
		jmsService.send(queue:'SME_Event','event:PatientAdmittedWithNoBed,timestamp:2012-10-04/07-03-10,Unit_ID:CW,Patient_ID:Pa111111')
		
		jmsService.send(queue:'SME_Event','event:BedCleanUpRequest,timestamp:2012-10-04/12-10-00,Unit_ID:CW,Location_ID:R105')
		jmsService.send(queue:'SME_Event','event:HouseKeepingInCW,timestamp:2012-10-04/12-45-00,Unit_ID:CW,Location_ID:R105,HouseKeeping_ID:R107')
		jmsService.send(queue:'SME_Event','event:HouseKeepingOutCW,timestamp:2012-10-04/14-35-00,Unit_ID:CW,Location_ID:R105,HouseKeeping_ID:R107')
		
		
		jmsService.send(queue:'SME_Event','event:PatientAdmittedWithBed,timestamp:2012-10-04/15-40-00,Location_ID:R105,Unit_ID:CW,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CW,timestamp:2012-10-04/15-45-00,Provider_ID:Nur121212,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:TransportInED,timestamp:2012-10-04/16-05-00,Unit_ID:ED,Location_ID:ED1,Transport_ID:Tra1234')
		jmsService.send(queue:'SME_Event','event:TransportOutED,timestamp:2012-10-04/16-06-00,Unit_ID:ED,Location_ID:ED1,Transport_ID:Tra1234')
		jmsService.send(queue:'SME_Event','event:PatientOutED,timestamp:2012-10-04/16-07-00,Unit_ID:ED,Location_ID:ED1,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:PatientInCW,timestamp:2012-10-04/16-25-00,Unit_ID:CW,Location_ID:R105,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:TransportInCW,timestamp:2012-10-04/16-26-00,Unit_ID:CW,Location_ID:R105,Transport_ID:Tra1234')
		jmsService.send(queue:'SME_Event','event:TransportOutCW,timestamp:2012-10-04/16-30-00,Unit_ID:CW,Location_ID:R105,Transport_ID:Tra1234')
		jmsService.send(queue:'SME_Event','event:ProceduresScheduled,timestamp:2012-10-04/17-00-00,ProcedureScheduledTime:2012-10-05/3-00-00,Unit_ID:CCL,Patient_ID:Pa111111')
		
		jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CW,timestamp:2012-10-05/02-05-00,Provider_ID:Nur121212,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:TransportInCW,timestamp:2012-10-05/02-26-00,Unit_ID:CW,Location_ID:R105,Transport_ID:Tra1234')
		jmsService.send(queue:'SME_Event','event:TransportOutCW,timestamp:2012-10-05/02-30-00,Unit_ID:CW,Location_ID:R105,Transport_ID:Tra1234')
		jmsService.send(queue:'SME_Event','event:PatientOutCW,timestamp:2012-10-05/02-31-00,Unit_ID:CW,Location_ID:R105,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:PatientInCCL,timestamp:2012-10-05/02-47-00,Unit_ID:CCL,Location_ID:CCLRoom,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:TransportInCCL,timestamp:2012-10-05/02-26-00,Unit_ID:CCL,Location_ID:CCLRoom,Transport_ID:Tra1234')
		jmsService.send(queue:'SME_Event','event:TransportOutCCL,timestamp:2012-10-05/02-30-00,Unit_ID:CCL,Location_ID:CCLRoom,Transport_ID:Tra1234')
				
		jmsService.send(queue:'SME_Event','event:ProcedureStarted,timestamp:2012-10-05/03-00-00,Provider_ID:Phy777777,Procedure_Type:Angiogram,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:ProcedureCompleted,timestamp:2012-10-05/03-50-00,Provider_ID:Phy777777,Procedure_Type:Angiogram,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:ProcedureStarted,timestamp:2012-10-05/04-00-00,Provider_ID:Phy777777,Procedure_Type:PCI,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:ProcedureCompleted,timestamp:2012-10-05/04-30-00,Provider_ID:Phy777777,Procedure_Type:PCI,Patient_ID:Pa111111')
		
		jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CCL,timestamp:2012-10-05/05-29-00,Provider_ID:Nur121212,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:TransportInCCL,timestamp:2012-10-05/05-35-00,Unit_ID:CCL,Location_ID:CCLRoom,Transport_ID:Tra1234')
		jmsService.send(queue:'SME_Event','event:TransportOutCCL,timestamp:2012-10-05/05-40-00,Unit_ID:CCL,Location_ID:CCLRoom,Transport_ID:Tra1234')
		jmsService.send(queue:'SME_Event','event:PatientOutCCL,timestamp:2012-10-05/05-40-00,Unit_ID:CCL,Location_ID:CCLRoom,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:TransportInCW,timestamp:2012-10-05/06-00-00,Unit_ID:CW,Location_ID:R105,Transport_ID:Tra1234')
		jmsService.send(queue:'SME_Event','event:PatientInCW,timestamp:2012-10-05/06-02-00,Unit_ID:CW,Location_ID:R105,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:TransportOutCW,timestamp:2012-10-05/06-10-00,Unit_ID:CW,Location_ID:R105,Transport_ID:Tra1234')
		
		jmsService.send(queue:'SME_Event','event:PhysicianInCW,timestamp:2012-10-05/06-15-00,Unit_ID:CW,Location_ID:R105,Physician_ID:Phy77777')
		jmsService.send(queue:'SME_Event','event:DischargeRequest,Provider_ID:Phy77777,timestamp:2012-10-05/06-21-00,Unit_ID:CW,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:PhysicianOutCW,timestamp:2012-10-05/06-30-00,Unit_ID:CW,Location_ID:R105,Physician_ID:Phy77777')
		//jmsService.send(queue:'SME_Event','event:PatientOutCW,timestamp:2012-10-05/07-31-00,Unit_ID:CW,Location_ID:R105,Patient_ID:Pa111111')
		
		
		/*jmsService.send(queue:'SME_Event','event:WaitForConsultation1,timestamp:2012-10-04/04-00-00,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:ConsultationStarted1,timestamp:2012-10-04/04-20-00,Provider_ID:Phy777777,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:ConsultationCompleted1,timestamp:2012-10-04/04-40-00,Provider_ID:Phy777777,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1111,OrderType:ECG,Provider_ID:Phy777777,timestamp:2012-10-04/04-41-00,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1112,OrderType:BloodSample,Provider_ID:Phy777777,timestamp:2012-10-04/04-41-00,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1113,OrderType:BloodAnalysis,Provider_ID:Phy777777,timestamp:2012-10-04/04-41-00,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:WaitForOrderExecution,timestamp:2012-10-04/04-42-00,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1111,OrderType:ECG,Provider_ID:Nurse121212,timestamp:2012-10-04/05-05-00,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1112,OrderType:BloodSample,Provider_ID:Nurse121212,timestamp:2012-10-04/05-08-00,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1113,OrderType:BloodAnalysis,Provider_ID:Nurse121212,timestamp:2012-10-04/05-40-00,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:OrdersExecutionCompleted,timestamp:2012-10-04/05-40-10,Provider_ID:Nurse121212,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:WaitForConsultation2,timestamp:2012-10-04/05-40-50,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:ConsultationStarted2,timestamp:2012-10-04/06-00-00,Provider_ID:Phy777777,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:ConsultationCompleted2,timestamp:2012-10-04/06-34-00,Provider_ID:Phy777777,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:PatientAdmittedWithNoBed,timestamp:2012-10-04/06-41-10,Unit_ID:CW,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:WaitForBed,timestamp:2012-10-04/06-42-00,Unit_ID:CW,Patient_ID:Pa111111')
		
		jmsService.send(queue:'SME_Event','event:PatientAdmittedWithBed,timestamp:2012-10-04/15-40-00,Location_ID:R105,Unit_ID:CW,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:WaitForTransport,timestamp:2012-10-04/15-41-00,Unit_ID:CW,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:PatientTransportStarted,Unit_ID:CW,timestamp:2012-10-04/16-00-00,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:PatientArrivedInBed,timestamp:2012-10-04/16-10-00,Location_ID:R105,Unit_ID:CW,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:WaitForProcedures,timestamp:2012-10-04/16-10-10,Unit_ID:CW,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CCL,timestamp:2012-10-05/02-05-00,Provider_ID:Nur121212,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:WaitForTransport,timestamp:2012-10-05/02-05-10,Unit_ID:CCL,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:PatientTransportStarted,Unit_ID:CCL,timestamp:2012-10-05/02-30-00,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:PatientArrivedInBed,timestamp:2012-10-05/02-40-00,Location_ID:CCLRoom,Unit_ID:CCL,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:ProcedureStarted,timestamp:2012-10-05/04-20-00,Provider_ID:Phy777777,Procedure_Type:Angiogram,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:ProcedureCompleted,timestamp:2012-10-05/04-40-00,Provider_ID:Phy777777,Procedure_Type:Angiogram,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:ProcedureStarted,timestamp:2012-10-05/04-45-00,Provider_ID:Phy777777,Procedure_Type:PCI,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:ProcedureCompleted,timestamp:2012-10-05/05-40-00,Provider_ID:Phy777777,Procedure_Type:PCI,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:ProceduresExecutionCompleted,timestamp:2012-10-05/05-40-50,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CW,timestamp:2012-10-05/07-05-00,Provider_ID:Nur121212,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:WaitForTransport,timestamp:2012-10-05/07-05-10,Unit_ID:CW,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:PatientTransportStarted,Unit_ID:CW,timestamp:2012-10-05/07-30-00,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:PatientArrivedInBed,timestamp:2012-10-05/07-40-00,Location_ID:R105,Unit_ID:CW,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:ConsultationStarted3,timestamp:2012-10-05/08-00-00,Provider_ID:Phy777777,Patient_ID:Pa111111')
		//jmsService.send(queue:'SME_Event','event:ConsultationCompleted3,timestamp:2012-10-05/08-10-00,Provider_ID:Phy777777,Patient_ID:Pa111111')
		//jmsService.send(queue:'SME_Event','event:WaitForDischarge,timestamp:2012-10-05/08-11-00,Unit_ID:CW,Patient_ID:Pa111111')
		//jmsService.send(queue:'SME_Event','event:DischargeCompleted,timestamp:2012-10-05/09-00-00,Unit_ID:CW,Patient_ID:Pa111111')*/
		
				
	   jmsService.send(queue:'SME_Event','event:TriageScore,timestamp:2012-10-05/11-10-00,Provider_ID:Nurse745774,CTAS:2,Patient_ID:Pa123457')
	   jmsService.send(queue:'SME_Event','event:PatientInED,timestamp:2012-10-05/11-11-00,Location_ID:AssessmentRoom,Patient_ID:Pa123457')
	   
	   /*jmsService.send(queue:'SME_Event','event:WaitForConsultation1,timestamp:2012-10-05/04-00-00,Patient_ID:Pa123457')
		//jmsService.send(queue:'SME_Event','event:ConsultationStarted1,timestamp:2012-10-05/04-20-00,Provider_ID:Phy777777,Patient_ID:Pa123457')
		//jmsService.send(queue:'SME_Event','event:ConsultationCompleted1,timestamp:2012-10-05/04-40-00,Provider_ID:Phy777777,Patient_ID:Pa123457')
		//jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1111,OrderType:ECG,Provider_ID:Phy777777,timestamp:2012-10-05/04-41-00,Patient_ID:Pa123457')
		//jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1112,OrderType:BloodSample,Provider_ID:Phy777777,timestamp:2012-10-05/04-41-00,Patient_ID:Pa123457')
	//	jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1113,OrderType:BloodAnalysis,Provider_ID:Phy777777,timestamp:2012-10-05/04-41-00,Patient_ID:Pa123457')
		//jmsService.send(queue:'SME_Event','event:WaitForOrderExecution,timestamp:2012-10-05/04-42-00,Patient_ID:Pa123457')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1111,OrderType:ECG,Provider_ID:Nurse121212,timestamp:2012-10-05/05-05-00,Patient_ID:Pa123457')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1112,OrderType:BloodSample,Provider_ID:Nurse121212,timestamp:2012-10-05/05-08-00,Patient_ID:Pa123457')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1113,OrderType:BloodAnalysis,Provider_ID:Nurse121212,timestamp:2012-10-05/05-40-00,Patient_ID:Pa123457')
		jmsService.send(queue:'SME_Event','event:OrdersExecutionCompleted,timestamp:2012-10-05/05-40-10,Provider_ID:Nurse121212,Patient_ID:Pa123457')
		jmsService.send(queue:'SME_Event','event:WaitForConsultation2,timestamp:2012-10-05/05-40-50,Patient_ID:Pa123457')
		jmsService.send(queue:'SME_Event','event:ConsultationStarted2,timestamp:2012-10-05/06-00-00,Provider_ID:Phy777777,Patient_ID:Pa123457')
		jmsService.send(queue:'SME_Event','event:ConsultationCompleted2,timestamp:2012-10-05/06-34-00,Provider_ID:Phy777777,Patient_ID:Pa123457')
		jmsService.send(queue:'SME_Event','event:PatientAdmittedWithBed,timestamp:2012-10-05/06-40-00,Location_ID:R106,Unit_ID:CW,Patient_ID:Pa123457')
		jmsService.send(queue:'SME_Event','event:PatientTransportRequest,timestamp:2012-10-05/06-50-00,Unit_ID:CW,Patient_ID:Pa123457')
		
		//jmsService.send(queue:'SME_Event','event:WaitForTransport,timestamp:2012-10-05/06-50-00,Unit_ID:CW,Patient_ID:Pa123457')
		jmsService.send(queue:'SME_Event','event:PatientTransportStarted,Unit_ID:CW,timestamp:2012-10-05/07-30-00,Patient_ID:Pa123457')
		jmsService.send(queue:'SME_Event','event:PatientArrivedInBed,timestamp:2012-10-05/07-40-00,Location_ID:R106,Unit_ID:CW,Patient_ID:Pa123457')
		
		jmsService.send(queue:'SME_Event','event:WaitForBedCleanUp,timestamp:2012-10-05/09-10-10,Unit_ID:CW,Location_ID:R107')
		*/ //jmsService.send(queue:'SME_Event','event:BedCleanUpStarted,timestamp:2012-10-05/11-10-00,Unit_ID:CW,Location_ID:R107,HouseKeeping_ID:R107')
		//jmsService.send(queue:'SME_Event','event:BedCleanUpCompleted,timestamp:2012-10-05/12-10-10,Unit_ID:CW,Location_ID:R107,HouseKeeping_ID:R107')
	   jmsService.send(queue:'SME_Event','event:BedCleanUpRequest,timestamp:2012-10-05/20-10-00,Unit_ID:CW,Location_ID:R107')
	   jmsService.send(queue:'SME_Event','event:HouseKeepingInCW,timestamp:2012-10-05/21-10-00,Unit_ID:CW,Location_ID:R107,HouseKeeping_ID:R107')
	   jmsService.send(queue:'SME_Event','event:HouseKeepingOutCW,timestamp:2012-10-05/21-35-00,Unit_ID:CW,Location_ID:R107,HouseKeeping_ID:R107')
	  
	}
	def createUnits() {
		
		def room1 = new Room()
		room1.roomID = "AssessmentRoom"
		room1.save()
		def unit1 = new Unit()
		unit1.unitId = "ED"
		unit1.rooms.add(room1)
		unit1.save()
		
		def room2 = new Room()
		room2.roomID = "CCLRoom"
		room2.save()
		def unit2 = new Unit()
		unit2.unitId = "CCL"
		unit2.rooms.add(room2)
		unit2.save()
		
		def room3 = new Room()
		room3.roomID = "R107"
		room3.save()
		def room4 = new Room()
		room4.roomID = "R106"
		room4.save()
		def room5 = new Room()
		room5.roomID = "R105"
		room5.save()
		def unit3 = new Unit()
		unit3.unitId = "CW"
		unit3.rooms.add(room3)
		unit3.rooms.add(room4)
		unit3.rooms.add(room5)
		unit3.save()
		
	}
}
