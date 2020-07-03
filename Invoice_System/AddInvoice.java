package data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.sql.Date;
import java.sql.SQLException;
//import java.util.Calendar;
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
public class AddInvoice extends JFrame{
private JLabel orderDate,subTotal,Discount,orderLabel,productLabel;	
private JPanel gridPanel,outerPanel,innerPanel;
private JTextField order,subT,discount;
private JButton submit;
//Panels 
private InvoiceTable invoiceTable;
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
private JButton add = new JButton("Add Invoice");
private JButton delete = new JButton("Delete Invoice");
private JButton edit = new JButton("Edit Invoice");
private JButton query = new JButton("Query Invoice");
static final String QUERY = "SELECT * FROM invoice";
//Jcombo box order
private JComboBox comboBox;
private String[] ids = new String[30];
private String orderId ="";
//Jcombo box product
private JComboBox comboBoxP;
private String[] pids = new String[30];
private String productId ="";
	public AddInvoice() {
	super("Add Invoice");
	try {	
		/////////make form to add customer
		//Labels
		orderDate = new JLabel("Order Date (YYY/MM/DD) ");
		subTotal = new JLabel("Sub Total");
		Discount = new JLabel("Discount");
		orderLabel = new JLabel("Select Order ID");
		productLabel = new JLabel("Select Product ID");
		//JTextFields
		order = new JTextField("");
		subT = new JTextField("");
		subT.setToolTipText("(€)Sub-Total of Order");
		discount = new JTextField("");
		discount.setToolTipText("(€) Amount Of Any Discounts On The Order");
		submit = new JButton("Submit");
		subT.setColumns(10);
		invoiceTable = new InvoiceTable();
		invoiceTable.setQuery(QUERY);
		//make comboBox order
		int size1 = invoiceTable.getOrderId(ids,0);
		String[] filteredIds1 = new String[size1];
		for (int i = 0; i < size1; i++) {
			filteredIds1[i] = ids[i];
		}
		comboBox = new JComboBox(filteredIds1);
		ComboListener combo = new ComboListener();
		comboBox.addActionListener(combo);
		//make comboBox product
		int size2 = invoiceTable.getProductId(pids,0);
		String[] filteredIds2 = new String[size2];
		for (int i = 0; i < size2; i++) {
			
			filteredIds2[i] = pids[i];
		}
		comboBoxP = new JComboBox(filteredIds2);
		BoxListener box = new BoxListener();
		comboBoxP.addActionListener(box);
		//add components into 2*8 grid
		gridPanel = new JPanel(new GridLayout(5, 2, 0,10));
		gridPanel.setBackground(new Color(255, 174, 201)); //pink
		gridPanel.add(orderLabel);
		gridPanel.add(comboBox);
		gridPanel.add(productLabel);
		gridPanel.add(comboBoxP);
		gridPanel.add(orderDate);
		gridPanel.add(order);
		gridPanel.add(subTotal);
		gridPanel.add(subT);
		gridPanel.add(Discount);
		gridPanel.add(discount);
		//add listener to submit button
		FormListener form = new FormListener();
		submit.addActionListener(form);
		//add grid panel to borderLayout
		innerPanel = new JPanel(new BorderLayout(0,10));
		innerPanel.setBackground(new Color(255, 174, 201)); //pink
		innerPanel.add(BorderLayout.NORTH,gridPanel);
		innerPanel.add(BorderLayout.CENTER,submit);
		//add inner panel to flow layout panel then to frame
		outerPanel = new JPanel(new FlowLayout());
		outerPanel.setBackground(new Color(255, 174, 201)); //pink
		outerPanel.add(innerPanel);//flow layout
		//set tabs in frame
 		TabListener tabListener = new TabListener();
 		tabs.add("Invoice Table Menu",label1);
 		tabs.add("Main Menu", label2);
 		tabs.add("Customer Table Menu",label3);//make tab for customer table
		tabs.add("Product Table Menu",label4);
		tabs.add("Order Table Menu",label5);
		tabs.addChangeListener(tabListener);
		UIManager.put("TabbedPane.contentAreaColor", new Color(255, 174, 201, 100));
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
		flowPanel.setBackground(new Color(255, 174, 201)); //pink
		//sql customer table
		JTable resultTable = new JTable((TableModel) invoiceTable);
		//add resultTable to a border layout 
		innerBorderPanel = new JPanel(new BorderLayout(40,0));//Push center components apart
		innerBorderPanel.add( new JScrollPane( resultTable));
		innerBorderPanel.setBackground(new Color(255, 174, 201)); //pink
		//add components to frame
		outerBorderPanel = new JPanel();
		outerBorderPanel.setLayout(new BorderLayout());
		outerBorderPanel.add(BorderLayout.NORTH,tabs);
		outerBorderPanel.add(BorderLayout.WEST,outerPanel);
		outerBorderPanel.add(BorderLayout.CENTER,innerBorderPanel);
		outerBorderPanel.add(BorderLayout.SOUTH,flowPanel);
		outerBorderPanel.setBackground(new Color(255, 174, 201)); //pink
		add(outerBorderPanel);//add to jframe
		invoiceTable.setQuery(QUERY);
	}
	catch (SQLException sqlException) {
		JOptionPane.showMessageDialog( null, sqlException.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
		invoiceTable.disconnectFromDatabase();
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
				orderId = (String)cb.getSelectedItem();
			}
		}//end combo
	public class BoxListener implements ActionListener{//deals with j combo box selection
		public void actionPerformed(ActionEvent e) {
				JComboBox cbox = (JComboBox)e.getSource();
				productId = (String)cbox.getSelectedItem();
			}
		}//end combo	
	public class FormListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == submit) {
				
