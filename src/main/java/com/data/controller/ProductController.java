package com.data.controller;

import com.data.entity.Product;
import com.data.service.CloudinaryService;
import com.data.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword
    ) {
        int pageResult = productService.findAllPagingAndSearch(page, size, keyword);
        model.addAttribute("products", pageResult.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageResult.getTotalPages());
        model.addAttribute("keyword", keyword);
        return "admin/products/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/products/create";
    }

    @PostMapping("/create")
    public String create(
            @javax.validation.Valid @ModelAttribute("product") Product product,
            BindingResult result,
            @RequestParam("imageFile") MultipartFile imageFile,
            Model model
    ) throws IOException {
        if (result.hasErrors()) {
            return "admin/products/create";
        }

        if (!imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(imageFile);
            product.setImage(imageUrl);
        } else {
            model.addAttribute("errorImage", "Vui lòng chọn ảnh sản phẩm");
            return "admin/products/create";
        }

        productService.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        if (product == null) {
            return "redirect:/admin/products";
        }
        model.addAttribute("product", product);
        return "admin/products/edit";
    }
    @PostMapping("/edit/{id}")
    public String edit(
            @PathVariable Long id,
            @Valid @ModelAttribute("product") Product product,
            BindingResult result,
            @RequestParam("imageFile") MultipartFile imageFile,
            Model model
    ) throws IOException {
        if (result.hasErrors()) {
            return "admin/products/edit";
        }

        Product existing = productService.findById(id);
        if (existing == null) {
            return "redirect:/admin/products";
        }

        existing.setProductName(product.getProductName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setStock(product.getStock());

        if (!imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(imageFile);
            existing.setImage(imageUrl);
        }

        productService.save(existing);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/admin/products";
    }
}