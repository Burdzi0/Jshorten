package shortener.url;

import shortener.url.service.factory.DefaultUrlCreator;

public class Main {

	public static void main(String[] args) {
		new Server<>(new DefaultUrlCreator()).serve();
	}
}
