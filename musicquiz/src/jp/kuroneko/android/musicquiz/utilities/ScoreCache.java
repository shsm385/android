package jp.kuroneko.android.musicquiz.utilities;

import static jp.kuroneko.android.musicquiz.configs.Config.MAXIMUM_HISTORY;
import jp.kuroneko.android.musicquiz.configs.Config.QuestionKind;
import jp.kuroneko.android.musicquiz.configs.Config.QuestionType;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * スコアのキャッシュを管理するクラス
 * @author kuroneko
 *
 */
public class ScoreCache extends CacheManager {

	/**
	 * キャッシュ名
	 */
	private static final String CACHE_NAME = "MusicQuiz";

	/**
	 * デフォルトのスコア
	 */
	private static final int DEFAULT_SCORE = 0;

	/**
	 * デフォルトのランキング
	 */
	private static final int DEFAULT_RANKING = 0;

	/**
	 * デフォルトの回数
	 */
	private static final int DEFAULT_COUNT = 0;

	/**
	 * デフォルトのクリアフラグ
	 */
	private static final boolean DEFAULT_CLEAR = false;

	/**
	 * デフォルトの履歴
	 */
	private static final String DEFAULT_HISTORY;

	/**
	 * 直前にプレイした結果スコアのキー
	 */
	private static final String KEY_CURRENT_SCORE = "CScore";

	/**
	 * 直前にプレイした結果スコアの順位のキー
	 */
	private static final String KEY_CURRENT_RANKING = "CRanking";

	/**
	 * 直前にプレイした問題数
	 */
	private static final String KEY_CURRENT_QUESTION_COUNT = "CQuestionCount";

	/**
	 * 直前にプレイした問題の正解回数
	 */
	private static final String KEY_CURRENT_CORRECT_COUNT = "CCorrectCount";

	/**
	 * 直前にプレイした問題のジャンル毎のプレイした問題数
	 */
	private static final String KEY_CURRENT_GENRE_COUNT = "CGenreCount";

	/**
	 * 直前にプレイした問題のジャンル毎の問題の正解数
	 */
	private static final String KEY_CURRENT_GENRE_CORRECT_COUNT = "CGenreCorrectCount";

	/**
	 * 直前にプレイした結果のクリアフラグのキー
	 */
	private static final String KEY_CLRAR = "Clear";

	/**
	 * スコア履歴のキー
	 */
	private static final String KEY_SCORE_HISTORY = "ScoreHistory";

	/**
	 * トータルのプレイした問題数
	 */
	private static final String KEY_TOTAL_QUESTION_COUNT = "QuestionCount";

	/**
	 * トータルのプレイした問題の正解回数
	 */
	private static final String KEY_TOTAL_CORRECT_COUNT = "CorrectCount";

	/**
	 * トータルのプレイした問題のジャンル毎のプレイした問題数
	 */
	private static final String KEY_TOTAL_GENRE_COUNT = "GenreCount";

	/**
	 * トータルのプレイした問題のジャンル毎の問題の正解数
	 */
	private static final String KEY_TOTAL_GENRE_CORRECT_COUNT = "GenreCorrectCount";

	/**
	 * プレイ回数
	 */
	private static final String KEY_PLAY_COUNT = "PlayCount";

	/**
	 * クリア回数
	 */
	private static final String KEY_CLEAR_COUNT = "ClearCount";

	/**
	 * キーを連結する場合に用いる記号
	 */
	private static final char KEY_GLUE_SIGN = '-';

	/**
	 * スコアのキャッシュを管理するために使用
	 */
	private static final ScoreCache sCache = new ScoreCache();

	static{
		JSONArray json = new JSONArray();
		for(int index = 0; index < MAXIMUM_HISTORY; ++index){
			json.put(DEFAULT_SCORE);
		}
		DEFAULT_HISTORY = json.toString();
	}

	/**
	 * スコアのキャッシュをシングルトンパターンで管理するためのコンストラクタ
	 */
	private ScoreCache(){
		super(CACHE_NAME);
		return;
	}

	/**
	 * 直前にプレイした結果スコアを保存する
	 * @param score
	 * 	直前にプレイした結果スコア
	 */
	public static void saveCurrentScore(int score){
		ScoreCache.sCache.save(KEY_CURRENT_SCORE, score, false);
		return;
	}

	/**
	 * 直前にプレイした結果スコアを取得する
	 * @return
	 * 	直前にプレイした結果スコア
	 */
	public static int getCurrentScore(){
		return ScoreCache.sCache.getInt(KEY_CURRENT_SCORE, DEFAULT_SCORE, false);
	}

	/**
	 * 直前にプレイした結果スコアの順位を保存する
	 * @param score
	 * 	直前にプレイした結果スコアの順位
	 */
	public static void saveCurrentRanking(int ranking){
		ScoreCache.sCache.save(KEY_CURRENT_RANKING, ranking, false);
		return;
	}

