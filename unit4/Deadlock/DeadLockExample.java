package Deadlock;

public class DeadLockExample {
    // Create two lock objects to synchronize on

    private static final Object lock1 = new Object();

    private static final Object lock2 = new Object();

    public static void main(String[] args) {

        // Thread 1 tries to acquire lock1 first, then lock2

        Thread thread1 = new Thread(() -> {

            System.out.println("Thread 1: Trying to acquire lock1");

            synchronized (lock1) {

                System.out.println("Thread 1: Acquired lock1");

                // Sleep to simulate work and give time for Thread 2 to acquire lock2

                try {

                    Thread.sleep(100);

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();

                }

                System.out.println("Thread 1: Waiting to acquire lock2");

                // Attempt to acquire lock2 (this will cause deadlock if Thread 2 holds it)

                synchronized (lock2) {

                    System.out.println("Thread 1: Acquired lock2");

                }

            }

        });

        // Thread 2 tries to acquire lock2 first, then lock1

        Thread thread2 = new Thread(() -> {

            System.out.println("Thread 2: Trying to acquire lock2");

            synchronized (lock2) {

                System.out.println("Thread 2: Acquired lock2");

                // Sleep to simulate work and give time for Thread 1 to acquire lock1

                try {

                    Thread.sleep(100);

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();

                }

                System.out.println("Thread 2: Waiting to acquire lock1");

                // Attempt to acquire lock1 (this will cause deadlock if Thread 1 holds it)

                synchronized (lock1) {

                    System.out.println("Thread 2: Acquired lock1");

                }

            }

        });

        // Start both threads

        thread1.start();

        thread2.start();

    }

}