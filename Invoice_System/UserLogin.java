package data;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * 
 * @author Michelle Bolger
 *
 */
public class UserLogin extends JFrame {
private JLabel header,userName,password;
private JTextField textField,passwordField;
private JButton submit;
/**
 * Default Constructor	
 */
public UserLogin() {
		super("User Login");
		
		ButtonListener listener = new ButtonListener();
		header = new JLabel("Login",SwingConstants.CENTER);
		header.setFont(new Font("",Font.BOLD, 50));//add(header);
		
		userName = new JLabel("Username");
		userName.setFont(new Font("",Font.PLAIN, 20));

		
		textField = new JTextField("Root");
		textField.setFont(new Font("",Font.PLAIN, 20));
		textField.setEditable(false);
		
		
		password = new JLabel("Password");
		password.setFont(new Font("",Font.PLAIN, 20));
		
		
		passwordField = new JPasswordField();
		
		submit = new JButton("LOGIN");
		submit.setFont(new Font("",Font.BOLD, 20));
		submit.addActionListener(listener);
		
		///Setting layout
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout( new GridLayout(2,2,20,20));// set grid layout to panel
		gridPanel.add(userName);
		gridPanel.add(textField);
		gridPanel.add(password);
		gridPanel.add(passwordField);
		//add(gridPanel);
		
		JPanel borderLayout = new JPanel();
		BorderLayout border = new BorderLayout(150,50);
		borderLayout.setLayout(border);// set border layout to panel
		borderLayout.add(BorderLayout.NORTH,header);
		borderLayout.add(BorderLayout.CENTER,gridPanel);
		borderLayout.add(BorderLayout.SOUTH,submit);
		
		borderLayout.setBackground(new Color(153, 204, 255));
		gridPanel.setBackground(new Color(153, 204, 255));
 
		JPanel lastPanel = new JPanel(new FlowLayout());
		lastPanel.add(borderLayout);
		add(lastPanel);
		lastPanel.setBackground(new Color(153, 204, 255)); //light blue
		
		setLocation(480, 150);
		pack();//stops layout manager only with swing
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String rightPassword = "";
			if(e.getSource() == submit) {
				rightPassword = passwordField.getText();
				
				if(rightPassword.equals("123danny") || rightPassword.equals("dbpass")) {
					MainMenu menu = new MainMenu();
					menu.setVisible(true);
					setVisible(false);
					dispose();
				}
			else {
				passwordField.setText("");
				JOptionPane.showMessageDialog(null, "Invalid Password", "Alert", JOptionPane.ERROR_MESSAGE);
				}
			}//end of source if
		}// end of action performed	
	}//end of inner class 
}//end of outer class
