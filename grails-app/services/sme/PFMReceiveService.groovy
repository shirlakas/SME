package sme
import javax.jws.*
import java.util.List;
import java.util.Map
import java.util.Date
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes;
import com.sme.handler.EventHandler;
import org.springframework.context.*
import grails.plugin.jms.*
import java.text.SimpleDateFormat
import groovy.xml.MarkupBuilder

class SMEReceiveService {

   static transactional = true
	static expose=['cxfjax']
		
	def patientService
	def roomService
	def monitorService
	def unitService
	def grailsApplication
	def jmsService
	
    def serviceMethod() {
    }
	
	@WebResult(name="pingResponse")
	@WebMethod(operationName="ping")
	String ping(@WebParam(name="message")String message){
		//return the received message
		return message;
	}
	/*************** Start of SME events *************************/

	@WebResult(name="PatientRegisteredResponse")
	@WebMethod(operationName="patientRegistered")
	String patientRegistered(@WebParam(name="patientId")String patientId,
		@WebParam(name="roomId")String roomId,
		@WebParam(name="timestamp")String timestamp){
		//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
		String ts=modifyTimeStamp(timestamp)
			
		//Receiving values sent in event as a GString and sending to message queue
		String msg= "event:PatientRegistered,Patient_ID:$patientId,Room_ID:$roomId,timestamp:$ts"
		jmsService.send(queue:'SME_Event',msg)
		
		return "Message received"
	}
		
	@WebResult(name="triageScoreResponse")
	@WebMethod(operationName="triageScore")
	String triageScore(@WebParam(name="patientId")String patientId,
		@WebParam(name="providerId")String providerId,
		@WebParam(name="ctas")String ctas,
		@WebParam(name="timestamp")String timestamp){
		/********Processing request using the variables provided:*******
		*******   patientId, providerId, ctas, timestamp****************/
		//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss 
		String ts=modifyTimeStamp(timestamp)
		
		//Receiving values sent in event as a GString
		String msg= "event:TriageScore,Patient_ID:$patientId,Provider_ID:$providerId,CTAS:$ctas,timestamp:$ts"
		
		//Sending the event to the message queue
		jmsService.send(queue:'SME_Event',msg)	
		return "Message received"
	}
	
		
		@WebResult(name="OrderRequestResponse")
		@WebMethod(operationName="orderRequest")
		String orderRequest(@WebParam(name="patientId")String patientId,
			@WebParam(name="providerId")String providerId,
			@WebParam(name="orderType")String orderType,
			@WebParam(name="orderId")String orderId,
			@WebParam(name="timestamp")String timestamp){
			
			//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
			String ts=modifyTimeStamp(timestamp)
			
			//Receiving values sent in event as a GString and sending the event to the SME_Event queue
			String msg= "event:OrderRequest,Patient_ID:$patientId,Provider_ID:$providerId,OrderType:$orderType,Order_ID:$orderId,timestamp:$ts"
			jmsService.send(queue:'SME_Event',msg)
						
			return "Message received"
		}
					
			@WebResult(name="OrderRequestCompletedResponse")
			@WebMethod(operationName="orderRequestCompleted")
			String orderRequestCompleted(@WebParam(name="patientId")String patientId,
				@WebParam(name="providerId")String providerId,
				@WebParam(name="orderType")String orderType,
				@WebParam(name="orderId")String orderId,
				@WebParam(name="timestamp")String timestamp){
				
				//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
				String ts=modifyTimeStamp(timestamp)
				
				//Receiving values sent in event as a GString and sending the event to the SME_Event queue
				String msg= "event:OrderRequestCompleted,Patient_ID:$patientId,Provider_ID:$providerId,OrderType:$orderType,Order_ID:$orderId,timestamp:$ts"
				jmsService.send(queue:'SME_Event',msg)
								
				return "Message received"
			}
			
			
	@WebResult(name="RequestReferralResponse")
	@WebMethod(operationName="requestReferral")
	String requestReferral(@WebParam(name="patientId")String patientId,
			@WebParam(name="providerId")String providerId,
			@WebParam(name="referralType")String referralType,
			@WebParam(name="unitId")String unitId,
			@WebParam(name="timestamp")String timestamp){
			
			//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
			String ts=modifyTimeStamp(timestamp)
			
			//Receiving values sent in event as a GString and sending the event to the SME_Event queue
			String msg= "event:RequestReferral,Patient_ID:$patientId,Provider_ID:$providerId,ReferralType:$referralType,Unit_ID:$unitId,timestamp:$ts"
			jmsService.send(queue:'SME_Event',msg)
			
			return "Message received"
		}	
		
