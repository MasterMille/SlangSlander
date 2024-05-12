package com.shitflix.views;

import com.shitflix.App;
import com.shitflix.controllers.LoginController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private final LoginController controller;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel errorTextLabel = new JLabel();

    public void resetForm() {
        emailField.setText("");
        passwordField.setText("");
        errorTextLabel.setText("");
    }

    public LoginPanel(LoginController controller) {
        this.controller = controller;

        // Font
        Font font = new Font("Verdana", Font.ITALIC, 12);
        errorTextLabel.setForeground(Color.RED);


        // Panels
        JPanel loginPanel = new JPanel(new BorderLayout());
        JPanel registerPanel = new JPanel(new BorderLayout());


        // Login labels and fields
        JLabel emailText = new JLabel("Email");
        emailField = new JTextField(20);
        JLabel passwordText = new JLabel("Password");
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");


        // Register page button
        JButton registerButton = new JButton("Register");


        // Action listener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                boolean isAuthenticated = controller.login(email, password);
                if (isAuthenticated) {
                    App.show("home");
                    resetForm();
                } else {
                    errorTextLabel.setText("Invalid Email Or Password");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.show("register");
                resetForm();
            }
        });


        // Window layout manager
        this.setLayout(new GridBagLayout());


        // Constraints login panel
        GridBagConstraints loginConstraints = new GridBagConstraints();
        loginConstraints.gridx = 0;
        loginConstraints.gridy = 0;
        loginConstraints.gridheight = 2;
        loginConstraints.fill = GridBagConstraints.VERTICAL;

        loginPanel.setLayout(new GridLayout(3, 2));
        TitledBorder loginBorder = new TitledBorder("Login");
        loginBorder.setTitleFont(font);
        loginPanel.setBorder(loginBorder);

        loginPanel.add(emailText);
        loginPanel.add(emailField);
        loginPanel.add(passwordText);
        loginPanel.add(passwordField);
        loginPanel.add(errorTextLabel); // Empty label for spacing
        loginPanel.add(loginButton);

        // Constraints register panel
        GridBagConstraints registerConstraints = new GridBagConstraints();
        registerConstraints.gridx = 0;
        registerConstraints.gridy = 2;
        registerConstraints.gridheight = 1;
        registerConstraints.fill = GridBagConstraints.VERTICAL;

        TitledBorder registerBorder = new TitledBorder("Register");
        registerBorder.setTitleFont(font);
        registerPanel.setBorder(registerBorder);

        registerPanel.add(registerButton);

        // Window
        add(loginPanel, loginConstraints);
        add(registerPanel, registerConstraints);

    }
}
