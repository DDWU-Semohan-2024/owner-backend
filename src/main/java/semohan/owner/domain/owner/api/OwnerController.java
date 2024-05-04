package semohan.owner.domain.owner.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import semohan.owner.domain.owner.application.OwnerService;
import semohan.owner.domain.owner.dto.OwnerDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/owner")
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping(value = "/info")
    public ResponseEntity<OwnerDto> ownerInfo(HttpServletRequest request) {
        Long id = (Long) request.getSession().getAttribute("id");
        return ResponseEntity.ok(ownerService.getOwnerInfo(id));
    }

    @PostMapping("/edit-info")
    public RedirectView editOwnerInfo(HttpSession session, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("password") String password, @RequestParam("newPassword")String newPassword) {
        Long id = (Long) session.getAttribute("id");
        if (id != null) {
            //비밀번호 들어왔는지 확인
            if (password.isEmpty()) {
                String errorMessage = "비밀번호를 입력해주세요."; // 오류 메시지
                session.setAttribute("errorMessage", errorMessage); // 세션에 오류 메시지 저장
                return new RedirectView("/edit-info"); // 기존 페이지로 리다이렉트
            }

            boolean updateSuccess = ownerService.updateOwnerInfo(id, phoneNumber, password, newPassword);
            if (updateSuccess)
            {
                return new RedirectView("/owner/info"); // 수정 완료 후 사용자 정보 페이지로 리다이렉트
            } else {
                String errorMessage = "비밀번호 오류입니다. 다시 시도해주세요."; // 오류 메시지
                session.setAttribute("errorMessage", errorMessage); // 세션에 오류 메시지 저장
                return new RedirectView("/edit-info"); // 수정 실패 시 기존 페이지로 리다이렉트
            }
        } else {
            // 세션에 사용자 아이디가 없는 경우에 대한 처리
            return new RedirectView("/auth/sign-in");
        }
    }
}
