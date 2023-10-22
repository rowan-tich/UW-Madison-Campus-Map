

import java.io.FileNotFoundException;		
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
// these imports are used for the First JavaFX Activity
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import java.util.ArrayList;

//--== CS400 File Header Information ==--			
//Name: <Maximilian Ganske>
//Email: <mganske2@wisc.edu>
//Group and Team: <BF>
//Group TA: <Naman Gupta>
//Lecturer: <Gary Dahl>
//Notes to Grader: <The application works as intended according to the proposal on Eclipse; however,
//it does not work on the VM due to that I cannot use the remote Linux as shown in class because my
//computer does not support Remote Desktop (which I did contact the course staff but issue persisted)
//The same goes for the Tests, they all follow the formatting that we were shown, but do not pass
//due to lack of existence of the lookup keyword as that javafx does not work. Furthermore, I want to
//clarify that all the work done here follows the proposal as expected.


public class displayFrontend extends Application implements displayFrontendInterfaceFD {
	
	MapBackend backend = new MapBackend();
	ComboBox<String> comboBox = new ComboBox<>();
	boolean isWalkingActive;
	StackPane root = new StackPane();
	
    public void start(final Stage stage) throws FileNotFoundException {
    	
    	//Add buildings placeholder
    //	backend.addLocation("kronshage");
    //	backend.addLocation("nicholas gym");
    //	backend.addLocation("bascom hill");
    //	backend.addLocation("science hall");
    //	backend.addLocation("north hall");
    //	backend.addLocation("red gym");
    //	backend.addLocation("law building");
    	
    	//loads file
    	backend.loadData("madisonmap.dot");
    	AtomicInteger optionsSelected = new AtomicInteger(0);
    	AtomicReference<String> option1 = new AtomicReference<>();
    	AtomicReference<String> option2 = new AtomicReference<>();
        
        Label notSelected = new Label("You have not selected the starting and ending points");
	notSelected.setId("notSelected");

		StackPane.setMargin(notSelected, new Insets(0, 900, 650, 0));
		
		root.getChildren().add(notSelected);
		notSelected.setVisible(false);
		
		//Show user their location choices
		Label firstLoc = new Label();
		StackPane.setMargin(firstLoc, new Insets(0, 0, 700, 50));
		Label secondLoc = new Label();
		StackPane.setMargin(secondLoc, new Insets(0, 0, 650, 50));
		
		Label routeExec = new Label();
		StackPane.setMargin(routeExec, new Insets(0, 100, 900, 0));
		
	     Region shortPathDisplay = new Region();
	        shortPathDisplay.setStyle("-fx-background-color: gray; -fx-border-style: solid; "
	        		+ "-fx-border-width: 5; -fx-border-color: black; -fx-min-width: 20; "
	        		+ "-fx-min-height:20; -fx-max-width:1200; -fx-max-height: 450;");
	        StackPane.setMargin(shortPathDisplay, new Insets(0, 0, 150, 0));
	    Label shortPathResult = new Label();
	    shortPathResult.setId("shortPathResult");
		
	    shortPathResult.setFont(new Font(16.0));
	   // shortPathResult.setY(10);
	    
	    //Setting up buttons
	    Button walkRoutesB = new Button("Walking routes");
	    Button busRoutesB = new Button("Bus routes");
	    walkRoutesB.setId("walkRoutesB");
	    busRoutesB.setId("busRoutesB");
	    StackPane.setMargin(walkRoutesB, new Insets(0, 1100, 700, 0));
	    StackPane.setMargin(busRoutesB, new Insets(0, 900, 700, 0));
	    
	    Button getStats = new Button("Statistics");
	    Label displayStats = new Label();
	    getStats.setId("getStats");
	    displayStats.setId("displayStats");
	    StackPane.setMargin(getStats, new Insets(0, 0, -700, 1100));
	    StackPane.setMargin(displayStats, new Insets(0, 0, -750, 800));
	    
	    Button multiStop = new Button("Multi-stop");
            multiStop.setId("multiStop");
	    StackPane.setMargin(multiStop, new Insets(0, 700, 700, 0));
	    multiStop.setDisable(true);
	    TextField multiStopUser = new TextField();
            multiStopUser.setMaxWidth(200);
            StackPane.setMargin(multiStopUser, new Insets(0, 700, 800, 0));
	    multiStopUser.setDisable(true);

	    
	    Region userTextDisplay = new Region();
	    userTextDisplay.setStyle("-fx-background-color: gray; -fx-border-style: solid; "
        		+ "-fx-border-width: 5; -fx-border-color: black; -fx-min-width: 20; "
        		+ "-fx-min-height:20; -fx-max-width: 900; -fx-max-height: 150;");
	    StackPane.setMargin(userTextDisplay, new Insets(0, 0, -520, 0));
	    
	    Button addLoc = new Button("Add Location");
	    addLoc.setId("addLoc");
	    Button removeLoc = new Button("Remove location");
	    Button addRoute = new Button("Add Route");
	    Button removeRoute = new Button("Remove Route");
	    StackPane.setMargin(addLoc, new Insets(0, 720, -520, 0));
	    StackPane.setMargin(removeLoc, new Insets(0, 720, -600, 0));
	    StackPane.setMargin(addRoute, new Insets(0, 70, -520, 0));
	    StackPane.setMargin(removeRoute, new Insets(0, 70, -600, 0));
	    addLoc.setPrefSize(130, 30);
	    removeLoc.setPrefSize(130, 30);
	    addRoute.setPrefSize(130, 30);
	    removeRoute.setPrefSize(130, 30);
	    
	    //Setting up the fields for the user to add routes
	    TextField userBuilding = new TextField();
	    userBuilding.setId("userBuilding");
	    TextField userLoc1 = new TextField();
	    TextField userLoc2 = new TextField();
	    TextField userTime = new TextField();
	    CheckBox userMotion = new CheckBox("Walking");
	    
	    userBuilding.setMaxWidth(150);
	    userLoc1.setMaxWidth(150);
	    userLoc2.setMaxWidth(150);
	    userTime.setMaxWidth(50);
	    
	    Label enterBuilding = new Label("Enter Building: ");
	    Label enterOption1 = new Label("Enter start Point: ");
	    Label enterOption2 = new Label("Enter end point: ");
	    Label enterTime = new Label("Enter time in minutes: ");
	    
	    StackPane.setMargin(enterBuilding, new Insets(0, 720, -400, 0));
	    StackPane.setMargin(enterOption1, new Insets(0, 250, -400, 0));
	    StackPane.setMargin(enterOption2, new Insets(0, -100, -400, 0));
	    StackPane.setMargin(enterTime, new Insets(0, -400, -400, 0));
	    
	    StackPane.setMargin(userBuilding, new Insets(0, 720, -450, 0));
	    StackPane.setMargin(userLoc1, new Insets(0, 250, -450, 0));
	    StackPane.setMargin(userLoc2, new Insets(0, -100, -450, 0));
	    StackPane.setMargin(userTime, new Insets(0, -400, -450, 0));
	    StackPane.setMargin(userMotion, new Insets(0, -550, -450, 0));
	    
	    Button reset = new Button("Reset");
	    Button goBack = new Button("Go back <-");
	    reset.setId("reset");
	    StackPane.setMargin(reset, new Insets(0, 0, 700, 1100));
	    StackPane.setMargin(goBack, new Insets(0, 0, 630, 1100));
        
        comboBox.getItems().addAll(backend.getLocations());
        comboBox.setValue("Buildings");
        StackPane.setMargin(comboBox, new Insets(0, 0, 700, 710));
        
        root.getChildren().addAll(userTextDisplay, comboBox, walkRoutesB, busRoutesB, reset, goBack, firstLoc, secondLoc,
        		routeExec, shortPathDisplay, shortPathResult, getStats, displayStats, addLoc, removeLoc,
        		addRoute, removeRoute, userBuilding, userLoc1, userLoc2, userTime, userMotion,
        		enterBuilding, enterOption1, enterOption2, enterTime, multiStop, multiStopUser);
        
        //Dropdown menu of buildings
        comboBox.setOnAction(e -> {
            
        	if(optionsSelected.get() == 0) {
        		System.out.println("First option: " + comboBox.getValue());
        		option1.set(comboBox.getValue());
            	firstLoc.setText("Your starting point: " + option1.get());
            	
        	} if(optionsSelected.get() == 1) {
        		 System.out.println("Second option: " + comboBox.getValue());
        		 option2.set(comboBox.getValue());
        		 secondLoc.setText("Your destination point: " + option2.get());
        		 comboBox.setDisable(true);
        		 
        	}
            
            optionsSelected.incrementAndGet();
            System.out.println(optionsSelected.get());
            
        });
        //Walk routes button
        walkRoutesB.setOnAction(e -> {
        	if(option1.get() == null || option2.get() == null) {
        		notSelected.setVisible(true);
        	} else {
        		notSelected.setVisible(false);
        		notSelected.setVisible(false);
        		routeExec.setText("Fastest route by walking from " + option1.get() + 
        			" to " + option2.get());
        		busRoutesB.setDisable(true);
        		walkRoutesB.setDisable(true);
        		goBack.setDisable(true);
        		isWalkingActive = true;
        		multiStop.setDisable(false);
			multiStopUser.setDisable(false);
        		shortPathResult.setText(backend.findBestRoutes(option1.get(), option2.get(), true) + 
			"[" + backend.findBestRoutesCost(option1.get(), option2.get(), true) + " Minutes]");
        	}
        });
        //Bus routes button
        busRoutesB.setOnAction(e -> {
        	if(option1.get() == null || option2.get() == null) {
        		notSelected.setVisible(true);
        	} else {
        		notSelected.setVisible(false);
        		routeExec.setText("Fastest route by bus from " + option1.get() + 
        			" to " + option2.get());
        		busRoutesB.setDisable(true);
        		walkRoutesB.setDisable(true);
        		goBack.setDisable(true);
        		isWalkingActive = false;
        		multiStop.setDisable(false);
			multiStopUser.setDisable(false);
        		shortPathResult.setText(backend.findBestRoutes(option1.get(), option2.get(), false) +
			"[" + backend.findBestRoutesCost(option1.get(), option2.get(), false) + " Minutes]");
        	}
        });
        
        //reset button
        reset.setOnAction(e -> {
        	option1.set(null);
        	option2.set(null);
        	firstLoc.setText("");
        	secondLoc.setText("");
        	routeExec.setText("");
        	shortPathResult.setText("");
        	displayStats.setText("");
        	comboBox.setDisable(false);
        	optionsSelected.set(0);
        	busRoutesB.setDisable(false);
        	walkRoutesB.setDisable(false);
        	goBack.setDisable(false);
        	multiStop.setDisable(true);
		multiStopUser.setDisable(true);
        	
        });
        //Go Back button
        goBack.setOnAction(e -> {
        	if(option1.get() != null && option2.get() == null) {
        		option1.set(null);
        		firstLoc.setText("");
        		optionsSelected.decrementAndGet();
        	}
        	if(option2.get() != null) {
        		option2.set(null);
        		secondLoc.setText("");
        		optionsSelected.decrementAndGet();
        		comboBox.setDisable(false);
        	}
        });
        
        //Get stats feature
        getStats.setOnAction(e -> {
        	displayStats.setText(backend.getStatisticsString());
        });
        
        //Add user's building to the list
        addLoc.setOnAction(e -> {
        	userAdd(userBuilding.getText());
        });
        //Remove user's building to the list
        removeLoc.setOnAction(e -> {
        	userDelete(userBuilding.getText());
        	
        });
        
        //Add user's route
        addRoute.setOnAction(e -> {
        	
        	try {
        		int time = Integer.parseInt(userTime.getText());
        		boolean walking;
        		if(userMotion.isSelected()) {
            		walking = true;
            	} else {
            		walking = false;
            	}
        		userAddRoute(userLoc1.getText(), userLoc2.getText(), time, walking);
            	
        	} catch (NumberFormatException er) {
        		Alert error = new Alert(AlertType.WARNING);
        		error.setContentText("The given time is not a number");
        		error.show();
        	}
        	
        });
        //Remove user's route
        removeRoute.setOnAction(e ->{
        	try {
        		int time = Integer.parseInt(userTime.getText());
        		boolean walking;
        		if(userMotion.isSelected()) {
            		walking = true;
            	} else {
            		walking = false;
            	}
        		userRemoveRoute(userLoc1.getText(), userLoc2.getText(), time, walking);
            	
        	} catch (NumberFormatException er) {
        		Alert error = new Alert(AlertType.WARNING);
        		error.setContentText("The given time is not a number");
        		error.show();
        	}
        	
        });
        //Multi stop button
        multiStop.setOnAction(e -> {
		ArrayList<String> userInput = new ArrayList<>();
		String[] split = multiStopUser.getText().split(",");
		for(int i = 0; i < split.length; i++) {
			userInput.add(split[i]);
		}
        	shortPathResult.setText(backend.findMultiStopRoutes(userInput, isWalkingActive) + "[" +
		backend.findMultiStopRoutesCost(userInput, isWalkingActive) + " Minutes]");
        });
        
        //Create scene
        Scene scene = new Scene(root, 1400, 1000);
        stage.setTitle("UW-Madison Best Routes");
        stage.setScene(scene);
        stage.show();
    }

