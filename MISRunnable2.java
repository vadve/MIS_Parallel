import java.util.*;
public class MISRunnable2 implements Runnable
{
    
    private final int start_index;
    private final int end_index;
    private AdjacencyGraph.Node[] nodes;
    public MISRunnable2(int start,int end,AdjacencyGraph.Node[] nodes)
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
        	check(curr_nodes,v);
        }
    }

    public static void check(AdjacencyGraph.Node[] nodes, AdjacencyGraph.Node v)
    {

        for(int i = 0; i < nodes.length; i++)
        {
            if(nodes[i] == v)
	        nodes[i].lock.writeLock().lock();
	    else
                nodes[i].lock.readLock().lock();
        }
        
	boolean flag = true;
        for(AdjacencyGraph.Node u : v.neighbors)
        {
            if(u.inMIS)
            {
                flag = false;
                break;
	    }
        }
        if(flag)
           v.inMIS = true;
        
        for(int i = 0; i < nodes.length; i++)
        {
            if(nodes[i] == v)
               nodes[i].lock.writeLock().unlock();
            else
               nodes[i].lock.readLock().unlock();
        }
    }




}
