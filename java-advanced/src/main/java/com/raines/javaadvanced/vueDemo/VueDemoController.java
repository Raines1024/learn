package com.raines.javaadvanced.vueDemo;

import com.raines.comm.$;
import com.raines.javaadvanced.vueDemo.vo.Article;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class VueDemoController {

    private static List<Article> list = new ArrayList<>();

    static {
        for (long i = 1; i <= 5; i++) {
            Article article = new Article();
            article.setId(i);
            article.setTitle("title"+i);
            article.setBody("body"+i);
            list.add(article);
        }
    }

    @PostMapping("/articles")
    public Object articles(@RequestBody Article article) {
        long id = Collections.max(list.stream().map(Article::getId).collect(Collectors.toList()));
        article.setId(id+1);
        list.add(article);
        return $.reason(200,"成功",article);
    }

    @GetMapping("/articles")
    public Object articles() {
        return $.reason(200,"成功",list);
    }

    @GetMapping("/articlesById/{id}")
    public Object articlesbyid(@PathVariable("id") Long id) {
        for (Article article : list) {
            if (article.getId().equals(id)){
                return $.reason(200,"成功",article);
            }
        }
        return $.reason(500,"无数据");
    }

    @DeleteMapping("/articleDel/{id}")
    public Object articleDel(@PathVariable("id") Long id) {
        Iterator<Article> iterator = list.iterator();
        while (iterator.hasNext()){
            Long obj = iterator.next().getId();
            if(id.equals(obj)){
                iterator.remove();
            }
        }
        return $.reason(200,"成功");
    }

}
