package com.data.controller;

import com.data.entity.Order;
import com.data.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String listOrders(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "5") int size,
                             @RequestParam(required = false) String username,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
                             @RequestParam(required = false) String status,
                             Model model,
                             HttpSession session) {

        Object user = session.getAttribute("currentUser");
        if (user == null || !user.getRole().getName().equals("ADMIN")) {
            return "redirect:/login";
        }

        List<Order> orders = orderService.getAll(page, size, username, fromDate, toDate, status);
        long total = orderService.count(username, fromDate, toDate, status);

        model.addAttribute("orders", orders);
        model.addAttribute("total", total);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);

        model.addAttribute("username", username);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        model.addAttribute("status", status);

        return "admin/orders/list";
    }

    @GetMapping("/confirm/{id}")
    public String confirmOrder(@PathVariable Long id) {
        Order order = orderService.getById(id);
        if (order != null && "pending".equals(order.getStatus())) {
            order.setStatus("confirmed");
            orderService.update(order);
        }
        return "redirect:/admin/orders";
    }

    @GetMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable Long id) {
        Order order = orderService.getById(id);
        if (order != null && !"cancelled".equals(order.getStatus())) {
            order.setStatus("cancelled");
            orderService.update(order);
        }
        return "redirect:/admin/orders";
    }

    @GetMapping("/changeStatus/{id}")
    public String changeStatus(@PathVariable Long id, @RequestParam String status) {
        Order order = orderService.getById(id);
        if (order != null) {
            order.setStatus(status);
            orderService.update(order);
        }
        return "redirect:/admin/orders";
    }
}