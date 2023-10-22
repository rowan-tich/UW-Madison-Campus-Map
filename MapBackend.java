import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MapBackend implements MapBackendInterface {

    protected GraphReaderInterface graphReader = new GraphReaderDW();
    protected MapAlgorithmInterface<String, Double> mainMap = new DijkstraGraph<String, Double>();

    public void loadData(String filename) throws FileNotFoundException {
        MapImportInterface map = graphReader.readGraphFromFile(filename);
        List<StopNodeInterface> nodesToAdd = map.getNodes();
        List<EdgePathInterface> edgesToAdd = map.getEdges();

        for (int i = 0; i < nodesToAdd.size(); i++) {
            String node = nodesToAdd.get(i).toString();
            mainMap.insertNode(node);
        }

        for (int j = 0; j < edgesToAdd.size(); j++) {
            EdgePathInterface edge = edgesToAdd.get(j);
            String predecessor = edge.getPredecessor().toString();
            String successor = edge.getSuccessor().toString();
            double weight = edge.getWeight();
            boolean isWalking = edge.isWalkingPath();
            mainMap.insertEdge(predecessor, successor, weight, isWalking);
        }
    }

    public void addLocation(String location) {
        mainMap.insertNode(location);
    }

    public void removeLocation(String location) {
        mainMap.removeNode(location);
    }

    public void addRoute(String location1, String location2, int time, boolean walking) {
        mainMap.insertEdge(location1, location2, (double) time, walking);
    }

    public void removeRoute(String location1, String location2, int time, boolean walking) {
        mainMap.removeEdge(location1, location2, walking);
    }

    public String findBestRoutes(String location1, String location2, boolean walking) {
        List<String> retBestRoute = mainMap.shortestPathData(location1, location2, walking);

        String retBestRouteStr = "";

        for (int i = 0; i < retBestRoute.size(); i++) {
            if (i % 4 == 0) { retBestRouteStr += "\n"; }
            if (i == retBestRoute.size() - 1) { retBestRouteStr += retBestRoute.get(i); break; }
            retBestRouteStr += retBestRoute.get(i) + " -> ";
        }
        
        return retBestRouteStr;
    }

    public String findMultiStopRoutes(ArrayList<String> stops, boolean walking) {
        List<String> retBestRoute = mainMap.shortestPathDataMulti(stops, walking);
        
        String retMultiRoute = "";

        for (int i = 0; i < retBestRoute.size(); i++) {
            if (i % 4 == 0) { retMultiRoute += "\n"; }
            if (i == retBestRoute.size() - 1) { retMultiRoute += retBestRoute.get(i); break; }
            retMultiRoute += retBestRoute.get(i) + " -> ";
        }

        return retMultiRoute;
    }

    public ArrayList<String> getLocations() {
        return (ArrayList<String>) mainMap.getLocations();
    }

    public String getStatisticsString() {
        return "There are " + mainMap.getNodeCount() + " locations in our map, and there are " + 
            mainMap.getEdgeCount() + " routes in our map.";
    }

    @Override
    public double findBestRoutesCost(String location1, String location2, boolean walking) {
        return mainMap.shortestPathCost(location1, location2, walking);
    }

    @Override
    public double findMultiStopRoutesCost(ArrayList<String> stops, boolean walking) {
        return mainMap.shortestPathCostMulti(stops, walking);
    }
}
