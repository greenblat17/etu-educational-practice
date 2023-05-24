package com.greenblat.etuep.controller;

import com.greenblat.etuep.service.IndexDocumentService;
import com.greenblat.etuep.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final IndexDocumentService indexDocumentService;

    @GetMapping("/documents")
    public String getAllDocuments(Model model,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("documents", userService.getDocumentsByUser(userDetails));
        return "user/documents";
    }

    @GetMapping("/document/{id}")
    public String getDocument(@PathVariable("id") Long id,
                              Model model) {
        model.addAttribute("document", userService.getDocument(id));
        model.addAttribute("id", id);
        return "user/document";
    }

    @GetMapping("/document/update")
    public String updateDocument(@RequestParam("filename") String filename,
                                 Model model) {
        model.addAttribute("filename", filename);
        return "user/update";
    }

    @GetMapping("/search-file")
    public String searchDocument(@RequestParam("line") String searchLine,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 Model model) {
        model.addAttribute("documentsContainsText", indexDocumentService.searchDocument(searchLine, userDetails));
        model.addAttribute("documents", userService.getDocumentsByUser(userDetails));
        return "user/documents";
    }

}
