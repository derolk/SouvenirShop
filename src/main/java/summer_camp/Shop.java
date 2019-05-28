package summer_camp;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Shop {

    private Date date = null;


    private double amountt;
    private String currency;
    private String product;
    private Object[][] P;
    private int count;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amountt;
    }

    public void setAmount(double amount) {
        this.amountt = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Shop() {
        P = new Object[100][4];
        count = 0;

    }


    public void purchase(String s, double amountt, String currency, String product) throws ParseException {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-mm-dd");
        date = d.parse(s);
        this.amountt = amountt;
        this.currency = currency;
        this.product = product;
        P[count][0] =(Date) this.date;
        P[count][1] = (Double) this.amountt;
        P[count][2] = this.currency;
        P[count][3] = this.product;
        count++;

    }

    public void all() {
        bubblesort();
        //  System.out.println(Arrays.toString(P));
        int n = count;
        for(int i = 0; i<n;i++)
            for (int j = 0; j<n;j++){

                System.out.println(P[i][j]+" ");
            }


    }

    public void clear() throws ParseException {

        SimpleDateFormat d = new SimpleDateFormat("yyyy-mm-dd");
        Date x = d.parse("2019-04-25");
        // if (getDate() == d.parse("2019-04-25")) ;
        int n = count;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (((Date) P[j][0]).equals(x)) {
                    for (int k = j; k < count - 1; k++) {

                        P[k][i] = P[k + 1][i];

                    }
                    count--;
                    break;

                }
            }
        }
    }

    public void bubblesort() {
        int n = count;
        for (int i = 0; i < n - 1; i++)
            for (int j = i + 1; j < n; j++) {
                if (((Date) P[j][0]).before((Date) P[i][0])) {

                    //swap elements
                    Object temp[] = P[j];
                    P[j] = P[i];
                    P[i] = temp;

                }
            }
    }
    // incase API fails to respond, this methos can be used but it only coverts USD
    public void report() throws ParseException {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-mm-dd");
        String q;
        double sum = 0;
        int n = count;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                double value1 = (Double)P[j][1], value2;
                q = d.format(P[j][0]);
                Pattern p = Pattern.compile("2019");
                Matcher m = p.matcher(q);
                boolean b = m.find();
                if (b == true) {
                    String currency = (String) P[j][2];
                    if (currency.equals("USD")) {

                        value2 = value1 * 26.36;
                        System.out.println(value1 + "USD = " + value2 + " uah. (Conversion rate: 1 USD = 26.36 uah)");
                        sum = +value2;

                    } else {
                        System.out.println("Currency not USD" + "\nConversion rate: 1 USD =  26.36 uah");
                        continue;
                    }
                    ;
                }


            }

        System.out.println("total sum" + sum);

    }



    // method uses api
    public void reportAPI() throws ParseException, IOException {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-mm-dd");
        String q;
        double sum = 0;
        int n = count;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                double value1 = (Double)P[j][1], value2;
                q = d.format(P[j][0]);
                Pattern p = Pattern.compile("2019");
                Matcher m = p.matcher(q);
                boolean b = m.find();
                if (b == true) {
                    String currency = (String) P[j][2];
                    Object res= convertcurrency();
                    System.out.println(currency + "= " + res + " uah. (Conversion rate: 1 USD = 26.36 uah)");
                    sum =+ (Double) res;

                } else {
                    System.out.println("date out of range" + "\n applicable for transactions that happened in 2019");
                    continue;
                }
                ;
            }

        System.out.println("total sum" + sum);
    }





    public Object convertcurrency() throws IOException {
        int n = count;
        String endpoint = "convert";
        String to = "UAH";
        String access_key = "921d6ced8fcf6bffb0712943debd7175";
        String from;
        double amount;
        String base = null;
        double am=0;
        double res = 0;


        for(int i = 0; i<n;i++)
            for (int j = 0; j<n;j++){

                base = (String) P[j][2];
                am= (Double) P[j][1];
                from = base;
                amount = am;
                String url_str = "http://data.fixer.io/api/"+endpoint+"?access_key="+access_key+"&from=" + from + "&to=" + to + "&amount=" + amount;

                URL url= new URL(url_str);
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                request.connect();

                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                JsonObject jsonobj = root.getAsJsonObject();

                String req_result = jsonobj.get("result").getAsString();
                res=Double.parseDouble(req_result);

                for ( i=0; i<n;i++)
                    for(int z=j;z<n;z++){
                        P[z][1] =res;
                    }
            }
        return P;
    }


}
