package test;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class TestSpEl {

	public static void main(String[] args) {
		SpelExpressionParser  parser = new SpelExpressionParser();
		EvaluationContext ctx = new StandardEvaluationContext(new Bean());
		ctx.setVariable("var", null);
		
		
		System.out.println(parser.parseExpression("#var.toString()").getValue(ctx));
	}
	
	
	public static class Bean{
		private int x;
		private String y;
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public String getY() {
			return y;
		}
		public void setY(String y) {
			this.y = y;
		}
		
	}
	
}
