package jp.kuroneko.android.musicquiz.controllers;

import static jp.kuroneko.android.musicquiz.R.id.btnquiz1;
import static jp.kuroneko.android.musicquiz.R.id.btnquiz2;
import static jp.kuroneko.android.musicquiz.R.id.btnquiz3;
import static jp.kuroneko.android.musicquiz.R.id.btnquiz4;
import static jp.kuroneko.android.musicquiz.R.string.empty;
import static jp.kuroneko.android.musicquiz.R.string.question_number;
import static jp.kuroneko.android.musicquiz.ayncs.CounterThread.CURRENT_COUNT;
import static jp.kuroneko.android.musicquiz.ayncs.TimerThread.REMAINING_TIME;
import static jp.kuroneko.android.musicquiz.ayncs.TimerThread.TIMER_FINISHED;
import static jp.kuroneko.android.musicquiz.configs.Config.MAXIMUM_CHOICES;
import static jp.kuroneko.android.musicquiz.configs.Config.MAXIMUM_HISTORY;
import static jp.kuroneko.android.musicquiz.configs.Config.MAXIMUM_QUESTION;
import static jp.kuroneko.android.musicquiz.configs.Config.MAXIMUM_WRONG_ANSWER;
import static jp.kuroneko.android.musicquiz.configs.Config.QUESTION_BACKGOUND_RESOURCES;
import static jp.kuroneko.android.musicquiz.configs.Config.QUESTION_INTERVAL;
import static jp.kuroneko.android.musicquiz.configs.Config.QUESTION_TIME_LIMIT;
import static jp.kuroneko.android.musicquiz.configs.Config.SCORE_RATIO;
import static jp.kuroneko.android.musicquiz.configs.Config.QuestionBackground.BACKGROUND_BAD;
import static jp.kuroneko.android.musicquiz.configs.Config.QuestionBackground.BACKGROUND_DEFAULT;
import static jp.kuroneko.android.musicquiz.configs.Config.QuestionBackground.BACKGROUND_GOOD;
import static jp.kuroneko.android.musicquiz.configs.Config.QuestionType.TYPE_INTRODUCTION;

import java.util.HashMap;
import java.util.Map.Entry;

import jp.kuroneko.android.musicquiz.activities.ResultActivity;
import jp.kuroneko.android.musicquiz.ayncs.CounterThread;
import jp.kuroneko.android.musicquiz.ayncs.TimerThread;
import jp.kuroneko.android.musicquiz.configs.Config;
import jp.kuroneko.android.musicquiz.configs.Config.QuestionBackground;
import jp.kuroneko.android.musicquiz.configs.Config.QuestionKind;
import jp.kuroneko.android.musicquiz.configs.Config.QuestionType;
import jp.kuroneko.android.musicquiz.fragments.GameFragment;
import jp.kuroneko.android.musicquiz.parameters.Question;
import jp.kuroneko.android.musicquiz.utilities.ScoreCache;
import jp.kuroneko.android.musicquiz.utilities.Utility;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * ゲーム画面のコントローラ
 * @author kuroneko
 * @author shimadahiroshi
 */

public class GameFragmentController extends BaseFragmentController implements Runnable, OnClickListener{

	/**
	 * タイマのリクエストコード
	 */
	private static final int TIMER_REQUEST = 1;

	/**
	 * カウンタのリクエストコード
	 */
	private static final int COUNTER_REQUEST = 2;

	/**
	 * タイムアップした時に選択したことになる番号
	 */
	private static final int TIMEUP_NUMBER = -1;

	/**
	 * 問題数のインデックス
	 */
	private static final int INDEX_QUESTION_COUNT = 0;

	/**
	 * 正解数のインデックス
	 */
	private static final int INDEX_CORRECT_COUNT = 1;

	/**
	 * インデックスの最大数
	 */
	private static final int MAXIMUM_INDEXES = 2;

	/**
	 * 出題する問題情報を{@link Config#MAXIMUM_QUESTION}の数だけ配列で管理する
	 */
	private Question[] mQuestions;

	/**
	 * タイマスレッド
	 */
	private TimerThread mTimer;

	/**
	 * 残り時間
	 */
	private long mReminingTime;

	/**
	 * 音楽を流すプレイヤ
	 */
	private MediaPlayer mPlayer;

	/**
	 * 現在の問題が何問目かを示す値
	 */
	private int mNumber;

	/**
	 * 問題文を表示する文字列バッファ
	 */
    private StringBuffer mText;

    /**
     * 問題文の文字情報
     */
    private char[] mData;

    /**
     * カウンタスレッド
     */
    private CounterThread mCounter;

    /**
     * スコア
     */
    private int mScore;

    /**
     * 間違えた回数
     */
    private int mWrongAnswer;

    /**
     * ゲーム実行中フラグ
     */
    private boolean mPlayingFlag;

    /**
     * 初回起動フラグ
     */
    private boolean mFirstFlag;