	@WebResult(name="BedRequestResponse")
	@WebMethod(operationName="bedRequest")
	String bedRequest(@WebParam(name="patientId")String patientId,
		@WebParam(name="providerId")String providerId,
		@WebParam(name="unitId")String unitId,
		@WebParam(name="timestamp")String timestamp){
		
		//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
		String ts=modifyTimeStamp(timestamp)			
		
		//Receiving values sent in event as a GString and sending the event to the SME_Event queue
		String msg= "event:BedRequest,Patient_ID:$patientId,Provider_ID:$providerId,Unit_ID:$unitId,timestamp:$ts"	
		jmsService.send(queue:'SME_Event',msg)
		
		return "Message received"
	}
		
	@WebResult(name="PatientAdmittedWithNoBedResponse")
	@WebMethod(operationName="patientAdmittedWithNoBed")
	String patientAdmittedWithNoBed(@WebParam(name="patientId")String patientId,
		@WebParam(name="unitId")String unitId,
		@WebParam(name="timestamp")String timestamp){
		
		//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
		String ts=modifyTimeStamp(timestamp)			
		
		//Receiving values sent in event as a GString and sending the event to the SME_Event queue
		String msg= "event:PatientAdmittedWithNoBed,Patient_ID:$patientId,Unit_ID:$unitId,timestamp:$ts"	
		jmsService.send(queue:'SME_Event',msg)
				
		return "Message received"
	}
		
	@WebResult(name="ProceduresScheduledResponse")
	@WebMethod(operationName="proceduresScheduled")
	String proceduresScheduled(@WebParam(name="patientId")String patientId,
		@WebParam(name="procedureScheduledTime")String procedureScheduledTime,
		@WebParam(name="unitId")String unitId,
		@WebParam(name="timestamp")String timestamp){
		
		//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
		String ts=modifyTimeStamp(timestamp)			
		
		//Receiving values sent in event as a GString and sending the event to the SME_Event queue
		String msg= "event:ProceduresScheduled,Patient_ID:$patientId,ProcedureScheduledTime:$procedureScheduledTime,Unit_ID:$unitId,timestamp:$ts"	
		jmsService.send(queue:'SME_Event',msg)
				
		return "Message received"
		
	}
		
	@WebResult(name="PatientAdmittedWithBedResponse")
	@WebMethod(operationName="patientAdmittedWithBed")
	String patientAdmittedWithBed(@WebParam(name="patientId")String patientId,
		@WebParam(name="unitId")String unitId,
		@WebParam(name="locationId")String locationId,
		@WebParam(name="timestamp")String timestamp){
		
		//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
		String ts=modifyTimeStamp(timestamp)			
		
		//Receiving values sent in event as a GString and sending the event to the SME_Event queue
		String msg= "event:PatientAdmittedWithBed,Patient_ID:$patientId,Unit_ID:$unitId,Location_ID:$locationId,timestamp:$ts"	
		jmsService.send(queue:'SME_Event',msg)
				
		return "Message received"
	}
		
