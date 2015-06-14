package com.davorsauer.controller;

import com.davorsauer.commons.Logger;
import com.davorsauer.error.LoadArticleException;
import com.davorsauer.error.ScanArticlesException;
import com.davorsauer.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 * Created by davor on 13/04/15.
 */
@Controller
public class BlogController implements Logger {

    private static final String DEFAULT_BLOG_TEMPLATE = "blogTemplate";

    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/blog", method = {RequestMethod.GET})
    public String redirect() {
        return "redirect:/";
    }

    @RequestMapping(value = "/blog/{slug}", method = {RequestMethod.GET})
    public String blog(@PathVariable("slug") String slug, Model model) throws LoadArticleException {
        String content = blogService.getArticle(slug);
        model.addAttribute("blog_content", content);

        return DEFAULT_BLOG_TEMPLATE;
    }

    @RequestMapping(value = "/preview/{branch}/{slug}", method = {RequestMethod.GET})
    public String preview(@PathVariable("branch") String branch, @PathVariable("slug") String slug, Model model) throws IOException, LoadArticleException {
        String content = blogService.getArticle(slug, branch);
        model.addAttribute("blog_content", content);
        return DEFAULT_BLOG_TEMPLATE;
    }

    @RequestMapping(value = {"/blog_reload"})
    public String blogReload() throws IOException, ScanArticlesException {
        blogService.scan();

        return "redirect:/";
    }

}
