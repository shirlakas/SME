<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<nav:resources/>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<title>Room Details</title>
<style type="text/css" title="currentStyle">
@import "<%=request.getContextPath() %>/css/demo_page.css";
@import "<%=request.getContextPath() %>/css/demo_table.css";
@import "<%=request.getContextPath() %>/css/jQuery_menu.css";
</style>


<script type="text/javascript" language="javascript"
	src="<%=request.getContextPath() %>/js/jquery.js"></script>
<script type="text/javascript" language="javascript"
	src="<%=request.getContextPath() %>/js/jquery.dataTables.js"></script>
<script type="text/javascript" language="javascript"
	src="<%=request.getContextPath() %>/js/jquery.tabify.source.js"></script>	
<script type="text/javascript" charset="utf-8">

			//console.info("<%=room%>");

			<%
				def events = [];
				def eventList = room.events
				eventList.each({
					events<<"['${it.eventName}','${it.timeStamp}']"
				})
				//println events
				
				def states = [];
				def stateList = room.states;
				stateList.each({
					states<<"['${it.stateName}','${it.startTime}','${it.endTime}','${it.duration}']"
				})
			%>
		
			/* Data set - can contain whatever information you want */
			var eventDataSet = <%=events%>;

			var stateDataSet = <%=states%>;
			
			$(document).ready(function() {

				$('#events').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id="event_table"></table>' );
				$('#event_table').dataTable( {
					 "bPaginate": false, /*  remove this line if you want paging*/
					"aaData": eventDataSet,
					"aaSorting": [[ 1, "desc" ]],
					"aoColumns": [
						{ "sTitle": "Event data received" },
						{ "sTitle": "Received Time", "sClass": "center" }
					]
				} );	
				$('#states').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id="state_table"></table>' );
				$('#state_table').dataTable( {
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
									if (oObj.aData[2]=="N/A")
										return 'N/A';
									return oObj.aData[3];
								}
							 }
					]
				} );

				$('#menutab').tabify();
			} );
		</script>
</head>
<body id="dt_example">

	<div id="container">
		<nav:render group="tabs"/>
		<h1>Room Info:<%=room%></h1>
		
		<ul id="menutab" class="menu">
			<li class="active"><a href="#clinicalInfo">Room States</a></li>
			<li><a href="#otherInfo">Events Received</a></li>
		</ul>

		<div id="clinicalInfo">
				<h1>Room States</h1>
				<div id="states"></div>
		</div>
				
		<div id="otherInfo">
				<h1>Events Received</h1>
				<div id="events"></div>
		</div>

	</div>
</body>
</html>
