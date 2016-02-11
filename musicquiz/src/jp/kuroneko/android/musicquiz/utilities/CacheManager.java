package jp.kuroneko.android.musicquiz.utilities;

import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * キャッシュ管理を行うためのクラス
 * @author kuroneko
 *
 */
public class CacheManager extends Object{

	/**
	 * システム固定パラメータと自由に扱えるパラメータを区別するために使用するプレフィックス
	 */
	private static final String PREFIX = "K-S_";

	/**
	 * キャッシュの書き込みを行うためのエディタ
	 */
	protected Editor mEditor;

	/**
	 * キャッシュ操作を行うための {@link SharedPreferences}
	 */
	private SharedPreferences mPreferences;

	/**
	 * キャッシュ管理を行うためのコンストラクタ
	 * @param name
	 * 	キャッシュファイル名
	 * @param mode
	 * 	アクセス権
	 */
	public CacheManager(String name, int mode){
		super();
		this.mPreferences = Utility.getInstance().getSharedPreferences(name, mode);
		this.mEditor = this.mPreferences.edit();
		return;
	}

	/**
	 * プライベート設定でキャッシュ管理を行うためのコンストラクタ
	 * @param name
	 * 	キャッシュファイル名
	 */
	public CacheManager(String name){
		this(name, MODE_PRIVATE);
		return;
	}

	/**
	 * int 型の値をキャッシュに保存する
	 * @param key
	 * 	キャッシュのキー
	 * @param value
	 * 	キャッシュに保存する値
	 */
	public void save(String key, int value){
		this.save(key, value, true);
		return;
	}

	/**
	 * long 型の値をキャッシュに保存する
	 * @param key
	 * 	キャッシュのキー
	 * @param value
	 * 	キャッシュに保存する値
	 */
	public void save(String key, long value){
		this.save(key, value, true);
		return;
	}

	/**
	 * boolean 型の値をキャッシュに保存する
	 * @param key
	 * 	キャッシュのキー
	 * @param value
	 * 	キャッシュに保存する値
	 */
	public void save(String key, boolean value){
		this.save(key, value, true);
		return;
	}

	/**
	 * float 型の値をキャッシュに保存する
	 * @param key
	 * 	キャッシュのキー
	 * @param value
	 * 	キャッシュに保存する値
	 */
	public void save(String key, float value){
		this.save(key, value, true);
		return;
	}

	/**
	 * 文字列をキャッシュに保存する
	 * @param key
	 * 	キャッシュのキー
	 * @param value
	 * 	キャッシュに保存する値
	 */
	public void save(String key, String value){
		this.save(key, value, true);
		return;
	}

	/**
	 * {@link Object#toString} で取得出来る値をキャッシュに保存する
	 * @param key
	 * 	キャッシュのキー
	 * @param value
	 * 	キャッシュに保存する値
	 */
	@Deprecated
	public void save(String key, Object value){
		this.save(key, value, true);
		return;
	}

	/**
	 * int 型の値をキャッシュから取得する
	 * @param key
	 * 	キャッシュのキー
	 * @param defaultValue
	 * 	キーが存在しない場合のデフォルト値
	 * @return
	 * 	キャッシュから取得した値
	 */
	public int getInt(String key, int defaultValue){
		return this.getInt(key, defaultValue, true);
	}

	/**
	 * long 型の値をキャッシュから取得する
	 * @param key
	 * 	キャッシュのキー
	 * @param defaultValue
	 * 	キーが存在しない場合のデフォルト値
	 * @return
	 * 	キャッシュから取得した値
	 */
	public long getLong(String key, long defaultValue){
		return this.getLong(key, defaultValue, true);
	}

	/**
	 * float 型の値をキャッシュから取得する
	 * @param key
	 * 	キャッシュのキー
	 * @param defaultValue
	 * 	キーが存在しない場合のデフォルト値
	 * @return
	 * 	キャッシュから取得した値
	 */
	public float getFloat(String key, float defaultValue){
		return this.getFloat(key, defaultValue, true);
	}

