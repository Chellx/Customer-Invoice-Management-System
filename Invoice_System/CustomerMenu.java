package data;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTabbedPane;

public class CustomerMenu extends JFrame{
private JLabel logo;//used for background logo
private JPanel borderPanel;
private JPanel flowPanel;
//Used for JTabbedPane menu
private JTabbedPane tabs = new JTabbedPane();
private JLabel label1 = new JLabel();	
private JLabel label2 = new JLabel();
private JLabel label3 = new JLabel();
private JLabel label4 = new JLabel();
private JLabel label5 = new JLabel();
//Buttons for add,edit,delete,Query
private JButton add = new JButton("Add Customer");
private JButton delete = new JButton("Delete Customer");
private JButton edit = new JButton("Edit Customer");
private JButton query = new JButton("Query Customer");
	public CustomerMenu(){
		super("Customer Menu");
		//set tabs in frame
 		TabListener tabListener = new TabListener();
 		tabs.add("Customer Table Menu",label1);//make tab for customer table
 		tabs.add("Main Menu", label2);
		tabs.add("Invoice Table Menu",label3);
		tabs.add("Product Table Menu",label4);
		tabs.add("Order Table Menu",label5);
		tabs.addChangeListener(tabListener);
		UIManager.put("TabbedPane.contentAreaColor", new Color(188, 241, 219, 100));
		
		//get logo
		ImageIcon picture = new ImageIcon("C:\\Users\\shell\\Desktop\\Project-Repo\\Database\\src\\data\\DBLOGOGREEN.png");
		logo = new JLabel(picture);
		
		//Set buttons in flow layout
		ButtonListener listener = new ButtonListener();
		add.addActionListener(listener);
		delete.addActionListener(listener);
		edit.addActionListener(listener);
		query.addActionListener(listener);
		flowPanel = new JPanel();
		flowPanel.setLayout(new FlowLayout());
		flowPanel.add(add);
		flowPanel.add(delete);
		flowPanel.add(edit);
		flowPanel.add(query);
		flowPanel.setBackground(new Color(188, 241, 219)); //green
		
		//set tabs and grid panel in borderlayout
		borderPanel = new JPanel();
		borderPanel.setLayout(new BorderLayout());
		borderPanel.add(BorderLayout.NORTH,tabs);
		borderPanel.add(BorderLayout.CENTER,logo);
		borderPanel.add(BorderLayout.SOUTH,flowPanel);
		borderPanel.setBackground(new Color(188, 241, 219)); //green
		add(borderPanel);//add to jframe
		
		pack();//stops layout manager only with swing
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(400, 50);
		setSize(700, 600);
		setVisible(true);
	}
class TabListener implements ChangeListener{
	public void stateChanged(ChangeEvent e) {
		int pickTab = tabs.getSelectedIndex();//selects tab index
		if(pickTab == 1) {//if Main menu is selected
			//open new window
			MainMenu mainMenu = new MainMenu();
			mainMenu.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
			}
		else if(pickTab == 0) {//if customer table is selected
				
			}//end of if
		else if(pickTab == 2) {//if invoice table is selected
			//open new window
			InvoiceMenu invoiceMenu = new InvoiceMenu();
			invoiceMenu.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
			}
		else if(pickTab == 3) {//product table
			//open new window
			ProductMenu productMenu = new ProductMenu();
			productMenu.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
			}
		else if(pickTab == 4) {//order table
			//open new window
			OrderMenu orderMenu = new OrderMenu();
			orderMenu.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
		}
		}//end of stateChanged method	 
	 }//end of TabListener class
class ButtonListener implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == add) {
			//open new window
			AddCustomer addCustomer = new AddCustomer();
			addCustomer.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
		}
		else if(e.getSource() == delete) {
			DeleteCustomer deleteCustomer = new DeleteCustomer();
			deleteCustomer.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
		}
		else if(e.getSource() == edit) {
			//open new window
			EditCustomer editCustomer = new EditCustomer();
			editCustomer.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
		}
		else if(e.getSource() == query) {//move to customer Query screen
			//open new window
			CustomerQuery customerQuery = new CustomerQuery();
			customerQuery.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
		}
	}//end of action performed method
}//end of Button Listener class	
}//end of customer class