    /**
     * 画面スリープ中フラグ
     */
    private boolean mSleepingFlag;

    /**
     * ジャンルごとの問題の数を数えるために使用する
     */
    private HashMap<QuestionType, HashMap<QuestionKind, int[]>> mGenreCount;

	/**
	 * フラグメントを用いて初期設定を行うコンストラクタ
	 * @param fragment
	 * 	このコントローラを使用するフラグメント
	 */
	public GameFragmentController(Fragment fragment){
		super(fragment);
		return;
	}

	/**
	 * テキストを書き込む
	 * @param msg
	 * 	書き込むメッセージ
	 */
	public void writeText(String msg){
		this.mText = new StringBuffer(String.format(Utility.getResouceString(question_number), this.mNumber + 1));
		this.mData = msg.toCharArray();
		this.mCounter = new CounterThread(mData.length);
		this.runThread(this.mCounter, COUNTER_REQUEST);
		return;
	}

	@Override
	public void onHandleMessage(int requestCode, int resultCode, Object data) {
		super.onHandleMessage(requestCode, resultCode, data);
		switch(requestCode){
			case TIMER_REQUEST:
				this.adjustTimer(resultCode, (Long)data);
				break;

			case COUNTER_REQUEST:
				if(resultCode == CURRENT_COUNT){
					Integer integer = (Integer)data;
					char c = this.mData[integer];
					this.mText.append(c);
					((GameFragment)super.mFragment).getQuestionText().setText(mText);

				}
				break;

			default:
				break;
		}
		return;
	}

	@Override
	public void onStart() {
		super.onStart();
		if(!this.mFirstFlag){
			this.mFirstFlag = true;
			this.mPlayingFlag = true;
			this.start();
		}
		return;
	}


	@Override
	public void onStop() {
		super.onStop();
		if(this.mPlayer != null && this.mPlayer.isPlaying()){
			this.mPlayer.stop();
		}
		return;
	}

	@Override
	public void onPause() {
		super.onPause();
		if(this.mPlayer != null && this.mPlayer.isPlaying()){
			this.mPlayer.pause();
		}
		this.mSleepingFlag = true;
		return;
	}

	@Override
	public void onResume() {
		super.onResume();
		this.mSleepingFlag = false;
		if(!this.mPlayingFlag){
			this.mPlayingFlag = true;
			this.next();
		}
		else if(this.mPlayer != null){
			this.mPlayer.start();
		}
		return;
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.mQuestions = Utility.getRandomQuestion();
		this.mGenreCount = new HashMap<Config.QuestionType, HashMap<QuestionKind,int[]>>();
		for(QuestionType type: QuestionType.values()){
			HashMap<QuestionKind, int[]> map = new HashMap<QuestionKind, int[]>();
			this.mGenreCount.put(type, map);
			for(QuestionKind kind: QuestionKind.values()){
				int[] count = new int[MAXIMUM_INDEXES];
				for(int index = 0; index < MAXIMUM_INDEXES; ++index){
					count[index] = 0;
				}
				map.put(kind, count);
			}
		}
		return;
	}

	@Override
	public void run() {
		if(this.mActivity != null && !this.mActivity.isFinishing()){
			if(MAXIMUM_WRONG_ANSWER <= this.mWrongAnswer || MAXIMUM_QUESTION <= this.mNumber){
				this.saveCache(this.mWrongAnswer < MAXIMUM_WRONG_ANSWER);
				this.moveActivity(ResultActivity.class, true);
			}
			else{
				this.startQuestion();
			}
		}
		return;
	}

	@Override
	public void onClick(View v) {
		int number;
		switch(v.getId()){
			case btnquiz1:
				number = 0;
				break;

			case btnquiz2:
				number = 1;
				break;

			case btnquiz3:
				number = 2;
				break;

			case btnquiz4:
				number = 3;
				break;

			default:
				number = -1;
				break;
		}
		if(this.mTimer != null){
			this.onSelected(number);
		}
		return;
	}


	/**
	 * クイズをスタートする
	 */
	private void start(){
		this.postDelayed(this, QUESTION_INTERVAL);
		return;
	}

	/**
	 * 次の問題へ行く
	 */
	private void next(){
		++this.mNumber;
		this.postDelayed(this, QUESTION_INTERVAL);
		return;
	}

	/**
	 * 問題を開始する
	 */
	private void startQuestion(){
		Question question = this.mQuestions[this.mNumber];
		for(int index = 0; index < MAXIMUM_CHOICES; ++index){
			((GameFragment)this.mFragment).getChoiceButton(index).setText(question.getChoice(index));
		}
		if(question.getType() == TYPE_INTRODUCTION){
			this.mPlayer = MediaPlayer.create(Utility.getInstance(), question.getAnswerData().getUri());
			this.mPlayer.start();
		}
		this.resetLinearBackGround(BACKGROUND_DEFAULT);
		changeDisplayText(View.VISIBLE);
		this.startTimer();
		this.writeText(question.getQuestionText());
		return;
	}

