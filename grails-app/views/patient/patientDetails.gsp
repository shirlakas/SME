<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<nav:resources/>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<title>Patient Details</title>
<style type="text/css" title="currentStyle">
@import "<%=request.getContextPath() %>/css/demo_page.css";
@import "<%=request.getContextPath() %>/css/demo_table.css";
@import "<%=request.getContextPath() %>/css/jQuery_menu.css";
</style>
<script type="text/javascript" language="javascript"
	src="<%=request.getContextPath() %>/js/jquery.js"></script>
	<script type="text/javascript" language="javascript">

	/*function to receive a dateString manually match it to a regular expression
	 split it and parse it as a date. This is to prevent issues with the different
	 date formats not being supported by different browsers
	*/
	var parseDate = function(s) {
		  var re = /^(\d{4})-(\d\d)-(\d\d) (\d\d):(\d\d):(\d\d)$/;
		  var m = re.exec(s);
		  return m ? new Date(m[1], m[2]-1, m[3], m[4], m[5], m[6]) : null;
		};
	</script>

<script type="text/javascript" language="javascript"
	src="<%=request.getContextPath() %>/js/jquery.js"></script>
<script type="text/javascript" language="javascript"
	src="<%=request.getContextPath() %>/js/jquery.dataTables.js"></script>
<script type="text/javascript" language="javascript"
	src="<%=request.getContextPath() %>/js/jquery.tabify.source.js"></script>	
