<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2022-09-07
  Time: 오후 2:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Write Board</title>

    <!-- 스타일시트 연결 -->
    <link rel="stylesheet" href="CSS/WriteBook.css" type="text/css">
    <link rel="stylesheet" href="CSS/Main.css" type="text/css">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
    </style>

</head>
<body>

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
            <form method="post" action="write.do">
                <table id="member_table">
                    <tr>
                        <th> 제목</th>
                        <td><input type="text" name="title"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <th> 내용</th>
                        <td><textarea rows="20" cols="100" name="content"></textarea></td>
                        <td><input type="hidden" name="current_seq" value="a">
                            <input type="hidden" name="next_seq" value=" ">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3"><input type="submit" value="게시글 작성"></td>
                    </tr>
                </table>
            </form>
        </div>
    </article>
</section>


<%--푸터--%>
<footer>
    <p>Footer</p>
</footer>

</body>
</html>
