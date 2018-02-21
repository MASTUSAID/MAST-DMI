package com.rmsi.mast.studio.service;

import java.util.List;

import com.rmsi.mast.studio.domain.UserProject;

public interface UserProjectService {

	List<UserProject> findAllUserProjectByUserID(Long id);
}
