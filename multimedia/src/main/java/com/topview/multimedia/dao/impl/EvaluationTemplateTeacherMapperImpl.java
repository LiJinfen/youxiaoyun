package com.topview.multimedia.dao.impl;

import org.springframework.stereotype.Repository;

import com.topview.multimedia.dao.EvaluationTemplateTeacherMapper;
import com.topview.multimedia.dao.base.impl.BaseDaoImpl;
import com.topview.multimedia.po.EvaluationTemplateTeacher;

/** * @author  Rachel 
@date 创建时间：2016年9月20日 下午8:21:43 * 
@version 1.0 * 
@parameter  *
 @since  * 
@return  */
@Repository
public class EvaluationTemplateTeacherMapperImpl extends BaseDaoImpl<EvaluationTemplateTeacher>
		implements EvaluationTemplateTeacherMapper {

	@Override
	public int deleteByTeacher(EvaluationTemplateTeacher ett) {
		// TODO Auto-generated method stub
		return template.delete(getFirstInterface() + ".deleteByTeacher", ett);
	}

	
	
}
