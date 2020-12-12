package net.ojhp.dare.services;

import net.ojhp.dare.models.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("style")
public class StyleService {
    private final AssetPathFixer assetPathFixer;

    @Autowired
    public StyleService(AssetPathFixer assetPathFixer) {
        this.assetPathFixer = assetPathFixer;
    }

    public String setBackgroundImage(String selector, Asset image) {
        return String.format("%s{background-image:url('%s');}",
                selector,
                this.assetPathFixer.fixAssetPath(image.getUrl()));
    }

    public String getIconClasses(String iconName) {
        return "icon fa-" + iconName.replace('_', '-');
    }
}
