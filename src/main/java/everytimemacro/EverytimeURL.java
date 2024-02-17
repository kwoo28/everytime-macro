package everytimemacro;

import lombok.Getter;

@Getter
public enum EverytimeURL {
    //자유게시판
    FREE_BOARD("https://everytime.kr/393736"),
    //새내기게시판
    NEW_BOARD("https://everytime.kr/412610"),
    //비밀게시판
    SECRET_BOARD("https://everytime.kr/258947");

    private final String URL;

    EverytimeURL(String URL) {
        this.URL = URL;
    }
}
