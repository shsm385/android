package jp.kuroneko.android.musicquiz.parameters;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class BaseParameter extends Object{

	/**
	 * フィールド情報を連結して文字列として取得する
	 * @return
	 * 	フィールド情報を連結した文字列
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		this.appendStringForClass(builder, this.getClass(), 0);
		return builder.toString();
	}

	/**
	 * フィールド情報を連結して、インデントが設定された文字列として取得する
	 * @param space
	 * 	インデントを行う際にスペースの数
	 *  0 を指定するとインデント無しと同じになる
	 * @return
	 * 	フィールド情報を連結した文字列
	 */
	public String toString(int space){
		StringBuilder builder = new StringBuilder();
		this.appendStringForClass(builder, this.getClass(), space);
		return builder.toString();
	}

	/**
	 * クラス内の情報を文字列に変換して、第一引数 {@link StringBuilder} に追加していく
	 * @param builder
	 * 	文字列を作成するビルだ
	 * @param current
	 * 	文字列に変換するクラス
	 * @param spaces
	 * 	インデントを入れるスペースの数
	 *  0 の場合はインデント無し
	 * @param nest
	 *  現在のネスト
	 */
	private void appendStringForClass(StringBuilder builder, Class<?> current, int spaces, int nest){
		boolean flag = spaces == 0;
		builder.append(flag ? "{" : "{\n");
		String indent = this.getSpaces(spaces * nest);
		for(Field field: current.getDeclaredFields()){
			if(Modifier.isStatic(field.getModifiers())){
				continue;
			}
			builder.append(indent + field.getName() + ": ");
			try {
				field.setAccessible(true);
				builder.append(field.get(this));
			} catch (Exception e) {
			}
			builder.append(flag ? ", " : ",\n");
		}
		current = current.getSuperclass();
		if(!current.equals(BaseParameter.class)){
			builder.append(indent + "super: ");
			this.appendStringForClass(builder, current, spaces, nest + 1);
		}
		else{
			int length = builder.length();
			builder.delete(length - 2, length);
			if(!flag){
				builder.append("\n");
			}
		}
		builder.append(this.getSpaces(spaces * (nest - 1)) + (flag ? "}" : "}\n"));
		return;
	}

	/**
	 * クラス内の情報を文字列に変換して、第一引数 {@link StringBuilder} に追加していく
	 * @param builder
	 * 	文字列を作成するビルだ
	 * @param current
	 * 	文字列に変換するクラス
	 * @param spaces
	 * 	インデントを入れるスペースの数
	 *  0 の場合はインデント無し
	 */
	private void appendStringForClass(StringBuilder builder, Class<?> current, int spaces){
		this.appendStringForClass(builder, current, spaces, spaces == 0 ? 0 : 1);
		return;
	}

	/**
	 * スペースを取得する
	 * @param spaces
	 * 	スペースの数
	 * @return
	 * 	spaces で指定された数のスペース
	 */
	private String getSpaces(int spaces){
		StringBuilder builder = new StringBuilder();
		for(int index = 0; index < spaces; index++){
			builder.append(" ");
		}
		return builder.toString();
	}

}