	/**
	 * boolean 型の値をキャッシュから取得する
	 * @param key
	 * 	キャッシュのキー
	 * @param defaultValue
	 * 	キーが存在しない場合のデフォルト値
	 * @return
	 * 	キャッシュから取得した値
	 */
	public boolean getBoolean(String key, boolean defaultValue){
		return this.getBoolean(key, defaultValue, true);
	}

	/**
	 * 文字列をキャッシュから取得する
	 * @param key
	 * 	キャッシュのキー
	 * @param defaultValue
	 * 	キーが存在しない場合のデフォルト値
	 * @return
	 * 	キャッシュから取得した値
	 */
	public String getString(String key, String defaultValue){
		return this.getString(key, defaultValue, true);
	}

	/**
	 * キャッシュから指定した情報を削除する
	 * @param key
	 * 	削除するキャッシュのキー
	 */
	public void remove(String key){
		this.remove(key, true);
		return;
	}

	/**
	 * キャッシュ内にキーが存在するかどうかを取得する
	 * @param key
	 * 	存在確認を行うキー
	 * @return
	 * 	キーが存在する場合に true を、存在しない場合に false を返す
	 */
	public boolean contains(String key){
		return this.contains(key, true);
	}

	/**
	 * キャッシュ情報を全て削除する
	 */
	public void clear(){
		this.mEditor.clear().commit();
		return;
	}

	/**
	 * int 型の値をキャッシュに保存する
	 * @param key
	 * 	キャッシュのキー
	 * @param value
	 * 	キャッシュに保存する値
	 * @param prefixFlag
	 * 	プレフィックスを付けて保存する場合のフラグ<br/>
	 *  基本的にはプレフィックスをつける仕様<br/>
	 *  但し、システム内で固定パラメータと、自由に扱うパラメータを区別するために、プレフィックスを付けないことも可能<br/>
	 *  プレフィックスは{@link CacheManager#PREFIX}の値になる
	 */
	protected void save(String key, int value, boolean prefixFlag){
		this.mEditor
			.putInt(CacheManager.getKey(key, prefixFlag), value)
			.commit();
		return;
	}

	/**
	 * long 型の値をキャッシュに保存する
	 * @param key
	 * 	キャッシュのキー
	 * @param value
	 * 	キャッシュに保存する値
	 * @param prefixFlag
	 * 	プレフィックスを付けて保存する場合のフラグ<br/>
	 *  基本的にはプレフィックスをつける仕様<br/>
	 *  但し、システム内で固定パラメータと、自由に扱うパラメータを区別するために、プレフィックスを付けないことも可能<br/>
	 *  プレフィックスは{@link CacheManager#PREFIX}の値になる
	 */
	protected void save(String key, long value, boolean prefixFlag){
		this.mEditor
			.putLong(CacheManager.getKey(key, prefixFlag), value)
			.commit();
		return;
	}

	/**
	 * boolean 型の値をキャッシュに保存する
	 * @param key
	 * 	キャッシュのキー
	 * @param value
	 * 	キャッシュに保存する値
	 * @param prefixFlag
	 * 	プレフィックスを付けて保存する場合のフラグ<br/>
	 *  基本的にはプレフィックスをつける仕様<br/>
	 *  但し、システム内で固定パラメータと、自由に扱うパラメータを区別するために、プレフィックスを付けないことも可能<br/>
	 *  プレフィックスは{@link CacheManager#PREFIX}の値になる
	 */
	protected void save(String key, boolean value, boolean prefixFlag){
		this.mEditor
			.putBoolean(CacheManager.getKey(key, prefixFlag), value)
			.commit();
		return;
	}

	/**
	 * float 型の値をキャッシュに保存する
	 * @param key
	 * 	キャッシュのキー
	 * @param value
	 * 	キャッシュに保存する値
	 * @param prefixFlag
	 * 	プレフィックスを付けて保存する場合のフラグ<br/>
	 *  基本的にはプレフィックスをつける仕様<br/>
	 *  但し、システム内で固定パラメータと、自由に扱うパラメータを区別するために、プレフィックスを付けないことも可能<br/>
	 *  プレフィックスは{@link CacheManager#PREFIX}の値になる
	 */
	protected void save(String key, float value, boolean prefixFlag){
		this.mEditor
			.putFloat(CacheManager.getKey(key, prefixFlag), value)
			.commit();
		return;
	}

