package net.ojhp.dare.services;

import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.IParse;
import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.ast.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MarkdownServiceTests {
    private IParse mockParser;
    private IRender mockRenderer;
    private AssetPathFixer mockAssetPathFixer;
    private MarkdownService sut;

    @BeforeEach
    public void setUp() {
        this.mockParser = mock(IParse.class);
        this.mockRenderer = mock(IRender.class);
        this.mockAssetPathFixer = mock(AssetPathFixer.class);
        this.sut = new MarkdownService(this.mockParser, this.mockRenderer,
                this.mockAssetPathFixer);
    }

    @Test
    public void renderHtml_WhenCalled_ParsesMarkdownIntoHtml() {
        String stubMarkdown = "some test in markdown";
        Node stubNode = Document.NULL;
        String stubHtml = "some html <tags> and stuff";

        when(this.mockParser.parse(stubMarkdown)).thenReturn(stubNode);
        when(this.mockRenderer.render(stubNode)).thenReturn(stubHtml);

        String result = this.sut.renderHtml(stubMarkdown);

        assertEquals(stubHtml, result);
    }

    @Test
    public void renderHtml_WhenCalled_FixesAssetPathsBeforeRendering() {
        Node stubNode = Document.NULL;

        InOrder inOrder = inOrder(this.mockAssetPathFixer, this.mockRenderer);
        when(this.mockParser.parse(any(String.class))).thenReturn(stubNode);

        this.sut.renderHtml("markdown");

        inOrder.verify(this.mockAssetPathFixer).fixAstAssetPaths(stubNode);
        inOrder.verify(this.mockRenderer).render(stubNode);
    }
}
