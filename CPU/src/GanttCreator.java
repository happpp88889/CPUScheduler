import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Comparator;

import javax.swing.event.ChangeEvent;
import javafx.beans.value.ChangeListener;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
//This is the gui/gant chart creator.

public class GanttCreator extends Application {
	    public static void main(String[] args) {
	        launch(args);
	    }
	 
	    static ArrayList<Process> data = new ArrayList<>();
	    @Override
	    public void start(Stage stage) {
	    	
	    	
	    	TableView<Process> table = new TableView<Process>();
	    	TableView<Process> timeTable = new TableView<>();
	    	
	    	CategoryAxis yAxis = new CategoryAxis();
	    	NumberAxis xAxis = new NumberAxis();
	       	StackedBarChart<Number,String> ganttChart = new StackedBarChart<Number,String>(xAxis, yAxis);
	    	
	    	
	    	
	    	VBox vTable = new VBox(), runSection = new VBox(), vTimeTable = new VBox(), combo = new VBox(), addGantt = new VBox(), legend = new VBox();
	    	HBox hbTableAdd = new HBox(), topRow = new HBox(), hbTimeLabels = new HBox(), legendLables = new HBox();
	        Scene scene = new Scene(new Group());
	        stage.setTitle("Scheduling Algorihms");
	        stage.setWidth(815);
	        stage.setHeight(500);
	        
	        Label algoLabel = new Label("Algorithm");
	        ComboBox<String> algo = new ComboBox<String>();
	        algo.getItems().addAll("FCFS","SJF","SRT","Priority", "RR Fixed", "RR Variable");
	        algo.getSelectionModel().selectFirst();
	        algo.setPrefWidth(100);
	        TextField addQuantum = new TextField();
	        addQuantum.setPromptText("Quantum");
	        addQuantum.setMaxWidth(algo.getPrefWidth());
	        addQuantum.setVisible(false);
	        
	        algo.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					// TODO Make CPU object with process list data and JobQueue with the algorithm
					if(algo.getValue().equals("RR Fixed") || algo.getValue().equals("RR Variable"))
						addQuantum.setVisible(true);
					else
						addQuantum.setVisible(false);
					
				}
	        	
	        });
	 
	        Label label = new Label("Processes");
	        label.setFont(new Font("Arial", 20));
	        
	        Label waitLabel = new Label("avgWT: 0");
	        Label tatLabel = new Label("avgTAT: 0");
	 
	        table.setEditable(true);
	        System.out.println(data.size());
	        TableColumn PIDCol = new TableColumn("PID");
	        PIDCol.setCellValueFactory(new PropertyValueFactory("PID"));
	        TableColumn arrivalTimeCol= new TableColumn("Arrival Time");
	        arrivalTimeCol.setCellValueFactory(new PropertyValueFactory("arrivalTime"));

	        TableColumn burstTimeCol = new TableColumn("Burst Time");
	        burstTimeCol.setCellValueFactory(new PropertyValueFactory("burstTime"));

	        TableColumn priorityCol = new TableColumn("Priority");
	        priorityCol.setCellValueFactory(new PropertyValueFactory("priority"));
	        
	        table.getColumns().addAll(PIDCol, arrivalTimeCol, burstTimeCol,priorityCol);
	        table.setPrefHeight(200);
	        table.setPrefWidth(200);
	        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	        
	        table.setItems(FXCollections.observableArrayList(data));
	        
	        
	        final Label timeLabel = new Label("Process Times");
	        timeLabel.setFont(new Font("Arial", 20));
	        timeTable.setPrefSize(300, 200);
	 
	        timeTable.setEditable(true);
	        timeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	        timeTable.setItems(FXCollections.observableArrayList(data));
	        TableColumn PIDCol1 = new TableColumn("PID");
	        PIDCol1.setCellValueFactory(new PropertyValueFactory("PID"));
	        TableColumn tatCol= new TableColumn("Turn Around Time");
	        tatCol.setCellValueFactory(new PropertyValueFactory("turnAroundTime"));

	        TableColumn waitTimeCol = new TableColumn("Wait Time");
	        waitTimeCol.setCellValueFactory(new PropertyValueFactory("waitTime"));

	        timeTable.getColumns().addAll(PIDCol1 , tatCol, waitTimeCol);
	 
	       ganttChart.setTitle("Gantt Chart");
	       ganttChart.setPrefHeight(50);
	        
	        
	        
	 
	        
	        TextField addPID = new TextField();
	        addPID.setPromptText("PID");
	        addPID.setMaxWidth(PIDCol.getPrefWidth());
	        TextField addArrivalTime = new TextField();
	        addArrivalTime.setMaxWidth(arrivalTimeCol.getPrefWidth());
	        addArrivalTime.setPromptText("Arrival Time");
	        TextField addBurstTime = new TextField();
	        addBurstTime.setMaxWidth(burstTimeCol.getPrefWidth());
	        addBurstTime.setPromptText("Burst Time");
	        TextField addPriority = new TextField();
	        addPriority.setMaxWidth(priorityCol.getPrefWidth());
	        addPriority.setPromptText("Priority");
	         
	        Button closeButton = new Button("Close");
	        closeButton.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					stage.close();
					
				}
	        	
	        });
	        closeButton.setPrefWidth(100);
	        Button addButton = new Button("Add");
	        addButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                if(!addPID.getText().isEmpty() && !addArrivalTime.getText().isEmpty() && !addBurstTime.getText().isEmpty() && !addPriority.getText().isEmpty()){
		            	data.add(new Process(
		                    Integer.parseInt(addPID.getText()),
		                    Integer.parseInt(addArrivalTime.getText()),
		                    Integer.parseInt(addBurstTime.getText()),
		                    Integer.parseInt(addPriority.getText())
		                ));
		            	}
	                addPID.clear();
	                addArrivalTime.clear();
	                addBurstTime.clear();
	                addPriority.clear();
	                table.setItems(FXCollections.observableArrayList(data));
	            }
	        });
	        Button runButton = new Button("Run");
	        runButton.setPrefWidth(100);
	        runButton.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					// TODO Make CPU object with process list data and JobQueue with the algorithm
					ArrayList<Process> temp = data;
					ArrayList<Integer> ganttData = new ArrayList<Integer>();
					int quantum = 0;
					boolean fixed = false, preemptive = false;
					Comparator<Process> algorithm;
					
					ganttChart.getData().clear();
					
					switch(algo.getValue()){
					case "FCFS":
						algorithm = new Comparator<Process>() {
							//
							@Override
							public int compare(Process arg0, Process arg1) {
									if(arg0.getArrivalTime() < arg1.getArrivalTime()){
										return -1;
									}else if(arg0.getArrivalTime() > arg1.getArrivalTime()){
										return 1;
									}else{
										return (arg0.getPID() < arg1.getPID()) ? -1 : 1;
									}
							}
						};
						break;
						
					case "SJF":
						algorithm = new Comparator<Process>() {

							@Override
							public int compare(Process arg0, Process arg1) {
									if(arg0.getRemainingTime() < arg1.getRemainingTime()){
										return -1;
									}else if(arg0.getRemainingTime() > arg1.getRemainingTime()){
										return 1;
									}else{
										return (arg0.getPID() < arg1.getPID()) ? -1 : 1;
									}
							}
							
						};
						break;
						
					case "SRT":
						algorithm = new Comparator<Process>() {

							@Override
							public int compare(Process arg0, Process arg1) {
									if(arg0.getRemainingTime() < arg1.getRemainingTime()){
										return -1;
									}else if(arg0.getRemainingTime() > arg1.getRemainingTime()){
										return 1;
									}else{
										return (arg0.getPID() < arg1.getPID()) ? -1 : 1;
									}
							}
							
						};
						preemptive = true;
						break;
					case "Priority":
						algorithm = new Comparator<Process>() {

							@Override
							public int compare(Process arg0, Process arg1) {
									if(arg0.getPriority() < arg1.getPriority()){
										return -1;
									}else if(arg0.getPriority() > arg1.getPriority()){
										return 1;
									}else{
										return (arg0.getPID() < arg1.getPID()) ? -1 : 1;
									}
							}
							
						};
						preemptive = true;
						break;
						
					case "RR Fixed":
						algorithm = new Comparator<Process>(){

							@Override
							public int compare(Process o1, Process o2) {
								return 0;
							}
							
						};
						preemptive = true;
						fixed = true;
						quantum = Integer.parseInt(addQuantum.getText());
						break;
					default:
						algorithm = new Comparator<Process>(){

							@Override
							public int compare(Process o1, Process o2) {
								return 0;
							}
							
						};
						preemptive = true;
						quantum = Integer.parseInt(addQuantum.getText());
						
					}
					
					JobQueue n = new JobQueue(data, new Comparator<Process>() {

						@Override
						public int compare(Process arg0, Process arg1) {
								if(arg0.getArrivalTime() < arg1.getArrivalTime()){
									return -1;
								}else if(arg0.getArrivalTime() > arg1.getArrivalTime()){
									return 1;
								}else{
									return (arg0.getPID() < arg1.getPID()) ? -1 : 1;
								}
						}
						
					}), j = new JobQueue(preemptive, algorithm);
					
					CPU cpu = new CPU(n,j,quantum,fixed);
					cpu.run();
					if(quantum ==0)
						xAxis.setTickUnit(1);
					else
						xAxis.setTickUnit(quantum);
					ganttData = cpu.getGantt();
					for(int i = 0; i < ganttData.size(); i++){
						System.out.print(ganttData.get(i) + ", ");
					}
					
					ArrayList<Process>finished = cpu.finished();
					
					
					
					timeTable.setItems(FXCollections.observableArrayList(finished));
					tatLabel.setText("avgTAT: " + Double.toString(getAvgTAT(finished)));
					waitLabel.setText("avgWT: " + Double.toString(getAvgWT(finished)));
					
					int previousNum = 0;
					if (ganttData.size() > 0)
						previousNum = ganttData.get(0);
					int inARow = 0;
					Map<String,String> colors = new HashMap<String,String>();
					while(ganttData.size() >= 0){
						
						if(ganttData.size() == 0 || previousNum != ganttData.get(0)){
							
							//TODO make a series with PID and inARow
							XYChart.Series<Number,String> series = new XYChart.Series<Number,String>();
							XYChart.Data<Number,String> dataToAdd = new XYChart.Data<Number,String>(inARow, "");
							series.setName("P" + Integer.toString(previousNum));
							if(!colors.containsKey("P" + Integer.toString(previousNum))){
								colors.put("P" + Integer.toString(previousNum), "#" + Integer.toHexString(new Random().nextInt(16777216)));
								System.out.println("true");
							}
							//series.getNode().lookup(".chart-series-area-fill").setStyle("-fx-background-color: red");
							dataToAdd.setExtraValue(new Integer(previousNum));
							dataToAdd.nodeProperty().addListener(new ChangeListener<Node>() {
								@Override
								public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
										newValue.setStyle("-fx-bar-fill: " + colors.get("P" + dataToAdd.getExtraValue()).toString() + ";");
								}
							});
							
							series.getData().add(dataToAdd);
							ganttChart.getData().add(series);
							
							
							if(ganttData.size() ==0)
								break;
							previousNum = ganttData.get(0);
							inARow = 0;
						}else{
							ganttData.remove(0);
							inARow++;
						}
				}
					ganttChart.setLegendVisible(false);
					for(String PIDS : colors.keySet()){
						Label square = new Label("  ");
						square.setStyle("-fx-background-color: " + colors.get(PIDS) + ";");
						if (PIDS.equals("P-1"))
							PIDS = "CPU Idle";
						Label pidLabel = new Label(PIDS);
						legendLables.getChildren().addAll(square, pidLabel);
					}
					data = temp;
				}
	        	
	        });
	        final Button clearButton = new Button("Clear");
	        clearButton.setPrefWidth(100);
	        clearButton.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					data.clear();
					table.setItems(FXCollections.observableArrayList(data));
					timeTable.setItems(FXCollections.observableArrayList(data));
					ganttChart.getData().clear();
					legendLables.getChildren().clear();
				}
	        	
	        });
	        
	        combo.getChildren().addAll(algoLabel, algo);
	        runSection.getChildren().addAll(combo,addQuantum,runButton, clearButton, closeButton);
	        runSection.setSpacing(25);
	        runSection.setPadding(new Insets(25,0,0,0));
	        
	        hbTableAdd.getChildren().addAll(addPID, addArrivalTime, addBurstTime, addPriority, addButton);
	        hbTableAdd.setSpacing(3);
	        hbTimeLabels.getChildren().addAll(tatLabel,waitLabel);
	        hbTimeLabels.setAlignment(Pos.CENTER_LEFT);
	        hbTimeLabels.setSpacing(5);
	        vTable.setSpacing(5);
	        vTable.setPadding(new Insets(10, 5, 5, 10));
	        vTable.getChildren().addAll(label, table, hbTableAdd);
	        vTimeTable.setSpacing(5);
	        vTimeTable.setPadding(new Insets(10,0,0,10));
	        vTimeTable.getChildren().addAll(timeLabel, timeTable, hbTimeLabels);
	        topRow.getChildren().addAll(vTable, runSection, vTimeTable);
	        addGantt.getChildren().addAll(topRow, ganttChart, legendLables);
	        legendLables.setAlignment(Pos.CENTER);
	        addGantt.setSpacing(5);
	        table.setItems(FXCollections.observableArrayList(data));

	        ((Group) scene.getRoot()).getChildren().addAll(addGantt);
	        
	        stage.setScene(scene);
	        stage.show();
	    }
	    private static double getAvgTAT(ArrayList<Process> p){
	    	double sum = 0;
	    	if(p.size() == 0)
	    		return 0;
	    	for(int i = 0; i < p.size(); i++){
	    		sum += p.get(i).getTurnAroundTime();
	    	}
	    	return sum/p.size();
	    	
	    	
	    }
	    private static double getAvgWT(ArrayList<Process> p){
	    	double sum = 0;
	    	if(p.size() == 0)
	    		return 0;
	    	for(int i = 0; i < p.size(); i++){
	    		sum += p.get(i).getWaitTime();
	    	}
	    	return sum/p.size();
	    }
}
