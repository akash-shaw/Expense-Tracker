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
import javafx.geometry.Insets;
import java.util.Collections;
import javafx.scene.control.ScrollPane;

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
    private String remarks;
    private double currentBalance;

    public Transaction(String type, double amount, String remarks, double currentBalance) {
        this.type = type;
        this.amount = amount;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.date = LocalDateTime.now().format(formatter);
        this.remarks = remarks;
        this.currentBalance = currentBalance;
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

    public String getRemarks(){
        return remarks;
    }

    public String getStatementString() {
        return String.format("%s: %.2f\t on %s,\tBalance: %.2f,\tRemarks: %s", type, amount, date, currentBalance, remarks);
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

        Label headingSc1 = new Label("Expense Manager");
        headingSc1.setStyle("-fx-font-weight: bold; -fx-font-size: 24px;");

        Label amountLabel = new Label("Enter amount:");
        TextField amountTextField = new TextField();

        Label remarksLabel = new Label("Remarks:");
        TextField remarksTextField = new TextField();

        Button creditBtn = new Button("Credit");
        creditBtn.setOnAction(e -> handleCredit(amountTextField, remarksTextField));

        Button debitBtn = new Button("Debit");
        debitBtn.setOnAction(e -> handleDebit(amountTextField, remarksTextField));

        Button statementBtn = new Button("View Statement");

        GridPane gp1 = new GridPane();
        gp1.addRow(0, amountLabel, amountTextField);
        gp1.addRow(1, remarksLabel, remarksTextField);
        gp1.setHgap(10);
        gp1.setVgap(10);

        GridPane gp2 = new GridPane();
        gp2.addRow(0, creditBtn, debitBtn);
        gp2.setHgap(10);

        GridPane gp3 = new GridPane();
        gp3.addRow(0, messageLabel);

        GridPane gp4 = new GridPane();
        gp4.addRow(0, balanceLabel);

        GridPane gp5 = new GridPane();
        gp5.addRow(0, statementBtn);

        VBox root1 = new VBox();
        root1.getChildren().addAll(headingSc1, gp1, gp2, gp3, gp4, gp5);
        root1.setSpacing(10);
        root1.setPadding(new Insets(20));

        Scene sc1 = new Scene(root1, 500, 450);


        // Start of sc2
        Label headingSc2 = new Label("Your Expenses");
        headingSc2.setStyle("-fx-font-weight: bold; -fx-font-size: 24px;");

        Button mainPageBtn = new Button("Back to Main Page");

        VBox root2 = new VBox();
        root2.setSpacing(10);
        VBox statements = new VBox();
        statements.setSpacing(5);
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(statements);
        root2.getChildren().addAll(headingSc2, mainPageBtn, scroll);
        root2.setPadding(new Insets(20));

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
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            root.getChildren().add(new Label(t.getStatementString()));
        }
    }

    private void handleCredit(TextField amountTextField, TextField remarksTextField) {
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
        String remarks = remarksTextField.getText().trim().isEmpty() ? "Nil" : remarksTextField.getText();
        transactions.add(new Transaction("Credit", enteredAmount, remarks, ac.getBalance()));
        // amountTextField.clear();
    }
    
    private void handleDebit(TextField amountTextField, TextField remarksTextField) {
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
            String remarks = remarksTextField.getText().trim().isEmpty() ? "Nil" : remarksTextField.getText();
            transactions.add(new Transaction("Debit", enteredAmount, remarks, ac.getBalance()));
            // amountTextField.clear();
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
