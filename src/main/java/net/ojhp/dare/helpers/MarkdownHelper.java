package net.ojhp.dare.helpers;

import com.vladsch.flexmark.util.ast.IParse;
import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.ast.Node;
import net.ojhp.dare.services.AssetPathFixer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("markdown")
public class MarkdownHelper {
    private final IParse parser;
    private final IRender renderer;
    private final AssetPathFixer assetPathFixer;

    @Autowired
    public MarkdownHelper(
            IParse parser,
            IRender renderer,
            AssetPathFixer assetPathFixer) {
        this.parser = parser;
        this.renderer = renderer;
        this.assetPathFixer = assetPathFixer;
    }

    public String renderHtml(String markdown) {
        Node root = this.parser.parse(markdown);
        this.assetPathFixer.fixAstAssetPaths(root);
        return this.renderer.render(root);
    }
}
