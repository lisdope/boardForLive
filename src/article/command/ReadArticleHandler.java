package article.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticleData;
import article.service.ReadArticleService;
import mvc.command.CommandHandler;

public class ReadArticleHandler implements CommandHandler {

	private ReadArticleService readService = new ReadArticleService();
	// ReadArticleService 객체 readService를 생성
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// CommandHandler의 process 메서드를 사용해 사용자의 요청을 처리할 객체들을 매개변수로 받는다.
		// getParameter로 받은 매개변수를 noVal에 저장한다.
		String noVal = req.getParameter("no");
		// String형인 noVal을 int형으로 바꿔주고 articleNum에 저장한다.
		int articleNum = Integer.parseInt(noVal);
		try {
			// ReadArticleService의 getArticle메서드를 이용해 articleNum과 increaseReadCount를 true값으로 하여
			// articleData에 저장한다.
			ArticleData articleData = readService.getArticle(articleNum, true);
			req.setAttribute("articleData", articleData); // articleData의 속성에 articleData값을 저장한다.
			return "/WEB-INF/view/readArticle.jsp"; // /WEB-INF/view/readArticle.jsp로 리턴을 한다.
		
			// 위 코드가 정상적으로 작동이 안된다면 ArticleNotFoundException과 ArticleContentNotFoundException로 예외처리를 해준다.
		} catch (Exception e) {
			req.getServletContext().log("no article", e); 
			// 예외에 대한 내용을 no article이라는 log로 기록을한고 req객체에 저장한다.
			res.sendError(HttpServletResponse.SC_NOT_FOUND); 
			// sendError 메서드로 SC_NOT_FOUND로 요청된  Resource가 없는 경우를 나타내는 404에러를 res객체에 저장한다.
			return null; // null값을 리턴한다.
		}
		
	}

	
	
}
