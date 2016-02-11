package jp.kuroneko.android.musicquiz.fragments;

import static jp.kuroneko.android.musicquiz.R.id.image_flame;
import static jp.kuroneko.android.musicquiz.R.id.progressBar1;
import static jp.kuroneko.android.musicquiz.R.id.text_view;
import static jp.kuroneko.android.musicquiz.R.layout.fragment_game;
import static jp.kuroneko.android.musicquiz.configs.Config.MAXIMUM_CHOICES;
import static jp.kuroneko.android.musicquiz.configs.Config.QUESTION_TIME_LIMIT;
import jp.kuroneko.android.musicquiz.configs.Config;
import jp.kuroneko.android.musicquiz.controllers.GameFragmentController;
import jp.kuroneko.android.musicquiz.utilities.Utility;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * ゲーム画面のフラグメント
 * @author kuroneko
 * @author shimadahiroshi
 *
 */
public class GameFragment extends BaseFragment implements InputFilter{

	private TextView mQuestionText;

	private ImageView mImage;

	/**
	 * 選択肢のボタン
	 */
	private Button[] mChoiceButtons = new Button[MAXIMUM_CHOICES];

	/**
	 * プログレスバー
	 */
	private ProgressBar mProgressBar;

	/**
	 * コントローラの初期設定を行うコンストラクタ
	 */
	public GameFragment(){
		super();
		this.mController = new GameFragmentController(this);
		return;
	}

	/**
	 * プログレスバーを表示
	 * @param view
	 */
	public ProgressBar createProgressBar(View view){
		ProgressBar bar = (ProgressBar)view.findViewById(progressBar1);
	    bar.setMax((int)QUESTION_TIME_LIMIT);
	    bar.setProgress((int)QUESTION_TIME_LIMIT);
	    return bar;
	}

	public TextView getQuestionText(){
		return this.mQuestionText;
	}

	public View getImageFrame(){
		return this.mImage;
	}

	/**
	 * 音楽はとりあえず問題が始まったらスタートしっぱなしかつid固定になっています（今のところ）
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		GameFragmentController controller = (GameFragmentController)this.mController;
		View view = inflater.inflate( fragment_game , container , false );
		this.mQuestionText = (TextView)view.findViewById(text_view);
		this.mQuestionText.setFilters(new InputFilter[]{this});
		this.mImage = (ImageView)view.findViewById(image_flame);
		this.mProgressBar = createProgressBar(view);
		for(int index = 0; index < MAXIMUM_CHOICES; ++index){
			Button button = (Button)view.findViewById(Utility.getResouoceId("btnquiz" + (index + 1), "id"));
			button.setOnClickListener(controller);
			this.mChoiceButtons[index] = button;
		}
		return view;
	}

	/**
	 * 選択肢のボタンを取得する
	 * @param index
	 * 	選択肢のボタンのインデックス<br/>
	 *  {@link Config#MAXIMUM_CHOICES}の数だけ取得可能
	 * @return
	 */
	public Button getChoiceButton(int index){
		return this.mChoiceButtons[index];
	}

	/**
	 * 残り時間を表示するプログレスバーを取得する
	 * @return
	 * 	プログレスバー
	 */
	public ProgressBar getProgressBar(){
		return this.mProgressBar;
	}

	@Override
	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {
		TextPaint paint = this.mQuestionText.getPaint();
		int width = this.mQuestionText.getWidth() - this.mQuestionText.getCompoundPaddingLeft() - this.mQuestionText.getCompoundPaddingRight();
		SpannableStringBuilder builder = new SpannableStringBuilder();
		for(int index = start; index < end; ++index){
			if(Layout.getDesiredWidth(source, start, index + 1, paint) > width){
				builder.append(source.subSequence(start, index));
				builder.append("\n");
				start = index;
			}
			else if(source.charAt(index) == '\n'){
				builder.append(source.subSequence(start, index));
				start = index;
			}
		}
		if(start < end){
			builder.append(source.subSequence(start, end));
		}
		return builder;
	}

}
