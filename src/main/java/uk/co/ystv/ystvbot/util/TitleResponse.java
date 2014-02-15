package uk.co.ystv.ystvbot.util;

import lombok.Getter;

public class TitleResponse {

	@Getter private final String url;
	@Getter private final String title;
	@Getter private final int depth;

	public TitleResponse(String url, String title, int depth) {
		this.url = url;
		this.title = title;
		this.depth = depth;
	}

}
