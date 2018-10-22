package com.rmsi.mast.studio.dao;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.LaRrr;

public interface LaRrrDAO extends GenericDAO<LaRrr, Integer>{
	@Transactional
	LaRrr addLaRrr(LaRrr larrrObject);

}
