package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import lexer.Token;
import parser.Parser;
import lexer.Lexer;


public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// 获取文件
		File file = new File("D:/test.txt");
		Reader reader = null;
		reader = new InputStreamReader(new FileInputStream(file));
		//reader = new InputStreamReader(System.in);
		// 新建一个词法解析器，将文件传给lexer，由它读取文件内容
		Lexer lex = new Lexer(reader);
//		do {
//			Token token = lex.scan();
//			switch (token.tag) {
//				case 270:
//				case 272:
//					System.out.println("(NUM , "+token.toString()+")");
//					break;
//				case 264:
//					System.out.println("(ID , "+token.toString()+")");
//					break;
//				case 256:
//				case 257:
//				case 258:
//				case 259:
//				case 260:
//				case 265:
//				case 274:
//				case 275:
//					System.out.println("(KEY , "+token.toString()+")");
//					break;
//				case 13:
//					break;
//				default:
//					System.out.println("(SYM , "+token.toString()+")");
//					break;
//			}
//		} while(lex.getPeek()!='\n');
		Parser parser = new Parser(lex);//语法分析
		parser.program();
		System.out.print("\n");
	}


}
