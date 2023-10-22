import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface MapBackendInterface {
	// instance variables (placeholders for other classes too) constructors
	// private MapAlgorithmInterface map; should not be done in the interface

	// Methods
	// loads data from the data wrangler’s .dot file into the algorithm engineer’s graph
	public void loadData(String filename) throws FileNotFoundException;

    // adds a location to the graph
	public void addLocation(String location);

    // removes a location from the graph
	public void removeLocation(String location);

    // adds an route to the graph
    public void addRoute(String location1, String location2, int time, boolean walking);

    // removes an route from the graph
    public void removeRoute(String location1, String location2, int time, boolean walking);

    // finds the best route between two locations
    public String findBestRoutes(String location1, String location2, boolean walking);

    // finds the cost of the best route between two locations
    public double findBestRoutesCost(String location1, String location2, boolean walking);

    // finds the best four multi stop routes between two locations
    public String findMultiStopRoutes(ArrayList<String> stops, boolean walking);

    // finds the cost of the best route between multi stop routes
    public double findMultiStopRoutesCost(ArrayList<String> stops, boolean walking);
    
    // returns all possible stops in the map
    public ArrayList<String> getLocations();

    // simply returns number of locations in graph and number of edges
     public String getStatisticsString();
}
