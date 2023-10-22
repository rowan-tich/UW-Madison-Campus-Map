

import java.io.FileNotFoundException;

import javafx.stage.Stage;

public interface displayFrontendInterfaceFD {
	
	/* This method will be responsible for displaying everything on the GUI. */
	public void start(final Stage stage) throws FileNotFoundException;

	/* This method will collect the users input using textField from JAVAFX and using the backends implementation of addLocation */
	public void userAdd(String building);

	/* This method will collect the users input using textField from JAVAFX and using the backends implementation of removeLocation */
	public void userDelete(String building);

	/* This method will be collecting the users input using textField from JAVAFX and using the backends implementation of addRoute */
	public void userAddRoute(String location1, String location2, int time, boolean walking);
	
	/* This method will be collecting the users input using textField from JAVAFX and using the backends implementation of removeRoute */

	public void userRemoveRoute(String location1, String location2, int time, boolean walking);


}
