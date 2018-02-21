/**
 * 
 */
package com.rmsi.mast.studio.mobile.service;

import java.util.List;

import com.rmsi.mast.studio.domain.ProjectArea;

/**
 * @author Shruti.Thakur
 *
 */
public interface ProjectService {

	public List<ProjectArea> getProjectArea(String projectName);

}
