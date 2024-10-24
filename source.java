import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LibraryManagementSystem extends Frame {
    TextField titleField, authorField, yearField;
    TextField memberNameField, memberEmailField;
    TextField borrowDateField, returnDateField;

    Label statusLabel;

    public LibraryManagementSystem() {
        setLayout(new FlowLayout());

        // Add Book Section
        add(new Label("Book Title:"));
        titleField = new TextField(20);
        add(titleField);

        add(new Label("Author:"));
        authorField = new TextField(20);
        add(authorField);

        add(new Label("Year:"));
        yearField = new TextField(4);
        add(yearField);

        Button addBookButton = new Button("Add Book");
        addBookButton.addActionListener(new AddBookHandler());
        add(addBookButton);

        // Confirm Add Book Section
        Button confirmButton = new Button("OK");
        confirmButton.addActionListener(new ConfirmHandler());
        add(confirmButton);

        // Close Button
        Button closeButton = new Button("Close");
        closeButton.addActionListener(new CloseHandler());
        add(closeButton);

        // Status Label
        statusLabel = new Label();
        add(statusLabel);

        // Add Member Section
        add(new Label("Member Name:"));
        memberNameField = new TextField(20);
        add(memberNameField);

        add(new Label("Member Email:"));
        memberEmailField = new TextField(20);
        add(memberEmailField);

        Button addMemberButton = new Button("Add Member");
        addMemberButton.addActionListener(new AddMemberHandler());
        add(addMemberButton);

        // Borrow Book Section
        add(new Label("Borrow Date (YYYY-MM-DD):"));
        borrowDateField = new TextField(10);
        add(borrowDateField);

        add(new Label("Return Date (YYYY-MM-DD):"));
        returnDateField = new TextField(10);
        add(returnDateField);

        Button borrowBookButton = new Button("Borrow Book");
        borrowBookButton.addActionListener(new BorrowBookHandler());
        add(borrowBookButton);

        setTitle("Library Management System");
        setSize(400, 400);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });
        
    }

    class AddBookHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String title = titleField.getText();
            String author = authorField.getText();
            int year = Integer.parseInt(yearField.getText());

            try {
                Connection connection = DatabaseConnection.getConnection();
                String query = "INSERT INTO books (title, author, year) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, title);
                statement.setString(2, author);
                statement.setInt(3, year);
                statement.executeUpdate();
                statusLabel.setText("Book added successfully.");
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                statusLabel.setText("Error adding book.");
            }
        }
    }

    class ConfirmHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            statusLabel.setText("Book entry confirmed.");
            titleField.setText("");
            authorField.setText("");
            yearField.setText("");
        }
    }

    class CloseHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    class AddMemberHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = memberNameField.getText();
            String email = memberEmailField.getText();

            try {
                Connection connection = DatabaseConnection.getConnection();
                String query = "INSERT INTO members (name, email) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, name);
                statement.setString(2, email);
                statement.executeUpdate();
                statusLabel.setText("Member added successfully.");
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                statusLabel.setText("Error adding member.");
            }
        }
    }

    class BorrowBookHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int bookId = 1; // You need to retrieve these IDs based on your UI or another source
            int memberId = 1; // You need to retrieve these IDs based on your UI or another source
            Date borrowDate = Date.valueOf(borrowDateField.getText());
            Date returnDate = Date.valueOf(returnDateField.getText());

            try {
                Connection connection = DatabaseConnection.getConnection();
                String query = "INSERT INTO borrowings (book_id, member_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, bookId);
                statement.setInt(2, memberId);
                statement.setDate(3, borrowDate);
                statement.setDate(4, returnDate);
                statement.executeUpdate();
                statusLabel.setText("Book borrowed successfully.");
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                statusLabel.setText("Error borrowing book.");
            }
        }
    }

    public static void main(String[] args) {
        new LibraryManagementSystem();
     }
}

