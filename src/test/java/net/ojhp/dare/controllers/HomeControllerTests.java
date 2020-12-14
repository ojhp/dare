package net.ojhp.dare.controllers;

import net.ojhp.dare.models.Page;
import net.ojhp.dare.repositories.CmsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HomeControllerTests {
    private CmsRepository mockRepository;
    private HomeController sut;

    @BeforeEach
    public void setUp() {
        this.mockRepository = mock(CmsRepository.class);
        this.sut = new HomeController(this.mockRepository);
    }

    @Test
    public void index_WhenCalled_RendersIndexTemplate() {
        String result = this.sut.index(mock(Model.class));

        assertEquals("index", result);
    }

    @Test
    public void index_WhenCalled_SetsPageCollectionOnModel() {
        List<Page> stubPages = new ArrayList<>();
        Model mockModel = mock(Model.class);

        when(this.mockRepository.getAll(Page.class)).thenReturn(stubPages);

        this.sut.index(mockModel);

        verify(mockModel, times(1)).addAttribute("pages", stubPages);
    }
}
