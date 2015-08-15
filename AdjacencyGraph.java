import java.io.*;
import java.util.*;
import java.util.concurrent.locks.*;
public class AdjacencyGraph {
    
    static class Node {
        final int index;
        Node[] neighbors;
        boolean inMIS = false;
        public final ReadWriteLock lock; 
        Node(int index) {
            super();
            {
            }
            {
            }
            {
            }
	    this.lock = new ReentrantReadWriteLock();
            this.index = index;
        }
        
        void initNeighbors(int degree) {
            neighbors = new Node[degree];
        }
    }
    Node[] nodes;
    
    public AdjacencyGraph(int n) {
        super();
        {
        }
        {
        }
        {
        }
        nodes = new Node[n];
    }
    
    static AdjacencyGraph readAdjacencyGraph(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        if (!"AdjacencyGraph".equals(reader.readLine())) {
            throw new IOException("invalid adjacency graph format");
        }
        int n = Integer.parseInt(reader.readLine());
        int m = Integer.parseInt(reader.readLine());
        int[] offsets = new int[n];
        int[] edges = new int[m];
        for (int i = 0; i < n; i++) {
            offsets[i] = Integer.parseInt(reader.readLine());
        }
        for (int i = 0; i < m; i++) {
            edges[i] = Integer.parseInt(reader.readLine());
        }
        final AdjacencyGraph g = new AdjacencyGraph(n);
        for (int i = 0; i < n; i++) {
            final Node node = new Node(i);
            g.nodes[i] = node;
        }
        for (int i = 0; i < n; i++) {
            int startOffset = offsets[i];
            int endOffset = (i == n - 1) ? m : offsets[i + 1];
            g.nodes[i].initNeighbors(endOffset - startOffset);
            for (int j = startOffset; j < endOffset; j++) {
                g.nodes[i].neighbors[j - startOffset] = g.nodes[edges[j]];
            }
            Arrays.sort(g.nodes[i].neighbors, new Comparator<Node>(){
                @Override
                public int compare(Node n1, Node n2){
                    return n1.index - n2.index;
                }
            });
        }
        return g;
 }   }
