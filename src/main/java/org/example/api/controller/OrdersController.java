//package org.example.api.controller;
//
//import jakarta.websocket.server.PathParam;
//import lombok.RequiredArgsConstructor;
//import org.example.business.dao.OrdersDAO;
//import org.example.domain.Orders;
//import org.example.domain.exception.NotFoundException;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//
//@Controller
//@RequiredArgsConstructor
//public class OrdersController {
//
//    public static final String ORDERS = "/orders";
//    private static final String ORDERS_BY_ID = "/orders/id";
//
//    private final OrdersDAO ordersDAO;
//
//    @PostMapping(value = ORDERS)
//        @Consumes(MediaType.APPLICATION_JSON)
//        public ResponseEntity<String> createOrder(Orders order) throws ApplicationException {
//            if (!order.isValid()) {
//                throw new ApplicationException("Order is invalid!");
//            }
//
//            ordersDAO.createOrder(order);
//
//            return ResponseEntity.ok("The order has been created.");
//        }
//
//        @GetMapping(value = ORDERS_BY_ID)
//        @Produces(MediaType.APPLICATION_JSON)
//        public ResponseEntity<String> getOrderById(@PathParam("id") Integer id) throws NotFoundException {
//
//            Orders order = ordersDAO.findById(id);
//
//            if (null == order) {
//                throw new NotFoundException("No Order found with Id " + id);
//            }
//
//            return ResponseEntity.ok("Order found");
//        }
//
//        @GetMapping
//        @Path("/table/{table}")
//        @Produces(MediaType.APPLICATION_JSON)
//        public Response getAllOrdersByTable(@PathParam("table") Integer table) throws ResourceNotFoundException {
//            // TODO: Change to Dependency Injection
//            OrderDao orderDao = new OrderDaoImpl(new MongoDBManager());
//
//            List<Orders> orders = orderDao.findAllByTable(table);
//
//            if (null == orders) {
//                throw new ResourceNotFoundException("No Orders found for table " + table);
//            }
//
//            return Response.status(Status.OK).entity(orders).build();
//        }
//
//        @PutMapping
//        @Path("/{id}/{status}")
//        public Response putOrderStatusByOrderId(@PathParam("id") String id, @PathParam("status") String status) throws ResourceNotFoundException, ApplicationException {
//            // TODO: Change to Dependency Injection
//            OrderDao orderDao = new OrderDaoImpl(new MongoDBManager());
//
//            Order order = orderDao.findById(id);
//
//            if (null == order) {
//                throw new ResourceNotFoundException("No Order found with Id " + id);
//            }
//
//            if (!EnumUtils.isValidEnum(OrderStatus.class, status)) {
//                throw new ApplicationException("Status is invalid!");
//            }
//
//            return Response.status(Status.NO_CONTENT).build();
//        }
//    }
//
//
//}
