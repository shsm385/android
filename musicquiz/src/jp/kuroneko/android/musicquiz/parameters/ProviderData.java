package jp.kuroneko.android.musicquiz.parameters;

import static android.provider.BaseColumns._COUNT;
import static android.provider.BaseColumns._ID;
import static jp.kuroneko.android.musicquiz.parameters.AutoColumnData.ColumnType.INTEGER;
import static jp.kuroneko.android.musicquiz.parameters.AutoColumnData.ColumnType.LONG;

import java.lang.reflect.Field;

import android.database.Cursor;

/**
 * プロバイダのベースとなる情報のパラメータクラス
 * @author kuroneko
 *
 */
public class ProviderData extends BaseParameter{

	/**
	 * データベースから情報の自動取得する場合のカラムと格納するフィールド
	 */
	private static final AutoColumnData[] COLUMN_DATA= {
		new AutoColumnData(_ID, "mId", LONG),
		new AutoColumnData(_COUNT, "mCount", INTEGER)
	};

	/**
	 * ID
	 */
	protected long mId;

	/**
	 * ディレクトリ内の行数
	 */
	protected int mCount;

	/**
	 * 初期設定を行わないコンストラクタ
	 */
	public ProviderData(){
		super();
		return;
	}

	/**
	 * 各カラムの情報から自動的にフィールドを埋めるためのコンストラクタ
	 * @param cursor
	 * 	各カラム情報
	 */
	public ProviderData(Cursor cursor){
		super();
		try {
			this.setFieldsFromCursor(ProviderData.class, cursor, COLUMN_DATA);
		} catch (IllegalArgumentException e) {
		} catch (NoSuchFieldException e) {
		} catch (IllegalAccessException e) {
		}
		return;
	}

	/**
	 * ID を取得する
	 * @return
	 * 	ID
	 */
	public long getId(){
		return this.mId;
	}

	/**
	 * ID を設定する
	 * @param id
	 * 	ID
	 */
	public void setId(long id){
		this.mId = id;
	}

	/**
	 * ディレクトリ内の行数を取得する
	 * @return
	 * 	ディレクトリ内の行数
	 */
	public int getCount(){
		return this.mCount;
	}

	/**
	 * ディレクトリ内の行数を設定する
	 * @param count
	 * 	ディレクトリ内の行数
	 */
	public void setCount(int count){
		this.mCount = count;
	}

	/**
	 * 書くカラム情報からフィールドに値を設定する
	 * @param myClass
	 * 	解析する現在のクラス
	 * @param cursor
	 * 	各カラム除法
	 * @param columns
	 * 	自動でカラム除法をフィールドに設定するための情報
	 * @throws NoSuchFieldException
	 * 	指定したフィールド名が、このクラスに存在しない場合に発生する例外
	 * @throws IllegalArgumentException
	 * 	リフレクション使用時に引数が誤っていた場合に発生する例外
	 * @throws IllegalAccessException
	 * 	リフレクション時に指定したフィールドにアクセス出来なかった場合に発生する例外
	 */
	protected void setFieldsFromCursor(Class<?> current, Cursor cursor, AutoColumnData[] columns) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		for(AutoColumnData column: columns){
			int index = cursor.getColumnIndex(column.getColumn());
			if(index != -1){
				Field field = current.getDeclaredField(column.getField());
				field.setAccessible(true);
				this.setFieldEveryType(field, column, cursor, index);
			}
		}
		return;
	}

	/**
	 * タイプ毎にフィールドの値を設定する
	 * @param field
	 * 	設定するフィールド
	 * @param column
	 * 	設定する型
	 * @param cursor
	 * 	各カラム情報
	 * @param index
	 * 	カラムのインデックス
	 * @throws IllegalArgumentException
	 * 	リフレクション使用時に引数が誤っていた場合に発生する例外
	 * @throws IllegalAccessException
	 * 	リフレクション時に指定したフィールドにアクセス出来なかった場合に発生する例外
	 */
	private void setFieldEveryType(Field field, AutoColumnData column, Cursor cursor, int index) throws IllegalArgumentException, IllegalAccessException{
		switch(column.getType()){
			case SHORT:
				field.setShort(this, cursor.getShort(index));
				break;

			case INTEGER:
				field.setInt(this, cursor.getInt(index));
				break;

			case LONG:
				field.setLong(this, cursor.getLong(index));
				break;

			case FLOAT:
				field.setFloat(this, cursor.getFloat(index));
				break;

			case DOUBLE:
				field.setDouble(this, cursor.getDouble(index));
				break;

			case BOOLEAN:
				field.setBoolean(this, cursor.getInt(index) != 0);
				break;

			case STRING:
				field.set(this, cursor.getString(index));
				break;

			default:
				break;
		}
		return;
	}

}
