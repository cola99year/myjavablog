package com.cola.colablog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cola.colablog.pojo.Article;
import org.springframework.stereotype.Repository;

/**
* @author cola
* @description 针对表【cola_article】的数据库操作Mapper
* @createDate 2022-06-26 16:49:38
* @Entity .pojo.Article
*/
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

}




