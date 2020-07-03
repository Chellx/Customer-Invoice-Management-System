package data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event. ActionListener ;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane ;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
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
/**
 * 
 * @author Michelle Bolger
 *
 */
public class ProductQuery extends JFrame{
private JTextArea queryArea;
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
//Buttons for add,edit,delete,Query
private JButton add = new JButton("Add Product");
private JButton delete = new JButton("Delete Product");
private JButton edit = new JButton("Edit Product");
private JButton query = new JButton("Query Product");
static final String QUERY = "SELECT * FROM product";
/**
 * Default Constructor	
 */
public ProductQuery() {
		super("Product Query");
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
			queryArea = new JTextArea("SELECT * FROM product", 3, 100 );
			queryArea.setWrapStyleWord( true );
			queryArea.setLineWrap( true );
			JScrollPane scrollPane = new JScrollPane( queryArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			QueryListener queryListener = new QueryListener();
			submit = new JButton("Submit Query");
			submit.addActionListener(queryListener);//add listener to button
			//Create box for scrollPane and submit button
			Box boxNorth = Box.createHorizontalBox();
			boxNorth.add(scrollPane);
			boxNorth.add(submit);
			//set customer table in j table
			JTable resultTable = new JTable((TableModel) productTable);
			//add the first box ,the resultTable and the last box to a border layout 
			innerBorderPanel = new JPanel(new BorderLayout());
			innerBorderPanel.add( boxNorth, BorderLayout.NORTH );
			innerBorderPanel.add( new JScrollPane( resultTable ), BorderLayout.CENTER );
			innerBorderPanel.setBackground(new Color(255, 201, 14)); //yellow
			
			//set tabs and grid panel in borderlayout
			outerBorderPanel = new JPanel();
			outerBorderPanel.setLayout(new BorderLayout());
			outerBorderPanel.add(BorderLayout.NORTH,tabs);
			outerBorderPanel.add(BorderLayout.CENTER,innerBorderPanel);
			outerBorderPanel.add(BorderLayout.SOUTH,flowPanel);
			outerBorderPanel.setBackground(new Color(255, 201, 14)); //yellow
			add(outerBorderPanel);//add to jframe
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
class QueryListener implements ActionListener{
		public void actionPerformed( ActionEvent event ){
			try{
				productTable.setQuery( queryArea.getText());
			}
		catch ( SQLException sqlException ) {
			JOptionPane.showMessageDialog( null, sqlException.getMessage(),"Database Error", JOptionPane.ERROR_MESSAGE );
			try {
				productTable.setQuery(QUERY);
				queryArea.setText(QUERY);
			}
			catch ( SQLException sqlException2 ) {
				JOptionPane.showMessageDialog( null, sqlException2.getMessage(),"Database Error", JOptionPane.ERROR_MESSAGE );
				productTable.disconnectFromDatabase();
				System.exit ( 1 );
				}//end of inner catch
			}//end of outer catch
	}//end of action performs
}// end of Query listener class	
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
}//end of Product Query class
