package net.ojhp.dare.helpers;

import net.ojhp.dare.models.Asset;
import net.ojhp.dare.services.AssetPathFixer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StyleHelperTests {
    private AssetPathFixer mockAssetPathFixer;
    private StyleHelper sut;

    @BeforeEach
    public void setUp() {
        this.mockAssetPathFixer = mock(AssetPathFixer.class);
        this.sut = new StyleHelper(this.mockAssetPathFixer);
    }

    @Test
    public void setBackgroundImage_WhenCalled_ReturnsStyleTagSettingImage() {
        String stubSelector = "#id.class:pseudo";
        String stubAssetUri = "/path/to/an/asset.jpg";
        String stubFixedAssetUri = "/actual/path/to/asset.jpg";
        Asset stubAsset = new Asset();
        stubAsset.setUrl(stubAssetUri);

        when(this.mockAssetPathFixer.fixAssetPath(stubAssetUri)).thenReturn(stubFixedAssetUri);

        String result = this.sut.setBackgroundImage(stubSelector, stubAsset);

        String expected = String.format("%s{background-image:url('%s');}",
                stubSelector, stubFixedAssetUri);
        assertEquals(expected, result);
    }

    @Test
    public void getIconClasses_WhenCalled_ReturnsIconClasses() {
        String stubIcon = "dog";

        String result = this.sut.getIconClasses(stubIcon);

        assertEquals("icon fa-dog", result);
    }

    @Test
    public void getIconClasses_WhenIconNameHasUnderscores_ReplacesWithDashes() {
        String stubIcon = "cat_picture";

        String result = this.sut.getIconClasses(stubIcon);

        assertEquals("icon fa-cat-picture", result);
    }
}
