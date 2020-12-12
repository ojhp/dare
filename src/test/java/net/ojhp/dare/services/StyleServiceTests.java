package net.ojhp.dare.services;

import net.ojhp.dare.models.Asset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StyleServiceTests {
    private AssetPathFixer mockAssetPathFixer;
    private StyleService sut;

    @BeforeEach
    public void setUp() {
        this.mockAssetPathFixer = mock(AssetPathFixer.class);
        this.sut = new StyleService(this.mockAssetPathFixer);
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
