package jp.kuroneko.android.musicquiz.ayncs;

import static jp.kuroneko.android.musicquiz.configs.Config.COUNTER_INTERVAL;
import jp.kuroneko.android.musicquiz.configs.Config;
import jp.kuroneko.android.musicquiz.controllers.BaseController;

/**
 * カウンタとなるスレッドクラス<br/>
 * このカウンタスレッドを実行した際に渡される情報は<br/>
 * {@link BaseController#onHandleMessage} に渡される情報は以下の通りに成る<br/>
 * 	<table>
 * 		<tr><th>第一引数</th><th>第二引数</th><th>第三引数</th></tr>
 * 		<tr><td>指定したリクエストコード</td><td>{@link CounterThread#CURRENT_COUNT}</td><td>{@link Integer} で現在のカウント数</td></tr>
 * 		<tr><td>指定したリクエストコード</td><td>{@link CounterThread#COUNTER_FINISHED}</td><td>{@link Integer} でカウンタに設定したカウント数</td></tr>
 * 	</table>
 * @author kuroneko
 *
 */
public class CounterThread extends BaseControlThread {

	/**
	 * 現在のカウント数のリザルトコード
	 */
	public static final int CURRENT_COUNT = 1;

	/**
	 * カウンタが終了した場合のリザルトコード
	 */
	public static final int COUNTER_FINISHED = 2;

	/**
	 * カウントする数
	 */
	private int mCount;

	/**
	 * インターバル時間 [ms]
	 */
	private long mInterval = COUNTER_INTERVAL;

	/**
	 * カウント数を設定するコンストラクタ<br/>
	 * インターバルタイムはデフォルト値である {@link Config#COUNTER_INTERVAL} を使用する
	 * @param count
	 * 	カウンタに設定するカウント数
	 */
	public CounterThread(int count) {
		super();
		if(count <= 0){
			throw new RuntimeException("illegal count exception: count=" + count);
		}
		this.mCount = count;
		return;
	}

	/**
	 * カウント数を設定するコンストラクタ
	 * @param timer
	 * 	カウンタに設定するカウント数
	 * @param interval
	 *  タイマの経過時間を確認するインターバルタイム
	 */
	public CounterThread(int count, long interval) {
		this(count);
		if(interval <= 0){
			throw new RuntimeException("illegal time exception: interval=" + interval);
		}
		this.mInterval = interval;
		return;
	}

	@Override
	public void run() {
		super.run();
		for(int count = 0; !this.mFinishFlag && count < this.mCount; count++){
			this.sendMessage(CURRENT_COUNT, Integer.valueOf(count));
			try {
				Thread.sleep(this.mInterval);
			} catch (InterruptedException e) {
				break;
			}
		}
		this.sendMessage(COUNTER_FINISHED, Long.valueOf(this.mCount));
		this.free();
		return;
	}

}
