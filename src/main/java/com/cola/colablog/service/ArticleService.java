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

}
