package jp.co.sss.lms.ct.f02_faq;

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
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// @author 小野寺結芽
		// ドロップダウン「機能」をクリックして開く
		webDriver.findElement(By.linkText("機能")).click();
		
		// 中にある「ヘルプ」をクリック
		webDriver.findElement(By.linkText("ヘルプ")).click();
		
		// h2タグが表示されるまで最大5秒待つ
		visibilityTimeout(By.cssSelector("h2"), 5);
		
		// 検証：ヘルプ画面に遷移したか
		assertEquals("ヘルプ | LMS", webDriver.getTitle());
		
		// エビデンス取得
		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// @author 小野寺結芽
		// 後で比較するために現在のタブを記憶しておく
		String originalWindow = webDriver.getWindowHandle();
		
		// 「よくある質問」リンクをクリック
		webDriver.findElement(By.linkText("よくある質問")).click();
		
		// h2タグが表示されるまで最大5秒待つ
		visibilityTimeout(By.cssSelector("h2"), 5);
		
		// 別タブがあることを確認(開いているタブの総数が「2」になっていることを検証)
		// getWindowHandles().size()ブラウザの現在開いているタブの総数をカウントする
		int windowCount = webDriver.getWindowHandles().size();
		assertEquals(2, windowCount, "別タブが開かれていません");
		
		// 開いているタブを1つずつ順番にwindowHandleという変数に入れて確認
		for (String windowHandle : webDriver.getWindowHandles()) {
			// もし今確認しているタブ(windowHandle)が、originalWindowと一致しなかったらwindowHandleに移動
			if (!originalWindow.contentEquals(windowHandle)) {
				webDriver.switchTo().window(windowHandle);
				break; // 移動できたからチェック終わり
			}
		}
		// h2タグが表示されるまで最大5秒待つ
		visibilityTimeout(By.cssSelector("h2"), 5);

		// 検証：別タブに「よくある質問」画面が開かれたか
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		
		// エビデンス取得
		getEvidence(new Object() {});

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// @author 小野寺結芽
		// キーワード欄に「研修」と入力
		webDriver.findElement(By.id("form")).sendKeys("研修");
		
		// 検索ボタン押下
		webDriver.findElement(By.cssSelector("input[value='検索']")).click();
		
		// 「キーワードを含む検索結果だけ」が表示されたか
		String resultText = webDriver.findElement(By.id("DataTables_Table_0")).getText();

		// 検証：そのテキストの中に「研修」が含まれているか
		assertTrue(resultText.contains("研修"), "検索結果にキーワードが含まれていません");
		
		// エビデンス取得
		getEvidence(new Object() {});
	}
	
	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// @author 小野寺結芽
		// クリアボタン押下
		webDriver.findElement(By.cssSelector("input[value='クリア']")).click();
		
		// キーワード入力欄（id="form"）の現在の入力値（value）を取得
		String keywordValue = webDriver.findElement(By.id("form")).getAttribute("value");

		// 検証：キーワード欄が空になったか
		assertEquals("", keywordValue, "キーワード欄が空になっていません");
		
		// エビデンス取得
		getEvidence(new Object() {});
		
	}

}
