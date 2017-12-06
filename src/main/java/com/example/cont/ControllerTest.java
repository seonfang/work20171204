package com.example.cont;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.People;
import com.example.entity.Person;
import com.example.entity.User;
import com.example.repository.UserRepository;

@Controller
public class ControllerTest {

	@RequestMapping("/show")
	public String test() {
		// model.addAttribute("code", "forward");
		// return "index";
		return "json";
	}

	@RequestMapping("/show1")
	public String test2(@RequestParam(value = "name", defaultValue = "admin") String name,
			@RequestParam(value = "id", defaultValue = "100") String id) {
		System.out.println("" + name + "  " + id);
		return "succeed";
	}

	@RequestMapping("show2")
	public String show2(Person person) {
		System.out.println("(Person):" + person);
		return "succeed";
	}

	@RequestMapping("show3")
	public String show3(@RequestParam Map<String, String> m) {
		System.out.println("(Map):" + m);
		return "succeed";
	}

	@RequestMapping("show4")
	public String show4(@RequestHeader("User-Agent") String userAgent,
			@RequestHeader("Accept-Encoding") String encoding) {
		System.out.println("(userAgent):" + userAgent + "  (Encoding):" + encoding);

		return "succeed";
	}

	@RequestMapping("show5/{name}/{id}")
	public String show6(@PathVariable String name, @PathVariable String id) {
		System.out.println("(@PathVariable方式) id:" + id + " name:" + name);
		return "succeed";
	}

	// 响应界面
	@RequestMapping("show10")
	public String show7(Person person, Model model) {
		model.addAttribute("code", "model");
		model.addAttribute("per", person);
		return "model";
	}

	// 重定向
	@RequestMapping("show11")
	public String show11(Model model, RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("mss", "success");
		return "redirect:mo"; // model 为请求
	}

	@RequestMapping("mo")
	public String showM(String mss, Model model) {
		model.addAttribute("code", mss);
		return "model";
	}

	// 请求转发
	@RequestMapping("show12")
	public String show3(Person person, Model model) {
		model.addAttribute("msg", "forward");
		model.addAttribute("per", person);
		return "forward:mo";
	}

	// JSON
	/**
	 * 接收前台传的json
	 */
	@RequestMapping("showJson")
	@ResponseBody
	public People showJson(@RequestBody People peo) throws IOException {
		System.out.println("接受到请求:" + peo);
		People p1 = new People(7788, "wangwu");
		return p1;
	}

	/**
	 * Ajax响应:Json传递单个对象
	 */
	@RequestMapping("aj")
	@ResponseBody
	public People getJaon() {
		// 处理请求信息
		// 调用业务层
		// List<People> p=new ArrayList<People>();
		// p.add(new People(7788, "wangwu"));
		// p.add(new People(7799, "wangsu"));
		People p = new People(7700, "wangyu");
		return p;
	}

	/**
	 * Json 传递List
	 */
	@RequestMapping("aja")
	@ResponseBody
	public List<People> getJson() {

		List<People> p = new ArrayList<People>();
		p.add(new People(7788, "wangwu"));
		p.add(new People(7799, "wangsu"));
		p.add(new People(7711, "wangqu"));
		return p;
	}

	@RequestMapping("sess")
	public String ses(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.setAttribute("Name", "Seon");
		return "thymeleaf";
	}

	@RequestMapping("thyeach")
	public String getThy(Model model) {

		List<People> p = new ArrayList<People>();
		p.add(new People(7788, "wangwu"));
		p.add(new People(7799, "wangsu"));
		p.add(new People(7711, "wangqu"));
		model.addAttribute("peolist", p);
		return "thymeleaf";
	}
	@Autowired
	UserRepository userRepository;

	
	/**
	 * 展示所有用户信息
	 * @param model
	 * @return
	 */
	@RequestMapping("usershow")
	public String getUser(Model model) {
		List<User> userList =  userRepository.findAll();
		model.addAttribute("userlist", userList);
		System.out.println("userList:  "+userList);
		return "seleuser";
	}
	/**
	 *提交信息，并跳转到首页
	 */
	@RequestMapping("addUser")
	public String adduser(User user) {
		userRepository.saveAndFlush(user);
		System.out.println("(User):" + user);
		return "redirect:usershow";
	}

	/**
	 * 修改用户信息
	 */
	@RequestMapping("toUpdata")
	public String updauser(String id, Model model) {
		User user = new User();
		System.out.println("id:" + id);
		if(id=="" || id==null) {
		}else {
			user = userRepository.findOne(id);
		}
		System.out.println("USER----" + user);
		model.addAttribute("user", user);
		return "adduser";
	}
	
	/**
	 * 使用按钮切换中英文
	 */
	@RequestMapping("/changeLang")
	public String changeSessionLanguage(HttpServletRequest req, String lang) {
		if("zh".equals(lang)) {
			req.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("zh","CH"));
		}else if("en".equals(lang)){
			req.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("en","US"));
		}
		return "changeLang";
	}
	
	/**
	 * 移动端 电脑端 访问 不同页面
	 */
	@RequestMapping("/moandcom")
	public String moAndcom() {
		
		return "moblieandcomp";
	}
}
