package jp.kuroneko.android.musicquiz.parameters;

import static jp.kuroneko.android.musicquiz.configs.Config.MAXIMUM_CHOICES;
import static jp.kuroneko.android.musicquiz.configs.Config.QuestionType.TYPE_INTRODUCTION;
import static jp.kuroneko.android.musicquiz.configs.Config.QuestionType.TYPE_TEXT;
import static jp.kuroneko.android.musicquiz.configs.Config.QuestionKind.KIND_MUSIC;
import jp.kuroneko.android.musicquiz.configs.Config.QuestionKind;
import jp.kuroneko.android.musicquiz.configs.Config.QuestionType;

/**
 * 問題のパラメータを保持しているクラス
 * @author kuroneko
 *
 */
public class Question extends BaseParameter{

	/**
	 * 問題文
	 */
	private String mQuestionText;

	/**
	 * 問題タイプ
	 */
	private QuestionType mType;

	/**
	 * 問題の種類
	 */
	private QuestionKind mKind;

	/**
	 * 答えのオーディオ情報
	 */
	private Audio mAnswerData;

	/**
	 * 答え番号
	 */
	private int mAnswerNumber;

	/**
	 * 問題の選択肢
	 */
	private String[] mChoices = new String[MAXIMUM_CHOICES];

	/**
	 * 問題文を取得する
	 * @return
	 * 	問題文
	 */
	public String getQuestionText(){
		return this.mQuestionText;
	}

	/**
	 * 問題文を設定する
	 * @param text
	 * 	問題文
	 */
	public void setQuestionText(String text){
		this.mQuestionText = text;
		return;
	}

	/**
	 * 問題タイプを取得する<br/>
	 * 但し、問題タイプが{@link QuestionType#TYPE_TEXT}で、問題の種類が{@link QuestionKind#KIND_MUSIC}の場合は
	 * 問題が存在しないため、{@link QuestionType#TYPE_INTRODUCTION}を返す
	 * @return
	 * 	問題タイプ
	 */
	public QuestionType getType() {
		if(this.mType == TYPE_TEXT && this.mKind == KIND_MUSIC){
			return TYPE_INTRODUCTION;
		}
		return this.mType;
	}

	/**
	 * 問題タイプを設定する
	 * @param type
	 * 	問題タイプ
	 */
	public void setType(QuestionType type) {
		this.mType = type;
		return;
	}

	/**
	 * 問題の種類を取得する
	 * @return
	 * 	問題の種類
	 */
	public QuestionKind getKind() {
		return this.mKind;
	}

	/**
	 * 問題の種類を設定する
	 * @param type
	 * 	問題の種類
	 */
	public void setKind(QuestionKind kind) {
		this.mKind = kind;
		return;
	}

	/**
	 * 答えのオーディオ情報を取得する
	 * @return
	 * 	答えのオーディオ情報
	 */
	public Audio getAnswerData() {
		return this.mAnswerData;
	}

	/**
	 * 答えのオーディオ情報を設定する
	 * @param data
	 * 	答えのオーディオ情報
	 */
	public void setAnswerData(Audio data) {
		this.mAnswerData = data;
		return;
	}

	/**
	 * 答え番号を取得する
	 * @return
	 * 	答え番号
	 */
	public int getAnswerNumber() {
		return this.mAnswerNumber;
	}

	/**
	 * 答え番号を設定する
	 * @param number
	 * 	答え番号
	 */
	public void setAnswerNumber(int number) {
		this.mAnswerNumber = number;
		return;
	}

	/**
	 * 問題の選択肢を取得する
	 * @param index
	 * 	何番目の選択肢かを指定するインデックス
	 * @return
	 * 	指定したインデックスの問題の選択肢
	 */
	public String getChoice(int index){
		return this.mChoices[index];
	}

	/**
	 * 問題の選択肢を設定する
	 * @param index
	 * 	何番目の選択肢かを指定するインデックス
	 * @param choice
	 * 	指定したインデックスに設定する問題の選択肢
	 */
	public void setChoice(int index, String choice){
		this.mChoices[index] = choice;
		return;
	}

}
