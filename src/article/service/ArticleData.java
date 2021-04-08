package article.service;

import article.model.Article;
import article.model.ArticleContent;

public class ArticleData {

	private Article article; // Article테이블 객체
	private ArticleContent content; //ArticleContent테이블 객체
	
	public ArticleData(Article article, ArticleContent content) {
		this.article = article;
		this.content = content;
	}
	// 게시글을 가져오는 메서드
	public Article getArticle() { 
		return article;
	}
	// ArticleContent 게시글을 가져오는 메서드
	public String getContent() { 
		return content.getContent();
	}
	
}
