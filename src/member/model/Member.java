package member.model;

/* Member클래스(유사 MemberVO 클래스)
 * : DB에서 가져온 board.member테이블의 데이터를 담을 때 사용하는 클래스
 */
public class Member {
	
	private String id; //아이디고
	private String name; //이름이다
	private String password;//비번
	
	public Member(String id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}// 이 클래스를 참조하는 객체를 생성자로 생성하면 들어온 인수를 여기 멤버변수에 넣는거고

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	
	 /* matchPassword() 메서드
		: 암호변경 기능 구현시 사용함(원래 암호와 맞는지 확인하는 기능) */
	public boolean matchPassword(String pwd) { //이 메서드는 암호 변경 기능 구현시 사용함
		return password.equals(pwd);
	}
	
	public void changePassword(String newPwd) {//이 메서드는 암호 변경 기능 구현시 사용함22
		this.password=newPwd;
	}
}
