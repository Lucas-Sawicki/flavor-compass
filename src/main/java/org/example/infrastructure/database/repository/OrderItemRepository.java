package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.OrderItemDAO;
import org.example.domain.OrderItem;
import org.example.infrastructure.database.entity.OrderItemEntity;
import org.example.infrastructure.database.repository.jpa.OrderItemJpaRepository;
import org.example.infrastructure.database.repository.mapper.OrderItemEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class OrderItemRepository implements OrderItemDAO {
    private final OrderItemJpaRepository orderItemJpaRepository;
    private final OrderItemEntityMapper orderItemEntityMapper;

    @Override
    public void save(OrderItem orderItem) {
        OrderItemEntity toSave = orderItemEntityMapper.mapToEntity(orderItem);
        orderItemJpaRepository.saveAndFlush(toSave);
    }

    @Override
    public List<OrderItem> findByOrderId(Integer orderId) {
        List<OrderItemEntity> entities = orderItemJpaRepository.findByOrdersId(orderId);
        return entities.stream().map(orderItemEntityMapper::mapFromEntity).toList();
    }
}
