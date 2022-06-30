package com.cola.colablog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cola.colablog.dos.Archives;
import com.cola.colablog.mapper.ArticleBodyMapper;
import com.cola.colablog.mapper.ArticleMapper;
import com.cola.colablog.mapper.ArticleTagMapper;
import com.cola.colablog.pojo.Article;
import com.cola.colablog.pojo.ArticleBody;
import com.cola.colablog.pojo.ArticleTag;
import com.cola.colablog.pojo.SysUser;
import com.cola.colablog.service.ArticleService;
import com.cola.colablog.service.CategoryService;
import com.cola.colablog.service.SysUserService;
import com.cola.colablog.service.TagService;
import com.cola.colablog.utils.UserThreadLocal;
import com.cola.colablog.vo.*;
import com.cola.colablog.vo.params.ArticleParam;
import com.cola.colablog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private CategoryService categoryService;

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

    //文章详情
    @Override
    public Result findArticleById(Integer articleId) {
        /**
         * 1. 根据id查询 文章信息
         * 2. 根据bodyId和categoryid 去做关联查询
         */
        Article article = this.articleMapper.selectById(articleId);
        //浏览量+1
        article.setViewCounts(article.getViewCounts()+1);
        articleMapper.updateById(article);
        ArticleVo articleVo = copy(article);//一篇，copy便可
        return Result.success(articleVo);
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
        SysUser user = sysUserService.findUserById(article.getAuthorId());
        UserVo userVo=new UserVo();
        BeanUtils.copyProperties(user,userVo);
        articleVo.setAuthor(userVo);
        articleVo.setTags(tagService.findTagsByArticleId(article.getId()));

        Integer bodyId = article.getBodyId();
        articleVo.setBody(findArticleBodyById(bodyId));//article.getBodyId
        Integer categoryId = article.getCategoryId();
        //articleVo.setCategory(findCategoryById(categoryId));
        articleVo.setCategory(categoryService.findCategoryById(categoryId));

        return articleVo;
    }

    private ArticleBodyVo findArticleBodyById(Integer bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    // 发布文章
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Override
    public Result publish(ArticleParam articleParam) {
        //此接口 要加入到登录拦截当中
        //对发布文章进行拦截，如果登陆了就放行，就能得到当前用户
        SysUser sysUser = UserThreadLocal.get();
        /**
         * 1. 发布文章 目的 构建Article对象
         * 2. 作者id  当前的登录用户
         * 3. 标签  要将标签加入到 关联列表当中
         * 4. body 内容存储 article bodyId
         */
        Article article = new Article();
        boolean isEdit = false;
        if (articleParam.getId() != null){
            //article = new Article();
            article.setId(articleParam.getId());
            article.setTitle(articleParam.getTitle());
            article.setSummary(articleParam.getSummary());
            article.setCategoryId(Integer.parseInt(articleParam.getCategory().getId()));
            articleMapper.updateById(article);
            isEdit = true;
        }else{
            article = new Article();
            article.setAuthorId(sysUser.getId());
            article.setWeight(0);
            article.setViewCounts(0);
            article.setTitle(articleParam.getTitle());
            article.setSummary(articleParam.getSummary());
            article.setCommentCounts(0);
            article.setCreateDate(System.currentTimeMillis());
            article.setCategoryId(Integer.parseInt(articleParam.getCategory().getId()));
            //插入之后 会生成一个文章id
            this.articleMapper.insert(article);
        }
        //tag
        List<TagVo> tags = articleParam.getTags();
        if (tags != null){
            for (TagVo tag : tags) {
                Integer articleId = article.getId();
                if (isEdit){
                    //先删除
                    LambdaQueryWrapper<ArticleTag> queryWrapper = Wrappers.lambdaQuery();
                    queryWrapper.eq(ArticleTag::getArticleId,articleId);
                    articleTagMapper.delete(queryWrapper);
                }
                ArticleTag articleTag = new ArticleTag();
//                此处！！！
                articleTag.setTagId(tag.getId());

                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }
        }
        //body
        if (isEdit){
            ArticleBody articleBody = new ArticleBody();
            articleBody.setArticleId(article.getId());
            articleBody.setContent(articleParam.getBody().getContent());
            articleBody.setContentHtml(articleParam.getBody().getContentHtml());
            LambdaUpdateWrapper<ArticleBody> updateWrapper = Wrappers.lambdaUpdate();
            updateWrapper.eq(ArticleBody::getArticleId,article.getId());
            articleBodyMapper.update(articleBody, updateWrapper);
        }else {
            ArticleBody articleBody = new ArticleBody();
            articleBody.setArticleId(article.getId());
            articleBody.setContent(articleParam.getBody().getContent());
            articleBody.setContentHtml(articleParam.getBody().getContentHtml());
            articleBodyMapper.insert(articleBody);

            article.setBodyId(articleBody.getId());
            articleMapper.updateById(article);
        }
        Map<String,String> map = new HashMap<>();
        map.put("id",article.getId().toString());

        return Result.success(map);
    }
}




