import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.text.NumberFormat;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

class Account {
    private double balance;

    Account() {
        balance = 0;
    }

    Account(double amount) {
        balance = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void displayBalance() {
        System.out.printf("Current Balance = %.2f", balance);
    }

    public boolean credit(double amount) {
        balance += amount;
        return true;
    }

    public boolean debit(double amount) {
        if (amount > balance) {
            return false;
        } else {
            balance -= amount;
            return true;
        }
    }
}


class Transaction {
    private String type;
    private double amount;
    private String date;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.date = LocalDateTime.now().format(formatter);
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f on %s", type, amount, date);
    }
}


public class ExpenseTracker extends Application {
    Account ac = new Account();
    List<Transaction> transactions = new ArrayList<>();

    Label messageLabel = new Label("Message: NIL");
    Label balanceLabel = new Label("Balance = " + ac.getBalance());

    public void updateMessage(String message) {
        messageLabel.setText("Message: " + message);
    }

    public void updateBalance() {
        balanceLabel.setText("Balance = " + ac.getBalance());
    }

    @Override
    public void start(Stage st) {
        st.setTitle("Expense Tracker");
        Label amountLabel = new Label("Enter amount:");
        TextField amountTextField = new TextField();

        Button creditBtn = new Button("Credit");
        creditBtn.setOnAction(e -> handleCredit(amountTextField));

        Button debitBtn = new Button("Debit");
        debitBtn.setOnAction(e -> handleDebit(amountTextField));

        Button statementBtn = new Button("View Statement");

        GridPane gp1 = new GridPane();
        gp1.addRow(0, amountLabel, amountTextField, creditBtn, debitBtn);
        gp1.setHgap(10);

        GridPane gp2 = new GridPane();
        gp2.addRow(0, messageLabel);

        GridPane gp3 = new GridPane();
        gp3.addRow(0, balanceLabel);

        GridPane gp4 = new GridPane();
        gp4.addRow(0, statementBtn);

        VBox root1 = new VBox();
        root1.getChildren().addAll(gp1, gp2, gp3, gp4);
        root1.setSpacing(10);

        Scene sc1 = new Scene(root1, 500, 450);


        // Start of sc2

        Button mainPageBtn = new Button("Back to Main Page");
        //TODO statement table

        VBox root2 = new VBox();
        VBox statements = new VBox();
        statements.setSpacing(5);
        root2.getChildren().addAll(mainPageBtn, statements);

        Scene sc2 = new Scene(root2, 500, 450);


        statementBtn.setOnAction(e -> {
            updateStatementList(statements);
            switchScene(st, sc2);
        });
        mainPageBtn.setOnAction(e -> switchScene(st, sc1));


        st.setScene(sc1);
        st.show();
    }

    private void updateStatementList(VBox root){
        root.getChildren().clear();
        for(Transaction t: transactions){
            root.getChildren().add( new Label(t.toString()) );
        }
    }

    private void handleCredit(TextField amountTextField) {
        double enteredAmount;
    
        try {
            enteredAmount = Double.parseDouble(amountTextField.getText());
            if (enteredAmount < 0) {
                throw new NumberFormatException();
            }
        } catch (Exception e) {
            updateMessage("Invalid Amount");
            return;
        }
    
        ac.credit(enteredAmount);
        updateMessage(enteredAmount + " Credited Successfully");
        updateBalance();
        transactions.add(new Transaction("Credit", enteredAmount)); // Add transaction
        amountTextField.clear();
    }
    
    private void handleDebit(TextField amountTextField) {
        double enteredAmount;
    
        try {
            enteredAmount = Double.parseDouble(amountTextField.getText());
            if (enteredAmount < 0) {
                throw new NumberFormatException();
            }
        } catch (Exception e) {
            updateMessage("Invalid Amount");
            return;
        }
    
        if (ac.debit(enteredAmount)) {
            updateMessage(enteredAmount + " Debited Successfully");
            updateBalance();
            transactions.add(new Transaction("Debit", enteredAmount)); // Add transaction
            amountTextField.clear();
        } else {
            updateMessage("Insufficient Balance");
        }
    }    

    private void switchScene(Stage st, Scene sc){
        st.setScene(sc);
        st.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
