import com.earny1996.moneytracker.beans.Account;
import com.earny1996.moneytracker.beans.Transaction;
import com.earny1996.moneytracker.beans.User;

public class Main{

    public static void main(String[] args){
        User user1 = new User("Rene", "Neumann", "rene1996neumann@web.de", "dla8_ia§k4");
        System.out.println(user1.toString());
        user1.getDaoLayer().persist();

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


    }
}