package pre;

import java.io.*;

/**
 * �ʷ�����
 */
public class Worlanalysis {
	BufferedReader in;
	String str,str1; // ��ȡһ��
	int Length;// ���ÿ�е��ַ���
	int lineNum = 0;// ����к�
	int i;// ��ŵ�ǰ��ȡ���ַ�λ��
	public char Cha; // ��ŵ�ǰ��ȡ���ַ�
	public boolean isEnd = false;
	static Word KEY[]; // �ؼ��ֱ�
	public Error error;
	public int errorNum = 0; // �ʷ�����������Ŀ

	int len = 10; // �����ʶ������󳤶�
	int nmax = 4;// �����������λ��
	boolean print;
	/**
	 * �ʷ�����
	 * 
	 * @param filename
	 */
	public Worlanalysis(String filename,boolean print) {
		this.print = print;
		try {
			FileReader fr = new FileReader(filename);
			in = new BufferedReader(fr); // BufferedReader��FileReaderЧ�ʸ�
			str = in.readLine().trim(); // ����ǰ���հ׺�β���հ�
			Length = str.length();
			lineNum = 1;
			// ���ı���ͷ�Ŀ��ж�ȥ��
			while (Length == 0) {
				str = in.readLine().trim();
				Length = str.length();
				lineNum++;
			}
			i = 0;
			getchar();
		} catch (FileNotFoundException a) {
			System.out.println("û���ҵ��ļ���");
		} catch (IOException b) {
			System.out.println("�ļ���ȡ����");
		}

		// �ؼ��ֱ�
		KEY = new Word[13];
		KEY[0] = new Word("begin", 'f');
		KEY[1] = new Word("call", 'm');
		KEY[2] = new Word("const", 'c');
		KEY[3] = new Word("do", 'o');
		KEY[4] = new Word("end", 'g');
		KEY[5] = new Word("if", 'p');
		KEY[6] = new Word("odd", 'h');
		KEY[7] = new Word("procedure", 'e');
		KEY[8] = new Word("read", 'i');
		KEY[9] = new Word("then", 'q');
		KEY[10] = new Word("var", 'd');
		KEY[11] = new Word("while", 'n');
		KEY[12] = new Word("write", 'j');
	}

	/**
	 * ��ȡһ���ַ�
	 */
	public void getchar() {
		if (isEnd)
			return;
		try {
			if (i == Length)// ���������β,�����¶�ȡһ��Դ��
			{
				str = in.readLine();
				if (str == null) {
					isEnd = true;
					return;
				}
				str = str.trim();
				Length = str.length();
				lineNum++;
				// ��ȥ����
				while (Length == 0) {
					str1 = in.readLine();
					if (str1 == null) {
						isEnd = true;
						return;
					}
					str = str1.trim();
					Length = str.length();
					lineNum++;
				}
				i = 0;
				Cha = str.charAt(i);
				i++;
			} else {
				Cha = str.charAt(i);
				i++;
			}
		} catch (IOException c) {
			System.out.println("�ַ���ȡ����");
		}
	}

	/**
	 * @return ���ش�����
	 */
	public int getErrorNumber() {
		return errorNum;
	}

