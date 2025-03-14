import java.awt.*;
import java.awt.event.*;
class MyCalc extends WindowAdapter implements ActionListener {
	private Frame f;
	private Label l1;
	private Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0;
	private Button bAdd, bSub, bMult, bDiv, bMod, bCalc, bClr, bPts, bNeg, bBack;
	
	private StringBuilder currentInput;
	private double num1, num2;
	private int operation;
	
	MyCalc(){
		f = new Frame("MyCalculator");
		f.setSize(360,500);
		f.setLayout(null);
		f.setResizable(false);
		
		currentInput = new StringBuilder();
		
		//Labels
		l1 = new Label();
		l1.setBackground(Color.LIGHT_GRAY);
		l1.setBounds(50,50,260,60);
		
		setupButtons();
		addComponentsToFrame();
		
		f.addWindowListener(this);
		f.setVisible(true);
	}
	
	
	private void setupButtons() {
		b1 = createButton("1",50,340);
		b2 = createButton("2",120,340);
		b3 = createButton("3",190,340);
		b4 = createButton("4",50,270);
		b5 = createButton("5",120,270);
		b6 = createButton("6",190,270);
		b7 = createButton("7",50,200);
		b8 = createButton("8",120,200);
		b9 = createButton("9",190,200);
		b0 = createButton("0",120,410);
		
		bNeg = createButton("+/-",50,410);
		bPts = createButton(".",190,410);
		bBack = createButton("Back",120,130);
		
		bAdd = createButton("+",260,340);
		bSub = createButton("-",260,270);
		bMult = createButton("*",260,200);
		bDiv = createButton("/",260,130);
		bMod = createButton("%",190,130);
		bCalc = createButton("=",260,410);
		bClr = createButton("CE",50,130);
	}
	
	private Button createButton(String label, int x, int y)
	{
		Button button = new Button(label);
		button.setBounds(x,y,50,50);
		button.addActionListener(this);
		return button;
	}
	
	private void addComponentsToFrame() {
		f.add(l1);
		f.add(b1);
		f.add(b2);
		f.add(b3);
		f.add(b4);
		f.add(b5);
		f.add(b6);
		f.add(b7);
		f.add(b8);
		f.add(b9);
		f.add(b0);
		
		f.add(bAdd);
		f.add(bSub);
		f.add(bMod);
		f.add(bMult);
		f.add(bDiv);
		f.add(bCalc);
		f.add(bClr);
		f.add(bPts);
		f.add(bNeg);
		f.add(bBack);
		
		
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		f.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		//check if a number button is clicked
		if (source==b1) addDigit("1");
		else if(source==b2) addDigit("2");
		else if(source==b3) addDigit("3");
		else if(source==b4) addDigit("4");
		else if(source==b5) addDigit("5");
		else if(source==b6) addDigit("6");
		else if(source==b7) addDigit("7");
		else if(source==b8) addDigit("8");
		else if(source==b9) addDigit("9");
		else if(source==b0) addDigit("0");
		
		else if(source==bPts) addDecimal();
		else if(source==bNeg) toggleNegative();
		else if(source==bBack) backspace();
		else if(source==bAdd) prepareOperation(1);
		else if(source==bSub) prepareOperation(2);
		else if(source==bMult) prepareOperation(3);
		else if(source==bDiv) prepareOperation(4);
		else if(source==bMod) prepareOperation(5);
		else if(source==bCalc) calculateResult();
		else if(source==bClr) clear();
		
	}
	private void addDigit(String digit)
	{
		currentInput.append(digit);
		l1.setText(currentInput.toString());
	}
	
	private void addDecimal() {
		if(currentInput.indexOf(".")==-1) {
			currentInput.append(".");
			l1.setText(currentInput.toString());
		}
	}
	
	private void toggleNegative() {
		if(currentInput.length()>0) {
			if(currentInput.charAt(0)=='-') {
				currentInput.deleteCharAt(0);
			}
			else
			{
				currentInput.insert(0,"-");
			}
			l1.setText(currentInput.toString());
		}
	}
	
	private void backspace() {
		if(currentInput.length()>0) {
			currentInput.deleteCharAt(currentInput.length()-1);
			l1.setText(currentInput.toString());
		}
	}
	private void prepareOperation(int optCode) {
		if(currentInput.length()>0)
		{
			num1 = Double.parseDouble(currentInput.toString());
			operation = optCode;
			currentInput.setLength(0);
			l1.setText("");
		}
	}
	private void calculateResult() {
		if(currentInput.length()>0) {
			num2 = Double.parseDouble(currentInput.toString());
			double result = switch (operation) {
			case 1 -> num1+num2;
			case 2 -> num1-num2;
			case 3 -> num1*num2;
			case 4 -> num1/num2;
			case 5 -> num1%num2;
			default -> 0;
			};
			l1.setText(String.valueOf(result));
			currentInput.setLength(0);
			currentInput.append(result);
		}
	}
	private void clear() {
		num1 = 0;
		num2 = 0;
		operation = 0;
		currentInput.setLength(0);
		l1.setText("");
	}
	
	public static void main(String[] args)
	{
		new MyCalc();
	}
}