    //User add validades the user's building name and uses the backend implementation to add it
	@Override
	public void userAdd(String building) {
		
		boolean canAdd = true;
		
		if(building.isEmpty()) {
    		Alert error = new Alert(AlertType.WARNING);
    		error.setContentText("Building has no value");
    		error.show();
    		canAdd = false;
    	}
    	if(backend.getLocations().contains(building.toLowerCase())) {
    		Alert error = new Alert(AlertType.WARNING);
    		error.setContentText( "\"" + building + "\"" + " is already in the list");
    		error.show();
    		canAdd = false;
    	} 
    	if(canAdd){
    		backend.addLocation(building.toLowerCase());
        	comboBox.getItems().add(building.toLowerCase());
        	Alert hasAdded = new Alert(AlertType.CONFIRMATION);
        	hasAdded.setContentText( "\"" + building + "\"" + " has been added successfully");
        	hasAdded.show();
        	System.out.println(backend.getLocations().size());
    	}
		
	}

	//User delete validades the user's building name and uses the backend implementation to remove it
	@Override
	public void userDelete(String building) {
		
		boolean canRemove = true;
		boolean checkList = true;
		
		if(building.isEmpty()) {
    		Alert error = new Alert(AlertType.WARNING);
    		error.setContentText("Building has no value");
    		error.show();
    		canRemove = false;
    		checkList = false;
    	}
    	if(!backend.getLocations().contains(building.toLowerCase()) && checkList) {
    		Alert error = new Alert(AlertType.WARNING);
    		error.setContentText( "\"" + building + "\"" + " is not in the list");
    		error.show();
    		canRemove = false;
    	} 
    	if(canRemove){
    		backend.removeLocation(building.toLowerCase());
        	comboBox.getItems().remove(building.toLowerCase());
        	Alert hasRemoved = new Alert(AlertType.CONFIRMATION);
        	hasRemoved.setContentText( "\"" + building + "\"" + " has been removed successfully");
        	hasRemoved.show();
    	}
		
	}

