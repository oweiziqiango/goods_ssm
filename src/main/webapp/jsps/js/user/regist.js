$(function() {
	/*
	 * 1.得到所以错误信息，循环遍历，调用showError（）方法,确定是否显示错误信息
	 */
	$(".Error").each(function(){
		showError($(this));//循环调用，使用每个元素都调用showError方法
	});
	/*
	 * 2.切换注册图片
	 * hover()一个模仿悬停事件（鼠标移动到一个对象上面及移出这个对象）的方法
	 */
	$("#submitBtn").hover(
			//鼠标移到元素执行的函数
			function() {
				$("#submitBtn").attr("src","/goods_ssm/images/regist2.jpg");
			},
			//鼠标从元素上移走执行的函数
			function() {
				$("#submitBtn").attr("src","/goods_ssm/images/regist1.jpg");
			}
	);
	/*
	 * 3.输入框得到焦点，隐藏错误信息
	 */
	//inputText是所以input标签的class
	$(".inputText").focus(
			
			function(){
				var labelId=$(this).attr("id")+"Error";
				$("#"+labelId).text("");//把lable内的文本内容置空
				showError($("#"+labelId));
			}
	);
	/*
	 * 4.输入框失去焦点，进行校验
	 */
	$(".inputText").blur(
			function(){
				var id=$(this).attr("id");
				var funName="validate"+id.substring(0,1).toUpperCase()+id.substring(1)+"()";
				eval(funName);//将字符串当做javascript的语句执行
			}
	);
	/*
	 * 5.提交表单时，全部校验
	 */
	$("#registForm").submit(
		function(){
			if(!validateLoginname()){
				return false;
			}
			if(!validateLoginpass()){
				return false;
			}
			if(!validateReloginpass()){
				return false;
			}
			/*if(!validateEmail()){
				return false;
			}	
			if(!validateVerifycode()){
				return false;
			}*/
			return true;
		}
	);
});
/*
 * 校验用户名
 */
function validateLoginname(){
	var id="loginname";
	var value=$("#"+id).val();//input输入框内的value
	/*
	 * 1.判断是否为空
	 */
	if(!value){
		var labelId=id+"Error";//获得label的id
		$("#"+labelId).text("用户名不能为空！");//重置label标签内的text
		showError($("#"+labelId));//确定是否显示错误信息
		return false;
	}
	/*
	 * 2.判断长度是否在3~20之间
	 */
	if(value.length<3 || value.length>20){
		var labelId=id+"Error";
		$("#"+labelId).text("用户名长度必须在3~20之间！");
		showError($("#"+labelId));
		return false;
	}
	/*
	 * 3.是否注册
	 */
	/*$.ajax({
		type:"POST",
		url:"/goods/UserServlet",
		data:{method:"ajaxValidateLoginname",loginname:value},
		dataType:"json",
		asyns:false,
		cache:false,
		success:function(result){
			if(!result){
				var labelId=id+"Error";
				$("#"+labelId).text("用户名已经注册！");
				showError($("#"+labelId));
				return false;
			}
		}
		
	});*/
	return true;
}
/*
 * 校验密码 
 */
function validateLoginpass(){
	var id="loginpass";
	var value=$("#"+id).val();//input输入框内的value
	/*
	 * 1.判断是否为空
	 */
	if(!value){
		var labelId=id+"Error";
		$("#"+labelId).text("密码不能为空！");//label标签内的text
		showError($("#"+labelId));//确定是否显示错误信息
		return false;
	}
	/*
	 * 2.判断长度是否在3~20之间
	 */
	if(value.length<3 || value.length>20){
		var labelId=id+"Error";
		$("#"+labelId).text("密码长度必须在3~20之间！");
		showError($("#"+labelId));
		return false;
	}
	return true;
}
/*
 * 校验确认密码
 * */
function validateReloginpass(){
	var id="reloginpass";
	var value=$("#"+id).val();//input输入框内的value
	/*
	 * 1.判断是否为空
	 */
	if(!value){
		var labelId=id+"Error";
		$("#"+labelId).text("确认密码不能为空！");//label标签内的text
		showError($("#"+labelId));//确定是否显示错误信息
		return false;
	}
	/*
	 * 2.判断是否在相等  jquery 取值函数val()
	 */
	if(value!=($("#loginpass").val())){
		var labelId=id+"Error";
		$("#"+labelId).text("两次输入密码不一样！");
		showError($("#"+labelId));
		return false;
	}
	return true;
}
/*
 * 校验邮箱
 * */
function validateEmail(){
	var id="email";
	var value=$("#"+id).val();//input输入框内的value
	/*
	 * 1.判断是否为空
	 */
	if(!value){
		var labelId=id+"Error";
		$("#"+labelId).text("Email不能为空！");//label标签内的text
		showError($("#"+labelId));//确定是否显示错误信息
		return false;
	}
	/*
	 * 2.判断Email格式   运用正则表达式
	 */
	if(!(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/.test(value))){
		var labelId=id+"Error";
		$("#"+labelId).text("Email格式不正确！");
		showError($("#"+labelId));
		return false;
	}
	/*
	 * 3.是否注册
	 */
	/*$.ajax({
		type:"POST",
		url:"/goods/UserServlet",
		data:{method:"ajaxValidateEmail",email:value},
		dataType:"json",
		asyns:false,
		cache:false,
		success:function(result){
			if(!result){
				var labelId=id+"Error";
				$("#"+labelId).text("Email已经注册！");
				showError($("#"+labelId));
				return false;
			}
		}
		
	});*/
	return true;
}
/*
 * 校验验证码
 * */
function validateVerifycode(){
	var id="verifycode";
	var value=$("#"+id).val();//input输入框内的value
	/*
	 * 1.判断是否为空
	 */
	if(!value){
		var labelId=id+"Error";
		$("#"+labelId).text("验证码不能为空！");//label标签内的text
		showError($("#"+labelId));//确定是否显示错误信息
		return false;
	}
	/*
	 * 2.判断验证码是否为4位
	 */
	if(value.length!=4){
		var labelId=id+"Error";
		$("#"+labelId).text("不正确的验证码！");//label标签内的text
		showError($("#"+labelId));//确定是否显示错误信息
		return false;
	}
    /*
     * 3.判读验证码是否正确
     */
	$.ajax({
		type:"POST",
		url:"/goods/UserServlet",
		data:{method:"ajaxValidateVerifyCode",verifycode:value},
		dataType:"json",
		asyns:false,
		cache:false,
		success:function(result){
			if(!result){
				var labelId=id+"Error";
				$("#"+labelId).text("验证码不正确！");
				showError($("#"+labelId));
				return false;
			}
		}
		
	});
	return true;
}
/*
 * 确定是否显示错误信息
 */
function showError(ele){
	var text=ele.text();//获取元素上的text
	if(!text){//判断是否text为空
		ele.css("display","none");//text为空，不显示，使用css（）方法，修改样式，隐藏
	}else{
		ele.css("display","");//text不为空，显示
	}
}
/*
 * 换一张验证码
 */
function _hyz(){
	/*
	 * 1.获取img元素
	 * 2.重置src属性
	 * 3.使用毫秒来添加参数
	 */
	$("#img").attr("src","/goods/VerifyCodeServlet?a="+new Date().getTime());
}


