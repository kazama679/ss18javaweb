package com.data.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account user;

    @Temporal(TemporalType.DATE)
    private Date orderDate;

    private String status;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Account getUser() { return user; }
    public void setUser(Account user) { this.user = user; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}