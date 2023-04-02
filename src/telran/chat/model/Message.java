package telran.chat.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	String nickName;
	LocalTime time;
	String message;

	public Message(String nickName, String message) {
		this.nickName = nickName;
		this.message = message;
	}


	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return time.truncatedTo(ChronoUnit.SECONDS) + " "+ nickName +" "+ message;
	}

}
