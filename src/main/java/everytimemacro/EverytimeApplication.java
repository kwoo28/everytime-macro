package everytimemacro;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EverytimeApplication {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("에브리타임 아이디를 입력하세요 : ");
        String userId = sc.nextLine();
        System.out.print("에브리타임 비밀번호를 입력하세요 : ");
        String userPw = sc.nextLine();

        EverytimeMacro everytimeMacro = new EverytimeMacro(userId, userPw, EverytimeURL.NEW_BOARD.getURL());
        EverytimeMacro everytimeMacro2 = new EverytimeMacro(userId, userPw, EverytimeURL.FREE_BOARD.getURL());

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(everytimeMacro::activateLike, 0, 5, TimeUnit.MINUTES);
        service.scheduleAtFixedRate(everytimeMacro2::activateLike, 0, 5, TimeUnit.MINUTES);
    }
}