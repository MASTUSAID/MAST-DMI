package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.WorkflowActionmapping;

public interface WorkflowActionService {

	@Transactional
	public List<WorkflowActionmapping> getWorkflowActionmapping(Integer workflowid,Integer roleid ,Integer worflowdefId);
	
	@Transactional
	public Integer actionApprove(Long id, long userid, Integer workflowId,String comments );
	
	@Transactional
	public Integer actionReject(Long id, long userid, Integer workflowId,String comments );
	
	@Transactional
	public Integer actionRegister(Long id, long userid, Integer workflowId,String comments );
	
	@Transactional
	public Integer actionVerification(Long id, long userid, Integer workflowId,String comments );
	
	
	@Transactional
	public Integer actiondelete(Long id, long userid, Integer workflowId,String comments );
	
	
}
