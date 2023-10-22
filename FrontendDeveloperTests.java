
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import edu.wisc.cs.cs400.JavaFXTester;

public class FrontendDeveloperTests extends JavaFXTester {

	public FrontendDeveloperTests(){
		super(displayFrontend.class);
	}
	
	//Tests that there will be an error message when locations are not selected
	@Test
	public void test1() {
		
		Label label = lookup("#notSelected").query();
		clickOn("#walkRoutesB");
		assertEquals("You have not selected the starting and ending points", label.getText());
	}
	//Check that the stats feature work
	@Test
	public void test2() {
		
		Label label = lookup("#displayStats").query();
		
		clickOn("#getStats");
		
		assertEquals("There are 71 locations in our map, and there are 208 routes in our map.", label.getText());
	}
	//Check that the reset features work
	@Test
	public void test3() {
		
		Label label = lookup("#displayStats").query();
		
		clickOn("#reset");
		
		assertEquals("", label.getText());
	}
	//Check that the Multi stop features work which should change the previous shortPathResult
	@Test
	public void test4() {
		
		Label label = lookup("#shortPathResult").query();
		
		clickOn("#multiStop");
		
		assertEquals(true, label.getText().isEmpty());
	}
	//Check that even when trying to press the Bus Routes, it will not work if locations are not selected
	@Test
	public void test5() {
		
		Label label = lookup("#notSelected").query();
		
		clickOn("#busRoutesB");
		
		assertEquals("You have not selected the starting and ending points", label.getText());
	}
	//check if the map can be loaded and that we can modify it by adding a location
	@Test
	public void reviewBD1() throws FileNotFoundException {
	MapBackend backend = new MapBackend();
	backend.loadData("madisonmap.dot");
	backend.addLocation("test");
	assertEquals("There are 72 locations in our map, and there are 208 routes in our map.", backend.getStatisticsString());
	
	}
	//Check if the map can be loaded and tries different methods of modifying it such as removing a building, adding 2 buildings
	//and creating a route between those two newly added routes
	@Test
	public void reviewBD2() throws FileNotFoundException {
	MapBackend backend = new MapBackend();
	backend.loadData("madisonmap.dot");
	backend.removeLocation("University Hospital");
	backend.addLocation("test1");
	backend.addLocation("test2");
	backend.addRoute("test1", "test2", 10, true);
	assertEquals("There are 72 locations in our map, and there are 209 routes in our map.", backend.getStatisticsString());
	}
	//Check if the a building can be added using the textfield, should display stats should not display anything as a success
	//message would pop up
	@Test
	public void integrate1() {
	clickOn("#userBuilding").write("test");
	Label label = lookup("#displayStats").query();
	clickOn("#addLoc");
	assertEquals("", label.getText());
	}
	//Check if we can keep writing above a single word in the text box and that it won't glitch out by clicking on it multiple times
	//should not make stats display anything because the success warning would still be there.
	@Test
	public void integrate2() {
	clickOn("#userBuilding").write("test");
	clickOn("#userBuilding").write("test1");
	clickOn("#userBuilding").write("test3");
        Label label = lookup("#displayStats").query();
        clickOn("#addLoc");
        assertEquals("", label.getText());
	}
	/* Review questions
	1) I Believe that the only thing that could be added to the backend's code would be more commenting
	2) I believe that the backend did an excelent job with organizing his code
	3) Definitely, I'd say that in the addRoutes and removeRoutes, a little bit more commenting would be helpful
	4) There were no bugs that I noticed myself, I did test his code and it all works as it is supposed to do so
	*/
}
