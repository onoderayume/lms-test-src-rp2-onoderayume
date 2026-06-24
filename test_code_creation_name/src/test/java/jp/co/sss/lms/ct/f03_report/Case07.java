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
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// @author 小野寺結芽
		// 未提出の研修日の「詳細」ボタンを押下
		// 「未提出」というテキストを含んでいる行（tr）を探し、その行の中にある詳細ボタンを押下
		
		// テーブル(sctionList)のtbodyのすべての行（tr）を取得
		List<WebElement> rows = webDriver.findElements(By.cssSelector("table.sctionList tbody tr"));
		
		// 「未提出」の行を見つけて、その中の詳細ボタンを押下
		boolean clickSuccess = false;
		for (WebElement row : rows) { // 1行ずつ調べる
			// 行全体のテキストを取得
			String rowText = row.getText();
			
			// その行に「未提出」が含まれている場合
			if (rowText.contains("未提出")) {
				// その行の「詳細」ボタンを押下
				row.findElement(By.cssSelector("input[value='詳細']")).click();
				clickSuccess = true;
				break; // ボタンを押下したらループを抜ける
			}
		}
		// 検証：「未提出」を見つけて「詳細」ボタンを押下できたか
		assertTrue(clickSuccess, "「未提出」が見つかりませんでした。");		
		
		// h2タグが表示されるまで最大5秒待つ
		visibilityTimeout(By.cssSelector("h2"), 5);
				
		// 検証：セクション詳細画面に遷移したか
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		
		// エビデンス取得
		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// @author 小野寺結芽
		// 日報【デモ】を提出するを押下
		webDriver.findElement(By.cssSelector("input[value='日報【デモ】を提出する']")).click();
		
		// h2タグが表示されるまで最大5秒待つ
		visibilityTimeout(By.cssSelector("h2"), 5);
		
		// 検証：レポート登録画面に遷移したか
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		
		// エビデンス取得
		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		// @author 小野寺結芽
		// 「本日の報告内容をお書きください。」欄に、「ケース07テスト」と入力
		webDriver.findElement(By.tagName("textarea")).sendKeys("ケース07テスト");
		
		// 「提出する」ボタン押下
		webDriver.findElement(By.className("btn-primary")).click();
		
		// h2タグが表示されるまで最大5秒待つ
		visibilityTimeout(By.cssSelector("h2"), 5);
		
		// 検証：セクション詳細 | LMSに再び遷移したか
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		
		// 提出後のボタン名(value属性の値)を取得
		WebElement checkButton = webDriver.findElement(By.cssSelector("input[value='提出済み日報【デモ】を確認する']"));
		String actualButtonName = checkButton.getAttribute("value");

		// 検証：ボタン名が「提出済み日報【デモ】を確認する」に更新されているか
		assertEquals("提出済み日報【デモ】を確認する", actualButtonName, "ボタン名が一致しません。");
		
		// エビデンス取得
		getEvidence(new Object() {});
	}

}
