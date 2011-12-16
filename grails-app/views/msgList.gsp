<%@ page import="groovy.io.FileType" %>

<%
def i=0
def msgs = []
def msgs_js = []

def folder = application.getRealPath("/txt")
def baseDir = new File(folder)
def files =[]
baseDir.eachFileRecurse(FileType.FILES){
	files<<it.name
}

def fileName = request.getParameter("fileName")
if (fileName==null) fileName = files[0]

def file = new File(application.getRealPath("/txt")+'/'+fileName)
file.eachLine{line->
	//println line
	msgs.add(line)
	msgs_js.add("'${line}'");
}
%>

<html>
<g:javascript library="jquery" plugin="jquery"/>
<script>
$.ajaxSetup ({
	cache: false
});

var msgs_js = <%=msgs_js%>;

var checkPoint=<%=msgs.size%>

var sendMsgs=function (){

	for (var i=0;i<=checkPoint;i++){
	
		$.ajax({
			url:'message/save2',
			type:'post',
			async:false,
			data:{body:msgs_js[i]},
		});	
	}
}

var sendSomeMsgs=function(){
	checkPoint=document.getElementById("cp").value
	sendMsgs()
	checkPoint=<%=msgs.size%>
}

function loadTestCase(fileName){
	//alert("load " + fileName);
	window.location="<%=request.getContextPath() %>/msgList.gsp?fileName="+fileName;
	//alert("why?")
}

$(document).ready(function() {

	$('#all').click(sendMsgs)
	$('#uptoCertainPoint').click(sendSomeMsgs)
})

</script>
<body>

<h1>Message List</h1>
<div align="right">
Select a test case:</br>
<select onChange="loadTestCase(this.value)">
<%files.each{ %>
<option <%=it==fileName?"selected":""%>><%=it%></option>
<%} %>
</select>
</div>
<div>Now you can send it one by one</div>
<div>or by one click to execute all &nbsp; <button type="button" id="all">Click</button></div>
<div>or to execute to certain check point:&nbsp;<input size=2 id="cp"/>&nbsp;<button type="button" id="uptoCertainPoint">Click</button></div>

<g:while test="${i < msgs.size}">
   <g:form>
    <p> <%=i %>&nbsp;
    <input name="body" size="150" value="<%=msgs.get(i)%>"/> 
    <g:submitToRemote controller="message" action="save2" value="send"/>
     </p>
<%--    <input type="hidden" name="body" value="<%=msgs.get(i)%>"/>--%>

   </g:form>
    <%i++%>
   
</g:while>

<br/>
<%=msgs.size()%> messages

</body>
</html>
