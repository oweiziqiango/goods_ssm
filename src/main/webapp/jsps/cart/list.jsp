<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>cartlist.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/cart/list.css'/>">
<script type="text/javascript">
$(function(){
	showTotal();
	setCartItemCheck(true);
	setjieSuan(true);
	/*
	*给全选框 添加点击事件
	*/
	$("#selectAll").click(function(){
		/*
		*1.点击全选，设置所有itemcartcheck 
		*2.调用设置结算按钮
		*3.调用计算总计
		*/
		var bool=$("#selectAll").attr("checked");
		//$(":checkbox[name=checkboxBtn]").attr("checked",bool);
		setCartItemCheck(bool);
		setjieSuan(bool);
		showTotal();
	});
	/*
	*给所有条目的复选框添加click事件
	*/
	$(":checkbox[name=checkboxBtn]").click(function(){
		var all=$(":checkbox[name=checkboxBtn]").length;
		var select=$(":checkbox[name=checkboxBtn][checked=true]").length;
		if(all==select){//全选中
			$("#selectAll").attr("checked",true);//全选框选中
			//setCartItemCheck(true);
			setjieSuan(true);//结算按钮生效
			//showTotal();	
		}else if(select==0){//全不选中
			$("#selectAll").attr("checked",false);//全选框不选中
			//setCartItemCheck(false);
			setjieSuan(false);//结算按钮失效
			//showTotal();	
		}else{//部分选中
			$("#selectAll").attr("checked",false);
			setjieSuan(true);
			//showTotal();	
		}
		showTotal();
	});
	
	$(".jian").click(function() {
		/*
		1.获取id
		2.获取数量
		3.ajax修改数量和小计
		*/
		
		var id = $(this).attr("id").substring(0,32);
		var quantity=$("#"+id+"Quantity").val();
		//alert(id);
		if(quantity == 1){
			if(confirm("是否真的删除该条目？"))
				location = "/cart/batchDelete.action?cartItemIds="+id;
		}else{
			sendQuantityAjax(id,Number(quantity)-1);
		}
	});
	
	
	$(".jia").click(function() {
		/*
		1.获取id
		2.获取数量
		3.ajax修改数量和小计
		*/
		var id = $(this).attr("id").substring(0,32);
		var quantity=$("#"+id+"Quantity").val();
		//alert("jia");
		
		sendQuantityAjax(id,Number(quantity)+1);
	});
	
});
/*
*ajax修改图书数量
*/
function sendQuantityAjax(cartItemId,quantity){
	$.ajax({
		async:false,
		cache:false,
		url:"/cart/updateQuantityCartItem.action",
		data:{cartitemid:cartItemId,quantity:quantity},
		type:"POST",
		dataType:"json",
		success:function(result){
			/*
			1.获取数量和小计
			2.修改页面上的数量和小计
			3.计算总计
			*/
			var quantity=result.cartItem.quantity;
			var subtotal=result.subtotal;
			$("#"+cartItemId+"Quantity").val(quantity);
			$("#"+cartItemId+"Subtotal").text(subtotal);
			showTotal();
		}
	});
}
 /*
 *计算总计
 */
 function showTotal(){
 	var total=0;
 	//alert("id");
 	$(":checkbox[name=checkboxBtn][checked=true]").each(function(){
 		
 		var id = $(this).val();
 		//alert(id);
 		var subtoatl = $("#"+id+"Subtotal").text();
 		//alert(subtoatl);
 		total += Number(subtoatl);
 	});
 	//alert("id3");
 	$("#total").text(round(total,2));//round(total,2)把total处理保留2位小数
 }
 /*
  *统一设置cartItemCheck
 */
 function setCartItemCheck(bool){
 	$(":checkbox[name=checkboxBtn]").attr("checked",bool);
 }
 /*
 *设置结算按钮  
 */
 function setjieSuan(bool){
	if(bool){
		$("#jiesuan").removeClass("kill").addClass("jiesuan");
		$("#jiesuan").unbind("click");
	}else{
		$("#jiesuan").removeClass("jiesuan").addClass("kill");
		$("#jiesuan").click(function(){return false;});
	}
 }
 //批量删除
 function batchDelete(){
 	/*
 	1.获取被选框的值
 	2.存入字符数组中
 	3.location定位
 	*/
 	var cartItemIdArray=new Array();
 	$(":checkbox[name=checkboxBtn][checked=true]").each(function(){
 		var id=$(this).val();
 		cartItemIdArray.push(id);//将复选框的值放入数组中
 	});
 	//批量删除
 	location = "/cart/batchDelete.action?cartItemIds="+cartItemIdArray;
 }
 //点击结算
 function jiesuan(){
 //1.获取当前选中的复选框的值
 	var cartItemIdArray=new Array();
 	$(":checkbox[name=checkboxBtn][checked=true]").each(function(){
 		var id=$(this).val();
 		cartItemIdArray.push(id);
 	});
 	//2.给input的cartItemIds和hiddenTotal赋值
 	$("#cartItemIds").val(cartItemIdArray.toString());
 	//alert(cartItemIdArray.toString());
 	$("#hiddenTotal").val($("#total").text());
 	//3.触发提交
 	$("#jiesuanform").submit();
 }
