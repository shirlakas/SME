
package patientflowmonitoring;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.6
 * 2013-01-10T12:28:08.037-05:00
 * Generated source version: 2.4.6
 * 
 */
public final class PFMServerService_PFMServerServicePort_Client {

    private static final QName SERVICE_NAME = new QName("http://patientflowmonitoring/", "PFMServerServiceService");

    private PFMServerService_PFMServerServicePort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = PFMServerServiceService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        PFMServerServiceService ss = new PFMServerServiceService(wsdlURL, SERVICE_NAME);
        PFMServerService port = ss.getPFMServerServicePort();  
        
        {
        System.out.println("Invoking patientRegistered...");
        java.lang.String _patientRegistered_patientId = "";
        java.lang.String _patientRegistered_roomId = "";
        java.lang.String _patientRegistered_timestamp = "";
        java.lang.String _patientRegistered__return = port.patientRegistered(_patientRegistered_patientId, _patientRegistered_roomId, _patientRegistered_timestamp);
        System.out.println("patientRegistered.result=" + _patientRegistered__return);


        }
        {
        System.out.println("Invoking requestReferral...");
        java.lang.String _requestReferral_patientId = "";
        java.lang.String _requestReferral_providerId = "";
        java.lang.String _requestReferral_referralType = "";
        java.lang.String _requestReferral_unitId = "";
        java.lang.String _requestReferral_timestamp = "";
        java.lang.String _requestReferral__return = port.requestReferral(_requestReferral_patientId, _requestReferral_providerId, _requestReferral_referralType, _requestReferral_unitId, _requestReferral_timestamp);
        System.out.println("requestReferral.result=" + _requestReferral__return);


        }
        {
        System.out.println("Invoking dischargeCompleted...");
        java.lang.String _dischargeCompleted_patientId = "";
        java.lang.String _dischargeCompleted_unitId = "";
        java.lang.String _dischargeCompleted_timestamp = "";
        java.lang.String _dischargeCompleted__return = port.dischargeCompleted(_dischargeCompleted_patientId, _dischargeCompleted_unitId, _dischargeCompleted_timestamp);
        System.out.println("dischargeCompleted.result=" + _dischargeCompleted__return);


        }
        {
        System.out.println("Invoking waitForProcedures...");
        java.lang.String _waitForProcedures_patientId = "";
        java.lang.String _waitForProcedures_unitId = "";
        java.lang.String _waitForProcedures_timestamp = "";
        java.lang.String _waitForProcedures__return = port.waitForProcedures(_waitForProcedures_patientId, _waitForProcedures_unitId, _waitForProcedures_timestamp);
        System.out.println("waitForProcedures.result=" + _waitForProcedures__return);


        }
        {
        System.out.println("Invoking consultationCompleted2...");
        java.lang.String _consultationCompleted2_patientId = "";
        java.lang.String _consultationCompleted2_physicianId = "";
        java.lang.String _consultationCompleted2_locationId = "";
        java.lang.String _consultationCompleted2_timestamp = "";
        java.lang.String _consultationCompleted2__return = port.consultationCompleted2(_consultationCompleted2_patientId, _consultationCompleted2_physicianId, _consultationCompleted2_locationId, _consultationCompleted2_timestamp);
        System.out.println("consultationCompleted2.result=" + _consultationCompleted2__return);


        }
        {
        System.out.println("Invoking patientAdmittedWithBed...");
        java.lang.String _patientAdmittedWithBed_patientId = "";
        java.lang.String _patientAdmittedWithBed_unitId = "";
        java.lang.String _patientAdmittedWithBed_locationId = "";
        java.lang.String _patientAdmittedWithBed_timestamp = "";
        java.lang.String _patientAdmittedWithBed__return = port.patientAdmittedWithBed(_patientAdmittedWithBed_patientId, _patientAdmittedWithBed_unitId, _patientAdmittedWithBed_locationId, _patientAdmittedWithBed_timestamp);
        System.out.println("patientAdmittedWithBed.result=" + _patientAdmittedWithBed__return);


        }
        {
        System.out.println("Invoking patientInCW...");
        java.lang.String _patientInCW_patientId = "";
        java.lang.String _patientInCW_locationId = "";
        java.lang.String _patientInCW_timestamp = "";
        java.lang.String _patientInCW__return = port.patientInCW(_patientInCW_patientId, _patientInCW_locationId, _patientInCW_timestamp);
        System.out.println("patientInCW.result=" + _patientInCW__return);


        }
        {
        System.out.println("Invoking consultationStarted1...");
        java.lang.String _consultationStarted1_patientId = "";
        java.lang.String _consultationStarted1_physicianId = "";
        java.lang.String _consultationStarted1_locationId = "";
        java.lang.String _consultationStarted1_timestamp = "";
        java.lang.String _consultationStarted1__return = port.consultationStarted1(_consultationStarted1_patientId, _consultationStarted1_physicianId, _consultationStarted1_locationId, _consultationStarted1_timestamp);
        System.out.println("consultationStarted1.result=" + _consultationStarted1__return);


        }
        {
        System.out.println("Invoking patientOutCCL...");
        java.lang.String _patientOutCCL_patientId = "";
        java.lang.String _patientOutCCL_locationId = "";
        java.lang.String _patientOutCCL_timestamp = "";
        java.lang.String _patientOutCCL__return = port.patientOutCCL(_patientOutCCL_patientId, _patientOutCCL_locationId, _patientOutCCL_timestamp);
        System.out.println("patientOutCCL.result=" + _patientOutCCL__return);


        }
        {
        System.out.println("Invoking physicianInCW...");
        java.lang.String _physicianInCW_physicianId = "";
        java.lang.String _physicianInCW_locationId = "";
        java.lang.String _physicianInCW_timestamp = "";
        java.lang.String _physicianInCW__return = port.physicianInCW(_physicianInCW_physicianId, _physicianInCW_locationId, _physicianInCW_timestamp);
        System.out.println("physicianInCW.result=" + _physicianInCW__return);


        }
        {
        System.out.println("Invoking procedureStarted...");
        java.lang.String _procedureStarted_patientId = "";
        java.lang.String _procedureStarted_providerId = "";
        java.lang.String _procedureStarted_procedureType = "";
        java.lang.String _procedureStarted_timestamp = "";
        java.lang.String _procedureStarted__return = port.procedureStarted(_procedureStarted_patientId, _procedureStarted_providerId, _procedureStarted_procedureType, _procedureStarted_timestamp);
        System.out.println("procedureStarted.result=" + _procedureStarted__return);


        }
        {
        System.out.println("Invoking procedureUpdated...");
        java.lang.String _procedureUpdated_patientId = "";
        java.lang.String _procedureUpdated_procedureType = "";
        java.lang.String _procedureUpdated_status = "";
        java.lang.String _procedureUpdated_timestamp = "";
        java.lang.String _procedureUpdated__return = port.procedureUpdated(_procedureUpdated_patientId, _procedureUpdated_procedureType, _procedureUpdated_status, _procedureUpdated_timestamp);
        System.out.println("procedureUpdated.result=" + _procedureUpdated__return);


        }
        {
        System.out.println("Invoking consultationCompleted1...");
        java.lang.String _consultationCompleted1_patientId = "";
        java.lang.String _consultationCompleted1_physicianId = "";
        java.lang.String _consultationCompleted1_locationId = "";
        java.lang.String _consultationCompleted1_timestamp = "";
        java.lang.String _consultationCompleted1__return = port.consultationCompleted1(_consultationCompleted1_patientId, _consultationCompleted1_physicianId, _consultationCompleted1_locationId, _consultationCompleted1_timestamp);
        System.out.println("consultationCompleted1.result=" + _consultationCompleted1__return);


        }
        {
        System.out.println("Invoking patientInED...");
        java.lang.String _patientInED_patientId = "";
        java.lang.String _patientInED_locationId = "";
        java.lang.String _patientInED_timestamp = "";
        java.lang.String _patientInED__return = port.patientInED(_patientInED_patientId, _patientInED_locationId, _patientInED_timestamp);
        System.out.println("patientInED.result=" + _patientInED__return);


        }
        {
        System.out.println("Invoking waitForTransport...");
        java.lang.String _waitForTransport_patientId = "";
        java.lang.String _waitForTransport_unitId = "";
        java.lang.String _waitForTransport_timestamp = "";
        java.lang.String _waitForTransport__return = port.waitForTransport(_waitForTransport_patientId, _waitForTransport_unitId, _waitForTransport_timestamp);
        System.out.println("waitForTransport.result=" + _waitForTransport__return);


        }
        {
        System.out.println("Invoking ordersExecutionCompleted...");
        java.lang.String _ordersExecutionCompleted_patientId = "";
        java.lang.String _ordersExecutionCompleted_timestamp = "";
        java.lang.String _ordersExecutionCompleted__return = port.ordersExecutionCompleted(_ordersExecutionCompleted_patientId, _ordersExecutionCompleted_timestamp);
        System.out.println("ordersExecutionCompleted.result=" + _ordersExecutionCompleted__return);


        }
        {
        System.out.println("Invoking physicianInED...");
        java.lang.String _physicianInED_physicianId = "";
        java.lang.String _physicianInED_locationId = "";
        java.lang.String _physicianInED_timestamp = "";
        java.lang.String _physicianInED__return = port.physicianInED(_physicianInED_physicianId, _physicianInED_locationId, _physicianInED_timestamp);
        System.out.println("physicianInED.result=" + _physicianInED__return);


        }
        {
        System.out.println("Invoking ping...");
        java.lang.String _ping_message = "";
        java.lang.String _ping__return = port.ping(_ping_message);
        System.out.println("ping.result=" + _ping__return);


        }
        {
        System.out.println("Invoking waitForOrderExecution...");
        java.lang.String _waitForOrderExecution_patientId = "";
        java.lang.String _waitForOrderExecution_timestamp = "";
        java.lang.String _waitForOrderExecution__return = port.waitForOrderExecution(_waitForOrderExecution_patientId, _waitForOrderExecution_timestamp);
        System.out.println("waitForOrderExecution.result=" + _waitForOrderExecution__return);


        }
        {
        System.out.println("Invoking patientOutED...");
        java.lang.String _patientOutED_patientId = "";
        java.lang.String _patientOutED_locationId = "";
        java.lang.String _patientOutED_timestamp = "";
        java.lang.String _patientOutED__return = port.patientOutED(_patientOutED_patientId, _patientOutED_locationId, _patientOutED_timestamp);
        System.out.println("patientOutED.result=" + _patientOutED__return);


        }
        {
        System.out.println("Invoking patientTransportRequest...");
        java.lang.String _patientTransportRequest_patientId = "";
        java.lang.String _patientTransportRequest_providerId = "";
        java.lang.String _patientTransportRequest_unitId = "";
        java.lang.String _patientTransportRequest_timestamp = "";
        java.lang.String _patientTransportRequest__return = port.patientTransportRequest(_patientTransportRequest_patientId, _patientTransportRequest_providerId, _patientTransportRequest_unitId, _patientTransportRequest_timestamp);
        System.out.println("patientTransportRequest.result=" + _patientTransportRequest__return);


        }
        {
        System.out.println("Invoking consultationStarted2...");
        java.lang.String _consultationStarted2_patientId = "";
        java.lang.String _consultationStarted2_physicianId = "";
        java.lang.String _consultationStarted2_locationId = "";
        java.lang.String _consultationStarted2_timestamp = "";
        java.lang.String _consultationStarted2__return = port.consultationStarted2(_consultationStarted2_patientId, _consultationStarted2_physicianId, _consultationStarted2_locationId, _consultationStarted2_timestamp);
        System.out.println("consultationStarted2.result=" + _consultationStarted2__return);


        }
        {
        System.out.println("Invoking procedureCompleted...");
        java.lang.String _procedureCompleted_patientId = "";
        java.lang.String _procedureCompleted_providerId = "";
        java.lang.String _procedureCompleted_procedureType = "";
        java.lang.String _procedureCompleted_timestamp = "";
        java.lang.String _procedureCompleted__return = port.procedureCompleted(_procedureCompleted_patientId, _procedureCompleted_providerId, _procedureCompleted_procedureType, _procedureCompleted_timestamp);
        System.out.println("procedureCompleted.result=" + _procedureCompleted__return);


        }
        {
        System.out.println("Invoking proceduresExecutionCompleted...");
        java.lang.String _proceduresExecutionCompleted_patientId = "";
        java.lang.String _proceduresExecutionCompleted_timestamp = "";
        java.lang.String _proceduresExecutionCompleted__return = port.proceduresExecutionCompleted(_proceduresExecutionCompleted_patientId, _proceduresExecutionCompleted_timestamp);
        System.out.println("proceduresExecutionCompleted.result=" + _proceduresExecutionCompleted__return);


        }
        {
        System.out.println("Invoking consultationStarted3...");
        java.lang.String _consultationStarted3_patientId = "";
        java.lang.String _consultationStarted3_physicianId = "";
        java.lang.String _consultationStarted3_locationId = "";
        java.lang.String _consultationStarted3_timestamp = "";
        java.lang.String _consultationStarted3__return = port.consultationStarted3(_consultationStarted3_patientId, _consultationStarted3_physicianId, _consultationStarted3_locationId, _consultationStarted3_timestamp);
        System.out.println("consultationStarted3.result=" + _consultationStarted3__return);


        }
        {
        System.out.println("Invoking patientOutCW...");
        java.lang.String _patientOutCW_patientId = "";
        java.lang.String _patientOutCW_locationId = "";
        java.lang.String _patientOutCW_timestamp = "";
        java.lang.String _patientOutCW__return = port.patientOutCW(_patientOutCW_patientId, _patientOutCW_locationId, _patientOutCW_timestamp);
        System.out.println("patientOutCW.result=" + _patientOutCW__return);


        }
        {
        System.out.println("Invoking waitForConsultation2...");
        java.lang.String _waitForConsultation2_patientId = "";
        java.lang.String _waitForConsultation2_locationId = "";
        java.lang.String _waitForConsultation2_timestamp = "";
        java.lang.String _waitForConsultation2__return = port.waitForConsultation2(_waitForConsultation2_patientId, _waitForConsultation2_locationId, _waitForConsultation2_timestamp);
        System.out.println("waitForConsultation2.result=" + _waitForConsultation2__return);


        }
        {
        System.out.println("Invoking bedRequest...");
        java.lang.String _bedRequest_patientId = "";
        java.lang.String _bedRequest_providerId = "";
        java.lang.String _bedRequest_unitId = "";
        java.lang.String _bedRequest_timestamp = "";
        java.lang.String _bedRequest__return = port.bedRequest(_bedRequest_patientId, _bedRequest_providerId, _bedRequest_unitId, _bedRequest_timestamp);
        System.out.println("bedRequest.result=" + _bedRequest__return);


        }
        {
        System.out.println("Invoking waitForConsultation1...");
        java.lang.String _waitForConsultation1_patientId = "";
        java.lang.String _waitForConsultation1_locationId = "";
        java.lang.String _waitForConsultation1_timestamp = "";
        java.lang.String _waitForConsultation1__return = port.waitForConsultation1(_waitForConsultation1_patientId, _waitForConsultation1_locationId, _waitForConsultation1_timestamp);
        System.out.println("waitForConsultation1.result=" + _waitForConsultation1__return);


        }
        {
        System.out.println("Invoking patientInCCL...");
        java.lang.String _patientInCCL_patientId = "";
        java.lang.String _patientInCCL_locationId = "";
        java.lang.String _patientInCCL_timestamp = "";
        java.lang.String _patientInCCL__return = port.patientInCCL(_patientInCCL_patientId, _patientInCCL_locationId, _patientInCCL_timestamp);
        System.out.println("patientInCCL.result=" + _patientInCCL__return);


        }
        {
        System.out.println("Invoking bedCleanUpRequest...");
        java.lang.String _bedCleanUpRequest_locationId = "";
        java.lang.String _bedCleanUpRequest_unitId = "";
        java.lang.String _bedCleanUpRequest_timestamp = "";
        java.lang.String _bedCleanUpRequest__return = port.bedCleanUpRequest(_bedCleanUpRequest_locationId, _bedCleanUpRequest_unitId, _bedCleanUpRequest_timestamp);
        System.out.println("bedCleanUpRequest.result=" + _bedCleanUpRequest__return);


        }
        {
        System.out.println("Invoking physicianOutCW...");
        java.lang.String _physicianOutCW_physicianId = "";
        java.lang.String _physicianOutCW_locationId = "";
        java.lang.String _physicianOutCW_timestamp = "";
        java.lang.String _physicianOutCW__return = port.physicianOutCW(_physicianOutCW_physicianId, _physicianOutCW_locationId, _physicianOutCW_timestamp);
        System.out.println("physicianOutCW.result=" + _physicianOutCW__return);


        }
        {
        System.out.println("Invoking bedCleanUpStarted...");
        java.lang.String _bedCleanUpStarted_houseKeepingId = "";
        java.lang.String _bedCleanUpStarted_locationId = "";
        java.lang.String _bedCleanUpStarted_unitId = "";
        java.lang.String _bedCleanUpStarted_timestamp = "";
        java.lang.String _bedCleanUpStarted__return = port.bedCleanUpStarted(_bedCleanUpStarted_houseKeepingId, _bedCleanUpStarted_locationId, _bedCleanUpStarted_unitId, _bedCleanUpStarted_timestamp);
        System.out.println("bedCleanUpStarted.result=" + _bedCleanUpStarted__return);


        }
        {
        System.out.println("Invoking bedCleanUpCompleted...");
        java.lang.String _bedCleanUpCompleted_houseKeepingId = "";
        java.lang.String _bedCleanUpCompleted_locationId = "";
        java.lang.String _bedCleanUpCompleted_unitId = "";
        java.lang.String _bedCleanUpCompleted_timestamp = "";
        java.lang.String _bedCleanUpCompleted__return = port.bedCleanUpCompleted(_bedCleanUpCompleted_houseKeepingId, _bedCleanUpCompleted_locationId, _bedCleanUpCompleted_unitId, _bedCleanUpCompleted_timestamp);
        System.out.println("bedCleanUpCompleted.result=" + _bedCleanUpCompleted__return);


        }
        {
        System.out.println("Invoking patientArrivedInBed...");
        java.lang.String _patientArrivedInBed_patientId = "";
        java.lang.String _patientArrivedInBed_unitId = "";
        java.lang.String _patientArrivedInBed_locationId = "";
        java.lang.String _patientArrivedInBed_timestamp = "";
        java.lang.String _patientArrivedInBed__return = port.patientArrivedInBed(_patientArrivedInBed_patientId, _patientArrivedInBed_unitId, _patientArrivedInBed_locationId, _patientArrivedInBed_timestamp);
        System.out.println("patientArrivedInBed.result=" + _patientArrivedInBed__return);


        }
        {
        System.out.println("Invoking patientTransportStarted...");
        java.lang.String _patientTransportStarted_patientId = "";
        java.lang.String _patientTransportStarted_unitId = "";
        java.lang.String _patientTransportStarted_timestamp = "";
        java.lang.String _patientTransportStarted__return = port.patientTransportStarted(_patientTransportStarted_patientId, _patientTransportStarted_unitId, _patientTransportStarted_timestamp);
        System.out.println("patientTransportStarted.result=" + _patientTransportStarted__return);


        }
        {
        System.out.println("Invoking waitForBed...");
        java.lang.String _waitForBed_patientId = "";
        java.lang.String _waitForBed_unitId = "";
        java.lang.String _waitForBed_timestamp = "";
        java.lang.String _waitForBed__return = port.waitForBed(_waitForBed_patientId, _waitForBed_unitId, _waitForBed_timestamp);
        System.out.println("waitForBed.result=" + _waitForBed__return);


        }
        {
        System.out.println("Invoking consultationCompleted3...");
        java.lang.String _consultationCompleted3_patientId = "";
        java.lang.String _consultationCompleted3_physicianId = "";
        java.lang.String _consultationCompleted3_locationId = "";
        java.lang.String _consultationCompleted3_timestamp = "";
        java.lang.String _consultationCompleted3__return = port.consultationCompleted3(_consultationCompleted3_patientId, _consultationCompleted3_physicianId, _consultationCompleted3_locationId, _consultationCompleted3_timestamp);
        System.out.println("consultationCompleted3.result=" + _consultationCompleted3__return);


        }
        {
        System.out.println("Invoking waitForBedCleanUp...");
        java.lang.String _waitForBedCleanUp_locationId = "";
        java.lang.String _waitForBedCleanUp_unitId = "";
        java.lang.String _waitForBedCleanUp_timestamp = "";
        java.lang.String _waitForBedCleanUp__return = port.waitForBedCleanUp(_waitForBedCleanUp_locationId, _waitForBedCleanUp_unitId, _waitForBedCleanUp_timestamp);
        System.out.println("waitForBedCleanUp.result=" + _waitForBedCleanUp__return);


        }
        {
        System.out.println("Invoking triageScore...");
        java.lang.String _triageScore_patientId = "";
        java.lang.String _triageScore_providerId = "";
        java.lang.String _triageScore_ctas = "";
        java.lang.String _triageScore_timestamp = "";
        java.lang.String _triageScore__return = port.triageScore(_triageScore_patientId, _triageScore_providerId, _triageScore_ctas, _triageScore_timestamp);
        System.out.println("triageScore.result=" + _triageScore__return);


        }
        {
        System.out.println("Invoking proceduresScheduled...");
        java.lang.String _proceduresScheduled_patientId = "";
        java.lang.String _proceduresScheduled_procedureScheduledTime = "";
        java.lang.String _proceduresScheduled_unitId = "";
        java.lang.String _proceduresScheduled_timestamp = "";
        java.lang.String _proceduresScheduled__return = port.proceduresScheduled(_proceduresScheduled_patientId, _proceduresScheduled_procedureScheduledTime, _proceduresScheduled_unitId, _proceduresScheduled_timestamp);
        System.out.println("proceduresScheduled.result=" + _proceduresScheduled__return);


        }
        {
        System.out.println("Invoking orderRequest...");
        java.lang.String _orderRequest_patientId = "";
        java.lang.String _orderRequest_providerId = "";
        java.lang.String _orderRequest_orderType = "";
        java.lang.String _orderRequest_orderId = "";
        java.lang.String _orderRequest_timestamp = "";
        java.lang.String _orderRequest__return = port.orderRequest(_orderRequest_patientId, _orderRequest_providerId, _orderRequest_orderType, _orderRequest_orderId, _orderRequest_timestamp);
        System.out.println("orderRequest.result=" + _orderRequest__return);


        }
        {
        System.out.println("Invoking orderRequestCompleted...");
        java.lang.String _orderRequestCompleted_patientId = "";
        java.lang.String _orderRequestCompleted_providerId = "";
        java.lang.String _orderRequestCompleted_orderType = "";
        java.lang.String _orderRequestCompleted_orderId = "";
        java.lang.String _orderRequestCompleted_timestamp = "";
        java.lang.String _orderRequestCompleted__return = port.orderRequestCompleted(_orderRequestCompleted_patientId, _orderRequestCompleted_providerId, _orderRequestCompleted_orderType, _orderRequestCompleted_orderId, _orderRequestCompleted_timestamp);
        System.out.println("orderRequestCompleted.result=" + _orderRequestCompleted__return);


        }
        {
        System.out.println("Invoking dischargeRequest...");
        java.lang.String _dischargeRequest_patientId = "";
        java.lang.String _dischargeRequest_providerId = "";
        java.lang.String _dischargeRequest_unitId = "";
        java.lang.String _dischargeRequest_timestamp = "";
        java.lang.String _dischargeRequest__return = port.dischargeRequest(_dischargeRequest_patientId, _dischargeRequest_providerId, _dischargeRequest_unitId, _dischargeRequest_timestamp);
        System.out.println("dischargeRequest.result=" + _dischargeRequest__return);


        }
        {
        System.out.println("Invoking houseKeepingInCW...");
        java.lang.String _houseKeepingInCW_houseKeepingId = "";
        java.lang.String _houseKeepingInCW_locationId = "";
        java.lang.String _houseKeepingInCW_timestamp = "";
        java.lang.String _houseKeepingInCW__return = port.houseKeepingInCW(_houseKeepingInCW_houseKeepingId, _houseKeepingInCW_locationId, _houseKeepingInCW_timestamp);
        System.out.println("houseKeepingInCW.result=" + _houseKeepingInCW__return);


        }
        {
        System.out.println("Invoking patientAdmittedWithNoBed...");
        java.lang.String _patientAdmittedWithNoBed_patientId = "";
        java.lang.String _patientAdmittedWithNoBed_unitId = "";
        java.lang.String _patientAdmittedWithNoBed_timestamp = "";
        java.lang.String _patientAdmittedWithNoBed__return = port.patientAdmittedWithNoBed(_patientAdmittedWithNoBed_patientId, _patientAdmittedWithNoBed_unitId, _patientAdmittedWithNoBed_timestamp);
        System.out.println("patientAdmittedWithNoBed.result=" + _patientAdmittedWithNoBed__return);


        }
        {
        System.out.println("Invoking houseKeepingOutCW...");
        java.lang.String _houseKeepingOutCW_houseKeepingId = "";
        java.lang.String _houseKeepingOutCW_locationId = "";
        java.lang.String _houseKeepingOutCW_timestamp = "";
        java.lang.String _houseKeepingOutCW__return = port.houseKeepingOutCW(_houseKeepingOutCW_houseKeepingId, _houseKeepingOutCW_locationId, _houseKeepingOutCW_timestamp);
        System.out.println("houseKeepingOutCW.result=" + _houseKeepingOutCW__return);


        }
        {
        System.out.println("Invoking physicianOutED...");
        java.lang.String _physicianOutED_physicianId = "";
        java.lang.String _physicianOutED_locationId = "";
        java.lang.String _physicianOutED_timestamp = "";
        java.lang.String _physicianOutED__return = port.physicianOutED(_physicianOutED_physicianId, _physicianOutED_locationId, _physicianOutED_timestamp);
        System.out.println("physicianOutED.result=" + _physicianOutED__return);


        }
        {
        System.out.println("Invoking waitForDischarge...");
        java.lang.String _waitForDischarge_patientId = "";
        java.lang.String _waitForDischarge_unitId = "";
        java.lang.String _waitForDischarge_timestamp = "";
        java.lang.String _waitForDischarge__return = port.waitForDischarge(_waitForDischarge_patientId, _waitForDischarge_unitId, _waitForDischarge_timestamp);
        System.out.println("waitForDischarge.result=" + _waitForDischarge__return);


        }

        System.exit(0);
    }

}
