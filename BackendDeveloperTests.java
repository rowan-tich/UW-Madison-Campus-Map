import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class BackendDeveloperTests {
    
    /**
     * This method simply tests if the findBestRoutes method works as intended. The desired return format
     * is a string of locations which contain the shortest path from start to finish
     * @throws FileNotFoundException
     */
    @Test
    public void testbestRouteMethod() throws FileNotFoundException {
        MapBackend test1 = new MapBackend();
        test1.loadData("madisonmap.dot");

	String returnStr = test1.findBestRoutes("Smith Residence Hall", "Ogg Residence Hall", true);
        assertEquals(true, returnStr.contains("Smith Residence Hall") && returnStr.contains("Ogg Residence Hall"));
    }

    /**
     * This method tests whether or not the multi stop method works as intended by taking in an arbitrary 
     * ArrayList and returning the correct shortest path
     * @throws FileNotFoundException
     */
    @Test
    public void testMultiStopMethod() throws FileNotFoundException {
        MapBackend test2 = new MapBackend();
        test2.loadData("madisonmap.dot");
        ArrayList<String> testLst = new ArrayList<>();
        testLst.add("Smith Residence Hall");
        testLst.add("Ogg Residence Hall");
        testLst.add("Grainger Hall");

	String resultOfMultiStop = test2.findMultiStopRoutes(testLst, true);
        assertEquals(true, resultOfMultiStop.contains("Smith Residence Hall") && resultOfMultiStop.contains("Ogg Residence Hall") && resultOfMultiStop.contains("Merit Residence Hall") && resultOfMultiStop.contains("Grainger Hall"));
    }

    /**
     * This method makes sure that the loadData function works as intended, by seeing if our .dot file is correctly
     * read into our graph map in MapBackend, and if MapImport works as intended
     * @throws FileNotFoundException
     */
    @Test
    public void testCostMethods() throws FileNotFoundException {
        MapBackend test3 = new MapBackend();

        test3.loadData("madisonmap.dot");

        assertEquals(71, test3.getLocations().size());

        test3.addLocation("test2");
        test3.addLocation("test3");
        test3.addLocation("test1");

        assertEquals(true, test3.getLocations().contains("test2") && test3.getLocations().contains("test1") &&
        test3.getLocations().contains("test3"));
    }

    /**
     * This method makes sure that the statistics string words as intended by displaying the correct values
     * of number of locations and edges in the graph
     * @throws FileNotFoundException
     */
    @Test
    public void testStatisticsString() throws FileNotFoundException {
        MapBackend test4 = new MapBackend();
        test4.loadData("madisonmap.dot");

        assertEquals("There are 71 locations in our map, and there are 208 routes in our map.", test4.getStatisticsString());
    }

    /**
     * This method makes sure that the remove location and add location methods work as intended
     * This just adds a couple of locations in Madison to the main graph, and also removes some
     * and sees if the correct size and locations remain
     */
    @Test
    public void testAddAndRemove() {
        MapBackend test5 = new MapBackend();
        test5.addLocation("Bakke Recreation and Wellbeing");
        test5.addLocation("DeLuca Biochem");
        test5.addLocation("Engineering Hall");
        test5.addLocation("Union South");
        test5.addLocation("The Shell");
        test5.removeLocation("The Shell");

        assertEquals(4, test5.getLocations().size());
        assertEquals(true, test5.getLocations().contains("Bakke Recreation and Wellbeing") &&
            test5.getLocations().contains("Engineering Hall") &&
            test5.getLocations().contains("Union South"));
        assertEquals(false, test5.getLocations().contains("The Shell"));
    }

    /**
     * This method is the first of two integration tests. This specific method loads in the data from the DW's graph reader, and
     * makes use of the multi path functions of the AE to compute both the shortest multi stop path and the shortest multi stop 
     * cost.
     * @throws FileNotFoundException
     */
    @Test
    public void testIntegration1() throws FileNotFoundException {
        MapBackend integrationOne = new MapBackend();
        integrationOne.loadData("madisonmap.dot");

        ArrayList<String> testLst = new ArrayList<>();
        testLst.add("Smith Residence Hall");
        testLst.add("Ogg Residence Hall");
        testLst.add("The Kohl Center");

        String resultWalking = integrationOne.findMultiStopRoutes(testLst, true);
        double resultWalkingCost = integrationOne.findMultiStopRoutesCost(testLst, true);
        assertEquals(true, resultWalking.contains("Smith Residence Hall") && resultWalking.contains("The Kohl Center"));
        assertEquals(4.0, resultWalkingCost, 0.001);
    }

    /**
     * This method is the second of two integration tests. This specific method tests the difference between bus paths and walking
     * paths. Specifically, it tests especially that a bus path from a location to another location is faster in the specific instance
     * tested in the method body.
     * @throws FileNotFoundException
     */
    @Test
    public void testIntegration2() throws FileNotFoundException {
        MapBackend integrationTwo = new MapBackend();
        integrationTwo.loadData("madisonmap.dot");

        ArrayList<String> testLst = new ArrayList<>();
        testLst.add("Smith Residence Hall");
        testLst.add("Ogg Residence Hall");
        testLst.add("Grainger Hall");
        testLst.add("University Hospital");

        String resultWalking = integrationTwo.findMultiStopRoutes(testLst, true);
        String resultBussing = integrationTwo.findMultiStopRoutes(testLst, false);
        double resultWalkingCost = integrationTwo.findMultiStopRoutesCost(testLst, true);
        double resultBussingCost = integrationTwo.findMultiStopRoutesCost(testLst, false);
    
        assertEquals(true, resultWalking.contains("Smith Residence Hall") && resultWalking.contains("Ogg Residence Hall") && 
            resultWalking.contains("Grainger Hall") && resultWalking.contains("University Hospital"));
        assertEquals(true, resultBussing.contains("Smith Residence Hall") && resultWalking.contains("Ogg Residence Hall") && 
            resultWalking.contains("Grainger Hall") && resultWalking.contains("University Hospital"));
        assertEquals(true, resultWalkingCost > resultBussingCost);
    }

    /**
     * This is one of two tests which examines the AE's code. The specific test looks into whether or not the ability to add a node 
     * works as intended, since that is an integral part of 
     */
    @Test
    public void testCodeReviewOfAlgorithmEngineer1() {
        DijkstraGraph<String, Integer> test = new DijkstraGraph<>();
        test.insertNode("A");
        test.insertNode("B");
        test.insertNode("H");
        test.insertNode("M");
        test.insertNode("E");
        test.insertNode("F");
        test.insertNode("I");
        test.insertNode("D");
        test.insertNode("G");
        test.insertNode("L");
        test.removeNode("E");
        test.removeNode("A");

        assertEquals(8, test.getNodeCount());
    }

    /**
     * This is the second of two tests which examines the AE's code. This specific test looks into the whether or not the shortest
     * path computation works as intended and if the returned cost is accurate.
     */
    @Test
    public void testCodeReviewOfAlgorithmEngineer2() {
        DijkstraGraph<String, Integer> test = new DijkstraGraph<>();
        // inserting the nodes
        test.insertNode("A");
        test.insertNode("B");
        test.insertNode("H");
        test.insertNode("M");
        test.insertNode("E");
        test.insertNode("F");
        test.insertNode("I");
        test.insertNode("D");
        test.insertNode("G");
        test.insertNode("L");

        // inserting the edges
        test.insertEdge("I", "H", 2, true);
        test.insertEdge("H", "I", 2, true);
        test.insertEdge("A", "H", 8, true);
        test.insertEdge("H", "B", 6, true);
        test.insertEdge("A", "B", 1, true);
        test.insertEdge("B", "M", 3, true);
        test.insertEdge("A", "M", 5, true);
        test.insertEdge("M", "E", 3, true);
        test.insertEdge("M", "F", 4, true);
        test.insertEdge("F", "G", 9, true);
        test.insertEdge("D", "G", 2, true);
        test.insertEdge("G", "L", 7, true);
        test.insertEdge("D", "A", 7, true);
        test.insertEdge("I", "D", 1, true);
        test.insertEdge("I", "L", 5, true);

        assertEquals("[A, H, I, D, G]", test.shortestPathData("A", "G", true).toString());
        assertEquals(13.0, test.shortestPathCost("A", "G", true), 0.1);
    }

    // -----------------------------------------------------------------------------------
    //                        Code Review of Algorithm Engineer
    //                        ---------------------------------
    //  1. I do not see any stylistic ways in which the AE's code could be improved. He did
    //     a very good job commenting what he were doing each step of the way in a succinct
    //     manner. The only thing he could improve is provide an explanation of how the pity
    //     factor comes into play and its prominence.
    //
    //  2. No, I do not recognize any way to simplify his code. His tasks was very hard
    //     in regards to computing shortest path and also computing multiple shortest path.
    //     The organization of the code was also top notch, since he separated the document
    //     to showcase the new code he added.
    //     
    //  3. The only portions I can think of in which extra comments would be helpful are 
    //     when he is computing the multi path data and cost, because it got a little bit
    //     confusing trying to understand it. Other than that, everything is great.
    //
    //  4. I did not encounter any bugs with the code. In my two code review tests, no errors
    //     came up, and no errors came up in the integration tests either, which make great
    //     use of the AE's code.
    // -----------------------------------------------------------------------------------
}
