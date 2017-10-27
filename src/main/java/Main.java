import de.siegmar.fastcsv.reader.CsvReader;

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
        DateFormat dfmt = new SimpleDateFormat("dd.MM.yy");

        //Create jedis instance and connect to redis server
        JedisClient jedisClient = new JedisClient("192.168.162.130", 6379);

        //File with Sample Data (word frequency timestamp)
        jedisClient.readSampleData("/home/reserchr/IdeaProjects/redispraktikum/src/main/java/words.txt");

        //Searchparameters
        String searchword = "hello";
        Date eins = new Date(Word.toDay("0000000000000"));
        Date zwei = new Date(Word.toDay("1436297602000"));


        //execute query
        long timeBegin = System.nanoTime();
        Map<Date, Integer> sum = jedisClient.query(searchword, eins, zwei);
        long timeEnd = System.nanoTime();
        long nanos = timeEnd - timeBegin;

        int wordSum = 0;
        for (int i : sum.values()) {
            wordSum += i;
        }

        // print Result
        System.out.println("word " + searchword + " appears " + wordSum + " times between " + dfmt.format(eins) + " " +
                "and " + dfmt.format(zwei));

        System.out.println("time: " + nanos + " nanoseconds");

        for (Map.Entry entry : sum.entrySet()) {
            System.out.println("Time: " + dfmt.format(entry.getKey())
                    + "  Frequency: " + entry.getValue());
        }
    }
}
