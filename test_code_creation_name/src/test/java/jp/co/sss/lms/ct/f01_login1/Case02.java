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
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

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
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {
		// @author 小野寺結芽
		goTo("http://localhost:8080/lms");
		// DBに登録されていないユーザーでログイン
		webDriver.findElement(By.id("loginId")).sendKeys("wrong-loginId");
		webDriver.findElement(By.id("password")).sendKeys("wrong-password");
		// ログインボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();
		// 検証：エラーメッセージ「ログインに失敗しました。」が表示されているか
		WebElement errorMsg = webDriver.findElement(By.className("error"));
		// メッセージが画面に表示されているか（CSS等で隠れていないか）
		assertTrue(errorMsg.isDisplayed(), "エラーメッセージが画面に表示されていません。");
		// メッセージの内容が正しいか
		assertEquals("* ログインに失敗しました。", errorMsg.getText());
		// 検証：画面が遷移していない（タイトルが変わっていない）ことの確認
		assertEquals("ログイン | LMS", webDriver.getTitle());
		// エビデンス取得
		getEvidence(new Object() {});
	}

}
