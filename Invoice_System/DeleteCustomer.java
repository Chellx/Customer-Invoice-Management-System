package data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.table.TableModel;

public class DeleteCustomer extends JFrame{
private JButton submit;
private CustomerTable customerTable;
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
private JLabel deleteMessage = new JLabel("Enter Customer ID To Delete");
//array for checking valid ids
private String [] ids = new String[30];
//Buttons for add,edit,delete,Query
private JButton add = new JButton("Add Customer");
private JButton delete = new JButton("Delete Customer");
private JButton edit = new JButton("Edit Customer");
private JButton query = new JButton("Query Customer");
private JTextField deleteField = new JTextField();
static final String QUERY = "SELECT * FROM customer";	
	
	public DeleteCustomer() {
		super("Delete Customer");
		try {
			//set tabs in frame
	 		TabListener tabListener = new TabListener();
	 		tabs.add("Customer Table Menu",label1);//make tab for customer table
	 		tabs.add("Main Menu", label2);
			tabs.add("Invoice Table Menu",label3);
			tabs.add("Product Table Menu",label4);
			tabs.add("Order Table Menu",label5);
			tabs.addChangeListener(tabListener);
			UIManager.put("TabbedPane.contentAreaColor", new Color(188, 241, 219, 100));
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
			//sql customer table
			customerTable = new CustomerTable();
			customerTable.setQuery(QUERY);
			//change to delete listener
			DeleteListener deleteListener = new DeleteListener();
			submit = new JButton("Delete");
			submit.addActionListener(deleteListener);//add listener to button
			//Create box for scrollPane and submit button
			Box boxNorth = Box.createHorizontalBox();
			boxNorth.add(deleteMessage);
			boxNorth.add(deleteField);
			boxNorth.add(submit);
			//set customer table in j table
			JTable resultTable = new JTable((TableModel) customerTable);
			//add the first box ,the resultTable and the last box to a border layout 
			innerBorderPanel = new JPanel(new BorderLayout());
			innerBorderPanel.add( boxNorth, BorderLayout.NORTH );
			innerBorderPanel.add( new JScrollPane( resultTable ), BorderLayout.CENTER );
			innerBorderPanel.setBackground(new Color(188, 241, 219)); //green
			//set tabs and grid panel in borderlayout
			outerBorderPanel = new JPanel();
			outerBorderPanel.setLayout(new BorderLayout());
			outerBorderPanel.add(BorderLayout.NORTH,tabs);
			outerBorderPanel.add(BorderLayout.CENTER,innerBorderPanel);
			outerBorderPanel.add(BorderLayout.SOUTH,flowPanel);
			outerBorderPanel.setBackground(new Color(188, 241, 219)); //green
			add(outerBorderPanel);//add to jframe
		} //end of try
		catch (SQLException sqlException) {
			JOptionPane.showMessageDialog( null, sqlException.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
			customerTable.disconnectFromDatabase();
			System.exit (1);
		}//end of catch
		pack();//stops layout manager
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(310, 20);
		setSize(900, 700);
		setVisible(true);
	}//end of constructor
class DeleteListener implements ActionListener{
	public void actionPerformed(ActionEvent e) {

			if(e.getSource() == submit) {
				int id = 0;
				int i = 1;
				try {//handles someone entering anything other than a number
					customerTable.getCustomerId(ids,1);// used for checking valid id
					while(!ids[i].equals("")){
						if(ids[i].equals(deleteField.getText())) {
							id = Integer.parseInt(deleteField.getText());//gets text from field
						}
						i++;
					}
				}
				catch(Exception numError) {
					
				}
				deleteField.setText("");
				if(id != 0) {
					try {
						customerTable.deleteInfo(id);
						DeleteCustomer deleteCustomer = new DeleteCustomer();
						deleteCustomer.setVisible(true);
						//close out current window
						setVisible(false);
						dispose();
						
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}	
				}//end of inner if
				else {
					JOptionPane.showMessageDialog(null, "Only Enter A Valid ID", "Alert", JOptionPane.ERROR_MESSAGE);
				}
			}//end of outer if	
		}//end of method
}//end of delete listener class
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
			else if(pickTab == 3) {
				//open new window
				ProductMenu productMenu = new ProductMenu();
				productMenu.setVisible(true);
				//close out current window
				setVisible(false);
				dispose();
				}
			else if(pickTab == 4) {
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
			//open new window
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
}