	@WebResult(name="PatientTransportRequestResponse")
	@WebMethod(operationName="patientTransportRequest")
	String patientTransportRequest(@WebParam(name="patientId")String patientId,
		@WebParam(name="providerId")String providerId,
		@WebParam(name="unitId")String unitId,
		@WebParam(name="timestamp")String timestamp){
		
		//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
		String ts=modifyTimeStamp(timestamp)			
		
		//Receiving values sent in event as a GString and sending the event to the SME_Event queue
		String msg= "event:PatientTransportRequest,Patient_ID:$patientId,Provider_ID:$providerId,Unit_ID:$unitId,timestamp:$ts"	
		jmsService.send(queue:'SME_Event',msg)
		
		return "Message received"
		
	}
			
	@WebResult(name="ProcedureStartedResponse")
	@WebMethod(operationName="procedureStarted")
	String procedureStarted(@WebParam(name="patientId")String patientId,
		@WebParam(name="providerId")String providerId,
		@WebParam(name="procedureType")String procedureType,
		@WebParam(name="timestamp")String timestamp){
			
		//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
		String ts=modifyTimeStamp(timestamp)
					
		//Receiving values sent in event as a GString and sending the event to the SME_Event queue
		String msg= "event:ProcedureStarted,Patient_ID:$patientId,Provider_ID:$providerId,Procedure_Type:$procedureType,timestamp:$ts"
		jmsService.send(queue:'SME_Event',msg)
		
		return "Message received"
		}
		
		@WebResult(name="ProcedureCompletedResponse")
		@WebMethod(operationName="procedureCompleted")
		String procedureCompleted(@WebParam(name="patientId")String patientId,
			@WebParam(name="providerId")String providerId,
			@WebParam(name="procedureType")String procedureType,
			@WebParam(name="timestamp")String timestamp){
			
			//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
			String ts=modifyTimeStamp(timestamp)
					
			//Receiving values sent in event as a GString and sending the event to the SME_Event queue
			String msg= "event:ProcedureCompleted,Patient_ID:$patientId,Provider_ID:$providerId,Procedure_Type:$procedureType,timestamp:$ts"
			jmsService.send(queue:'SME_Event',msg)
			
			return "Message received"
		}
		
		@WebResult(name="ProcedureUpdatedResponse")
		@WebMethod(operationName="procedureUpdated")
		String procedureUpdated(@WebParam(name="patientId")String patientId,
			@WebParam(name="procedureType")String procedureType,
			@WebParam(name="status")String status,
			@WebParam(name="timestamp")String timestamp){
			
			//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
			String ts=modifyTimeStamp(timestamp)
					
			//Receiving values sent in event as a GString and sending the event to the SME_Event queue
			String msg= "event:ProcedureUpdated,Patient_ID:$patientId,Procedure_Type:$procedureType,Status:$status,timestamp:$ts"
			jmsService.send(queue:'SME_Event',msg)
			
			return "Message received"
		}
		
	@WebResult(name="DischargeRequestResponse")
	@WebMethod(operationName="dischargeRequest")
	String dischargeRequest(@WebParam(name="patientId")String patientId,
		@WebParam(name="providerId")String providerId,
		@WebParam(name="unitId")String unitId,
		@WebParam(name="timestamp")String timestamp){
		
		//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
		String ts=modifyTimeStamp(timestamp)
		
		//Receiving values sent in event as a GString and sending the event to the SME_Event queue
		String msg= "event:DischargeRequest,Patient_ID:$patientId,Provider_ID:$providerId,Unit_ID:$unitId,timestamp:$ts"
		jmsService.send(queue:'SME_Event',msg)
		
		return "Message received"
	}
	
	@WebResult(name="BedCleanUpRequestResponse")
	@WebMethod(operationName="bedCleanUpRequest")
	String bedCleanUpRequest(@WebParam(name="locationId")String locationId,
		@WebParam(name="unitId")String unitId,
		@WebParam(name="timestamp")String timestamp){
			
		//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
		String ts=modifyTimeStamp(timestamp)
			
		//Receiving values sent in event as a GString and sending the event to the SME_Event queue
		String msg= "event:BedCleanUpRequest,Location_ID:$locationId,Unit_ID:$unitId,timestamp:$ts"
		jmsService.send(queue:'SME_Event',msg)
						
		return "Message received"
	}
			
