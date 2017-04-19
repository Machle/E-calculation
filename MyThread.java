import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MyThread extends Thread {
		
		static long start1 = System.currentTimeMillis();
		int el_count;
		BigDecimal r[];
		int int_size;
		int num_thrd;
		boolean quiet;
		int thread_count;
		int scale;
		
		
		public MyThread(int el_count, BigDecimal r[], int int_size, int num_thrd, int thread_count, int scale,
																							boolean quiet) {
				this.el_count=el_count;
				this.r=r;
				this.quiet=quiet;
				this.int_size=int_size;
				this.num_thrd=num_thrd;
				this.thread_count=thread_count;
				this.scale=scale;
		}
			
		public void run() {
			long start1 = System.currentTimeMillis();
			if (!quiet) 
		       System.out.println("Thread <"+ num_thrd + "> is starting!");
			int start;
			int end;
			BigDecimal prod; // prod_rev;
			if (num_thrd == (thread_count - 1)) {
			   		start= int_size*num_thrd;
			   		end=el_count;
			} else {
			   	start = num_thrd * int_size;
			   	end = (num_thrd+1) * int_size - 1;
			}
			   		 
			if(num_thrd==0)
			   	r[num_thrd] =new BigDecimal(3);
			else 
			   	r[num_thrd]=BigDecimal.ZERO;
			if(start==0){
				prod=new BigDecimal(1);
				start=start+1;
			}else 
				prod=factorial(2*start+1);
			
		    for (int i = start; i <= end; i++) {
		    	prod = prod.multiply(new BigDecimal((2*i)*(2*i+1)));
		            	
		    	r[num_thrd] = r[num_thrd].add(BigDecimal.valueOf(3 - 4*Math.pow(i, 2))
		                     .divide(prod, scale, RoundingMode.CEILING));
		         
		    }
		    long stop = System.currentTimeMillis();
		    if (!quiet) 
		    	System.out.println("Thread <"+ num_thrd + "> stopped.\n"+ "The execution time was: " +
		                													(stop - start1) + " ms");
			}
		BigDecimal factorial(int x){
			BigDecimal pr = new BigDecimal("1");
		    for (int i = x; i > 1; i--){
		        pr = pr.multiply(new BigDecimal(i));
		    }
		        return pr;
		}
	
}
