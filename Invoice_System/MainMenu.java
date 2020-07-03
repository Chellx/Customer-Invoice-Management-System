package data;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
/**
 * 
 * @author Michelle Bolger
 *
 */
public class MainMenu extends JFrame{
private JTabbedPane tabs = new JTabbedPane();
private JPanel outerPanel = new JPanel();
private JLabel label1 = new JLabel();	
private JLabel label2 = new JLabel();
private JLabel label3 = new JLabel();
private JLabel label4 = new JLabel();
private JLabel label5 = new JLabel();
private JLabel logo;
 	public MainMenu() {
	 	super("Main Menu");
 	//set tabs in frame
 		TabListener tabListener = new TabListener();
 		tabs.add("Main Menu", label1);
		tabs.add("Customer Table Menu",label2);//make tab for customer table
		tabs.add("Invoice Table Menu",label3);
		tabs.add("Product Table Menu",label4);
		tabs.add("Order Table Menu",label5);
		tabs.addChangeListener(tabListener);
	//put JTabbedpane in north BorderLayout and change jtabbed pane colour
		UIManager.put("TabbedPane.contentAreaColor", new Color(153, 204, 255, 100));
		outerPanel.setLayout(new BorderLayout(0,50));
		outerPanel.add(BorderLayout.NORTH,tabs);
	//put picture in center of BorderLayout
		ImageIcon picture = new ImageIcon("C:\\Users\\shell\\Desktop\\Project-Repo\\Database\\src\\data\\DBLOGO.png");
		logo = new JLabel(picture);
		outerPanel.add(BorderLayout.CENTER,logo);
	//change Panel color and add panel to frame
		outerPanel.setBackground(new Color(153, 204, 255)); //light blue
		add(outerPanel);
		
		pack();//stops layout manager only with swing
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(400, 50);
		setSize(700, 600);
		setVisible(true);
	}
 class TabListener implements ChangeListener{

	public void stateChanged(ChangeEvent e) {
		int pickTab = tabs.getSelectedIndex();//selects tab index
		if(pickTab == 0) {//if Main menu is selected
			
		}
		else if(pickTab == 1) {//if customer table is selected
			//open new window
			CustomerMenu customerMenu = new CustomerMenu();
			customerMenu.setVisible(true);
			//close out current window
			setVisible(false);
			dispose();
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
}//end of MainMenu class
