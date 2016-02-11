package jp.kuroneko.android.musicquiz.parameters;

/**
 * 自動的にカラム情報をフィールドに格納する場合に用いるパラメータクラス
 * @author kuroneko
 *
 */
final class AutoColumnData {

	/**
	 * データベースから自動的にデータを格納する場合の型
	 * @author kuroneko
	 *
	 */
	protected static enum ColumnType {SHORT, INTEGER, LONG, FLOAT, DOUBLE, BOOLEAN, STRING, OBJECT};

	/**
	 * カラム名
	 */
	private String mColumn;

	/**
	 * フィールド名
	 */
	private String mField;

	/**
	 * 型
	 */
	private ColumnType mType;

	/**
	 * 初期値を設定するためのコンストラクタ
	 * @param column
	 * 	カラム名
	 * @param field
	 * 	フィールド名
	 * @param type
	 * 	型
	 */
	public AutoColumnData(String column, String field, ColumnType type){
		super();
		this.mColumn = column;
		this.mField = field;
		this.mType = type;
		return;
	}

	/**
	 * カラム名を取得する
	 * @return mColumn
	 * 	カラム名
	 */
	public String getColumn() {
		return mColumn;
	}

	/**
	 * カラム名を設定する
	 * @param mColumn
	 * 	カラム名
	 */
	public void setColumn(String column) {
		this.mColumn = column;
	}

	/**
	 * フィールド名を取得する
	 * @return mField
	 * 	フィールド名
	 */
	public String getField() {
		return mField;
	}

	/**
	 * フィールド名を設定する
	 * @param mField セットする mField
	 * 	フィールド名
	 */
	public void setField(String field) {
		this.mField = field;
	}

	/**
	 * 型を取得する
	 * @return mType
	 * 	型
	 */
	public ColumnType getType() {
		return mType;
	}

	/**
	 * 型を設定する
	 * @param mType セットする mType
	 * 	型
	 */
	public void setType(ColumnType type) {
		this.mType = type;
	}

}
