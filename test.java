public class test {
    public static int calculate(String exp){
        
    String[] tokens = exp.split("(?=[-+*/])|(?<=[-+*/])");
    
    int result = Integer.parseInt(tokens[0]);
    
    for(int i = 1; i < tokens.length; i += 2){
        
        String op = tokens[i];
        int num = Integer.parseInt(tokens[i+1]);
        
        switch(op){
            case "+": result += num; break;
            case "-": result -= num; break;
            case "*": result *= num; break;
            case "/": result /= num; break;
        }
    }
    
    return result;
}
    public static void main(String[] args) {
        String x = "1+2*3+4";
        System.err.println(calculate(x));
    }
}
