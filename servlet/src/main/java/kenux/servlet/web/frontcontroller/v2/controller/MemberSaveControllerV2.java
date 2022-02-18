package kenux.servlet.web.frontcontroller.v2.controller;

import kenux.servlet.domain.member.Member;
import kenux.servlet.domain.member.MemberRepository;
import kenux.servlet.web.frontcontroller.MyView;
import kenux.servlet.web.frontcontroller.v1.ControllerV1;
import kenux.servlet.web.frontcontroller.v2.ControllerV2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberSaveControllerV2 implements ControllerV2 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        Member member = new Member(username, age);
        memberRepository.save(member);
        // Model에 데이터를 보관한다.
        request.setAttribute("member", member);
        return new MyView("/WEB-INF/views/save-result.jsp");
    }
}
