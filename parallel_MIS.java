import java.util.*;
import java.io.*;
class parallel_MIS
{
	
	public static void check(AdjacencyGraph.Node v)
	{       
               AdjacencyGraph.Node[] nodes = new AdjacencyGraph.Node[v.neighbors.length+1];
	       nodes[0] = v;
	       for(int i =0; i < v.neighbors.length; i++)
	           nodes[i+1] = v.neighbors[i];
	       
	       Arrays.sort(nodes, new Comparator<AdjacencyGraph.Node>(){
                @Override
                   public int compare(AdjacencyGraph.Node n1,AdjacencyGraph.Node n2){
                       return n1.index - n2.index;
                   }
               });
               check(nodes,0,v);
	}

	// recursive function to acquire locks on v and all its neighbors
	public static void check(AdjacencyGraph.Node[] nodes, int depth, AdjacencyGraph.Node v)
        {
            // base case = all locks have been acquired
            if(depth == nodes.length)
	    {
                for(AdjacencyGraph.Node u: v.neighbors)
		{
			if(u.inMIS)
			    return;
		}
		v.inMIS = true;
	    }
	    
	    else
	    {
	        synchronized(nodes[depth])
		{
		    check(nodes,depth+1,v);
		}
	    }
        }

	public static void countMIS(AdjacencyGraph g)
	{
		int count = 0;
		for(AdjacencyGraph.Node v : g.nodes)
		{
			if(v.inMIS)
				count++;
		}
		System.out.println(count + " vertices in MIS");
	}
   

	public static void buildMIS(AdjacencyGraph g)
	{
		for(AdjacencyGraph.Node v : g.nodes)
		{
			check(v);
		}

	}


    public static void main(String[] args) throws java.io.IOException
	{
		AdjacencyGraph g = AdjacencyGraph.readAdjacencyGraph(args[0]);
		final long start_time = System.currentTimeMillis();
		buildMIS(g);
		final long end_time = System.currentTimeMillis();
		System.out.println("Run time was: " + (end_time - start_time) + " milliseconds");
		countMIS(g);

	}
}
