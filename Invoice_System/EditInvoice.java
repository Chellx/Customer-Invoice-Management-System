package data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event. ActionListener ;
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
public class EditInvoice extends JFrame{
private JButton submit;
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
//panels for update info
private JPanel gridPanel;
private JPanel innerPanel;
private JPanel outerPanel;
//labels for update info
private JLabel subTotal = new JLabel("Edit Subtotal");
private JLabel discount = new JLabel("Edit Discount");
private JLabel where = new JLabel("Select ID To Edit");
private JLabel info = new JLabel("Please Enter Data For All Fields");
//textfields for update
private JTextField sTotalTextField = new JTextField();
private JTextField discountTextField = new JTextField();
//Buttons for add,edit,delete,Query
private JButton add = new JButton("Add Invoice");
private JButton delete = new JButton("Delete Invoice");
private JButton edit = new JButton("Edit Invoice");
private JButton query = new JButton("Query Invoice");
static final String QUERY = "SELECT * FROM invoice";
//Jcombo box
private JComboBox comboBox;
private String[] ids = new String[30];
private String invoiceId ="";
	public EditInvoice() {
		super("Edit Invoice");
		sTotalTextField.setToolTipText("(€) Sub-Total Of Order");
		discountTextField.setToolTipText("(€) Amount Of Any Discounts On The Order");
		try {
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
			invoiceTable = new InvoiceTable();
			invoiceTable.setQuery(QUERY);
			UpdateListener update = new UpdateListener();
			//QueryListener queryListener = new QueryListener();
			submit = new JButton("Update");
			submit.addActionListener(update);//add listener to button
			//make comboBox
			int size = invoiceTable.getInvoiceId(ids,0);
			String[] filteredIds = new String[size];
			for (int i = 0; i < size; i++) {
				filteredIds[i] = ids[i];
			}
			comboBox = new JComboBox(filteredIds);
			ComboListener combo = new ComboListener();
			comboBox.addActionListener(combo);
			//Create grid for  submit button
			sTotalTextField.setColumns(10);
			gridPanel = new JPanel(new GridLayout(4,2,0,5));
			gridPanel.setBackground(new Color(255, 174, 201)); //pink
			gridPanel.add(where);
			gridPanel.add(comboBox);
			gridPanel.add(subTotal);
			gridPanel.add(sTotalTextField);
			gridPanel.add(discount);
			gridPanel.add(discountTextField);
			gridPanel.add(submit);
			//add grid panel to borderLayout
			innerPanel = new JPanel(new BorderLayout(0,10));
			innerPanel.setBackground(new Color(255, 174, 201)); //pink
			innerPanel.add(BorderLayout.NORTH,info);
			innerPanel.add(BorderLayout.CENTER,gridPanel);
			innerPanel.add(BorderLayout.SOUTH,submit);
			//add inner panel to flow layout panel then to frame
			outerPanel = new JPanel(new FlowLayout());
			outerPanel.setBackground(new Color(255, 174, 201)); //pink
			outerPanel.add(innerPanel);//flow layout
			//set customer table in j table
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
			//resets jtable display
			invoiceTable.setQuery(QUERY);
		} //end of try
		catch (SQLException sqlException) {
			JOptionPane.showMessageDialog( null, sqlException.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
			invoiceTable.disconnectFromDatabase();
			System.exit (1);
		}//end of catch
		pack();//stops layout manager
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(310, 20);
		setSize(900, 700);
		setVisible(true);
		}
public class ComboListener implements ActionListener{//deals with j combo box selection

		public void actionPerformed(ActionEvent e) {
			 JComboBox cb = (JComboBox)e.getSource();
			 invoiceId = (String)cb.getSelectedItem();
		}
	}//end combo	
	public class UpdateListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == submit) {
				String sub,dis,wher;
				double subTotal = 0,discount = 0,total = 0;
				sub = sTotalTextField.getText();
				dis = discountTextField.getText();
				int id = 0;
				try {//handles someone entering anything other than a number
					id = Integer.parseInt(invoiceId);
					subTotal = Double.parseDouble(sub);
					discount = Double.parseDouble(dis);
					total =  subTotal * (discount / 100);
					total = subTotal - total;
				}
				catch(Exception numError) {	
					
				}
					try {
						if(subTotal != 0 && discount != 0 && total != 0 && id != 0 ) {
							invoiceTable.updateAll(subTotal,discount,total,id);
							EditInvoice editInvoice = new EditInvoice();
							editInvoice.setVisible(true);
							//close out current window
							setVisible(false);
							dispose();
						}
						else {
							JOptionPane.showMessageDialog(null, "Please Fill In All Details Correctly", "Alert", JOptionPane.ERROR_MESSAGE);
							}
				
						} //end of try
					catch (SQLException e1) {
						e1.printStackTrace();
					}
			}
	}//end of action performed
}//end of Update Listener
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
}//end of Edit Customer Class
