import patientflowmonitoring.Patient
import patientflowmonitoring.PatientState;
import patientflowmonitoring.Room
import patientflowmonitoring.PatientState.PatientStateName
import patientflowmonitoring.*


class BootStrap {
	
	def jmsService

    def init = { servletContext ->
		createUnits()
		createTestRecords()
    }
    def destroy = {
    }
	
	def createTestRecords(){
		/*  Patient Pa222223-------*/
		/*jmsService.send(queue:'SME_Event','event:TriageScore,timestamp:2013-04-30/00-01-00,Provider_ID:Nurse745774,CTAS:1,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:PatientInED,timestamp:2013-04-30/00-10-00,Location_ID:AssessmentRoom12,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:PhysicianInED,timestamp:2013-04-30/00-16-00,Unit_ID:ED,Location_ID:AssessmentRoom12,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:PhysicianOutED,timestamp:2013-04-30/00-30-00,Unit_ID:ED,Location_ID:AssessmentRoom12,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1111,OrderType:ECG,Provider_ID:Phy777777,timestamp:2013-04-30/00-49-00,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1112,OrderType:BloodSample,Provider_ID:Phy777777,timestamp:2013-04-30/00-50-00,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1113,OrderType:BloodAnalysis,Provider_ID:Phy777777,timestamp:2013-04-30/00-51-00,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1111,OrderType:ECG,Provider_ID:Nurse121212,timestamp:2013-04-30/01-19-00,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1112,OrderType:BloodSample,Provider_ID:Nurse121212,timestamp:2013-04-30/01-32-00,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1113,OrderType:BloodAnalysis,Provider_ID:Nurse121212,timestamp:2013-04-30/01-47-00,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:PhysicianInED,timestamp:2013-04-30/01-54-00,Unit_ID:ED,Location_ID:AssessmentRoom12,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:PhysicianOutED,timestamp:2013-04-30/02-21-00,Unit_ID:ED,Location_ID:AssessmentRoom12,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:PatientAdmittedWithNoBed,timestamp:2013-04-30/02-23-00,Unit_ID:CW,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:BedCleanUpRequest,timestamp:2013-04-30/02-28-00,Unit_ID:CW,Location_ID:R106')
		jmsService.send(queue:'SME_Event','event:HouseKeepingInCW,timestamp:2013-04-30/02-32-00,Unit_ID:CW,Location_ID:R106,HouseKeeping_ID:HK44444')
		jmsService.send(queue:'SME_Event','event:HouseKeepingOutCW,timestamp:2013-04-30/02-41-00,Unit_ID:CW,Location_ID:R106,HouseKeeping_ID:HK44444')
		jmsService.send(queue:'SME_Event','event:PatientAdmittedWithBed,timestamp:2013-04-30/02-59-00,Location_ID:R106,Unit_ID:CW,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CW,timestamp:2013-04-30/03-01-00,Provider_ID:Nurse121212,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:PatientOutED,timestamp:2013-04-30/03-09-00,Unit_ID:ED,Location_ID:AssessmentRoom12,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:PatientInCW,timestamp:2013-04-30/03-18-00,Unit_ID:CW,Location_ID:R106,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:ProceduresScheduled,timestamp:2013-04-30/03-39-00,ProcedureScheduledTime:2013-04-30/03-40-00,Unit_ID:CCL,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CCL,timestamp:2013-04-30/03-52-00,Provider_ID:Nurse121212,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:PatientOutCW,timestamp:2013-04-30/04-11-00,Unit_ID:CW,Location_ID:R106,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:PatientInCCL,timestamp:2013-04-30/04-27-00,Unit_ID:CCL,Location_ID:CCLRoom1,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:ProcedureStarted,timestamp:2013-04-30/04-40-00,Provider_ID:Phy777777,Procedure_Type:Angiogram,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:ProcedureCompleted,timestamp:2013-04-30/04-58-00,Provider_ID:Phy777777,Procedure_Type:Angiogram,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:ProcedureStarted,timestamp:2013-04-30/05-00-00,Provider_ID:Phy777777,Procedure_Type:PCI,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:ProcedureCompleted,timestamp:2013-04-30/05-24-00,Provider_ID:Phy777777,Procedure_Type:PCI,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CW,timestamp:2013-04-30/05-29-00,Provider_ID:Nurse121212,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:PatientOutCCL,timestamp:2013-04-30/05-40-00,Unit_ID:CCL,Location_ID:CCLRoom1,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:PatientInCW,timestamp:2013-04-30/06-02-00,Unit_ID:CW,Location_ID:R106,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:PhysicianInCW,timestamp:2013-04-30/06-15-00,Unit_ID:CW,Location_ID:R106,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:PhysicianOutCW,timestamp:2013-04-30/06-45-00,Unit_ID:CW,Location_ID:R106,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:DischargeRequest,Provider_ID:Phy777777,timestamp:2013-04-30/07-13-00,Unit_ID:CW,Patient_ID:Pa222223')
		jmsService.send(queue:'SME_Event','event:PatientOutCW,timestamp:2013-04-30/09-02-00,Unit_ID:CW,Location_ID:R106,Patient_ID:Pa222223')
		
		/*  Patient Pa111117-------*/
		/*jmsService.send(queue:'SME_Event','event:TriageScore,timestamp:2013-04-30/05-01-00,Provider_ID:Nurse745774,CTAS:2,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:PatientInED,timestamp:2013-04-30/05-10-00,Location_ID:ED1,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:PhysicianInED,timestamp:2013-04-30/05-26-00,Unit_ID:ED,Location_ID:ED1,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:PhysicianOutED,timestamp:2013-04-30/05-45-00,Unit_ID:ED,Location_ID:ED1,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1111,OrderType:ECG,Provider_ID:Phy777777,timestamp:2013-04-30/05-49-00,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1112,OrderType:BloodSample,Provider_ID:Phy777777,timestamp:2013-04-30/05-50-00,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1113,OrderType:BloodAnalysis,Provider_ID:Phy777777,timestamp:2013-04-30/05-51-00,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1111,OrderType:ECG,Provider_ID:Nurse121212,timestamp:2013-04-30/06-05-00,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1112,OrderType:BloodSample,Provider_ID:Nurse121212,timestamp:2013-04-30/06-10-00,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1113,OrderType:BloodAnalysis,Provider_ID:Nurse121212,timestamp:2013-04-30/06-40-00,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:PhysicianInED,timestamp:2013-04-30/07-05-00,Unit_ID:ED,Location_ID:ED1,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:PhysicianOutED,timestamp:2013-04-30/07-50-00,Unit_ID:ED,Location_ID:ED1,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:PatientAdmittedWithNoBed,timestamp:2013-04-30/08-03-10,Unit_ID:CW,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:BedCleanUpRequest,timestamp:2013-04-30/12-10-00,Unit_ID:CW,Location_ID:R105')
		jmsService.send(queue:'SME_Event','event:HouseKeepingInCW,timestamp:2013-04-30/12-45-00,Unit_ID:CW,Location_ID:R105,HouseKeeping_ID:HK44444')
		jmsService.send(queue:'SME_Event','event:HouseKeepingOutCW,timestamp:2013-04-30/13-25-00,Unit_ID:CW,Location_ID:R105,HouseKeeping_ID:HK44444')
		jmsService.send(queue:'SME_Event','event:PatientAdmittedWithBed,timestamp:2013-04-30/13-29-00,Location_ID:R105,Unit_ID:CW,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CW,timestamp:2013-04-30/13-41-00,Provider_ID:Nurse121212,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:PatientOutED,timestamp:2013-04-30/14-07-00,Unit_ID:ED,Location_ID:ED1,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:PatientInCW,timestamp:2013-04-30/14-25-00,Unit_ID:CW,Location_ID:R105,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:ProceduresScheduled,timestamp:2013-04-30/14-30-00,ProcedureScheduledTime:2013-04-30/18-00-00,Unit_ID:CCL,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CCL,timestamp:2013-04-30/17-05-00,Provider_ID:Nurse121212,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:PatientOutCW,timestamp:2013-04-30/17-31-00,Unit_ID:CW,Location_ID:R105,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:PatientInCCL,timestamp:2013-04-30/17-47-00,Unit_ID:CCL,Location_ID:CCLRoom1,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:ProcedureStarted,timestamp:2013-04-30/18-00-00,Provider_ID:Phy777777,Procedure_Type:Angiogram,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:ProcedureCompleted,timestamp:2013-04-30/18-50-00,Provider_ID:Phy777777,Procedure_Type:Angiogram,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:ProcedureStarted,timestamp:2013-04-30/18-52-00,Provider_ID:Phy777777,Procedure_Type:PCI,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:ProcedureCompleted,timestamp:2013-04-30/19-30-00,Provider_ID:Phy777777,Procedure_Type:PCI,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CW,timestamp:2013-04-30/19-39-00,Provider_ID:Nurse121212,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:PatientOutCCL,timestamp:2013-04-30/19-50-00,Unit_ID:CCL,Location_ID:CCLRoom1,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:PatientInCW,timestamp:2013-04-30/20-02-00,Unit_ID:CW,Location_ID:R105,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:PhysicianInCW,timestamp:2013-04-30/20-15-00,Unit_ID:CW,Location_ID:R105,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:PhysicianOutCW,timestamp:2013-04-30/20-30-00,Unit_ID:CW,Location_ID:R105,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:DischargeRequest,Provider_ID:Phy777777,timestamp:2013-04-30/20-31-00,Unit_ID:CW,Patient_ID:Pa111117')
		jmsService.send(queue:'SME_Event','event:PatientOutCW,timestamp:2013-04-30/23-00-00,Unit_ID:CW,Location_ID:R105,Patient_ID:Pa111117')
		
		
		/*  Patient Pa111118-------*/
		jmsService.send(queue:'SME_Event','event:TriageScore,timestamp:2013-04-30/06-01-00,Provider_ID:Nurse745774,CTAS:2,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:PatientInED,timestamp:2013-04-30/06-10-00,Location_ID:ED1,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:PhysicianInED,timestamp:2013-04-30/06-26-00,Unit_ID:ED,Location_ID:ED1,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:PhysicianOutED,timestamp:2013-04-30/06-45-00,Unit_ID:ED,Location_ID:ED1,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1111,OrderType:ECG,Provider_ID:Phy777777,timestamp:2013-04-30/06-49-00,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1112,OrderType:BloodSample,Provider_ID:Phy777777,timestamp:2013-04-30/06-50-00,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1113,OrderType:BloodAnalysis,Provider_ID:Phy777777,timestamp:2013-04-30/06-51-00,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1111,OrderType:ECG,Provider_ID:Nurse121212,timestamp:2013-04-30/07-05-00,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1112,OrderType:BloodSample,Provider_ID:Nurse121212,timestamp:2013-04-30/07-10-00,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1113,OrderType:BloodAnalysis,Provider_ID:Nurse121212,timestamp:2013-04-30/07-40-00,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:PhysicianInED,timestamp:2013-04-30/08-05-00,Unit_ID:ED,Location_ID:ED1,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:PhysicianOutED,timestamp:2013-04-30/08-50-00,Unit_ID:ED,Location_ID:ED1,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:PatientAdmittedWithNoBed,timestamp:2013-04-30/08-03-10,Unit_ID:CW,Patient_ID:Pa111118')
		
		jmsService.send(queue:'SME_Event','event:BedCleanUpRequest,timestamp:2013-04-30/12-10-00,Unit_ID:CW,Location_ID:R101')
		jmsService.send(queue:'SME_Event','event:HouseKeepingInCW,timestamp:2013-04-30/12-45-00,Unit_ID:CW,Location_ID:R101,HouseKeeping_ID:HK44444')
		jmsService.send(queue:'SME_Event','event:HouseKeepingOutCW,timestamp:2013-04-30/13-25-00,Unit_ID:CW,Location_ID:R101,HouseKeeping_ID:HK44444')
		jmsService.send(queue:'SME_Event','event:PatientAdmittedWithBed,timestamp:2013-04-30/13-29-00,Location_ID:R101,Unit_ID:CW,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CW,timestamp:2013-04-30/13-41-00,Provider_ID:Nurse121212,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:PatientOutED,timestamp:2013-04-30/14-07-00,Unit_ID:ED,Location_ID:ED1,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:PatientInCW,timestamp:2013-04-30/14-25-00,Unit_ID:CW,Location_ID:R101,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:ProceduresScheduled,timestamp:2013-04-30/14-30-00,ProcedureScheduledTime:2013-04-30/18-00-00,Unit_ID:CCL,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CCL,timestamp:2013-04-30/17-05-00,Provider_ID:Nurse121212,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:PatientOutCW,timestamp:2013-04-30/17-31-00,Unit_ID:CW,Location_ID:R101,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:PatientInCCL,timestamp:2013-04-30/17-47-00,Unit_ID:CCL,Location_ID:CCLRoom1,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:ProcedureStarted,timestamp:2013-04-30/18-00-00,Provider_ID:Phy777777,Procedure_Type:Angiogram,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:ProcedureCompleted,timestamp:2013-04-30/18-50-00,Provider_ID:Phy777777,Procedure_Type:Angiogram,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:ProcedureStarted,timestamp:2013-04-30/19-00-00,Provider_ID:Phy777777,Procedure_Type:PCI,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:ProcedureCompleted,timestamp:2013-04-30/19-30-00,Provider_ID:Phy777777,Procedure_Type:PCI,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CW,timestamp:2013-04-30/19-49-00,Provider_ID:Nurse121212,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:PatientOutCCL,timestamp:2013-04-30/19-50-00,Unit_ID:CCL,Location_ID:CCLRoom1,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:PatientInCW,timestamp:2013-04-30/20-02-00,Unit_ID:CW,Location_ID:R105,Patient_ID:Pa111118')
		jmsService.send(queue:'SME_Event','event:PhysicianInCW,timestamp:2013-04-30/20-15-00,Unit_ID:CW,Location_ID:R101,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:PhysicianOutCW,timestamp:2013-04-30/20-33-00,Unit_ID:CW,Location_ID:R101,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:DischargeRequest,Provider_ID:Phy777777,timestamp:2013-04-30/20-39-00,Unit_ID:CW,Patient_ID:Pa111118')
		//jmsService.send(queue:'SME_Event','event:PatientOutCW,timestamp:2013-04-30/21-00-00,Unit_ID:CW,Location_ID:R101,Patient_ID:Pa111118')
		
		/*  Patient Pa222224-------*/
		jmsService.send(queue:'SME_Event','event:TriageScore,timestamp:2013-04-30/09-34-00,Provider_ID:Nurse745774,CTAS:1,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:PatientInED,timestamp:2013-04-30/09-50-00,Location_ID:AssessmentRoom12,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:PhysicianInED,timestamp:2013-04-30/10-16-00,Unit_ID:ED,Location_ID:AssessmentRoom12,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:PhysicianOutED,timestamp:2013-04-30/10-30-00,Unit_ID:ED,Location_ID:AssessmentRoom12,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1111,OrderType:ECG,Provider_ID:Phy777777,timestamp:2013-04-30/10-49-00,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1112,OrderType:BloodSample,Provider_ID:Phy777777,timestamp:2013-04-30/10-50-00,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1113,OrderType:BloodAnalysis,Provider_ID:Phy777777,timestamp:2013-04-30/10-51-00,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1111,OrderType:ECG,Provider_ID:Nurse121212,timestamp:2013-04-30/11-09-00,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1112,OrderType:BloodSample,Provider_ID:Nurse121212,timestamp:2013-04-30/12-00-00,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1113,OrderType:BloodAnalysis,Provider_ID:Nurse121212,timestamp:2013-04-30/12-09-00,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:PhysicianInED,timestamp:2013-04-30/12-19-00,Unit_ID:ED,Location_ID:AssessmentRoom12,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:PhysicianOutED,timestamp:2013-04-30/12-21-00,Unit_ID:ED,Location_ID:AssessmentRoom12,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:PatientAdmittedWithNoBed,timestamp:2013-04-30/12-23-00,Unit_ID:CW,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:BedCleanUpRequest,timestamp:2013-04-30/12-24-00,Unit_ID:CW,Location_ID:R106')
		jmsService.send(queue:'SME_Event','event:HouseKeepingInCW,timestamp:2013-04-30/12-32-00,Unit_ID:CW,Location_ID:R106,HouseKeeping_ID:HK44444')
		jmsService.send(queue:'SME_Event','event:HouseKeepingOutCW,timestamp:2013-04-30/12-39-00,Unit_ID:CW,Location_ID:R106,HouseKeeping_ID:HK44444')
		jmsService.send(queue:'SME_Event','event:PatientAdmittedWithBed,timestamp:2013-04-30/12-59-00,Location_ID:R106,Unit_ID:CW,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CW,timestamp:2013-04-30/13-09-00,Provider_ID:Nurse121212,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:PatientOutED,timestamp:2013-04-30/13-24-00,Unit_ID:ED,Location_ID:AssessmentRoom12,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:PatientInCW,timestamp:2013-04-30/13-39-00,Unit_ID:CW,Location_ID:R106,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:ProceduresScheduled,timestamp:2013-04-30/13-49-00,ProcedureScheduledTime:2013-04-30/14-40-00,Unit_ID:CCL,Patient_ID:Pa222224')
		/*jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CCL,timestamp:2013-04-30/14-09-00,Provider_ID:Nurse121212,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:PatientOutCW,timestamp:2013-04-30/14-31-00,Unit_ID:CW,Location_ID:R106,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:PatientInCCL,timestamp:2013-04-30/14-47-00,Unit_ID:CCL,Location_ID:CCLRoom1,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:ProcedureStarted,timestamp:2013-04-30/15-10-00,Provider_ID:Phy777777,Procedure_Type:Angiogram,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:ProcedureCompleted,timestamp:2013-04-30/15-50-00,Provider_ID:Phy777777,Procedure_Type:Angiogram,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:ProcedureStarted,timestamp:2013-04-30/16-00-00,Provider_ID:Phy777777,Procedure_Type:PCI,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:ProcedureCompleted,timestamp:2013-04-30/16-30-00,Provider_ID:Phy777777,Procedure_Type:PCI,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CW,timestamp:2013-04-30/17-29-00,Provider_ID:Nurse121212,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:PatientOutCCL,timestamp:2013-04-30/17-40-00,Unit_ID:CCL,Location_ID:CCLRoom1,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:PatientInCW,timestamp:2013-04-30/18-02-00,Unit_ID:CW,Location_ID:R106,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:PhysicianInCW,timestamp:2013-04-30/18-15-00,Unit_ID:CW,Location_ID:R106,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:PhysicianOutCW,timestamp:2013-04-30/18-45-00,Unit_ID:CW,Location_ID:R106,Physician_ID:Phy777777')
		jmsService.send(queue:'SME_Event','event:DischargeRequest,Provider_ID:Phy777777,timestamp:2013-04-30/19-13-00,Unit_ID:CW,Patient_ID:Pa222224')
		jmsService.send(queue:'SME_Event','event:PatientOutCW,timestamp:2013-04-30/20-02-00,Unit_ID:CW,Location_ID:R106,Patient_ID:Pa222224')
		*/

		
		
		
	   /*  Patient Pa123448-------*/
	   jmsService.send(queue:'SME_Event','event:TriageScore,timestamp:2013-04-30/08-08-00,Provider_ID:Nurse745774,CTAS:2,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:PatientInED,timestamp:2013-04-30/08-10-00,Location_ID:ED2,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:PhysicianInED,timestamp:2013-04-30/08-12-00,Unit_ID:ED,Location_ID:ED2,Physician_ID:Phy777777')
	   jmsService.send(queue:'SME_Event','event:PhysicianOutED,timestamp:2013-04-30/08-15-00,Unit_ID:ED,Location_ID:ED2,Physician_ID:Phy777777')
	   jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1111,OrderType:ECG,Provider_ID:Phy777777,timestamp:2013-04-30/08-19-00,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1112,OrderType:BloodSample,Provider_ID:Phy777777,timestamp:2013-04-30/08-19-10,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1113,OrderType:BloodAnalysis,Provider_ID:Phy777777,timestamp:2013-04-30/08-20-00,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1111,OrderType:ECG,Provider_ID:Nurse121212,timestamp:2013-04-30/09-05-00,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1112,OrderType:BloodSample,Provider_ID:Nurse121212,timestamp:2013-04-30/09-10-00,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1113,OrderType:BloodAnalysis,Provider_ID:Nurse121212,timestamp:2013-04-30/09-40-00,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:PhysicianInED,timestamp:2013-04-30/10-00-00,Unit_ID:ED,Location_ID:ED2,Physician_ID:Phy777777')
	   jmsService.send(queue:'SME_Event','event:PhysicianOutED,timestamp:2013-04-30/10-12-00,Unit_ID:ED,Location_ID:ED2,Physician_ID:Phy777777')
	   jmsService.send(queue:'SME_Event','event:PatientAdmittedWithNoBed,timestamp:2013-04-30/10-13-10,Unit_ID:CW,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:BedCleanUpRequest,timestamp:2013-04-30/10-20-00,Unit_ID:CW,Location_ID:R103')
	   jmsService.send(queue:'SME_Event','event:HouseKeepingInCW,timestamp:2013-04-30/10-25-00,Unit_ID:CW,Location_ID:R103,HouseKeeping_ID:HK44444')
	   jmsService.send(queue:'SME_Event','event:HouseKeepingOutCW,timestamp:2013-04-30/10-35-00,Unit_ID:CW,Location_ID:R103,HouseKeeping_ID:HK44444')
	   jmsService.send(queue:'SME_Event','event:PatientAdmittedWithBed,timestamp:2013-04-30/10-37-00,Location_ID:R103,Unit_ID:CW,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CW,timestamp:2013-04-30/10-39-00,Provider_ID:Nurse121212,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:PatientOutED,timestamp:2013-04-30/11-02-00,Unit_ID:ED,Location_ID:ED2,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:PatientInCW,timestamp:2013-04-30/11-13-00,Unit_ID:CW,Location_ID:R103,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:ProceduresScheduled,timestamp:2013-04-30/11-13-00,ProcedureScheduledTime:2013-04-30/03-00-00,Unit_ID:CCL,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CCL,timestamp:2013-04-30/12-05-00,Provider_ID:Nurse121212,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:PatientOutCW,timestamp:2013-04-30/12-31-00,Unit_ID:CW,Location_ID:R103,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:PatientInCCL,timestamp:2013-04-30/12-47-00,Unit_ID:CCL,Location_ID:CCLRoom1,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:ProcedureStarted,timestamp:2013-04-30/13-00-00,Provider_ID:Phy777777,Procedure_Type:Angiogram,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:ProcedureCompleted,timestamp:2013-04-30/13-50-00,Provider_ID:Phy777777,Procedure_Type:Angiogram,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:ProcedureStarted,timestamp:2013-04-30/14-00-00,Provider_ID:Phy777777,Procedure_Type:PCI,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:ProcedureCompleted,timestamp:2013-04-30/14-30-00,Provider_ID:Phy777777,Procedure_Type:PCI,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CW,timestamp:2013-04-30/15-29-00,Provider_ID:Nurse121212,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:PatientOutCCL,timestamp:2013-04-30/15-40-00,Unit_ID:CCL,Location_ID:CCLRoom1,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:PatientInCW,timestamp:2013-04-30/16-02-00,Unit_ID:CW,Location_ID:R103,Patient_ID:Pa123448')
	   jmsService.send(queue:'SME_Event','event:PhysicianInCW,timestamp:2013-04-30/16-15-00,Unit_ID:CW,Location_ID:R103,Physician_ID:Phy777777')
	   jmsService.send(queue:'SME_Event','event:PhysicianOutCW,timestamp:2013-04-30/16-30-00,Unit_ID:CW,Location_ID:R103,Physician_ID:Phy777777')
	   jmsService.send(queue:'SME_Event','event:DischargeRequest,Provider_ID:Phy777777,timestamp:2013-04-30/16-31-00,Unit_ID:CW,Patient_ID:Pa123448')
	   //jmsService.send(queue:'SME_Event','event:PatientOutCW,timestamp:2013-04-30/17-00-00,Unit_ID:CW,Location_ID:R103,Patient_ID:Pa123448')
	  
	   /*  Patient Pa123458-------*/
	   jmsService.send(queue:'SME_Event','event:TriageScore,timestamp:2013-04-30/11-10-00,Provider_ID:Nurse745774,CTAS:2,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:PatientInED,timestamp:2013-04-30/11-11-00,Location_ID:ED3,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:PhysicianInED,timestamp:2013-04-30/11-20-00,Unit_ID:ED,Location_ID:ED3,Physician_ID:Phy777777')
	   jmsService.send(queue:'SME_Event','event:PhysicianOutED,timestamp:2013-04-30/11-45-00,Unit_ID:ED,Location_ID:ED3,Physician_ID:Phy777777')
	   jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1111,OrderType:ECG,Provider_ID:Phy777777,timestamp:2013-04-30/11-49-00,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1112,OrderType:BloodSample,Provider_ID:Phy777777,timestamp:2013-04-30/11-50-00,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:OrderRequest,Order_ID:Order1113,OrderType:BloodAnalysis,Provider_ID:Phy777777,timestamp:2013-04-30/11-51-00,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1111,OrderType:ECG,Provider_ID:Nurse121212,timestamp:2013-04-30/12-05-00,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1112,OrderType:BloodSample,Provider_ID:Nurse121212,timestamp:2013-04-30/12-10-00,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:OrderRequestCompleted,Order_ID:Order1113,OrderType:BloodAnalysis,Provider_ID:Nurse121212,timestamp:2013-04-30/12-40-00,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:PhysicianInED,timestamp:2013-04-30/13-05-00,Unit_ID:ED,Location_ID:ED3,Physician_ID:Phy777777')
	   jmsService.send(queue:'SME_Event','event:PhysicianOutED,timestamp:2013-04-30/14-00-00,Unit_ID:ED,Location_ID:ED3,Physician_ID:Phy777777')
	   jmsService.send(queue:'SME_Event','event:PatientAdmittedWithNoBed,timestamp:2013-04-30/14-03-10,Unit_ID:CW,Patient_ID:Pa123458')
	   /*jmsService.send(queue:'SME_Event','event:BedCleanUpRequest,timestamp:2013-04-30/14-10-00,Unit_ID:CW,Location_ID:R102')
	   jmsService.send(queue:'SME_Event','event:HouseKeepingInCW,timestamp:2013-04-30/14-24-00,Unit_ID:CW,Location_ID:R102,HouseKeeping_ID:HK44444')
	   jmsService.send(queue:'SME_Event','event:HouseKeepingOutCW,timestamp:2013-04-30/14-50-00,Unit_ID:CW,Location_ID:R102,HouseKeeping_ID:HK44444')
	   jmsService.send(queue:'SME_Event','event:PatientAdmittedWithBed,timestamp:2013-04-30/14-53-00,Location_ID:R102,Unit_ID:CW,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CW,timestamp:2013-04-30/14-57-00,Provider_ID:Nurse121212,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:PatientOutED,timestamp:2013-04-30/15-07-00,Unit_ID:ED,Location_ID:ED3,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:PatientInCW,timestamp:2013-04-30/15-25-00,Unit_ID:CW,Location_ID:R102,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:ProceduresScheduled,timestamp:2013-04-30/15-31-00,ProcedureScheduledTime:2013-04-30/16-20-00,Unit_ID:CCL,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CCL,timestamp:2013-04-30/16-05-00,Provider_ID:Nurse121212,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:PatientOutCW,timestamp:2013-04-30/16-11-00,Unit_ID:CW,Location_ID:R102,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:PatientInCCL,timestamp:2013-04-30/16-17-00,Unit_ID:CCL,Location_ID:CCLRoom1,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:ProcedureStarted,timestamp:2013-04-30/16-20-00,Provider_ID:Phy777777,Procedure_Type:Angiogram,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:ProcedureCompleted,timestamp:2013-04-30/16-50-00,Provider_ID:Phy777777,Procedure_Type:Angiogram,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:ProcedureStarted,timestamp:2013-04-30/17-00-00,Provider_ID:Phy777777,Procedure_Type:PCI,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:ProcedureCompleted,timestamp:2013-04-30/17-20-00,Provider_ID:Phy777777,Procedure_Type:PCI,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:PatientTransportRequest,Unit_ID:CW,timestamp:2013-04-30/17-29-00,Provider_ID:Nurse121212,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:PatientOutCCL,timestamp:2013-04-30/17-40-00,Unit_ID:CCL,Location_ID:CCLRoom1,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:PatientInCW,timestamp:2013-04-30/18-02-00,Unit_ID:CW,Location_ID:R102,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:PhysicianInCW,timestamp:2013-04-30/18-15-00,Unit_ID:CW,Location_ID:R102,Physician_ID:Phy777777')
	   jmsService.send(queue:'SME_Event','event:PhysicianOutCW,timestamp:2013-04-30/18-30-00,Unit_ID:CW,Location_ID:R102,Physician_ID:Phy777777')
	   jmsService.send(queue:'SME_Event','event:DischargeRequest,Provider_ID:Phy777777,timestamp:2013-04-30/18-31-00,Unit_ID:CW,Patient_ID:Pa123458')
	   jmsService.send(queue:'SME_Event','event:PatientOutCW,timestamp:2013-04-30/18-51-00,Unit_ID:CW,Location_ID:R102,Patient_ID:Pa123458')*/
	   
		
		/******** Pa111111***********/
		
		jmsService.send(queue:'SME_Event','event:TriageScore,timestamp:2013-04-30/11-46-00,Provider_ID:Nurse745774,CTAS:2,Patient_ID:Pa111111')
		jmsService.send(queue:'SME_Event','event:PatientInED,timestamp:2013-04-30/12-00-00,Location_ID:AssessmentRoom12,Patient_ID:Pa111111')
		
		/******** Pa111112***********/
		jmsService.send(queue:'SME_Event','event:TriageScore,timestamp:2013-04-30/23-00-00,Provider_ID:Nurse745774,CTAS:2,Patient_ID:Pa111112')
		jmsService.send(queue:'SME_Event','event:PatientInED,timestamp:2013-04-30/23-07-00,Location_ID:ED2,Patient_ID:Pa111112')
		
		/******** Pa111114***********/
		//jmsService.send(queue:'SME_Event','event:TriageScore,timestamp:2013-04-30/23-02-00,Provider_ID:Nurse745774,CTAS:2,Patient_ID:Pa111114')
		//jmsService.send(queue:'SME_Event','event:PatientInED,timestamp:2013-04-30/23-11-00,Location_ID:AssessmentRoom,Patient_ID:Pa111114')
		
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
