package DatabaseWorkers;

import utility.Console;

import java.sql.*;

public class DataBaseHandler {
    // Table names
    public static final String PERSONS_TABLE = "persons_table";
    public static final String USER_TABLE = "my_users";
    // persons_table column names
    public static final String PERSONS_TABLE_ID_COLUMN = "id";
    public static final String PERSONS_TABLE_NAME_COLUMN = "name";
    public static final String PERSONS_TABLE_COORDINATES_X_COLUMN = "coordinates_x";
    public static final String PERSONS_TABLE_COORDINATES_Y_COLUMN = "coordinates_y";
    public static final String PERSONS_TABLE_HEIGHT_COLUMN = "height";
    public static final String PERSONS_TABLE_CREATION_DATE_COLUMN = "creation_date";
    public static final String PERSONS_TABLE_EYE_COLOR_COLUMN = "eye_color";
    public static final String PERSONS_TABLE_HAIR_COLOR_COLUMN = "hair_color";
    public static final String PERSONS_TABLE_NATIONALITY_COLUMN = "nationality";
    public static final String PERSONS_TABLE_LOCATION_X_COLUMN = "location_x";
    public static final String PERSONS_TABLE_LOCATION_Y_COLUMN = "location_y";
    public static final String PERSONS_TABLE_LOCATION_Z_COLUMN = "location_z";
    public static final String PERSONS_TABLE_LOCATION_NAME_COLUMN = "location_name";
    public static final String PERSONS_TABLE_USER_ID_COLUMN = "user_id";
    // USER_TABLE column names
    public static final String USER_TABLE_ID_COLUMN = "id";
    public static final String USER_TABLE_USERNAME_COLUMN = "username";
    public static final String USER_TABLE_PASSWORD_COLUMN = "password";

    private final String url;
    private final String user;
    private final String password;
    private Connection connection;

    public DataBaseHandler(String databaseHost, int databasePort, String user, String password, String databaseName) {
        this.url = "jdbc:postgresql://" + databaseHost + ":"+databasePort+"/"+databaseName;
        this.user = user;
        this.password = password;
        connectToDataBase();
    }

    /**
     * A class for connect to database.
     */
    private void connectToDataBase() {
        try {
            String JDBC_DRIVER = "org.postgresql.Driver";
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при подключении к базе данных!");
        } catch (ClassNotFoundException exception) {
            System.out.println("Драйвер управления базой дынных не найден!");
        }
    }

    /**
     * @param sqlStatement SQL statement to be prepared.
     * @param generateKeys Is keys needed to be generated.
     * @return Prepared statement.
     * @throws SQLException When there's exception inside.
     */
    public PreparedStatement getPreparedStatement(String sqlStatement, boolean generateKeys) throws SQLException {
        PreparedStatement preparedStatement;
        try {
            if (connection == null) throw new SQLException();
            int autoGeneratedKeys = generateKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS;
            preparedStatement = connection.prepareStatement(sqlStatement, autoGeneratedKeys);
            return preparedStatement;
        } catch (SQLException exception) {
            if (connection == null) Console.printerror("Соединение с базой данных не установлено!");
            throw new SQLException(exception);
        }
    }

    /**
     * Close prepared statement.
     *
     * @param sqlStatement SQL statement to be closed.
     */
    public void closePreparedStatement(PreparedStatement sqlStatement) {
        if (sqlStatement == null) return;
        try {
            sqlStatement.close();
        } catch (SQLException exception) {
            Console.printerror("Кажется, возникла ошибка закрытия запроса:(");
        }
    }

    /**
     * Close connection to database.
     */
    public void closeConnection() {
        if (connection == null) return;
        try {
            connection.close();
            Console.printerror("Соединение с базой данных разорвано.");
        } catch (SQLException exception) {
            Console.printerror("Произошла ошибка при разрыве соединения с базой данных!");
        }
    }

    /**
     * Set commit mode of database.
     */
    public void setCommitMode() {
        try {
            if (connection == null) throw new SQLException();
            connection.setAutoCommit(false);
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при установлении режима транзакции базы данных!");
        }
    }

    /**
     * Set normal mode of database.
     */
    public void setNormalMode() {
        try {
            if (connection == null) throw new SQLException();
            connection.setAutoCommit(true);
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при установлении нормального режима базы данных!");
        }
    }

    /**
     * Commit database status.
     */
    public void commit() {
        try {
            if (connection == null) throw new SQLException();
            connection.commit();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при подтверждении нового состояния базы данных!");
        }
    }

    /**
     * Roll back database status.
     */
    public void rollback() {
        try {
            if (connection == null) throw new SQLException();
            connection.rollback();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при возврате исходного состояния базы данных!");
        }
    }

    /**
     * Set save point of database.
     */
    public void setSavepoint() {
        try {
            if (connection == null) throw new SQLException();
            connection.setSavepoint();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при сохранении состояния базы данных!");
        }
    }
}
