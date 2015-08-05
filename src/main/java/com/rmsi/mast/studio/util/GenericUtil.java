package com.rmsi.mast.studio.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericUtil {

	public static boolean isUserInValidRole (String roleIdToMatch, int roleId){
		List<String> list = new ArrayList<String>(Arrays.asList(roleIdToMatch.split(",")));
		if(list.contains(""+roleId)){
			return true;
		}else{
			return false;
		}
	}
	
	
}
