package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

/**
 * 結合テスト ログイン機能①
 * ケース03
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// @author 小野寺結芽
		// localhost:8080/lmsにアクセスし、ログイン画面が表示されるか
		
		// WebDriverUtilsのgoToを呼び出し、localhost:8080/lmsにアクセス
		goTo("http://localhost:8080/lms");
		
		// タイトルの検証
		assertEquals("ログイン | LMS", webDriver.getTitle());
		
		// エビデンス取得
		getEvidence(new Object() {});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// @author 小野寺結芽
		// ログイン画面を開く
		goTo("http://localhost:8080/lms");
		
		// IDとパスワードを入力 (sendKeys)
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).sendKeys("StdAA001");
		
		// ログインボタンをクリック (click)
		webDriver.findElement(By.className("btn-primary")).click();
		
		// h1タグが表示されるまで最大5秒待つ(SeleniumテキストP.28)
		visibilityTimeout(By.cssSelector("h2"), 5);
		
		// 検証：コース詳細画面に遷移したか
		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		
		// エビデンス取得
		getEvidence(new Object() {});
		
	}

}
