package com.topview.multimedia.service.post;

import javax.servlet.http.HttpSession;

import com.topview.multimedia.vo.PostVo;
import com.topview.multimedia.vo.PraiseVo;
import com.topview.multimedia.vo.ReplyVo;
import com.topview.multimedia.vo.result.PostVoResult;
import com.topview.multimedia.vo.result.PraiseVoResult;
import com.topview.multimedia.vo.result.ReplyVoResult;

public interface PostService {

	/**
	 * 保存主帖
	 * @return
	 */
	public PostVoResult savePost(PostVo vo);
	
	/**
	 * 保存评论
	 * @param vo
	 * @return
	 */
	public ReplyVoResult saveReply(ReplyVo vo);
	
	/**
	 * 保存点赞
	 * @param vo
	 * @return
	 */
	public PraiseVoResult savePraise(PraiseVo vo);
	
	/**
	 * 根据班级id、帖子狀態和时间查询该班级指定时间之后的所有帖子,若时间为空则查询该班级所有帖子
	 * @param classId
	 * @return
	 */
	public PostVoResult seePostByTMid(HttpSession session, String classId, int status, String lastUpdate, String start, String limit);
	
	/**
	 * 根据帖子id和时间查询评论
	 * @param postId
	 * @return
	 */
	public ReplyVoResult seeReplyByPostId(String postId, String lastUpdate);
	
	/**
	 * 根据帖子id和时间查询点赞
	 * @param postId
	 * @param lastUpdate
	 * @return
	 */
	public PraiseVoResult seePraiseByPostId(String postId, String lastUpdate);
	
	/**
	 * 删除帖子或删除回复或取消赞 ,type等于1删除帖子;等于2删除评论;等于三删除点赞
	 * @param id
	 * @return
	 */
	public PostVoResult delete(String id, int type);
	
	/**
	 * 根据点赞者id和帖子id查询点赞记录
	 * @param praiserId
	 * @param postId
	 * @return
	 */
	public PraiseVoResult judgePraise(String praiserId, String postId);
	
}
