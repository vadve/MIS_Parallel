import java.util.*;
public class MISRunnable3 implements Runnable
{
    
    private final int start_index;
    private final int end_index;
    private AdjacencyGraph.Node[] nodes;
    public MISRunnable3(int start,int end,AdjacencyGraph.Node[] nodes)
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
        	check(v.neighbors,0,false,v);
        }
    }

    public static void check(AdjacencyGraph.Node[] nodes,int depth, boolean v_isLocked, AdjacencyGraph.Node v)
    {
          
        if(depth == nodes.length && v_isLocked)
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
 
	    if((depth == nodes.length || v.index < nodes[depth].index) && !v_isLocked)
	    {
	        synchronized(v)
                {
                   check(nodes,depth,true,v);
                }
            }
            else
            {
               synchronized(nodes[depth])
               {
                  check(nodes,depth+1,v_isLocked,v);
               }
            }
        }
    }




}
