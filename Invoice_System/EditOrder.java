package data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event. ActionListener ;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JScrollPane ;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.table.TableModel;

import data.EditCustomer.ComboListener;
/**
 * 
 * @author Michelle Bolger
 *
 */
public class EditOrder extends JFrame{
private JButton submit;
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
//panels for update info
private JPanel gridPanel;
private JPanel innerPanel;
private JPanel outerPanel;
//labels for update info
private JLabel orderLabel = new JLabel("Edit Order Date (YYYY/MM/DD) ");
private JLabel shipLabel = new JLabel("Edit Ship Date (YYYY/MM/DD) ");
private JLabel payLabel = new JLabel("Edit PayReceived Y/N");
private JLabel priceLabel = new JLabel("Edit Price");
private JLabel where = new JLabel("Select ID To Edit");
//textfields for update
private JTextField orderTextField = new JTextField("");
private JTextField shipTextField = new JTextField("");
private JTextField payTextField = new JTextField("");
private JTextField priceTextField = new JTextField("");
//Buttons for add,edit,delete,Query
private JButton add = new JButton("Add Order");
private JButton delete = new JButton("Delete Order");
private JButton edit = new JButton("Edit Order");
private JButton query = new JButton("Query Order");
static final String QUERY = "SELECT * FROM invoicedatabase.order";
//used to make j combo box
private JComboBox comboBox;
private String[] ids = new String[30];
private String orderId ="";
/**
 * Default Constructor	
 */
public EditOrder() {
		super("Edit Order");
		orderTextField.setToolTipText("Date Order Was Placed ");
		shipTextField.setToolTipText("Date Order Was Shipped ");
		payTextField.setToolTipText("Payment Recieved From Customer (YES/NO)");
		priceTextField.setToolTipText("Cost Of Order");
		try {
			//set tabs in frame
	 		TabListener tabListener = new TabListener();
	 		tabs.add("Order Table Menu",label5);
	 		tabs.add("Customer Table Menu",label1);//make tab for customer table
	 		tabs.add("Main Menu", label2);
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
			orderTable = new OrderTable();
			orderTable.setQuery(QUERY);
			UpdateListener update = new UpdateListener();
			//QueryListener queryListener = new QueryListener();
			submit = new JButton("Update");
			submit.addActionListener(update);//add listener to button
			//make comboBox
			int size = orderTable.getOrderId(ids,0);
			String[] filteredIds = new String[size];
			for (int i = 0; i < size; i++) {
				filteredIds[i] = ids[i];
			}
			comboBox = new JComboBox(filteredIds);
			ComboListener combo = new ComboListener();
			comboBox.addActionListener(combo);
			//Create grid for  submit button
			orderTextField.setColumns(10);
			gridPanel = new JPanel(new GridLayout(5,2,0,10));
			gridPanel.setBackground(new Color(200, 191, 231)); //purple
			gridPanel.add(where);
			gridPanel.add(comboBox);
			gridPanel.add(orderLabel);
			gridPanel.add(orderTextField);
			gridPanel.add(shipLabel);
			gridPanel.add(shipTextField);
			gridPanel.add(payLabel);
			gridPanel.add(payTextField);
			gridPanel.add(priceLabel);
			gridPanel.add(priceTextField);
			//add grid panel to borderLayout
			innerPanel = new JPanel(new BorderLayout(0,10));
			innerPanel.setBackground(new Color(200, 191, 231)); //purple
			innerPanel.add(BorderLayout.CENTER,gridPanel);
			innerPanel.add(BorderLayout.SOUTH,submit);
			//add inner panel to flow layout panel then to frame
			outerPanel = new JPanel(new FlowLayout());
			outerPanel.setBackground(new Color(200, 191, 231)); //purple
			outerPanel.add(innerPanel);//flow layout
			//set customer table in j table
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
			//used to reset jtable display  
			orderTable.setQuery(QUERY);
		} //end of try
		catch (SQLException sqlException) {
			JOptionPane.showMessageDialog( null, sqlException.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
			orderTable.disconnectFromDatabase();
			System.exit (1);
		}//end of catch
		pack();//stops layout manager
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(310, 20);
		setSize(900, 700);
		setVisible(true);
	}//end of constructor
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
			else if(pickTab == 0) {
					
				}
			else if(pickTab == 3) {
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
public class ComboListener implements ActionListener{//deals with j combo box selection

	public void actionPerformed(ActionEvent e) {
		 JComboBox cb = (JComboBox)e.getSource();
		 orderId = (String)cb.getSelectedItem();
	}
}//end combo
public class UpdateListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submit) {
			String orDate,shDate,pay,quan,pric;
			Test testing = new Test();
			orDate = orderTextField.getText();
			shDate = shipTextField.getText();
			pay = payTextField.getText();
			pric = priceTextField.getText();
			if(!orDate.equals("")) {
				orDate = testing.checkDate(orDate);
			}
			if(orDate.equals("?")) {
				JOptionPane.showMessageDialog(null, "Please Enter Correct Order Date", "Alert", JOptionPane.ERROR_MESSAGE);
			}
			if(!shDate.equals("")) {
				shDate = testing.checkDate(shDate);
			}
			if(shDate.equals("?")) {
				JOptionPane.showMessageDialog(null, "Please Enter Correct Shiping Date", "Alert", JOptionPane.ERROR_MESSAGE);
			}
			if(pay.equals("y") || pay.equals("Y")) {
				pay = "Yes";
			}
			else if(pay.equals("n") || pay.equals("N")) {
				pay = "No";
			}
			else {
				pay = "";
			}
			int id = 0,qty = 0;
			double money = 0;
			
			try {//handles someone entering anything other than a number
				id = Integer.parseInt(orderId);
				money = Double.parseDouble(pric);
				
			}
			catch(Exception numError) {
				
			}
			if(!orderId.equals("")){
				try {
					if(!orDate.equals("")&& id != 0 && !orDate.equals("?")) {
						orderTable.updateODate(orDate, id);
					}
					if(!shDate.equals("") && id != 0 && !shDate.equals("?")) {
						orderTable.updatesDate(shDate, id);
					}
					if(!pay.equals("") && id != 0 ) {
						orderTable.updatePay(pay, id);
					}
					if(money != 0 && id != 0 ) {
						orderTable.updatePrice(money, id);
					}
					//open new window
					EditOrder editOrder = new EditOrder();
					editOrder.setVisible(true);
					setVisible(false);
					dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Please Fill In Details Correctly", "Alert", JOptionPane.ERROR_MESSAGE);
			}
		}
	}//end of action performed
}//end of Update Listener
}//end of Edit Order class