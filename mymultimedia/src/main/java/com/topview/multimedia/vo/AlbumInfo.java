package com.topview.multimedia.vo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.topview.multimedia.bean.Pager;
import com.topview.multimedia.po.MultimediaAlbum;
import com.topview.multimedia.service.album.enums.AlbumEnums;
/**
 * 相册信息
 * 项目名称:com.topview.multimedia.vo<br>
 * 
 * @author houwenjun<br>
 * 创建时间:Apr 11, 2015<br>
 */
public class AlbumInfo {
	private String id;
	private String tMId;
	private String name;
	private String comment;
	private String description;
	private String createTime;
	private String updateTime;
	private Integer photoCount2;
	private String type;
	private Pager pager;
	private String coverPath;
	public String getCoverPath() {
		return coverPath;
	}
	
	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPhotoCount2() {
		return photoCount2;
	}

	public void setPhotoCount2(Integer photoCount2) {
		this.photoCount2 = photoCount2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public static MultimediaAlbum changeToPo(AlbumInfo albumInfo) {
		MultimediaAlbum album = new MultimediaAlbum();
		album.setComment(albumInfo.getComment());
		album.setDescription(albumInfo.getDescription());
		album.setId(albumInfo.getId());
		album.setName(albumInfo.getName());
		album.setPhotoCount2(albumInfo.getPhotoCount2());
		album.settMId(albumInfo.gettMId());
		album.setCoverPath(albumInfo.getCoverPath());
		AlbumEnums e = AlbumEnums.VIEW_PAGER.getName(albumInfo.getType());
		if (e != null) {
			album.setType(e.getCode());
		} 
		return album;
	}

	public static List<MultimediaAlbum> changeToPo(List<AlbumInfo> list) {
		List<MultimediaAlbum> albums = new ArrayList<MultimediaAlbum>();
		if (list.size() > 0) {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				albums.add(changeToPo(list.get(i)));
			}
		}
		return albums;
	}

	public static AlbumInfo changeToVo(MultimediaAlbum album) {
		AlbumInfo info = new AlbumInfo();
		info.setComment(album.getComment());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		info.setCreateTime(df.format(album.getCreateTime()));
		info.setUpdateTime(df.format(album.getUpdateTime()));
		info.setDescription(album.getDescription());
		info.setId(album.getId());
		info.setName(album.getName());
		info.setPhotoCount2(album.getPhotoCount2());
		info.settMId(album.gettMId());
		info.setCoverPath(album.getCoverPath());
		AlbumEnums enums = AlbumEnums.VIEW_PAGER.getCode(album.getType());
		if(enums!=null){
			info.setType(enums.getName());
		}
		return info;
	}

	public static List<AlbumInfo> changeToVo(List<MultimediaAlbum> list) {
		List<AlbumInfo> infos = new ArrayList<AlbumInfo>();
		if (list.size() > 0) {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				infos.add(changeToVo(list.get(i)));
			}
		}
		return infos;
	}
}