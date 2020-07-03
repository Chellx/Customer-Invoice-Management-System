package data;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.table.AbstractTableModel;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
/**
 * 
 * @author Michelle Bolger
 *
 */
public class OrderTable extends AbstractTableModel{
private Connection connection;
private Statement statement;
private ResultSet resultSet;
private ResultSetMetaData metaData;
private int numberOfRows;
private boolean connectedToDatabase = false;
	/**
	 * Default Constructor
	 */
	public OrderTable() throws SQLException{
		connectedToDatabase = true;
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoicedatabase", "root", "dbpass");
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	}//end of constructor
	
//************************************INSERT********************************************
	/**
	 * 
	 * @param oDate - Order Date
	 * @param sDate - Shipping Date
	 * @param pay - Payment Received
	 * @param price - Order Price
	 * @param custId - Customer ID
	 */
	public void insertInfo(String oDate,String sDate,String pay,double price,int custId)throws SQLException {
		PreparedStatement pstat = connection.prepareStatement("INSERT INTO invoicedatabase.order (orderDate,shipDate,paymentReceived,price,customer_CustomerID) VALUES (?,?,?,?,?)");
		pstat . setString (1, oDate);
		pstat . setString (2, sDate);
		pstat . setString (3, pay);
		pstat . setDouble (4, price);
		pstat . setDouble (5, custId);
		pstat .executeUpdate();
	}
//************************************************RETRIEVE QUERY**************************************************
	public Class getColumnClass( int column ) throws IllegalStateException {
		if ( connectedToDatabase == false ) throw new IllegalStateException ( "Not Connected to Database");
		try{
			String className = metaData.getColumnClassName( column + 1 );// return Class object that represents className
			return Class . forName( className );
		}
		catch ( Exception exception ) {
			exception . printStackTrace () ;
		}
			return Object. class ; // if problems occur above, assume type Object
		}
	
	public int getColumnCount() throws IllegalStateException {
		if (!connectedToDatabase) throw new IllegalStateException ( "Not Connected to Database");
		try {
			return metaData.getColumnCount();
		}
		catch ( SQLException sqlException ){
			sqlException . printStackTrace () ;
		}
		return 0; // if problems occur above, return 0 for number of columns
	}
	
	public String getColumnName( int column ) throws IllegalStateException {
		if (connectedToDatabase == false ) throw new IllegalStateException ("Not Connected to Database");
		try {
			return metaData.getColumnName( column + 1 );
		}
			catch ( SQLException sqlException ){
			sqlException . printStackTrace () ;
		}
		return ""; // if problems, return empty string for column name
	}
	public int getRowCount() throws IllegalStateException {
		if ( !connectedToDatabase ) throw new IllegalStateException ("Not Connected to Database");
			return numberOfRows;
	}	
	
	public Object getValueAt( int row, int column )throws IllegalStateException {
		if ( !connectedToDatabase ) throw new IllegalStateException ("Not Connected to Database");
		try {
			resultSet . absolute ( row + 1 );
			return resultSet .getObject( column + 1 );
		}
			catch ( SQLException sqlException ){
			sqlException . printStackTrace () ;
		}
		return ""; // if problems, return empty string object
	}
	
	/**
	 * 
	 * @param query - Allows query to be sent to database
	 */
	public void setQuery( String query ) throws SQLException, IllegalStateException {
		if (connectedToDatabase == false) throw new IllegalStateException ("Not Connected to Database");
			resultSet = statement.executeQuery( query );
			metaData = resultSet.getMetaData();
			resultSet . last () ;
			numberOfRows = resultSet.getRow();
			fireTableStructureChanged();
		}
	//get customer id
	public int getCustomerId(String [] array,int j) throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoicedatabase", "root", "dbpass");
		resultSet = statement.executeQuery("Select CustomerID From invoicedatabase.Customer");
		while(resultSet.next()) {
		
			array[j] = resultSet.getString("CustomerID");
			j++;
		}
		return j;
	}
	//get order id
	/**
	 * 
	 * @param array - Order ID'S from database held in array
	 * @param j - Position of start element in array
	 * @return - Amount of Order ID's
	 */
	public int getOrderId(String [] array,int j) throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoicedatabase", "root", "dbpass");
		resultSet = statement.executeQuery("Select idorder From invoicedatabase.order");
		while(resultSet.next()) {
		
			array[j] = resultSet.getString("idorder");
			j++;
		}
		return j;
	}
//***************************************UPDATE*********************************************************************
	/**
	 * 
	 * @param oDate - Order date to be updated
	 * @param id - Selects which order date to update
	 * @throws SQLException
	 */
	public void updateODate(String oDate,int id )throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE invoicedatabase.order SET orderDate= ?WHERE idorder = ?");
		pstat2.setString (1, oDate);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	/**
	 * 
	 * @param sDate - Shipping date to be updated
	 * @param id - Selects which shipping date to update
	 */
	public void updatesDate(String sDate,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE invoicedatabase.order SET shipDate= ?WHERE idorder = ?");
		pstat2.setString (1, sDate);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	/**
	 * 
	 * @param pay - Payment received to be updated 
	 * @param id - Selects which payment received to update
	 */
	public void updatePay(String pay,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE invoicedatabase.order SET paymentReceived = ? WHERE idorder = ?");
		pstat2.setString (1, pay);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	/**
	 * 
	 * @param qty - Quantity to be updated
	 * @param id - Selects which quantity to update
	 */
	public void updateQuantity(int qty,int id)throws SQLException {
		PreparedStatement pstat4 = connection.prepareStatement("UPDATE invoicedatabase.order SET quantity = ? WHERE idorder = ?");
		pstat4.setInt (1, qty);
		pstat4.setInt (2, id);
		pstat4.executeUpdate();
	}
	/**
	 * 
	 * @param price - Price to be updated
	 * @param id - Selects which price to update
	 */
	public void updatePrice(double price,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE invoicedatabase.order SET price= ?WHERE idorder = ?");
		pstat2.setDouble (1, price);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	//********************************************** DELETE ******************************************
	/**
	 * 
	 * @param value - ID used to delete entry
	 */
	public void deleteInfo(int value)throws SQLException {
		PreparedStatement pstat3 = connection.prepareStatement("DELETE FROM invoicedatabase.order WHERE idorder = ?");
		pstat3.setInt (1, value);
		pstat3.executeUpdate();
	}
	//********************************************** DISCONNECT ******************************************
	public void disconnectFromDatabase() {
		if(connectedToDatabase == true) {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			}//end of try
				catch(Exception exception){
				exception.printStackTrace();
			}//end of catch
			finally {
				connectedToDatabase = false;
			}
		}
	}
}

