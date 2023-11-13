import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author ljl
 * @create 2023-10-29-21:54
 */
public class Hello {

    public static void main(String[] args) {
        Date date = new Date();
//        System.out.println(TimeZone.getDefault());
        System.out.println(date.toString());

//        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
//        System.out.println(date);

        System.out.println(LocalDateTime.now());
//
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"));
//        System.out.println(date);


//        TimeZone.setDefault();
//        System.out.println(aDefault);
//
//        System.out.println(aDefault);

    }
}
