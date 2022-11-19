package error;

import java.util.Stack;

public class MyError {

    Stack<ErrMsg> stk = null;
    public MyError() {
        stk = new Stack<>();
    }
    public void push(ErrMsg errMsg) {
        stk.push(errMsg);
    }

    public void errorReport() {
        while(!stk.empty()) {
            ErrMsg errMsg = stk.pop();
            System.out.println("error: line "+errMsg.line +" "+ errMsg.error);
        }
    }
}
