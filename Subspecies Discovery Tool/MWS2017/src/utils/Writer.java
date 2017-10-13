//このプログラムはファイルへの書き込みをメインとしたクラスです

package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Writer extends Thread {
	// このメソッドは一行ごとに書き込みます。改行あり。追記式。
	// input ファイルパス,書き込むテキスト
	// output なし
	public static void writeline(File filePath, String text) {
		try {
			PrintWriter filewriter = new PrintWriter(
					new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), "SHIFT-JIS")));
			filewriter.println(text);
			filewriter.close();
		} catch (IOException e) {
			System.out.println("Write.writeline Error:IOException");
			System.out.println(e);
		}
	}

	// このメソッドはファイルに改行を含まない形で書き込みます。追記式。
	// input ファイルパス,書き込むテキスト
	// output なし
	public static void write(File filePath, String text) {
		try {
			PrintWriter filewriter = new PrintWriter(
					new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), "SHIFT-JIS")));
			filewriter.print(text);
			filewriter.close();
		} catch (IOException e) {
			System.out.println("Write.writeline Error:IOException");
			System.out.println(e);
		}
	}

	// このメソッドはファイルを作成するのみです。
	// input ファイルパス
	// return true
	public static boolean creater(File file) {
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.out.println(e);
			return false;
		}
		return true;
	}
}
