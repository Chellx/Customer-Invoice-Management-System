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
public class EditCustomer extends JFrame{
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
//panels for update info
private JPanel gridPanel;
private JPanel innerPanel;
private JPanel outerPanel;
//labels for update info
private JLabel firstName = new JLabel("Edit First Name");
private JLabel lastName = new JLabel("Edit Last Name");
private JLabel street = new JLabel("Edit Street");
private JLabel townCity = new JLabel("Edit Town/City");
private JLabel county = new JLabel("Edit County");
private JLabel country = new JLabel("Edit Country");
private JLabel email = new JLabel("Edit Email");
private JLabel phone = new JLabel("Edit Phone");
private JLabel where = new JLabel("Select ID To Edit");
//textfields for update
private JTextField fNameTextField = new JTextField();
private JTextField lNameTextField = new JTextField();
private JTextField streetTextField = new JTextField();
private JTextField townCityTextField = new JTextField();
private JTextField countyTextField = new JTextField();
private JTextField countryTextField = new JTextField();
private JTextField emailTextField = new JTextField();
private JTextField phoneTextField = new JTextField();
//Buttons for add,edit,delete,Query
private JButton add = new JButton("Add Customer");
private JButton delete = new JButton("Delete Customer");
private JButton edit = new JButton("Edit Customer");
private JButton query = new JButton("Query Customer");
//Jcombo box
private JComboBox comboBox;
private String[] ids = new String[30];
private String customerId ="";
static final String QUERY = "SELECT * FROM customer";	
	public EditCustomer() {
		super("Edit Customer");
		
		
		
		fNameTextField.setToolTipText("Alphabetical Characters Only!");
		lNameTextField.setToolTipText("Alphabetical Characters Only!");
		streetTextField.setToolTipText("eg: 123 Main Street");
		townCityTextField.setToolTipText("Name of town or city customer currently live in");
		countyTextField.setToolTipText("eg: Carlow");
		countryTextField.setToolTipText("eg: Ireland");
		emailTextField.setToolTipText("eg: johndoe@yahoo.ie");
		phoneTextField.setToolTipText("eg: 0851234567");
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
			UpdateListener update = new UpdateListener();
			//QueryListener queryListener = new QueryListener();
			submit = new JButton("Update");
			submit.addActionListener(update);//add listener to button
			//make comboBox
			int size = customerTable.getCustomerId(ids,0);
			String[] filteredIds = new String[size];
			for (int i = 0; i < size; i++) {
				filteredIds[i] = ids[i];
			}
			comboBox = new JComboBox(filteredIds);
			ComboListener combo = new ComboListener();
			comboBox.addActionListener(combo);
			//Create grid for submit button
			fNameTextField.setColumns(10);
			gridPanel = new JPanel(new GridLayout(9,2,0,10));
			gridPanel.setBackground(new Color(188, 241, 219)); //green
			gridPanel.add(where);
			gridPanel.add(comboBox);
			gridPanel.add(firstName);
			gridPanel.add(fNameTextField);
			gridPanel.add(lastName);
			gridPanel.add(lNameTextField);
			gridPanel.add(street);
			gridPanel.add(streetTextField);
			gridPanel.add(townCity);
			gridPanel.add(townCityTextField);
			gridPanel.add(county);
			gridPanel.add(countyTextField);
			gridPanel.add(country);
			gridPanel.add(countryTextField);
			gridPanel.add(email);
			gridPanel.add(emailTextField);
			gridPanel.add(phone);
			gridPanel.add(phoneTextField);
			//add grid panel to borderLayout
			innerPanel = new JPanel(new BorderLayout(0,10));
			innerPanel.setBackground(new Color(188, 241, 219)); //green
			innerPanel.add(BorderLayout.CENTER,gridPanel);
			innerPanel.add(BorderLayout.SOUTH,submit);
			//add inner panel to flow layout panel then to frame
			outerPanel = new JPanel(new FlowLayout());
			outerPanel.setBackground(new Color(188, 241, 219)); //green
			outerPanel.add(innerPanel);//flow layout
			//set customer table in j table
			JTable resultTable = new JTable((TableModel) customerTable); 
			//add resultTable to a border layout 
			innerBorderPanel = new JPanel(new BorderLayout(40,0));//Push center components apart
			innerBorderPanel.add( new JScrollPane( resultTable));
			innerBorderPanel.setBackground(new Color(188, 241, 219)); //green
			//add components to frame
			outerBorderPanel = new JPanel();
			outerBorderPanel.setLayout(new BorderLayout());
			outerBorderPanel.add(BorderLayout.NORTH,tabs);
			outerBorderPanel.add(BorderLayout.WEST,outerPanel);
			outerBorderPanel.add(BorderLayout.CENTER,innerBorderPanel);
			outerBorderPanel.add(BorderLayout.SOUTH,flowPanel);
			outerBorderPanel.setBackground(new Color(188, 241, 219)); //green
			add(outerBorderPanel);//add to jframe
			//needed to reset j table display
			customerTable.setQuery(QUERY);
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
public class ComboListener implements ActionListener{//deals with j combo box selection

	public void actionPerformed(ActionEvent e) {
		 JComboBox cb = (JComboBox)e.getSource();
		 customerId = (String)cb.getSelectedItem();
	}
}//end combo
public class UpdateListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submit) {
			String firstName,lastName,road,cityTown,count,countr,emai,phon;
			
