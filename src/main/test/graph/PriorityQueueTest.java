package graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PriorityQueueTest
{
    PriorityQueue pq;

    @BeforeEach
    void before()
    {
        pq = new PriorityQueue(16);
        for (int i = 0; i < 12; i++) pq.insert(i, i*10, 0);
        System.out.print("Initial ");
        System.out.println(pq);
    }

    @Test
    void insert()
    {
        for (int i = 0; i < 4; i++) pq.insert(i, i*10, 0);
        System.out.print("4 Inserted into ");
        System.out.println(pq);
    }

    @Test
    void removeMin()
    {
        System.out.print("1st min: ");
        System.out.println(pq.removeMin());
        System.out.print("1 Removed ");
        System.out.println(pq);
        for (int i = 0; i < 4; i++) pq.removeMin();
        System.out.print("5 Total Removed ");
        System.out.println(pq);
        for (int i = 0; i < 8; i++) pq.removeMin();
        System.out.println("no fail on more removes than size");
    }

    @Test
    void reduceKey()
    {
        for (int i = 0; i < 16; i++) System.out.print("locs[" + i + "]::" + pq.locs()[i] + ", ");
        System.out.println();
        System.out.println("Reduce key on nodeID 4, from locs[] = " + pq.locs()[4]);
        pq.reduceKey(4, 1, 0);
        for (int i = 0; i < 16; i++) System.out.print("locs[" + i + "]::" + pq.locs()[i] + ", ");
        System.out.print("4th Reduced ");
        System.out.println(pq);
        pq.reduceKey(11, 3, 0);
        for (int i = 0; i < 16; i++) System.out.print("locs[" + i + "]::" + pq.locs()[i] + ", ");
        System.out.print("11th Reduced ");
        System.out.println(pq);
        pq.reduceKey(13, 5, 0);
        for (int i = 0; i < 16; i++) System.out.print("locs[" + i + "]::" + pq.locs()[i] + ", ");
        System.out.print("13th Reduced ");
        System.out.println(pq);
    }

    @Test
    void swap()
    {
        System.out.println(Arrays.toString(pq.heap()));

        pq.swap(0, 1);
        System.out.println(Arrays.toString(pq.heap()));
    }
}