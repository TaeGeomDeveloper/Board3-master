package gntp.lesson.board.dao;

import gntp.lesson.board.utils.CalculatorNextSeq;
import gntp.lesson.board.vo.BoardVO;
import gntp.lesson.board.utils.ConnectionManagerV2;
import gntp.lesson.board.vo.ReplyVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardDAO {
    public boolean insertItem(BoardVO board) throws SQLException {
        boolean flag = false;
        String sql = "insert into board(current_seq,title,content,next_seq,writer,user_id) values (?,?,?,?,?,?)";
        Connection con = ConnectionManagerV2.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, board.getCurrent_seq());
        pstmt.setString(2, board.getTitle());
        pstmt.setString(3, board.getContent());
        pstmt.setString(4, board.getNext_seq());
        pstmt.setString(5, board.getWriter());
        pstmt.setString(6, board.getUser_id());
        int affectedCount = pstmt.executeUpdate();
        if(affectedCount>0) {
            flag = true;
        }
        ResultSet rs = null;
        if(flag) {
            sql = "select max(seq) from board";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            int max = 0;
            if(rs.next()) {
                max = rs.getInt(1);
            }
            sql = "update board set write_seq = ? where seq = ? ";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, max);
            pstmt.setInt(2, max);
            pstmt.executeUpdate();
            //write_seq 생성
            System.out.println("write_seq 생성");
        }
        ConnectionManagerV2.closeConnection(rs, pstmt, con);
        return flag;
    }

    public ArrayList<BoardVO> selectAll() throws SQLException{
        ArrayList<BoardVO> list = null;
        String sql = "select * from board order by write_seq desc , current_seq asc";
        Connection con = ConnectionManagerV2.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        list = new ArrayList<BoardVO>();
        BoardVO board = null;
        while(rs.next()) {
            board = new BoardVO(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),
                    rs.getString(5),rs.getInt(6),rs.getString(7),rs.getTimestamp(8),"user","user");
            list.add(board);
        }
        ConnectionManagerV2.closeConnection(rs, pstmt, con);
        return list;
    }


    public BoardVO selectBoard(String seq) throws SQLException {

        //ArrayList<memberVO> list = new ArrayList<>();
        BoardVO vo = null;

        // 컨낵션 정보
        Connection con = ConnectionManagerV2.getConnection();

        // 쿼리부분     아이디, 비밀번호, 이름
        String sql = "SELECT * FROM board where seq = ?";

        // 특정한 쿼리만 통과 하는 전용 통로 작성.
        PreparedStatement pstmt = con.prepareStatement(sql);

        // 쿼리 ? 부분에 값을 넣어 비교 해준다.
        pstmt.setString(1,seq);
        ResultSet rs = pstmt.executeQuery();

        // 쿼리 처리
        if(rs.next()) {
            vo = new BoardVO(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),
                    rs.getString(5),rs.getInt(6),rs.getString(7),rs.getTimestamp(8),"user","user");
        }

        // 닫기
        ConnectionManagerV2.closeConnection(rs, pstmt, con);

        return vo;
    }

    public BoardVO selectBoardV2(String seq) throws SQLException {

        //ArrayList<memberVO> list = new ArrayList<>();
        BoardVO vo = null;

        // 컨낵션 정보
        Connection con = ConnectionManagerV2.getConnection();

        // 쿼리부분     아이디, 비밀번호, 이름
        String sql = "SELECT * FROM board where seq = ?";

        // 특정한 쿼리만 통과 하는 전용 통로 작성.
        PreparedStatement pstmt = con.prepareStatement(sql);

        // 쿼리 ? 부분에 값을 넣어 비교 해준다.
        pstmt.setString(1,seq);
        ResultSet rs = pstmt.executeQuery();

        // 쿼리 처리
        if(rs.next()) {
            String nextSeq = CalculatorNextSeq.getNextSeq(rs.getString(3), rs.getString(7));
            vo = new BoardVO(rs.getInt(1),rs.getInt(2),nextSeq,rs.getString(4),
                    rs.getString(5),rs.getInt(6),rs.getString(7),rs.getTimestamp(8),"user","user");
        }

        // 닫기
        ConnectionManagerV2.closeConnection(rs, pstmt, con);

        return vo;
    }
    public BoardVO selectBoardV3(int readCount,String seq) throws SQLException {

        //ArrayList<memberVO> list = new ArrayList<>();
        BoardVO vo = null;

        // 컨낵션 정보
        Connection con = ConnectionManagerV2.getConnection();

        // 조회수 추가
        String sql = "update board set read_count = ? where seq = ?";
        // 특정한 쿼리만 통과 하는 전용 통로 작성.
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, readCount);
        pstmt.setString(2, seq);

        // 쿼리 처리
        int count = pstmt.executeUpdate();

        // 게시글 정보 검색
        sql = "SELECT * FROM board where seq = ?";

        // 특정한 쿼리만 통과 하는 전용 통로 작성.
        pstmt = con.prepareStatement(sql);

        // 쿼리 ? 부분에 값을 넣어 비교 해준다.
        pstmt.setString(1,seq);
        ResultSet rs = pstmt.executeQuery();

        // 쿼리 처리
        if(rs.next()) {
            vo = new BoardVO(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),
                    rs.getString(5),rs.getInt(6),rs.getString(7),rs.getTimestamp(8),"user","user");
        }

        // 닫기
        ConnectionManagerV2.closeConnection(rs, pstmt, con);

        return vo;
    }

    public ArrayList<ReplyVO> getReplyList(String seq) throws SQLException {
        ArrayList<ReplyVO> list = new ArrayList<>();

        // 컨낵션 정보
        Connection con = ConnectionManagerV2.getConnection();

        // 쿼리부분     아이디, 비밀번호, 이름
        String sql = "SELECT * FROM reply where seq = ?";

        // 특정한 쿼리만 통과 하는 전용 통로 작성.
        PreparedStatement pstmt = con.prepareStatement(sql);

        // 쿼리 ? 부분에 값을 넣어 비교 해준다.
        pstmt.setString(1,seq);
        ResultSet rs = pstmt.executeQuery();

        // 쿼리 처리
        ReplyVO vo = null;
        while(rs.next()) {
            vo = new ReplyVO(rs.getInt(1),
                    rs.getString(2),
                    rs.getDate(3),
                    rs.getInt(4));
            list.add(vo);
        }

        // 닫기
        ConnectionManagerV2.closeConnection(rs, pstmt, con);

        return list;
    }

    public boolean InsertReply(ReplyVO vo) throws SQLException {
        //ArrayList<memberVO> list = new ArrayList<>();
        boolean flag = false;

        // 컨낵션 정보
        Connection con = ConnectionManagerV2.getConnection();

        // 쿼리부분     아이디, 비밀번호, 이름
        String sql = "Insert into reply (content,seq) values(?,?)";

        // 특정한 쿼리만 통과 하는 전용 통로 작성.
        PreparedStatement pstmt = con.prepareStatement(sql);

        // 쿼리 ? 부분에 값을 넣어 비교 해준다.
        pstmt.setString(1,vo.getContent());
        pstmt.setInt(2,vo.getSeq());
        int count = pstmt.executeUpdate();

        // 쿼리 처리
        if(count > 0){
            flag = true;
        }

        // 닫기
        pstmt.close();
        con.close();

        return flag;
    }

    public void deleteBoard(String seq) throws SQLException {

        // 컨낵션 정보
        Connection con = ConnectionManagerV2.getConnection();

        // 쿼리부분     아이디, 비밀번호, 이름
        String sql = "delete from board where seq = ?";

        // 특정한 쿼리만 통과 하는 전용 통로 작성.
        PreparedStatement pstmt = con.prepareStatement(sql);

        // 쿼리 ? 부분에 값을 넣어 비교 해준다.
        pstmt.setString(1,seq);
        int count = pstmt.executeUpdate();

        // 닫기
        pstmt.close();
        con.close();
    }

    public void updateItem(BoardVO board,int seq) throws SQLException {
        //ArrayList<memberVO> list = new ArrayList<>();
        boolean flag = false;

        // 컨낵션 정보
        Connection con = ConnectionManagerV2.getConnection();

        // 쿼리부분     아이디, 비밀번호, 이름
        String sql = "update board set title = ?, content = ? where seq = ?";

        // 특정한 쿼리만 통과 하는 전용 통로 작성.
        PreparedStatement pstmt = con.prepareStatement(sql);

        // 쿼리 ? 부분에 값을 넣어 비교 해준다.
        pstmt.setString(1,board.getTitle());
        pstmt.setString(2,board.getContent());
        pstmt.setInt(3,board.getSeq());

        int count = pstmt.executeUpdate();
        // 쿼리 처리
        if(count > 0){
            flag = true;
        }

        // 닫기
        pstmt.close();
        con.close();
    }

    // 답글 삽입하기
    public boolean insertReplyBoard(BoardVO board) throws SQLException {
        boolean flag = false;
        String sql = "insert into board(write_seq,current_seq,title,content,next_seq,writer,user_id) values (?,?,?,?,?,?,?)";
        Connection con = ConnectionManagerV2.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, board.getWrite_seq());
        pstmt.setString(2, board.getCurrent_seq());
        pstmt.setString(3, board.getTitle());
        pstmt.setString(4, board.getContent());
        pstmt.setString(5, board.getNext_seq());
        pstmt.setString(6, board.getWriter());
        pstmt.setString(7, board.getUser_id());
        int affectedCount = pstmt.executeUpdate();
        if(affectedCount>0) {
            flag = true;
        }
        if(flag) {
            sql = "update board set next_seq = ? where seq = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, board.getCurrent_seq());
            pstmt.setInt(2, board.getSeq());
            affectedCount = pstmt.executeUpdate();
            if(affectedCount>0) {
                flag = true;
            }
        }
        ConnectionManagerV2.closeConnection(null, pstmt, con);
        return flag;
    }
}
