import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class assists in loading .dot files and turing them into graphs
 */
public class GraphReaderDW implements GraphReaderInterface {

    /**
     * This method loads the locations and paths from a .dot file and turns it into a graph
     * @param filename the .dot file to load the graph from
     * @return a Hashtable with all the location.toLowerCase() as the key and the corresponding node
     * @throws FileNotFoundException if the file is not found
     */
    public MapImportDW readGraphFromFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner readFile = new Scanner(file);
        Hashtable<String, StopNodeInterface> visitedNodes = new Hashtable<>();
        MapImportDW map = new MapImportDW();

        // Regex Guide
        // Group 1: Location 1
        // Group 2: Location 2
        // Group 3: contains weight of path as well as if it is walking or bus in format:
        //          Ex: 43, bus
        Pattern pattern = Pattern.compile("\"(.+)\".*->.*\"(.+)\".*\\[.*\"(.*)\".*");


        while (readFile.hasNextLine()) {
            String nextLine = readFile.nextLine().trim();
            Matcher matcher = pattern.matcher(nextLine);

            // if the pattern is found on this line then we have to gather all the information
            // and turn it into a path
            if (matcher.find()) {

                // get location 1: the predecessor
                String location1 = matcher.group(1);
                // get location 2: the successor
                String location2 = matcher.group(2);
                // split group 3 around the comma and get the double value from the part before
                // the comma
                double weight = Double.parseDouble(matcher.group(3).split(",")[0]);
                // check if group 3 contains the word "walk". if not then it must be a bus route
                boolean isWalking = matcher.group(3).toLowerCase().contains("walk");

                StopNodeInterface node1;
                StopNodeInterface node2;

                // check if location 1 has already been added to the hashtable
                // if it has not been added then create a new node for that location and add it
                // to the hashtable
                if (!visitedNodes.containsKey(location1.toLowerCase())) {
                    node1 = new StopNodeDW(location1);
                    visitedNodes.put(location1.toLowerCase(), node1);
                    map.addNode(node1);

                } else {
                    node1 = visitedNodes.get(location1.toLowerCase());
                }

                // check if location 2 has already been added to the hashtable
                // if it has not been added then create a new node for that location and add it
                // to the hashtable
                if (!visitedNodes.containsKey(location2.toLowerCase())) {
                    node2 = new StopNodeDW(location2);
                    visitedNodes.put(location2.toLowerCase(), node2);
                    map.addNode(node2);
                } else {
                    node2 = visitedNodes.get(location2.toLowerCase());
                }

                // If the path is a walking path then we need to create 2 edges one going into
                // the successor and one going into the predecessor. (We can walk from building1
                // to building2, but also from building2 to building1)
                if (isWalking) {
                    // edge going from node1 to node2
                    EdgePathInterface edge1 = new EdgePathDW(weight, isWalking, node1, node2);
                    // edge going from node2 to node1
                    EdgePathInterface edge2 = new EdgePathDW(weight, isWalking, node2, node1);

                    // add the edges to the nodes' lists
                    node1.addEdgeComingFrom(edge1);
                    node1.addEdgeGoingInto(edge2);
                    node2.addEdgeGoingInto(edge1);
                    node2.addEdgeComingFrom(edge2);
                    map.addEdge(edge1);
                    map.addEdge(edge2);

                // if it is a bus path then we only need to create one new edge from node1 to node 2
                } else {
                    EdgePathInterface edge = new EdgePathDW(weight, isWalking, node1, node2);
                    node1.addEdgeComingFrom(edge);
                    node2.addEdgeGoingInto(edge);
                    map.addEdge(edge);
                }

            }
        }
		readFile.close();
        map.setMap(visitedNodes);
        return map;
    }

}