	//user add route gathers user's info and add a new route with them
	@Override
	public void userAddRoute(String location1, String location2, int time, boolean walking) {
		
		boolean exit = false;

		if(!backend.getLocations().contains(location1) || !backend.getLocations().contains(location2)) {
			Alert error = new Alert(AlertType.WARNING);
			error.setContentText("Locations are not in the buildings list");
			error.show();
			exit = true;
		}
		if(!exit){
		if(location1.isEmpty() || location2.isEmpty() || time == 0) {
			Alert error = new Alert(AlertType.WARNING);
			error.setContentText("Not all fields were filled");
			error.show();
		} else {
			backend.addRoute(location1, location2, time, walking);
			Alert hasAdded = new Alert(AlertType.CONFIRMATION);
			hasAdded.setContentText("Route has been added");
			hasAdded.show();
		}

		}
	}
	
	//user remove route gathers user's info and remove a route with them
	@Override
	public void userRemoveRoute(String location1, String location2, int time, boolean walking) {
		
		if(location1.isEmpty() || location2.isEmpty() || time == 0) {
			Alert error = new Alert(AlertType.WARNING);
			error.setContentText("Not all fields were filled");
			error.show();
		} 
		else{
			backend.addRoute(location1, location2, time, walking);
			Alert hasRemoved = new Alert(AlertType.CONFIRMATION);
			hasRemoved.setContentText("Route has been removed");
			hasRemoved.show();
		}
		
	}
	//Launches app
	public static void main(String[] args) {
		Application.launch();
	}
}
