package com.wbd.log.starter.completablefuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * CompletableFuture  使用详解
 * @author zgh
 *CompletableFuture 提供了四个静态方法来创建一个异步操作。
 *public static CompletableFuture<Void> runAsync(Runnable runnable)
  public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)
  public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
  public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
  
  没有指定Executor的方法会使用ForkJoinPool.commonPool() 作为它的线程池执行异步代码。如果指定线程池，则使用指定的线程池运行。以下所有的方法都类同。

runAsync方法不支持返回值。
supplyAsync可以支持返回值。
 */
public class CompletableFutureTest {

	//没有返回值
	
	public static void runAsync() throws InterruptedException {
		
		CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(()->{
			System.out.println("abc...");
			//TimeUnit.SECONDS.sleep(1);
			
		
		});
		
		//completableFuture.get();
		System.out.println("run end....");
	}
	
	
	//有返回值
	public static void supplyAsync() {
		
		CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(()->{
			
			System.out.println("run end ===");
			return System.currentTimeMillis();
		});
		
		try {
			long time = completableFuture.get();
			System.out.println("time="+time);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 
	 * 2、计算结果完成时的回调方法
当CompletableFuture的计算结果完成，或者抛出异常的时候，可以执行特定的Action。主要是下面的方法：
public CompletableFuture<T> whenComplete(BiConsumer<? super T,? super Throwable> action)
public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T,? super Throwable> action)
public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T,? super Throwable> action, Executor executor)
public CompletableFuture<T> exceptionally(Function<Throwable,? extends T> fn)
	 * 
	 * whenComplete 和 whenCompleteAsync 的区别：
whenComplete：是执行当前任务的线程执行继续执行 whenComplete 的任务。
whenCompleteAsync：是执行把 whenCompleteAsync 这个任务继续提交给线程池来进行执行


	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	
	
	
	public static void whenComplate() throws InterruptedException {
		CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(()->{
			
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(new Random().nextInt()%2>=0) {
				int i=12/12;
			}
			
			System.out.println("run end....");
		});
		
		completableFuture.whenComplete(new BiConsumer<Void, Throwable>() {

			@Override
			public void accept(Void t, Throwable u) {
				System.out.println(t);
				System.out.println("执行完成成功");
			}
			
		});
		
		completableFuture.exceptionally(new Function<Throwable, Void>() {

			@Override
			public Void apply(Throwable t) {
			System.out.println("执行失败："+t.getMessage());
				return null;
			}
		});
		
		TimeUnit.SECONDS.sleep(2);
	}
	
	
	/**
	 * 3、 thenApply 方法
当一个线程依赖另一个线程时，可以使用 thenApply 方法来把这两个线程串行化。
public <U> CompletableFuture<U> thenApply(Function<? super T,? extends U> fn)
public <U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn)
public <U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn, Exec
。
	 * 
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	
	public static void main(String[] args) throws InterruptedException {
		//supplyAsync();
		//runAsync();
		whenComplate();
	}

}
