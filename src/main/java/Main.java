import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by reserchr on 10.10.17.
 */
public class Main {
    public static void main(String[] args) {
        //Set format for Dates
        DateFormat dfmt = new SimpleDateFormat( "dd.MM.yy hh:mm:ss");

        //Create jedis instance and connect to redis server
        JedisClient jedisClient = new JedisClient("192.168.162.130", 6379);

        //File with Sample Data (word frequency timestamp)
        jedisClient.readSampleData("/home/reserchr/IdeaProjects/redispraktikum/src/main/java/words.txt");

        //Searchparameters
        String searchword = "amendment";
        Date eins = new Date(Long.parseLong("0000000000000"));
        Date zwei = new Date(Long.parseLong("0000000000000"));


        long begin = System.nanoTime();
        //execute query
        Map<Date, Integer> sum = jedisClient.query(searchword, eins, zwei);
        long end = System.nanoTime();
        long nanos = end - begin;

        //calc wordfrequency
        int wordSum = 0;
        for(Map.Entry<Date, Integer> entry : sum.entrySet()){
            wordSum += entry.getValue();
        }

        // print Result
        System.out.println("word " + searchword + " appears " + wordSum + " times between " + dfmt.format(eins) + " " +
                "and " + dfmt.format(zwei));

        System.out.println("time in nanoseconds for executing the query: " + nanos);

    }
}
