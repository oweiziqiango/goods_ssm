package com.cqupt.goods_ssm.web.frontend;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cqupt.goods_ssm.domain.extend.TUserExtend;
import com.cqupt.goods_ssm.service.UserService;

//处理器控制器
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	/*
	 * 处理方法的返回值
	 * 1.string
	 * 2.modelandview
	 * 3.void 传递json数据传   使用@ResponseBody，@RequestBody自动映射
	 */
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request,HttpServletResponse response,TUserExtend user)throws Exception{
		/*
		 * 1.通过spring mvc 参数绑定 user
		 * 2.校验省去
		 * 3.service 完成登录的查询 返回userFind
		 * 4.判断用户是否存在
		 * 5.判断用户状态是否激活
		 * 6.将登录的用户信息 存入session
		 * 7.将用户名存入cookie
		 */
		System.out.println("有user前来登录。。。。。。");
		
		TUserExtend userFind = userService.login(user);
		/*
		 * 如果等于null
		 * 
		 */
		if(userFind==null)
		{	
			request.setAttribute("msg", "用户名或密码错误");
			request.setAttribute("form", user);
			return "forward:/jsps/user/login.jsp";
		}else{
			/*
			 * 如果不等于null
			 *  用户状态是否为true
			 */
			if(!userFind.getStatus()){
				request.setAttribute("msg", "您还没有激活！");
				request.setAttribute("form", user);
				return "forward:/jsps/user/login.jsp";
			}else{
				
				/*
				 * springmvc 默认对pojo类型进行回显
				 * key等于pojo类型（首字母小写）
				 * 
				 * 这里手动设置sessionUser，为了对应前端已写好的页面
				 */
				request.getSession().setAttribute("sessionUser", userFind);
				
				String loginname=user.getLoginname();
				loginname=URLEncoder.encode(loginname,"utf-8");
				Cookie cookie=new Cookie("loginname",loginname);
				cookie.setMaxAge(60*60*24*10);
				response.addCookie(cookie);
				System.out.println("允许登录，跳转主页。。。。。。");
				return "redirect:/index.jsp";
			}
			
		}
	}
	
	@RequestMapping("/quit")
	public String quit(HttpServletRequest request,HttpServletResponse response)throws Exception{
		System.out.println("有user退出。。。。。。");
		
		request.getSession().invalidate();
		
		return "forward:/jsps/user/login.jsp";
	}
	
	
	@RequestMapping("/regist")
	public String regist(HttpServletRequest request,HttpServletResponse response,TUserExtend formUser)throws Exception{
		System.out.println("有user来注册。。。。。。");
		userService.regist(formUser);
		
		request.setAttribute("code", "success");
		request.setAttribute("msg", "注册功能，请马上到邮箱激活！");
		System.out.println("注册信息没有填写错误!\n");
		
		return "forward:/jsps/msg.jsp";
		
		//return "forward:/jsps/user/login.jsp";
	}
	@RequestMapping("/activation")
	public String activation(HttpServletRequest request,HttpServletResponse response,String activationCode) throws Exception{
		System.out.println("user前来激活。。。。。。");
		userService.activation(activationCode);
		request.setAttribute("code", "success");
		request.setAttribute("msg", "激活成功！请登录！");
		
		return "forward:/jsps/msg.jsp";
	}
	@RequestMapping("/updatePassword")
	public String updatePassword(HttpServletRequest request,HttpServletResponse response,
			String loginpass,String newloginpass) throws Exception{
		/*
		 * 1.获取绑定参数
		 * 2.从session中获取uid
		 * 3.将用户id，新旧pass 传给service，完成修改密码
		 * 4.调整修改成功信息页面
		 */
		TUserExtend user = (TUserExtend) request.getSession().getAttribute("sessionUser");
		
		userService.updatePassword(user.getUid(), loginpass, newloginpass);
		
		request.setAttribute("code", "success");
		request.setAttribute("msg", "密码修改成功！");
		return "forward:/jsps/msg.jsp";
	}
}
