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
public class EditProduct extends JFrame{
private JButton submit;
private ProductTable productTable;
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
private JLabel title = new JLabel("Edit Book Title");
private JLabel author = new JLabel("Edit Author Name");
private JLabel publish = new JLabel("Edit Publisher Details");
private JLabel ISBN = new JLabel("Edit ISBN Code");
private JLabel unitPrice = new JLabel("Edit Unit Price");
private JLabel salePrice = new JLabel("Edit Sale Price");
private JLabel stock = new JLabel("Edit Stock");
private JLabel where = new JLabel("Select ID To Edit");
//textfields for update
private JTextField titleTextField = new JTextField("");
private JTextField authorTextField = new JTextField("");
private JTextField publishTextField = new JTextField("");
private JTextField isbnTextField = new JTextField("");
private JTextField unitTextField = new JTextField("");
private JTextField saleTextField = new JTextField("");
private JTextField stockTextField = new JTextField("");
//Buttons for add,edit,delete,Query
private JButton add = new JButton("Add Product");
private JButton delete = new JButton("Delete Product");
private JButton edit = new JButton("Edit Product");
private JButton query = new JButton("Query Product");
static final String QUERY = "SELECT * FROM product";	
//Jcombo box
private JComboBox comboBox;
private String[] ids = new String[30];
private String productId ="";
/**
 * Default Constructor
 */
	public EditProduct() {
		super("Edit Product");
		publishTextField.setToolTipText("Name Of Publisher");
		isbnTextField.setToolTipText("ISBN Code");
		unitTextField.setToolTipText("(€) Unit Price");
		saleTextField.setToolTipText("(€) Sell Price");
		stockTextField.setToolTipText("Stock Amount");
		
		try {
			//set tabs in frame
	 		TabListener tabListener = new TabListener();
	 		tabs.add("Product Table Menu",label4);
	 		tabs.add("Customer Table Menu",label1);//make tab for customer table
	 		tabs.add("Main Menu", label2);
			tabs.add("Invoice Table Menu",label3);
			tabs.add("Order Table Menu",label5);
			tabs.addChangeListener(tabListener);
			UIManager.put("TabbedPane.contentAreaColor", new Color(255, 201, 14, 100));
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
			flowPanel.setBackground(new Color(255, 201, 14)); //yellow
			//sql customer table
			productTable = new ProductTable();
			productTable.setQuery(QUERY);
			UpdateListener update = new UpdateListener();
			submit = new JButton("Update");
			submit.addActionListener(update);//add listener to button
			//make comboBox
			int size = productTable.getProductId(ids,0);
			String[] filteredIds = new String[size];
			for (int i = 0; i < size; i++) {
				filteredIds[i] = ids[i];
			}
			comboBox = new JComboBox(filteredIds);
			ComboListener combo = new ComboListener();
			comboBox.addActionListener(combo);
			//Create grid for  submit button
			titleTextField.setColumns(10);
			gridPanel = new JPanel(new GridLayout(8,2,0,10));
			gridPanel.setBackground(new Color(255, 201, 14)); //yellow
			gridPanel.add(where);
			gridPanel.add(comboBox);
			gridPanel.add(title);
			gridPanel.add(titleTextField);
			gridPanel.add(author);
			gridPanel.add(authorTextField);
			gridPanel.add(publish);
			gridPanel.add(publishTextField);
			gridPanel.add(ISBN);
			gridPanel.add(isbnTextField);
			gridPanel.add(unitPrice);
			gridPanel.add(unitTextField);
			gridPanel.add(salePrice);
			gridPanel.add(saleTextField);
			gridPanel.add(stock);
			gridPanel.add(stockTextField);
			//add grid panel to borderLayout
			innerPanel = new JPanel(new BorderLayout(0,10));
			innerPanel.setBackground(new Color(255, 201, 14)); //yellow
			innerPanel.add(BorderLayout.CENTER,gridPanel);
			innerPanel.add(BorderLayout.SOUTH,submit);
			//add inner panel to flow layout panel then to frame
			outerPanel = new JPanel(new FlowLayout());
			outerPanel.setBackground(new Color(255, 201, 14)); //yellow
			outerPanel.add(innerPanel);//flow layout
			//set customer table in j table
			JTable resultTable = new JTable((TableModel) productTable); 
			//add resultTable to a border layout 
			innerBorderPanel = new JPanel(new BorderLayout(40,0));//Push center components apart
			innerBorderPanel.add( new JScrollPane( resultTable));
			innerBorderPanel.setBackground(new Color(255, 201, 14)); //yellow
			//add components to frame
			outerBorderPanel = new JPanel();
			outerBorderPanel.setLayout(new BorderLayout());
			outerBorderPanel.add(BorderLayout.NORTH,tabs);
			outerBorderPanel.add(BorderLayout.WEST,outerPanel);
			outerBorderPanel.add(BorderLayout.CENTER,innerBorderPanel);
			outerBorderPanel.add(BorderLayout.SOUTH,flowPanel);
			outerBorderPanel.setBackground(new Color(255, 201, 14)); //yellow
			add(outerBorderPanel);//add to jframe
			//needed to reset table display
			productTable.setQuery(QUERY);
		} //end of try
		catch (SQLException sqlException) {
			JOptionPane.showMessageDialog( null, sqlException.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
			productTable.disconnectFromDatabase();
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
		if(pickTab == 2) {//if Main menu is selected
			//open new window
			MainMenu mainMenu = new MainMenu();
			mainMenu.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
			}
		else if(pickTab == 0) {//product
						
			}//end of if
		else if(pickTab == 3) {
			//open new window
			InvoiceMenu invoiceMenu = new InvoiceMenu();
			invoiceMenu.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
			}
		else if(pickTab == 1) {
			//open new window
			CustomerMenu customerMenu = new CustomerMenu();
			customerMenu.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
			}
		else if(pickTab == 4) {//order menu
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
			AddProduct addProduct = new AddProduct();
			addProduct.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
			}
		else if(e.getSource() == delete) {
			//open new window
			DeleteProduct deleteProduct = new DeleteProduct();
			deleteProduct.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
			}
		else if(e.getSource() == edit) {
			//open new window
			EditProduct editProduct = new EditProduct();
			editProduct.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
			}
		else if(e.getSource() == query) {
			//open new window
			ProductQuery productQuery = new ProductQuery();
			productQuery.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
			}
	}//end of action performed method
}//end of Button Listener class
public class ComboListener implements ActionListener{//deals with j combo box selection

	public void actionPerformed(ActionEvent e) {
		 JComboBox cb = (JComboBox)e.getSource();
		 productId = (String)cb.getSelectedItem();
	}
}//end combo
public class UpdateListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submit) {
			String uPrice= "",sPrice = "",stoc = "",title ="",author = "",publish = "",isbn = "";
				
