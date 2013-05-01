package pfm
import javax.jws.*
import java.util.List;
import java.util.Map
import java.util.Date
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes;
import org.springframework.context.*

import com.sme.handler.EventHandler;

import grails.plugin.jms.*
import java.text.SimpleDateFormat
import groovy.xml.MarkupBuilder

public class PFMReceiveService {

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
	public String ping(@WebParam(name="message")String message){
		//return the received message
		return message;
	}
	/*************** Start of SME events *************************/

	@WebResult(name="PatientRegisteredResponse")
	@WebMethod(operationName="patientRegistered")
	public String patientRegistered(@WebParam(name="patientId")String patientId,
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
	public String triageScore(@WebParam(name="patientId")String patientId,
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
		public String orderRequest(@WebParam(name="patientId")String patientId,
			@WebParam(name="providerId")String providerId,
			@WebParam(name="orderId")String orderId,
			@WebParam(name="orderType")String orderType,
			@WebParam(name="timestamp")String timestamp){
			
			//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
			String ts=modifyTimeStamp(timestamp)
			
			//Receiving values sent in event as a GString and sending the event to the SME_Event queue
			String msg= "event:OrderRequest,Patient_ID:$patientId,Provider_ID:$providerId,Order_ID:$orderId,Order_Type:$orderType,timestamp:$ts"
			jmsService.send(queue:'SME_Event',msg)
						
			return "Message received"
		}
					
			@WebResult(name="OrderRequestCompletedResponse")
			@WebMethod(operationName="orderRequestCompleted")
			public String orderRequestCompleted(@WebParam(name="patientId")String patientId,
				@WebParam(name="providerId")String providerId,
				@WebParam(name="orderId")String orderId,
				@WebParam(name="orderType")String orderType,
				@WebParam(name="timestamp")String timestamp){
				
				//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
				String ts=modifyTimeStamp(timestamp)
				
				//Receiving values sent in event as a GString and sending the event to the SME_Event queue
				String msg= "event:OrderRequestCompleted,Patient_ID:$patientId,Provider_ID:$providerId,Order_ID:$orderId,Order_Type:$orderType,timestamp:$ts"
				jmsService.send(queue:'SME_Event',msg)
								
				return "Message received"
			}
			
			
	@WebResult(name="RequestReferralResponse")
	@WebMethod(operationName="requestReferral")
	public String requestReferral(@WebParam(name="patientId")String patientId,
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
	public String bedRequest(@WebParam(name="patientId")String patientId,
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
	public String patientAdmittedWithNoBed(@WebParam(name="patientId")String patientId,
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
	public String proceduresScheduled(@WebParam(name="patientId")String patientId,
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
	public String patientAdmittedWithBed(@WebParam(name="patientId")String patientId,
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
	public String patientTransportRequest(@WebParam(name="patientId")String patientId,
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
	public String procedureStarted(@WebParam(name="patientId")String patientId,
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
		public String procedureCompleted(@WebParam(name="patientId")String patientId,
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
		public String procedureUpdated(@WebParam(name="patientId")String patientId,
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
	public String dischargeRequest(@WebParam(name="patientId")String patientId,
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
	public String bedCleanUpRequest(@WebParam(name="locationId")String locationId,
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
	public String patientInED(@WebParam(name="patientId")String patientId,
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
	public String patientOutED(@WebParam(name="patientId")String patientId,
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
	public String patientInCW(@WebParam(name="patientId")String patientId,
		@WebParam(name="locationId")String locationId,
		@WebParam(name="timestamp")String timestamp){
		
		//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
		String ts=modifyTimeStamp(timestamp)
		
		// Sending the event to the CEP queue
		String msg= "event:PatientInCW,Patient_ID:$patientId,Location_ID:$locationId,timestamp:$ts"
		jmsService.send(queue:'SME_Event',msg)
				
		return "Message received"
	}
		
		
	@WebResult(name="PatientInCCLResponse")
	@WebMethod(operationName="patientInCCL")
	public String patientInCCL(@WebParam(name="patientId")String patientId,
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
	public String patientOutCW(@WebParam(name="patientId")String patientId,
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
		public String patientOutCCL(@WebParam(name="patientId")String patientId,
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
			public String physicianInED(@WebParam(name="physicianId")String physicianId,
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
	public String physicianInCW(@WebParam(name="physicianId")String physicianId,
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
		public String physicianOutCW(@WebParam(name="physicianId")String physicianId,
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
	public String physicianOutED(@WebParam(name="physicianId")String physicianId,
		@WebParam(name="locationId")String locationId,
		@WebParam(name="timestamp")String timestamp){
		
		//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
		String ts=modifyTimeStamp(timestamp)
				
		// Sending the event to the CEP queue
		String msg= "event:PhysicianOutED,Physician_ID:$physicianId,Location_ID:$locationId,timestamp:$ts"
		jmsService.send(queue:'SME_Event',msg)
				
		return "Message received"
	}	
		
	/*	@WebResult(name="TransportInEDResponse")
		@WebMethod(operationName="transportInED")
		public String transportInED(@WebParam(name="transportId")String transportId,
			@WebParam(name="locationId")String locationId,
			@WebParam(name="timestamp")String timestamp){
				
			//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
			String ts=modifyTimeStamp(timestamp)
			
			// Sending the event to the SME queue
			String msg= "event:TransportInED,Transport_ID:$transportId,Location_ID:$locationId,timestamp:$ts"
			jmsService.send(queue:'SME_Event',msg)
					
			return "Message received"
		}
			
		@WebResult(name="TransportOutEDResponse")
		@WebMethod(operationName="transportOutED")
		public String transportOutED(@WebParam(name="transportId")String transportId,
			@WebParam(name="locationId")String locationId,
			@WebParam(name="timestamp")String timestamp){
			
			//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
			String ts=modifyTimeStamp(timestamp)
			
			// Sending the event to the CEP queue
			String msg= "event:TransportOutED,Transport_ID:$transportId,Location_ID:$locationId,timestamp:$ts"
			jmsService.send(queue:'SME_Event',msg)
					
			return "Message received"
		}
		
		@WebResult(name="TransportInCWResponse")
		@WebMethod(operationName="transportInCW")
		public String transportInCW(@WebParam(name="transportId")String transportId,
			@WebParam(name="locationId")String locationId,
			@WebParam(name="timestamp")String timestamp){
			
			// Sending the event to the CEP queue
			String msg= "event:TransportInCW,Transport_ID:$transportId,Location_ID:$locationId,timestamp:$timestamp"
			jmsService.send(queue:'SME_Event',msg)
					
			return "Message received"
		}
			
			
		@WebResult(name="TransportInCCLResponse")
		@WebMethod(operationName="transportInCCL")
		public String transportInCCL(@WebParam(name="transportId")String transportId,
			@WebParam(name="locationId")String locationId,
			@WebParam(name="timestamp")String timestamp){
			
			//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
			String ts=modifyTimeStamp(timestamp)
			
			// Sending the event to the SME queue
			String msg= "event:TransportInCCL,Transport_ID:$transportId,Location_ID:$locationId,timestamp:$ts"
			jmsService.send(queue:'SME_Event',msg)
					
			return "Message received"
		}
			
			
		@WebResult(name="TransportOutCWResponse")
		@WebMethod(operationName="transportOutCW")
		public String transportOutCW(@WebParam(name="transportId")String transportId,
			@WebParam(name="locationId")String locationId,
			@WebParam(name="timestamp")String timestamp){
			
			//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
			String ts=modifyTimeStamp(timestamp)
			
			// Sending the event to the SME queue
			String msg= "event:TransportOutCW,Transport_ID:$transportId,Location_ID:$locationId,timestamp:$ts"
			jmsService.send(queue:'SME_Event',msg)
					
			return "Message received"
		}
			
			@WebResult(name="TransportOutCCLResponse")
			@WebMethod(operationName="transportOutCCL")
			public String transportOutCCL(@WebParam(name="transportId")String transportId,
				@WebParam(name="locationId")String locationId,
				@WebParam(name="timestamp")String timestamp){
				
				//Converting timestamp from String with format yyyy-MM-ddTHH:mm:ss to another String with format yyyy-MM-dd/HH-mm-ss
				String ts=modifyTimeStamp(timestamp)
				
				// Sending the event to the SME queue
				String msg= "event:TransportOutCCL,Transport_ID:$transportId,Location_ID:$locationId,timestamp:$ts"
				jmsService.send(queue:'SME_Event',msg)
				
				return "Message received"
			}
		*/
		@WebResult(name="HouseKeepingnInCWResponse")
		@WebMethod(operationName="houseKeepingInCW")
		public String houseKeepingInCW(@WebParam(name="houseKeepingId")String houseKeepingId,
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
			public String houseKeepingOutCW(@WebParam(name="houseKeepingId")String houseKeepingId,
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

  
