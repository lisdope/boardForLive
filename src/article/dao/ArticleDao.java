package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import article.model.Article;
import article.model.Writer;
import jdbc.JdbcUtil;

public class ArticleDao {

	public List<Article> select(Connection conn, int startRow, int size) throws SQLException{
		PreparedStatement pstmt = null; // SQL 구문을 실행하는 역할
		ResultSet rs = null; // 조회된 결과 데이터를 갖는 인터페이스 null 초기화
		try {
		pstmt = conn.prepareStatement("select * from article order by article_no desc limit ?, ?");  
		// article_no를 내림차순으로 정렬 limit 시작행,레코드 갯수
		pstmt.setInt(1, startRow);
		pstmt.setInt(2, size);
		rs = pstmt.executeQuery(); // ResultSet에 쿼리문을 담는다.
		List<Article> result = new ArrayList<Article>(); // List<Atricle> 객체 생성
		while(rs.next()) {// ResultSet next메서드는 읽어올레코드가 있으면 true 없으면 false 반환
			result.add(convertArticle(rs)); // List에 게시글정보를 추가
		}
		return result; // List<Article>게시글정보값을 가진 result 반환
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
	}
	public int selectCount(Connection conn) throws SQLException{ // 게시글 갯수를 가져오는 메서드
		Statement stmt =null; // SQL문을 실행하는 인터페이스 null 초기화
		ResultSet rs = null; // 조회된 결과 데이터를 갖는 인터페이스 null 초기화
		try { 
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from article"); // article 레코드 숫자를 가져온다.
			if(rs.next()) { // ResultSet next메서드는 읽어올레코드가 있으면 true 없으면 false 반환
				return rs.getInt(1); // 첫번째 컬럼값을 반환시킴
			}
			return 0; // 없을경우 0 반환
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
	private Article convertArticle(ResultSet rs) throws SQLException{
		return new Article(rs.getInt("article_no"), new Writer(rs.getString("writer_id"), rs.getString("writer_name")), 
				rs.getString("title"));
	}

	
	// 요청한 번호에 해당하는 게시글의 내용을 출력하는 메서드
	public Article selectById(Connection conn, int no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try { // pstmt객체에 쿼리문을 담아 데이터베이스에 전송한다.
			pstmt = conn.prepareStatement("select * from article where article_no = ?");
											// 게시글 번호를 이용하여 article의 게시글 내용을 가져오는 쿼리문							
			pstmt.setInt(1,  no); // ?에 들어갈 변수를 정한다.
			rs = pstmt.executeQuery(); // pstmt에 담긴 쿼리문을 실행하여 rs객체에 값을 담는다.
			Article article = null;
			if(rs.next()) { // rs 객체담긴 내용을 출력한다.
				article = convertArticle(rs); // rs객체 담긴 값을 convertArticle 메서드로 변환하여 Article 객체에 담아놓는다. 
			}
			return article; // selectById 메서드를 호출한 곳으로 article객체를 리턴시킨다.
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	// 게시글을 조회 할 때 마다 게시글의 조회수를 1씩 증가시키는 메서드
	
	public Article insert(Connection conn, Article art) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Statement st = null;
		try{
			pstmt = conn.prepareStatement("insert into article "
		+ "(writer_id, writer_name, title) "
				+"values (?,?,?)");
		pstmt.setString(1, art.getWriter().getWrtier_id());
		pstmt.setString(2, art.getWriter().getWriter_name());
		pstmt.setString(3, art.getTitle());
		int insertedCount = pstmt.executeUpdate();
		
		if(insertedCount > 0) {
			st=conn.createStatement();
			rs = st.executeQuery("select last_insert_id() from article");
			if(rs.next()) {
				Integer newNo = rs.getInt(1);
				return new Article(newNo, 
						art.getWriter(), 
						art.getTitle());
			}
		
		}return null;
	}finally {
		JdbcUtil.close(rs);
		JdbcUtil.close(st);
		JdbcUtil.close(pstmt);
	}
		
	
  }
	// 파라미터에서 전달받은 게시글 번호와 제목을 이용해서 데이터를 수정한다.
		public int update(Connection conn, int no, String title) throws SQLException {
																//데이터베이스에 연결해 업데이트 쿼리문을 쓴다. 첫번쨰 ?는 수정할 게시글의 제목이 들어가고  두번째 ?에는 게시글 번호가 들어간다.			
			try(PreparedStatement pstmt = conn.prepareStatement("update article set title = ?" + "where article_no= ?")){
				pstmt.setString(1, title);
				pstmt.setInt(2, no);
						//수행결과로 int값이 들어간다. 이 메서드가 인트형이기 때문이다.
				return pstmt.executeUpdate();
			} 
		}
		// 파라미터에서 전달받은 게시글 번호를 통해 게시글을 삭제한다.
		public int delete(Connection conn, int no) throws SQLException{
			try(//데이터베이스에 연결해 삭제 쿼리문을 쓴다.
				PreparedStatement pstmt =
										//?에 게시글 번호가 들어간다.
				conn.prepareStatement("delete from article where article_no=?")){
					pstmt.setInt(1, no);
					//수행결과로 int값이 들어간다. 이 메서드가 인트형이기 때문이다.
					return pstmt.executeUpdate();
			}
		
	}
}
