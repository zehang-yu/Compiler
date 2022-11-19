package error;

import java.util.Stack;

public class checker {
    public static String msg = "mismatch";
    MyError myError;
    Stack<ErrMsg> stk = null;
    String lch, rch;

    public checker(String lch, String rch, MyError myError) {
        this.lch = new String(lch);
        this.rch = new String(rch);
        stk = new Stack<ErrMsg>();
        this.myError = myError;
    }

    public void push(int line, String ch) {
        if(ch.contentEquals(lch)) {
            stk.push(new ErrMsg(line, msg + lch));
        } else {
            if(stk.isEmpty()) {
                myError.push(new ErrMsg(line, msg + rch));
            } else {
                stk.pop();
            }
        }
    }


}
