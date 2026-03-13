package cl.prezdev.config;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
public class ShellPromptConfig {

    @Value("${prompt}")
    private String prompt;

    @Bean
    PromptProvider promptProvider() {
        return () -> {
            return new AttributedString(prompt, AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN));
        };
    }
}