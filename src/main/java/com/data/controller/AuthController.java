package com.data.controller;

import com.data.entity.Account;
import com.data.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Account account = accountService.login(username, password);
        if (account != null) {
            session.setAttribute("currentUser", account);
            if ("ADMIN".equals(account.getRole().getName())) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/";
            }
        } else {
            model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
            return "auth/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session) {
        Account currentUser = (Account) session.getAttribute("currentUser");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole().getName())) {
            return "redirect:/login";
        }
        return "admin/dashboard";
    }
}