	/**************************** CEP Events	*************************/
	
	@WebResult(name="PatientInEDResponse")
	@WebMethod(operationName="patientInED")
	String patientInED(@WebParam(name="patientId")String patientId,
		@WebParam(name="locationId")String locationId,
		@WebParam(name="timestamp")String timestamp){
			
		//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
		String ts=modifyTimeStamp(timestamp)
		
		// Sending the event to the SME queue
		String msg= "event:PatientInED,Patient_ID:$patientId,Location_ID:$locationId,timestamp:$ts"
		jmsService.send(queue:'SME_Event',msg)
				
		return "Message received"
	}
		
	@WebResult(name="PatientOutEDResponse")
	@WebMethod(operationName="patientOutED")
	String patientOutED(@WebParam(name="patientId")String patientId,
		@WebParam(name="locationId")String locationId,
		@WebParam(name="timestamp")String timestamp){
		
		//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
		String ts=modifyTimeStamp(timestamp)
		
		// Sending the event to the CEP queue
		String msg= "event:PatientOutED,Patient_ID:$patientId,Location_ID:$locationId,timestamp:$ts"
		jmsService.send(queue:'SME_Event',msg)
				
		return "Message received"
	}
	
	@WebResult(name="PatientInCWResponse")
	@WebMethod(operationName="patientInCW")
	String patientInCW(@WebParam(name="patientId")String patientId,
		@WebParam(name="locationId")String locationId,
		@WebParam(name="timestamp")String timestamp){
		
		// Sending the event to the CEP queue
		String msg= "event:PatientInCW,Patient_ID:$patientId,Location_ID:$locationId,timestamp:$timestamp"
		jmsService.send(queue:'SME_Event',msg)
				
		return "Message received"
	}
		
		
	@WebResult(name="PatientInCCLResponse")
	@WebMethod(operationName="patientInCCL")
	String patientInCCL(@WebParam(name="patientId")String patientId,
		@WebParam(name="locationId")String locationId,
		@WebParam(name="timestamp")String timestamp){
		
		//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
		String ts=modifyTimeStamp(timestamp)
		
		// Sending the event to the SME queue
		String msg= "event:PatientInCCL,Patient_ID:$patientId,Location_ID:$locationId,timestamp:$ts"
		jmsService.send(queue:'SME_Event',msg)
				
		return "Message received"
	}	
		
		
	@WebResult(name="PatientOutCWResponse")
	@WebMethod(operationName="patientOutCW")
	String patientOutCW(@WebParam(name="patientId")String patientId,
		@WebParam(name="locationId")String locationId,
		@WebParam(name="timestamp")String timestamp){
		
		//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
		String ts=modifyTimeStamp(timestamp)
		
		// Sending the event to the SME queue
		String msg= "event:PatientOutCW,Patient_ID:$patientId,Location_ID:$locationId,timestamp:$ts"
		jmsService.send(queue:'SME_Event',msg)
				
		return "Message received"
	}	
		
		@WebResult(name="PatientOutCCLResponse")
		@WebMethod(operationName="patientOutCCL")
		String patientOutCCL(@WebParam(name="patientId")String patientId,
			@WebParam(name="locationId")String locationId,
			@WebParam(name="timestamp")String timestamp){
			
			//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
			String ts=modifyTimeStamp(timestamp)
			
			// Sending the event to the SME queue
			String msg= "event:PatientOutCCL,Patient_ID:$patientId,Location_ID:$locationId,timestamp:$ts"
			jmsService.send(queue:'SME_Event',msg)
			
			return "Message received"
		}
			
