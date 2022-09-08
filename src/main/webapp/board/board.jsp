<%@ page import="gntp.lesson.board.vo.BoardVO" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2022-09-07
  Time: 오후 3:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Board</title>

    <!-- 스타일시트 연결 -->
    <link rel="stylesheet" href="CSS/ListBook.css" type="text/css">
    <link rel="stylesheet" href="CSS/Main.css" type="text/css">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
    </style>

</head>
<body>

<%
    // 보낸 값 받기 getAttribute  // 오브젝트를 ArrayList 로 변경
    ArrayList<BoardVO> list = (ArrayList<BoardVO>) request.getAttribute("list");
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
            <h1> 방명록 </h1>

            <table id="list_table">
                <tr>
                    <th>작성자</th>
                    <th id="title">제목</th>
                    <th id="content">내용</th>
                    <th>작성일</th>
                    <th>조회수</th>
                    <th colspan="2"> 버튼</th>
                </tr>
                <%
                    for (BoardVO vo : list) {
                %>
                <tr>
                    <td>
                        <%=vo.getWriter()%>
                    </td>
                    <td>
                        <a href="read.do?seq=<%=vo.getSeq()%>&readCount=<%=vo.getRead_count()%>">
                            <%
                                int len = vo.getCurrent_seq().length();
                                for(int i=0; i < vo.getCurrent_seq().length()*2; i++) {
                                    out.print("&nbsp;");
                                }
                                if(len!=1){
                                    out.print("↳");
                                }
                            %>
                            <%=vo.getTitle()%>
                        </a>
                    </td>
                    <td>
                        <%=vo.getContent()%>
                    </td>
                    <td>
                        <%=vo.getWrite_date()%>
                    </td>
                    <td style="text-align:center;">
                        <%=vo.getRead_count()%>
                    </td>
                    <td>
                        <a href="update.do?seq=<%=vo.getSeq()%>">
                            <button>변경하기</button>
                        </a>
                    </td>
                    <td>
                        <a href="delete.do?seq=<%=vo.getSeq()%>">
                            <button>삭제하기</button>
                        </a>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
            <a href="writeBoard.do">
                <input type="button" value="글 작성하기">
            </a>
        </div>
    </article>
</section>

<%--푸터--%>
<footer>
    <p>Footer</p>
</footer>

</body>
</html>
