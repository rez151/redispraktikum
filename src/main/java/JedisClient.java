import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by reserchr on 09.10.17.
 */
public class JedisClient {

    private Jedis jedis;



    public JedisClient(String ip, int port) {

        jedis = new Jedis("192.168.162.130", 6379);
    }

    private static void readSampleData(String filePath) {
        String thisline;

        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));

            while((thisline = in.readLine()) != null){
                writeToJedis(thisline);
            }

            System.out.println(in.readLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToJedis(String thisline) {

    }

    public Map<Date,Integer> query(String word, Date from, Date to){



        return null;
    }

    public static void main(String[] args){

        JedisClient jedisClient = new JedisClient("192.168.162.130", 6379);

        jedisClient.readSampleData("/home/reserchr/IdeaProjects/redispraktikum/src/main/java/words.txt");

    }

}
