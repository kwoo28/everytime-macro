package everytimemacro;


import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@Slf4j
public abstract class LikeStrategy {
    public final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public final String WEB_DRIVER_PATH = "driver/chromedriver.exe";
    String url;

    WebDriver driver;
    ChromeOptions options = new ChromeOptions();

    public LikeStrategy(String url, String userId, String userPw) {
        //좋아요 누를 사이트 주소
        this.url = url;

        // WebDriver 경로 설정
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        // 2. WebDriver 옵션 설정
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");

        driver  = new ChromeDriver(options);

        try {
            driver.get("https://account.everytime.kr/login");
            driver.findElement(By.name("id")).sendKeys(userId);
            driver.findElement(By.name("password")).sendKeys(userPw);
            driver.findElement(By.xpath("//input[@value='에브리타임 로그인']")).click();
            log.info("{}님의 아이디로 로그인을 접근합니다.", userId);
            Thread.sleep(2000);
        } catch (InterruptedException e){
            throw new RuntimeException();
        }
    }

    abstract public void activeLike();
}
