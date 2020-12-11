package net.ojhp.dare.providers;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.IParse;
import com.vladsch.flexmark.util.ast.IRender;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MarkdownProvider {
    @Bean
    public IParse markdownParser() {
        return Parser.builder().build();
    }

    @Bean
    public IRender htmlRenderer() {
        return HtmlRenderer.builder().build();
    }
}
