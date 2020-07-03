package data;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing. table .AbstractTableModel;
/**
 * 
 * @author Michelle Bolger
 *
 */
public class ProductTable extends AbstractTableModel{
private Connection connection;
private Statement statement;
private ResultSet resultSet;
private ResultSetMetaData metaData;
private int numberOfRows;
private boolean connectedToDatabase = false;
/**
 *Default Constructor 
 *
 */
	public ProductTable() throws SQLException{
		connectedToDatabase = true;
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoicedatabase", "root", "dbpass");
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	}
//********************************************** INSERT ******************************************
	/**
	 * 
	 * @param unit - Unit Price
	 * @param sale - Sale Price
	 * @param stock - Amount In Stock
	 * @param author - Name Of Author
	 * @param publish - Name Of Publisher
	 * @param isbn - ISBN Code
	 * @param title - Book Title
	 */
	public void insertInfo(double unit,double sale,int stock,String author,String publish,int isbn,String title)throws SQLException {
		PreparedStatement pstat = connection.prepareStatement("INSERT INTO product (unitPrice,salePrice,stock,author,publisher,ISBN,bookTitle) VALUES (?,?,?,?,?,?,?)");
		pstat . setDouble (1, unit);
		pstat . setDouble (2, sale);
		pstat . setInt (3, stock);
		pstat . setString (4, author);
		pstat . setString (5, publish);
		pstat . setInt (6, isbn);
		pstat . setString (7, title);
		pstat .executeUpdate();
	}
	//********************************************** RETRIEVE QUERY ******************************************
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
	 * @param query - Allows Query to be sent to Database
	 */
	public void setQuery( String query ) throws SQLException, IllegalStateException {
		if (connectedToDatabase == false) throw new IllegalStateException ("Not Connected to Database");
			resultSet = statement.executeQuery( query );
			metaData = resultSet.getMetaData();
			resultSet . last () ;
			numberOfRows = resultSet.getRow();
			fireTableStructureChanged();
		}
	//get product id
	/**
	 * 	
	 * @param array - Product ID's from Database and held in array
	 * @param j - Position of start element in array
	 * @return - Amount of Product ID's
	 */
	public int getProductId(String [] array,int j) throws SQLException {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoicedatabase", "root", "dbpass");
			resultSet = statement.executeQuery("Select idproduct From invoicedatabase.product");
			while(resultSet.next()) {
			
				array[j] = resultSet.getString("idproduct");
				j++;
			}
			return j;
		}
//	//********************************************** UPDATE ******************************************
	/**
	 * 
	 * @param title - Book title to be updated
	 * @param id - Selects which book title to update
	 */
	public void updateTitle(String title,int id )throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE product SET bookTitle= ? WHERE idproduct= ?");
		pstat2.setString (1, title);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	/**
	 * 
	 * @param author - Author name to be updated
	 * @param id - Selects which author name to update
	 */
	public void updateAuthor(String author,int id )throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE product SET author=? WHERE idproduct=?");
		pstat2.setString (1, author);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	/**
	 *  
	 * @param publish - Publisher name to be updated
	 * @param id - Selects which publisher to update
	 */
	public void updatePublisher(String publish,int id )throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE product SET publisher= ? WHERE idproduct= ?");
		pstat2.setString (1, publish);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}//when statement not working make new pstat variable
	/**
	 * 
	 * @param isbn - ISBN Code to be updated
	 * @param id - Selects which ISBN to update
	 */
	public void updateISBN(int isbn,int id )throws SQLException {
		PreparedStatement pstat4 = connection.prepareStatement("UPDATE product SET ISBN = ? WHERE idproduct = ?");
		pstat4.setInt (1, isbn);
		pstat4.setInt (2, id);
		pstat4.executeUpdate();
	}
	/**
	 * 
	 * @param unit - Unit price to be updated
	 * @param id - Selects which Unit price to update
	 */
	public void updateUPrice(double unit,int id)throws SQLException {
		PreparedStatement pstat4 = connection.prepareStatement("UPDATE invoicedatabase.product SET unitPrice= ? WHERE idproduct = ?");
		pstat4.setDouble (1, unit);
		pstat4.setInt (2, id);
		pstat4.executeUpdate();
	}
	/**
	 * 
	 * @param sale - Sale price to be updated
	 * @param id -  Selects which Sale price to update
	 */
	public void updateSaleP(double sale,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE product SET salePrice= ? WHERE idproduct = ?");
		pstat2.setDouble (1, sale);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	/**
	 * 
	 * @param stock - Stock amount to be updated
	 * @param id - Selects which Stock amount to update
	 */
	public void updateStock(int stock,int id)throws SQLException {
		PreparedStatement pstat4 = connection.prepareStatement("UPDATE product SET stock= ? WHERE idproduct = ?");
		pstat4.setInt (1, stock);
		pstat4.setInt (2, id);
		pstat4.executeUpdate();
	}
	//********************************************** DELETE ******************************************
	/**
	 * 
	 * @param value - ID used to delete entry
	 */
	public void deleteInfo(int value)throws SQLException {
		PreparedStatement pstat3 = connection.prepareStatement("DELETE FROM product WHERE idproduct = ?");
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
		}//end of if
	}//end of disconnect method
}//end of product table
