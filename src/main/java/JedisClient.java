import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by reserchr on 09.10.17.
 */
public class JedisClient {


    private Jedis jedis;
    private Pipeline pipeline;

    public JedisClient(String ip, int port) {
        jedis = new Jedis(ip, port);
        pipeline = jedis.pipelined();
    }

    private void readSampleData(String filePath) {
        String thisline;

        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            while ((thisline = in.readLine()) != null) {
                writeToJedis(thisline);
            }
            //Response<Set<String>> sose = pipeline.zrange("woerter", 0, -1);  //get all words
            pipeline.sync();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToJedis(String wordline) {
        Word currentWord = new Word(wordline);
        pipeline.zadd("woerter", currentWord.getTimestamp(),
                currentWord.getLine());

    }

    public Map<Date, Integer> query(String word, Date from, Date to) {

        Map<Date, Integer> result = new HashMap<Date, Integer>();
        double begin = from.getTime();
        double end = to.getTime();


        Response<Set<String>> sose = pipeline.zrangeByScore("woerter", (long) begin, (long) end);
        pipeline.sync();

        Set<String> resp = sose.get();

        Word currentLine;
        for(String line : resp){
            currentLine = new Word(line);
            if(currentLine.getWord().equals(word)){
                result.put(currentLine.getTimestampreal(), Integer.parseInt(currentLine.getFrequency()));
            }
        }

        return result;
    }

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

        //execute query
        Map<Date, Integer> sum = jedisClient.query(searchword, eins, zwei);

        //calc wordfrequency
        int wordSum = 0;
        for(Map.Entry<Date, Integer> entry : sum.entrySet()){
            wordSum += entry.getValue();
        }

        System.out.println("word " + searchword + " appears " + wordSum + " times between " + dfmt.format(eins) + " " +
                "and " + dfmt.format(zwei));


    }
}
