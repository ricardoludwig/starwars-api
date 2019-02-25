package br.com.ricardo.starwars.adapter;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PageAdapter<T> extends PageImpl<T> {

	private static final long serialVersionUID = 1L;

	private long total;
	private long size;

	public PageAdapter(List<T> content) {
		super(content);
		this.total = super.getTotalElements();
		this.size = content.size();
	}

	public PageAdapter(List<T> content, Pageable pageable, long total) {
		super(content, pageable, total);
		this.total = total;
		this.size = content.size();
	}

	@Override
	public long getTotalElements() {
		return total;
	}
	
	@Override
	public int getTotalPages() {
		return size < 2 ? 1 : (int) Math.ceil((double) total / (double) size);
	}

}
