package com.mingi.jpaexs;

import java.io.Serializable;
import java.util.Objects;

public class MemberProductId implements Serializable {
	
	private Long member;
	
	private long product;
	
	@Override
	public boolean equals(Object o) {
		boolean ret = false;
		
		if(this == o) {
			ret = true;
		}
		return ret;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(member, product);
	}

}
