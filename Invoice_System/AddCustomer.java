package data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
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


public class AddCustomer extends JFrame{
private JLabel fNameLabel,lNameLabel,streetLabel,townCityLabel,countyLabel,countryLabel,emailLabel,phoneLabel;	
private JPanel gridPanel,outerPanel,innerPanel;
private JTextField fName,lName,street,townCity,county,country,email,phone;
private JButton submit;
//Panels 
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
//Buttons for add,edit,delete,Query
private JButton add = new JButton("Add Customer");
private JButton delete = new JButton("Delete Customer");
private JButton edit = new JButton("Edit Customer");
private JButton query = new JButton("Query Customer");
static final String QUERY = "SELECT * FROM customer";
	public AddCustomer() {
	super("Add Customer");
	try {	
		//Labels
		fNameLabel = new JLabel("First Name");
		lNameLabel = new JLabel("Last Name");
		streetLabel = new JLabel("Street");
		townCityLabel = new JLabel("Town/City");
		countyLabel = new JLabel("County");
		countryLabel = new JLabel("Country");
		emailLabel = new JLabel("Email");
		phoneLabel = new JLabel("Phone");
		//JTextFields
		fName = new JTextField("");
		fName.setToolTipText("Alphabetical Characters Only!");
		lName = new JTextField("");
		lName.setToolTipText("Alphabetical Characters Only!");
		street = new JTextField("");
		street.setToolTipText("eg: 123 Main Street");
		townCity = new JTextField("");
		townCity.setToolTipText("Name of town or city you currently live in");
		county = new JTextField("");
		county.setToolTipText("eg: Carlow");
		country = new JTextField("");
		country.setToolTipText("eg: Ireland");
		email = new JTextField("");
		email.setToolTipText("eg: johndoe@yahoo.ie");
		phone = new JTextField("");
		phone.setToolTipText("eg: 0851234567");
		submit = new JButton("Submit");
		fName.setColumns(10);
		//add components into 2*8 grid
		gridPanel = new JPanel(new GridLayout(9,2,20,15));
		gridPanel.setBackground(new Color(188, 241, 219)); //green
		gridPanel.add(fNameLabel);
		gridPanel.add(fName);
		gridPanel.add(lNameLabel);
		gridPanel.add(lName);
		gridPanel.add(streetLabel);
		gridPanel.add(street);
		gridPanel.add(townCityLabel);
		gridPanel.add(townCity);
		gridPanel.add(countyLabel);
		gridPanel.add(county);
		gridPanel.add(countryLabel);
		gridPanel.add(country);
		gridPanel.add(emailLabel);
		gridPanel.add(email);
		gridPanel.add(phoneLabel);
		gridPanel.add(phone);
		//add listener to submit button
		FormListener form = new FormListener();
		submit.addActionListener(form);
		//add grid panel to borderLayout
		innerPanel = new JPanel(new BorderLayout());
		innerPanel.setBackground(new Color(188, 241, 219)); //light green
		innerPanel.add(BorderLayout.NORTH,gridPanel);
		innerPanel.add(BorderLayout.CENTER,submit);
		//add inner panel to flow layout panel then to frame
		outerPanel = new JPanel(new FlowLayout());
		outerPanel.setBackground(new Color(188, 241, 219)); //light green
		outerPanel.add(innerPanel);//flow layout
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
		flowPanel.setBackground(new Color(188, 241, 219));//green
		//sql customer table
		customerTable = new CustomerTable();
		customerTable.setQuery(QUERY);
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
	}
	catch (SQLException sqlException) {
		JOptionPane.showMessageDialog( null, sqlException.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
		customerTable.disconnectFromDatabase();
		System.exit (1);
	}//end of catch
		pack();//stops layout manager only with swing
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(310, 20);
		setSize(900, 700);
		setVisible(true);
	}//end of constructor 

public class FormListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submit) {
			Test testing = new Test();
			String firstName,lastName,road,cityTown,count,countr,emai,phon;
			firstName = fName.getText();
			lastName = lName.getText();
			road = street.getText();
			cityTown = townCity.getText();
			count = county.getText();
			countr = country.getText();
			emai = email.getText();
			phon = phone.getText();
			firstName = testing.allAlpha(firstName);
			lastName = testing.allAlpha(lastName);
			road = testing.alphaAndNums(road);
			cityTown = testing.allAlpha(cityTown);
			count = testing.allAlpha(count);
			countr = testing.allAlpha(countr);
			emai = testing.atSymbolAndDot(emai);
			phon = testing.justNumbers(phon);
			if(firstName.equals("?") || lastName.equals("?") || road.equals("?") || cityTown.equals("?") || count.equals("?") || countr.equals("?") || emai.equals("?") || phon.equals("?")){
				JOptionPane.showMessageDialog(null, "Please Fill In All Details Correctly", "Alert", JOptionPane.ERROR_MESSAGE);
			}
			else {
				try {
					customerTable.insertInfo(firstName, lastName, road, cityTown, count, countr, emai, phon);
					//open new window
					AddCustomer addCustomer = new AddCustomer();
					addCustomer.setVisible(true);
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
		else if(pickTab == 4) {//product menu
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
}//end of Add Customer class