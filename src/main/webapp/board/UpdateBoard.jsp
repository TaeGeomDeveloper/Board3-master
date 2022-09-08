<%@ page import="gntp.lesson.board.vo.BoardVO" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2022-09-08
  Time: 오후 1:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UpdateBoard</title>

  <!-- 스타일시트 연결 -->
  <link rel="stylesheet" href="CSS/WriteBook.css" type="text/css">
  <link rel="stylesheet" href="CSS/Main.css" type="text/css">
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
  </style>

</head>
<body>

<%
    // 보낸 값 받기 getAttribute  // 오브젝트를 memberVO 로 변경
    BoardVO vo = (BoardVO) request.getAttribute("board");
%>

<%--헤더--%>
<header>
  Board List
</header>

<%--네비--%>
<ul>
  <li><a class="active" href="#home">Home</a></li>
  <li><a href="#contact">Contact</a></li>
  <li><a href="#about">About</a></li>
</ul>

<%--몸통--%>
<section>
  <nav>
    <ul>
      <li>
        <a href="MyServlet?command=list">
          <button> 리스트 불러오기</button>
        </a>
      </li>
      <li>
        <a href="MyServlet?command=Insert">
          <button> 회원 가입하기</button>
        </a>
      </li>
      <li>
        <a href="MyServlet?command=Home">
          <button> Home</button>
        </a>
      </li>
    </ul>
  </nav>
  <article>
    <div id="Main_Box">
      <form method="post" action="updateBoard.do">
        <table id="member_table">
          <tr>
            <th>제목
            </th>
            <th>
              <input type="text" name="title" value="<%=vo.getTitle()%>">
              <input type="hidden" name="user_Id" value="<%=vo.getUser_id()%>">
              <input type="hidden" name="seq" value="<%=vo.getSeq()%>">
            </th>
          </tr>
          <tr>
            <th style="text-align: center">내용</th>
            <td><input type="text" name="content" id="content" value="<%=vo.getContent()%>">
              <input type="hidden" name="current_seq" value="<%=vo.getCurrent_seq()%>">
              <input type="hidden" name="next_seq" value="<%=vo.getNext_seq()%>">
              <input type="hidden" name="write_seq" value="<%=vo.getWrite_seq()%>">
            </td>
          </tr>
          <tr>
            <td>
              <input type="button" value="뒤로가기" onclick="javascript:history.back();">
            </td>
            <td>
              <input type="submit" value="수정 완료">
            </td>
          </tr>
        </table>
      </form>

    </div>
  </article>
</section>

</body>
</html>