<script type="text/javascript" charset="utf-8">

			//console.info("<%=patient%>");

			<%
				def events = [];
				def eventList = patient.events
				eventList.each({
					events<<"['${it.eventName}','${it.timeStamp}']"
				})
				//println events
				
				def states = [];
				def chartStates =[];
				def total=0;
				def duratn=[];
				int i=0;
				chartStates<<"['State', 'Duration','Target']"
				def stateList = patient.states;
				def cnt =0;
				stateList.each({
					states<<"['${it.stateName}','${it.startTime}','${it.endTime}','${it.duration}','${it.target}']"
					chartStates<<"['${it.stateName}',${it.duration},${it.target}]"
					cnt++;
				})
				total =duratn.sum()
			%>
			
			/* Data set - can contain whatever information you want */
			var eventDataSet = <%=events%>;

			var stateDataSet = <%=states%>;
				
			$(document).ready(function() {

				$('#events').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id="event_table"></table>' );
				$('#event_table').dataTable( {
					"bPaginate": false, /* Delete this line if you want paging*/
					"aaData": eventDataSet,
					"aaSorting": [[ 1, "desc" ]],
					"aoColumns": [
						{ "sTitle": "Event data received" },
						{ "sTitle": "Received Time", "sClass": "center" }
					]
				} );	
				$('#states').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id="state_table"></table>' );
				$('#state_table').dataTable( {
					"bPaginate": false, /* Delete this line if you want paging*/
					"aaData": stateDataSet,
					"aaSorting": [[ 2, "desc" ]],
					"aoColumns": [
						{ "sTitle": "State" },
						{ "sTitle": "Start Time", "sClass": "center",
							"fnRender":function(oObj){
								if (oObj.aData[1]=="null")
									return 'N/A';
								return oObj.aData[1];
							} },
						{ "sTitle": "End Time", "sClass": "center",
							"fnRender":function(oObj){
									if (oObj.aData[2]=="null")
										return 'N/A';
									return oObj.aData[2];
								}
							 },
						{ "sTitle": "Duration (mins)", "sClass": "center","sWidth":"5%",
							"fnRender":function(oObj){
								//if the endTime is not available compute the duration using current time
								
								if ((oObj.aData[2]=="N/A")||(oObj.aData[2]=="null")||(oObj.aData[2]==null)){
										var stString = oObj.aData[1];
										var len =stString.length;
										stString = stString.substring(0,len-2);
										var st = parseDate(stString);
																					
										var currentDate = new Date();
										var diff = currentDate.getTime()-st.getTime();
										diff = Math.ceil(diff/60000);
										if (diff<0) {
											diff = 0;
										}
										return diff;
									}
								if (oObj.aData[3]<0){
										return 0;
									}
								return oObj.aData[3];
							}
							 },
							 { "sTitle": "Target (mins)", "sClass": "center","sWidth":"5%",
									"fnRender":function(oObj){
											if ((oObj.aData[4]=="null")||(oObj.aData[4]==0))
												return 'N/A';
											return oObj.aData[4];
										}
									 }
					]
				} );

				 //iterate through all the rows in the state table 
                //excluding the first row because those are column titles
                // and colour the values for duration depending on the target value 
                // 
                $(".state tr:not(:first)").each(function() { 
                	 
                    var duration = new Number;
                    var target = new Number;
                    var threshold = new Number;
                    
                    /* get the value of the table cell located in the fourth column of the 
                     * current row convert all values to floats first before comparing */
                    
                    duration = $(this).find("td:nth-child(4)").text();
                    duration = parseFloat(duration);
                    target = $(this).find("td:nth-child(5)").text();
                    target = parseFloat(target);
                    threshold = parseFloat(target*2.0/3.0);
                   //check if target is null
                    if (target == 'N/A'){
	                    }
                   else if(duration >= target){
                        //change the color of the text to red if duration is more than target
                       // $(this).find("td:nth-child(4)").css("color", "#FF0000"); 
                       //change the background colour to red if duration is more than target
                        $(this).find("td").css("background-color", "#FF0000"); 
                    }
                   else if (duration >= threshold){
                        //change the background color to yellow if duration is more than 2/3 of target
                        // but less than target
                        $(this).find("td").css("background-color", "#F4FA58");
                      //change the color of the text to yellow if duration is more than 2/3 of target
                        // but less than target
                       // $(this).find("td:nth-child(4)").css("color", "#FACC2E");
                    }
                    else if (duration < target){
                        //change the color of the text to green if duration is less than 2/3 of target
                       // $(this).find("td:nth-child(4)").css("color", "#00FF00");
                       //change the background color to green if duration is less than 2/3 of target
                    	$(this).find("td").css("background-color", "#00FF00");	
                    }   
                   
                });
			
				$('#menutab').tabify();
			} );
		</script>
		<script type="text/javascript" src="https://www.google.com/jsapi"></script>     <script type="text/javascript">       google.load("visualization", "1", {packages:["corechart"]});       google.setOnLoadCallback(drawChart);
      function drawChart() {
  // Create and populate the data table.
  var data = google.visualization.arrayToDataTable(          
				<%=chartStates%>
);

  // Create and draw the visualization.
  new google.visualization.BarChart(document.getElementById('chart_arrivals_div')).
      draw(data,
           {title:"Average Time Patients Spend in Each Step of the Cardiac Care Process",
            width:1400, height:800,
            vAxis: {title: "State"},
            hAxis: {title: "Duration in Minutes"}}
      );
}

</script>
</head>
<body id="dt_example">

	<div id="container">
		<nav:render group="tabs"/>
		<h1>Patient Info:<%=patient%> &nbsp; Room: <%=patient.roomID %> &nbsp; &nbsp; &nbsp;Overall Duration: <g:formatNum number="${totalhrs}" format="### hours"/> <g:formatNum number="${totalmins}" format="### mins"/> </h1>
		
		<ul id="menutab" class="menu">
			<li class="active"><a href="#patientstats">Patient States Chart</a></li>
			<li><a href="#clinicalInfo">Patient States</a></li>
			<li><a href="#otherInfo">Events Received</a></li>
		</ul>
		
		<div id="patientstats">
			<h1>Patient States Chart &nbsp; &nbsp; Overall Duration: <g:formatNum number="${totalhrs}" format="### hours"/> <g:formatNum number="${totalmins}" format="### mins"/> </h1>
			<div id="chart_arrivals_div"></div>
		</div>

		<div id="clinicalInfo">
			<h1>Patient States</h1>
			<div id="states" class = "state"></div>
			<h1>Overall Duration: <g:formatNum number="${totalhrs}" format="### hours"/> <g:formatNum number="${totalmins}" format="### mins"/></h1>			
		</div>
				
		<div id="otherInfo">
			<h1>Events Received</h1>
			<div id="events"></div>
		</div>
	</div>
</body>
</html>
