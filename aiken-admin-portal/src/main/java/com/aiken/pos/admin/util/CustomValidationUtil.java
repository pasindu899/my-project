package com.aiken.pos.admin.util;

/** 
 * Custom Common Validations
 * 
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-21
 */
public final class CustomValidationUtil {

	public static boolean isNullOrZero(Long id) {
		if (id == null || id == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isNullOrZero(Integer id) {
		if (id == null || id == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isSameId(Long id1, Long id2) {
		 if(!isNullOrZero(id1) && !isNullOrZero(id2)) {
			 if(id1.longValue() == id2.longValue())
				 return true;
		 }
		 
		 return false;
	}
	
	public static boolean isSameId(Integer id1, Integer id2) {
		 if(!isNullOrZero(id1) && !isNullOrZero(id2)) {
			 if(id1.intValue() == id2.intValue())
				 return true;
		 }
		 
		 return false;
	}
}