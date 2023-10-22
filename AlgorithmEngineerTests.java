import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AlgorithmEngineerTests {
    @Test
    public void testBusPathTrue(){
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();

        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");

        graph.insertEdge("A", "B", 3, false);
        graph.insertEdge("B", "C", 3, false);
        graph.insertEdge("A", "D", 2, true);
        graph.insertEdge("D", "E", 2, true);
        graph.insertEdge("E", "C", 2, true);


        assertEquals(graph.shortestPathData("A", "C", true).toString(), "[A, D, E, C]");
    }

    @Test
    public void testBusPathFalse(){
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();

        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");

        graph.insertEdge("A", "B", 3, false);
        graph.insertEdge("B", "C", 3, false);
        graph.insertEdge("A", "D", 2, true);
        graph.insertEdge("D", "E", 2, true);
        graph.insertEdge("E", "C", 2, true);


        assertEquals(graph.shortestPathData("A", "C", false).toString(), "[A, B, C]");
    }

    @Test
    public void testMultiPathData(){
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();

        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");

        graph.insertEdge("A", "B", 3, false);
        graph.insertEdge("B", "C", 3, false);
        graph.insertEdge("A", "D", 3, false);
        graph.insertEdge("D", "E", 3, false);
        graph.insertEdge("E", "C", 3, false);

        List<String> nodeList = new ArrayList<>();
        nodeList.add("A");
        nodeList.add("E");
        nodeList.add("C");

        assertEquals(graph.shortestPathDataMulti(nodeList, false).toString(), "[A, D, E, C]");
    }

    @Test
    public void testMultiPathCost(){
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();

        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");

        graph.insertEdge("A", "B", 3, false);
        graph.insertEdge("B", "C", 3, false);
        graph.insertEdge("A", "D", 3, false);
        graph.insertEdge("D", "E", 3, false);
        graph.insertEdge("E", "C", 3, false);

        List<String> nodeList = new ArrayList<>();
        nodeList.add("A");
        nodeList.add("E");
        nodeList.add("C");


        assertEquals(graph.shortestPathCostMulti(nodeList, false), 6.1875, 0.001);
    }

    @Test
    public void testRemoveEdge(){
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();

        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");

        graph.insertEdge("A", "B", 3, true);
        graph.insertEdge("B", "C", 3, true);
        graph.insertEdge("A", "D", 2, true);
        graph.insertEdge("D", "E", 2, true);
        graph.insertEdge("E", "C", 2, true);


        assertEquals(true, graph.containsEdge("D", "E", false));
        graph.removeEdge("D", "E", false);
        assertEquals(false, graph.containsEdge("D", "E", false));
    }

    @Test
    public void testIntegrationAddingAndRemovingEdges(){
        MapBackend mapBackend = new MapBackend();

        mapBackend.addLocation("A");
        mapBackend.addLocation("B");
        mapBackend.addRoute("A", "B", 1, false);

        assertEquals(mapBackend.getStatisticsString(), "There are 2 locations in our map, and there are 1 routes in our map.");

        mapBackend.removeRoute("A", "B", 1, false);
        assertEquals(mapBackend.getStatisticsString(), "There are 2 locations in our map, and there are 1 routes in our map.");
    }

    @Test
    public void testIntegrationLocationArrayThing(){
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");

        List<String> listOfLocations = new ArrayList<>();
        listOfLocations.add("A");
        listOfLocations.add("B");
        listOfLocations.add("C");
        listOfLocations.add("D");
        listOfLocations.add("E");

        assertEquals(listOfLocations.size(), graph.getLocations().size());

        listOfLocations.remove(0);
        graph.removeNode("A");

        assertEquals(listOfLocations.size(), graph.getLocations().size());
    }

    @Test
    public void testCodeReviewOfBackendDeveloperAddAndRemove(){
        MapBackend mapBackend = new MapBackend();
        mapBackend.addLocation("a");
        assertEquals(1, mapBackend.getLocations().size());
        mapBackend.removeLocation("a");
        assertEquals(0, mapBackend.getLocations().size());
    }

    @Test
    public void testCodeReviewOfBackendDeveloperLoadData() throws FileNotFoundException{
        MapBackend mapBackend = new MapBackend();

        mapBackend.loadData("madisonmap.dot");

        assertEquals(mapBackend.getStatisticsString(), "There are 71 locations in our map, and there are 208 routes in our map.");
    }
}