			title = titleTextField.getText();
			author = authorTextField.getText();
			publish = publishTextField.getText();
			isbn = isbnTextField.getText();
			uPrice = unitTextField.getText();
			sPrice = saleTextField.getText();
			stoc = stockTextField.getText();
			Test testing = new Test();
			if(!title.equals("")) {
				title = testing.alphaAndNums(title);
			}
			if(title.equals("?")) {
				JOptionPane.showMessageDialog(null, "Please Enter Correct Book Title", "Alert", JOptionPane.ERROR_MESSAGE);
			}
			if(!author.equals("")) {
				author = testing.allAlpha(author);
			}
			if(author.equals("?")) {
				JOptionPane.showMessageDialog(null, "Please Enter Correct Author Name", "Alert", JOptionPane.ERROR_MESSAGE);
			}
			if(!publish.equals("")) {
				publish = testing.allAlpha(publish);
			}
			if(publish.equals("?")) {
				JOptionPane.showMessageDialog(null, "Please Enter Correct Publisher Name", "Alert", JOptionPane.ERROR_MESSAGE);
			}
			double Unit = -1,Sale = -1;
			int id = 0,Stock = -1,is = -1;
			try {//handles someone entering anything other than a number
				id = Integer.parseInt(productId);
				is = Integer.parseInt(isbn);
				Unit = Double.parseDouble(uPrice);
				Sale = Double.parseDouble(sPrice);
				Stock = Integer.parseInt(stoc);
			}
			catch(Exception numError) {
				
			}
			try {//handles someone entering anything other than a number
				Unit = Double.parseDouble(uPrice);
			}
			catch(Exception numError2) {
				
			}
			try {//handles someone entering anything other than a number
				Sale = Double.parseDouble(sPrice);
			}
			catch(Exception numError5) {
				
			}
			try {//handles someone entering anything other than a number
				Stock = Integer.parseInt(stoc);
			}
			catch(Exception numError4) {
				
			}
			if(!productId.equals("")){
				try {
					if(!title.equals("")&& id != 0 && !title.equals("?")) {
						productTable.updateTitle(title, id);
					}
					if(!author.equals("")&& id != 0 && !author.equals("?")) {
						productTable.updateAuthor(author, id);
					}
					if(!publish.equals("")&& id != 0 && !publish.equals("?")) {
						productTable.updatePublisher(publish, id);
					}
					if(is != -1 && id != 0 ) {
						productTable.updateISBN(is, id);
					}
					if(Unit != -1 && id != 0 ) {
						//System.out.print(Unit);
						productTable.updateUPrice(Unit, id);
					}
					if(Sale != -1 && id != 0 ) {
						productTable.updateSaleP(Sale, id);
					}
					if(Stock != -1 && id != 0 ) {
						productTable.updateStock(Stock, id);;
					}
					//open new window
					EditProduct editproduct = new EditProduct();
					editproduct.setVisible(true);
					//close out current window
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
}// end of Edit Product class