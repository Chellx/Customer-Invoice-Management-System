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
public class OrderQuery extends JFrame{
private JTextArea queryArea;
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
//Buttons for add,edit,delete,Query
private JButton add = new JButton("Add Order");
private JButton delete = new JButton("Delete Order");
private JButton edit = new JButton("Edit Order");
private JButton query = new JButton("Query Order");
static final String QUERY = "SELECT * FROM invoicedatabase.order";
	public OrderQuery() {
		super("Order Query");
		try {
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
			orderTable = new OrderTable();
			orderTable.setQuery(QUERY);
			queryArea = new JTextArea("SELECT * FROM invoicedatabase.order", 3, 100 );
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
			JTable resultTable = new JTable((TableModel) orderTable);
			//add the first box ,the resultTable and the last box to a border layout 
			innerBorderPanel = new JPanel(new BorderLayout());
			innerBorderPanel.add( boxNorth, BorderLayout.NORTH );
			innerBorderPanel.add( new JScrollPane( resultTable ), BorderLayout.CENTER );
			innerBorderPanel.setBackground(new Color(200, 191, 231)); //purple
			
			//set tabs and grid panel in borderlayout
			outerBorderPanel = new JPanel();
			outerBorderPanel.setLayout(new BorderLayout());
			outerBorderPanel.add(BorderLayout.NORTH,tabs);
			outerBorderPanel.add(BorderLayout.CENTER,innerBorderPanel);
			outerBorderPanel.add(BorderLayout.SOUTH,flowPanel);
			outerBorderPanel.setBackground(new Color(200, 191, 231)); //purple
			add(outerBorderPanel);//add to jframe
		} //end of try
		catch (SQLException sqlException) {
			JOptionPane.showMessageDialog( null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
			orderTable.disconnectFromDatabase();
			System.exit (1);
		}//end of catch
		pack();//stops layout manager
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(310, 20);
		setSize(900, 700);
		setVisible(true);
	}
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
class QueryListener implements ActionListener{

	public void actionPerformed( ActionEvent event ){
		try{
			orderTable.setQuery( queryArea.getText());
		}
	catch ( SQLException sqlException ) {
		JOptionPane.showMessageDialog( null, sqlException.getMessage(),"Database error", JOptionPane.ERROR_MESSAGE );
		try {
			orderTable.setQuery(QUERY);
			queryArea.setText(QUERY);
		}
		catch ( SQLException sqlException2 ) {
			JOptionPane.showMessageDialog( null, sqlException2.getMessage(),"Database error", JOptionPane.ERROR_MESSAGE );
			orderTable.disconnectFromDatabase();
			System.exit ( 1 );
			}//end of inner catch
		}//end of outer catch
	}//end of action performs
}// end of Query listener class
}//end of Order Query class
