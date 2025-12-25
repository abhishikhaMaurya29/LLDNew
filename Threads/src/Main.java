//// Shared resource: CoffeeMachine acts as a buffer of size 1
//class CoffeeMachine {
//    // Flag to indicate buffer status: true → coffee ready, false → cup empty
//    private boolean isCoffeeReady = false;
//
//    // Method to be run by the producer thread
//    public synchronized void makeCoffee() throws InterruptedException {
//        // If coffee is already ready, producer must wait (buffer is full)
//        while (isCoffeeReady) {
//            // Releases lock and waits until notified by consumer
//            wait();
//        }
//
//        // Simulate coffee preparation
//        System.out.println("Brewing coffee…");
//        Thread.sleep(1000); // Simulate time to make coffee
//
//        // Set the buffer to full (coffee is now ready)
//        isCoffeeReady = true;
//        System.out.println("Coffee ready!");
//
//        // Notify the consumer thread that coffee is available
//        notify();
//    }
//
//    // Method to be run by the consumer thread
//    public synchronized void drinkCoffee() throws InterruptedException {
//        // If no coffee is available, consumer must wait (buffer is empty)
//        while (!isCoffeeReady) {
//            // Releases lock and waits until notified by producer
//            wait();
//        }
//
//        // Simulate coffee consumption
//        System.out.println("Drinking coffee…");
//        Thread.sleep(1000); // Simulate time to drink coffee
//
//        // Set the buffer to empty (coffee has been consumed)
//        isCoffeeReady = false;
//        System.out.println("Cup emptied - brew next!");
//
//        // Notify the producer thread that it can brew again
//        notify();
//    }
//}
//
//// Driver class
//class ProducerConsumerProblem {
//    public static void main(String[] args) {
//        // Shared CoffeeMachine object acts as the synchronized monitor
//        CoffeeMachine machine = new CoffeeMachine();
//
//        // Producer thread that continuously makes coffee
//        Thread producer = new Thread(() -> {
////            while (true) {
//            try {
//                machine.makeCoffee(); // Try to produce coffee
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
////            }
//        });
//
//        // Consumer thread that continuously drinks coffee
//        Thread consumer = new Thread(() -> {
////            while (true) {
//            try {
//                machine.drinkCoffee(); // Try to consume coffee
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
////            }
//        });
//
//        // Start both threads
//        producer.start();
//        consumer.start();
//    }
//}

// ──────────────────────────────────────────────────────────────
// A simple mutable account object guarded by its intrinsic lock
// ──────────────────────────────────────────────────────────────
class BankAccount {

    // Name helps us identify the account in logs
    private final String name;

    // Shared mutable state that needs protection
    private int balance;

    // Constructor – sets initial state
    public BankAccount(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

    // Read-only helper used only for logging
    public String getName() {
        return name;
    }

    // Deposit is a critical section – guard with the object’s lock
    public synchronized void deposit(int amount) {
        balance += amount;
    }

    // Withdraw is also a critical section – same lock
    public synchronized void withdraw(int amount) {
        balance -= amount;
    }

    // Another read-only helper for debugging
    public int getBalance() {
        return balance;
    }
}

// ──────────────────────────────────────────────────────────────
// Runnable that transfers money from one account to another
// ──────────────────────────────────────────────────────────────
class TransferTask implements Runnable {

    private final BankAccount from;
    private final BankAccount to;
    private final int amount;

    // Capture the two accounts and transfer amount
    public TransferTask(BankAccount from, BankAccount to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public void run() {

        // First lock: ‘from’ account – thread now owns its monitor
        synchronized (from) {
            System.out.println(Thread.currentThread().getName() +
                    " locked " + from.getName());

            // Artificial delay widens timing window
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ignored) {
//            }

            // Second lock: ‘to’ account – may block if another thread owns it
            if (to.getName().compareTo(from.getName()) > 0) {

            }
            synchronized (to) {
                System.out.println(Thread.currentThread().getName() +
                        " locked " + to.getName());

                // Critical update – atomic with respect to both locks
                from.withdraw(amount);
                to.deposit(amount);
                System.out.println("Transferred " + amount + " from " +
                        from.getName() + " to " + to.getName());
            }
        }
    }
}

// ──────────────────────────────────────────────────────────────
// Main class kept separate as per user preference
// ──────────────────────────────────────────────────────────────
class DeadlockDemoMain {

    public static void main(String[] args) throws Exception {

        // Create two independent accounts
        BankAccount accountA = new BankAccount("Account-A", 1000);
        BankAccount accountB = new BankAccount("Account-B", 1000);

        // Thread T1 transfers A → B
        Thread t1 = new Thread(new TransferTask(accountA, accountB, 100), "T1");

        // Thread T2 transfers B → A (reverse order)
        Thread t2 = new Thread(new TransferTask(accountB, accountA, 200), "T2");

        // Kick off both threads
        t1.start();
        t2.start();

        // Waiting for both the threads to complete executions
        t1.join();
        t2.join();

        /* This line never gets executed because
        both the threads were stuck in deadlock */
        System.out.println("Both threads finished execution.");
    }
}