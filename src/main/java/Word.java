import java.sql.Timestamp;

/**
 * Created by reserchr on 09.10.17.
 */
public class Word{
    private String line;
    private String word;
    private String frequency;
    private String timestampString;
    private double timestamp;
    private Timestamp timestampreal;

    public Word(String line) {
        this.line = line;
        String[] lineElements = line.split("  ");
        this.word = lineElements[0];
        this.frequency = lineElements[1];
        this.timestampString = lineElements[2];
        this.timestamp = Double.parseDouble(timestampString);
        this.timestampreal = new Timestamp(Long.parseLong(timestampString));
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getTimestampString() {
        return timestampString;
    }

    public void setTimestampString(String timestampString) {
        this.timestampString = timestampString;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestampreal() {
        return timestampreal;
    }

    public void setTimestampreal(Timestamp timestampreal) {
        this.timestampreal = timestampreal;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
