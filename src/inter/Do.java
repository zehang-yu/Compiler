package inter;

import symbols.Type;

public class Do extends Stmt {

   Expr expr; Stmt stmt;

   public Do() { expr = null; stmt = null; }

   // do stmt while(expr);
   
   public void init(Stmt s, Expr x) {
      expr = x; stmt = s;
      if( expr.type != Type.Bool ) expr.error("boolean required in do");
   }

   public void gen(int b, int a) {
      after = a;
      int label = newlabel();   // label for expr
      stmt.gen(b,label);
      emitlabel(label);
      expr.jumping(b,0);
   }

   @Override
   public void display() {
      emit("stmt??do begin");
      stmt.display();
      emit("stmt: do end");
   }
}