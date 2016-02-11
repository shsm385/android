package jp.kuroneko.android.musicquiz.fragments;

import static android.view.View.VISIBLE;
import static jp.kuroneko.android.musicquiz.R.array.game_over_face;
import static jp.kuroneko.android.musicquiz.R.array.result_ranking;
import static jp.kuroneko.android.musicquiz.R.id.btnRetry;
import static jp.kuroneko.android.musicquiz.R.id.btnTitle;
import static jp.kuroneko.android.musicquiz.R.id.clear;
import static jp.kuroneko.android.musicquiz.R.id.faceText;
import static jp.kuroneko.android.musicquiz.R.id.gameover;
import static jp.kuroneko.android.musicquiz.R.id.introduction_correct;
import static jp.kuroneko.android.musicquiz.R.id.score;
import static jp.kuroneko.android.musicquiz.R.id.rankingText;
import static jp.kuroneko.android.musicquiz.R.id.text_correct;
import static jp.kuroneko.android.musicquiz.R.layout.fragment_result;
import static jp.kuroneko.android.musicquiz.R.string.result_count;
import static jp.kuroneko.android.musicquiz.R.string.result_score;
import static jp.kuroneko.android.musicquiz.configs.Config.MAXIMUM_HISTORY;
import static jp.kuroneko.android.musicquiz.configs.Config.QuestionType.TYPE_INTRODUCTION;
import static jp.kuroneko.android.musicquiz.configs.Config.QuestionType.TYPE_JACKET;
import static jp.kuroneko.android.musicquiz.configs.Config.QuestionType.TYPE_TEXT;

import java.util.Random;

import jp.kuroneko.android.musicquiz.configs.Config.QuestionKind;
import jp.kuroneko.android.musicquiz.configs.Config.QuestionType;
import jp.kuroneko.android.musicquiz.controllers.ResultFragmentController;
import jp.kuroneko.android.musicquiz.utilities.ScoreCache;
import jp.kuroneko.android.musicquiz.utilities.Utility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 結果表示画面のフラグメント
 * @author kuroneko
 *
 */
public class ResultFragment extends BaseFragment {

	/**
	 * コントローラの初期設定を行うコンストラクタ
	 */
	public ResultFragment(){
		super();
		this.mController = new ResultFragmentController(this);
		return;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(fragment_result, container, false);
		ResultFragmentController controller = (ResultFragmentController)this.mController;
		view.findViewById(btnTitle).setOnClickListener(controller);
		view.findViewById(btnRetry).setOnClickListener(controller);
		if(ScoreCache.isClear()){
			this.createClearView(view);
		}
		else{
			this.createGameOverView(view);
		}
		return view;
	}

	/**
	 * クリア時のビューを生成する
	 * @param root
	 * 	ルートとなるビュー
	 */
	private void createClearView(View root){
		root.findViewById(clear).setVisibility(VISIBLE);
		int ranking = ScoreCache.getCurrentRanking();
		if(ranking < 0 || MAXIMUM_HISTORY <= ranking){
			ranking = MAXIMUM_HISTORY;
		}
		String[] texts = Utility.getInstance().getResources().getStringArray(result_ranking);
		((TextView)root.findViewById(rankingText)).setText(texts[ranking]);
		String format = Utility.getResouceString(result_score);
		((TextView)root.findViewById(score)).setText(String.format(format, ScoreCache.getCurrentScore()));
		int[] resources = new int[QuestionType.values().length];
		resources[TYPE_INTRODUCTION.ordinal()] = introduction_correct;
		resources[TYPE_TEXT.ordinal()] = text_correct;
		resources[TYPE_JACKET.ordinal()] = -1;
		for(QuestionType type: QuestionType.values()){
			int question = 0;
			int correct = 0;
			for(QuestionKind kind: QuestionKind.values()){
				question += ScoreCache.getCurrentGenreCount(type, kind);
				correct += ScoreCache.getCurrentGenreCorrectCount(type, kind);
			}
			int index = type.ordinal();
			if(resources[index] != -1){
				format = Utility.getResouceString(result_count);
				((TextView)root.findViewById(resources[index])).setText(String.format(format, question, correct));
			}
		}
		return;
	}

	/**
	 * ゲームオーバ時のビューを生成する
	 * @param root
	 * 	ルートとなるビュー
	 */
	private void createGameOverView(View root){
		root.findViewById(gameover).setVisibility(VISIBLE);
		Random random = new Random();
		String[] texts = Utility.getInstance().getResources().getStringArray(game_over_face);
		((TextView)root.findViewById(faceText)).setText(texts[random.nextInt(texts.length)]);
		return;
	}

}
