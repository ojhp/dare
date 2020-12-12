package net.ojhp.dare.services;

import com.vladsch.flexmark.util.ast.Node;

public interface AssetPathFixer {
    String fixAssetPath(String path);
    void fixAstAssetPaths(Node root);
}
