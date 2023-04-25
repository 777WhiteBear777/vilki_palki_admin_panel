package lab.space.vilki_palki.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    @GetMapping({"/", ""})
    public String showProductPage() {
        return "/admin-panel/pages/product-structure/products";
    }
}