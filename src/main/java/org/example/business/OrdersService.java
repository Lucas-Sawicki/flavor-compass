package org.example.business;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.OrdersDTO;
import org.example.api.dto.mapper.OrdersMapper;
import org.example.business.dao.OrdersDAO;
import org.example.domain.*;
import org.example.infrastructure.database.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class OrdersService {

    private final OrdersDAO ordersDAO;
    private final OrdersMapper ordersMapper;
    private final HttpSession httpSession;


    public Long generateOrderRequestNumber(OffsetDateTime when) {
        int datePart = when.getYear() * 10000 + when.getMonthValue() * 100 + when.getDayOfMonth();
        int millisPart = when.getNano() / 1_000_000;
        Random random = new Random();
        int randomPart = random.nextInt(90) + 10;
        String orderNumberStr = String.format("%d%02d%02d", datePart, randomPart, millisPart);

        return Long.parseLong(orderNumberStr);
    }

    public Orders getOrderFromCart() {
        return (Orders) httpSession.getAttribute("cart");
    }

    @Transactional
    public Orders getOrderByOrderNumber(Long orderNr) {
        return ordersDAO.getOrderByOrderNumber(orderNr);
    }

    @Transactional
    public void delete(Long orderNumber) {
        ordersDAO.delete(orderNumber);
    }

    @Transactional
    public Orders saveOrder(Orders orders) {
        return ordersDAO.saveOrder(orders);
    }

    @Transactional
    public List<Orders> ordersHistoryForCustomer(Customer customer) {
        return ordersDAO.findByCustomer(customer);
    }

    @Transactional
    public List<Orders> ordersHistoryForOwner(Owner owner) {
        return ordersDAO.findByOwner(owner);
    }

    @Transactional
    public void completeOrder(Long orderNumber) {
        ordersDAO.completeOrder(orderNumber);
    }
}
