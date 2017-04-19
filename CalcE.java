import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.math.BigDecimal;

public class CalcE {
	
	public static void main(String[] args) throws FileNotFoundException{	
		
		// Time of start
		long start1 = System.currentTimeMillis();
		//Default values
		int el_count=1000;
		int thread_count=1;
		boolean quiet = false;
		String nameOffile = "result.txt";
		
		
		//Getting values of command line parameters
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-p")) {
				el_count = new Integer(args[i + 1]);
				}
			if (args[i].equals("-t") || args[i].equals("-task")) {
				thread_count = new Integer(args[i + 1]);
		    }
			if (args[i].equals("-o")) {
				nameOffile = args[i + 1];
			}
			if (args[i].equals("-q"))
			{
				quiet = true;
		    }
		}
		
		//Check for correct input
		if (el_count <= 0 || thread_count <= 0 || args.length < 4 || args.length > 8) 
			System.out.println("ERROR: Args are not correct!!!");
		
		// Array of result for every thread
		BigDecimal res[] = new BigDecimal[thread_count];
		// Array of threads
		Thread tr[] = new Thread[thread_count];
		// Size of iterations for every thread
		int int_size = el_count / (thread_count);
		//Rounding scale
		int scale=el_count;
			
		for(int i = 0; i < thread_count; i++) {
			// Creates a runnable object
			MyThread r = new MyThread(el_count, res, int_size, i, thread_count, scale, quiet);
			// Creates a thread over this runnable object
			Thread t = new Thread(r);
			// Saves the thread into the array 
			tr[i] = t;
			// Launches it
			t.start();
		}
		BigDecimal E = BigDecimal.ZERO;
		for(int i = 0; i < thread_count; i++){
			try {
				//Waiting for the thread to finish
				tr[i].join();
				// Add the result of the thread in E
				E = E.add(res[i]);
			} catch (InterruptedException ee) {
					
			}
		}
		if (!quiet) 
			System.out.println("e = " + E);
		
		//Write the result to a file
		Writer writer = null;
		try{
		
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(nameOffile), "utf-8"));
			 
			writer.write("" + E);
		} catch(IOException ex) {
			
			//ex.printStackTrace();
		
		} finally {
			try { 
					// close the file
				writer.close(); 
				} catch(Exception ex) {
						//ignore
				}
		}
		// Current time
		// aca end time
		long stop = System.currentTimeMillis();
		//Time for execution
		long diff = stop - start1;
		System.out.println("Total execution time for current run (millis): " + diff + " ms");
			
	}
}