	/**
	 * タイマをスタートさせる
	 */
	private void startTimer(){
		this.mTimer = new TimerThread(QUESTION_TIME_LIMIT);
		this.runThread(this.mTimer, TIMER_REQUEST);
		return;
	}

	/**
	 * タイマの表示を調節する
	 * @param resultCode
	 * 	{@link TimerThread} から渡される結果コード
	 * @param time
	 * 	{@link TimerThread} から渡される残り時間
	 */
	synchronized private void adjustTimer(int resultCode, long time){
		if(this.mTimer != null){
			this.mReminingTime = time;
			switch(resultCode){
				case REMAINING_TIME:
					((GameFragment)this.mFragment).getProgressBar().setProgress((int)(time));
					break;

				case TIMER_FINISHED:
					this.mTimer = null;
					this.timeup();
					break;

				default:
					break;
			}
		}
		return;
	}

	/**
	 * タイムアップした時の処理
	 */
	private void timeup(){
		this.onSelected(TIMEUP_NUMBER);
		return;
	}

	/**
	 * 問題文を表示している背景画像の再設定
	 * @param background
	 * 	背景の種類
	 */
	public void resetLinearBackGround(QuestionBackground background){

		if(background != null){
			((ImageView) ((GameFragment)super.mFragment).getImageFrame())
				.setImageResource(QUESTION_BACKGOUND_RESOURCES[background.ordinal()]);
		}
		return;
	}

	/**
	 * テキストの表示、非表示の切り替え
	 * @param typeView
	 */

	public void changeDisplayText(int typeView){
		switch(typeView){
			case View.VISIBLE:
				((GameFragment)this.mFragment).getQuestionText().setVisibility(View.VISIBLE);
				break;

			case View.INVISIBLE:
				((GameFragment)this.mFragment).getQuestionText().setVisibility(View.INVISIBLE);
				break;

			case View.GONE:
				((GameFragment)this.mFragment).getQuestionText().setVisibility(View.GONE);
				break;
		}
		return;
	}

	/**
	 * 答えを選択した時の処理
	 * @param number
	 * 	選択した番号
	 */
	synchronized private void onSelected(int number){
		Question question = this.mQuestions[this.mNumber];
		int[] count = this.mGenreCount.get(question.getType()).get(question.getKind());
		++count[INDEX_QUESTION_COUNT];
		if(this.mPlayer != null){
			this.mPlayer.stop();
			this.mPlayer = null;
		}
		if(this.mTimer != null){
			this.mTimer.finish();
			this.mTimer = null;
		}
		if(this.mCounter != null){
			this.mCounter.finish();
			this.mCounter = null;
		}
		((GameFragment)super.mFragment).getQuestionText().setText(empty);
		if(number == question.getAnswerNumber()){
			++count[INDEX_CORRECT_COUNT];
			changeDisplayText(View.GONE);
			this.resetLinearBackGround(BACKGROUND_GOOD);
			this.mScore += this.mReminingTime / SCORE_RATIO;
		}
		else{
			++this.mWrongAnswer;
			changeDisplayText(View.GONE);
			this.resetLinearBackGround(BACKGROUND_BAD);
		}
		if(this.mSleepingFlag){
			this.mPlayingFlag = false;
		}
		else{
			this.next();
		}
		return;
	}

	/**
	 * キャッシュに情報を保存する
	 * @param clearFlag
	 * 	クリアした場合は true を、していない場合は false を指定する
	 */
	private void saveCache(boolean clearFlag){
		ScoreCache.incrementPlayCount();
		ScoreCache.saveClear(clearFlag);
		ScoreCache.saveCurrentQuestionCount(this.mNumber);
		ScoreCache.saveCurrentCorrectCount(this.mNumber - this.mWrongAnswer);
		for(Entry<QuestionType, HashMap<QuestionKind, int[]>> types: this.mGenreCount.entrySet()){
			for(Entry<QuestionKind, int[]> kinds: types.getValue().entrySet()){
				int[] count = kinds.getValue();
				ScoreCache.saveGenreResult(types.getKey(), kinds.getKey(), count[INDEX_QUESTION_COUNT], count[INDEX_CORRECT_COUNT]);
			}
		}
		int ranking = -1;
		if(clearFlag){
			ScoreCache.incrementClearCount();
			ScoreCache.saveCurrentScore(this.mScore);
			int[] history = ScoreCache.getHistory();
			int index = 0;
			for(; index < MAXIMUM_HISTORY; ++index){
				if(history[index] < this.mScore){
					ranking = index;
					break;
				}
			}
			for(index = MAXIMUM_HISTORY - 1; ranking < index; --index){
				history[index] = history[index-1];
			}
			history[ranking] = this.mScore;
			ScoreCache.saveHistory(history);
		}
		else{
			ScoreCache.saveCurrentScore(0);
		}
		ScoreCache.saveCurrentRanking(ranking);
		return;
	}

}
