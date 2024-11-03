# 💰 Expense Tracker Application

An intuitive JavaFX expense tracker application designed to make managing your finances easier. With support for tracking credits and debits, transaction history, and persistent file storage, this application is perfect for managing personal finances.

## 🌟 Features

- **💵 Balance Management**: Effortlessly track and manage your balance with options for crediting (increases balance) and debiting (decreases balance).
- **📜 Expense Records**: Record every expense and income with details like date, amount, type, balance, and remarks. All entries are saved in `expenses.txt` for long-term tracking.
- **💾 File-Based Storage**:
    - `balance.txt` stores your latest balance, updated automatically.
    - `expenses.txt` keeps a detailed log of all transactions for easy review.
- **📂 View Expenses**: Open `expenses.txt` to see all past transactions or view a summarized statement in-app.

## 📥 Installation

1. [Download and install Java SDK](https://www.oracle.com/in/java/technologies/downloads/) (Version 21 or higher).
2. [Download and extract JavaFX SDK](https://gluonhq.com/products/javafx/) (Version 21 or higher).
3. Clone the repository or download the `ExpenseTracker.java` file.
4. Compile and run the JavaFX application:

    ```bash
    javac --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml ExpenseTracker.java
    java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml ExpenseTracker
    ``` 

    > Replace `%PATH_TO_FX%` with the actual path to your JavaFX SDK, e.g., `"C:\Program Files\java\javafx-sdk-21.0.5\lib"`.

## 📝 How to Use

1. **Enter Amount**: Specify the amount you wish to credit or debit.
2. **Remarks**: Add an optional remark to categorize or describe the transaction.
3. **➕ Credit / ➖ Debit**: Click the appropriate button to adjust the balance and save the transaction.
4. **📊 View Statements**: Check your transaction history with the “View Statement” button or open `expenses.txt` for a detailed view.

## 📂 Project Structure

- `ExpenseTracker.java`: Main JavaFX application with UI and logic.
- `Account` class: Manages balance and saves it to `balance.txt` for persistence.
- `Transaction` class: Records each transaction in `expenses.txt` with full details.

## 🤝 Contributing

Contributions are welcome! If you have ideas for new features, bug fixes, or other improvements, feel free to submit a pull request. For larger changes, consider opening an issue first to discuss your ideas.

## 📄 License

This project is licensed under the [MIT License](LICENSE).

