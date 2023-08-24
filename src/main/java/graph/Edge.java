package graph;

/** Edge class represents a link in the linked list of edges for a vertex.
 *  Each Edge stores the id of the "neighbor" (the vertex where this edge is going =
 *  "destination" vertex), the cost and the reference to the next Edge.
 */
class Edge
{
    private int neighbor;
    private int cost;
    private Edge next;

    public Edge(int neighbor, int cost)
    {
        this.neighbor = neighbor;
        this.cost = cost;
        next = null;
    }

    public int neighbor() { return neighbor; }
    public int cost() { return cost; }
    public Edge next() { return next; }

    public boolean hasNext() { return next != null; }

    public void add(Edge edge)
    {
        Edge eiq = this;
        while (eiq.hasNext()) eiq = eiq.next();
        {
            eiq.next = edge;
        }
    }
}