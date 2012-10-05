<?xml version="1.0" encoding="UTF-8"?>
<! DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
      
<%
	def patients = [];
	def patientLocations = [];
	def patientList = mapping.values().toList();
	mapping.entrySet().each({
		patients<<"${it.value}"
		patientLocations<<"${it.key}"
	})
	
	def rooms=[] 
	def roomList = mapping.values().toList();
	mapping.entrySet.each({
		rooms<<"${it.value}"
			
	})
%>      
      
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<nav:resources/>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title>Unit Map 1</title>
  <meta name="generator" content="Amaya, see http://www.w3.org/Amaya/" />
	<style type="text/css" title="currentStyle">
		@import "<%=request.getContextPath() %>/css/demo_page.css";
		@import "<%=request.getContextPath() %>/css/demo_table.css";
	</style>
	<script type="text/javascript" language="javascript" src="<%=request.getContextPath() %>/js/jquery.js"></script>
	<script type="text/javascript" language="javascript" src="<%=request.getContextPath() %>/js/jquery.dataTables.js"></script>
	
	<script type="text/javascript" charset="utf-8">
	
	//console.info("<%=patients%>");
	//var aDataSet = <%=patients%>;
	
	$(document).ready(function() {

		//$('#dynamic').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
		$('#patients').dataTable( {
	        "bPaginate": false,
	        "bLengthChange": false,
	        "bFilter": false,
	        "bSort": false,
	        "bInfo": false,
	        "bAutoWidth": false
			//"aaData": aDataSet,
			//"aoColumns": [
			//	{ "sTitle": "Patient ID", "sClass": "center",
			//		"fnRender":function(oObj){
			//			return "<a href='getPatientDetails/"+oObj.aData[0]+"'>"+oObj.aData[0]+"</a>";
			//		}
			//	}
			//]
		} );
		
		var map_top = $('#map').offset().top;
		var map_left = $('#map').offset().left;

		<% for (i in 0..<roomList.size()){%>
		var marker<%=i%> = $('#marker').clone();
		<%}%>
		
		renderSpots()

		$(window).resize(function() {
			map_top = $('#map').offset().top;
			map_left = $('#map').offset().left;
			renderSpots();
			});	

		function renderSpots(){
			<% for (i in 0..<roomList.size()){%>
			marker<%=i%>.id="marker<%=i%>"
				marker<%=i%>.css('left', map_left+coord["<%=roomList[i]%>"]["left"])

				marker<%=i%>.css('top',  map_top+coord["<%=roomList[i]%>"]["top"])
				marker<%=i%>.css("display","inline")
				marker<%=i%>.attr("title","<%=patientList[i]%>")
				marker<%=i%>.bind('click',function(){
					//alert('Marker<%=i%> is clicked');
					window.location="getPatientDetails/<%=patientList[i]%>";
					});

				$('#td_map').append(marker<%=i%>);
				
				<%	if (patientList[i]==session.patient?.patientID){ %>
				marker<%=i%>.attr("src","<%=request.getContextPath() %>/images/red_spot.png");
				<%	} %>
			<%}%>	
				//$('#marker').css('left', map_left+100).css('top', map_top+290).show();
		}

		//alert(coord["ED1"]["top"]);
		
	} );	


	var coord = {'ED1':{'top':290,'left':95},
			'ED2':{'top':285,'left':140},
			'ED3':{'top':285,'left':200},
			'ED4':{'top':275,'left':250},
			'ED5':{'top':270,'left':302},
			'ED6':{'top':265,'left':362},
			'ED7':{'top':529,'left':103},
			'ED8':{'top':517,'left':150},
			'ED9':{'top':505,'left':217},
			'ED10':{'top':493,'left':267},
			'ED11':{'top':480,'left':317},
			'ED12':{'top':468,'left':367},
			
			'CCU1':{'top':245,'left':480},
			'CCU2':{'top':240,'left':535}
			};

	function unit_click(unit){
		//alert('clicked');
		window.location="../unit/showUnitPerformance/"+unit;
	}

	</script>

</head>

<body id="dt_example">
<div id="container">
<nav:render group="tabs"/>
<h1>Floor Directory</h1>

<!-- %=rooms[]%> -->
<table border="1" style="width: 100%">
  <tbody>
    <tr>
      <td width="200" valign="top">
		<div id="dynamic">
			<table cellpadding="0" cellspacing="0" border="0" class="display" id="units">
			<thead>
				<tr>
					<th>Units</th>
				</tr>
			</thead>
			<tbody>
				<%p=0%>
				<g:while test="${p < unitIds?.size}">
					<% def css=session.unitSelected==unitIds[p]?"selected":""  %>
					<tr class="<%=css%>"><td class="center"><a href="<%=request.getContextPath() %>/unit/showUnitPerformance/ED"><%=unitIds[p] %></a></td></tr>
					<%p++%>
				</g:while>
			</tbody>
			</table>
		</div>	
		</td>
		<td width="200" valign="top">
		<div> 
			<table cellpadding="0" cellspacing="0" border="0" class="display" >
			<thead>
				<tr>
					<th>Rooms</th>
				</tr>
			</thead>
			<tbody>
			<tr class="<%=css%>">
			<td class="center"><a href="<%=request.getContextPath() %>/room/getRoomDetails/R105">R105</a></td></tr>
			<tr class="<%=css%>">	<td class="center"><a href="<%=request.getContextPath() %>/room/getRoomDetails/R106">R106</a></td></tr>
			<tr class="<%=css%>">	<td class="center"><a href="<%=request.getContextPath() %>/room/getRoomDetails/R107">R107</a></td></tr>
				
			</tbody>
			</table>
			
		</div>
		</td>
		<td width="200" valign="top">
		<div>
		<table cellpadding="0" cellspacing="0" border="0" class="display" id="patients">
			<thead>
				<tr>
					<th>Patients</th>
				</tr>
			</thead>
			<tbody>
				<%p=0%>
				<g:while test="${p < patients?.size}">
					<% def css=session.patient?.patientID==patients[p]?"selected":""  %>
					<tr class="<%=css%>"><td class="center">
						<a href="getPatientDetails/<%=patients[p] %>"><%=patients[p] %></a>
						<br/>
									
					</td></tr>
					<%p++%>
				</g:while>
			</tbody>
			</table>
			</div>
			</td>
    </tr>
  </tbody>
</table>

<map name="unitmap">
	<area shape="poly" coords="80,185,360,155,400,500,80,545" onClick="javascript:unit_click('ED')" title="ED Unit Area" /> 
	<area shape="poly" coords="441,147,715,112,820,447,502,488" onClick="javascript:unit_click('CCU')" title="CCU Unit Area" /> 
</map>

</div>
</body>
</html>