	/**
	 * @return ����word����
	 */
	public Word getWord() {
		if (isEnd)
			return null;
		int charNum1;// ��¼��ǰ���ʵĿ�ʼ����
		int charNum2;// ��¼��ǰ���ʵĽ�������
		String tokenName;// ��ǰ����
		// �˵����ʼ�Ŀո�
		while (Cha == ' ') {
			getchar();
		}
		// ʶ���ʶ���͹ؼ���
		if (Cha >= 'a' && Cha <= 'z') {// ����ĸ��ͷ
			charNum1 = i - 1; // ��ʶ������ʼλ��,��Ϊ��getchar()��i++,��������i-1
			charNum2 = i; // ��ʶ�������һ���ַ��ĺ�һ��λ��,Cha��Ӧ������Ϊi-1
			while ((Character.isLetter(Cha) || Character.isDigit(Cha))
					&& i < Length) {
				charNum2 = i;
				getchar();
			}
			/*
			 * �ж�Ϊʲô����whileѭ���� ���Cha������ĸ�������֣�������Ϊ���ж����ֹͣ�ģ���ʱchatNum2=Length-1,
			 * Cha=str.charAt(Length-1)�� ���charNum1��ʼ���ַ����Ͷ��Ǳ�ʶ����
			 * ���Cha������ĸ�����֣����ʾ��Ϊ��������ĸ�����ֶ�ֹͣ
			 */
			if (Character.isLetter(Cha) || Character.isDigit(Cha)) {
				tokenName = str.substring(charNum1);
				getchar();
			} else
				// charNum2-1 ���Ǳ�ʶ�����һ���ַ��ĺ�һ��λ��
				// substring(a,b),������a��b-1��
				tokenName = str.substring(charNum1, charNum2);
			// �����ȳ�����ʶ������󳤶�len�򱨴�
			if ((charNum2 - charNum1) > len) {
				//error.error(lineNum, 19);
				//errorNum++;
				tokenName = str.substring(charNum1, charNum1 + len);
			}

			int n = 0; // ���ڼ�¼������������±�
			// �ж��Ƿ�Ϊ������
			while (n < 13 && (!KEY[n].getName().equals(tokenName))) {
				n++;
			}
			if (n == 13) // ��������ֱ���û���ҵ�,��Ϊ��ʶ��
			{
				return new Word(tokenName, 'b', lineNum);
			} else {
				KEY[n].setLineNum(lineNum);
				return KEY[n];
			}
		}
		// ʶ������
		else if (Character.isDigit(Cha)) {
			charNum1 = i - 1; // ���ֵ���ʼλ��
			charNum2 = i; // ���ֵ����һ���ַ��ĺ�һ��λ��
			while (Character.isDigit(Cha) && i < Length) {
				charNum2 = i;
				getchar();
			}
			// �ж�Ϊʲô����whileѭ�������Cha�������֣����������Ϊ���ж����ֹͣ�ģ����Cha�������֣���ʾ��Ϊ���������ֶ�ֹͣ
			if (Character.isDigit(Cha)) {
				tokenName = str.substring(charNum1);
				getchar();
			} else
				tokenName = str.substring(charNum1, charNum2);
			// �����ȳ������λ��nmax�򱨴�
			if ((charNum2 - charNum1) > nmax) {
				//error.error(lineNum, 8);
				//errorNum++;
				tokenName = tokenName.substring(charNum1, charNum1 + nmax);
			}
			return new Word(tokenName, 'a', lineNum);
		}
		// ʶ������ͽ��
		else if (Cha == ':') {
			getchar();
			if (Cha == '=') {
				getchar();
				return new Word(":=", 't', lineNum);

			} else {
				System.err.println("������ȱ��=!");
				return null;
			}

		} else if (Cha == '<') {
			getchar();
			if (Cha == '=') {
				getchar();
				return new Word("<=", 'r', lineNum);

			} else {
				return new Word("<", '<', lineNum);
			}

		} else if (Cha == '>') {
			getchar();
			if (Cha == '=') {
				getchar();
				return new Word(">=", 's', lineNum);
			} else {
				return new Word(">", '>', lineNum);
			}

		} else {
			char sym = '0';
			String sr = Character.toString(Cha);
			if (Cha == '+')
				sym = '+';
			else if (Cha == '-')
				sym = '-';
			else if (Cha == '*')
				sym = '*';
			else if (Cha == '/')
				sym = '/';
			else if (Cha == '(')
				sym = '(';
			else if (Cha == ')')
				sym = ')';
			else if (Cha == '=')
				sym = '=';
			else if (Cha == ',')
				sym = ',';
			else if (Cha == '.')
				sym = '.';
			else if (Cha == ';')
				sym = ';';
			else if (Cha == '#')
				sym = '#';
			getchar();
			return new Word(sr, sym, lineNum);
		}
	}
}
