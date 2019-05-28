package summer_camp;

import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String[]arg) throws ParseException, IOException {
        Shop souvenir = new Shop();
        souvenir.purchase("2019-09-20",50.5,"UAH","Crisps");
        souvenir.purchase("2019-04-25",109.5,"USD","Crisps");
        souvenir.purchase("2016-08-05",20,"AUD","Crisps");
        souvenir.purchase("2014-01-13",30,"ZAR","Crisps");
        souvenir.all();
        souvenir.clear();
        souvenir.reportAPI();
        souvenir.report();



    }

}
