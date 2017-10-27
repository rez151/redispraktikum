import de.siegmar.fastcsv.reader.CsvReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.*;
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

    public void readSampleData(String filePath) {

        List<String> lines = new ArrayList<String>();

        Reader in;
        try {
            in = new FileReader(filePath);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
            for (CSVRecord record : records) {
                lines.add(record.get(0));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pipeline.flushAll();
        writeToJedis(lines);
        pipeline.multi();
        pipeline.sync();
        pipeline.exec();

        try {
            pipeline.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void writeToJedis(List<String> wordlines) {

        for (String line : wordlines) {
            Word currentWord = new Word(line);
            pipeline.zadd("z:" + currentWord.getWord(), 0, String.valueOf(currentWord.getTimestamp()));
            pipeline.hincrBy("h:" + currentWord.getWord(), String.valueOf(currentWord.getTimestamp()), currentWord.getFrequency());
        }
    }

    public Map<Date, Integer> query(String word, Date from, Date to) {

        //execute query
        String[] timestamps = jedis.zrangeByLex
                ("z:" + word,
                        "[" + String.valueOf(from.getTime()),
                        "[" + String.valueOf(to.getTime())
                ).toArray(new String[0]);
        String[] frequencies = jedis.hmget("h:" + word, timestamps).toArray(new String[0]);
        pipeline.sync();

        //Hashmap with Date and frequency of matching words
        Map<Date, Integer> result = new HashMap<Date, Integer>();

        for (int i = 0; i < timestamps.length; i++) {
            result.put(new Date(Long.parseLong(timestamps[i])), Integer.parseInt(frequencies[i]));
        }
        return result;
    }
}