	/**
	 * 直前にプレイした結果スコアの順位を取得する
	 * @return
	 * 	直前にプレイした結果スコアの順位
	 */
	public static int getCurrentRanking(){
		return ScoreCache.sCache.getInt(KEY_CURRENT_RANKING, DEFAULT_RANKING, false);
	}

	/**
	 * クリアフラグを保存する
	 * @param clearFlag
	 * 	クリア出来た場合に true を、できなかった場合に false を指定する
	 */
	public static void saveClear(boolean clearFlag){
		ScoreCache.sCache.save(KEY_CLRAR, clearFlag, false);
		return;
	}

	/**
	 * クリアできたかどうかを取得する
	 * @return
	 * 	クリア出来た場合に true を、できなかった場合に false を返す
	 */
	public static boolean isClear(){
		return ScoreCache.sCache.getBoolean(KEY_CLRAR, DEFAULT_CLEAR, false);
	}

	/**
	 * スコア履歴を保存する
	 * @param history
	 * 	スコア履歴
	 */
	public static void saveHistory(int[] history){
		JSONArray json = new JSONArray();
		int index = 0;
		for(int score: history){
			json.put(score);
			if(++index == MAXIMUM_HISTORY){
				break;
			}
		}
		for(; index < MAXIMUM_HISTORY; ++index){
			json.put(DEFAULT_SCORE);
		}
		ScoreCache.sCache.save(KEY_SCORE_HISTORY, json.toString(), false);
	}

	/**
	 * スコア履歴を取得する
	 * @return
	 * 	スコア履歴
	 */
	public static int[] getHistory(){
		int[] history = new int[MAXIMUM_HISTORY];
		int index = 0;
		try {
			JSONArray json = new JSONArray(ScoreCache.sCache.getString(KEY_SCORE_HISTORY, DEFAULT_HISTORY, false));
			for(; index < MAXIMUM_HISTORY; ++index){
				history[index] = json.getInt(index);
			}
		} catch (JSONException e) {
			for(; index < MAXIMUM_HISTORY; ++index){
				history[index] = DEFAULT_SCORE;
			}
		}
		return history;
	}

	/**
	 * 直前にプレイした問題数を保存する
	 * @param count
	 * 	問題数
	 */
	public static void saveCurrentQuestionCount(int count){
		ScoreCache.sCache.mEditor
			.putInt(CacheManager.getKey(KEY_CURRENT_QUESTION_COUNT, false), count)
			.putInt(CacheManager.getKey(KEY_TOTAL_QUESTION_COUNT, false), ScoreCache.getTotalQuestionCount() + count)
			.commit();
		return;
	}

	/**
	 * 直前にプレイした問題数を取得する
	 * @return
	 *	直前にプレイした問題数
	 */
	public static int getCurrentQustionCount(){
		return ScoreCache.sCache.getInt(KEY_CURRENT_QUESTION_COUNT, DEFAULT_COUNT, false);
	}

	/**
	 * トータルの問題数を取得する
	 * @return
	 * 	トータルの問題数
	 */
	public static int getTotalQuestionCount(){
		return ScoreCache.sCache.getInt(KEY_TOTAL_QUESTION_COUNT, DEFAULT_COUNT, false);
	}

	/**
	 * 直前にプレイした問題の正解数を保存する
	 * @param count
	 * 	正解数
	 */
	public static void saveCurrentCorrectCount(int count){
		ScoreCache.sCache.mEditor
			.putInt(CacheManager.getKey(KEY_CURRENT_CORRECT_COUNT, false), count)
			.putInt(CacheManager.getKey(KEY_TOTAL_CORRECT_COUNT, false), ScoreCache.getTotalCorrectCount() + count)
			.commit();
		return;
	}

	/**
	 * 直前にプレイした問題の正解数を取得する
	 * @return
	 * 	直前にプレイした問題の正解数
	 */
	public static int getCurrentCorrectCount(){
		return ScoreCache.sCache.getInt(KEY_CURRENT_CORRECT_COUNT, DEFAULT_COUNT, false);
	}

	/**
	 * トータルのプレイした問題の正解数を取得する
	 * @return
	 */
	public static int getTotalCorrectCount(){
		return ScoreCache.sCache.getInt(KEY_TOTAL_CORRECT_COUNT, DEFAULT_COUNT, false);
	}

