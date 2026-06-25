package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済みの研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// @author 小野寺結芽
		// テーブル(sctionList)のtbodyのすべての行（tr）を取得
		List<WebElement> rows = webDriver.findElements(By.cssSelector("table.sctionList tbody tr"));
		
		boolean clickSuccess = false;
		
		for (WebElement row : rows) {
			String rowText = row.getText();
			// 行に「2022年10月2日」が含まれている場合
			if (rowText.contains("2022年10月2日")) {
				// その行の中にある「詳細」ボタンを押下
				row.findElement(By.cssSelector("input[value='詳細']")).click();
				clickSuccess = true;
				break;
			}
		}
		// 検証：「2022年10月2日」を見つけて「詳細」ボタンを押下できたか		
		assertTrue(clickSuccess, "「2022年10月2日」が見つかりませんでした。");
		
		// h2タグが表示されるまで最大5秒待つ
		visibilityTimeout(By.cssSelector("h2"), 5);
				
		// 検証：セクション詳細画面に遷移したか
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		
		// エビデンス取得
		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// @author 小野寺結芽
		
		// 下に500ピクセル画面をスクロール
		scrollBy("500");
		
		// 提出済み週報【デモ】を確認するを押下
		webDriver.findElement(By.cssSelector("input[value='提出済み週報【デモ】を確認する']")).click();
		
		// h2タグが表示されるまで最大5秒待つ
		visibilityTimeout(By.cssSelector("h2"), 5);
		
		// 検証：レポート登録画面に遷移したか
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		
		// エビデンス取得
		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		// @author 小野寺結芽
		// 一週間の振り返り欄を特定
		WebElement targetTextArea = webDriver.findElement(By.cssSelector("textarea[name*='2']"));
		
		// 空欄にする
		targetTextArea.clear();
		
		// 「ケース08テスト」と入力する
		targetTextArea.sendKeys("ケース08テスト");
		
		// 「提出する」ボタン押下
		webDriver.findElement(By.className("btn-primary")).click();
		
		// h2タグが表示されるまで最大5秒待つ
		visibilityTimeout(By.cssSelector("h2"), 5);
		
		// 検証：セクション詳細 | LMSに再び遷移したか
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		
		// エビデンス取得
		getEvidence(new Object() {});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		// @author 小野寺結芽
		// 「ようこそ○○さん」リンクをクリック
		webDriver.findElement(By.linkText("ようこそ受講生ＡＡ１さん")).click();
		
		// h2タグが表示されるまで最大5秒待つ
		visibilityTimeout(By.cssSelector("h2"), 5);
 
		// 検証：ユーザー詳細画面に遷移したか
		assertEquals("ユーザー詳細", webDriver.getTitle());
		
		// エビデンス取得
		getEvidence(new Object() {});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// @author 小野寺結芽
		// ユーザー詳細画面のテーブルの全行取得
		List<WebElement> reportRows = webDriver.findElements(By.cssSelector("table tbody tr"));
		
		// ボタンが押せるようにスクロール
		scrollBy("500");
		
		boolean clickSuccess = false;
		for (WebElement row : reportRows) {
			String rowText = row.getText();
			// 2022年10月2日の行を探す
			if (rowText.contains("2022年10月2日")) {
				// その行の「詳細」ボタンを押下
				row.findElement(By.cssSelector("input[value='詳細']")).click();
				clickSuccess = true;
				break;
			}
		}
		// 検証：「2022年10月2日」を見つけて「詳細」ボタンを押下できたか
		assertTrue(clickSuccess, "「2022年10月2日」が見つかりませんでした。");

		// id = "main"が表示されるまで最大5秒待つ
		visibilityTimeout(By.cssSelector("#main"), 5);
		
		// 検証：レポート詳細画面に遷移したか
		assertEquals("レポート詳細 | LMS", webDriver.getTitle());
		
		// id = "main"の全行取得
		String actualText = webDriver.findElement(By.cssSelector("#main")).getText();

		// 検証：修正内容が反映されたか
		assertTrue(actualText.contains("ケース08テスト"), "修正内容が反映されていません。");
		
		// エビデンス取得
		getEvidence(new Object() {});
		
	}

}
