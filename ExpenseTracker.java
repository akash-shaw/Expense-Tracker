import java.text.NumberFormat;
import java.util.EventListener;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;

class Account{
    private double balance;

    Account(){
        balance = 0;
    }

    Account(double amount){
        balance = amount;
    }

    public double getBalance(){
        return balance;
    }

    public void displayBalance(){
        System.out.printf("Current Balance = %.2f",balance);
    }

    public boolean credit(double amount){
        balance+=amount;
        return true;
    }

    public boolean debit(double amount){
        if(amount>balance){
            return false;
        }
        else{
            balance-=amount;
            return true;
        }
    }
}

public class ExpenseTracker extends Application{
    Account ac = new Account();
    Label operationStatus = new Label();
    Label balaceView = new Label( "Balance = " + ac.getBalance() );

    public void updateStatus(String message){
        operationStatus.setText("Status: "+message);
    }
    public void updateBalace(){
        balaceView.setText( "Balance = " + ac.getBalance() );
    }

    public void start(Stage st){
        st.setTitle("Expense Tracker");
        Label amountLabel = new Label("Enter amount:");
        TextField amountTextField = new TextField();

        Button creditBtn = new Button("Credit");
        creditBtn.setOnAction(new EventHandler<ActionEvent>() {  
        
            @Override  
            public void handle(ActionEvent arg0) {  

                double enteredAmount;

                try {
                    enteredAmount = Double.parseDouble(amountTextField.getText());
                    if(enteredAmount<0){
                        throw new NumberFormatException();
                    }
                } catch (Exception e) {
                    updateStatus("Invalid Amount");
                    return;
                }
                ac.credit(enteredAmount);
                updateStatus(enteredAmount+" Credited Succesfully");
                updateBalace();
            }
        });

        Button debitBtn = new Button("Debit");
        debitBtn.setOnAction(new EventHandler<ActionEvent>() {  
        
            @Override  
            public void handle(ActionEvent arg0) {  
 
                double enteredAmount;

                try {
                    enteredAmount = Double.parseDouble(amountTextField.getText());
                    if(enteredAmount<0){
                        throw new NumberFormatException();
                    }
                } catch (Exception e) {
                    updateStatus("Invalid Amount");
                    return;
                }
                if(ac.debit(enteredAmount)){
                    updateStatus(enteredAmount+" Debited Succesfully");
                    updateBalace();
                }
                else{
                    updateStatus("Insufficient Balance");
                }
            }
        });

        GridPane root = new GridPane();
        root.addRow(0,amountLabel, amountTextField);
        root.addRow(1,creditBtn,debitBtn);
        root.addRow(2, operationStatus);
        root.addRow(3, balaceView);
        //root.addRow(1,pw,tf2);
        Scene sc = new Scene(root, 500, 450);
        st.setScene(sc);
        st.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}