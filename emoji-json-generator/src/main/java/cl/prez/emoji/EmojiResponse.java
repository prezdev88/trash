package cl.prez.emoji;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EmojiResponse {
    private List<Emoji> emojis;

    public EmojiResponse(){
        this.emojis = new ArrayList<>();
    }

    public void addEmoji(Emoji emoji){
        this.emojis.add(emoji);
    }
}