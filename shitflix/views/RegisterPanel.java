package com.shitflix.views;

import com.shitflix.App;
import com.shitflix.controllers.RegisterController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPanel extends JPanel {
    private final RegisterController controller;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JLabel errorTextLabel = new JLabel();

    public void resetForm() {
        emailField.setText("");
        passwordField.setText("");
        usernameField.setText("");
        errorTextLabel.setText("");
    }

    public RegisterPanel(RegisterController controller) {
        this.controller = controller;

        // Font
        Font font = new Font("Verdana", Font.ITALIC, 12);
        errorTextLabel.setForeground(Color.RED);


        // Panels
        JPanel registerPanel = new JPanel(new BorderLayout());
        JPanel loginPanel = new JPanel(new BorderLayout());


        // Register labels and fields
        JLabel emailText = new JLabel("Email");
        emailField = new JTextField(20);
        JLabel passwordText = new JLabel("Password");
        passwordField = new JPasswordField(20);
        JLabel usernameText = new JLabel("Username");
        usernameField = new JTextField(20);
        JButton registerButton = new JButton("Create Account");


        // login page button
        JButton loginButton = new JButton("Login");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String username = usernameField.getText();
                boolean isSuccessful = controller.register(email, password, username);
                if (isSuccessful) {
                    App.show("home");
                    resetForm();
                } else {
                    errorTextLabel.setText("Invalid entry");
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.show("login");
                resetForm();
            }
        });

        // Window layout manager
        setLayout(new GridBagLayout());


        // Constraints register panel
        GridBagConstraints registerConstraints = new GridBagConstraints();
        registerConstraints.gridx = 0;
        registerConstraints.gridy = 0;
        registerConstraints.gridheight = 2;
        registerConstraints.fill = GridBagConstraints.VERTICAL;

        registerPanel.setLayout(new GridLayout(4, 2));
        TitledBorder registerBorder = new TitledBorder("Register");
        registerBorder.setTitleFont(font);
        registerPanel.setBorder(registerBorder);

        registerPanel.add(emailText);
        registerPanel.add(emailField);
        registerPanel.add(usernameText);
        registerPanel.add(usernameField);
        registerPanel.add(passwordText);
        registerPanel.add(passwordField);
        registerPanel.add(errorTextLabel);
        registerPanel.add(registerButton);

        // Constraints login panel
        GridBagConstraints loginConstraints = new GridBagConstraints();
        loginConstraints.gridx = 0;
        loginConstraints.gridy = 2;
        loginConstraints.gridheight = 1;
        loginConstraints.fill = GridBagConstraints.VERTICAL;

        TitledBorder loginBorder = new TitledBorder("Login");
        loginBorder.setTitleFont(font);
        loginPanel.setBorder(loginBorder);

        loginPanel.add(loginButton);

        // Window
        add(registerPanel, registerConstraints);
        add(loginPanel, loginConstraints);

    }
}
