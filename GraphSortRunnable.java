public class GraphSortRunnable implements Runnable
{
    private final int start_idx;
    private final int end_idx;
    private AdjacencyGraph.Node[] nodes;

    public GraphSortRunnable(int start, int end, AdjacencyGraph.Node[] nodes)
    {
        this.start_idx = start;
        this.end_idx = end;
        this.nodes = nodes;
    }
  
 
    public void run()
    {
        for(int i = this.start_idx; i < this.end_idx; i++)
            AdjacencyGraph.sort_neighbors(nodes[i]);
    }






}
