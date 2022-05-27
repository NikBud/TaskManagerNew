import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class User implements Serializable {
    private static ArrayList<User> users = new ArrayList<>();

    private String firstName;
    private String lastName;
    private String userName;
    private ArrayList<Task> tasks;



    public User(String fn, String ln, String un){
        if(!usersContainsChecker(users, un)) {
            firstName = fn;
            lastName = ln;
            userName = un;
            users.add(this);
        }
        else {
            System.out.println("User is NOT created!!!     List of users cannot contain the same usernames!");
        }
    }

    public User(String fn, String ln, String un, ArrayList<Task> ts){
        if(!usersContainsChecker(users, un)) {
            firstName = fn;
            lastName = ln;
            userName = un;
            tasks = ts;
            users.add(this);
        }
        else {
            System.out.println("User is NOT created!!!     List of users cannot contain the same usernames!");
        }
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public int getTaskSize() {
        return tasks.size();
    }

    public static User getUser(String userName){
        for(User u : users){
           if(u.userName.equals(userName)){
               return u;
           }
        }
        throw new NullPointerException("\n\n\nПРИ ДОБАВЛЕНИИ ИЛИ ОТОБРАЖЕНИИ ПРОИЗОШЛА ОШИБКА!!!!     Мы не нашли такого или таких userName в системе :(     ERROR!!!!\n\n\n");
    }


    public static boolean usersContainsChecker(ArrayList<User> us, String uName){
        for(User user : us){
            if (user.userName.equals(uName))
                return true;
        }
        return false;
    }


    public boolean taskChecker(Task task) {
        for(Task t : this.tasks) {
            if(task.getTitle().equals(t.getTitle())) {
                return false;
            }
        }
        return true;
    }


    public static Task addTask(String userName, String titleOfTask, String descOfTask) {
        Task newTask = new Task(titleOfTask, descOfTask);

        try {
            User user = getUser(userName);
            if (user.userName.equals(userName)) {
                if (user.taskChecker(newTask))
                    user.tasks.add(newTask);
                else
                    System.out.println("\n***************** Такое задание уже есть в списке! *******************\n");
            }
        }
        catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
        return newTask;
    }


    public static void addTaskToGroup(ArrayList<String> group, Task task) {
        for(String name : group) {
            try {
                User user = getUser(name);
                if (user.taskChecker(task))
                    user.tasks.add(task);
                else
                    System.out.println("\n***************** Такое задание уже есть в списке! *******************\n");
            }
            catch (NullPointerException ex){
                System.out.println(ex.getMessage());
            }
        }
    }


    public Task addTask(String titleOfTask, String descOfTask) {
        Task newTask = new Task(titleOfTask, descOfTask);

        if(this.taskChecker(newTask))
            this.tasks.add(newTask);
        else
            System.out.println("\n***************** Такое задание уже есть в списке! *******************\n");

        return newTask;
    }


    public static ArrayList<Task> showUserTasks(String userName) {
        ArrayList<Task> list = new ArrayList<>();
        try {
            User u = getUser(userName);
            for (int i = 0; i < u.tasks.size(); i++) {
                System.out.println(i + 1 + ".) " + u.tasks.get(i).getTitle() + "  :  " + u.tasks.get(i).getDescription());
            }
            list = u.tasks;
        }
        catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
        return list;
    }


    public ArrayList<Task> showUserTasks() {
        for(int i = 0; i < this.tasks.size(); i++) {
            System.out.println(i+1 + ".) " + this.tasks.get(i).getTitle() + "  :  " + this.tasks.get(i).getDescription());
        }
        return this.tasks;
    }

    public void showUser() {
        System.out.println("*FIRST NAME*" + "   " + "*LAST NAME*" + "   " + "*USERNAME*" + "   " + "*COUNT OF TASKS*");
        System.out.println(this.firstName + "       " + this.lastName + "        " + this.userName + "       (" + this.tasks.size() + " current tasks)");
    }


    public static void showAllUsers() {
        System.out.println("*FIRST NAME*" + "   " + "*LAST NAME*" + "   " + "*USERNAME*" + "   " + "*COUNT OF TASKS*\n");
        for(int i = 0; i < users.size(); i++){
            System.out.println(i+1 + ".) " + users.get(i).firstName + "   :   " + users.get(i).lastName + "   :   " + users.get(i).userName + "   :    " + users.get(i).tasks.size() + " current tasks");
        }
    }


    public int compareTo(User u) {
        if(this.userName.equals(u.userName)){
            return 1;
        }
        return 0;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
