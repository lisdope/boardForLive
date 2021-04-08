package article.service;


import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import jdbc.connection.ConnectionProvider;

public class ReadArticleService {

	private ArticleDao articleDao = new ArticleDao(); // ArticleDao 객체 생성
	private ArticleContentDao contentDao = new ArticleContentDao(); // ArticleContentDao 객체 생성
			// ArticleData의 getArticle메서드를 이용 articleNum와 increaseReadCount를 매개 변수로 받는다.
	public ArticleData getArticle(int articleNum, boolean increaseReadCount) {
		try (Connection conn = ConnectionProvider.getConnection()){ 
			// ConnectionProvider의 getConnection메서드로  커넥션 풀을 이용해 데이터베이스에  연결한다.
			Article article = articleDao.selectById(conn, articleNum);
			// articleDao의 selectById메서드로 얻어낸 articleNum을 article 객체에 저장한다.
			

			ArticleContent content = contentDao.selectById(conn, articleNum);
			// contentDao의 selectById메서드로 얻어낸 articleNum을 content 객체에 저장한다.
													  // 다 정상적으로 처리된다면
			return new ArticleData(article, content); // ArticleData객체에 article과 content를 저장하고 리턴한다.
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}