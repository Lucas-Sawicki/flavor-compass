package org.example.business;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.OrdersDTO;
import org.example.api.dto.mapper.OrdersMapper;
import org.example.business.dao.OrdersDAO;
import org.example.domain.*;
import org.example.domain.exception.CustomException;
import org.example.infrastructure.database.repository.OrdersRepository;
import org.springframework.dao.DataAccessException;
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
    @Transactional
    public Long generateOrderRequestNumber(OffsetDateTime when) {
        int datePart = when.getYear() * 10000 + when.getMonthValue() * 100 + when.getDayOfMonth();
        int millisPart = when.getNano() / 1_000_000;
        Random random = new Random();
        int randomPart = random.nextInt(90) + 10;
        String orderNumberStr = String.format("%d%02d%02d", datePart, randomPart, millisPart);

        return Long.parseLong(orderNumberStr);
    }

    @Transactional
    public Orders getOrderByOrderNumber(Long orderNr) {
        try {
            return ordersDAO.getOrderByOrderNumber(orderNr);
        } catch (DataAccessException ex) {
            throw new CustomException("Error while accessing data.", ex.getMessage());
        }
    }

    @Transactional
    public void delete(Long orderNumber) {
        try {
            ordersDAO.delete(orderNumber);
        } catch (DataAccessException ex) {
            throw new CustomException("Error while deleting order.", ex.getMessage());
        }
    }

    @Transactional
    public Orders saveOrder(Orders orders) {
        try {
            return ordersDAO.saveOrder(orders);
        } catch (DataAccessException ex) {
            throw new CustomException("Error while saving order.", ex.getMessage());
        }
    }

    @Transactional
    public List<Orders> ordersHistoryForCustomer(Customer customer) {
        try {
            return ordersDAO.findByCustomer(customer);
        } catch (DataAccessException ex) {
            throw new CustomException("Error while finding orders for customer.", ex.getMessage());
        }
    }

    @Transactional
    public List<Orders> ordersHistoryForOwner(Owner owner) {
        try {
            return ordersDAO.findByOwner(owner);
        } catch (DataAccessException ex) {
            throw new CustomException("Error while finding orders for owner.", ex.getMessage());
        }
    }

    @Transactional
    public void completeOrder(Long orderNumber) {
        try {
            ordersDAO.completeOrder(orderNumber);
        } catch (DataAccessException ex) {
            throw new CustomException("Error while completing order.", ex.getMessage());
        }
    }
}
