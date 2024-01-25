import java.util.ArrayList;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class User {
    private String userId;
    private String userPin;
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public User(String userId, String userPin, double initialBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean authenticate(String enteredPin) {
        return userPin.equals(enteredPin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void transfer(User recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("Transfer", amount));
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History for User " + userId + ":");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction.getType() + ": " + transaction.getAmount());
        }
    }
}

class ATM {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        User user = initializeUser();
        if (user != null) {
            performATMOperations(user);
        }
    }

    private static User initializeUser() {
        System.out.println("Enter User ID:");
        String userId = scanner.next();
        System.out.println("Enter User PIN:");
        String userPin = scanner.next();

        // Dummy user initialization, replace with actual authentication logic
        User user = new User(userId, userPin, 1000.0);

        if (user.authenticate(userPin)) {
            System.out.println("Authentication successful. Welcome, " + userId + "!");
            return user;
        } else {
            System.out.println("Authentication failed. Exiting.");
            return null;
        }
    }

    private static void performATMOperations(User user) {
        int choice;
        do {
            System.out.println("\nATM Operations:");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            System.out.println("Enter your choice:");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    user.displayTransactionHistory();
                    break;
                case 2:
                    System.out.println("Enter withdrawal amount:");
                    double withdrawAmount = scanner.nextDouble();
                    user.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.println("Enter deposit amount:");
                    double depositAmount = scanner.nextDouble();
                    user.deposit(depositAmount);
                    break;
                case 4:
                    System.out.println("Enter recipient's User ID:");
                    String recipientId = scanner.next();
                    System.out.println("Enter transfer amount:");
                    double transferAmount = scanner.nextDouble();
                    // Replace with actual logic to find recipient user
                    User recipient = new User(recipientId, "", 0.0);
                    user.transfer(recipient, transferAmount);
                    break;
                case 5:
                    System.out.println("Exiting ATM. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }
}