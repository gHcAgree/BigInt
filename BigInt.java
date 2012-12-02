
public class BigInt {
	
	public String add(String a, String b) {
		if(a==null||a.equals("")||b==null||b.equals("")) return null;
		
		char signA = a.charAt(0);
		char signB = b.charAt(0);
		boolean negtive = false;
		
		if(signA=='-'&&signB=='-') {
			a = a.substring(1);
			b = b.substring(1);
			negtive = true;
		}
		else if(signA=='-') {
			a = a.substring(1);
			return sub(b,a);
		}
		else if(signB=='-') {
			b = b.substring(1);
			return sub(a,b);
		}
		
		int carry = 0; int tmp = 0; String result = "";
		char ca, cb;
		
		int length = a.length()>b.length()?a.length():b.length();
		if(length>a.length()) a = appendZero(a,length);
		if(length>b.length()) b = appendZero(b,length);
		
		int i = length-1;
		
		while(i>=0) {
			ca = a.charAt(i);
			cb = b.charAt(i);
			if(!isDigit(ca)||!isDigit(cb)) {
				System.err.println("Invalid number!");
				return null;
			}
			tmp = (ca-'0')+(cb-'0')+carry;
			carry = tmp/10;
			tmp %= 10;
			
			result = tmp + result;
			i--;
		}
		
		if(carry!=0) result = carry + result;
		
		if(negtive) result = "-" + result;
		
		return result;
	}
	
	public String sub(String a, String b) {
		if(a==null||a.equals("")||b==null||b.equals("")) return null;
		
		char signA = a.charAt(0);
		char signB = b.charAt(0);
		boolean negtive = false;
		
		if(signA=='-'&&signB=='-') {
			a = b.substring(1);
			b = a.substring(1);
		}
		else if(signA=='-') {
			b = "-"+b;
			return add(a,b);
		}
		else if(signB=='-') {
			b = b.substring(1);
			return add(a,b);
		}
		
		if(!notLessThan(a,b)) {
			negtive = true;
			String t = a;
			a = b;
			b = t;
		}
		
		int carry = 0; int tmp = 0; String result = "";
		char ca, cb;
		
		int length = a.length()>b.length()?a.length():b.length();
		if(length>a.length()) a = appendZero(a,length);
		if(length>b.length()) b = appendZero(b,length);
		
		int i = length-1;
		while(i>=0) {
			ca = a.charAt(i);
			cb = b.charAt(i);
			if(!isDigit(ca)||!isDigit(cb)) {
				System.err.println("Invalid number!");
				return null;
			}
			tmp = ca-cb-carry;
			if(tmp<0) { tmp+=10; carry++; }
			else carry = 0;
			
			result = tmp + result;
			i--;
		}
		
		result = trimZero(result);
		
		if(negtive) result = "-" + result;
		
		return result;
	}
	
	public String multiply(String a, String b) {
		if(a==null||b==null) return null;
		
		char signA = a.charAt(0);
		char signB = b.charAt(0);
		boolean negtive = false;
		
		if(signA=='-'&&signB=='-') {
			a = b.substring(1);
			b = a.substring(1);
		}
		else if(signA=='-') {
			a = a.substring(1);
			negtive = true;
		}
		else if(signB=='-') {
			b = b.substring(1);
			negtive = true;
		}
		
		String[] pResult = new String[10];
		pResult[0] = "0";
		pResult[1] = a;

		for(int k=2;k<10;k++) {
			pResult[k] = add(pResult[k-1],a);
		}
		
		String result = "0"; char cb;
		int length = b.length();
		
		for(int i=length-1;i>=0;i--) {
			cb = b.charAt(i);
			if(!isDigit(cb)) {
				System.err.println("Invalid number!");
				return null;
			}
			result = add(result,power10(pResult[cb-'0'],length-1-i));
		}
		
		if(negtive) result = "-"+result;
		
		return result;
	}
	
	public String divide(String a, String b) {
		if(a==null||b==null) return null;
		
		char signA = a.charAt(0);
		char signB = b.charAt(0);
		boolean negtive = false;
		
		if(signA=='-'&&signB=='-') {
			a = b.substring(1);
			b = a.substring(1);
		}
		else if(signA=='-') {
			a = a.substring(1);
			negtive = true;
		}
		else if(signB=='-') {
			b = b.substring(1);
			negtive = true;
		}
		
		if(!notLessThan(a,b)) return "0";
		
		String[] pResult = new String[10];
		pResult[0] = "0";
		pResult[1] = b;
		for(int k=2;k<10;k++)
			pResult[k] = add(pResult[k-1],b);
		
		String result = "";
		
		int aLength = a.length();
		int bLength = b.length();
		String tmpA = a.substring(0,bLength-1);
		for(int i=bLength;i<=aLength;i++) {
			tmpA += a.charAt(i-1);
			
			int k;
			for(k=9;k>0;k--) {
				if(notLessThan(tmpA,pResult[k])) {
					result += k;
					tmpA = sub(tmpA,pResult[k]);
					break;
				}
			}
			
			if(k==0) result += "0"; 
		}
			
		if(negtive) result = "-" + result;
		
		return result;
	}
	
	private boolean isDigit(char c) {
		if(c>='0'&&c<='9') return true;
		return false;
	}
	
	private String appendZero(String a, int length) {
		if(a==null) return null;
		
		while(a.length()<length) a = "0"+a;
		return a;
	}
	
	private String trimZero(String a) {
		if(a==null) return null;
		
		int i=0;
		while(i<a.length()-1) {
			if(a.charAt(i)=='0') i++;
			else {
				break;
			}
		}
		a = a.substring(i);
		
		return a;
	}
	
	private String power10(String a,int n) {
		if(a==null||n<0) return null;
		
		for(int i=0;i<n;i++) {
			a = a+"0";
		}
		
		return a;
	}
	
	private boolean notLessThan(String a, String b) {
		boolean bigger = true;
		if(a.length()>b.length()) bigger = true;
		else if(a.length()<b.length()) bigger = false;
		else {
			for(int i=0;i<a.length();i++) {
				int diff = a.charAt(i)-b.charAt(i);
				if(diff<0) { bigger = false; break; }
			}
		}
		
		return bigger;
	}
	
	public static void main(String[] args) {
		BigInt bi = new BigInt();
		System.out.println(bi.add("123456789", "876543211"));
		System.out.println(bi.sub("123456789", "123456798"));
		System.out.println(bi.multiply("11111", "11111"));
		System.out.println(bi.divide("11111", "-11111"));

	}
}
