package everytimemacro;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EverytimeMacro {

    private String userId;
    private String userPw;
    private String url;

    public EverytimeMacro(String userId, String userPw, String url) {
        this.userId = userId;
        this.userPw = userPw;
        this.url = url;
    }

    public void activateLike() {
        try {
            LikeStrategy likeStrategy = new PostWithCommentLikeStrategy(url, userId, userPw);
            likeStrategy.activeLike();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
