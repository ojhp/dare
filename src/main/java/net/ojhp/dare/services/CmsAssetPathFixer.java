package net.ojhp.dare.services;

import com.vladsch.flexmark.ast.LinkNodeBase;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import net.ojhp.dare.configuration.CmsSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class CmsAssetPathFixer implements AssetPathFixer {
    private static final Logger log = LoggerFactory.getLogger(CmsAssetPathFixer.class);

    private final CmsSettings cmsSettings;

    @Autowired
    public CmsAssetPathFixer(CmsSettings cmsSettings) {
        this.cmsSettings = cmsSettings;
    }

    @Override
    public String fixAssetPath(String path) {
        if (path.startsWith(this.cmsSettings.getAssetPrefix())) {
            try {
                URL assetRoot = new URL(this.cmsSettings.getAssetRootUri());
                URL assetUri = new URL(assetRoot, path);
                return assetUri.toString();
            } catch (MalformedURLException e) {
                log.warn("Invalid asset path: {}", path);
            }
        }

        return path;
    }

    @Override
    public void fixAstAssetPaths(Node root) {
        for (Node child : root.getChildren()) {
            if (child instanceof LinkNodeBase) {
                LinkNodeBase asset = (LinkNodeBase) child;
                String fixedPath = this.fixAssetPath(asset.getUrl().toString());
                asset.setUrl(BasedSequence.of(fixedPath));
            }

            if (child.hasChildren()) {
                this.fixAstAssetPaths(child);
            }
        }
    }
}
