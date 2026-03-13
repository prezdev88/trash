package cl.prez.emoji;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Hello world!
 *
 */

// https://www.unicode.org/Public/emoji/13.0/emoji-test.txt
public class App {
    public static void main(final String[] args) throws FileNotFoundException
    {
        final long initTime = System.nanoTime();

        final Scanner scanner = new Scanner(new File("emojis.txt"));
        EmojiResponse emojiResponse = new EmojiResponse();

        // code points; status # emoji name
        String line;
        String[] codePoints;
        String name;
        String version = "";

        Emoji emoji;
        while(scanner.hasNext()){
            line = scanner.nextLine();

            if(!line.startsWith("#")){
                if(!line.trim().isEmpty()){
                    emoji = new Emoji();
                    
                    // Code Points
                    codePoints = line.split(";")[0].trim().split(" ");
                    for(String codePoint: codePoints){
                        emoji.addCodePoint(codePoint);
                    }
                    // Code Points

                    // Name and version
                    String[] nameVector = line.split("#")[1].trim().split(" ");
                    name = "";
                    for(int i = 1; i < nameVector.length; i++){
                        if(i == 1){
                            version = nameVector[i];
                        }else{
                            name += nameVector[i];
                            name += " ";
                        }
                    }

                    emoji.setName(name.trim());
                    emoji.setVersion(version);
                    // Name and version

                    emojiResponse.addEmoji(emoji);
                }
            }
        }

        scanner.close();

        final long endTime = System.nanoTime();

        System.out.println(TimeUnit.NANOSECONDS.toMillis((endTime - initTime)) + "ms");
        System.out.println("Emojis: "+ emojiResponse.getEmojis().size());

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        
        try {
            File jsonEmojiFile = new File("emojis.json");
            writer.writeValue(jsonEmojiFile, emojiResponse);
            System.out.println("Generated file: "+jsonEmojiFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
