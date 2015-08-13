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
   
	public static void main(String[] args) throws java.io.IOException
	{
		AdjacencyGraph g = AdjacencyGraph.readAdjacencyGraph(args[0]);
		final int NUM_THREADS = 32;
		int size = g.nodes.length / NUM_THREADS;
		List<Thread> threads = new ArrayList<Thread>();
		final long start_time = System.currentTimeMillis();
		for(int i = 0; i < NUM_THREADS; i++)
		{
			Runnable task = new MISRunnable(i*size, (i+1)*size, g.nodes);
			Thread worker = new Thread(task);
			worker.setName(String.valueOf(i));
			worker.start();
			threads.add(worker);
		}
		int threads_running = 0;
		do
		{
			threads_running = 0;
			for(Thread thread : threads)
			{
				if(thread.isAlive())
					threads_running++;
			}
		}while(threads_running > 0);
		final long end_time = System.currentTimeMillis();
		System.out.println("Run time was: " + (end_time - start_time) + " milliseconds");
		countMIS(g);

	}

}
