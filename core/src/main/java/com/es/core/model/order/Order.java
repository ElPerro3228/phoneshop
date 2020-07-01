package com.es.core.model.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Order
{
    private Long id;
    private UUID uuid;
    private List<OrderItem> orderItems;
    /**
     *  A sum of order item prices;
     */
    private BigDecimal subtotal;
    private BigDecimal deliveryPrice;
    /**
     * <code>subtotal</code> + <code>deliveryPrice</code>
     */
    private BigDecimal totalPrice;

    private String firstName;
    private String lastName;
    private String deliveryAddress;
    private String contactPhoneNo;

    private OrderStatus status;

    public Order() {
    }

    public Order(UUID uuid, List<OrderItem> orderItems, BigDecimal subtotal, BigDecimal deliveryPrice,
                 BigDecimal totalPrice, String firstName, String lastName, String deliveryAddress,
                 String contactPhoneNo, OrderStatus status) {
        this.uuid = uuid;
        this.orderItems = orderItems;
        this.subtotal = subtotal;
        this.deliveryPrice = deliveryPrice;
        this.totalPrice = totalPrice;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deliveryAddress = deliveryAddress;
        this.contactPhoneNo = contactPhoneNo;
        this.status = status;
    }

    public Order(Long id, UUID uuid, List<OrderItem> orderItems, BigDecimal subtotal, BigDecimal deliveryPrice,
                 BigDecimal totalPrice, String firstName, String lastName, String deliveryAddress,
                 String contactPhoneNo, OrderStatus status) {
        this.id = id;
        this.uuid = uuid;
        this.orderItems = orderItems;
        this.subtotal = subtotal;
        this.deliveryPrice = deliveryPrice;
        this.totalPrice = totalPrice;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deliveryAddress = deliveryAddress;
        this.contactPhoneNo = contactPhoneNo;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getContactPhoneNo() {
        return contactPhoneNo;
    }

    public void setContactPhoneNo(String contactPhoneNo) {
        this.contactPhoneNo = contactPhoneNo;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}

