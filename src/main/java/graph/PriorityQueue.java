package graph;


import java.util.Arrays;

/** A class that implements a priority queue and all
 *  integral functionality.
 */

public class PriorityQueue
{
    private int size;
    private int capacity;
    private Trip[] heap; //id = nodeID, k = cost, v = parentID
    private int[] locs;
    private boolean[] active;

    public PriorityQueue(int capacity)
    {
        this.size = 0;
        this.capacity = capacity;
        heap = new Trip[capacity];
        locs = new int[capacity];
        active = new boolean[capacity];
        Arrays.fill(locs, -1);
    }

    public Trip[] heap() { return heap; }
    public int[] locs() { return locs; }

    public void insert(int nodeId, int cost, int parentId)
    {
        if (size == capacity) return;

        heap[size] = new Trip(nodeId, cost, parentId);
        locs[nodeId] = size;
        active[nodeId] = true;
        size++;
    }

    public int cost() { return heap[0].k; }
    public int parent() { return heap[0].v; }

    public int removeMin()
    {
        if (size < 1) return -1;

        int g = heap[0].id;

        size--;
        this.swap(0, size);
        heap[size] = null;
        active[g] = false;

        this.bubbleDown(0);
        return g;
    }

    private void bubbleDown(int i)
    {
        int l = i + i + 1;
        int r = l+1;

        if (heap[l] == null && heap[r] == null) return;

        int min;
        if      (heap[r] == null)          min = l;
        else if (heap[l] == null)          min = r;
        else min = (heap[l].k <= heap[r].k) ? l : r;

        if (heap[i].k > heap[min].k)
        {
            this.swap(i, min);
            if ((min + min + 1) < size)
                this.bubbleDown(min);
        }
    }

    private void bubbleUp(int i)
    {
        if (i < 1) return;

        int t = (i-1)/2;
        int a = t + (t+1) + (i%2);

        int min;
        if (heap[a] == null) min = i;
        else min = (heap[i].k <= heap[a].k) ? i : a;

        if (heap[t].k > heap[min].k)
        {
            this.swap(t, min);
            this.bubbleUp(t);
        }
    }

    public void reduceKey(int nodeId, int newCost, int newParent)
    {
        if (nodeId < 0 || nodeId >= capacity) return;
        if (!active[nodeId]) return;
        if (heap[locs[nodeId]].k <= newCost) return;

        heap[locs[nodeId]].k = newCost;
        heap[locs[nodeId]].v = newParent;
        this.bubbleUp(locs[nodeId]);
    }

    public void swap(int a, int b)
    {
        locs[heap[a].id] = b;
        locs[heap[b].id] = a;

        Trip tmp = this.heap[a];
        this.heap[a] = this.heap[b];
        this.heap[b] = tmp;
    }

    @Override
    public String toString()
    {
        StringBuilder stb = new StringBuilder();

        stb.append("Heap:\n");
        int i;
        for (i = 0; i < size; i++)
        {
            stb.append("  ").append(i)
                    .append(".  nodeID: ").append(heap[i].id)
                    .append(",  cost: ").append(heap[i].k)
                    .append(",  parentID: ").append(heap[i].v);
            if (i != size-1) stb.append("\n");
        }

        return stb.toString();
    }
}