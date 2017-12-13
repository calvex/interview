package org.aicool.study.interview.qianmi;

import java.util.Map;
import java.util.concurrent.*;

public class MessageDemo {

    private static BlockingQueue<Message> queue = new LinkedBlockingQueue<>(100);

    public static void main(String[] args) throws InterruptedException {

        TypeOneHandle typeOneHandle = new TypeOneHandle();
        TypeTwoHandle typeTwoHandle = new TypeTwoHandle();

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Consumer consumer = new Consumer();
        Consumer consumer2 = new Consumer();

        consumer.addHandle(typeOneHandle);
        consumer.addHandle(typeTwoHandle);

        executorService.execute(new Producer());
        executorService.execute(consumer);
        executorService.execute(consumer2);
        Thread.sleep(10);
        consumer.stop();
        consumer2.stop();
        executorService.shutdown();
    }


    static class Producer implements Runnable {

        @Override
        public void run() {

            FileUtils.readStream().skip(1).forEach(x -> {
                String[] split = x.split(",");
                Message message = new Message(split[0], split[1], split[2], split[3]);
                try {
                    queue.put(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        }
    }


    interface Handle {

        String getKey();

        void handleMessage(Message message);
    }

    static class TypeOneHandle implements Handle {

        @Override
        public String getKey() {
            return "type1";
        }

        @Override
        public void handleMessage(Message message) {
            System.out.println(Thread.currentThread().getName() + "---type--One--Handle---" + message);
        }
    }

    static class TypeTwoHandle implements Handle {

        @Override
        public String getKey() {
            return "type2";
        }

        @Override
        public void handleMessage(Message message) {

            System.out.println(Thread.currentThread().getName() + "---type--Two--Handle---" + message);
        }
    }


    static class Consumer implements Runnable {


        private static Map<String, Handle> handleMap = new ConcurrentHashMap<>();

        private  boolean stop = false;

        public void addHandle(Handle handle) {
            handleMap.put(handle.getKey(), handle);
        }

        public void stop() {
            stop = true;
        }


        @Override
        public void run() {
            try {
                for (; ; ) {
                    if (stop && queue.size() == 0) break;
                    Message takeMessage = queue.poll(10, TimeUnit.SECONDS);
                    if (takeMessage == null) continue;
                    Handle handle = handleMap.get(takeMessage.getType());
                    if (handle != null)
                        handle.handleMessage(takeMessage);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
