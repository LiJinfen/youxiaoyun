package com.topview.multimedia.vo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.topview.multimedia.bean.Pager;
import com.topview.multimedia.po.MultimediaRichText;
import com.topview.multimedia.service.section.draft.enums.RichTextTypeEnum;
import com.topview.multimedia.util.UUIDUtil;

/**
 * 文章信息 项目名称:com.topview.multimedia.vo<br>
 * 
 * @author houwenjun<br>
 *         创建时间:Apr 11, 2015<br>
 */
public class RichTextInfo {
	private String zoneId;
	private String id;
	private String tMId;
	private String createTime;
	private String context;
	private Integer status;
	private String type;
	private String title;
	private String subtitle;
	private String titlephoto;
	private String summary;
	private Boolean essence;
	private Boolean top;
	private Pager pager;
	private String url;
	private String collectid;

	public String getCollectid() {
		return collectid;
	}

	public void setCollectid(String collectid) {
		this.collectid = collectid;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String gettMId() {
		return tMId;
	}

	public void settMId(String tMId) {
		this.tMId = tMId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getTitlephoto() {
		return titlephoto;
	}

	public void setTitlephoto(String titlephoto) {
		this.titlephoto = titlephoto;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Boolean getEssence() {
		return essence;
	}

	public void setEssence(Boolean essence) {
		this.essence = essence;
	}

	public Boolean getTop() {
		return top;
	}

	public void setTop(Boolean top) {
		this.top = top;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static MultimediaRichText changeToPo(RichTextInfo info) {
		MultimediaRichText richText = new MultimediaRichText();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		richText.setId(info.getId() == null ? UUIDUtil.getUUID() : info.getId());
		richText.setContext(info.getContext());
		richText.setEssence(info.getEssence());
		richText.setStatus(info.getStatus());
		richText.setSubtitle(info.getSubtitle());
		richText.setSummary(info.getSummary());
		richText.setTitle(info.getTitle());
		richText.setTitlephoto(info.getTitlephoto());
		richText.settMId(info.gettMId());
		richText.setTop(info.getTop());
		RichTextTypeEnum type = RichTextTypeEnum.DRAFT; // 这里只是为了得到RichaTextTypeEnum实例，调用type.getName().getCode()方法
		richText.setType(type.getName(info.getType()).getCode());
		
		if (info.getCreateTime() != null && !"".equals(info.getCreateTime())) {
			try {
				richText.setCreateTime(df.parse(info.getCreateTime()));
			} catch (ParseException e) {
				e.printStackTrace();
				richText.setCreateTime(new Date());
			}
		} else {
			richText.setCreateTime(new Date());
		}
		return richText;
	}

	public static List<MultimediaRichText> changeToPo(List<RichTextInfo> list) {
		List<MultimediaRichText> photos = new ArrayList<MultimediaRichText>();
		if (list.size() > 0) {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				photos.add(changeToPo(list.get(i)));
			}
		}
		return photos;
	}

	public static RichTextInfo changeToVo(MultimediaRichText richText) {
		RichTextInfo info = new RichTextInfo();
		info.setContext(richText.getContext());
		info.setEssence(richText.getEssence());
		info.setId(richText.getId());
		info.setStatus(richText.getStatus());
		info.setSubtitle(richText.getSubtitle());
		info.setSummary(richText.getSummary());
		info.setTitle(richText.getTitle());
		info.setTitlephoto(richText.getTitlephoto());
		info.settMId(richText.gettMId());
		info.setTop(richText.getTop());
		RichTextTypeEnum type = RichTextTypeEnum.DRAFT;
		info.setType(type.getCode(richText.getType()).getName());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		info.setCreateTime(df.format(richText.getCreateTime()));
		return info;
	}

	public static List<RichTextInfo> changeToVo(List<MultimediaRichText> list) {
		List<RichTextInfo> photos = new ArrayList<RichTextInfo>();
		if (list.size() > 0) {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				photos.add(changeToVo(list.get(i)));
			}
		}
		return photos;
	}

	public List<Object> convert2Object(List<RichTextInfo> lines) {

		List<Object> objects = new LinkedList<Object>();
		for (int i = 0; i < lines.size(); i++) {
			objects.add(i, lines.get(i));
		}
		return objects;
	}

}
