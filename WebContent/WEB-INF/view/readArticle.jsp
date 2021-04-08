<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 읽기</title>
</head>
<body>
<table border="1" width="100%">
<tr>
	<td>번호</td>
	<td>${articleData.article.article_no}</td> <!-- 번호 행에 나타낼 번호 -->
</tr>
<tr>
	<td>작성자</td>
	<td>${articleData.article.writer.writer_name}</td><!-- 작성자 행에 나타낼 작성자명 -->
</tr>	
<tr>
	<td>제목</td>
	<td><c:out value='${articleData.article.title}'/></td><!-- 제목 테이블에 나타낼 제목 -->
</tr>	
<tr>
	<td>내용</td>
	<!-- pre태그를 사용해 입력되어있는 content을 그대로 보여준다. -->
	<td>${articleData.content}</td>
</tr>
<tr>
	<td colspan="2">
	<!-- pageNo = JSTL이 지원하는 태그에서 사용할 수 있는 변수  -->
	<c:set var="pageNo" value="${empty param.pageNo ?'1':param.pageNo}" />
		<a href="list.do?pageNo=${pageNo}">[목록]</a>	 <!-- 페이지목록가는 링크 -->
		<c:if test="${authUser.id == articleData.article.writer.wrtier_id}"> <!-- 작성자아이디와 접속한 아이디가 같은면 밑에 링크코드가 가능하게 한다 -->
		<a href="modify.do?no=${articleData.article.article_no}">[게시글수정]</a><!-- 수정 뷰로 가는 링크 --> 
		<a href="delete.do?no=${articleData.article.article_no}">[게시글삭제]</a><!-- 삭제 뷰로 가는 링크 -->
		</c:if>
</table>
</body>
</html>