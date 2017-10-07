package com.visiansystems.docs;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class DocsController {

    @RequestMapping("/docs")
    public void handleDocsRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
}
