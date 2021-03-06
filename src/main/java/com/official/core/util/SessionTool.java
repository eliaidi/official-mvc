package com.official.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
 
/**
 * Session 工具类
 * @author Administrator
 *
 */
public class SessionTool {
	
	private HttpServletRequest request;
	
	private HttpSession session;
	
	private static SessionTool st=null;
	
	private int defaultTime=30*60;//半小时
	
	private SessionTool(HttpServletRequest request){
		this.request=request;
		setSession(request.getSession());
	}
	private SessionTool(HttpServletRequest request,int defaultTime){
		this(request);
		this.setDefaultTime(defaultTime);
	}
	/**
	 * 实例化SessionTool
	 * @param request
	 * @return
	 */
	public static SessionTool getInstance(HttpServletRequest request){
		if(st==null||st.getSession()==null){
			synchronized(SessionTool.class){
				if(st==null){
					st=new SessionTool(request);
				}
			}
		}	
		return st;
	}
	/**
	 * 清空
	 */
	public static void flush(){
		st=null;
	}
	/**
	 * 实例化SessionTool
	 * @param request
	 * @param defaultTime
	 * @return
	 */
	public static SessionTool getInstance(HttpServletRequest request,int defaultTime){
		return new SessionTool(request,defaultTime);
	}
	/**
	 * Session是否存在
	 * @param key
	 * @return
	 */
	public boolean exists(String key){
		Object obj=get(key);
		return obj!=null||StringUtils.isNotBlank(Commutil.null2String(obj));
	}
	/**
	 * 获取Session值
	 * @param key
	 * @return
	 */
	public Object get(String key){
		try{
			return session.getAttribute(key);
		}catch(Exception e){
			
		}
		return "";
	}
	/**
	 * 插入Session
	 * @param key
	 * @param value
	 * @param time
	 */
	public void set(String key,Object value,int time){
		session.setAttribute(key, value);
		session.setMaxInactiveInterval(time);
	}
	/**
	 * 插入Session
	 * @param key
	 * @param Object
	 */
	public void set(String key,Object value){
		set(key,value,defaultTime);
	}
	
	public void del(String...keys){
		for(String key:keys){
			session.removeAttribute(key);
		}
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}
	public int getDefaultTime() {
		return defaultTime;
	}
	public void setDefaultTime(int defaultTime) {
		this.defaultTime = defaultTime;
	}

}
