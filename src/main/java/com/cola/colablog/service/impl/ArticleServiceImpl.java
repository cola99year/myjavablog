package com.cola.colablog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cola.colablog.dos.Archives;
import com.cola.colablog.mapper.ArticleMapper;
import com.cola.colablog.pojo.Article;
import com.cola.colablog.service.ArticleService;
import com.cola.colablog.service.SysUserService;
import com.cola.colablog.service.TagService;
import com.cola.colablog.vo.ArticleVo;
import com.cola.colablog.vo.Result;
import com.cola.colablog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author cola
* @description 针对表【cola_article】的数据库操作Service实现
* @createDate 2022-06-26 16:49:38
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TagService tagService;

    @Override
    public Result listArticle(PageParams pageParam) {

        Page<Article> page = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, lambdaQueryWrapper);
        //得到分页查询的数据
        List<Article> records = articlePage.getRecords();
        //数据最终赋复制到 List<ArticleVo>
        List<ArticleVo> articleVoList = copyList(records);
        //List<ArticleVo>放进Result的data里，最终返回Result成功success的对象
        return Result.success(articleVoList);
    }

    //最热文章：浏览数量最多
    @Override
    public Result listHotArticle(Integer limit) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Article::getViewCounts)
                .select(Article::getId,Article::getTitle)
                        .last("limit "+limit);
        List<Article> articles = articleMapper.selectList(lambdaQueryWrapper);
        return Result.success(articles);
    }

    //最新的文章=创建日期最新
    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Article::getCreateDate)
                .select(Article::getId,Article::getTitle)
                .last("limit "+limit);
        List<Article> articles = articleMapper.selectList(lambdaQueryWrapper);
        return Result.success(articles);
    }

    //文章按年份和月份分组
    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }


    private List<ArticleVo> copyList(List<Article> records){
        List<ArticleVo> articleVoList = new ArrayList<>();
        for(Article article : records){
            articleVoList.add(copy(article));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        //实体类的int型与vo层类型不同，需要手动复制，把int型转换成String类型
        articleVo.setId(String.valueOf(article.getId()));
        //时间类型也不用，用joda处理时间,new出DateTime对象包住，是为了用joda的toString方法
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("YYYY年MM月dd日 HH:mm"));
        //这句仍然是单表查询
        articleVo.setAuthor(sysUserService.findUserById(article.getAuthorId()).getNickname());
        articleVo.setTags(tagService.findTagsByArticleId(article.getId()));
        return articleVo;
    }
}




