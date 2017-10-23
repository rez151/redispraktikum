import java.sql.Timestamp;

/**
 * Created by reserchr on 09.10.17.
 */
public class Word {
    private String line;
    private String word;
    private String frequency;
    private double timestamp;

    public Word(String line) {
        this.line = line;
        String[] lineElements = line.split("  ");
        this.word = lineElements[0];
        this.frequency = lineElements[1];
        this.timestamp = Double.parseDouble(lineElements[2]);
    }

    public Word(String word, String line) {
        this.line = line;
        String[] lineElements = line.split(":");
        this.word = word;
        this.frequency = lineElements[1];
        this.timestamp = Double.parseDouble(lineElements[0]);
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

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
