package net.ojhp.dare.helpers;

import net.ojhp.dare.models.Asset;
import net.ojhp.dare.services.AssetPathFixer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("style")
public class StyleHelper {
    private final AssetPathFixer assetPathFixer;

    @Autowired
    public StyleHelper(AssetPathFixer assetPathFixer) {
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
