package com.promotion.aggregate.base;

public interface BaseProcess<Q,R,S> {
	R process(Q req,S shareData);

}
