//this acts as the process control block. States when it arrived
//burst time, priority and keeps track of the time worked/finished.
public class Process {
	private int PID;
	private int arrivalTime = 0;
	private int burstTime = 0;
	private int priority = Integer.MAX_VALUE;
	private int timeWorked = 0;
	private int finishTime;
    
    public Process(int PID, int arrivalTime, int burstTime, int priority){
    	this.PID = PID;
    	this.arrivalTime = arrivalTime;
    	this.burstTime = burstTime;
    	this.priority = priority;
    }
    public int getArrivalTime(){
    	return arrivalTime;
    }
    public int getBurstTime(){
    	return burstTime;
    }
    public int getPriority(){
    	return priority;
    }
    public void setPID(int PID){
    	this.PID = PID;
    }
    public void setArrivalTime(int arrivalTime){
    	this.arrivalTime = arrivalTime;
    }
    public void setBurstTime(int burstTime){
    	this.burstTime = burstTime;
    }
    public void setPriority(int priority){
    	this.priority = priority;
    }
    public int getPID(){
    	return PID;
    }
    public int getFinishTime(){
    	return finishTime;
    }
    public void setFinishTime(int finishTime){
    	this.finishTime = finishTime;
    }
    public void setTimeWorked(int timeWorked){
    	this.timeWorked = timeWorked;
    }
    public int getTimeWorked(){
    	return timeWorked;
    }
    public String toString(){
    	return "P " + Integer.toString(PID) + "; ArrivalTime: " + Integer.toString(arrivalTime) + "; BurstTime: " + Integer.toString(burstTime)
    		+ "; Priority: " + Integer.toString(priority) + "; timeWorked: " + timeWorked;
    }
    public int getTurnAroundTime(){
    	return getTurnAroundTimeHelp(finishTime, arrivalTime);
    }
    public int getWaitTime(){
    	return getTurnAroundTimeHelp(finishTime, arrivalTime) - burstTime;
    }
    private static int getTurnAroundTimeHelp(int finishTime, int arrivalTime){
    	return finishTime - arrivalTime;
    }
    
    public int getRemainingTime(){
    	return burstTime - timeWorked;
    }

}
