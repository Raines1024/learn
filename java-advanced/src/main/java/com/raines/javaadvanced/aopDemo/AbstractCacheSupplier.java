package com.raines.javaadvanced.aopDemo;


import com.raines.javaadvanced.aopDemo.aop.CacheListener;

public abstract class AbstractCacheSupplier implements CacheSupplier {

	protected AbstractCacheSupplier() {
		CacheListener.registSupplier(this);
	}
}
