package jp.kuroneko.android.musicquiz.configs;

import static jp.kuroneko.android.musicquiz.R.drawable.frame;
import static jp.kuroneko.android.musicquiz.R.drawable.frame_bad;
import static jp.kuroneko.android.musicquiz.R.drawable.frame_good;

/**
 * 設定情報を管理するクラス
 * @author shimadahiroshi
 *
 */
public class Config extends Object {

	/**
	 * 問題タイプ
	 * @author kuroneko
	 *
	 * ジャケット問題はバージョンアップで対応
	 */
	public enum QuestionType{TYPE_INTRODUCTION, TYPE_TEXT, TYPE_JACKET};

	/**
	 * 問題の種類
	 * @author kuroneko
	 *
	 */
	public enum QuestionKind{KIND_ALBUM, KIND_SINGER, KIND_MUSIC, KIND_AGE};

	/**
	 * 問題文の背景の設定値<br/>
	 * {@link Config#QUESTION_BACKGOUND_RESOURCES} のインデックスに対応している<br/>
	 * 	<table>
	 * 		<tr><td>BACKGROUND_DEFAULT</td><td>デフォルト</td></tr>
	 * 		<tr><td>BACKGROUND_GOOD</td><td>正解</td></tr>
	 * 		<tr><td>BACKGROUND_BAD</td><td>不正解</td></tr>
	 * 	</table>
	 * @author kuroneko
	 *
	 */
	public enum QuestionBackground{BACKGROUND_DEFAULT, BACKGROUND_GOOD, BACKGROUND_BAD};

	/**
	 * 問題文を表示させる部分の背景のリソース ID
	 */
	public static final int[] QUESTION_BACKGOUND_RESOURCES = new int[]{frame, frame_good, frame_bad};

	/**
	 * タイマのインターバル時間 [ms]
	 */
	public static final long TIMER_INTERVAL = 50L;

	/**
	 * カウンタのインターバル時間 [ms]
	 */
	public static final long COUNTER_INTERVAL = 100L;

	/**
	 * 次の問題を表示するまでのインターバル [ms]
	 */
	public static final long QUESTION_INTERVAL = 2000L;

	/**
	 * 問題の最大数
	 */
	public static final int MAXIMUM_QUESTION = 15;

	/**
	 * 問題の選択数の最大数
	 */
	public static final int MAXIMUM_CHOICES = 4;

	/**
	 * 問題の制限時間 [ms]
	 */
	public static final long QUESTION_TIME_LIMIT = 20000L;

	/**
	 * 音楽情報の最低数
	 */
	public static final int MINIMUM_MUSICS = 10;

	/**
	 * 間違えることが出来る最大数
	 */
	public static final int MAXIMUM_WRONG_ANSWER = 3;

	/**
	 * 履歴の最大数
	 */
	public static final int MAXIMUM_HISTORY = 5;

	/**
	 * スコア計算時に割る値 time[ms]/{@link Config#SCORE_RATIO}
	 */
	public static final int SCORE_RATIO = 10;

}
