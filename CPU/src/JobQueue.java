/*
 * This class implements ArrayLists and provides a comparator that sorts it.
 * This allows for the same algorithm to schedule jobs differently.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
public class JobQueue {
	private ArrayList<Process> processes = new ArrayList<>();
	private Comparator<Process> algoType;
	private boolean preemption = true;
	
	public JobQueue(ArrayList<Process> processes, Comparator<Process> algoType){
		this.algoType = algoType;
		this.processes = processes;
		Collections.sort(this.processes, algoType);
	}

	public JobQueue(boolean preemption, Comparator<Process> algoType){
		this.preemption = preemption;
		this.algoType = algoType;
	}
	public void enqueue(Process p){
		processes.add(p);
	}
	public void sort(){
		if(!preemption && processes.size() > 0){
			Process p = processes.remove(0);
			Collections.sort(processes,algoType);
			processes.add(0, p);
		}else{
			Collections.sort(processes, algoType);
		}
	}
	public Process pop(){
		if (processes.isEmpty())
			return new Process(-1,-1,-1,-1);
		Process p = processes.get(0);
		processes.remove(0);
		
		return p;
	}
	public Process peek(){
		if (processes.isEmpty())
			return new Process(-1,-1,-1,-1);
		Process p = processes.get(0);
		
		return p;
	}
	public boolean isEmpty(){
		return processes.isEmpty();
	}
	public void processWorked(){
		if(!processes.isEmpty()){
			Process p = processes.remove(0);
			p.setTimeWorked(p.getTimeWorked() + 1);
			processes.add(0,p);
		}
	}
	public int size(){
		return processes.size();
	}
}

