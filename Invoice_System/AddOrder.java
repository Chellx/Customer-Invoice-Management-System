package data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
/**
 * 
 * @author Michelle Bolger
 *
 */
public class AddOrder extends JFrame{
private JLabel orderLabel,shipLabel,payLabel,priceLabel,customerLabel;	
private JPanel gridPanel,outerPanel,innerPanel;
private JTextField orderField,shipField,payField,priceField;
private JButton submit;
//Panels 
private OrderTable orderTable;
private JPanel outerBorderPanel;
private JPanel flowPanel;
private JPanel innerBorderPanel;
//Used for JTabbedPane menu
private JTabbedPane tabs = new JTabbedPane();
private JLabel label1 = new JLabel();	
private JLabel label2 = new JLabel();
private JLabel label3 = new JLabel();
private JLabel label4 = new JLabel();
private JLabel label5 = new JLabel();
//Buttons for add,edit,delete,Query
private JButton add = new JButton("Add Order");
private JButton delete = new JButton("Delete Order");
private JButton edit = new JButton("Edit Order");
private JButton query = new JButton("Query Order");
static final String QUERY = "SELECT * FROM invoicedatabase.order";
//Jcombo box
private JComboBox comboBox;
private String[] ids = new String[30];
private String customerId ="";
/**
 * Default Constructor	
 */
public AddOrder() {
		super("Add Order");
		try {	
			//Labels
			orderLabel = new JLabel("Order Date (YYYY/MM/DD) ");
			shipLabel = new JLabel("Ship Date (YYYY/MM/DD) ");
			payLabel = new JLabel("Payment Received (Y/N)");
			priceLabel = new JLabel("Price");
			customerLabel = new JLabel("Select Customer ID");
			//JTextFields
			orderField = new JTextField("");
			shipField = new JTextField("");
			payField = new JTextField("");
			payField.setToolTipText("Payment Recieved From Customer (YES/NO)");
			priceField = new JTextField("");
			priceField.setToolTipText("Cost Of Order");
			submit = new JButton("Submit");
			orderField.setColumns(10);
			orderTable = new OrderTable();
			orderTable.setQuery(QUERY);
			//make comboBox
			int size = orderTable.getCustomerId(ids,0);
			String[] filteredIds = new String[size];
			for (int i = 0; i < size; i++) {
				filteredIds[i] = ids[i];
			}
			comboBox = new JComboBox(filteredIds);
			ComboListener combo = new ComboListener();
			comboBox.addActionListener(combo);
			//add components into 2*8 grid
			gridPanel = new JPanel(new GridLayout(5,2,20,15));
			gridPanel.setBackground(new Color(200, 191, 231)); //purple
			gridPanel.add(customerLabel);
			gridPanel.add(comboBox);
			gridPanel.add(orderLabel);
			gridPanel.add(orderField);
			gridPanel.add(shipLabel);
			gridPanel.add(shipField);
			gridPanel.add(payLabel);
			gridPanel.add(payField);
			gridPanel.add(priceLabel);
			gridPanel.add(priceField);
			//add listener to submit button
			FormListener form = new FormListener();
			submit.addActionListener(form);
			//add grid panel to borderLayout
			innerPanel = new JPanel(new BorderLayout(0,10));
			innerPanel.setBackground(new Color(200, 191, 231)); //purple
			innerPanel.add(BorderLayout.NORTH,gridPanel);
			innerPanel.add(BorderLayout.CENTER,submit);
			//add inner panel to flow layout panel then to frame
			outerPanel = new JPanel(new FlowLayout());
			outerPanel.setBackground(new Color(200, 191, 231)); //purple
			outerPanel.add(innerPanel);//flow layout
			//set tabs in frame
	 		TabListener tabListener = new TabListener();
	 		tabs.add("Order Table Menu",label5);
	 		tabs.add("Main Menu", label2);
	 		tabs.add("Customer Table Menu",label1);//make tab for customer table
			tabs.add("Invoice Table Menu",label3);
			tabs.add("Product Table Menu",label4);
			tabs.addChangeListener(tabListener);
			UIManager.put("TabbedPane.contentAreaColor", new Color(200, 191, 231, 100));
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
			flowPanel.setBackground(new Color(200, 191, 231)); //purple
			//sql customer table
			JTable resultTable = new JTable((TableModel) orderTable);
			//add resultTable to a border layout 
			innerBorderPanel = new JPanel(new BorderLayout(40,0));//Push center components apart
			innerBorderPanel.add( new JScrollPane( resultTable));
			innerBorderPanel.setBackground(new Color(200, 191, 231)); //purple
			//add components to frame
			outerBorderPanel = new JPanel();
			outerBorderPanel.setLayout(new BorderLayout());
			outerBorderPanel.add(BorderLayout.NORTH,tabs);
			outerBorderPanel.add(BorderLayout.WEST,outerPanel);
			outerBorderPanel.add(BorderLayout.CENTER,innerBorderPanel);
			outerBorderPanel.add(BorderLayout.SOUTH,flowPanel);
			outerBorderPanel.setBackground(new Color(200, 191, 231)); //purple
			add(outerBorderPanel);//add to jframe
			orderTable.setQuery(QUERY);
		}
		catch (SQLException sqlException) {
			JOptionPane.showMessageDialog( null, sqlException.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
			orderTable.disconnectFromDatabase();
			System.exit (1);
		}//end of catch
			pack();//stops layout manager only with swing
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocation(310, 20);
			setSize(900, 700);
			setVisible(true);
	}//end of constructor
public class ComboListener implements ActionListener{//deals with j combo box selection
		public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				customerId = (String)cb.getSelectedItem();
			}
		}//end combo
public class FormListener implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submit) {
			String oDate,sDate,pay;
			Test testing = new Test();
			oDate = orderField.getText();
			sDate = shipField.getText();
			pay = payField.getText();
			oDate = testing.checkDate(oDate);
			sDate = testing.checkDate(sDate);
			if(pay.equals("y") || pay.equals("Y")) {
				pay = "Yes";
			}
			else if(pay.equals("n") || pay.equals("N")) {
				pay = "No";
			}
			else {
				pay = "";
			}
			int custId = -1;
			double price = 0;
			try {
					custId = Integer.parseInt(customerId);
					price = Double.parseDouble(priceField.getText());
				}
				catch(Exception err) {
					err.printStackTrace();
				}
			if(oDate.equals("?") || sDate.equals("?") || pay.equals("") ||price == 0 || custId == -1){
				JOptionPane.showMessageDialog(null, "Please Fill In All Details Correctly", "Alert", JOptionPane.ERROR_MESSAGE);
			}
			else {
				try {
					orderTable.insertInfo(oDate, sDate, pay,price,custId);
					//open new window
					AddOrder addOrder = new AddOrder();
					addOrder.setVisible(true);
					//close out current window
					setVisible(false);
					dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}//end of action performed
}//end of FormListener
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
		else if(pickTab == 3) {//if invoice table is selected
			//open new window
			InvoiceMenu invoiceMenu = new InvoiceMenu();
			invoiceMenu.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
			}
		else if(pickTab == 4) {//product table
			//open new window
			ProductMenu productMenu = new ProductMenu();
			productMenu.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
			}
		else if(pickTab == 2) {
			//open new window
			CustomerMenu customerMenu = new CustomerMenu();
			customerMenu.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
		}
}//end of stateChanged method	 
}//end of TabListener class	
class ButtonListener implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == add) {
			AddOrder addOrder = new AddOrder();
			addOrder.setVisible(true);
			setVisible(false);
			dispose();
		}
		else if(e.getSource() == delete) {
			DeleteOrder deleteOrder = new DeleteOrder();
			deleteOrder.setVisible(true);
			setVisible(false);
			dispose();	
		}
		else if(e.getSource() == edit) {
			EditOrder editOrder = new EditOrder();
			editOrder.setVisible(true);
			setVisible(false);
			dispose();	
		}
		else if(e.getSource() == query) {//move to customer Query screen
			OrderQuery orderQuery = new OrderQuery();
			orderQuery.setVisible(true);
			setVisible(false);
			dispose();		
		}
	}//end of action performed method
}//end of Button Listener class	
}// end of add order class
