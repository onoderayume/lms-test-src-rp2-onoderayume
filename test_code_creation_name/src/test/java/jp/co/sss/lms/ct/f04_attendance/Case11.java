package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト 勤怠管理機能
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

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
		
		// IDとパスワードを入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).sendKeys("StdAA001");
		
		// ログインボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();
		
		// h2タグが表示されるまで最大5秒待つ
		visibilityTimeout(By.cssSelector("h2"), 5);
		
		// 検証：コース詳細画面に遷移したか
		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		
		// エビデンス取得
		getEvidence(new Object() {});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		// @author 小野寺結芽
		// 「勤怠」リンク押下
		webDriver.findElement(By.linkText("勤怠")).click();
		
	    // 最大10秒待つ
	    Duration waitTime = Duration.ofSeconds(10);
	    WebDriverWait wait = new WebDriverWait(webDriver, waitTime);
	    // ダイアログが表示されるまで待機(switchTo()もやってくれる)
	    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
				
		// ダイアログのOKボタン押下
		alert.accept();
		
		// class="container"が表示されるまで最大5秒待つ
		visibilityTimeout(By.cssSelector(".container"), 5);
		
		// 検証：勤怠管理画面に遷移したか
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		
		// エビデンス取得
		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {
		// @author 小野寺結芽
		// 「勤怠情報を直接編集する」リンク押下
		webDriver.findElement(By.linkText("勤怠情報を直接編集する")).click();
		
		// class="container"が表示されるまで最大5秒待つ
		visibilityTimeout(By.cssSelector(".container"), 5);
		
		// 検証：勤怠情報直接変更画面に遷移したか
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		
		// エビデンス取得
		getEvidence(new Object() {});
		
		
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {
		// @author 小野寺結芽
		// 下に500ピクセル画面をスクロール
		scrollBy("500");
		
		// dataTableのすべての行を取得
		List<WebElement> rows = webDriver.findElements(By.cssSelector("table.dataTable tbody tr"));
		
		boolean clickSuccess = false;
		
		for (WebElement row : rows) {
			String rowText = row.getText();
			// 行に「2022年10月13日」が含まれている場合
			if (rowText.contains("2022年10月13日")) {
				// その行の中にある「定時」ボタンを押下
				row.findElement(By.className("default-button")).click();
				clickSuccess = true;
				break;
			}
		}
		// 検証：「2022年10月13日」を見つけて「定時」ボタンを押下できたか		
		assertTrue(clickSuccess, "「2022年10月13日」が見つかりませんでした。");	
		
		// 更新ボタン押下
		webDriver.findElement(By.cssSelector("input[value='更新']")).click();
		
	    // 最大10秒待つ
	    Duration waitTime = Duration.ofSeconds(10);
	    WebDriverWait wait = new WebDriverWait(webDriver, waitTime);
	    
	    // 「更新します。よろしいですか？」が表示されるまで待機 (switchTo()もやってくれる)
	    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
				
		// ダイアログのOKボタン押下
		alert.accept();
		
		// id = "main"が表示されるまで最大5秒待つ
		visibilityTimeout(By.cssSelector("#main"), 5);
		
		// 検証：勤怠管理画面に遷移したか
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		
		// エビデンス取得
		getEvidence(new Object() {});
	}

}