			firstName = fNameTextField.getText();
			lastName = lNameTextField.getText();
			road = streetTextField.getText();
			cityTown = townCityTextField.getText();
			count = countyTextField.getText();
			countr = countryTextField.getText();
			emai = emailTextField.getText();
			phon = phoneTextField.getText();
			Test testing = new Test();
			if(!firstName.equals("")) {
				firstName = testing.allAlpha(firstName);
			}
			if(firstName.equals("?")) {
				JOptionPane.showMessageDialog(null, "Please Fill In First Name Correctly", "Alert", JOptionPane.ERROR_MESSAGE);
			}
			if(!lastName.equals("")) {
				lastName = testing.allAlpha(lastName);
			}
			if(lastName.equals("?")) {
				JOptionPane.showMessageDialog(null, "Please Fill In Last Name Correctly", "Alert", JOptionPane.ERROR_MESSAGE);
			}
			if(!road.equals("")) {
				road = testing.alphaAndNums(road);
			}
			if(road.equals("?")) {
				JOptionPane.showMessageDialog(null, "Please Fill In Street Name Correctly", "Alert", JOptionPane.ERROR_MESSAGE);
			}
			if(!count.equals("")) {
				count = testing.allAlpha(count);
			}
			if(count.equals("?")) {
				JOptionPane.showMessageDialog(null, "Please Fill In County Name Correctly", "Alert", JOptionPane.ERROR_MESSAGE);
			}
			if(!countr.equals("")) {
				countr = testing.allAlpha(countr);
			}	
			if(countr.equals("?")) {
				JOptionPane.showMessageDialog(null, "Please Fill In Country Name Correctly", "Alert", JOptionPane.ERROR_MESSAGE);
			}
			if(!emai.equals("")) {
				emai = testing.atSymbolAndDot(emai);
			}
			if(emai.equals("?")) {
				JOptionPane.showMessageDialog(null, "Please Fill In Email Correctly", "Alert", JOptionPane.ERROR_MESSAGE);
			}
			if(!phon.equals("")) {
				phon = testing.justNumbers(phon);
			}
			if(phon.equals("?")) {
				JOptionPane.showMessageDialog(null, "Please Fill In phone number Correctly", "Alert", JOptionPane.ERROR_MESSAGE);
			}
			int id = 0;
			try {//handles someone entering anything other than a number
				id = Integer.parseInt(customerId);
			}
			catch(Exception numError) {
				
			}
			
			if(!customerId.equals("")){
				try {
					if(!firstName.equals("")&& id != 0 && !firstName.equals("?")) {
						customerTable.updateFName(firstName, id);
					}
					if(!lastName.equals("") && id != 0 && !firstName.equals("?")) {
						customerTable.updateLName(lastName, id);
					}
					if(!road.equals("") && id != 0 && !firstName.equals("?")) {
						customerTable.updateStreet(road, id);
					}
					if(!cityTown.equals("") && id != 0 && !firstName.equals("?")) {
						customerTable.updateTownCity(cityTown, id);
					}
					if(!count.equals("") && id != 0 && !firstName.equals("?")) {
						customerTable.updateCounty(count, id);
					}
					if(!countr.equals("") && id != 0 && !firstName.equals("?")) {
						customerTable.updateCountry(countr, id);
					}
					if(!emai.equals("") && id != 0 && !firstName.equals("?")) {
						customerTable.updateEmail(emai, id);
					}
					if(!phon.equals("") && id != 0 && !firstName.equals("?")) {
						customerTable.updatePhone(phon, id);
					}
					//open new window
					EditCustomer editCustomer = new EditCustomer();
					editCustomer.setVisible(true);
					//close out current window
					setVisible(false);
					dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Please Fill In All Details Correctly", "Alert", JOptionPane.ERROR_MESSAGE);
			}
		}
	}//end of action performed
}//end of Update Listener
}//end of Edit Customer class
