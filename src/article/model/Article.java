package article.model;

public class Article {
	private Integer article_no;
	private Writer writer;
	private String title;
	
	public Article(Integer article_no, Writer writer, String title) {
		this.article_no = article_no;
		this.writer = writer;
		this.title = title;
	}
	public int getArticle_no() {
		return article_no;
	}
	public Writer getWriter() {
		return writer;
	}
	public String getTitle() {
		return title;
	}
	
	
}


