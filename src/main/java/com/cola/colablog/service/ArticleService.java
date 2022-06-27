package com.cola.colablog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cola.colablog.pojo.Article;
import com.cola.colablog.vo.Result;
import com.cola.colablog.vo.params.PageParams;

/**
* @author cola
* @description 针对表【cola_article】的数据库操作Service
* @createDate 2022-06-26 16:49:38
*/
public interface ArticleService extends IService<Article> {

    Result listArticle(PageParams pageParam);
    //最热文章：浏览数量最多
    Result listHotArticle(Integer limit);
    //最新的文章=创建日期最新
    Result newArticles(int limit);

    //文章按年份和月份分组
    Result listArchives();
}
