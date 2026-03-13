package cl.prez.emoji;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Emoji {
    private List<String> codePoints;
    private String name;
    private String version;

    public Emoji(){
        this.codePoints = new ArrayList<>();
    }

    public void addCodePoint(String codePoint){
        this.codePoints.add(codePoint);
    }
}