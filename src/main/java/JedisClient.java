import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by reserchr on 09.10.17.
 */

public class JedisClient {

    private Jedis jedis;
    private Pipeline pipeline;
    private static int index;

    public JedisClient(String ip, int port) {
        jedis = new Jedis(ip, port);
        pipeline = jedis.pipelined();
    }

    public void readSampleData(String filePath) {

        pipeline.flushAll();

        String thisline;
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            while ((thisline = in.readLine()) != null) {
                writeToJedis(thisline);
            }
            pipeline.sync();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToJedis(String wordline) {
        Word currentWord = new Word(wordline);
        pipeline.zadd(currentWord.getWord(), 0,
                currentWord.getTimestamp() + ":" + currentWord.getFrequency());
    }

    public Map<Date, Integer> query(String word, Date from, Date to) {

        //Hashmap with Date and frequency of matching words
        Map<Date, Integer> result = new HashMap<Date, Integer>();

        //execute query
        Response<Set<String>> sose = pipeline.zrangeByLex(word, "[" + String.valueOf(from.getTime()),
                "[" + String.valueOf(to.getTime()));
        pipeline.sync();

        Set<String> resp = sose.get();

        //create words with results
        Word currentLine;
        for (String line : resp) {
            currentLine = new Word(word, line);
            result.put(new Date((long) currentLine.getTimestamp()), Integer.parseInt(currentLine.getFrequency()));
        }
        return result;
    }
}
