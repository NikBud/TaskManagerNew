import junit.framework.TestCase;

import java.util.ArrayList;

public class UserTest extends TestCase {

    ArrayList<String> names;
    ArrayList<Task> tasks;
    User danil;
    User alexei;


    @Override
    protected void setUp() throws Exception
    {
        System.out.println("\n\nStart testing!\n\n");
        tasks = new ArrayList<>();
        names =  new ArrayList<>();
        alexei =  new User("Pupkov", "Alexei", "AlPup", tasks);
        danil = new User("Socolov", "Danil", "socdan", tasks);
        tasks.add(new Task("Сделать тест для стажировки", "Внимательно прочитать каждую из предложенных на тесте задач, постараться выполнить их наиболее оптимальным методом"));
        tasks.add(new Task("Погулять с девушкой", "Забрать ее с дома, сводить в кино и накормить"));
        names.add("socdan");
        names.add("AlPup");
    }

    public void testUser()
    {
        User alexandr = new User("Popov", "Alexandr", "AlPup", tasks);
        User oleg = new User("Sidorov", "Oleg", "OlSi", tasks);
        int actual = User.getUsers().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    public void testAddTask()
    {
        User.addTask("socdan", "Погулять с девушкой", "qwerty");
        try {
            int actual = User.getUser("socdan").getTaskSize();
            int expected = 2;
            assertEquals(expected, actual);
        }
        catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void testAddTaskToGroup()
    {
        User.addTaskToGroup(names, new Task("Покушать","Подогреть еду и только потом кушать"));
        User.addTaskToGroup(names, new Task("Погулять с девушкой", "qwerty"));
        try {
            int actual = User.getUser("socdan").getTaskSize();
            int expected = 3;
            assertEquals(expected, actual);
        }
        catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void testAddTaskNonStatic(){
        User person1 = new User("Budeanski", "Nikita", "nikbud", tasks);
        person1.addTask("Подстричь кота", "Найти хорошую клинику недалеко от дома и пойти туда");
        try {
            int actual = User.getUser("AlPup").getTaskSize();
            int expected = 3;
            assertEquals(expected, actual);
        }
        catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    protected void tearDown() throws Exception
    {
        tasks = null;
        names = null;
        alexei = null;
        danil = null;
        System.out.println("\n\nThe end of the testing!\n\n");
    }
}
