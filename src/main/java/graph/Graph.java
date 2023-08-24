package graph;


/** A class that represents a graph where nodes are cities (of type CityNode).
 * The cost of each edge connecting two cities is the distance between the cities.
 */

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph
{
    public final int EPS_DIST = 5;
    private int numNodes;
    private int numEdges;
    private CityNode[] nodes;
    private Edge[] adjacencyList;
    private Map<String, Integer> labelsToIndices;

    /**
     * Read graph info from the given file, and create nodes and edges of
     * the graph.
     *
     * @param filename name of the file that has nodes and edges
     */
    public void loadGraph(String filename)
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            br.readLine();
            String buf = br.readLine();
            numNodes = Integer.parseInt(buf);
            nodes = new CityNode[numNodes];
            adjacencyList = new Edge[numNodes];
            labelsToIndices = new HashMap(numNodes);

            for (int i = 0; i < numNodes; i++)
            {
                buf = br.readLine();
                String[] tokens = buf.split(" ");
                labelsToIndices.put(tokens[0], i);
                this.addNode(new CityNode(tokens[0], Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])));
            }
            br.readLine();

            while ((buf = br.readLine()) != null)
            {
                String[] tokens = buf.split(" ");
                this.addEdge(labelsToIndices.get(tokens[0]), new Edge(labelsToIndices.get(tokens[1]), Integer.parseInt(tokens[2])));
                this.addEdge(labelsToIndices.get(tokens[1]), new Edge(labelsToIndices.get(tokens[0]), Integer.parseInt(tokens[2])));
            }
        }
        catch (IOException e) { e.printStackTrace(); System.exit(1); }
    }

    /**
     * Get the head of a linked list
     * containing all outgoing edges from
     * an origin.
     *
     * @param i index of origin
     */
    public Edge getAdjacents(int i) { return adjacencyList[i]; }

    /**
     * Add a node to the array of nodes.
     * Called from loadGraph.
     *
     * @param node a CityNode to add to the graph
     */
    public void addNode(CityNode node) { nodes[labelsToIndices.get(node.getCity())] = node; }

    /**
     * Return the number of nodes in the graph.
     * @return number of nodes
     */
    public int numNodes() { return numNodes; }

    /**
     * Adds the edge to the linked list for the given nodeId
     * Called from loadGraph.
     *
     * @param nodeId id of the node
     * @param edge edge to add
     */
    public void addEdge(int nodeId, Edge edge)
    {
        numEdges++;

        if (adjacencyList[nodeId] == null) adjacencyList[nodeId] = edge;
        else adjacencyList[nodeId].add(edge);
    }

    /**
     * Returns an integer id of the given city node
     * @param city node of the graph
     * @return its integer id
     */
    public int getId(CityNode city) { return labelsToIndices.get(city.getCity()); }

    /**
     * Return the edges of the graph as a 2D array of points.
     * Called from GUIApp to display the edges of the graph.
     *
     * @return a 2D array of Points.
     * For each edge, we store an array of two Points, v1 and v2.
     * v1 is the source vertex for this edge, v2 is the destination vertex.
     */
    public Point[][] getEdges()
    {
        if (adjacencyList == null || adjacencyList.length == 0) return null;

        Point[][] edges2D = new Point[numEdges][2];

        int ie = 0;
        for (int i = 0; i < numNodes; i++)
        {
            Edge eiq = adjacencyList[i];
            while (eiq != null)
            {
                edges2D[ie][0] = nodes[i].getLocation();
                edges2D[ie][1] = nodes[eiq.neighbor()].getLocation();
                ie++;
                eiq = eiq.next();
            }
        }

        return edges2D;
    }

    /**
     * Get the nodes of the graph as a 1D array of Points.
     * Used in GUIApp to display the nodes of the graph.
     * @return a list of Points that correspond to nodes of the graph.
     */
    public Point[] getNodes()
    {
        if (nodes == null) return null;

        Point[] nodes = new Point[this.nodes.length];
        for (int i = 0; i < nodes.length; i++) nodes[i] = this.nodes[i].getLocation();

        return nodes;
    }

    /**
     * Used in GUIApp to display the names of the airports.
     * @return the list that contains the names of cities (that correspond
     * to the nodes of the graph)
     */
    public String[] getCities()
    {
        if (this.nodes == null) return null;

        String[] labels = new String[nodes.length];
        for (int i = 0; i < nodes.length; i++) labels[i] = nodes[i].getCity();

        return labels;
    }

    /** Take a list of node ids on the path and return an array where each
     * element contains two points (an edge between two consecutive nodes)
     * @param pathOfNodes A list of node ids on the path
     * @return array where each element is an array of 2 points
     */
    public Point[][] getPath(List<Integer> pathOfNodes)
    {
        Point[][] edges2D = new Point[pathOfNodes.size()-1][2];

        for (int i = 0; i < edges2D.length; i++)
        {
            edges2D[i][0] = nodes[pathOfNodes.get(i)].getLocation();
            edges2D[i][1] = nodes[pathOfNodes.get(i+1)].getLocation();
        }

        return edges2D;
    }

    /**
     * Return the CityNode for the given nodeId
     * @param nodeId id of the node
     * @return CityNode
     */
    public CityNode getNode(int nodeId) { return nodes[nodeId]; }

    /**
     * Take the location of the mouse click as a parameter, and return the node
     * of the graph at this location. Needed in GUIApp class. No need to modify.
     * @param loc the location of the mouse click
     * @return reference to the corresponding CityNode
     */
    public CityNode getNode(Point loc)
    {
        if (nodes == null) return null;

        for (CityNode v : nodes)
        {
            Point p = v.getLocation();
            if ((Math.abs(loc.x - p.x) < EPS_DIST) && (Math.abs(loc.y - p.y) < EPS_DIST))
                return v;
        }
        return null;
    }
}