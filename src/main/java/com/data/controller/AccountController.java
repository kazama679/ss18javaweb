package com.data.controller;

import com.data.entity.Account;
import com.data.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String listUsers(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size,
                            @RequestParam(required = false) String keyword,
                            Model model,
                            HttpSession session) {

        Object user = session.getAttribute("currentUser");
        if (user == null || !user.getRole().getName().equals("ADMIN")) {
            return "redirect:/login";
        }

        List<Account> accounts = accountService.getAll(page, size, keyword);
        long total = accountService.count(keyword);

        model.addAttribute("accounts", accounts);
        model.addAttribute("total", total);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", keyword);

        return "admin/users/list";
    }

    @GetMapping("/lock/{id}")
    public String lockUser(@PathVariable Long id) {
        Account account = accountService.getById(id);
        if (account != null) {
            account.setEnabled(false);
            accountService.update(account);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/unlock/{id}")
    public String unlockUser(@PathVariable Long id) {
        Account account = accountService.getById(id);
        if (account != null) {
            account.setEnabled(true);
            accountService.update(account);
        }
        return "redirect:/admin/users";
    }
}