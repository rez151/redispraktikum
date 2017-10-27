
/**
 * Created by reserchr on 09.10.17.
 */
public class Word {
    private String line;
    private String word;
    private int frequency;
    private long timestamp;

    public Word(String line) {
        this.line = line;
        String[] lineElements = line.split("  ");
        this.word = lineElements[0];
        this.frequency = Integer.parseInt(lineElements[1]);
        this.timestamp = toDay(lineElements[2]);
    }

    public static long toDay(String timestamp) {

        while (timestamp.length() < 13){
            timestamp += "0";
        }
        timestamp = timestamp.substring(0, 13);

        long ts = Long.parseLong(timestamp);
        long newts = ts - ts % 86400000;
        return newts;
    }

    public Word(String word, String line) {
        this.line = line;
        String[] lineElements = line.split(":");
        this.word = word;
        this.frequency = Integer.parseInt(lineElements[1]);
        this.timestamp = Long.parseLong(lineElements[0]);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