</script>
  </head>
  <body>
<c:choose>
	<c:when test="${empty cartItemList }">
	

	<table width="95%" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right">
				<img align="top" src="<c:url value='/images/icon_empty.png'/>"/>
			</td>
			<td>
				<span class="spanEmpty">您的购物车中暂时没有商品</span>
			</td>
		</tr>
	</table>  

</c:when>
	<c:otherwise>
	
<table width="95%" align="center" cellpadding="0" cellspacing="0">
	<tr align="center" bgcolor="#efeae5">
		<td align="left" width="50px">
			<input type="checkbox" id="selectAll" checked="checked"/><label for="selectAll">全选</label>
		</td>
		<td colspan="2">商品名称</td>
		<td>单价</td>
		<td>数量</td>
		<td>小计</td>
		<td>操作</td>
	</tr>



<c:forEach items="${cartItemList }"  var="cartItem">
	<tr align="center">
		<td align="left">
			<input value="${cartItem.cartitemid }" type="checkbox" name="checkboxBtn" checked="checked"/>
		</td>
		<td align="left" width="70px">
			<a class="linkImage" href="<c:url value='/jsps/book/desc.jsp'/>"><img border="0" width="54" align="top" src="<c:url value='/${cartItem.book.imageB}'/>"/></a>
		</td>
		<td align="left" width="400px">
		    <a href="<c:url value='/jsps/book/desc.jsp'/>"><span>${cartItem.book.bname }</span></a>
		</td>
		<td><span>&yen;<span class="currPrice" >${cartItem.book.currprice }</span></span></td>
		<td>
			<a class="jian" id="${cartItem.cartitemid }Jian"></a>
			<input class="quantity" readonly="readonly" id="${cartItem.cartitemid }Quantity" type="text" value="${cartItem.quantity }"/>
			<a class="jia" id="${cartItem.cartitemid }Jia"></a>
		</td>
		<td width="100px">
			<span class="price_n">&yen;<span class="subTotal" id="${cartItem.cartitemid }Subtotal">${cartItem.subtotal }</span></span>
		</td>
		<td>
			<a href="<c:url value='/cart/batchDelete.action?cartItemIds=${cartItem.cartitemid }'/>">删除</a>
		</td>
	</tr>
</c:forEach>





	
	<tr>
		<td colspan="4" class="tdBatchDelete">
			<a href="javascript:batchDelete();">批量删除</a>
		</td>
		<td colspan="3" align="right" class="tdTotal">
			<span>总计：</span><span class="price_t">&yen;<span id="total"></span></span>
		</td>
	</tr>
	<tr>
		<td colspan="7" align="right">
			<a href="javascript:jiesuan();" id="jiesuan" class="jiesuan"></a>
		</td>
	</tr>
</table>
	
	<form id="jiesuanform" action="<c:url value='/cart/loadCartItems.action'/>" method="post">
		<input type="hidden" name="cartItemIds" id="cartItemIds"/>
		<input type="hidden" name="total" id="hiddenTotal"/>
		<!--加载购物车  -->
		<!-- <input type="hidden" name="method" value="loadCartItems"/> -->
	</form>

</c:otherwise>
</c:choose>

  </body>
</html>
