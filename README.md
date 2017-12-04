2017年12月4日19:44:29

SpringMVC 获取请求参数
紧藕方式：
使用原有的request对象进行获取
只需要在方法内声明request形参即可
按照request的使用方式正常使用
 pringmvc参数优点：
    没有参数，不赋值，正常执行
    有参数，则给参数进行赋值，然后执行。
解耦方式一：
请求参数名和方法参数名相同，方法的形参名和请求参数名相同会自动赋值
解耦方式二： 请求参数名和方法参数名不同
使用别名的方式进行匹配：
在方法的参数（请求参数名和方法参数名不同的参数）前 使用 注解
   @RequestParam(value="name",required=true)
 value（必写） 为请求参数名
   required =
true:表示参数必须有值，没有则报400
  默认是fasle，没有值也行。
  required和defaultValue不能一起使用。
 defaultValue：当请参数值为null或者或空字符串的时候会将默认值赋值给方法参数。
解耦方式三：
 如果方法参数为引用类型，则执行时直接给参数创建对象，并且 请求参数名与引用对象属性名相同时，会自动匹配
解耦方式四：
 当方法参数类型为多种类型时， 请求参数会匹配所有与请求参数名相同的对象属性名和方法参数名
package com.bjsxt.controller;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.bjsxt.pojo.People;
@Controller
public class TestCon {
     @RequestMapping("show")
     public String show(){
          
          System.out.println("TestCon.show(获取请求参数的方式)");
          return "index.jsp";
     }
     
     
     /**
      * 紧藕方式：
      *        使用原有的request对象进行获取
      *        只需要在方法内声明request形参即可
      *        按照request的使用方式正常使用
 *         Springmvc参数优点：
 *                 没有参数，不赋值，正常执行
 *                 有参数，则给参数进行赋值，然后执行。
      * @return
      */
     @RequestMapping("show1")
     public  String show1(HttpServletRequest req) {
          String name = req.getParameter("name");
          String  id = req.getParameter("id");
          System.out.println("TestCon.show1(紧藕方式) id:"+id+"  name:"+name);
          return "index.jsp";
     }
     
     /**
      * 解耦方式一：
      *        请求参数名和方法参数名相同
      *        方法的形参名和请求参数名相同会自动赋值
      * @param req
      * @return
      */
     @RequestMapping("show2")
     public  String show2(String name ,String id) {
          System.out.println("(解耦方式一) id:"+id+"  name:"+name);
          return "index.jsp";
     }
     
     /**
      * 解耦方式二： 请求参数名和方法参数名不同
      *        使用别名的方式进行匹配
      *            在方法的参数（请求参数名和方法参数名不同的参数）前 使用 注解
      *                 @RequestParam(value="name",required=true)
      *            value（必写） 为请求参数名
      *            required
      *                      true:表示参数必须有值，没有则报400
      *                      默认是fasle，没有值也行。
      *                      required和defaultValue不能一起使用。
      *            defaultValue：当请参数值为null或者或空字符串的时候会将默认值赋值给方法参数。       
      * @param name
      * @param id
      * @return
      */
     @RequestMapping("show3")
     public  String show3(@RequestParam(value="name",required=true) String aname ,@RequestParam(value="id",required=true) String aid) {
          System.out.println("(解耦方式二) id:"+aid+"  name:"+aname);
          return "index.jsp";
     }
     
     /**
      * 解耦方式三：
      *        如果方法参数为引用类型，则执行时直接给参数创建对象，
      *        并且 请求参数名与引用对象属性名相同时，会自动匹配
      *
      * @return
      */
     @RequestMapping("show4")
     public  String show4(People peo) {
          System.out.println("(解耦方式三)"+ peo);
          return "index.jsp";
     }
     
     /**
      * 解耦方式四：
      *            当方法参数类型为多种类型时，
      *            请求参数会匹配所有与请求参数名相同的对象属性名和方法参数名
      * @param peo
      * @return
      */
     @RequestMapping("show5")
     public  String show4(People peo,String name) {
          System.out.println("(解耦方式四)"+ peo+" name:"+name);
          return "index.jsp";
     }
}
使用result方式进行赋值
（基本获取方式和上述获取获取方式相同，只是请求数据格式不同）
前台界面使用指定的格式进行发送数据请求
  <a href="show6/zhangsan/123">
后台使用指定的格式进行变量占位接收数据请求
  @RequestMapping("show6/{name}/{id}")
 在方法的每个参数前使用@PathVariable 进行注明（只有请求参数名和方法参数名相同是才会自动匹配，否则就要使用别名）
@RequestMapping("show6/{name}/{id}")
     public  String show6(@PathVariable String name,@PathVariable String id) {
          System.out.println("(result方式) id:"+id+" name:"+name);
          return "index.jsp";
     }

获取请求参数中，同名不同值得键值对（例如：复选框）
@RequestMapping("show7")
     public  String show7(@RequestParam(value="c") List<String> chks) {
          System.out.println("(复选框) chks:"+chks);
          return "index.jsp";
     }

SpringMVC 响应数据
基于Request的作用域传值
 使用解耦方式一 ：作用域传值
  使用Map集合传值
 在控制单元方法声明Map集合
 在方法内直接给Map进行数据填充，相当于填充到了request的作用域中
  map.put("code", "map");
springmvc使用解耦方式二：作用域传值
 使用Model方式
 在控制单元方法中声明Model对象
 使用model.addAttribute("code", "model");进行数据填充，相当于使用request作用域

请求转发和重定向
springmvc的请求转发
  默认是请求转发
  单元方法的请求转发的返回值：
  return "forward:/index.jsp"; ("forward: " 可以省略不写)
springmvc的重定向
 单元方法的重定向的返回值：redirect:路径
 例如：return "redirect:/index.jsp";
  注意：
 重定向的时候会将第一次请求的request作用域中的数据作为第二次请求的请求参数使用
直接响应处理结果
单元方法没有返回值
  使用response对象直接输出相应结果即可
 resp.getWriter().write("zhi jie "); 
springmvc的json传参（重点）
后台获取前台请求的json数据
原有方式该怎么时候就怎么使用
 注意：springmvc会要求我们单元方法必须对ajax的请求做出响应，不然就报错。
 将ajax发送给后台的数据直接封装到对象中，而不是使用req对象获取。
 前端ajax请求参数
 contentType:"application/json",/* 设置ajax请求数据为json  */
 data:JSON.stringify({"name":"zhansgan","id":"123"}),
 后台在单元方法的应用对象前使用注解@RequestBody，Springmvc会自动将josn数据填充到对象中。
后台响应给前台json数据
在单元方法上使用注解@ResponseBody
 然后单元方法直接返回java对象即可。
 前端接受的是将java对象转换好的json对象，所有前端不用再使用eval。
$(document).ready(function(){
          $("input[type=button]").click(function(){
              $.ajax({
                   type:"post",
                   url:"show6",
                    contentType:"application/json",/* 设置ajax请求数据为json  */
                   data:JSON.stringify({"name":"zhansgan","id":"123"}),
                   success:function(data){
                        alert(data.name);
                   }
              });
          })        
     })
@RequestMapping("show6")
     @ResponseBody
     public People show6(@RequestBody People peo, HttpServletResponse resp) throws IOException{
          System.out.println("接受到请求:"+peo);
          People p1=new People(7788, "wangwu");
          
          return p1;

     }





20171110