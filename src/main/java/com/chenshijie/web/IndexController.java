package com.chenshijie.web;

import com.chenshijie.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    @GetMapping("/{x}/{y}")
    public String index(@PathVariable Integer x, @PathVariable Integer y) {
//        int x = 9 / 0;
        /*String blog = null;
        if(blog == null) {
            throw new NotFoundException("博客找不到");
        }*/

        return "index";
    }
}
