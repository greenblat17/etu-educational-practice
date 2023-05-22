package com.greenblat.etuep.controller;

import com.greenblat.etuep.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/documents")
    public String getAllDocuments(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("documents", userService.getDocumentsByUser(userDetails));
        return "user/documents";
    }
}
