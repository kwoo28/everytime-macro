package everytimemacro;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PostLikeStrategy extends LikeStrategy{

    public PostLikeStrategy(String url, String userId, String userPw) {
        super(url, userId, userPw);
    }
    @Override
    public void activeLike() {
        try {
            //좋아요 누르지 않은 게시글 불러오기
            List<WebElement> elements = getNotLikePostElements();
            log.info("좋아요 누르지 않은 게시글을 불러옵니다 : {}", elements);

            if(!elements.isEmpty()) {
                log.info("좋아요를 누릅니다.");
                clickLike(elements);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private List<WebElement> getNotLikePostElements() throws InterruptedException {

        driver.get(super.url);
        Thread.sleep(500);

        List<WebElement> elements = driver.findElements(By.cssSelector("div.wrap.articles article.list"));

        List<WebElement> filteredElements = elements.stream()
                .filter(element -> {
                    try {
                        element.findElement(By.cssSelector("li.vote"));
                        return false;
                    } catch (NoSuchElementException e) {
                        // 'li.vote' 선택자를 가진 요소가 없는 경우 false를 반환
                        return true;
                    }
                })
                .collect(Collectors.toList());
        return filteredElements;
    }

    private void clickLike(List<WebElement> elements) throws InterruptedException {
        for (WebElement element : elements) {
            String title = element.findElement(By.cssSelector(".medium.bold")).getText();
            String content = element.findElement(By.cssSelector("p.medium")).getText();

            System.out.println("title = " + title);
            System.out.println("content = " + content);
            System.out.println();

            //해당 작성게시글로 이동
            String postURI = element.findElement(By.cssSelector("a.article")).getAttribute("href");
            driver.get(postURI);
            Thread.sleep(2000);

            //좋아요 기능 누르기
            driver.findElement(By.className("posvote")).click();
            Alert alert = driver.switchTo().alert();
            alert.accept();
            Thread.sleep(1000);

            try {
                String msg = alert.getText();
                System.out.println("msg = " + msg);
                alert.accept();
                Thread.sleep(500);
            } catch (NoAlertPresentException e) {
                e.getMessage();
            } finally {
                driver.navigate().back();
            }
        }
    }
}