	/**
	 * 直前にプレイした内容のジャンルごとの結果を保存する
	 * @param type
	 * 	問題タイプ
	 * @param kind
	 * 	問題の種類
	 * @param count
	 * 	問題数
	 * @param correct
	 * 	正解数
	 */
	public static void saveGenreResult(QuestionType type, QuestionKind kind, int count, int correct){
		int totalCount = ScoreCache.getTotalGenreCount(type, kind) + count;
		int totalCorrect = ScoreCache.getTotalGenreCorrectCount(type, kind) + correct;
		ScoreCache.sCache.mEditor
			.putInt(ScoreCache.createGenreKey(KEY_CURRENT_GENRE_COUNT, type, kind, true), count)
			.putInt(ScoreCache.createGenreKey(KEY_CURRENT_GENRE_CORRECT_COUNT, type, kind, true), correct)
			.putInt(ScoreCache.createGenreKey(KEY_TOTAL_GENRE_COUNT, type, kind, true), totalCount)
			.putInt(ScoreCache.createGenreKey(KEY_TOTAL_GENRE_CORRECT_COUNT, type, kind, true), totalCorrect)
			.commit();
		return;
	}

	/**
	 * 直前にプレイしたジャンル毎の問題数を取得する
	 * @param type
	 * 	問題タイプ
	 * @param kind
	 * 	問題の種類
	 * @return
	 * 	直前にプレイしたジャンル毎の問題数
	 */
	public static int getCurrentGenreCount(QuestionType type, QuestionKind kind){
		String key = ScoreCache.createGenreKey(KEY_CURRENT_GENRE_COUNT, type, kind, false);
		return ScoreCache.sCache.getInt(key, DEFAULT_COUNT, false);
	}

	/**
	 * 直前にプレイしたジャンル毎の正解数を取得する
	 * @param type
	 * 	問題タイプ
	 * @param kind
	 * 	問題の種類
	 * @return
	 * 	直前にプレイしたジャンル毎の正解数
	 */
	public static int getCurrentGenreCorrectCount(QuestionType type, QuestionKind kind){
		String key = ScoreCache.createGenreKey(KEY_CURRENT_GENRE_CORRECT_COUNT, type, kind, false);
		return ScoreCache.sCache.getInt(key, DEFAULT_COUNT, false);
	}

	/**
	 * トータルのプレイした内容のジャンルごとの問題数を取得する
	 * @param type
	 * 	問題タイプ
	 * @param kind
	 * 	問題の種類
	 * @return
	 * 	トータルのプレイした内容のジャンルごとの問題数
	 */
	public static int getTotalGenreCount(QuestionType type, QuestionKind kind){
		String key = ScoreCache.createGenreKey(KEY_TOTAL_GENRE_COUNT, type, kind, false);
		return ScoreCache.sCache.getInt(key, DEFAULT_COUNT, false);
	}

	/**
	 * トータルのプレイした内容のジャンルごとの正解数を取得する
	 * @param type
	 * 	問題タイプ
	 * @param kind
	 * 	問題の種類
	 * @return
	 * 	トータルのプレイした内容のジャンルごとの正解数
	 */
	public static int getTotalGenreCorrectCount(QuestionType type, QuestionKind kind){
		String key = ScoreCache.createGenreKey(KEY_TOTAL_GENRE_CORRECT_COUNT, type, kind, false);
		return ScoreCache.sCache.getInt(key, DEFAULT_COUNT, false);
	}

	/**
	 * プレイ回数を加算する
	 */
	public static void incrementPlayCount(){
		ScoreCache.sCache.save(KEY_PLAY_COUNT, ScoreCache.getPlayCount() + 1, false);
		return;
	}

	/**
	 * プレイ回数を取得する
	 * @return
	 * 	プレイ回数
	 */
	public static int getPlayCount(){
		return ScoreCache.sCache.getInt(KEY_PLAY_COUNT, DEFAULT_COUNT, false);
	}

	/**
	 * クリア回数を加算する
	 */
	public static void incrementClearCount(){
		ScoreCache.sCache.save(KEY_CLEAR_COUNT, ScoreCache.getClearCount() + 1, false);
	}

	/**
	 * クリア回数を取得する
	 * @return
	 * 	クリア回数
	 */
	public static int getClearCount(){
		return ScoreCache.sCache.getInt(KEY_CLEAR_COUNT, DEFAULT_COUNT, false);
	}

	/**
	 * ジャンルのためのキーを作成する
	 * @param prefix
	 * 	プレフィックス
	 * @param type
	 * 	問題タイプ
	 * @param kind
	 * 	問題の種類
	 * @param encodeFlag
	 * 	キャッシュのキーを扱うためのエンコードされたキーを取得する場合は true を、そのままの値を取得する場合は false を指定する
	 * @return
	 * 	作成したジャンルのためのキー
	 */
	private static String createGenreKey(String prefix, QuestionType type, QuestionKind kind, boolean encodeFlag){
		StringBuilder builder = new StringBuilder(prefix);
		builder.append(KEY_GLUE_SIGN).append(type.name());
		builder.append(KEY_GLUE_SIGN).append(kind.name());
		return encodeFlag ? CacheManager.getKey(builder.toString(), false) : builder.toString();
	}

}
