package org.example.business;

import org.example.api.dto.MenuItemDTO;
import org.example.api.dto.mapper.MenuItemMapper;
import org.example.business.dao.MenuItemDAO;
import org.example.domain.MenuItem;
import org.example.domain.Restaurant;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemServiceMockitoTest {

    @Mock
    private MenuItemDAO menuItemDAO;
    @Mock
    private MenuItemMapper menuItemMapper;

    @InjectMocks
    private MenuItemService menuItemService;

    @Test
    public void  shouldFindMenuItemByIdCorrectly() {
        // given
        MenuItem menuItem = OtherFixtures.someMenuItem();
        when(menuItemDAO.findById(1)).thenReturn(menuItem);

        // when
        Optional<MenuItem> result = menuItemService.findMenuItemById(menuItem);

        // then
        assertThat(result.get(), is(menuItem));
    }

    @Test
    public void shouldAddMenuItemSuccessfully() {
        // given
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        Restaurant restaurant = OtherFixtures.someRestaurant();
        MenuItem menuItem = OtherFixtures.someMenuItem();
        when(menuItemMapper.map(menuItemDTO, restaurant)).thenReturn(menuItem);
        doNothing().when(menuItemDAO).saveMenuItem(menuItem);

        // when
        menuItemService.addMenuItem(menuItemDTO, restaurant);

        // then
        verify(menuItemDAO).saveMenuItem(menuItem);
    }

    @Test
    public void shouldFindMenuByRestaurantSuccessfully() {
        // given
        Integer id = 1;
        List<MenuItem> menu = Collections.singletonList(OtherFixtures.someMenuItem());
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        when(menuItemDAO.findAllByRestaurantId(id)).thenReturn(menu);
        when(menuItemMapper.map(menu.get(0))).thenReturn(menuItemDTO);

        // when
        List<MenuItemDTO> result = menuItemService.findMenuByRestaurant(id);

        // then
        assertThat(result.get(0), is(menuItemDTO));
    }

    @Test
    public void shouldSortAndPaginationCorrectly() {
        // given
        int page = 0;
        int size = 10;
        String sortBy = "name";
        Integer id = 1;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        Page<MenuItem> menuItemPage = new PageImpl<>(Collections.singletonList(OtherFixtures.someMenuItem()));
        when(menuItemDAO.findAll(pageRequest, id)).thenReturn(menuItemPage);

        // when
        Page<MenuItem> result = menuItemService.pagination(page, size, sortBy, id);

        // then
        assertThat(result, is(menuItemPage));
    }

    @Test
    public void shouldFindMenuItemByIdSuccessfully() {
        // given
        Integer menuItemId = 1;
        MenuItem menuItem = OtherFixtures.someMenuItem();
        when(menuItemDAO.findById(menuItemId)).thenReturn(menuItem);

        // when
        MenuItem result = menuItemService.findById(menuItemId);

        // then
        assertThat(result, is(menuItem));
    }

}