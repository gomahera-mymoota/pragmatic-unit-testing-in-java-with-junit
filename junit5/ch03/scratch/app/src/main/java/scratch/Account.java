package scratch;

public class Account {
    int balance;
    String name;

    Account(String name) {
        this.name = name;
    }

    void deposit(int dollars) {
        balance += dollars;
    }

    void withdraw(int dollars) {
        if (balance < dollars) {
            throw new InsufficientFundsException("balance only " + balance);
        }

        balance -= dollars;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public boolean hasPositiveBalance() {
        return balance > 0;
    }

}


class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String message) {
        super(message);
    }

    private static final long serialVersionUID = 1L;
}