				String ord="";
				double subTotal = 0.0,dis = 0.0,Total = 0.0;
				int orderID =-1,productID = -1;
				Test testing = new Test();
				ord =  order.getText();
				ord = testing.checkDate(ord);
				System.out.print(ord);
				try {
					orderID = Integer.parseInt(orderId);
					productID = Integer.parseInt(productId);
					subTotal = Double.parseDouble(subT.getText());
					dis = Double.parseDouble(discount.getText());
					dis =  subTotal * (dis / 100);
					Total = subTotal - dis;
				}
				catch(Exception err) {
					order.setText("");
					subT.setText("");
					discount.setText("");
				}
				
				if(order.getText().equals("?") || subT.getText().equals("") || discount.getText().equals("") || orderID == -1 || productID == -1){
					JOptionPane.showMessageDialog(null, "Please Fill In All Details Correctly", "Alert", JOptionPane.ERROR_MESSAGE);
				}
				else {
					try {
						System.out.println("***" + orderID);
						invoiceTable.insertInfo(ord, subTotal, dis, Total,orderID,productID);
						//open new window
						AddInvoice addInvoice = new AddInvoice();
						addInvoice.setVisible(true);
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
			else if(pickTab == 2) {//if customer table is selected
				//open new window
				CustomerMenu customerMenu = new CustomerMenu();
				customerMenu.setVisible(true);
				//close out current window
				setVisible(false);
				dispose();
			}//end of if
			else if(pickTab == 0) {//if invoice table is selected
				
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
				AddInvoice addInvoice = new AddInvoice();
				addInvoice.setVisible(true);
				//close out current window
				setVisible(false);
				dispose();
			}
			else if(e.getSource() == delete) {
				DeleteInvoice deleteInvoice = new DeleteInvoice();
				deleteInvoice.setVisible(true);
				//close out current window
				setVisible(false);
				dispose();
			}
			else if(e.getSource() == edit) {
				EditInvoice editInvoice = new EditInvoice();
				editInvoice.setVisible(true);
				//close out current window
				setVisible(false);
				dispose();
			}
			else if(e.getSource() == query) {//move to customer Query screen
				//open new window
				InvoiceQuery query = new InvoiceQuery();
				query.setVisible(true);
				//close out current window
				setVisible(false);
				dispose();
			}
		}//end of action performed method
	}//end of Button Listener class	
}//end of Add Invoice class
