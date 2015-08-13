import java.util.*;
public class MISRunnable implements Runnable
{
    
    private final int start_index;
    private final int end_index;
    private AdjacencyGraph.Node[] nodes;
    public MISRunnable(int start,int end,AdjacencyGraph.Node[] nodes)
    {
        this.start_index = start;
	this.end_index = end;
	this.nodes = nodes;

    }
    @Override
    public void run()
    {
	for(int i = this.start_index; i < this.end_index; i++)
	{
		AdjacencyGraph.Node v = this.nodes[i];
        	AdjacencyGraph.Node[] curr_nodes = new AdjacencyGraph.Node[v.neighbors.length+1];
        	curr_nodes[0] = v;
        	for(int j =0; j < v.neighbors.length; j++)
            		curr_nodes[j+1] = v.neighbors[j];
               
        	Arrays.sort(curr_nodes, new Comparator<AdjacencyGraph.Node>(){
            		@Override
            		public int compare(AdjacencyGraph.Node n1,AdjacencyGraph.Node n2){
                		return n1.index - n2.index;
            		}
        	});
        	check(curr_nodes,0,v);
        }
    }

    public static void check(AdjacencyGraph.Node[] nodes, int depth, AdjacencyGraph.Node v)
    {
            
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




}
