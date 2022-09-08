package gntp.lesson.board.controller;

import gntp.lesson.board.dao.BoardDAO;
import gntp.lesson.board.utils.ConnectionManagerV2;
import gntp.lesson.board.vo.BoardVO;
import gntp.lesson.board.vo.ReplyVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("*.do")
public class BoardController extends HttpServlet{

    public void doHandler(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        System.out.println("hi");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        String[] temp = uri.split("/");
        String command = temp[temp.length - 1];
        System.out.println(command);

        PrintWriter out = resp.getWriter();
        BoardDAO dao = new BoardDAO();

        String url = "board/board.jsp";

        if(command.equals("list.do")) {

            // 리스트 처리
            ArrayList<BoardVO> list = new ArrayList<BoardVO>();
            try {
                list = dao.selectAll();
                HttpSession session = req.getSession();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            req.setAttribute("list", list);

            url = "board/board.jsp";
        }

        else if(command.equals("writeBoard.do")) {

            url = "board/writeBoard.jsp";
        }
        else if(command.equals("write.do")) {
            // 추가 회원 내용
            String current_seq = req.getParameter("current_seq");
            String userId = "user";
            String writer = "user";
            String title = req.getParameter("title");
            String next_seq = req.getParameter("next_seq");
            String content = req.getParameter("content");

            BoardVO vo = new BoardVO();
            vo.setTitle(title);
            vo.setContent(content);
            vo.setCurrent_seq(current_seq);
            vo.setWriter(writer);
            vo.setNext_seq(next_seq);
            vo.setUser_id(userId);

            // 로직 처리
            try {
                dao.insertItem(vo);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            url = "list.do";

        } else if (command.equals("read.do")) {
            String seq = req.getParameter("seq");
            String readCount = req.getParameter("readCount");
            int rcount = Integer.valueOf(readCount)+1;
            BoardVO board = dao.selectBoardV3(rcount,seq);

            // 댓글 전체 조회
            ArrayList<ReplyVO> list = new ArrayList<ReplyVO>();

            list = dao.getReplyList(seq);

            req.setAttribute("board",board);
            req.setAttribute("list", list);

            url = "board/Read.jsp";

        } else if (command.equals("ReplyWrite.do")) {
            // 추가 댓글 내용
            String content = req.getParameter("ReplyContent");
            String seq = req.getParameter("seq");

            ReplyVO vo = new ReplyVO();
            vo.setSeq(Integer.parseInt(seq));
            vo.setContent(content);

            // 로직 처리
            try {
                boolean flag = dao.InsertReply(vo);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            url = "read.do?seq="+seq;
        }
        // 게시글 삭제
        else if(command.equals("delete.do")) {
            String seq = req.getParameter("seq");

            dao.deleteBoard(seq);

            url = "list.do";
        }

        // 게시글 답글 화면
        else if(command.equals("ReplyBoard.do")) {
            String seq = req.getParameter("seq");
            BoardVO board = dao.selectBoardV2(seq);
            req.setAttribute("board", board);

            url = "board/replyBoard.jsp";
        }
            // 게시글 수정 화면
        else if(command.equals("update.do")) {
            String seq = req.getParameter("seq");
            BoardVO board = dao.selectBoard(seq);
            req.setAttribute("board",board);

            url = "board/UpdateBoard.jsp";
        }
        // 게시글 수정
        else if(command.equals("updateBoard.do")) {
            // 회원 수정 내용
            String seq = req.getParameter("seq");
            String title = req.getParameter("title");
            String content = req.getParameter("content");

            BoardVO vo = new BoardVO();
            vo.setTitle(title);
            vo.setContent(content);

            // 로직 처리
            try {
                dao.updateItem(vo,Integer.valueOf(seq));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            url = "list.do";
        }
        // 답글 달기
        else if(command.equals("replyInput.do")) {
            int seq = Integer.parseInt(req.getParameter("seq").trim());
            System.out.println("seq "+seq);
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            String currentSeq = req.getParameter("current_seq");
            String nextSeq = req.getParameter("next_seq");
            int writeSeq = Integer.parseInt(req.getParameter("write_seq").trim());
            String userId = "user";
            String writer = "user";
            BoardVO board = new BoardVO(seq,writeSeq,currentSeq,title,content,0,nextSeq,null,writer,userId);

            try {
                dao.insertReplyBoard(board);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            url = "list.do";
        }

        RequestDispatcher rd = req.getRequestDispatcher(url);
        rd.forward(req, resp);
    }

    @Override
    public void init() throws ServletException {
        System.out.println("init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doHandler(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doHandler(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void destroy() {
        System.out.println("destory");
    }
}
