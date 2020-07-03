package data;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing. table .AbstractTableModel;
public class CustomerTable extends AbstractTableModel{
private Connection connection;
private Statement statement;
private ResultSet resultSet;
private ResultSetMetaData metaData;
private int numberOfRows;
private boolean connectedToDatabase = false;
	
	public CustomerTable() throws SQLException{
		connectedToDatabase = true;
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoicedatabase", "root", "dbpass");
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	}
	///////////////////////////////////////////////////////////////////////////////////////Insert
	public void insertInfo(String fName,String lName,String street,String townCity,String county,String country,String email,String phone)throws SQLException {
		PreparedStatement pstat = connection.prepareStatement("INSERT INTO customer (FirstName,LastName,Street,TownCity,County,Country,email,Phone) VALUES (?,?,?,?,?,?,?,?)");
		pstat . setString (1, fName);
		pstat . setString (2, lName);
		pstat . setString (3, street);
		pstat . setString (4, townCity);
		pstat . setString (5, county);
		pstat . setString (6, country);
		pstat . setString (7, email);
		pstat . setString (8, phone);
		pstat .executeUpdate();
	}
	///////////////////////////////////////////////////////////////////////////////////////////////Retrieve query
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
	
	public void setQuery( String query ) throws SQLException, IllegalStateException {
		if (connectedToDatabase == false) throw new IllegalStateException ("Not Connected to Database");
		resultSet = statement.executeQuery( query );
		metaData = resultSet.getMetaData();
		resultSet . last () ;
		numberOfRows = resultSet.getRow();
		fireTableStructureChanged();
		}
	///for getting a array of ids
	public int getCustomerId(String [] array,int j) throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoicedatabase", "root", "dbpass");
		resultSet = statement.executeQuery("Select CustomerID From Customer");
		while(resultSet.next()) {
			array[j] = resultSet.getString("CustomerID");
			j++;
		}
		return j;
	}
//////////////////////////////////////////////////////////////////////////update
	public void updateFName(String fName,int id )throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE customer SET FirstName= ?WHERE CustomerID = ?");
		pstat2.setString (1, fName);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	public void updateLName(String lName,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE customer SET LastName= ?WHERE CustomerID = ?");
		pstat2.setString (1, lName);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	public void updateStreet(String newStreet,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE customer SET Street= ?WHERE CustomerID = ?");
		pstat2.setString (1, newStreet);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	public void updateTownCity(String newTC,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE customer SET TownCity= ?WHERE CustomerID = ?");
		pstat2.setString (1, newTC);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	public void updateCounty(String newCount,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE customer SET County= ?WHERE CustomerID = ?");
		pstat2.setString (1, newCount);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	public void updateCountry(String newCountry,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE customer SET Country= ?WHERE CustomerID = ?");
		pstat2.setString (1, newCountry);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	public void updateEmail(String newEmail,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE customer SET email= ?WHERE CustomerID = ?");
		pstat2.setString (1, newEmail);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	public void updatePhone(String newPhone,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE customer SET Phone= ?WHERE CustomerID = ?");
		pstat2.setString (1, newPhone);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	///////////////////////////////////////////////////////////////////////////delete
	public void deleteInfo(int value)throws SQLException {
		PreparedStatement pstat3 = connection.prepareStatement("DELETE FROM customer WHERE CustomerID = ?");
		pstat3.setInt (1, value);
		pstat3.executeUpdate();
	}
/////////////////////////////////////////////////////////////////////////disconnect		
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