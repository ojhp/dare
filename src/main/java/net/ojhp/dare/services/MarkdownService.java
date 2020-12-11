package net.ojhp.dare.services;

import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.util.ast.IParse;
import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import net.ojhp.dare.configuration.CmsSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service("markdown")
public class MarkdownService {
    private final IParse parser;
    private final IRender renderer;
    private final CmsSettings cmsSettings;

    @Autowired
    public MarkdownService(
            IParse parser,
            IRender renderer,
            CmsSettings cmsSettings) {
        this.parser = parser;
        this.renderer = renderer;
        this.cmsSettings = cmsSettings;
    }

    public String renderHtml(String markdown) {
        Node root = this.parser.parse(markdown);

        this.fixImages(root);

        return this.renderer.render(root);
    }

    private void fixImages(Node root) {
        for (Node child : root.getChildren()) {
            if (child instanceof Image) {
                Image image = (Image) child;
                if (image.getUrl().startsWith("/uploads/")) {
                    try {
                        URL url = new URL(new URL(this.cmsSettings.getAssetRootUri()), image.getUrl().toString());
                        image.setUrl(BasedSequence.of(url.toString()));
                    } catch (MalformedURLException e) {
                        // Do nothing
                    }
                }
            }

            if (child.hasChildren()) {
                this.fixImages(child);
            }
        }
    }
}
