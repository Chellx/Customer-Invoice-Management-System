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

import data.EditProduct.ComboListener;
/**
 * 
 * @author Michelle Bolger
 *
 */
public class AddProduct extends JFrame{
private JLabel unitLabel,saleLabel,stockLabel,titleLabel,authorLabel,ISBNLabel,publisherLabel;	
private JPanel gridPanel,outerPanel,innerPanel;
private JTextField unit,sale,stock,bookTitle,authorName,publishName,ISBNCode;
private JButton submit;
//Panels 
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
//Buttons for add,edit,delete,Query
private JButton add = new JButton("Add Product");
private JButton delete = new JButton("Delete Product");
private JButton edit = new JButton("Edit Product");
private JButton query = new JButton("Query Product");
static final String QUERY = "SELECT * FROM product";
/**
 * Default Constructor
 */
	public AddProduct() {
		super("Add Product");
		try {
			titleLabel = new JLabel("Book Title");
			authorLabel = new JLabel("Author");
			publisherLabel = new JLabel("Publisher");
			ISBNLabel = new JLabel("ISBN");
			unitLabel = new JLabel("Unit Price");
			saleLabel = new JLabel("Sale Price");
			stockLabel = new JLabel("Stock");
			//JTextFields
			bookTitle = new JTextField("");
			authorName = new JTextField("");
			publishName = new JTextField("");
			publishName.setToolTipText("Name Of Publisher");
			ISBNCode = new JTextField("");
			ISBNCode.setToolTipText("ISBN Code");
			unit = new JTextField("");
			unit.setToolTipText("(€) Unit Price");
			sale = new JTextField("");
			sale.setToolTipText("(€) Sell Price");
			stock = new JTextField("");
			stock.setToolTipText("Stock Amount");
			submit = new JButton("Submit");
			productTable = new ProductTable();
			productTable.setQuery(QUERY);
			authorName.setColumns(10);
			//add components into 2*8 grid
			gridPanel = new JPanel(new GridLayout(8,2,20,10));
			gridPanel.setBackground(new Color(255, 201, 14)); //yellow
			gridPanel.add(titleLabel);
			gridPanel.add(bookTitle);
			gridPanel.add(authorLabel);
			gridPanel.add(authorName);
			gridPanel.add(publisherLabel);
			gridPanel.add(publishName);
			gridPanel.add(ISBNLabel);
			gridPanel.add(ISBNCode);
			gridPanel.add(unitLabel);
			gridPanel.add(unit);
			gridPanel.add(saleLabel);
			gridPanel.add(sale);
			gridPanel.add(stockLabel);
			gridPanel.add(stock);
			//add listener to submit button
			FormListener form = new FormListener();
			submit.addActionListener(form);
			//add grid panel to borderLayout
			innerPanel = new JPanel(new BorderLayout(0,10));
			innerPanel.setBackground(new Color(255, 201, 14)); //yellow
			innerPanel.add(BorderLayout.NORTH,gridPanel);
			innerPanel.add(BorderLayout.CENTER,submit);
			//add inner panel to flow layout panel then to frame
			outerPanel = new JPanel(new FlowLayout());
			outerPanel.setBackground(new Color(255, 201, 14)); //yellow
			outerPanel.add(innerPanel);//flow layout
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
			//productTable.setQuery(QUERY);
	}
	catch (SQLException sqlException) {
		JOptionPane.showMessageDialog( null, sqlException.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
		productTable.disconnectFromDatabase();
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
			String Unit,Sale,Stock,bookName,bookAuthor,bookPublish,ISBNNum;
			double uniPrice = -1,salPrice = -1;
			int stoc = -1,isbnNum = -1;
			Test testing = new Test();
			bookName = bookTitle.getText();
			bookAuthor = authorName.getText();
			bookPublish = publishName.getText();
			ISBNNum = ISBNCode.getText();
			Unit = unit.getText();
			Sale = sale.getText();
			Stock = stock.getText();
			
			bookName = testing.alphaAndNums(bookName);
			bookAuthor = testing.allAlpha(bookAuthor);
			bookPublish = testing.allAlpha(bookPublish);
		try {
			isbnNum = Integer.parseInt(ISBNNum);
			uniPrice = Double.parseDouble(Unit);
			salPrice = Double.parseDouble(Sale);
			stoc = Integer.parseInt(Stock);
		}
		catch(Exception ee) {
			
		}
		if(bookName.equals("?") || bookAuthor.equals("?") || bookPublish.equals("?") || uniPrice == -1 || salPrice == -1 || stoc == -1 || isbnNum == -1){
			JOptionPane.showMessageDialog(null, "Please Fill In All Details Correctly", "Alert", JOptionPane.ERROR_MESSAGE);
		}
		else {
			try {
				productTable.insertInfo(uniPrice, salPrice, stoc,bookAuthor,bookPublish,isbnNum,bookName);
				//open new window
				AddProduct addProduct = new AddProduct();
				addProduct.setVisible(true);
				//close out current window
				setVisible(false);
				dispose();
			} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}//end of outer if
	}//end of action performed
}//end of FormListener
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
}//end of add product class
