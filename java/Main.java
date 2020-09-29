import com.earny1996.moneytracker.beans.Account;
import com.earny1996.moneytracker.beans.Transaction;
import com.earny1996.moneytracker.beans.User;
import com.earny1996.moneytracker.daos.UserDAO;

public class Main{

    public static void main(String[] args){
        User user1 = new User("Rene", "Neumann", "rene1996neumann@web.de", "dla8_ia§k4", true);
        UserDAO userDAO = new UserDAO();
        System.out.println("Persist User");
        //userDAO.persist(user1);
        User getUser = userDAO.getByEmail("rene1996neumann@web.de");
        if(getUser == null){
            System.out.println("getUser ist leider NULL");
        } else {
            userDAO.delete(getUser);
        }

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