	/**
	 * 文字列をキャッシュに保存する
	 * @param key
	 * 	キャッシュのキー
	 * @param value
	 * 	キャッシュに保存する値
	 * @param prefixFlag
	 * 	プレフィックスを付けて保存する場合のフラグ<br/>
	 *  基本的にはプレフィックスをつける仕様<br/>
	 *  但し、システム内で固定パラメータと、自由に扱うパラメータを区別するために、プレフィックスを付けないことも可能<br/>
	 *  プレフィックスは{@link CacheManager#PREFIX}の値になる
	 */
	protected void save(String key, String value, boolean prefixFlag){
		this.mEditor
			.putString(CacheManager.getKey(key, prefixFlag), value)
			.commit();
		return;
	}

	/**
	 * {@link Object#toString} で取得出来る値をキャッシュに保存する
	 * @param key
	 * 	キャッシュのキー
	 * @param value
	 * 	キャッシュに保存する値
	 * @param prefixFlag
	 * 	プレフィックスを付けて保存する場合のフラグ<br/>
	 *  基本的にはプレフィックスをつける仕様<br/>
	 *  但し、システム内で固定パラメータと、自由に扱うパラメータを区別するために、プレフィックスを付けないことも可能<br/>
	 *  プレフィックスは{@link CacheManager#PREFIX}の値になる
	 */
	@Deprecated
	protected void save(String key, Object value, boolean prefixFlag){
		this.mEditor
			.putString(CacheManager.getKey(key, prefixFlag), value == null ? null : value.toString())
			.commit();
		return;
	}

	/**
	 * int 型の値をキャッシュから取得する
	 * @param key
	 * 	キャッシュのキー
	 * @param defaultValue
	 * 	キーが存在しない場合のデフォルト値
	 * @param prefixFlag
	 * 	プレフィックスを付けて保存する場合のフラグ<br/>
	 *  基本的にはプレフィックスをつける仕様<br/>
	 *  但し、システム内で固定パラメータと、自由に扱うパラメータを区別するために、プレフィックスを付けないことも可能<br/>
	 *  プレフィックスは{@link CacheManager#PREFIX}の値になる
	 * @return
	 * 	キャッシュから取得した値
	 */
	protected int getInt(String key, int defaultValue, boolean prefixFlag){
		return this.mPreferences.getInt(CacheManager.getKey(key, prefixFlag), defaultValue);
	}

	/**
	 * long 型の値をキャッシュから取得する
	 * @param key
	 * 	キャッシュのキー
	 * @param defaultValue
	 * 	キーが存在しない場合のデフォルト値
	 * @param prefixFlag
	 * 	プレフィックスを付けて保存する場合のフラグ<br/>
	 *  基本的にはプレフィックスをつける仕様<br/>
	 *  但し、システム内で固定パラメータと、自由に扱うパラメータを区別するために、プレフィックスを付けないことも可能<br/>
	 *  プレフィックスは{@link CacheManager#PREFIX}の値になる
	 * @return
	 * 	キャッシュから取得した値
	 */
	protected long getLong(String key, long defaultValue, boolean prefixFlag){
		return this.mPreferences.getLong(CacheManager.getKey(key, prefixFlag), defaultValue);
	}

	/**
	 * float 型の値をキャッシュから取得する
	 * @param key
	 * 	キャッシュのキー
	 * @param defaultValue
	 * 	キーが存在しない場合のデフォルト値
	 * @param prefixFlag
	 * 	プレフィックスを付けて保存する場合のフラグ<br/>
	 *  基本的にはプレフィックスをつける仕様<br/>
	 *  但し、システム内で固定パラメータと、自由に扱うパラメータを区別するために、プレフィックスを付けないことも可能<br/>
	 *  プレフィックスは{@link CacheManager#PREFIX}の値になる
	 * @return
	 * 	キャッシュから取得した値
	 */
	protected float getFloat(String key, float defaultValue, boolean prefixFlag){
		return this.mPreferences.getFloat(CacheManager.getKey(key, prefixFlag), defaultValue);
	}

