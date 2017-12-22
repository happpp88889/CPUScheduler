import java.util.ArrayList;
import java.util.Comparator;

//simulates a CPU
//Has two queues, ready queue and running queue
//sorts the running queue based on algorithm.

public class CPU {
	private JobQueue newQ;
	private JobQueue readyQ;
	private int timeCounter = 0;
	private ArrayList<Process> finishedProcesses = new ArrayList<>();
	private ArrayList<Integer> gantt = new ArrayList<>();
	private int quantum = 1, remainder = 0;
	private boolean roundRobin = false, fixed = false;
	
	//Distinguishes regular algorithms and RR
	public CPU(JobQueue newQ, JobQueue readyQ, int quantum, boolean fixedQuantum){
		this.newQ = newQ;
		this.readyQ = readyQ;
		if (quantum != 0){
			this.quantum = quantum;
			roundRobin = true;
			fixed = fixedQuantum;
		}
	}
	
	//checks new queue every time unit and "works" on processes.
	public void run(){
		System.out.println("Running");
		
		while(!newQ.isEmpty() || !readyQ.isEmpty()){
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			if(newQ.peek().getArrivalTime() == timeCounter || timeCounter % quantum == remainder){
				//During round robin at the end of a quantum the finishing process comes before incoming processes
				if(readyQ.size() > 1 && roundRobin && timeCounter % quantum == remainder){
					if(readyQ.peek().getRemainingTime() != 0)
						readyQ.enqueue(readyQ.pop());
					else 
						readyQ.pop();
					while(newQ.peek().getArrivalTime() == timeCounter){
						readyQ.enqueue(newQ.pop());
					}
				//unless it is the only process
				}else if(roundRobin && readyQ.size() == 1 && timeCounter % quantum == remainder){
					while(newQ.peek().getArrivalTime() == timeCounter){
						readyQ.enqueue(newQ.pop());
					}
					if(readyQ.peek().getRemainingTime() != 0)
							readyQ.enqueue(readyQ.pop());
					else 
							readyQ.pop();
				//this is for regular algorithms
				}else{
					while(newQ.peek().getArrivalTime() == timeCounter){
						readyQ.enqueue(newQ.pop());
					}
				}
				
				
				readyQ.sort();
			}
			//Increments time worked
			//If remaining time == 0 add to finish q
			if(readyQ.size() == 0){
				gantt.add(-1);
				timeCounter++;
				continue;
			}
			if (readyQ.peek().getRemainingTime() > 1){
				readyQ.processWorked();
				gantt.add(readyQ.peek().getPID());
			}else if(readyQ.peek().getRemainingTime() == 1){
				readyQ.processWorked();
				Process p = null;
				if(roundRobin){
					p = readyQ.peek();
					if(!fixed)
						remainder = (timeCounter +1) % quantum;
				}else{
					p = readyQ.pop();
				}
				gantt.add(p.getPID());
				p.setFinishTime(timeCounter + 1);
				finishedProcesses.add(p);
			}else if(readyQ.peek().getRemainingTime() == 0){
				if(timeCounter % quantum == remainder){
					//readyQ.pop();
					gantt.add(-1);
			}else{
			}
				gantt.add(-1);
			}
			timeCounter++;
		}
	}
	public ArrayList<Integer> getGantt(){
		return gantt;
	}
	public ArrayList<Process> finished(){
		return finishedProcesses;
	}

}

