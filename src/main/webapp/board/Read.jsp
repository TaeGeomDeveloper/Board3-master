<%@ page import="gntp.lesson.board.vo.BoardVO" %>
<%@ page import="gntp.lesson.board.vo.ReplyVO" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2022-09-07
  Time: 오후 4:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Read Board</title>

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

    // 보낸 값 받기 getAttribute  // 오브젝트를 ArrayList 로 변경
    ArrayList<ReplyVO> list = (ArrayList<ReplyVO>) request.getAttribute("list");
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
            <h1> 게시글 </h1>
            <table id="member_table">
                <tr>
                    <th>작성자 : <%=vo.getWriter()%>
                    </th>
                    <th>제목 : <%=vo.getTitle()%>
                        <input type="hidden" name="user_Id" value="<%=vo.getUser_id()%>">
                        <input type="hidden" name="seq" value="<%=vo.getSeq()%>">
                    </th>
                </tr>
                <tr>
                    <th style="text-align: center">내용</th>
                    <td><input type="text" name="content" id="content" value="<%=vo.getContent()%>" readonly="readonly">
                        <input type="hidden" name="current_seq" value="<%=vo.getCurrent_seq()%>">
                        <input type="hidden" name="next_seq" value="<%=vo.getNext_seq()%>">
                        <input type="hidden" name="write_seq" value="<%=vo.getWrite_seq()%>">
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="list.do">
                            <button> 게시물 </button>
                        </a>
<%--                        <input type="button" value="뒤로가기" onclick="javascript:history.back();">--%>
                    </td>
                    <td>
                        <a href="ReplyBoard.do?seq=<%=vo.getSeq()%>">
                            <button>답글 달기</button>
                        </a>
                    </td>
                </tr>
            </table>

            <%-- 댓글 리스트--%>
            <div id="reply_box">
                <%
                    for (ReplyVO vo2 : list) {
                %>
                <table id="reply">
                    <tr>
                        <th> content</th>
                        <td colspan="2"><%=vo2.getContent()%>
                        </td>
                    </tr>
                    <tr>
                        <th> 작성일</th>
                        <td><%=vo2.getReg_date()%>
                        </td>
                        <td><input type="button" value="댓글 삭제"></td>
                    </tr>
                </table>
                <%
                    }
                %>
            </div>

            <%--댓글 작성--%>
            <div>
                <form method="post" action="ReplyWrite.do" name="ReplyWrite">
                    <table id="WriteReplyBox">
                        <tr>
                            <td><input type="text" name="ReplyContent" id="ReplyContent">
                                <input type="hidden" name="seq" value="<%=vo.getSeq()%>">
                                <input type="submit" value="댓글 작성">
                            </td>
                        </tr>
                    </table>
                </form>
            </div>

        </div>
    </article>
</section>

<%--푸터--%>
<footer>
    <p>Footer</p>
</footer>

</body>
</html>