	/**
	 * boolean 型の値をキャッシュから取得する
	 * @param key
	 * 	キャッシュのキー
	 * @param defaultValue
	 * 	キーが存在しない場合のデフォルト値
	 * @param prefixFlag
	 * 	プレフィックスを付けて保存する場合のフラグ<br/>
	 *  基本的にはプレフィックスをつける仕様<br/>
	 *  但し、システム内で固定パラメータと、自由に扱うパラメータを区別するために、プレフィックスを付けないことも可能<br/>
	 *  プレフィックスは{@link CacheManager#PREFIX}の値になる
	 * @return
	 * 	キャッシュから取得した値
	 */
	protected boolean getBoolean(String key, boolean defaultValue, boolean prefixFlag){
		return this.mPreferences.getBoolean(CacheManager.getKey(key, prefixFlag), defaultValue);
	}

	/**
	 * 文字列をキャッシュから取得する
	 * @param key
	 * 	キャッシュのキー
	 * @param defaultValue
	 * 	キーが存在しない場合のデフォルト値
	 * @param prefixFlag
	 * 	プレフィックスを付けて保存する場合のフラグ<br/>
	 *  基本的にはプレフィックスをつける仕様<br/>
	 *  但し、システム内で固定パラメータと、自由に扱うパラメータを区別するために、プレフィックスを付けないことも可能<br/>
	 *  プレフィックスは{@link CacheManager#PREFIX}の値になる
	 * @return
	 * 	キャッシュから取得した値
	 */
	protected String getString(String key, String defaultValue, boolean prefixFlag){
		return this.mPreferences.getString(CacheManager.getKey(key, prefixFlag), defaultValue);
	}

	/**
	 * キャッシュから指定した情報を削除する
	 * @param key
	 * 	削除するキャッシュのキー
	 * @param prefixFlag
	 * 	プレフィックスを付けて保存する場合のフラグ<br/>
	 *  基本的にはプレフィックスをつける仕様<br/>
	 *  但し、システム内で固定パラメータと、自由に扱うパラメータを区別するために、プレフィックスを付けないことも可能<br/>
	 *  プレフィックスは{@link CacheManager#PREFIX}の値になる
	 */
	protected void remove(String key, boolean prefixFlag){
		this.mEditor.remove(CacheManager.getKey(key, prefixFlag)).commit();
		return;
	}

	/**
	 * キャッシュ内にキーが存在するかどうかを取得する
	 * @param key
	 * 	存在確認を行うキー
	 * @param prefixFlag
	 * 	プレフィックスを付けて保存する場合のフラグ<br/>
	 *  基本的にはプレフィックスをつける仕様<br/>
	 *  但し、システム内で固定パラメータと、自由に扱うパラメータを区別するために、プレフィックスを付けないことも可能<br/>
	 *  プレフィックスは{@link CacheManager#PREFIX}の値になる
	 * @return
	 * 	キーが存在する場合に true を、存在しない場合に false を返す
	 */
	protected boolean contains(String key, boolean prefixFlag){
		return this.mPreferences.contains(CacheManager.getKey(key, prefixFlag));
	}

	/**
	 * キャッシュファイルはだれでも見れるため、キーがわからないように難読化を行う
	 * @param key
	 * 	難読化前のキー
	 * @return
	 * 	難読化語のキー
	 */
	protected static String getKey(String key, boolean prefixFlag){
		return CacheManager.getKey(prefixFlag ? PREFIX + key : key);
	}

	/**
	 * キャッシュファイルはだれでも見れるため、キーがわからないように難読化を行う
	 * @param key
	 * 	難読化前のキー
	 * @return
	 * 	難読化語のキー
	 */
	private static String getKey(String key){
		StringBuilder builder = new StringBuilder();
		int index = 4;
		int temporary = 0x0;
		int connector = 0x0;
		for(byte value: key.getBytes()){
			connector += value;
			if(index == 0){
				index = 4;
				builder.append(String.format("%08x",temporary));
				temporary = 0x0;
				value = (byte)(0xFF&connector);
			}
			index--;
			temporary |= (0xFF & (((value + index) & 1) == 0 ? value : ~value)) << (index * 8);
		}
		builder.append(String.format("%0" + ((4 - index) * 2) + "x", temporary >>> (index * 8)));
		return builder.toString();
	}

}
