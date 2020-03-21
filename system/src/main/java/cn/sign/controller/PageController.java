package cn.sign.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {
    //跳转页面
    @RequestMapping(value="to/{url}")
    public String toPage(@PathVariable(value = "url") String url) {
        return url;

    }
}