			@WebResult(name="PhysicianInEDResponse")
			@WebMethod(operationName="physicianInED")
			String physicianInED(@WebParam(name="physicianId")String physicianId,
				@WebParam(name="locationId")String locationId,
				@WebParam(name="timestamp")String timestamp){
				
				//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
				String ts=modifyTimeStamp(timestamp)
				
				// Sending the event to the SME queue
				String msg= "event:PhysicianInED,Physician_ID:$physicianId,Location_ID:$locationId,timestamp:$ts"
				jmsService.send(queue:'SME_Event',msg)
				
				return "Message received"
			}
		
	@WebResult(name="PhysicianInCWResponse")
	@WebMethod(operationName="physicianInCW")
	String physicianInCW(@WebParam(name="physicianId")String physicianId,
		@WebParam(name="locationId")String locationId,
		@WebParam(name="timestamp")String timestamp){
		
		//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
		String ts=modifyTimeStamp(timestamp)
		
		// Sending the event to the CEP queue
		String msg= "event:PhysicianInCW,Physician_ID:$physicianId,Location_ID:$locationId,timestamp:$ts"
		jmsService.send(queue:'SME_Event',msg)
		
		return "Message received"
	}
	
		
		@WebResult(name="PhysicianOutCWResponse")
		@WebMethod(operationName="physicianOutCW")
		String physicianOutCW(@WebParam(name="physicianId")String physicianId,
			@WebParam(name="locationId")String locationId,
			@WebParam(name="timestamp")String timestamp){
			
			//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
			String ts=modifyTimeStamp(timestamp)
			
			// Sending the event to the CEP queue
			String msg= "event:PhysicianOutCW,Physician_ID:$physicianId,Location_ID:$locationId,timestamp:$ts"
			jmsService.send(queue:'SME_Event',msg)
						
			return "Message received"
		}
	@WebResult(name="PhysicianOutEDResponse")
	@WebMethod(operationName="physicianOutED")
	String physicianOutED(@WebParam(name="physicianId")String physicianId,
		@WebParam(name="locationId")String locationId,
		@WebParam(name="timestamp")String timestamp){
		
		//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
		String ts=modifyTimeStamp(timestamp)
				
		// Sending the event to the CEP queue
		String msg= "event:PhysicianOutED,Physician_ID:$physicianId,Location_ID:$locationId,timestamp:$ts"
		jmsService.send(queue:'SME_Event',msg)
				
		return "Message received"
	}	
		
		
		@WebResult(name="HouseKeepingnInCWResponse")
		@WebMethod(operationName="houseKeepingInCW")
		String houseKeepingInCW(@WebParam(name="houseKeepingId")String houseKeepingId,
			@WebParam(name="locationId")String locationId,
			@WebParam(name="timestamp")String timestamp){
			
			//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
			String ts=modifyTimeStamp(timestamp)
			
			// Sending the event to the SME queue
			String msg= "event:HouseKeepingInCW,HouseKeeping_ID:$houseKeepingId,Location_ID:$locationId,timestamp:$ts"
			jmsService.send(queue:'SME_Event',msg)
			
			return "Message received"
		}
		
			
			@WebResult(name="HouseKeepingOutCWResponse")
			@WebMethod(operationName="houseKeepingOutCW")
			String houseKeepingOutCW(@WebParam(name="houseKeepingId")String houseKeepingId,
				@WebParam(name="locationId")String locationId,
				@WebParam(name="timestamp")String timestamp){
				
				//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
				String ts=modifyTimeStamp(timestamp)
				
				// Sending the event to the SME queue
				String msg= "event:HouseKeepingOutCW,HouseKeeping_ID:$houseKeepingId,Location_ID:$locationId,timestamp:$ts"
				jmsService.send(queue:'SME_Event',msg)
								
				return "Message received"
			}
		
	/******************** End of CEP Events ***************************/
	
	def String modifyTimeStamp(String ts){
		Date dte= Date.parse ("yyyy-MM-dd'T'HH:mm:ss", ts)
		String out = dte.format("yyyy-MM-dd/HH-mm-ss")
		return out
	}
}

  
