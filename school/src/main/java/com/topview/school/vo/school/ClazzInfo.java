package com.topview.school.vo.school;

import java.util.ArrayList;
import java.util.List;

import com.topview.school.po.Clazz;

public class ClazzInfo {
	private String id;
    private String tScGradeId;
    private String name;
    private String sortName;
    private String comment;
    private String info;
    private Integer quality;
    private String headTeacher; //班主任姓名
    private String headTeacherId;//班主任id
    
    //选课部分增加显示接口
    private String curriculaId;
    private String curriculaName;
    private String curriculaTeacherName;
    private String curriculaTeacherId;

    
    public String getHeadTeacherId() {
		return headTeacherId;
	}
	
	public void setHeadTeacherId(String headTeacherId) {
		this.headTeacherId = headTeacherId;
	}

	public String getCurriculaId() {
		return curriculaId;
	}

	public void setCurriculaId(String curriculaId) {
		this.curriculaId = curriculaId;
	}

	public String getCurriculaName() {
		return curriculaName;
	}

	public void setCurriculaName(String curriculaName) {
		this.curriculaName = curriculaName;
	}

	public String getCurriculaTeacherName() {
		return curriculaTeacherName;
	}

	public void setCurriculaTeacherName(String curriculaTeacherName) {
		this.curriculaTeacherName = curriculaTeacherName;
	}

	public String getCurriculaTeacherId() {
		return curriculaTeacherId;
	}

	public void setCurriculaTeacherId(String curriculaTeacherId) {
		this.curriculaTeacherId = curriculaTeacherId;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String gettScGradeId() {
        return tScGradeId;
    }

    public void settScGradeId(String tScGradeId) {
        this.tScGradeId = tScGradeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }
    
	public String getHeadTeacher() {
		return headTeacher;
	}

	public void setHeadTeacher(String headTeacher) {
		this.headTeacher = headTeacher;
	}

	public static ClazzInfo changeToVo(Clazz clazz){
		ClazzInfo info = new ClazzInfo();
    	info.setComment(clazz.getComment());
    	info.setId(clazz.getId());
    	info.setInfo(clazz.getInfo());
    	info.setName(clazz.getName());
    	info.setQuality(clazz.getQuality());
    	info.setSortName(clazz.getSortName());
    	info.settScGradeId(clazz.gettScGradeId());
    	return info;
    }

	public static List<ClazzInfo> changeToVo(List<Clazz> list) {
		List<ClazzInfo> infos = new ArrayList<ClazzInfo>();
		if (list.size() > 0) {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				infos.add(changeToVo(list.get(i)));
			}
		}
		return infos;
	}
}
