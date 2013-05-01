package patientflowmonitoring


class Patient {

	String patientID
	PatientState currentState
	Event lastEventReceived
	String roomID
	String procedureStatus = ""
	int pendingOrders=0
	int ordersProcessed=0
	Map props = [:]
	List states = []
	List events = []
	
	static hasMany = [states:PatientState,events:Event]
	
	static mapping = {
		events cascade:'all-delete-orphan'
		states cascade:'all-delete-orphan'
	}
	
	static constraints = {
		patientID(unique:true)
		roomID(nullable:true)
		currentState()
		lastEventReceived(nullable:true)
		states(nullable:true)
		events(nullable:true)
		props(nullable:true)
	}
	

	void terminateCurrentState(Date ts){
	  currentState?.setEndTime(ts);
	}
	
	void startCurrentState(Date ts){
	  currentState?.setStartTime(ts);
	  
	}
	
	void changeState(PatientState newState, Date ts){
	  terminateCurrentState(ts);
	  setCurrentState(newState,ts);
	}
	
	void setCurrentState(PatientState newState, Date ts){
	  terminateCurrentState(ts);
	  currentState = newState;
	  states.add(currentState);
	  startCurrentState(ts);
	}
	
	public PatientState getCurrentState(){
		return currentState;
	}
	
	/*public int getPendingOrders(){
		return pendingOrders;
	}*/
	
	/*public int getOrdersProcessed(){
		return ordersProcessed;
	}*/
	
	public void appendEvent(Event event){
	  events.add(event)
	  lastEventReceived  = event
	}
	
	String toString() {"${this.patientID}"}
}
