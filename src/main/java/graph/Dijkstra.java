package graph;

/** Class Dijkstra. Implementation of Dijkstra's algorithm for finding the shortest path
 * between the source vertex and other vertices in the graph.
 *  Fill in code. You may add additional helper methods or classes.
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Dijkstra
{
    private Graph graph;
    private List<Integer> shortestPath = null;

    /** Constructor
     *
     * @param filename name of the file that contains info about nodes and edges
     * @param graph graph
     */
    public Dijkstra(String filename, Graph graph)
    {
        this.graph = graph;
        graph.loadGraph(filename);
    }

    /**
     * Returns the shortest path between the origin vertex and the destination vertex.
     * The result is stored in shortestPathEdges.
     * This function is called from GUIApp, when the user clicks on two cities.
     * @param origin source node
     * @param destination destination node
     * @return the ArrayList of nodeIds (of nodes on the shortest path)
     */
    public List<Integer> computeShortestPath(CityNode origin, CityNode destination)
    {
        Pair[] dijkTable = new Pair[graph.numNodes()];  //index = nodeID, k = cost, v = parentID
        dijkTable[graph.getId(origin)] = new Pair(0, -1);

        PriorityQueue pq = new PriorityQueue(graph.numNodes());
        pq.insert(graph.getId(origin), 0, -1);
        for (int i = 0; i < graph.numNodes(); i++)
        {
            if (i == graph.getId(origin)) continue;
            pq.insert(i, Integer.MAX_VALUE, -1);
        }
        pq.removeMin();

        int niq = graph.getId(origin);
        int sumPrevCosts = 0;
        for (int i = 0; i < graph.numNodes()-1; i++)
        {
            Edge eiq = graph.getAdjacents(niq);
            while (eiq != null)
            {
                pq.reduceKey(eiq.neighbor(), eiq.cost() + sumPrevCosts, niq);
                eiq = eiq.next();
            }

            int cost = pq.cost();
            int par = pq.parent();
            int next = pq.removeMin();
            dijkTable[next] = new Pair(cost, par);
            niq = next;
            sumPrevCosts = cost;
        }

        shortestPath = new ArrayList<>();
        niq = graph.getId(destination);
        while (niq >= 0)
        {
            shortestPath.add(niq);
            niq = dijkTable[niq].v;
        }

        return shortestPath;
    }

    /**
     * Return the shortest path as a 2D array of Points.
     * Each element in the array is another array that has 2 Points:
     * these two points define the beginning and end of a line segment.
     * @return 2D array of points
     */
    public Point[][] getPath()
    {
        if (shortestPath == null) return null;

        return graph.getPath(shortestPath);
    }

    /** Set the shortestPath to null.
     *  Called when the user presses Reset button.
     */
    public void resetPath() { shortestPath = null; }
}