package threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SimpleThreadPool {
    private final BlockingQueue<Runnable> queue;
    private final WorkManager[] pool;
    private boolean isActive = true;
    private boolean isJoined = false;

    public SimpleThreadPool(int nThreads) {
        pool = new WorkManager[nThreads];
        queue = new LinkedBlockingQueue<>();

        for (int i = 0; i < pool.length; i++) {
            pool[i] = new WorkManager();
            pool[i].start();
        }

    }

    public void joinAll() {
        isJoined = true;
        for (int i = 0; i < pool.length; i++) {
            try {
                pool[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void startWork(Runnable worker) {
        if (isActive) {
            queue.offer(worker);
        }
    }

    public void shutdown() {
        isActive = false;
    }

    private class WorkManager extends Thread {

        @Override
        public void run() {
            while (isActive) {
                Runnable worker = queue.poll();
                if (worker == null) {
                    if (isJoined) return;
                } else {
                    worker.run();
                }
            }
        }
    }
}