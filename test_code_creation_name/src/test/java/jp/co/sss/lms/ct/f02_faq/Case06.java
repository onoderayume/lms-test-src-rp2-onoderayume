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
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		// @author 小野寺結芽
		// カテゴリ検索の【人材開発支援助成金】を押下
		webDriver.findElement(By.linkText("【人材開発支援助成金】")).click();
		
		// 下に300ピクセル画面をスクロール
		scrollBy("500");
		
		// index.htmlのtbodyの中にあるtrタグの数を数える
		int resultCount = webDriver.findElements(By.cssSelector("table.table tbody tr")).size();
		
		// 検証：該当カテゴリの検索結果だけが表示されているか(検索結果の「件数」が正しいか)
		assertEquals(3, resultCount, "【人材開発支援助成金】の検索結果の件数が一致しません");
		
		// table全体のテキストを取得
		String tableText = webDriver.findElement(By.className("table")).getText();
		
		// 検証：該当カテゴリの検索結果だけが表示されているか(検索結果の「中身」が正しいか)
		assertTrue(tableText.contains("助成金書類の作成方法が分かりません"), "該当カテゴリの質問が表示されていません");
		
		// エビデンス取得
		getEvidence(new Object() {});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		// @author 小野寺結芽		
		// 下に300ピクセル画面をスクロール
		scrollBy("500");
		
		// 質問(セルフ・キャリアドック制度とは何か)を押下
		webDriver.findElement(By.className("mr10")).click();
		
		// Answer部分を取得
		WebElement answerElement = webDriver.findElement(By.className("fs18"));

		// 検証：その要素が表示されているか
		assertTrue(answerElement.isDisplayed(), "回答が表示されていません");
		
		// エビデンス取得
		getEvidence(new Object() {});
		
	}

}
