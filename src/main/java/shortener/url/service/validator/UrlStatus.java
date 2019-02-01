package shortener.url.service.validator;

public enum UrlStatus {
	GOOD(0), WRONG(-1), NO_PROTOCOL(1);

	private int status;

	UrlStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}