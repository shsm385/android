package jp.kuroneko.android.musicquiz.controllers;

import java.util.ArrayList;

import jp.kuroneko.android.musicquiz.ayncs.BaseControlThread;
import jp.kuroneko.android.musicquiz.ayncs.ControlHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

/**
 * 全てのコントローラのベースとなる抽象クラス
 * @author kuroneko
 *
 */
public abstract class BaseController extends Object{

	/**
	 * 現在のアクティビティ
	 */
	protected static Activity sCurrentActivity;

	/**
	 * アクティビティ
	 */
	protected Activity mActivity;

	/**
	 * デバイスキーの戻るボタンで戻る処理を許可する
	 */
	protected boolean mBackFlag = true;

	/**
	 * スレッド処理を管理するためのハンドラクラス
	 */
	private ControlHandler mHandler;

	/**
	 * スレッドのリスト
	 */
	private ArrayList<BaseControlThread> mThreads;

	/**
	 * 初期設定にアクティビティを用いるコンストラクタ
	 * @param activity
	 * 	アクティビティ
	 */
	public BaseController(Activity activity){
		super();
		this.setActivity(activity);
		this.initialize();
		return;
	}

	/**
	 * 継承時にアクティビティの初期設定を自動で行いたくない場合に用いるコンストラクタ
	 */
	protected BaseController(){
		super();
		return;
	}

	/**
	 * 現在のアクティビティを設定する
	 * @param activity
	 * 	アクティビティ
	 */
	public static void setCurrentActivity(Activity activity){
		BaseController.sCurrentActivity = activity;
		return;
	}

	/**
	 * アクティビティを設定する
	 * @param activity
	 * 	アクティビティ
	 */
	public void setActivity(Activity activity){
		this.mActivity = activity;
		return;
	}

	/**
	 * デバイスキーの戻るボタンで戻る処理を許可するフラグを設定する
	 * @param flag
	 * 	true を指定した場合はデバイスキーの戻るボタンで戻る事が可能となる
	 */
	public void setBackFlag(boolean flag){
		this.mBackFlag = flag;
		return;
	}

	/**
	 * デバイスキーの戻るボタンで戻る処理が許可されているかどうかを取得する
	 * @return
	 * 	許可されている場合は true を返す
	 */
	public boolean isEnabledForBack(){
		return this.mBackFlag;
	}

	/**
	 * {@link Activity#onCreate} が呼び出された時に実行する
	 * @param savedInstanceState
	 * 	{@link Activity#onCreate} の第一引数
	 */
	public void onCreate(Bundle savedInstanceState){
		return;
	}

	/**
	 * {@link Activity#onDestory} が呼び出された時に実行する
	 */
	public void onDestroy(){
		return;
	}

	/**
	 * {@link Activity#onKeyDown} が呼び出された時に実行する
	 * @param result
	 * 	{@link Activity#onKeyDown} の結果値
	 * @param keyCode
	 * 	{@link Activity#onKeyDown} の第一引数
	 * @param keyEvent
	 * 	{@link Activity#onKeyDown} の第二引数
	 * @return
	 * 	{@link Activity#onKeyDown} の返り値として渡す値
	 */
	public boolean onKeyDown(boolean result, int keyCode, KeyEvent keyEvent){
		return result;
	}

	/**
	 * {@link Activity#onActivityResult} が呼び出された時に実行する
	 * @param requestCode
	 * 	{@link Activity#onActivityResult} の第一引数
	 * @param resultCode
	 * 	{@link Activity#onActivityResult} の第二引数
	 * @param data
	 * 	{@link Activity#onActivityResult} の第三引数
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		return;
	}

	/**
	 * {@link Activity#finish} が呼び出された時に実行する
	 */
	public void finish(){
		this.mActivity = null;
		if(this.mThreads != null){
			for(BaseControlThread thread: this.mThreads){
				thread.finish();
			}
			this.mThreads.clear();
			this.mThreads = null;
		}
		if(this.mHandler != null){
			this.mHandler.free();
			this.mHandler = null;
		}
		return;
	}

	/**
	 * {@link Activity#onPause} が呼び出された時に実行する
	 */
	public void onPause(){
		return;
	}

	/**
	 * {@link Activity#onRestart} が呼び出された時に実行する
	 */
	public void onRestart(){
		return;
	}

	/**
	 * {@link Activity#onResume} が呼び出された時に実行する
	 */
	public void onResume(){
		return;
	}

	/**
	 * {@link Activity#onStart} が呼び出された時に実行する
	 */
	public void onStart(){
		return;
	}

	/**
	 * {@link Activity#onStop} が呼び出された時に実行する
	 */
	public void onStop(){
		return;
	}

	/**
	 * {@link Activity#onSaveInstanceState} が呼び出された時に実行する
	 * @param aState
	 * 	{@link Activity#onSaveInstanceState} の第一引数
	 */
	public void onSaveInstanceState(Bundle state){
		return;
	}

	/**
	 * スレッドから送られてきたメッセージをハンドリングした場合に呼び出される
	 * @param requestCode
	 * 	スレッドに設定したリクエストコード
	 * @param resultCode
	 * 	スレッドが設定したリザルトコード
	 * @param data
	 * 	スレッドから送信されてきたデータ
	 */
	public void onHandleMessage(int requestCode, int resultCode, Object data){
		return;
	}

	/**
	 * スレッドを実行する
	 * @param thread
	 * 	実行するスレッド
	 * @param requestCode
	 * 	スレッドに設定するリクエストコード<br/>
	 *  スレッドからの結果を取得する際の {@link BaseController#onHandleMessage} の第一引数に設定される
	 */
	protected final void runThread(BaseControlThread thread, int requestCode){
		this.initializeHandler();
		this.mHandler.relation(thread, requestCode);
		thread.start();
		return;
	}

	/**
	 * コンストラクタが呼ばれたタイミングで初期設定を行う
	 * どのコンストラクタが呼ばれても必ず実行される
	 */
	protected void initialize(){
		return;
	}

	/**
	 * アクティビティを移動する
	 * @param activity
	 * 	移動するアクティビティのクラス
	 * @param finish
	 * 	現在開いているアクティビティを終了する場合は true を、終了しない場合は false を指定する
	 */
	protected void moveActivity(Class<? extends Activity> activity, boolean finishFlag){
		Intent intent = new Intent(this.mActivity, activity);
		this.mActivity.startActivity(intent);
		if(finishFlag){
			this.mActivity.finish();
		}
		return;
	}

	/**
	 * アクティビティを移動して、移動先のアクティビティから結果を取得する
	 * @param activity
	 * 	移動するアクティビティのクラス
	 * @param requestCode
	 * 	移動先のアクティビティに送信するリクエストコード
	 */
	protected void moveActivity(Class<? extends Activity> activity, int requestCode){
		Intent intent = new Intent(this.mActivity, activity);
		this.mActivity.startActivityForResult(intent, requestCode);
		this.mActivity.startActivity(intent);
		return;
	}

	/**
	 * 指定した時間後にタスクを実行する
	 * @param task
	 * 	遅延実行を行うタスク
	 * @param delayTime
	 * 	どれだけ遅延させるかを指定する時間 [ms]
	 */
	protected void postDelayed(Runnable task, long delayTime){
		this.initializeHandler();
		this.mHandler.postDelayed(task, delayTime);
		return;
	}

	/**
	 * スレッドからの処理をハンドリングするためのハンドラの初期設定を行う
	 */
	private void initializeHandler(){
		if(this.mHandler == null){
			this.mHandler = new ControlHandler(this);
			this.mThreads = new ArrayList<BaseControlThread>();
		}
		return;
	}
}
