import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
class parallel_MIS
{
	 

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
   
	public static void main(String[] args) throws java.io.IOException, java.lang.InterruptedException
	{
		AdjacencyGraph g = AdjacencyGraph.readAdjacencyGraph(args[0]);
		final int NUM_THREADS = Integer.parseInt(args[1]);
		int size = g.nodes.length / NUM_THREADS;

                List<Thread> threads_sort = new ArrayList<Thread>();
                final long start_sort = System.currentTimeMillis();
                for(int i = 0; i < NUM_THREADS; i++)
                {
                    Runnable task = new GraphSortRunnable(i*size, (i+1)*size, g.nodes);
                    Thread worker = new Thread(task);
                    
                    worker.setName(String.valueOf(i));
                    worker.start();
                    threads_sort.add(worker);
                }
                for(int i = 0; i < NUM_THREADS; i++)
                    threads_sort.get(i).join();
                final long end_sort = System.currentTimeMillis();
                System.out.println("sort time: " + (end_sort - start_sort)+ "milliseconds");

		List<Thread> threads = new ArrayList<Thread>();
		final long start_time = System.currentTimeMillis();
		for(int i = 0; i < NUM_THREADS; i++)
		{
			Runnable task = new MISRunnable3(i*size, (i+1)*size, g.nodes);
			Thread worker = new Thread(task);
			worker.setName(String.valueOf(i));
			worker.start();
			threads.add(worker);
		} 
		for(int i=0; i < NUM_THREADS; i++)
			threads.get(i).join();
		final long end_time = System.currentTimeMillis();
		System.out.println("MIS time: " + (end_time - start_time) + " milliseconds");
		countMIS(g);

	}

}
