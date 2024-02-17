package everytimemacro;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class PostWithCommentLikeStrategy extends LikeStrategy{
    public PostWithCommentLikeStrategy(String url, String userId, String userPw) {
        super(url, userId, userPw);
    }

    @Override
    public void activeLike() {
        try {
            driver.get(super.url);
            Thread.sleep(500);
            List<WebElement> elements = driver.findElements(By.cssSelector("div.wrap.articles article.list"));

            for (int i = 0; i < elements.size(); i++) {
                WebElement element = driver.findElements(By.cssSelector("div.wrap.articles article.list")).get(i);
                String title = element.findElement(By.cssSelector(".medium.bold")).getText();
                String content = element.findElement(By.cssSelector("p.medium")).getText();

                System.out.println("title = " + title);
                System.out.println("content = " + content);

                //해당 작성게시글로 이동
                String postURI = element.findElement(By.cssSelector("a.article")).getAttribute("href");
                driver.get(postURI);
                Thread.sleep(2000);

                //게시글 좋아요 누르기
                int vote = Integer.parseInt(driver.findElement(By.className("vote")).getText());
                if(vote==0){
                    driver.findElement(By.className("posvote")).click();
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                }

                List<WebElement> parentElements = driver.findElements(By.cssSelector(".parent"));
                List<WebElement> childElements = driver.findElements(By.cssSelector(".child"));

                List<WebElement> commentsElements = Stream.concat(parentElements.stream(), childElements.stream())
                        .collect(Collectors.toList());
                log.info("전체 대댓글을 조회합니다 : {}", commentsElements.stream()
                        .map(elementName -> elementName.findElement(By.className("large")))
                        .map(WebElement::getText)
                        .collect(Collectors.toList()));

                //게시글 좋아요 누르지 않은 댓글 불러오기
                List<WebElement> commentElements = commentsElements.stream()
                        .filter(commentElement ->
                            Integer.parseInt(commentElement.findElement(By.cssSelector(".vote.commentvote")).getAttribute("innerHTML"))==0
                        ).collect(Collectors.toList());

                log.info("좋야요 누르지 않은 댓글을 조회합니다 : {}\n", commentElements.stream()
                        .map(elementName -> elementName.findElement(By.className("large")))
                        .map(WebElement::getText)
                        .collect(Collectors.toList()));

                if(!commentElements.isEmpty()){
                    for(WebElement commentElement : commentElements){
                        commentElement.findElement(By.className("commentvote")).click();
                        Alert alert = driver.switchTo().alert();
                        alert.accept();
                    }
                }

                driver.navigate().back();
                Thread.sleep(2000);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
