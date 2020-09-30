import com.earny1996.moneytracker.beans.Account;
import com.earny1996.moneytracker.beans.Transaction;
import com.earny1996.moneytracker.beans.User;
import com.earny1996.moneytracker.daos.UserDAO;

import java.util.List;

public class Main{

    public static void main(String[] args){
        User user = new User("Rene", "Neumann", "rene1996neumann@web.de", "dla8_ia§k4", true);
        System.out.println(user.toString());
        UserDAO userDAO = UserDAO.getInstance();
        System.out.println("Persist User");
        userDAO.persist(user);
        User getUser = userDAO.getByEmail("rene1996neumann@web.de");
        if(getUser == null){
            System.out.println("getUser ist leider NULL");
        } else {
            System.out.println(getUser.toString());
        }

        User user2 = new User("Benjamin", "Fox", "foxiBenji@web.de", "123", true);
        System.out.println(user2.toString());

        userDAO.persist(user2);

        List<User> currentUsers = userDAO.getAll();
        currentUsers.forEach(u -> System.out.println(u.toString()));

       // user1.getDaoLayer().persist();

        /*
        User user2 = new User("Jenny", "Test", "TEST", "Thermomix");
        System.out.println(user2.toString());

        Account bank = new Account("Bank", 2000, "EUR");
        Account kasse = new Account("Kasse", "EUR");

        System.out.println("");
        System.out.println(bank.toString());
        System.out.println(kasse.toString());
        System.out.println("");

        Transaction transaction = new Transaction(user1, bank, kasse, 500.78);
        System.out.println(transaction.toString());

        System.out.println("");
        System.out.println(bank.toString());
        System.out.println(kasse.toString());
        System.out.println("");

        */


    }
}