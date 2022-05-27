import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static String url = "jdbc:mysql://localhost/users";
    public static String userDB = "root";
    public static String pas = "password";

    public static void main(String[] args) {

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Сделать тест для стажировки", "Внимательно прочитать каждую из предложенных на тесте задач, постараться выполнить их наиболее оптимальным методом"));
        tasks.add(new Task("Погулять с девушкой", "Забрать ее с дома, сводить в кино и накормить"));

        User person = new User("Socolov", "Danil", "socdan", tasks);
        User person1 = new User("Pupkov", "Alexei", "AlPup", tasks);
        User person2 = new User("Budeanski", "Nikita", "nikbud", tasks);

        User.showAllUsers();

        User.addTask("jaqlean", "qwerty", "q");

        User.showAllUsers();


        addUserToDataBase(person2);

        displayDataFromDataBase("user", "users");




        //////////////////////////////         СЕРИАЛИЗАЦИЯ СПИСКА С ПОЛЬЗОВАТЕЛЯМИ        ////////////////////////////////////
        //ArrayList<User> users = new ArrayList<>();
//        users.add(person1);
//        users.add(person);
//        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat")))
//        {
//            oos.writeObject(users);
//            System.out.println("File has been written");
//        }
//        catch(Exception ex){
//            ex.printStackTrace();
//        }





        //////////////////////////////         ДЕСЕРИАЛИЗАЦИЯ СПИСКА С ПОЛЬЗОВАТЕЛЯМИ        ////////////////////////////////////
//        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.dat")))
//        {
//            users = (ArrayList<User>)ois.readObject();
//        }
//        catch(Exception ex){
//            ex.printStackTrace();
//        }



    }

    public static void addUserToDataBase(User user) {
        if(checkUnicality(user)) {
            try {
                Connection connection = DriverManager.getConnection(url, userDB, pas);
                Statement statement = connection.createStatement();

                String sqlUser = "INSERT INTO user (firstName, lastName, userName, countOfTasks)"
                        + " values (?, ?, ?, ?)";
                String sqlTask = "INSERT INTO task (id_user, title, description)"
                        + " values (?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlUser);
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getUserName());
                preparedStatement.setString(4, Integer.toString(user.getTasks().size()));
                preparedStatement.execute();
                System.out.println("Пользователь добавлен в БД!");

                ResultSet set = statement.executeQuery("SELECT MAX(id_user) FROM user");
                set.next();
                int n = Integer.parseInt(set.getString("MAX(id_user)"));


                for (int i = 0; i < user.getTasks().size(); i++) {
                    PreparedStatement preparedStatement1 = connection.prepareStatement(sqlTask);
                    preparedStatement1.setString(1, Integer.toString(n));
                    preparedStatement1.setString(2, user.getTasks().get(i).getTitle());
                    preparedStatement1.setString(3, user.getTasks().get(i).getDescription());
                    preparedStatement1.execute();
                }
                System.out.println("Задачи пользователя добавлены в БД!");
                connection.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else {
            System.out.println("Такой пользователь уже существкет в БД!");
        }
    }

    public static boolean checkUnicality(User user){
        try {
            Connection connection =  DriverManager.getConnection(url, userDB, pas);
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT userName from user");
            while(set.next()){
                String userName = set.getString("userName");
                if(userName.equals(user.getUserName())){
                    return false;
                }
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return true;
    }

    public static void displayDataFromDataBase(String table, String database) {
        String localUrl = "jdbc:mysql://localhost/" + database;
        Connection connection = null;
        try {
            String query = "SELECT * FROM " + table;
            connection = DriverManager.getConnection(localUrl, userDB, pas);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for(int i = 1 ; i <= columnsNumber; i++)
                {
                    System.out.print(rs.getString(i) + "                    ");
                }
                System.out.println();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
