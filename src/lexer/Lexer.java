package lexer;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;

import error.ErrMsg;
import error.MyError;
import symbols.Type;

public class Lexer {
	public static int line = 1;
	char peek = ' ';
	Hashtable words = new Hashtable();
	Reader reader = null;
	Boolean isSigNotes;
	Boolean isMulNotes;
	//MyError myError;
	void reserve(Word w) {
		words.put(w.lexeme, w);
		//System.out.println("("+w.tag+","+w.lexeme+")");
	}//把word放到哈希表里
	
	public Lexer(Reader r)	{
		//保留选定的关键字
		reserve(new Word("if", Tag.IF));
		reserve(new Word("else", Tag.ELSE));
		reserve(new Word("while", Tag.WHILE));
		reserve(new Word("do", Tag.DO));
		reserve(new Word("break", Tag.BREAK));
		reserve(new Word("for", Tag.FOR));
		reserve(new Word("switch", Tag.SWITCH));
		reserve(new Word("case", Tag.CASE));
		reserve(new Word("default", Tag.DEFAULT));

		//保留在其他地方定义的对象的词素
		reserve(Word.True); reserve(Word.False);
		reserve(Type.Int); reserve(Type.Char);
		reserve(Type.Bool); reserve(Type.Float);
		reader = r;
	}

	//用于把下一个输入字符读到变量peek中
	void readch() throws IOException {
		//peek = (char)System.in.read();
		peek = (char)reader.read();
	}
	//重载readch，进行检查
	boolean readch(char c) throws IOException{
		readch();
		if(peek!=c) return false;
		peek = ' ';
		return true;
	}



	public Token scan() throws IOException{
		//略过所有的空白行
		for(;;readch()){
			if(peek==' '||peek=='\t'||peek=='\r') continue;
			else if(peek=='\n') line = line + 1;
			else break;
		}

		//首先识别类似<=的复合词法单元 或者 365，3.14这样的数字，如果不成功就尝试读入一个字符串
		switch(peek){
		case '&':
			if(readch('&')) {
				//System.out.println("("+Word.and.tag+","+Word.and.lexeme+")");
				return Word.and;
			}else return new Token('&');
		case '|':
			if(readch('|')) {
				return Word.or;
			} else return new Token('|');
		case '=':
			if(readch('=')){
				return Word.eq;
			} else return new Token('=');
		case '!':
			if(readch('=')) {
				return Word.ne;
			} else return new Token('!');
		case '<':
			if(readch('=')) {
				return Word.le;
			} else return new Token('<');
		case '>':
			if(readch('=')) {
				return Word.ge;
			} else return new Token('>');
		case '/':
			if (readch('/')) {
				isSigNotes = true;
			} else if (getPeek() == '*') {
				isMulNotes = true;
			}
			return new Token('/');
		case '*':
			if (readch('/')) {
				isMulNotes = false;
			} else {
				return new Token('*');
			}
			return new Token(13);

		}

		//若首字符是数字，则一直读取整数部分，直到遇到一个非数字字符，
		// 若不是小数点结尾，则识别了一个整型常量，否则读取小数部分，识别了一个浮点型常量。
		if(Character.isDigit(peek)){
			Token tok = null;
			int v = 0;
			do{
				v=10*v+Character.digit(peek, 10); readch();
			}while(Character.isDigit(peek));
			if(peek!='.') {
				tok = new Num(v);
				//System.out.println("("+ tok.tag +","+ v +")");
				return tok;
			}
			float x = v; float d = 10;
			for(;;){
				readch();
				if(!Character.isDigit(peek)) break;
				x = x + Character.digit(peek, 10)/d; d=d*10;
			}
			tok = new Real(x);
			//System.out.println("("+ tok.tag +","+ x +")");
			return tok;
		}
		
		if(Character.isLetter(peek)){
			StringBuffer b = new StringBuffer();
			do{
				b.append(peek); readch();
			}while(Character.isLetterOrDigit(peek));
			String s=b.toString();
			Word w = (Word)words.get(s);
			if(w!=null) {
				return w;
			}
			w = new Word(s, Tag.ID);
			words.put(s, w);
			return w;
		}

		//最后peek中任意字符都被作为词法单元返回
		Token tok = new Token(peek);
//		String s = tok.toString();
//		if(!"+-*/%<<=>>==!=&&||==!;,.[](){}\"".contains(s)){
//			ErrMsg errMsg = new ErrMsg(line,s + " is invalid");
//			myError.push(errMsg);
//		}
		peek=' ';
		return tok;

	}

	public char getPeek() {
		return peek;
	}

	public void setPeek(char peek) {
		this.peek = peek;
	}

	public void out() {
		System.out.println(words.size());

	}
}
